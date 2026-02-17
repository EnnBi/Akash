package com.akash.controller;

import com.akash.controller.InventorySearch;
import com.akash.entity.InventoryCount;
import com.akash.entity.Product;
import com.akash.entity.Size;
import com.akash.repository.BillBookRepository;
import com.akash.repository.ManufactureRepository;
import com.akash.repository.ProductRepository;
import com.akash.repository.SizeRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value={"/inventory"})
public class InventoryController {
    @Autowired
    ProductRepository productRepo;
    @Autowired
    BillBookRepository billBookRepo;
    @Autowired
    ManufactureRepository manufactureRepo;
    @Autowired
    SizeRepository sizeRepo;

    @RequestMapping(method={RequestMethod.GET})
    public String inventoryGet(Model model) {
        model.addAttribute("products", (Object)this.productRepo.findAll());
        model.addAttribute("inventorySearch", (Object)new InventorySearch());
        return "inventory";
    }

    @RequestMapping(method={RequestMethod.POST})
    public String doPost(InventorySearch is, Model model) {
        Product product = this.productRepo.findById(is.getProduct()).orElse(null);
        ArrayList counts = new ArrayList();
        product.getSizes().stream().map(p -> p.getId()).forEach(id -> {
            Double manufactured = this.manufactureRepo.findSumOfManufactured(is.getProduct(), (long)id, is.getStartDate(), is.getEndDate());
            Double sold = this.billBookRepo.findSumOfSold(is.getProduct(), (long)id, is.getStartDate(), is.getEndDate());
            String sizeName = ((Size)this.sizeRepo.findById(id).get()).getName();
            String productName = product.getName();
            InventoryCount inventoryCount = new InventoryCount(productName, sizeName, manufactured, sold);
            counts.add(inventoryCount);
        });
        model.addAttribute("counts", counts);
        model.addAttribute("inventorySearch", (Object)is);
        model.addAttribute("products", (Object)this.productRepo.findAll());
        return "inventory";
    }
}
