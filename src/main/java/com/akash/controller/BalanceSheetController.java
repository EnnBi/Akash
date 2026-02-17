package com.akash.controller;

import com.akash.entity.AppUser;
import com.akash.entity.BalanceSheet;
import com.akash.entity.StatementSearch;
import com.akash.repository.AppUserRepository;
import com.akash.repository.BillBookRepository;
import com.akash.repository.ClearDuesRepository;
import com.akash.repository.DayBookRepository;
import com.akash.repository.GoodsReturnRepository;
import com.akash.repository.ManufactureRepository;
import com.akash.repository.RawMaterialRepository;
import com.akash.repository.UserTypeRepository;
import com.akash.util.CommonMethods;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value={"/balanceSheet"})
public class BalanceSheetController {
    @Autowired
    UserTypeRepository userTypeRepository;
    @Autowired
    AppUserRepository appUserRepo;
    @Autowired
    BillBookRepository billBookRepository;
    @Autowired
    DayBookRepository dayBookRepo;
    @Autowired
    ClearDuesRepository clearDuesRepo;
    @Autowired
    GoodsReturnRepository goodsReturnRepository;
    @Autowired
    ManufactureRepository manufactureRepository;
    @Autowired
    RawMaterialRepository rawMaterialRepo;

    @GetMapping
    public String add(Model model, HttpSession session) {
        model.addAttribute("userTypes", (Object)this.userTypeRepository.findAll());
        model.addAttribute("balanceSheet", (Object)new StatementSearch());
        return "balancesheet";
    }

    @PostMapping(params={"view"})
    public String getBalanceSheet(@ModelAttribute(value="balanceSheet") StatementSearch search, Model model, HttpSession session) {
        model.addAttribute("userTypes", (Object)this.userTypeRepository.findAll());
        Map<String, Object> map = new HashMap<>();
        switch (search.getUserType()) {
            case "Customer": {
                map = this.getCustomerBalanceSheet(search.getStartDate(), search.getEndDate());
                break;
            }
            case "Contractor": {
                map = this.getCustomerBalanceSheet(search.getStartDate(), search.getEndDate());
                break;
            }
            case "Driver": {
                map = this.getDriverBalanceSheet(search.getStartDate(), search.getEndDate());
                break;
            }
            case "Dealer": {
                map = this.getDealerBalanceSheet(search.getStartDate(), search.getEndDate());
                break;
            }
            case "Labour": {
                map = this.getLabourBalanceSheet(search.getStartDate(), search.getEndDate());
                break;
            }
            case "Owner": {
                map = this.getOwnerBalanceSheet(search.getStartDate(), search.getEndDate());
                break;
            }
        }
        model.addAttribute("totalCredit", (Object)CommonMethods.format(map.get("totalCredit")));
        model.addAttribute("totalDebit", (Object)CommonMethods.format(map.get("totalDebit")));
        model.addAttribute("totalBalance", (Object)CommonMethods.format(map.get("totalBalance")));
        model.addAttribute("balanceSheets", map.get("balanceSheets"));
        return "balancesheet";
    }

    @PostMapping(params={"excel"})
    public void exportToExcelBalanceSheet(@ModelAttribute(value="balanceSheet") StatementSearch search, Model model, HttpServletResponse response, HttpSession session) {
        model.addAttribute("userTypes", (Object)this.userTypeRepository.findAll());
        Map<String, Object> map = new HashMap<String, Object>();
        switch (search.getUserType()) {
            case "Customer": {
                map = this.getCustomerBalanceSheet(search.getStartDate(), search.getEndDate());
                break;
            }
            case "Contractor": {
                map = this.getCustomerBalanceSheet(search.getStartDate(), search.getEndDate());
                break;
            }
            case "Driver": {
                map = this.getDriverBalanceSheet(search.getStartDate(), search.getEndDate());
                break;
            }
            case "Dealer": {
                map = this.getDealerBalanceSheet(search.getStartDate(), search.getEndDate());
                break;
            }
            case "Labour": {
                map = this.getLabourBalanceSheet(search.getStartDate(), search.getEndDate());
                break;
            }
            case "Owner": {
                map = this.getOwnerBalanceSheet(search.getStartDate(), search.getEndDate());
                break;
            }
        }
        this.generateStatementExcel(map, response, search.getUserType());
    }

