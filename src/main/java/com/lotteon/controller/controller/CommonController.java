package com.lotteon.controller.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CommonController {

    @Value(value = "${spring.application.version}")
    private String version;
    @Value(value = "${spring.application.name}")
    private String appName;

    @ModelAttribute
    public void version(Model model) {
        model.addAttribute("version", version);
        model.addAttribute("appName", appName);

    }
}
