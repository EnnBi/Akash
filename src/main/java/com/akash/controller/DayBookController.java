package com.akash.controller;

import com.akash.entity.DayBook;
import com.akash.entity.DayBookSearch;
import com.akash.repository.AppUserRepository;
import com.akash.repository.DayBookRepository;
import com.akash.repository.UserTypeRepository;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value={"/day-book"})
public class DayBookController {
    @Autowired
    DayBookRepository daybookRepository;
    @Autowired
    UserTypeRepository userTypeRepository;
    @Autowired
    AppUserRepository appUserRepository;
    int from = 0;
    int total = 0;
    Long records = 0L;

    @GetMapping
    public String add(Model model) {
        model.addAttribute("dayBook", (Object)new DayBook());
        model.addAttribute("userTypes", (Object)this.userTypeRepository.findAll());
        model.addAttribute("accounts", this.appUserRepository.findByUserType_NameAndActive("Owner", true));
        return "daybook";
    }

    @PostMapping(value={"/save"})
    public String save(@ModelAttribute(value="dayBook") DayBook dayBook, Model model, RedirectAttributes redirectAttributes) {
        DayBook prevDayBook;
        if (dayBook.getTransactionType().equals("Expenditure") && dayBook.getTransactionBy().equals("Cheque") && (prevDayBook = this.daybookRepository.findByTransactionNumber(dayBook.getTransactionNumber())) != null) {
            prevDayBook.setStatus("Success");
            this.daybookRepository.save(prevDayBook);
        }
        this.daybookRepository.save(dayBook);
        redirectAttributes.addFlashAttribute("success", (Object)"Entry saved successfully");
        return "redirect:/day-book";
    }

    @GetMapping(value={"/edit/{id}"})
    public String update(@PathVariable(value="id") long id, Model model) {
        model.addAttribute("dayBook", this.daybookRepository.findById(id).orElse(null));
        this.fillModel(model);
        return "daybook";
    }

    @GetMapping(value={"/delete/{id}"})
    public String delete(@PathVariable(value="id") long id, RedirectAttributes redirectAttributes, HttpSession session) {
        int page = (Integer)session.getAttribute("page");
        this.daybookRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", (Object)"Entry deleted successfully");
        return "redirect:/day-book/pageno=" + page;
    }

    @GetMapping(value={"/search"})
    public String searchGet(Model model) {
        model.addAttribute("dayBookSearch", (Object)new DayBookSearch());
        this.fillModel(model);
        return "daybookSearch";
    }

    @PostMapping(value={"/search"})
    public String searchPost(@ModelAttribute(value="dayBookSearch") DayBookSearch dayBookSearch, Model model, HttpSession session) {
        session.setAttribute("dayBookSearch", (Object)dayBookSearch);
        int page = 1;
        this.fillModel(model);
        this.pagination(page, dayBookSearch, model, session);
        return "daybookSearch";
    }

    @GetMapping(value={"/pageno={page}"})
    public String page(@PathVariable(value="page") int page, HttpSession session, Model model) {
        DayBookSearch dayBookSearch = (DayBookSearch)session.getAttribute("dayBookSearch");
        this.pagination(page, dayBookSearch, model, session);
        return "daybookSearch";
    }

    public void pagination(int page, DayBookSearch dayBookSearch, Model model, HttpSession session) {
        page = page > 0 ? page : 1;
        this.from = 20 * (page - 1);
        this.records = this.daybookRepository.count(dayBookSearch);
        this.total = (int)Math.ceil((double)this.records.longValue() / 20.0);
        List dayBooks = this.daybookRepository.searchPaginated(dayBookSearch, this.from);
        model.addAttribute("totalPages", (Object)this.total);
        model.addAttribute("currentPage", (Object)page);
        session.setAttribute("page", (Object)page);
        model.addAttribute("dayBooks", (Object)dayBooks);
        model.addAttribute("dayBookSearch", (Object)dayBookSearch);
        this.fillModel(model);
    }

    private void fillModel(Model model) {
        model.addAttribute("userTypes", (Object)this.userTypeRepository.findAll());
        model.addAttribute("accounts", this.appUserRepository.findByUserType_NameAndActive("Owner", true));
        model.addAttribute("customers", (Object)this.appUserRepository.findAll());
    }
}
