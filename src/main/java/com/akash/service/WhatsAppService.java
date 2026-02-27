package com.akash.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WhatsAppService {

    private static final String GRAPH_API_BASE = "https://graph.facebook.com/v18.0";

    @Value("${whatsapp.api.token}")
    private String apiToken;

    @Value("${whatsapp.phone.number.id}")
    private String phoneNumberId;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Uploads the PDF to WhatsApp media endpoint, then sends it as a document
     * message to the given 10-digit Indian phone number.
     *
     * @param pdfBytes       PDF content as byte array
     * @param fileName       File name (without .pdf extension)
     * @param toPhoneNumber  10-digit customer phone number (e.g. "9876543210")
     */
    public boolean sendPdf(byte[] pdfBytes, String fileName, String toPhoneNumber) {
        try {
            String mediaId = uploadMedia(pdfBytes, fileName + ".pdf");
            sendDocumentMessage(mediaId, fileName + ".pdf", toPhoneNumber);
            return true;
        } catch (Exception e) {
            System.err.println("WhatsApp send failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private String uploadMedia(byte[] pdfBytes, String fileName) {
        String url = GRAPH_API_BASE + "/" + phoneNumberId + "/media";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(apiToken);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("messaging_product", "whatsapp");
        body.add("type", "application/pdf");
        body.add("file", new ByteArrayResource(pdfBytes) {
            @Override
            public String getFilename() {
                return fileName;
            }
        });

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        Map<?, ?> responseBody = response.getBody();
        if (responseBody == null || !responseBody.containsKey("id")) {
            throw new RuntimeException("Media upload failed: no id in response");
        }
        return (String) responseBody.get("id");
    }

    private void sendDocumentMessage(String mediaId, String fileName, String toPhoneNumber) {
        String url = GRAPH_API_BASE + "/" + phoneNumberId + "/messages";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiToken);

        // Format: India country code prefix + 10-digit number
        String formattedNumber = "91" + toPhoneNumber;

        Map<String, Object> document = new HashMap<>();
        document.put("id", mediaId);
        document.put("filename", fileName);

        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("messaging_product", "whatsapp");
        messageBody.put("to", formattedNumber);
        messageBody.put("type", "document");
        messageBody.put("document", document);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(messageBody, headers);
        restTemplate.postForEntity(url, request, Map.class);
    }
}
