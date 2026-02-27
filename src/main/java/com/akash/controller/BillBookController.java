package com.akash.controller;

import com.akash.entity.AppUser;
import com.akash.entity.BillBook;
import com.akash.entity.BillBookSearch;
import com.akash.entity.Sales;
import com.akash.repository.AppUserRepository;
import com.akash.repository.BillBookRepository;
import com.akash.repository.ClearDuesRepository;
import com.akash.repository.DayBookRepository;
import com.akash.repository.GoodsReturnRepository;
import com.akash.repository.LabourCostRepository;
import com.akash.repository.LabourGroupRepository;
import com.akash.repository.ProductRepository;
import com.akash.repository.SiteRepository;
import com.akash.repository.SizeRepository;
import com.akash.repository.VehicleRepository;
import com.akash.entity.dto.BillBookDTO;
import com.akash.repository.projections.LabourCostProj;
import com.akash.service.WhatsAppService;
import com.akash.util.CommonMethods;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value={"/bill-book"})
public class BillBookController {
    @Autowired
    BillBookRepository billBookRepository;
    @Autowired
    LabourCostRepository labourCostRepository;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    VehicleRepository vehicleRepo;
    @Autowired
    SiteRepository siteRepository;
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    LabourGroupRepository labourGroupRepository;
    @Autowired
    DayBookRepository dayBookRepo;
    @Autowired
    ClearDuesRepository clearDuesRepo;
    @Autowired
    GoodsReturnRepository goodsReturnRepository;
    @Autowired
    WhatsAppService whatsAppService;
    int from = 0;
    int total = 0;
    Long records = 0L;

    @GetMapping
    public String add(Model model, HttpServletResponse response) {
        model.addAttribute("billBook", (Object)new BillBook());
        String[] userTypes = new String[]{"Customer", "Contractor"};
        model.addAttribute("customers", this.appUserRepository.findAppUsersOnType(userTypes, true));
        model.addAttribute("vehicles", (Object)this.vehicleRepo.findAll());
        model.addAttribute("products", (Object)this.productRepository.findAll());
        model.addAttribute("labourGroups", (Object)this.labourGroupRepository.findAll());
        if (model.asMap().containsKey("print")) {
            BillBook billBook = (BillBook)model.asMap().get("billBookPrint");
            Double prevBalance = (Double)model.asMap().get("prevBalance");
            Double finalBalance = (Double)model.asMap().get("finalBalance");
            this.printBillBook(billBook, response, prevBalance, finalBalance);
        }
        return "billBook";
    }

    @RequestMapping(value={"/save"}, params={"save"}, method={RequestMethod.POST})
    public String save(@ModelAttribute(value="billBook") BillBook billBook, Model model, RedirectAttributes redirectAttributes) {
        if (Objects.isNull(billBook.getDate())) {
            billBook.setDate(LocalDate.now());
        }
        if (billBook.getVehicle() != null) {
            billBook.setDriver(billBook.getVehicle().getDriver());
        }
        if (billBook.getLoadingAmount() != null || billBook.getUnloadingAmount() != null) {
            this.setLoadingAndUnloadingCharges(billBook);
        }
        billBook.getSales().forEach(s -> s.setBillBook(billBook));
        this.billBookRepository.save(billBook);
        redirectAttributes.addFlashAttribute("success", (Object)"Bill Book saved successfully");
        return "redirect:/day-book";
    }

    @RequestMapping(value={"/save"}, params={"print"}, method={RequestMethod.POST})
    public String printAndSaveBillBook(@ModelAttribute(value="billBook") BillBook billBook, @RequestParam(value="prevBalance") Double prevBalance, @RequestParam(value="finalBalance") Double finalBalance, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        if (billBook.getVehicle() != null) {
            billBook.setDriver(billBook.getVehicle().getDriver());
        }
        this.setLoadingAndUnloadingCharges(billBook);
        this.billBookRepository.save(billBook);
        redirectAttributes.addFlashAttribute("success", (Object)"Bill Book saved successfully");
        redirectAttributes.addFlashAttribute("print", (Object)true);
        redirectAttributes.addFlashAttribute("billBookPrint", (Object)billBook);
        redirectAttributes.addFlashAttribute("prevBalance", (Object)prevBalance);
        redirectAttributes.addFlashAttribute("finalBalance", (Object)finalBalance);
        return "redirect:/day-book";
    }

