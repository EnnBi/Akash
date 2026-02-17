package com.akash.controller;

import com.akash.entity.MaterialType;
import com.akash.repository.MaterialTypeRepository;
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
@RequestMapping(value={"/material-type"})
public class MaterialTypeController {
    @Autowired
    MaterialTypeRepository materialTypeRepository;

    @GetMapping
    public String add(Model model, HttpSession session) {
        if (!model.asMap().containsKey("material")) {
            model.addAttribute("material", (Object)new MaterialType());
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.material", model.asMap().get("result"));
        }
        int page = 1;
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        return "material";
    }

    @PostMapping(value={"/save"})
    public String save(@Valid @ModelAttribute(value="material") MaterialType materialType, BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("material", (Object)materialType);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/material-type";
        }
        if (this.materialTypeRepository.existsByName(materialType.getName())) {
            redirect.addFlashAttribute("material", (Object)materialType);
            redirect.addFlashAttribute("fail", (Object)"Material Type Already Exixts");
            return "redirect:/material-type";
        }
        this.materialTypeRepository.save(materialType);
        redirect.addFlashAttribute("success", (Object)"MaterialType Saved Successfully");
        return "redirect:/material-type";
    }

    @GetMapping(value={"/edit/{id}"})
    public String update(@PathVariable(value="id") long id, Model model, HttpSession session) {
        if (!model.asMap().containsKey("material")) {
            model.addAttribute("material", (Object)this.materialTypeRepository.findById(id));
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.material", model.asMap().get("result"));
        }
        model.addAttribute("edit", (Object)true);
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        return "material";
    }

    @PostMapping(value={"/update"})
    public String update(@Valid @ModelAttribute(value="material") MaterialType materialType, BindingResult result, RedirectAttributes redirect, HttpSession session, Model model) {
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        if (result.hasErrors()) {
            redirect.addFlashAttribute("material", (Object)materialType);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/material-type/edit/" + materialType.getId();
        }
        if (this.materialTypeRepository.checkMaterialAlreadyExists(materialType.getName(), materialType.getId()) != null) {
            redirect.addFlashAttribute("material", (Object)materialType);
            redirect.addFlashAttribute("fail", (Object)"MaterialType Already Exists");
            return "redirect:/material-type/edit/" + materialType.getId();
        }
        this.materialTypeRepository.save(materialType);
        redirect.addFlashAttribute("success", (Object)"MaterialType Updated Successfully");
        return "redirect:/material-type/pageno=" + page;
    }

    @GetMapping(value={"/delete/{id}"})
    public String delete(@PathVariable(value="id") long id, RedirectAttributes redirect, HttpSession session) {
        int page = (Integer)session.getAttribute("currentPage");
        this.materialTypeRepository.deleteById(id);
        redirect.addFlashAttribute("success", (Object)"MaterialType Deleted Successfully");
        return "redirect:/material-type/pageno=" + page;
    }

    @GetMapping(value={"/pageno={page}"})
    public String paginate(@PathVariable(value="page") int page, Model model, HttpSession session) {
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        model.addAttribute("material", (Object)new MaterialType());
        return "material";
    }

    public void pagination(int page, Model model) {
        page = page <= 1 ? 0 : page - 1;
        PageRequest pageable = PageRequest.of((int)page, (int)10);
        Page list = this.materialTypeRepository.findAll((Pageable)pageable);
        System.out.println(list.getContent());
        model.addAttribute("list", (Object)list.getContent());
        model.addAttribute("currentPage", (Object)(page + 1));
        model.addAttribute("totalPages", (Object)list.getTotalPages());
    }
}
