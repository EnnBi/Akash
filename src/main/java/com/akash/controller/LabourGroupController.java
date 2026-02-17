package com.akash.controller;

import com.akash.entity.LabourGroup;
import com.akash.repository.LabourGroupRepository;
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
@RequestMapping(value={"/labour-group"})
public class LabourGroupController {
    @Autowired
    LabourGroupRepository labourGroupRepository;

    @GetMapping
    public String add(Model model, HttpSession session) {
        if (!model.asMap().containsKey("labourGroup")) {
            model.addAttribute("labourGroup", (Object)new LabourGroup());
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.labourGroup", model.asMap().get("result"));
        }
        int page = 1;
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        return "labourGroup";
    }

    @PostMapping(value={"/save"})
    public String save(@Valid @ModelAttribute(value="labourGroup") LabourGroup labourGroup, BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("labourGroup", (Object)labourGroup);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/labour-group";
        }
        if (this.labourGroupRepository.existsByName(labourGroup.getName())) {
            redirect.addFlashAttribute("labourGroup", (Object)labourGroup);
            redirect.addFlashAttribute("fail", (Object)"LabourGroup Already Exixts");
            return "redirect:/labour-group";
        }
        this.labourGroupRepository.save(labourGroup);
        redirect.addFlashAttribute("success", (Object)"LabourGroup Saved Successfully");
        return "redirect:/labour-group";
    }

    @GetMapping(value={"/edit/{id}"})
    public String update(@PathVariable(value="id") long id, Model model, HttpSession session) {
        if (!model.asMap().containsKey("labourGroup")) {
            model.addAttribute("labourGroup", (Object)this.labourGroupRepository.findById(id));
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.labourGroup", model.asMap().get("result"));
        }
        model.addAttribute("edit", (Object)true);
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        return "labourGroup";
    }

    @PostMapping(value={"/update"})
    public String update(@Valid @ModelAttribute(value="labourGroup") LabourGroup labourGroup, BindingResult result, RedirectAttributes redirect, HttpSession session, Model model) {
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        if (result.hasErrors()) {
            redirect.addFlashAttribute("labourGroup", (Object)labourGroup);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/labour-group/edit/" + labourGroup.getId();
        }
        if (this.labourGroupRepository.checkLabourGroupAlreadyExists(labourGroup.getName(), labourGroup.getId()) != null) {
            redirect.addFlashAttribute("labourGroup", (Object)labourGroup);
            redirect.addFlashAttribute("fail", (Object)"LabourGroup Already Exists");
            return "redirect:/labour-group/edit/" + labourGroup.getId();
        }
        this.labourGroupRepository.save(labourGroup);
        redirect.addFlashAttribute("success", (Object)"LabourGroup Updated Successfully");
        return "redirect:/labour-group/pageno=" + page;
    }

    @GetMapping(value={"/delete/{id}"})
    public String delete(@PathVariable(value="id") long id, HttpSession session, RedirectAttributes redirect) {
        int page = (Integer)session.getAttribute("currentPage");
        this.labourGroupRepository.deleteById(id);
        redirect.addFlashAttribute("success", (Object)"LabourGroup Deleted Successfully");
        return "redirect:/labour-group/pageno=" + page;
    }

    @GetMapping(value={"/pageno={page}"})
    public String paginate(@PathVariable(value="page") int page, Model model, HttpSession session) {
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        model.addAttribute("labourGroup", (Object)new LabourGroup());
        return "labourGroup";
    }

    public void pagination(int page, Model model) {
        page = page <= 1 ? 0 : page - 1;
        PageRequest pageable = PageRequest.of((int)page, (int)10);
        Page list = this.labourGroupRepository.findAll((Pageable)pageable);
        System.out.println(list.getContent());
        model.addAttribute("list", (Object)list.getContent());
        model.addAttribute("currentPage", (Object)(page + 1));
        model.addAttribute("totalPages", (Object)list.getTotalPages());
    }
}
