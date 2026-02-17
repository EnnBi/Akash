package com.akash.controller;

import com.akash.entity.RawMaterial;
import com.akash.entity.RawMaterialSearch;
import com.akash.repository.AppUserRepository;
import com.akash.repository.MaterialTypeRepository;
import com.akash.repository.RawMaterialRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value={"/raw-material"})
public class RawMaterialController {
    @Autowired
    RawMaterialRepository rawMaterialRepository;
    @Autowired
    MaterialTypeRepository materialRepository;
    @Autowired
    AppUserRepository userRepository;
    int from = 0;
    int total = 0;
    int ROWS = 2;
    Long records = 0L;

    @GetMapping
    public String add(Model model) {
        this.fillModel(model);
        if (!model.asMap().containsKey("rawMaterial")) {
            model.addAttribute("rawMaterial", (Object)new RawMaterial());
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.rawMaterial", model.asMap().get("result"));
        }
        return "rawMaterial";
    }

    @PostMapping(value={"/save"})
    public String save(@Valid @ModelAttribute(value="rawMaterial") RawMaterial rawMaterial, BindingResult result, Model model, RedirectAttributes redirect) {
        if (Objects.isNull(rawMaterial.getDate())) {
            rawMaterial.setDate(LocalDate.now());
        }
        if (result.hasErrors()) {
            redirect.addFlashAttribute("rawMaterial", (Object)rawMaterial);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter all field correctly");
            return "redirect:/raw-material";
        }
        if (this.rawMaterialRepository.existsByChalanNumber(rawMaterial.getChalanNumber())) {
            redirect.addFlashAttribute("rawMaterial", (Object)rawMaterial);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Raw Material Already Exists");
            return "redirect:/raw-material";
        }
        this.rawMaterialRepository.save(rawMaterial);
        redirect.addFlashAttribute("success", (Object)"Raw Material Saved Successfully");
        return "redirect:/raw-material";
    }

    @GetMapping(value={"/search"})
    public String list(Model model, HttpSession session) {
        this.fillModel(model);
        model.addAttribute("rawMaterialSearch", (Object)new RawMaterialSearch());
        session.setAttribute("currentPage", (Object)1);
        return "rawList";
    }

    @PostMapping(value={"/search"})
    public String searchOrders(RawMaterialSearch rawMaterialSearch, Model model, HttpSession session) {
        int page = 1;
        session.setAttribute("rawMaterialSearch", (Object)rawMaterialSearch);
        this.pagination(page, rawMaterialSearch, model, session);
        return "rawList";
    }

    @GetMapping(value={"/edit/{id}"})
    public String update(@PathVariable(value="id") long id, Model model, HttpSession session) {
        this.fillModel(model);
        if (!model.asMap().containsKey("rawMaterial")) {
            model.addAttribute("rawMaterial", (Object)this.rawMaterialRepository.findById(id));
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.rawMaterial", model.asMap().get("result"));
        }
        model.addAttribute("edit", (Object)true);
        int page = (Integer)session.getAttribute("currentPage");
        return "rawMaterial";
    }

    @PostMapping(value={"/update"})
    public String update(@Valid @ModelAttribute(value="rawMaterial") RawMaterial rawMaterial, BindingResult result, RedirectAttributes redirect, HttpSession session, Model model) {
        if (Objects.isNull(rawMaterial.getDate())) {
            rawMaterial.setDate(LocalDate.now());
        }
        int page = (Integer)session.getAttribute("currentPage");
        if (result.hasErrors()) {
            redirect.addFlashAttribute("rawMaterial", (Object)rawMaterial);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter all the fields correctly");
            return "redirect:/raw-material/edit/" + rawMaterial.getId();
        }
        if (this.rawMaterialRepository.checkRawMaterialAlreadyExists(rawMaterial.getChalanNumber(), rawMaterial.getId()) != null) {
            redirect.addFlashAttribute("rawMaterial", (Object)rawMaterial);
            redirect.addFlashAttribute("fail", (Object)"Raw Material Already Exists");
            return "redirect:/raw-material/edit/" + rawMaterial.getId();
        }
        this.rawMaterialRepository.save(rawMaterial);
        redirect.addFlashAttribute("success", (Object)"Raw Material Updated Successfully");
        return "redirect:/raw-material/" + page;
    }

    @GetMapping(value={"/delete/{id}"})
    public String delete(@PathVariable(value="id") long id, HttpSession session, RedirectAttributes redirect) {
        int page = (Integer)session.getAttribute("currentPage");
        this.rawMaterialRepository.deleteById(id);
        redirect.addFlashAttribute("success", (Object)"Raw Material Deleted Successfully");
        return "redirect:/raw-material/" + page;
    }

    private void fillModel(Model model) {
        model.addAttribute("userList", this.userRepository.findByUserType_NameAndActive("Dealer", true));
        model.addAttribute("rawList", (Object)this.materialRepository.findAll());
    }

    public void pagination(int page, RawMaterialSearch rawMaterialSearch, Model model, HttpSession session) {
        page = page > 0 ? page : 1;
        this.from = this.ROWS * (page - 1);
        this.records = this.rawMaterialRepository.searchRawMaterialsCount(rawMaterialSearch);
        this.total = (int)Math.ceil((double)this.records.longValue() / (double)this.ROWS);
        List rawMaterials = this.rawMaterialRepository.searchRawMaterialPaginated(rawMaterialSearch, this.from);
        model.addAttribute("totalPages", (Object)this.total);
        session.setAttribute("currentPage", (Object)page);
        model.addAttribute("rawMaterial", (Object)rawMaterials);
        model.addAttribute("rawMaterialSearch", (Object)rawMaterialSearch);
        System.out.println("total records: " + this.records + " total Pages: " + this.total + " Current page: " + page);
        this.fillModel(model);
    }

    @GetMapping(value={"/{page}"})
    public String showRawMaterial(@PathVariable(value="page") int page, Model model, HttpSession session) {
        RawMaterialSearch rawMaterialSearch = (RawMaterialSearch)session.getAttribute("rawMaterialSearch");
        this.pagination(page, rawMaterialSearch, model, session);
        return "rawList";
    }
}
