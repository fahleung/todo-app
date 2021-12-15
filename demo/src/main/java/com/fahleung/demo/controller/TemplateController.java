package com.fahleung.demo.controller;

import com.fahleung.demo.user.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/")
public class TemplateController {
    @GetMapping("login")
    public String getLoginView(WebRequest request, Model model) {
        User userDto = new User();
        model.addAttribute("user", userDto);
        return "login";
    }

    @GetMapping("index")
    public String getIndex() {
        return "index";
    }
}
