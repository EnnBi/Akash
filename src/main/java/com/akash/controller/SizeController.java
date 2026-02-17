package com.akash.controller;

import com.akash.entity.Size;
import com.akash.repository.SizeRepository;
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
@RequestMapping(value={"/size"})
public class SizeController {
    @Autowired
    SizeRepository sizeRepository;

    @GetMapping
    public String add(Model model, HttpSession session) {
        if (!model.asMap().containsKey("size")) {
            model.addAttribute("size", (Object)new Size());
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.size", model.asMap().get("result"));
        }
        int page = 1;
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        return "size";
    }

    @PostMapping(value={"/save"})
    public String save(@Valid @ModelAttribute(value="size") Size size, BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("size", (Object)size);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/size";
        }
        if (this.sizeRepository.existsByName(size.getName())) {
            redirect.addFlashAttribute("size", (Object)size);
            redirect.addFlashAttribute("fail", (Object)"Size Already Exists");
            return "redirect:/size";
        }
        this.sizeRepository.save(size);
        redirect.addFlashAttribute("success", (Object)"Size Saved Successfully");
        return "redirect:/size";
    }

    @GetMapping(value={"/edit/{id}"})
    public String update(@PathVariable(value="id") long id, Model model, HttpSession session) {
        if (!model.asMap().containsKey("size")) {
            model.addAttribute("size", (Object)this.sizeRepository.findById(id));
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.size", model.asMap().get("result"));
        }
        model.addAttribute("edit", (Object)true);
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        return "size";
    }

    @PostMapping(value={"/update"})
    public String update(@Valid @ModelAttribute(value="size") Size size, BindingResult result, RedirectAttributes redirect, HttpSession session, Model model) {
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        if (result.hasErrors()) {
            redirect.addFlashAttribute("size", (Object)size);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/size/edit/" + size.getId();
        }
        if (this.sizeRepository.checkSizeAlreadyExists(size.getName(), size.getId()) != null) {
            redirect.addFlashAttribute("size", (Object)size);
            redirect.addFlashAttribute("fail", (Object)"Size Already Exists");
            return "redirect:/size/edit/" + size.getId();
        }
        this.sizeRepository.save(size);
        redirect.addFlashAttribute("success", (Object)"Site Updated Successfully");
        return "redirect:/size/pageno=" + page;
    }

    @GetMapping(value={"/delete/{id}"})
    public String delete(@PathVariable(value="id") long id, HttpSession session, RedirectAttributes redirect) {
        int page = (Integer)session.getAttribute("currentPage");
        try {
            this.sizeRepository.deleteById(id);
            redirect.addFlashAttribute("success", (Object)"Size Deleted Successfully");
        }
        catch (Exception e) {
            redirect.addFlashAttribute("fail", (Object)"Size cannot be deleted");
        }
        return "redirect:/size/pageno=" + page;
    }

    @GetMapping(value={"/pageno={page}"})
    public String paginate(@PathVariable(value="page") int page, Model model, HttpSession session) {
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        model.addAttribute("size", (Object)new Size());
        return "size";
    }

    public void pagination(int page, Model model) {
        page = page <= 1 ? 0 : page - 1;
        PageRequest pageable = PageRequest.of((int)page, (int)10);
        Page list = this.sizeRepository.findAll((Pageable)pageable);
        System.out.println(list.getContent());
        model.addAttribute("list", (Object)list.getContent());
        model.addAttribute("currentPage", (Object)(page + 1));
        model.addAttribute("totalPages", (Object)list.getTotalPages());
    }
}
