package com.akash.controller;

import com.akash.entity.LabourCost;
import com.akash.repository.LabourCostRepository;
import com.akash.repository.LabourGroupRepository;
import com.akash.repository.ProductRepository;
import com.akash.repository.SizeRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value={"/labour-cost"})
public class LabourCostController {
    @Autowired
    LabourCostRepository labourCostRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    LabourGroupRepository labourGroupRepository;

    @GetMapping
    public String add(Model model, HttpSession session) {
        if (!model.asMap().containsKey("labourCost")) {
            model.addAttribute("labourCost", (Object)new LabourCost());
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.labourCost", model.asMap().get("result"));
        }
        int page = 1;
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        return "labourCost";
    }

    @PostMapping(value={"/save"})
    public String save(@Valid @ModelAttribute(value="labourCost") LabourCost labourCost, BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("labourCost", (Object)labourCost);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/labour-cost";
        }
        if (this.labourCostRepository.existsByProductAndLabourGroupAndSize(labourCost.getProduct(), labourCost.getLabourGroup(), labourCost.getSize())) {
            redirect.addFlashAttribute("labourCost", (Object)labourCost);
            redirect.addFlashAttribute("fail", (Object)"Labour Rate Already Exists");
            return "redirect:/labour-cost";
        }
        this.labourCostRepository.save(labourCost);
        redirect.addFlashAttribute("success", (Object)"Labour Rate Saved Successfully");
        return "redirect:/labour-cost";
    }

    @GetMapping(value={"/edit/{id}"})
    public String update(@PathVariable(value="id") long id, Model model, HttpSession session) {
        if (!model.asMap().containsKey("labourCost")) {
            model.addAttribute("labourCost", (Object)this.labourCostRepository.findById(id));
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.labourCost", model.asMap().get("result"));
        }
        model.addAttribute("edit", (Object)true);
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        return "labourCost";
    }

    @PostMapping(value={"/update"})
    public String update(@Valid @ModelAttribute(value="labourCost") LabourCost labourCost, BindingResult result, RedirectAttributes redirect, HttpSession session, Model model) {
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        if (result.hasErrors()) {
            redirect.addFlashAttribute("labourCost", (Object)labourCost);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/labour-cost/edit/" + labourCost.getId();
        }
        if (this.labourCostRepository.existsByProductAndLabourGroupAndSizeAndIdNot(labourCost.getProduct(), labourCost.getLabourGroup(), labourCost.getSize(), labourCost.getId())) {
            redirect.addFlashAttribute("labourCost", (Object)labourCost);
            redirect.addFlashAttribute("fail", (Object)"Labour Rate Already Exists");
            return "redirect:/labour-cost/edit/" + labourCost.getId();
        }
        this.labourCostRepository.save(labourCost);
        redirect.addFlashAttribute("success", (Object)"Labour Rate Updated Successfully");
        return "redirect:/labour-cost/pageno=" + page;
    }

    @GetMapping(value={"/delete/{id}"})
    public String delete(@PathVariable(value="id") long id, HttpSession session, RedirectAttributes redirect) {
        int page = (Integer)session.getAttribute("currentPage");
        try {
            this.labourCostRepository.deleteById(id);
            redirect.addFlashAttribute("success", (Object)"Labour Rate Deleted Successfully");
        }
        catch (Exception e) {
            redirect.addFlashAttribute("fail", (Object)"Labour Rate cannot be deleted");
        }
        return "redirect:/labour-cost/pageno=" + page;
    }

    @GetMapping(value={"/rate"})
    public ResponseEntity<?> findCost(@RequestParam(value="product") long productId, @RequestParam(value="size") long sizeId, @RequestParam(value="labourGroup") long labourGroupId) {
        return ResponseEntity.ok((Object)this.labourCostRepository.findRate(productId, sizeId, labourGroupId));
    }

    @GetMapping(value={"/pageno={page}"})
    public String paginate(@PathVariable(value="page") int page, Model model, HttpSession session) {
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        model.addAttribute("labourCost", (Object)new LabourCost());
        return "labourCost";
    }

    public void pagination(int page, Model model) {
        page = page <= 1 ? 0 : page - 1;
        PageRequest pageable = PageRequest.of((int)page, (int)20);
        Page list = this.labourCostRepository.findAll((Pageable)pageable);
        System.out.println(list.getContent());
        model.addAttribute("list", (Object)list.getContent());
        model.addAttribute("currentPage", (Object)(page + 1));
        this.fillModel(model);
        model.addAttribute("totalPages", (Object)list.getTotalPages());
    }

    public void fillModel(Model model) {
        model.addAttribute("labourGroups", (Object)this.labourGroupRepository.findAll());
        model.addAttribute("sizes", (Object)this.sizeRepository.findAll());
        model.addAttribute("products", (Object)this.productRepository.findAll());
    }
}
