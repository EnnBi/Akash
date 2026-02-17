package com.akash.controller;

import com.akash.entity.Manufacture;
import com.akash.entity.ManufactureSearch;
import com.akash.repository.AppUserRepository;
import com.akash.repository.LabourGroupRepository;
import com.akash.repository.ManufactureRepository;
import com.akash.repository.ProductRepository;
import com.akash.repository.SizeRepository;
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
@RequestMapping(value={"/manufacture"})
public class ManufactureController {
    @Autowired
    ManufactureRepository manufactureRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    LabourGroupRepository labourGroupRepository;
    int from = 0;
    int total = 0;
    Long records = 0L;

    @GetMapping
    public String add(Model model) {
        this.fillModel(model);
        model.addAttribute("manufacture", (Object)new Manufacture());
        return "manufacture";
    }

    @PostMapping(value={"/save"})
    public String save(@ModelAttribute(value="manufacture") Manufacture manufacture, Model model, RedirectAttributes redirectAttributes) {
        manufacture.getLabourInfo().forEach(l -> l.setManufacture(manufacture));
        this.manufactureRepository.save(manufacture);
        redirectAttributes.addFlashAttribute("success", (Object)"Manufacture saved successfully");
        return "redirect:/manufacture";
    }

    @GetMapping(value={"/edit/{id}"})
    public String update(@PathVariable(value="id") long id, Model model) {
        Manufacture manufacture = this.manufactureRepository.findById(id).orElse(null);
        model.addAttribute("manufacture", (Object)manufacture);
        model.addAttribute("labours", this.appUserRepository.findByUserType_NameAndLabourGroup_IdAndActive("Labour", manufacture.getLabourGroup().getId(), true));
        this.fillModel(model);
        return "manufactureEdit";
    }

    @GetMapping(value={"/delete/{id}"})
    public String delete(@PathVariable(value="id") long id, RedirectAttributes redirectAttributes) {
        this.manufactureRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", (Object)"Manufacture deleted successfully");
        return "redirect:/manufacture/search";
    }

    @GetMapping(value={"/search"})
    public String searchGet(Model model) {
        model.addAttribute("manufactureSearch", (Object)new ManufactureSearch());
        this.fillModel(model);
        return "manufactureSearch";
    }

    @PostMapping(value={"/search"})
    public String searchPost(ManufactureSearch manufactureSearch, Model model, HttpSession session) {
        session.setAttribute("manufactureSearch", (Object)manufactureSearch);
        int page = 1;
        this.pagination(page, manufactureSearch, model);
        return "manufactureSearch";
    }

    @GetMapping(value={"/pageno={page}"})
    public String page(@PathVariable(value="page") int page, HttpSession session, Model model) {
        ManufactureSearch manufactureSearch = (ManufactureSearch)session.getAttribute("manufactureSearch");
        this.pagination(page, manufactureSearch, model);
        return "manufactureSearch";
    }

    public void pagination(int page, ManufactureSearch manufactureSearch, Model model) {
        page = page > 0 ? page : 1;
        this.from = 20 * (page - 1);
        this.records = this.manufactureRepository.count(manufactureSearch);
        this.total = (int)Math.ceil((double)this.records.longValue() / 20.0);
        List manufactures = this.manufactureRepository.searchPaginated(manufactureSearch, this.from);
        model.addAttribute("totalPages", (Object)this.total);
        model.addAttribute("currentPage", (Object)page);
        model.addAttribute("manufactures", (Object)manufactures);
        model.addAttribute("manufactureSearch", (Object)manufactureSearch);
        System.out.println("total records: " + this.records + " total Pages: " + this.total + " Current page: " + page);
        this.fillModel(model);
    }

    private void fillModel(Model model) {
        model.addAttribute("products", (Object)this.productRepository.findAll());
        model.addAttribute("sizes", (Object)this.sizeRepository.findAll());
        model.addAttribute("labourGroups", (Object)this.labourGroupRepository.findAll());
    }
}
