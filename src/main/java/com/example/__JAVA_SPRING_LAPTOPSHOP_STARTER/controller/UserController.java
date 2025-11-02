package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

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
        model.addAttribute("vudoan1", "from controller");
        return "hello";
    }
}


// @RestController
// public class UserController {
    
//     private UserService userService;

//     public UserController(UserService userService) {
//         this.userService = userService;
//     }

//     @GetMapping("/")
//     public String getHomePage()
//     {
//         return this.userService.handleHello();
//     }
// }
