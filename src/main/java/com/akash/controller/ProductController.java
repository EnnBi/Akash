package com.akash.controller;

import com.akash.entity.Product;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value={"/product"})
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SizeRepository sizeRepository;

    @GetMapping
    public String add(Model model, HttpSession session) {
        if (!model.asMap().containsKey("product")) {
            model.addAttribute("product", (Object)new Product());
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.product", model.asMap().get("result"));
        }
        int page = 1;
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        return "product";
    }

    @PostMapping(value={"/save"})
    public String save(@Valid @ModelAttribute(value="product") Product product, BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("product", (Object)product);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the fields correctly");
            return "redirect:/product";
        }
        if (this.productRepository.existsByName(product.getName())) {
            redirect.addFlashAttribute("product", (Object)product);
            redirect.addFlashAttribute("fail", (Object)"Product Already Exists");
            return "redirect:/product";
        }
        this.productRepository.save(product);
        redirect.addFlashAttribute("success", (Object)"Product Saved Successfully");
        return "redirect:/product";
    }

    @GetMapping(value={"/edit/{id}"})
    public String update(@PathVariable(value="id") long id, Model model, HttpSession session) {
        if (!model.asMap().containsKey("product")) {
            model.addAttribute("product", (Object)this.productRepository.findById(id));
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.product", model.asMap().get("result"));
        }
        model.addAttribute("edit", (Object)true);
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        return "product";
    }

    @PostMapping(value={"/update"})
    public String update(@Valid @ModelAttribute(value="product") Product product, BindingResult result, RedirectAttributes redirect, HttpSession session, Model model) {
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        if (result.hasErrors()) {
            redirect.addFlashAttribute("product", (Object)product);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/product/edit/" + product.getId();
        }
        if (this.productRepository.checkProductAlreadyExists(product.getName(), product.getId()) != null) {
            redirect.addFlashAttribute("product", (Object)product);
            redirect.addFlashAttribute("fail", (Object)"Product Already Exists");
            return "redirect:/product/edit/" + product.getId();
        }
        this.productRepository.save(product);
        redirect.addFlashAttribute("success", (Object)"Product Updated Successfully");
        return "redirect:/product/pageno=" + page;
    }

    @GetMapping(value={"/delete/{id}"})
    public String delete(@PathVariable(value="id") long id, RedirectAttributes redirect, HttpSession session) {
        int page = (Integer)session.getAttribute("currentPage");
        try {
            this.productRepository.deleteById(id);
            redirect.addFlashAttribute("success", (Object)"Product Deleted Successfully");
        }
        catch (Exception e) {
            redirect.addFlashAttribute("fail", (Object)"Product cannot be deleted");
        }
        return "redirect:/product/pageno=" + page;
    }

    @GetMapping(value={"/{id}/sizes"})
    public ResponseEntity<?> getSizesOnProduct(@PathVariable long id) {
        return ResponseEntity.ok(this.productRepository.findSizesOnPrductId(id));
    }

    @GetMapping(value={"/pageno={page}"})
    public String paginate(@PathVariable(value="page") int page, Model model, HttpSession session) {
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        model.addAttribute("product", (Object)new Product());
        return "product";
    }

    public void pagination(int page, Model model) {
        page = page <= 1 ? 0 : page - 1;
        PageRequest pageable = PageRequest.of((int)page, (int)10);
        Page list = this.productRepository.findAll((Pageable)pageable);
        System.out.println(list.getContent());
        model.addAttribute("list", (Object)list.getContent());
        model.addAttribute("currentPage", (Object)(page + 1));
        model.addAttribute("sizeList", (Object)this.sizeRepository.findAll());
        model.addAttribute("totalPages", (Object)list.getTotalPages());
    }
}
