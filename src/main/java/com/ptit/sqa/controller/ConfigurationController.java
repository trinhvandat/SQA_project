package com.ptit.sqa.controller;

import com.ptit.sqa.model.Config;
import com.ptit.sqa.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/configuration")
@RequiredArgsConstructor
public class ConfigurationController {
    private final ConfigurationService configService;
    @GetMapping("/configB")
    public String configB(Model model ) {
        configService.init();
        model.addAttribute( "config", configService.getConfig());
        return "cauhinhBac";
    }
    @GetMapping("/configS")
    public String configS(){

        return "cauhinhSo";
    }
    @PostMapping("/configB")
    public String save(@ModelAttribute Config config ) {
        configService.saveData(config);
        return "success";

    }
    @PostMapping("/configB2")
    public String del(Model model){
        configService.deleteLevel();
        model.addAttribute("config",configService.getConfig());
        return "cauhinhBac";
    }
    @PostMapping("/configB3")
    public String add(Model model) {
        configService.addLevel();
        model.addAttribute("config",configService.getConfig());

        return "cauhinhBac";
    }
}
