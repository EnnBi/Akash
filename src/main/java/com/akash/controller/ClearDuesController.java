package com.akash.controller;

import com.akash.entity.ClearDues;
import com.akash.repository.AppUserRepository;
import com.akash.repository.BillBookRepository;
import com.akash.repository.ClearDuesRepository;
import com.akash.repository.DayBookRepository;
import com.akash.repository.GoodsReturnRepository;
import com.akash.repository.UserTypeRepository;
import com.akash.util.CommonMethods;
import java.time.LocalDate;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value={"/clearDues"})
public class ClearDuesController {
    @Autowired
    UserTypeRepository userTypeRepository;
    @Autowired
    ClearDuesRepository clearDuesRepository;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    BillBookRepository billBookRepo;
    @Autowired
    DayBookRepository daybookRepo;
    @Autowired
    GoodsReturnRepository goodsReturnRepo;

    @GetMapping
    public String add(Model model, HttpSession session) {
        model.addAttribute("clearDues", (Object)new ClearDues());
        int page = 1;
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        return "cleardues";
    }

    @PostMapping(value={"/save"})
    public String save(@ModelAttribute(value="clearDues") ClearDues clearDues, Model model, RedirectAttributes redirectAttributes) {
        clearDues.setDate(LocalDate.now());
        this.clearDuesRepository.save(clearDues);
        redirectAttributes.addFlashAttribute("success", (Object)"ClearDues saved successfully");
        return "redirect:/clearDues";
    }

    @GetMapping(value={"/edit/{id}"})
    public String update(@PathVariable(value="id") long id, Model model, HttpSession session) {
        ClearDues clearDues = (ClearDues)this.clearDuesRepository.findById(id).get();
        model.addAttribute("clearDues", (Object)clearDues);
        model.addAttribute("edit", (Object)true);
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        model.addAttribute("balance", (Object)CommonMethods.getCustomerBalance(clearDues.getUser().getId(), LocalDate.MIN, LocalDate.now(), this.billBookRepo, this.daybookRepo, this.clearDuesRepository, this.goodsReturnRepo));
        model.addAttribute("customers", this.appUserRepository.findByUserType_IdAndActive(clearDues.getUser().getUserType().getId(), true));
        return "cleardues";
    }

    @PostMapping(value={"/update"})
    public String update(@ModelAttribute(value="clearDues") ClearDues clearDues, Model model, RedirectAttributes redirectAttributes) {
        this.clearDuesRepository.save(clearDues);
        redirectAttributes.addFlashAttribute("success", (Object)"ClearDues Updated successfully");
        return "redirect:/clearDues";
    }

    @GetMapping(value={"/delete/{id}"})
    public String delete(@PathVariable(value="id") long id, HttpSession session, RedirectAttributes redirect) {
        int page = (Integer)session.getAttribute("currentPage");
        try {
            this.clearDuesRepository.deleteById(id);
            redirect.addFlashAttribute("success", (Object)"ClearDues Deleted Successfully");
        }
        catch (Exception e) {
            redirect.addFlashAttribute("fail", (Object)"ClearDues cannot be deleted");
        }
        return "redirect:/clearDues/pageno=" + page;
    }

    public void pagination(int page, Model model) {
        page = page <= 1 ? 0 : page - 1;
        PageRequest pageable = PageRequest.of((int)page, (int)10);
        Page list = this.clearDuesRepository.findAll((Pageable)pageable);
        System.out.println(list.getContent());
        model.addAttribute("list", (Object)list.getContent());
        model.addAttribute("currentPage", (Object)(page + 1));
        model.addAttribute("totalPages", (Object)list.getTotalPages());
        model.addAttribute("userTypes", (Object)this.userTypeRepository.findAll());
        model.addAttribute("owner", this.appUserRepository.findByUserType_NameAndActive("Owner", true));
    }

    @GetMapping(value={"/pageno={page}"})
    public String paginate(@PathVariable(value="page") int page, Model model, HttpSession session) {
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        model.addAttribute("clearDues", (Object)new ClearDues());
        return "cleardues";
    }
}
