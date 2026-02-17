package com.akash.controller;

import com.akash.entity.Site;
import com.akash.repository.SiteRepository;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(value={"/site"})
public class SiteController {
    @Autowired
    SiteRepository siteRepository;

    @GetMapping
    public String add(Model model, HttpSession session) {
        if (!model.asMap().containsKey("site")) {
            model.addAttribute("site", (Object)new Site());
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.site", model.asMap().get("result"));
        }
        int page = 1;
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        return "site";
    }

    @PostMapping(value={"/save"})
    public String save(@Valid @ModelAttribute(value="site") Site site, BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("site", (Object)site);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/site";
        }
        if (this.siteRepository.existsByName(site.getName())) {
            redirect.addFlashAttribute("site", (Object)site);
            redirect.addFlashAttribute("fail", (Object)"Site Already Exixts");
            return "redirect:/site";
        }
        this.siteRepository.save(site);
        redirect.addFlashAttribute("success", (Object)"Site Saved Successfully");
        return "redirect:/site";
    }

    @GetMapping(value={"/edit/{id}"})
    public String update(@PathVariable(value="id") long id, Model model, HttpSession session) {
        if (!model.asMap().containsKey("site")) {
            model.addAttribute("site", (Object)this.siteRepository.findById(id));
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.site", model.asMap().get("result"));
        }
        model.addAttribute("edit", (Object)true);
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        return "site";
    }

    @PostMapping(value={"/update"})
    public String update(@Valid @ModelAttribute(value="site") Site site, BindingResult result, RedirectAttributes redirect, HttpSession session, Model model) {
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        if (result.hasErrors()) {
            redirect.addFlashAttribute("site", (Object)site);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/site/edit/" + site.getId();
        }
        if (this.siteRepository.checkSiteAlreadyExists(site.getName(), site.getId()) != null) {
            redirect.addFlashAttribute("site", (Object)site);
            redirect.addFlashAttribute("fail", (Object)"Site Already Exists");
            return "redirect:/site/edit/" + site.getId();
        }
        this.siteRepository.save(site);
        redirect.addFlashAttribute("success", (Object)"Site Updated Successfully");
        return "redirect:/site/pageno=" + page;
    }

    @GetMapping(value={"/delete/{id}"})
    public String delete(@PathVariable(value="id") long id, HttpSession session, RedirectAttributes redirect) {
        int page = (Integer)session.getAttribute("currentPage");
        this.siteRepository.deleteById(id);
        redirect.addFlashAttribute("success", (Object)"Site Deleted Successfully");
        return "redirect:/site/pageno=" + page;
    }

    @GetMapping(value={"/pageno={page}"})
    public String paginate(@PathVariable(value="page") int page, Model model, HttpSession session) {
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        model.addAttribute("site", (Object)new Site());
        return "site";
    }

    public void pagination(int page, Model model) {
        page = page <= 1 ? 0 : page - 1;
        PageRequest pageable = PageRequest.of((int)page, (int)10);
        Page list = this.siteRepository.findAll((Pageable)pageable);
        System.out.println(list.getContent());
        model.addAttribute("list", (Object)list.getContent());
        model.addAttribute("currentPage", (Object)(page + 1));
        model.addAttribute("totalPages", (Object)list.getTotalPages());
    }
}
