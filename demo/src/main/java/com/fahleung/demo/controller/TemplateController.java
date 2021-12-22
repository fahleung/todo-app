package com.fahleung.demo.controller;

import com.fahleung.demo.task.TasklistService;
import com.fahleung.demo.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class TemplateController {

    private final TasklistService tasklistService;

    @Autowired
    public TemplateController(TasklistService tasklistService) {
        this.tasklistService = tasklistService;
    }

    @GetMapping("login")
    public String getLoginView(WebRequest request, Model model) {
        User userDto = new User();
        model.addAttribute("user", userDto);
        return "login";
    }

    @GetMapping("index")
    public ModelAndView getIndex(@AuthenticationPrincipal User user) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("tasklists", tasklistService.getUserTasklists(user.getId()));
        return modelAndView;
    }
}
