package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.User;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;



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
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id)
    {
        User user = this.userService.getUserById(id);

        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/detail";
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

    @RequestMapping("/admin/user/update/{id}") //GET
    public String getUpdateUserPage(Model model, @PathVariable long id)
    {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User vudoan)
    {
        User currentUser = this.userService.getUserById(vudoan.getId());
        if(currentUser != null){
            currentUser.setAddress(vudoan.getAddress());
            currentUser.setFullName(vudoan.getFullName());
            currentUser.setPhone(vudoan.getPhone());
            
            this.userService.handleSaveUser(vudoan);
        }
        //redirect sẽ chuyển hướng trình duyệt sang /admin/user
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        // User user = new User();
        // user.setId(id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete"; 
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User vudoan) {
        this.userService.DeleteAUser(vudoan.getId());
        return "redirect:/admin/user"; 
    }
    
}

