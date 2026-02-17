package com.akash.controller;

import com.akash.entity.UserType;
import com.akash.repository.AppUserRepository;
import com.akash.repository.UserTypeRepository;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value={"/user-type"})
public class UserTypeController {
    @Autowired
    UserTypeRepository userRepository;
    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping
    public String add(Model model, HttpSession session) {
        if (!model.asMap().containsKey("user")) {
            model.addAttribute("user", (Object)new UserType());
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.user", model.asMap().get("result"));
        }
        int page = 1;
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        return "userType";
    }

    @PostMapping(value={"/save"})
    public String save(@Valid @ModelAttribute(value="user") UserType userType, BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("user", (Object)userType);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/user-type";
        }
        if (this.userRepository.existsByName(userType.getName())) {
            redirect.addFlashAttribute("user", (Object)userType);
            redirect.addFlashAttribute("fail", (Object)"Person Type Already Exists");
            return "redirect:/user-type";
        }
        this.userRepository.save(userType);
        redirect.addFlashAttribute("success", (Object)"Person Type Saved Successfully");
        return "redirect:/user-type";
    }

    @GetMapping(value={"/edit/{id}"})
    public String update(@PathVariable(value="id") long id, Model model, HttpSession session) {
        if (!model.asMap().containsKey("user")) {
            model.addAttribute("user", (Object)this.userRepository.findById(id));
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.user", model.asMap().get("result"));
        }
        model.addAttribute("edit", (Object)true);
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        return "userType";
    }

    @PostMapping(value={"/update"})
    public String update(@Valid @ModelAttribute(value="user") UserType userType, BindingResult result, RedirectAttributes redirect, HttpSession session, Model model) {
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        if (result.hasErrors()) {
            redirect.addFlashAttribute("user", (Object)userType);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/user-type/edit/" + userType.getId();
        }
        if (this.userRepository.chechUserAlreadyExists(userType.getName(), userType.getId()) != null) {
            redirect.addFlashAttribute("user", (Object)userType);
            redirect.addFlashAttribute("fail", (Object)"Person Type Already Exists");
            return "redirect:/user-type/edit/" + userType.getId();
        }
        this.userRepository.save(userType);
        redirect.addFlashAttribute("success", (Object)"Person Type Updated Successfully");
        return "redirect:/user-type/pageno=" + page;
    }

    @GetMapping(value={"/delete/{id}"})
    public String delete(@PathVariable(value="id") long id, HttpSession session, RedirectAttributes redirect) {
        int page = (Integer)session.getAttribute("currentPage");
        try {
            this.userRepository.deleteById(id);
            redirect.addFlashAttribute("success", (Object)"Person Type Deleted Successfully");
        }
        catch (Exception e) {
            redirect.addFlashAttribute("fail", (Object)"Person Type cannot be deleted");
        }
        return "redirect:/user-type/pageno=" + page;
    }

    @GetMapping(value={"/pageno={page}"})
    public String paginate(@PathVariable(value="page") int page, Model model, HttpSession session) {
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        model.addAttribute("user", (Object)new UserType());
        return "userType";
    }

    @GetMapping(value={"/{name}/users"})
    public ResponseEntity<?> usersOnUserType(@PathVariable String name) {
        return ResponseEntity.ok(this.appUserRepository.findByUserType_NameAndActive(name, true));
    }

    public void pagination(int page, Model model) {
        page = page <= 1 ? 0 : page - 1;
        PageRequest pageable = PageRequest.of((int)page, (int)10);
        Page list = this.userRepository.findAll((Pageable)pageable);
        System.out.println(list.getContent());
        model.addAttribute("list", (Object)list.getContent());
        model.addAttribute("currentPage", (Object)(page + 1));
        model.addAttribute("totalPages", (Object)list.getTotalPages());
    }
}
