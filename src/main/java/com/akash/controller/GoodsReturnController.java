package com.akash.controller;

import com.akash.entity.BillBook;
import com.akash.entity.BillBookSearch;
import com.akash.entity.GoodsReturn;
import com.akash.entity.GoodsReturnSearch;
import com.akash.entity.Sales;
import com.akash.repository.AppUserRepository;
import com.akash.repository.BillBookRepository;
import com.akash.repository.GoodsReturnRepository;
import com.akash.repository.LabourGroupRepository;
import com.akash.repository.ProductRepository;
import com.akash.repository.SiteRepository;
import com.akash.repository.SizeRepository;
import com.akash.repository.VehicleRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value={"/return-goods"})
public class GoodsReturnController {
    int from = 0;
    int total = 0;
    Long records = 0L;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    BillBookRepository billBookRepository;
    @Autowired
    GoodsReturnRepository goodsReturnRepo;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    VehicleRepository vehicleRepo;
    @Autowired
    SiteRepository siteRepository;
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    LabourGroupRepository labourGroupRepository;

    @GetMapping(value={"/search"})
    public String searchGet(Model model) {
        model.addAttribute("goodsReturnSearch", (Object)new BillBookSearch());
        this.fillModel(model);
        return "goodsreturnsearch";
    }

    @PostMapping(value={"/search"})
    public String searchPost(BillBookSearch billBookSearch, Model model, HttpSession session) {
        session.setAttribute("goodsReturnSearch", (Object)billBookSearch);
        int page = 1;
        this.pagination(page, billBookSearch, model);
        return "goodsreturnsearch";
    }

    @GetMapping(value={"/add/{id}"})
    public String update(@PathVariable(value="id") long id, Model model) {
        BillBook billBook = this.billBookRepository.findById(id).orElse(null);
        for (Sales s : billBook.getSales()) {
            s.setQuantity(null);
        }
        GoodsReturn goodsReturn = this.goodsReturnRepo.billBookToGoodsReturnMapping(billBook);
        model.addAttribute("goodsReturn", (Object)goodsReturn);
        String[] userTypes = new String[]{"Customer", "Contractor"};
        model.addAttribute("customers", this.appUserRepository.findByUserType_NameInAndActive(userTypes, true));
        model.addAttribute("products", (Object)this.productRepository.findAll());
        model.addAttribute("sizes", (Object)this.sizeRepository.findAll());
        return "goodsreturnadd";
    }

    @RequestMapping(value={"/add"}, params={"save"}, method={RequestMethod.POST})
    public String update(@ModelAttribute(value="goodsReturn") GoodsReturn goodsReturn, Model model, RedirectAttributes redirectAttributes) {
        if (Objects.isNull(goodsReturn.getDate())) {
            goodsReturn.setDate(LocalDate.now());
        }
        goodsReturn.getSales().forEach(s -> {
            s.setBillBook(null);
            s.setId(0L);
            s.setGoodsReturn(goodsReturn);
        });
        this.goodsReturnRepo.save(goodsReturn);
        redirectAttributes.addFlashAttribute("success", (Object)"Goods Returned saved successfully");
        return "redirect:/return-goods/search";
    }

    @GetMapping(value={"/edit"})
    public String returnEditSearchGet(Model model) {
        model.addAttribute("ReturnSearch", (Object)new GoodsReturnSearch());
        this.fillModel(model);
        return "goodsreturneditsearch";
    }

    @PostMapping(value={"/edit"})
    public String searchPostEdit(GoodsReturnSearch goodsRetunSearch, Model model, HttpSession session) {
        session.setAttribute("ReturnSearch", (Object)goodsRetunSearch);
        int page = 1;
        this.editPagination(page, goodsRetunSearch, model);
        return "goodsreturneditsearch";
    }

    @GetMapping(value={"/edit/{id}"})
    public String edit(@PathVariable(value="id") long id, Model model) {
        GoodsReturn goodsReturn = this.goodsReturnRepo.findById(id).orElse(null);
        model.addAttribute("edit", (Object)true);
        model.addAttribute("goodsReturn", (Object)goodsReturn);
        String[] userTypes = new String[]{"Customer", "Contractor"};
        model.addAttribute("customers", this.appUserRepository.findByUserType_NameInAndActive(userTypes, true));
        model.addAttribute("products", (Object)this.productRepository.findAll());
        model.addAttribute("sizes", (Object)this.sizeRepository.findAll());
        return "goodsreturnadd";
    }

    @RequestMapping(value={"/update"}, params={"save"}, method={RequestMethod.POST})
    public String updateReturnGoods(@ModelAttribute(value="goodsReturn") GoodsReturn goodsReturn, Model model, RedirectAttributes redirectAttributes) {
        goodsReturn.getSales().forEach(s -> s.setGoodsReturn(goodsReturn));
        this.goodsReturnRepo.save(goodsReturn);
        redirectAttributes.addFlashAttribute("success", (Object)"Goods Returned updated successfully");
        return "redirect:/return-goods/edit";
    }

    public void pagination(int page, BillBookSearch billBookSearch, Model model) {
        page = page > 0 ? page : 1;
        this.from = 20 * (page - 1);
        this.records = this.billBookRepository.count(billBookSearch);
        this.total = (int)Math.ceil((double)this.records.longValue() / 20.0);
        List billBooks = this.billBookRepository.searchPaginated(billBookSearch, this.from);
        model.addAttribute("totalPages", (Object)this.total);
        model.addAttribute("currentPage", (Object)page);
        model.addAttribute("billBooks", (Object)billBooks);
        model.addAttribute("goodsReturnSearch", (Object)billBookSearch);
        System.out.println("total records: " + this.records + " total Pages: " + this.total + " Current page: " + page);
        this.fillModel(model);
    }

    public void editPagination(int page, GoodsReturnSearch goodsRetunSearch, Model model) {
        page = page > 0 ? page : 1;
        this.from = 20 * (page - 1);
        this.records = this.goodsReturnRepo.count(goodsRetunSearch);
        this.total = (int)Math.ceil((double)this.records.longValue() / 20.0);
        List goodsReturn = this.goodsReturnRepo.searchPaginated(goodsRetunSearch, this.from);
        model.addAttribute("totalPages", (Object)this.total);
        model.addAttribute("currentPage", (Object)page);
        model.addAttribute("goodsReturnList", (Object)goodsReturn);
        model.addAttribute("ReturnSearch", (Object)goodsRetunSearch);
        System.out.println("total records: " + this.records + " total Pages: " + this.total + " Current page: " + page);
        this.fillModel(model);
    }

    @GetMapping(value={"/add/page={page}"})
    public String page(@PathVariable(value="page") int page, HttpSession session, Model model) {
        BillBookSearch billBookSearch = (BillBookSearch)session.getAttribute("goodsReturnSearch");
        this.pagination(page, billBookSearch, model);
        return "goodsreturnsearch";
    }

    @GetMapping(value={"/edit/page={page}"})
    public String Editpage(@PathVariable(value="page") int page, HttpSession session, Model model) {
        GoodsReturnSearch goodsRetunSearch = (GoodsReturnSearch)session.getAttribute("ReturnSearch");
        this.editPagination(page, goodsRetunSearch, model);
        return "goodsreturneditsearch";
    }

    private void fillModel(Model model) {
        String[] userTypes = new String[]{"Customer", "Contractor"};
        model.addAttribute("customers", this.appUserRepository.findByUserType_NameInAndActive(userTypes, true));
    }
}
