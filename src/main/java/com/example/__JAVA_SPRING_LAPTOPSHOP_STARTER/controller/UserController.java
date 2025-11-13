package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.User;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service.UserService;

@Controller
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService; 
    }

    @RequestMapping("/")
    public String getHomePage(Model model)
    {
        List<User> arrUsers = this.userService.getAllUsersbyEmail("1dasd@gmail.com");
        System.out.println(arrUsers);

        model.addAttribute("vudoan", "test");
        model.addAttribute("vudoan1", "vudoan1 from controller");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model)
    {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/create")//GET
    public String getCreateUserPage(Model model)
    { 
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User vudoan)
    {
        this.userService.handleSaveUser(vudoan);
        return "redirect:/admin/user";
    }
}