    public Map<String, Object> getCustomerBalanceSheet(LocalDate startDate, LocalDate endDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String[] usertypes = new String[]{"Customer", "Contractor"};
        List<AppUser> users = this.appUserRepo.findAllAppUsersOnType(usertypes);
        Double totalBalance = 0.0;
        Double totalCredit = 0.0;
        Double totalDebit = 0.0;
        ArrayList<BalanceSheet> balanceSheets = new ArrayList<BalanceSheet>();
        for (AppUser appUser : users) {
            BalanceSheet balanceSheet = new BalanceSheet();
            balanceSheet.setUser(appUser);
            System.out.println("appuserName" + appUser.getName());
            Double credit = CommonMethods.getCustomerCredit(appUser.getId(), startDate, endDate, this.billBookRepository, this.dayBookRepo, this.goodsReturnRepository, this.clearDuesRepo);
            balanceSheet.setCredit(credit);
            totalCredit = totalCredit + credit;
            Double debit = CommonMethods.getCustomerDebit(appUser.getId(), startDate, endDate, this.dayBookRepo);
            balanceSheet.setDebit(debit);
            totalDebit = totalDebit + debit;
            Double balance = credit - debit;
            balanceSheet.setBalance(balance);
            totalBalance = totalBalance + balance;
            balanceSheets.add(balanceSheet);
        }
        System.out.println("balancesheetSizeee" + balanceSheets.size());
        map.put("totalCredit", totalCredit);
        map.put("totalDebit", totalDebit);
        map.put("totalBalance", totalBalance);
        map.put("balanceSheets", balanceSheets);
        return map;
    }

    public Map<String, Object> getDriverBalanceSheet(LocalDate startDate, LocalDate endDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String[] usertypes = new String[]{"Driver"};
        List<AppUser> users = this.appUserRepo.findAllAppUsersOnType(usertypes);
        Double totalBalance = 0.0;
        Double totalCredit = 0.0;
        Double totalDebit = 0.0;
        ArrayList<BalanceSheet> balanceSheets = new ArrayList<BalanceSheet>();
        for (AppUser appUser : users) {
            BalanceSheet balanceSheet = new BalanceSheet();
            balanceSheet.setUser(appUser);
            System.out.println("appuserName" + appUser.getName());
            Double credit = CommonMethods.getDriverCredit(appUser.getId(), startDate, endDate, this.dayBookRepo);
            balanceSheet.setCredit(credit);
            totalCredit = totalCredit + credit;
            Double debit = CommonMethods.getDriverDebit(appUser.getId(), startDate, endDate, this.dayBookRepo, this.billBookRepository, this.appUserRepo);
            balanceSheet.setDebit(debit);
            totalDebit = totalDebit + debit;
            Double balance = debit - credit;
            balanceSheet.setBalance(balance);
            totalBalance = totalBalance + balance;
            balanceSheets.add(balanceSheet);
        }
        System.out.println("balancesheetSizeee" + balanceSheets.size());
        map.put("totalCredit", totalCredit);
        map.put("totalDebit", totalDebit);
        map.put("totalBalance", totalBalance);
        map.put("balanceSheets", balanceSheets);
        return map;
    }

    public Map<String, Object> getOwnerBalanceSheet(LocalDate startDate, LocalDate endDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String[] usertypes = new String[]{"Owner"};
        List<AppUser> users = this.appUserRepo.findAllAppUsersOnType(usertypes);
        Double totalBalance = 0.0;
        Double totalCredit = 0.0;
        Double totalDebit = 0.0;
        ArrayList<BalanceSheet> balanceSheets = new ArrayList<BalanceSheet>();
        for (AppUser appUser : users) {
            BalanceSheet balanceSheet = new BalanceSheet();
            balanceSheet.setUser(appUser);
            System.out.println("appuserName" + appUser.getName());
            Double credit = CommonMethods.getOwnerCredit(appUser.getId(), startDate, endDate, this.dayBookRepo, this.appUserRepo);
            balanceSheet.setCredit(credit);
            totalCredit = totalCredit + credit;
            Double debit = CommonMethods.getOwnerDebit(appUser.getId(), startDate, endDate, this.dayBookRepo, this.appUserRepo);
            balanceSheet.setDebit(debit);
            totalDebit = totalDebit + debit;
            Double balance = debit - credit;
            balanceSheet.setBalance(balance);
            totalBalance = totalBalance + balance;
            balanceSheets.add(balanceSheet);
        }
        System.out.println("balancesheetSizeee" + balanceSheets.size());
        map.put("totalCredit", CommonMethods.format(totalCredit));
        map.put("totalDebit", CommonMethods.format(totalDebit));
        map.put("totalBalance", CommonMethods.format(totalBalance));
        map.put("balanceSheets", balanceSheets);
        return map;
    }

