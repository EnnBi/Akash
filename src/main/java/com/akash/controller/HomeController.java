package com.akash.controller;

import com.akash.repository.BillBookRepository;
import com.akash.repository.DayBookRepository;
import com.akash.repository.ManufactureRepository;
import java.time.LocalDate;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    DayBookRepository dayBookRepository;
    @Autowired
    ManufactureRepository manufactureRepository;
    @Autowired
    BillBookRepository billBookRepository;

    @GetMapping(value={"/dashboard"})
    public String home() {
        Log.info((Object)"Good");
        return "index";
    }

    @GetMapping(value={"/template"})
    public String template() {
        return "template";
    }

    @GetMapping(value={"/bar-chart"})
    public ResponseEntity<?> barChart() {
        LocalDate startDate = LocalDate.now().minusDays(7L);
        LocalDate endDate = LocalDate.now();
        return ResponseEntity.ok(this.manufactureRepository.findQuantityGroupByDateAndProductBetweenDates(startDate, endDate));
    }
}