    @RequestMapping(value={"/save"}, params={"whatsapp"}, method={RequestMethod.POST})
    public String saveAndWhatsApp(@ModelAttribute(value="billBook") BillBook billBook, @RequestParam(value="prevBalance") Double prevBalance, @RequestParam(value="finalBalance") Double finalBalance, RedirectAttributes redirectAttributes) {
        if (billBook.getVehicle() != null) {
            billBook.setDriver(billBook.getVehicle().getDriver());
        }
        this.setLoadingAndUnloadingCharges(billBook);
        billBook.getSales().forEach(s -> s.setBillBook(billBook));
        this.billBookRepository.save(billBook);
        String phone = billBook.getCustomer() != null ? billBook.getCustomer().getContact() : null;
        if (phone != null && !phone.isEmpty()) {
            byte[] pdfBytes = this.generatePdfBytes(billBook, prevBalance, finalBalance);
            boolean sent = this.whatsAppService.sendPdf(pdfBytes, billBook.getReceiptNumber(), phone);
            if (sent) {
                redirectAttributes.addFlashAttribute("success", (Object)"Bill saved and sent on WhatsApp");
            } else {
                redirectAttributes.addFlashAttribute("success", (Object)"Bill saved successfully");
                redirectAttributes.addFlashAttribute("fail", (Object)"WhatsApp sending failed");
            }
        } else {
            redirectAttributes.addFlashAttribute("success", (Object)"Bill saved (no phone number found for WhatsApp)");
        }
        return "redirect:/day-book";
    }

    @GetMapping(value={"/edit/{id}"})
    public String update(@PathVariable(value="id") long id, Model model) {
        BillBook billBook = this.billBookRepository.findById(id).orElse(null);
        Double finalBalance = this.getBalance(billBook.getCustomer().getId());
        Double prevBalance = finalBalance - billBook.getBalance();
        model.addAttribute("billBook", (Object)billBook);
        String[] userTypes = new String[]{"Customer", "Contractor"};
        model.addAttribute("customers", this.appUserRepository.findByUserType_NameInAndActive(userTypes, true));
        List<AppUser> labours = this.appUserRepository.findByUserType_NameAndActive("Labour", true);
        if (billBook.getDriver() != null) {
            labours.add(billBook.getDriver());
        }
        model.addAttribute("labours", labours);
        model.addAttribute("vehicles", (Object)this.vehicleRepo.findAll());
        model.addAttribute("products", (Object)this.productRepository.findAll());
        model.addAttribute("sites", (Object)this.siteRepository.findAll());
        model.addAttribute("sizes", (Object)this.sizeRepository.findAll());
        model.addAttribute("labourGroups", (Object)this.labourGroupRepository.findAll());
        model.addAttribute("finalBalance", (Object)finalBalance);
        model.addAttribute("prevBalance", (Object)prevBalance);
        return "billBookEdit";
    }

    @GetMapping(value={"/delete/{id}"})
    public String delete(@PathVariable(value="id") long id, RedirectAttributes redirectAttributes, HttpSession session) {
        int page = (Integer)session.getAttribute("page");
        this.billBookRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", (Object)"Bill Book deleted successfully");
        return "redirect:/bill-book/pageno=" + page;
    }

    @GetMapping(value={"/search"})
    public String searchGet(Model model) {
        model.addAttribute("billBookSearch", (Object)new BillBookSearch());
        this.fillModel(model);
        return "billBookSearch";
    }

    @GetMapping(value={"/customer/{id}"})
    @ResponseBody
    public Double getBalance(@PathVariable Long id) {
        return CommonMethods.getCustomerBalance(id, LocalDate.MIN, LocalDate.now(), this.billBookRepository, this.dayBookRepo, this.clearDuesRepo, this.goodsReturnRepository);
    }

