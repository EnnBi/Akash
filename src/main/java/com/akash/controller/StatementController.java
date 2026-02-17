package com.akash.controller;

import com.akash.entity.AppUser;
import com.akash.entity.BillBook;
import com.akash.entity.CustomerStatement;
import com.akash.entity.DealerStatement;
import com.akash.entity.DriverStatement;
import com.akash.entity.LabourStatement;
import com.akash.entity.OwnerStatement;
import com.akash.entity.StatementSearch;
import com.akash.repository.AppUserRepository;
import com.akash.repository.BillBookRepository;
import com.akash.repository.ClearDuesRepository;
import com.akash.repository.DayBookRepository;
import com.akash.repository.GoodsReturnRepository;
import com.akash.repository.ManufactureRepository;
import com.akash.repository.RawMaterialRepository;
import com.akash.repository.UserTypeRepository;
import com.akash.util.BillBookToCustomerStatement;
import com.akash.util.CommonMethods;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value={"/statement"})
public class StatementController {
    @Autowired
    BillBookRepository billBookRepo;
    @Autowired
    DayBookRepository dayBookRepo;
    @Autowired
    ManufactureRepository manufactureRepo;
    @Autowired
    RawMaterialRepository rawMaterialRepo;
    @Autowired
    UserTypeRepository userTypeRepo;
    @Autowired
    AppUserRepository appUserRepo;
    @Autowired
    ClearDuesRepository clearDuesRepository;
    @Autowired
    GoodsReturnRepository goodsReturnRepository;
    Double prevBalance = 0.0;
    Double totalCredit = 0.0;
    Double totalDebit = 0.0;
    String prevDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    DecimalFormat df = new DecimalFormat("#.##");

    @RequestMapping(method={RequestMethod.GET})
    public String statement(Model model) {
        this.fillModel(model, new StatementSearch());
        model.addAttribute("statementSearch", (Object)new StatementSearch());
        return "statement";
    }

