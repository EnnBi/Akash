package com.akash.controller;

import com.akash.entity.Vehicle;
import com.akash.repository.AppUserRepository;
import com.akash.repository.VehicleRepository;
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
@RequestMapping(value={"/vehicle"})
public class VehicleController {
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    AppUserRepository userRepository;

    @GetMapping
    public String add(Model model, HttpSession session) {
        if (!model.asMap().containsKey("vehicle")) {
            model.addAttribute("vehicle", (Object)new Vehicle());
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.vehicle", model.asMap().get("result"));
        }
        int page = 1;
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        return "vehicle";
    }

    @PostMapping(value={"/save"})
    public String save(@Valid @ModelAttribute(value="vehicle") Vehicle vehicle, BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("vehicle", (Object)vehicle);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the fields correctly");
            return "redirect:/vehicle";
        }
        if (this.vehicleRepository.existsByNumber(vehicle.getNumber())) {
            redirect.addFlashAttribute("vehicle", (Object)vehicle);
            redirect.addFlashAttribute("fail", (Object)"Vehicle Already Exists");
            return "redirect:/vehicle";
        }
        this.vehicleRepository.save(vehicle);
        redirect.addFlashAttribute("success", (Object)"Vehicle Saved Successfully");
        return "redirect:/vehicle";
    }

    @GetMapping(value={"/edit/{id}"})
    public String update(@PathVariable(value="id") long id, Model model, HttpSession session) {
        if (!model.asMap().containsKey("vehicle")) {
            model.addAttribute("vehicle", (Object)this.vehicleRepository.findById(id));
        }
        if (model.asMap().containsKey("result")) {
            model.addAttribute("org.springframework.validation.BindingResult.vehicle", model.asMap().get("result"));
        }
        model.addAttribute("edit", (Object)true);
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        return "vehicle";
    }

    @PostMapping(value={"/update"})
    public String update(@Valid @ModelAttribute(value="vehicle") Vehicle vehicle, BindingResult result, RedirectAttributes redirect, HttpSession session, Model model) {
        int page = (Integer)session.getAttribute("currentPage");
        this.pagination(page, model);
        if (result.hasErrors()) {
            redirect.addFlashAttribute("vehicle", (Object)vehicle);
            redirect.addFlashAttribute("result", (Object)result);
            redirect.addFlashAttribute("fail", (Object)"Please enter the field correctly");
            return "redirect:/vehicle/edit/" + vehicle.getId();
        }
        if (this.vehicleRepository.checkVehicleAlreadyExists(vehicle.getNumber(), vehicle.getId()) != null) {
            redirect.addFlashAttribute("vehicle", (Object)vehicle);
            redirect.addFlashAttribute("fail", (Object)"Vehicle Already Exists");
            return "redirect:/vehicle/edit/" + vehicle.getId();
        }
        this.vehicleRepository.save(vehicle);
        redirect.addFlashAttribute("success", (Object)"Vehicle Updated Successfully");
        return "redirect:/vehicle/pageno=" + page;
    }

    @GetMapping(value={"/delete/{id}"})
    public String delete(@PathVariable(value="id") long id, HttpSession session, RedirectAttributes redirect) {
        int page = (Integer)session.getAttribute("currentPage");
        try {
            this.vehicleRepository.deleteById(id);
            redirect.addFlashAttribute("success", (Object)"Vehicle Deleted Successfully");
        }
        catch (Exception e) {
            redirect.addFlashAttribute("fail", (Object)"Vehicle cannot be deleted");
        }
        return "redirect:/vehicle/pageno=" + page;
    }

    @GetMapping(value={"/pageno={page}"})
    public String paginate(@PathVariable(value="page") int page, Model model, HttpSession session) {
        session.setAttribute("currentPage", (Object)page);
        this.pagination(page, model);
        model.addAttribute("vehicle", (Object)new Vehicle());
        return "vehicle";
    }

    @GetMapping(value={"/{id}/driver"})
    public ResponseEntity<?> getDriver(@PathVariable(value="id") long id) {
        return ResponseEntity.ok((Object)((Vehicle)this.vehicleRepository.findById(id).get()).getDriver());
    }

    public void pagination(int page, Model model) {
        page = page <= 1 ? 0 : page - 1;
        PageRequest pageable = PageRequest.of((int)page, (int)10);
        Page list = this.vehicleRepository.findAll((Pageable)pageable);
        System.out.println(list.getContent());
        model.addAttribute("list", (Object)list.getContent());
        model.addAttribute("currentPage", (Object)(page + 1));
        model.addAttribute("userList", this.userRepository.findByUserType_NameAndActive("Driver", true));
        model.addAttribute("totalPages", (Object)list.getTotalPages());
    }
}
