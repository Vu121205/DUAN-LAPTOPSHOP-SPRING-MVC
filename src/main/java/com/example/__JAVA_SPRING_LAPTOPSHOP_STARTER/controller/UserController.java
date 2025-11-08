package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.User;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service.UserService;


@Controller
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model)
    {
        String test = this.userService.handleHello();
        model.addAttribute("vudoan", test);
        model.addAttribute("vudoan1", "vudoan1 from controller");
        return "hello";
    }

    @RequestMapping("/admin/user")//GET
    public String getUserPage(Model model)
    {
        String test = this.userService.handleHello();
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create1", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User vudoan)
    {
        System.err.println("run here"+ vudoan);
        return "hello";
    }
}

