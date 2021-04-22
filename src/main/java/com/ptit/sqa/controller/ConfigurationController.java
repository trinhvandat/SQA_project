package com.ptit.sqa.controller;

import com.ptit.sqa.model.Config;
import com.ptit.sqa.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/configurations")
@RequiredArgsConstructor
public class ConfigurationController {
    private final ConfigurationService configService;
    @GetMapping("/configuration_form")
    public String showConfigForm(Model model ) {
        configService.ConfigInit();
        model.addAttribute( "config", configService.getConfig());
        return "configurations";
    }
    @PostMapping("/save_config_todb")
    public String saveConfigToDB(@ModelAttribute Config config ) {
        configService.saveConfigToDB(config);
        return "success";

    }
    @RequestMapping("/del_level_from_form")
    public String deleteLevelFromForm(@RequestParam(value = "id") Integer idLevel,Model model){
        configService.deleteLevelFromForm(idLevel);
        model.addAttribute("config",configService.getConfig());
        return "configurations";
    }
    @PostMapping("/add_level_to_form")
    public String addLevelToForm(Model model) {
        configService.addLevelToForm();
        model.addAttribute("config",configService.getConfig());
        return "configurations";
    }
}