    @GetMapping(value={"/customer/{id}/sites"})
    @ResponseBody
    public List<String> getSitesByCustomer(@PathVariable Long id) {
        return this.billBookRepository.findDistinctSitesByCustomerId(id);
    }

    @PostMapping(value={"/search"})
    public String searchPost(BillBookSearch billBookSearch, Model model, HttpSession session) {
        session.setAttribute("billBookSearch", (Object)billBookSearch);
        int page = 1;
        this.pagination(page, billBookSearch, model, session);
        return "billBookSearch";
    }

    @GetMapping(value={"/pageno={page}"})
    public String page(@PathVariable(value="page") int page, HttpSession session, Model model) {
        BillBookSearch billBookSearch = (BillBookSearch)session.getAttribute("billBookSearch");
        this.pagination(page, billBookSearch, model, session);
        return "billBookSearch";
    }

    @GetMapping(value={"/receipt/{number}"})
    public ResponseEntity<?> findBillsByReceiptNumber(@PathVariable String number) {
        List<BillBookDTO> matches = this.billBookRepository.findDTOsByReceiptNumber(number);
        return ResponseEntity.ok((Object)matches);
    }

    @GetMapping(value={"/receipt/search"})
    @ResponseBody
    public List<Map<String, Object>> searchReceiptNumbers(@RequestParam(value="term") String term) {
        return this.billBookRepository.searchDTOsByReceiptNumber(term).stream().map(b -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", b.getReceiptNumber());
            map.put("text", b.getReceiptNumber());
            map.put("customerName", b.getCustomerName());
            map.put("date", b.getDate() != null ? b.getDate().toString() : "");
            map.put("total", b.getTotal());
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping(value={"/print/{id}"})
    public void printBillBook(@PathVariable long id, @RequestParam(value="prevBalance") Double prevBalance, @RequestParam(value="finalBalance") Double finalBalance, HttpServletResponse response) {
        BillBook billBook = (BillBook)this.billBookRepository.findById(id).get();
        this.printBillBook(billBook, response, prevBalance, finalBalance);
    }

    public void pagination(int page, BillBookSearch billBookSearch, Model model, HttpSession session) {
        page = page > 0 ? page : 1;
        this.from = 20 * (page - 1);
        this.records = this.billBookRepository.count(billBookSearch);
        this.total = (int)Math.ceil((double)this.records.longValue() / 20.0);
        List billBooks = this.billBookRepository.searchPaginated(billBookSearch, this.from);
        model.addAttribute("totalPages", (Object)this.total);
        model.addAttribute("currentPage", (Object)page);
        session.setAttribute("page", (Object)page);
        model.addAttribute("billBooks", (Object)billBooks);
        model.addAttribute("billBookSearch", (Object)billBookSearch);
        System.out.println("total records: " + this.records + " total Pages: " + this.total + " Current page: " + page);
        this.fillModel(model);
    }

    private void fillModel(Model model) {
        String[] userTypes = new String[]{"Customer", "Contractor"};
        model.addAttribute("customers", this.appUserRepository.findByUserType_NameInAndActive(userTypes, true));
        model.addAttribute("vehicles", (Object)this.vehicleRepo.findAll());
        model.addAttribute("sites", (Object)this.siteRepository.findAll());
        model.addAttribute("labourGroups", (Object)this.labourGroupRepository.findAll());
    }

    void setLoadingAndUnloadingCharges(BillBook billBook) {
        AppUser driver;
        if (billBook.getLoaders().size() > 0) {
            Double loadingAmtPerHead;
            driver = null;
            Double loadingAmount = this.calculateLoadingAmount(billBook);
            if (billBook.getDriver() != null) {
                driver = billBook.getLoaders().stream().filter(l -> l.getId() == billBook.getDriver().getId()).findFirst().orElse(null);
            }
            if (driver == null) {
                loadingAmtPerHead = loadingAmount / (double)billBook.getLoaders().size();
                billBook.setLoadingAmountPerHead(loadingAmtPerHead);
            } else {
                loadingAmtPerHead = loadingAmount / (double)billBook.getLoaders().size();
                billBook.setDriverLoadingCharges(loadingAmtPerHead);
                billBook.setLoadingAmountPerHead(loadingAmtPerHead);
            }
        }
        if (billBook.getUnloaders().size() > 0) {
            driver = null;
            Double unloadingAmount = this.calculateUnloadingAmount(billBook);
            if (billBook.getDriver() != null) {
                driver = billBook.getUnloaders().stream().filter(l -> l.getId() == billBook.getDriver().getId()).findFirst().orElse(null);
            }
            if (driver == null) {
                Double unloadingAmtPerHead = unloadingAmount / (double)billBook.getUnloaders().size();
                billBook.setUnloadingAmountPerHead(unloadingAmtPerHead);
            } else if (billBook.getUnloaders().size() > 1) {
                Double driverUnloadingCharge = unloadingAmount * 0.4;
                billBook.setDriverUnloadingCharges(driverUnloadingCharge);
                Double labourUnloadingCharge = unloadingAmount * 0.6 / (double)(billBook.getUnloaders().size() - 1);
                billBook.setUnloadingAmountPerHead(labourUnloadingCharge);
            } else {
                billBook.setDriverUnloadingCharges(unloadingAmount);
                billBook.setUnloadingAmountPerHead(0.0);
            }
        }
    }

    public void printBillBook(BillBook billBook, HttpServletResponse response, Double prevBalance, Double finalBalance) {
        InputStream mainJasperStream = this.getClass().getResourceAsStream("/BillBook.jasper");
        InputStream subJasperStream = this.getClass().getResourceAsStream("/SalesDetail.jasper");
        try {
            JasperReport mainReport = (JasperReport)JRLoader.loadObject((InputStream)mainJasperStream);
            JasperReport salesReport = (JasperReport)JRLoader.loadObject((InputStream)subJasperStream);
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("sales", salesReport);
            params.put("prevBalance", prevBalance);
            params.put("finalBalance", finalBalance);
            JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(Arrays.asList(billBook));
            JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport)mainReport, params, (JRDataSource)data);
            response.setContentType("application/pdf");
            String fileName = billBook.getReceiptNumber() + "_" + LocalDate.now();
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".pdf");
            ServletOutputStream output = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream((JasperPrint)jasperPrint, (OutputStream)output);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] generatePdfBytes(BillBook billBook, Double prevBalance, Double finalBalance) {
        InputStream mainJasperStream = this.getClass().getResourceAsStream("/BillBook.jasper");
        InputStream subJasperStream = this.getClass().getResourceAsStream("/SalesDetail.jasper");
        try {
            JasperReport mainReport = (JasperReport)JRLoader.loadObject((InputStream)mainJasperStream);
            JasperReport salesReport = (JasperReport)JRLoader.loadObject((InputStream)subJasperStream);
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("sales", salesReport);
            params.put("prevBalance", prevBalance);
            params.put("finalBalance", finalBalance);
            JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(Arrays.asList(billBook));
            JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport)mainReport, params, (JRDataSource)data);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream((JasperPrint)jasperPrint, (OutputStream)baos);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public Double calculateLoadingAmount(BillBook billBook) {
        Double loadingAmount = 0.0;
        for (Sales s : billBook.getSales()) {
            LabourCostProj loadingRate = this.labourCostRepository.findByProduct_IdAndSize_IdAndLabourGroup_Id(s.getProduct().getId(), s.getSize().getId(), billBook.getLabourGroup().getId());
            loadingAmount = loadingAmount + loadingRate.getLoadingRate() * s.getQuantity();
        }
        return loadingAmount;
    }

    public Double calculateUnloadingAmount(BillBook billBook) {
        Double unloadingAmount = 0.0;
        for (Sales s : billBook.getSales()) {
            LabourCostProj unloadingRate = this.labourCostRepository.findByProduct_IdAndSize_IdAndLabourGroup_Id(s.getProduct().getId(), s.getSize().getId(), billBook.getLabourGroup().getId());
            unloadingAmount = unloadingAmount + unloadingRate.getUnloadingRate() * s.getQuantity();
        }
        return unloadingAmount;
    }
}
