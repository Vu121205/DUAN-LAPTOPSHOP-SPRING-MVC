package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.controller.admin;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.User;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Role;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service.UploadService;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class UserController {
    
    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;  

    public UserController(UserService userService, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
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

    @GetMapping("/admin/user/create")//GET
    public String getCreateUserPage(Model model)
    { 
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUserPage(Model model, @ModelAttribute("newUser") @Valid User vudoan, BindingResult newUserbindingResult, @RequestParam("vudoanFile") MultipartFile file )////////////
    {
        //validate
        List<FieldError> errors = newUserbindingResult.getFieldErrors();
        for(FieldError error : errors)
        {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        if(newUserbindingResult.hasErrors())
        {
            return "/admin/user/create";
        }

        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(vudoan.getPassword());
 
        vudoan.setAvarta(avatar);
        vudoan.setPassword(hashPassword);
        vudoan.setRole(this.userService.getRoleByName(vudoan.getRole().getName()));

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
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User vudoan, @RequestParam("vudoanFile") MultipartFile file)
    {
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");      
        User currentUser = this.userService.getUserById(vudoan.getId());
        if(currentUser != null){
              
            currentUser.setPhone(vudoan.getPhone());
            currentUser.setFullName(vudoan.getFullName());
            currentUser.setAddress(vudoan.getAddress());
            currentUser.setAvarta(avatar);

            Role role = this.userService.getRoleByName(vudoan.getRole().getName());
            currentUser.setRole(role);

            this.userService.handleSaveUser(currentUser);
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

