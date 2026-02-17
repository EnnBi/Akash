package com.akash.controller;

import com.akash.entity.AppUser;
import com.akash.entity.AppUserSearch;
import com.akash.repository.AppUserRepository;
import com.akash.repository.LabourGroupRepository;
import com.akash.repository.SiteRepository;
import com.akash.repository.UserTypeRepository;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value={"/user"})
public class AppUserController {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    SiteRepository siteRepository;
    @Autowired
    UserTypeRepository userRepository;
    @Autowired
    LabourGroupRepository labourGroupRepository;
    int from = 0;
    int total = 0;
    int ROWS = 20;
    Long records = 0L;

    @GetMapping
    public String get(Model model) {
        this.fillModel(model);
        if (!model.asMap().containsKey("user")) {
            model.addAttribute("user", (Object)new AppUser());
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.user", model.asMap().get("result"));
        }
        return "appUser";
    }

    @PostMapping(value={"/save"})
    public String save(@Valid @ModelAttribute(value="user") AppUser appUser, BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("user", (Object)appUser);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter all the field correctly");
            return "redirect:/user";
        }
        if (this.appUserRepository.existsByContact(appUser.getContact())) {
            redirect.addFlashAttribute("user", (Object)appUser);
            redirect.addFlashAttribute("fail", (Object)"Person Already Exists");
            return "redirect:/user";
        }
        this.appUserRepository.save(appUser);
        redirect.addFlashAttribute("success", (Object)"Person Saved Successfully");
        return "redirect:/user";
    }

    @GetMapping(value={"/search"})
    public String list(Model model, HttpSession session) {
        this.fillModel(model);
        model.addAttribute("appUserSearch", (Object)new AppUserSearch());
        session.setAttribute("currentPage", (Object)1);
        return "userList";
    }

    @PostMapping(value={"/search"})
    public String searchOrders(AppUserSearch appUserSearch, Model model, HttpSession session) {
        int page = 1;
        session.setAttribute("appUserSearch", (Object)appUserSearch);
        this.pagination(page, appUserSearch, model, session);
        return "userList";
    }

    @GetMapping(value={"/edit/{id}"})
    public String update(@PathVariable(value="id") long id, Model model, HttpSession session) {
        this.fillModel(model);
        if (!model.asMap().containsKey("user")) {
            model.addAttribute("user", (Object)this.appUserRepository.findById(id));
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.user", model.asMap().get("result"));
        }
        model.addAttribute("edit", (Object)true);
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, new AppUserSearch(), model, session);
        return "appUser";
    }

    @PostMapping(value={"/update"})
    public String update(@Valid @ModelAttribute(value="user") AppUser appUser, BindingResult result, RedirectAttributes redirect, HttpSession session, Model model) {
        int page = (Integer)session.getAttribute("currentPage");
        if (result.hasErrors()) {
            redirect.addFlashAttribute("user", (Object)appUser);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/user/edit/" + appUser.getId();
        }
        if (this.appUserRepository.chechUserExistsAlready(appUser.getContact(), appUser.getId()) != null) {
            redirect.addFlashAttribute("user", (Object)appUser);
            redirect.addFlashAttribute("fail", (Object)"Person Alredy Exists");
            return "redirect:/user/edit/" + appUser.getId();
        }
        this.appUserRepository.save(appUser);
        redirect.addFlashAttribute("success", (Object)"Person Updated Successfully");
        return "redirect:/user/pageno=" + page;
    }

    @GetMapping(value={"/delete/{id}"})
    public String delete(@PathVariable(value="id") long id, HttpSession session, RedirectAttributes redirect) {
        int page = (Integer)session.getAttribute("currentPage");
        this.appUserRepository.deleteById(id);
        redirect.addFlashAttribute("success", (Object)"User Deleted Successfully");
        return "redirect:/user/pageno=" + page;
    }

    @GetMapping(value={"/customer"})
    public ResponseEntity<?> saveCustomer(@RequestParam(value="name") String name, @RequestParam(value="contact") String contact, @RequestParam(value="address") String address, @RequestParam(value="ledgerNumber") String ledgerNumber) {
        if (this.appUserRepository.existsByContact(contact)) {
            return ResponseEntity.badRequest().build();
        }
        AppUser appUser = new AppUser();
        appUser.setName(name);
        appUser.setContact(contact);
        appUser.setAddress(address);
        appUser.setLedgerNumber(ledgerNumber);
        appUser.setUserType(this.userRepository.findByName("Customer"));
        appUser.setActive(true);
        appUser = (AppUser)this.appUserRepository.save(appUser);
        return ResponseEntity.ok((Object)appUser);
    }

    private void fillModel(Model model) {
        model.addAttribute("UserList", (Object)this.userRepository.findAll());
        model.addAttribute("siteList", (Object)this.siteRepository.findAll());
        model.addAttribute("labourGroups", (Object)this.labourGroupRepository.findAll());
    }

    public void pagination(int page, AppUserSearch appUserSearch, Model model, HttpSession session) {
        page = page > 0 ? page : 1;
        this.from = this.ROWS * (page - 1);
        this.records = this.appUserRepository.searchAppUsersCount(appUserSearch);
        this.total = (int)Math.ceil((double)this.records.longValue() / (double)this.ROWS);
        List appUsers = this.appUserRepository.searchAppUserPaginated(appUserSearch, this.from);
        model.addAttribute("totalPages", (Object)this.total);
        session.setAttribute("currentPage", (Object)page);
        model.addAttribute("appUser", (Object)appUsers);
        model.addAttribute("appUserSearch", (Object)appUserSearch);
        System.out.println("total records: " + this.records + " total Pages: " + this.total + " Current page: " + page);
        this.fillModel(model);
    }

    @GetMapping(value={"/pageno={page}"})
    public String paginate(@PathVariable(value="page") int page, Model model, HttpSession session) {
        AppUserSearch appUserSearch = (AppUserSearch)session.getAttribute("appUserSearch");
        this.pagination(page, appUserSearch, model, session);
        return "userList";
    }

    @GetMapping(value={"/{id}/sites"})
    public ResponseEntity<?> findSitesOfUser(@PathVariable long id) {
        return ResponseEntity.ok(this.appUserRepository.findSitesOnUserId(id));
    }

    @GetMapping(value={"/labour-group/{id}"})
    public ResponseEntity<?> findLaboursOnLabourGroup(@PathVariable long id) {
        return ResponseEntity.ok(this.appUserRepository.findByUserType_NameAndLabourGroup_IdAndActive("Labour", id, true));
    }
}