    @RequestMapping(method={RequestMethod.POST}, params={"view"})
    public String statementShow(StatementSearch statementSearch, Model model) {
        this.prevBalance = 0.0;
        this.totalCredit = 0.0;
        this.totalDebit = 0.0;
        switch (statementSearch.getUserType()) {
            case "Driver": {
                model.addAttribute("statements", this.generateDriverStatement(statementSearch, model));
                model.addAttribute("driverStatement", (Object)true);
                break;
            }
            case "Dealer": {
                model.addAttribute("statements", this.generateDealerStatement(statementSearch, model));
                model.addAttribute("dealerStatement", (Object)true);
                break;
            }
            case "Labour": {
                model.addAttribute("statements", this.generateLabourStatement(statementSearch, model));
                model.addAttribute("labourStatement", (Object)true);
                break;
            }
            case "Owner": {
                model.addAttribute("statements", this.generateOwnerStatement(statementSearch, model));
                model.addAttribute("ownerStatement", (Object)true);
                break;
            }
            case "Customer": {
                model.addAttribute("statements", this.generateCustomerStatement(statementSearch, model));
                model.addAttribute("customerStatement", (Object)true);
                break;
            }
            case "Contractor": {
                model.addAttribute("statements", this.generateCustomerStatement(statementSearch, model));
                model.addAttribute("customerStatement", (Object)true);
                break;
            }
        }
        this.fillModel(model, statementSearch);
        model.addAttribute("date", (Object)LocalDate.now());
        model.addAttribute("user", this.appUserRepo.findById(statementSearch.getUser()).orElse(null));
        model.addAttribute("statementSearch", (Object)statementSearch);
        model.addAttribute("previousBalance", (Object)CommonMethods.format(this.prevBalance));
        model.addAttribute("totalCredit", (Object)CommonMethods.format(this.totalCredit));
        model.addAttribute("totalDebit", (Object)CommonMethods.format(this.totalDebit));
        model.addAttribute("prevDate", (Object)statementSearch.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        return "statement";
    }

    @RequestMapping(method={RequestMethod.POST}, params={"excel"})
    public void statementPrintExcel(StatementSearch statementSearch, Model model, HttpServletResponse response) {
        AppUser user = this.appUserRepo.findById(statementSearch.getUser()).orElse(null);
        this.prevBalance = 0.0;
        this.totalCredit = 0.0;
        this.totalDebit = 0.0;
        this.prevDate = statementSearch.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        switch (statementSearch.getUserType()) {
            case "Driver": {
                List<DriverStatement> driverStatements = this.generateDriverStatement(statementSearch, model);
                this.generateDriverStatementExcel(driverStatements, response, user);
                break;
            }
            case "Dealer": {
                List<DealerStatement> dealerStatements = this.generateDealerStatement(statementSearch, model);
                this.generateDealerStatementExcel(dealerStatements, response, user);
                break;
            }
            case "Labour": {
                List<LabourStatement> labourStatements = this.generateLabourStatement(statementSearch, model);
                this.generateLabourStatementExcel(labourStatements, response, user);
                break;
            }
            case "Owner": {
                List<OwnerStatement> ownerStatements = this.generateOwnerStatement(statementSearch, model);
                this.generateOwnerStatementExcel(ownerStatements, response, user);
                break;
            }
            case "Customer": {
                List<CustomerStatement> customerStatements = this.generateCustomerStatement(statementSearch, model);
                this.generateCustomerStatementExcel(customerStatements, response, user);
                break;
            }
            case "Contractor": {
                List<CustomerStatement> contracterStatements = this.generateCustomerStatement(statementSearch, model);
                this.generateCustomerStatementExcel(contracterStatements, response, user);
                break;
            }
        }
    }

    public void fillModel(Model model, StatementSearch statementSearch) {
        model.addAttribute("userTypes", (Object)this.userTypeRepo.findAll());
        model.addAttribute("users", this.appUserRepo.findByUserType_NameAndActive(statementSearch.getUserType(), true));
    }

    public List<DriverStatement> generateDriverStatement(StatementSearch statementSearch, Model model) {
        Double previousBalance;
        LocalDate previousDay = statementSearch.getStartDate().minusDays(1L);
        AppUser driver = this.appUserRepo.findById(statementSearch.getUser()).orElse(null);
        Double balance = previousBalance = Double.valueOf(CommonMethods.getDriverCredit(statementSearch.getUser(), LocalDate.MIN, previousDay, this.dayBookRepo) - CommonMethods.getDriverDebit(statementSearch.getUser(), LocalDate.MIN, previousDay, this.dayBookRepo, this.billBookRepo, this.appUserRepo));
        List<DriverStatement> billBookEntries = this.billBookRepo.findDriverDebits(driver, statementSearch.getStartDate(), statementSearch.getEndDate());
        List<DriverStatement> dayBookDebitEntries = this.dayBookRepo.findDriverDebitsBetweenDates(statementSearch.getUser(), statementSearch.getStartDate(), statementSearch.getEndDate());
        List<DriverStatement> dayBookCreditEntries = this.dayBookRepo.findDriverCreditsBetweenDates(statementSearch.getUser(), statementSearch.getStartDate(), statementSearch.getEndDate());
        ArrayList<DriverStatement> statements = new ArrayList<DriverStatement>();
        statements.addAll(billBookEntries);
        statements.addAll(dayBookCreditEntries);
        statements.addAll(dayBookDebitEntries);
        statements.sort(Comparator.comparing(DriverStatement::getDate));
        for (DriverStatement s : statements) {
            if ("BillBook".equals(s.getType())) {
                s.setBalance(balance + s.getDebit());
                balance = balance + s.getDebit();
                this.totalDebit = this.totalDebit + s.getDebit();
                continue;
            }
            if (s.getCredit() != null) {
                s.setBalance(balance - s.getCredit());
                balance = balance - s.getCredit();
                this.totalCredit = this.totalCredit + s.getCredit();
                continue;
            }
            s.setBalance(balance + s.getDebit());
            balance = balance + s.getDebit();
            this.totalDebit = this.totalDebit + s.getDebit();
        }
        return statements;
    }

    public List<DealerStatement> generateDealerStatement(StatementSearch statementSearch, Model model) {
        Double previousBalance;
        LocalDate previousDay = statementSearch.getStartDate().minusDays(1L);
        Double balance = previousBalance = Double.valueOf(CommonMethods.getDealerCredit(statementSearch.getUser(), LocalDate.MIN, previousDay, this.dayBookRepo) - CommonMethods.getDealerDebit(statementSearch.getUser(), LocalDate.MIN, previousDay, this.dayBookRepo, this.rawMaterialRepo));
        this.prevBalance = Double.valueOf(this.df.format(previousBalance));
        List<DealerStatement> rawMaterialEntries = this.rawMaterialRepo.findDealerDebits(statementSearch.getUser(), statementSearch.getStartDate(), statementSearch.getEndDate());
        List<DealerStatement> dayDebitEntries = this.dayBookRepo.findDealerDebitsBetweenDates(statementSearch.getUser(), statementSearch.getStartDate(), statementSearch.getEndDate());
        List<DealerStatement> dayCreditEntries = this.dayBookRepo.findDealerCreditsBetweenDates(statementSearch.getUser(), statementSearch.getStartDate(), statementSearch.getEndDate());
        ArrayList<DealerStatement> statements = new ArrayList<DealerStatement>();
        statements.addAll(rawMaterialEntries);
        statements.addAll(dayCreditEntries);
        statements.addAll(dayDebitEntries);
        Collections.sort(statements, (a, b) -> a.getLocalDate().compareTo(b.getLocalDate()));
        for (DealerStatement s : statements) {
            if ("RawMaterial".equals(s.getType())) {
                s.setBalance(balance + s.getDebit());
                balance = balance + s.getDebit();
                this.totalDebit = this.totalDebit + s.getDebit();
                continue;
            }
            if (s.getCredit() != null) {
                s.setBalance(balance - s.getCredit());
                balance = balance - s.getCredit();
                this.totalCredit = this.totalCredit + s.getCredit();
                continue;
            }
            s.setBalance(balance + s.getDebit());
            balance = balance + s.getDebit();
            this.totalDebit = this.totalDebit + s.getDebit();
        }
        return statements;
    }

    public List<LabourStatement> generateLabourStatement(StatementSearch search, Model model) {
        Double previousBalance;
        LocalDate previousDay = search.getStartDate().minusDays(1L);
        AppUser labour = this.appUserRepo.findById(search.getUser()).orElse(null);
        Double balance = previousBalance = Double.valueOf(CommonMethods.getLabourCredit(search.getUser(), LocalDate.MIN, previousDay, this.dayBookRepo) - CommonMethods.getLabourDebit(search.getUser(), LocalDate.MIN, previousDay, this.dayBookRepo, this.appUserRepo, this.billBookRepo, this.manufactureRepo));
        this.prevBalance = Double.valueOf(this.df.format(previousBalance));
        List<LabourStatement> billBookEntriesBoth = this.billBookRepo.findLabourBillBookDebitsBoth(labour, search.getStartDate(), search.getEndDate());
        List<LabourStatement> billBookEntriesLoading = this.billBookRepo.findLabourBillBookDebitsLoading(labour, search.getStartDate(), search.getEndDate());
        List<LabourStatement> billBookEntriesUnloading = this.billBookRepo.findLabourBillBookDebitsUnloading(labour, search.getStartDate(), search.getEndDate());
        List<LabourStatement> manufactureEntries = this.manufactureRepo.findLabourDebits(labour, search.getStartDate(), search.getEndDate());
        List<LabourStatement> dayDebitEntries = this.dayBookRepo.findLabourStatementDebitsBetweenDates(search.getUser(), search.getStartDate(), search.getEndDate());
        List<LabourStatement> dayCreditEntries = this.dayBookRepo.findLabourStatementCreditsBetweenDates(search.getUser(), search.getStartDate(), search.getEndDate());
        ArrayList<LabourStatement> statements = new ArrayList<LabourStatement>();
        statements.addAll(billBookEntriesBoth);
        statements.addAll(billBookEntriesLoading);
        statements.addAll(billBookEntriesUnloading);
        statements.addAll(manufactureEntries);
        statements.addAll(dayDebitEntries);
        statements.addAll(dayCreditEntries);
        Collections.sort(statements, (a, b) -> a.getLocalDate().compareTo(b.getLocalDate()));
        for (LabourStatement s : statements) {
            if ("Manufactre".equals(s.getType())) {
                s.setBalance(balance + s.getDebit());
                balance = balance + s.getDebit();
                this.totalDebit = this.totalDebit + s.getDebit();
                continue;
            }
            if ("BillBook".equals(s.getType())) {
                s.setBalance(balance + s.getDebit());
                balance = balance + s.getDebit();
                this.totalDebit = this.totalDebit + s.getDebit();
                continue;
            }
            if (s.getCredit() != null) {
                s.setBalance(balance - s.getCredit());
                balance = balance - s.getCredit();
                this.totalCredit = this.totalCredit + s.getCredit();
                continue;
            }
            s.setBalance(balance + s.getDebit());
            balance = balance + s.getDebit();
            this.totalDebit = this.totalDebit + s.getDebit();
        }
        return statements;
    }

    public List<OwnerStatement> generateOwnerStatement(StatementSearch search, Model model) {
        Double prevBalance;
        LocalDate previousDay = search.getStartDate().minusDays(1L);
        AppUser owner = this.appUserRepo.findById(search.getUser()).orElse(null);
        Double balance = prevBalance = Double.valueOf(CommonMethods.getOwnerCredit(search.getUser(), LocalDate.MIN, previousDay, this.dayBookRepo, this.appUserRepo) - CommonMethods.getOwnerDebit(search.getUser(), LocalDate.MIN, previousDay, this.dayBookRepo, this.appUserRepo));
        List<OwnerStatement> statements = this.dayBookRepo.findByAccountNumberAndDateBetween(owner.getAccountNumber(), search.getStartDate(), search.getEndDate());
        Collections.sort(statements, (a, b) -> a.getLocalDate().compareTo(b.getLocalDate()));
        for (OwnerStatement s : statements) {
            if ("Expenditure".equals(s.getTransactionType())) {
                s.setBalance(balance + s.getAmount());
                s.setDebit(s.getAmount());
                balance = balance + s.getAmount();
                this.totalDebit = this.totalDebit + s.getDebit();
                continue;
            }
            s.setBalance(balance - s.getAmount());
            s.setCredit(s.getAmount());
            balance = balance - s.getAmount();
            this.totalCredit = this.totalCredit + s.getCredit();
        }
        return statements;
    }

    public List<CustomerStatement> generateCustomerStatement(StatementSearch search, Model model) {
        Double balance;
        LocalDate previousDay = search.getStartDate().minusDays(1L);
        this.prevBalance = balance = Double.valueOf(CommonMethods.getCustomerCredit(search.getUser(), LocalDate.MIN, previousDay, this.billBookRepo, this.dayBookRepo, this.goodsReturnRepository, this.clearDuesRepository) - CommonMethods.getCustomerDebit(search.getUser(), LocalDate.MIN, previousDay, this.dayBookRepo));
        List<BillBook> customerBillBook = this.billBookRepo.findByCustomer_IdAndDateBetween(search.getUser(), search.getStartDate(), search.getEndDate());
        List<CustomerStatement> billBookCreditEntries = BillBookToCustomerStatement.convert(customerBillBook);
        billBookCreditEntries.forEach(b -> b.setSales(this.billBookRepo.findSalesOnBillBookId(b.getId())));
        List<CustomerStatement> dayBookCreditEntries = this.dayBookRepo.findCustomerCreditsBetweenDates(search.getUser(), search.getStartDate(), search.getEndDate());
        List<CustomerStatement> dayBookDebitEntries = this.dayBookRepo.findCustomerDebitsBetweenDates(search.getUser(), search.getStartDate(), search.getEndDate());
        List<CustomerStatement> clearDuesEntries = this.clearDuesRepository.findCustomerClearDuesBetweenDates(search.getUser(), search.getStartDate(), search.getEndDate());
        List<CustomerStatement> goodsReturnEntries = this.goodsReturnRepository.findCustomerGoodsReturnBetweenDates(search.getUser(), search.getStartDate(), search.getEndDate());
        ArrayList<CustomerStatement> statements = new ArrayList<CustomerStatement>();
        statements.addAll(billBookCreditEntries);
        statements.addAll(dayBookCreditEntries);
        statements.addAll(dayBookDebitEntries);
        statements.addAll(clearDuesEntries);
        statements.addAll(goodsReturnEntries);
        Collections.sort(statements, (a, b) -> a.getLocalDate().compareTo(b.getLocalDate()));
        for (CustomerStatement s : statements) {
            if ("BillBook".equals(s.getType())) {
                s.setBalance(balance + s.getCredit());
                balance = balance + s.getCredit();
                this.totalCredit = this.totalCredit + s.getCredit();
                continue;
            }
            if ("Dues".equals(s.getType())) {
                s.setBalance(balance - s.getDebit());
                balance = balance - s.getDebit();
                this.totalDebit = this.totalDebit + s.getDebit();
                continue;
            }
            if ("RG".equals(s.getType())) {
                s.setBalance(balance - s.getDebit());
                balance = balance - s.getDebit();
                this.totalDebit = this.totalDebit + s.getDebit();
                continue;
            }
            if (s.getDebit() != null) {
                s.setBalance(balance - s.getDebit());
                balance = balance - s.getDebit();
                this.totalDebit = this.totalDebit + s.getDebit();
                continue;
            }
            s.setBalance(balance + s.getCredit());
            balance = balance + s.getCredit();
            this.totalCredit = this.totalCredit + s.getCredit();
        }
        return statements;
    }

    public void generateCustomerStatementExcel(List<CustomerStatement> statements, HttpServletResponse response, AppUser user) {
        InputStream mainJasperStream = this.getClass().getResourceAsStream("/Customer.jasper");
        InputStream subJasperStream = this.getClass().getResourceAsStream("/Sales.jasper");
        try {
            JasperReport mainReport = (JasperReport)JRLoader.loadObject((InputStream)mainJasperStream);
            JasperReport salesReport = (JasperReport)JRLoader.loadObject((InputStream)subJasperStream);
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("salesReport", salesReport);
            params.put("name", user.getLabelName());
            params.put("contact", user.getContact());
            params.put("date", LocalDate.now());
            params.put("prevDate", this.prevDate);
            params.put("prevBalance", this.prevBalance);
            JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(statements);
            JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport)mainReport, params, (JRDataSource)data);
            response.setContentType("application/vnd.ms-excel");
            String fileName = user.getLabelName() + " " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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

    public void generateDealerStatementExcel(List<DealerStatement> statements, HttpServletResponse response, AppUser user) {
        InputStream mainJasperStream = this.getClass().getResourceAsStream("/Dealer.jasper");
        try {
            JasperReport mainReport = (JasperReport)JRLoader.loadObject((InputStream)mainJasperStream);
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("name", user.getLabelName());
            params.put("contact", user.getContact());
            params.put("date", LocalDate.now());
            params.put("prevBalance", this.prevBalance);
            params.put("prevDate", this.prevDate);
            JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(statements);
            JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport)mainReport, params, (JRDataSource)data);
            response.setContentType("application/vnd.ms-excel");
            String fileName = user.getLabelName() + " " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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

    public void generateDriverStatementExcel(List<DriverStatement> statements, HttpServletResponse response, AppUser user) {
        InputStream mainJasperStream = this.getClass().getResourceAsStream("/Driver.jasper");
        try {
            JasperReport mainReport = (JasperReport)JRLoader.loadObject((InputStream)mainJasperStream);
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("name", user.getLabelName());
            params.put("contact", user.getContact());
            params.put("date", LocalDate.now());
            params.put("prevBalance", this.prevBalance);
            params.put("prevDate", this.prevDate);
            JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(statements);
            JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport)mainReport, params, (JRDataSource)data);
            response.setContentType("application/vnd.ms-excel");
            String fileName = user.getLabelName() + " " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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

    public void generateOwnerStatementExcel(List<OwnerStatement> statements, HttpServletResponse response, AppUser user) {
        InputStream mainJasperStream = this.getClass().getResourceAsStream("/Owner.jasper");
        try {
            JasperReport mainReport = (JasperReport)JRLoader.loadObject((InputStream)mainJasperStream);
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("name", user.getLabelName());
            params.put("contact", user.getContact());
            params.put("date", LocalDate.now());
            params.put("prevBalance", this.prevBalance);
            params.put("prevDate", this.prevDate);
            JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(statements);
            JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport)mainReport, params, (JRDataSource)data);
            response.setContentType("application/vnd.ms-excel");
            String fileName = user.getLabelName() + " " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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

    public void generateLabourStatementExcel(List<LabourStatement> statements, HttpServletResponse response, AppUser user) {
        InputStream mainJasperStream = this.getClass().getResourceAsStream("/Labour.jasper");
        try {
            JasperReport mainReport = (JasperReport)JRLoader.loadObject((InputStream)mainJasperStream);
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("name", user.getLabelName());
            params.put("contact", user.getContact());
            params.put("date", LocalDate.now());
            params.put("prevBalance", this.prevBalance);
            params.put("prevDate", this.prevDate);
            JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(statements);
            JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport)mainReport, params, (JRDataSource)data);
            response.setContentType("application/vnd.ms-excel");
            String fileName = user.getLabelName() + " " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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