    public Map<String, Object> getLabourBalanceSheet(LocalDate startDate, LocalDate endDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String[] usertypes = new String[]{"Labour"};
        List<AppUser> users = this.appUserRepo.findAllAppUsersOnType(usertypes);
        Double totalBalance = 0.0;
        Double totalCredit = 0.0;
        Double totalDebit = 0.0;
        ArrayList<BalanceSheet> balanceSheets = new ArrayList<BalanceSheet>();
        for (AppUser appUser : users) {
            BalanceSheet balanceSheet = new BalanceSheet();
            balanceSheet.setUser(appUser);
            System.out.println("appuserName" + appUser.getName());
            Double credit = CommonMethods.getLabourCredit(appUser.getId(), startDate, endDate, this.dayBookRepo);
            balanceSheet.setCredit(credit);
            totalCredit = totalCredit + credit;
            Double debit = CommonMethods.getLabourDebit(appUser.getId(), startDate, endDate, this.dayBookRepo, this.appUserRepo, this.billBookRepository, this.manufactureRepository);
            balanceSheet.setDebit(debit);
            totalDebit = totalDebit + debit;
            Double balance = debit - credit;
            balanceSheet.setBalance(balance);
            totalBalance = totalBalance + balance;
            balanceSheets.add(balanceSheet);
        }
        map.put("totalCredit", totalCredit);
        map.put("totalDebit", totalDebit);
        map.put("totalBalance", totalBalance);
        map.put("balanceSheets", balanceSheets);
        return map;
    }

    public Map<String, Object> getDealerBalanceSheet(LocalDate startDate, LocalDate endDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String[] usertypes = new String[]{"Dealer"};
        List<AppUser> users = this.appUserRepo.findAllAppUsersOnType(usertypes);
        Double totalBalance = 0.0;
        Double totalCredit = 0.0;
        Double totalDebit = 0.0;
        ArrayList<BalanceSheet> balanceSheets = new ArrayList<BalanceSheet>();
        for (AppUser appUser : users) {
            BalanceSheet balanceSheet = new BalanceSheet();
            balanceSheet.setUser(appUser);
            System.out.println("appuserName" + appUser.getName());
            Double credit = CommonMethods.getDealerCredit(appUser.getId(), startDate, endDate, this.dayBookRepo);
            balanceSheet.setCredit(credit);
            totalCredit = totalCredit + credit;
            Double debit = CommonMethods.getDealerDebit(appUser.getId(), startDate, endDate, this.dayBookRepo, this.rawMaterialRepo);
            balanceSheet.setDebit(debit);
            totalDebit = totalDebit + debit;
            Double balance = debit - credit;
            balanceSheet.setBalance(balance);
            totalBalance = totalBalance + balance;
            balanceSheets.add(balanceSheet);
        }
        System.out.println("balancesheetSizeee" + balanceSheets.size());
        map.put("totalCredit", totalCredit);
        map.put("totalDebit", totalDebit);
        map.put("totalBalance", totalBalance);
        map.put("balanceSheets", balanceSheets);
        return map;
    }

    public void generateStatementExcel(Map<String, Object> map, HttpServletResponse response, String usertype) {
        InputStream mainJasperStream = this.getClass().getResourceAsStream("/BalanceSheet.jasper");
        try {
            JasperReport mainReport = (JasperReport)JRLoader.loadObject((InputStream)mainJasperStream);
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("totalBalance", map.get("totalBalance"));
            params.put("totalCredit", map.get("totalCredit"));
            params.put("totalDebit", map.get("totalDebit"));
            params.put("type", usertype);
            JRBeanCollectionDataSource data = new JRBeanCollectionDataSource((Collection)map.get("balanceSheets"));
            JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport)mainReport, params, (JRDataSource)data);
            response.setContentType("application/vnd.ms-excel");
            String fileName = usertype + " " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
            ServletOutputStream output = response.getOutputStream();
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, (Object)jasperPrint);
            exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, (Object)output);
            exporter.setParameter((JRExporterParameter)JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, (Object)Boolean.TRUE);
            exporter.setParameter((JRExporterParameter)JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, (Object)Boolean.FALSE);
            exporter.exportReport();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
