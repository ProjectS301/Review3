package com.scm.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page Handler");
        model.addAttribute("name", "Substring Technologies");
        model.addAttribute("YoutubeChannel", "It's me Rishabh");
        model.addAttribute("githubRepo", "https://github.com/learncodewithdurgesh/");
        return "home";
    }

    //about route

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isLogin", true);
        System.out.println("About page loading");
        return "about";
    }
    

    //services

    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("services page loading");
        return "services";
    }

    // contact page

    @GetMapping("/contact")
    public String contact() {
        return new String("contact");
    }

    //this is registration controller

    // this is showing login page
    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

    // do registration/process registration
    //registration page
    @GetMapping("/register")
    public String register(Model model) {

        UserForm userForm = new UserForm();
        //default data bhi de sakte hai
        model.addAttribute("userForm", userForm);

        
        return "register";
    }

    //processing register

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session){
        System.out.println("Processing Registration");
        // fetch form data
        // UserForm
        // validate form data

        if(rBindingResult.hasErrors()){
            return "register";
        }

        // save to database

        //userservice

        // UserForm --> User
        // User user=User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.vecteezy.com%2Ffree-vector%2Fdefault-profile-picture&psig=AOvVaw01gw01T6Fw6C2uN5tAo8DP&ust=1730293096913000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMChkKvSs4kDFQAAAAAdAAAAABAJ")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());

        user.setEnabled(true);

        user.setProfilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.vecteezy.com%2Ffree-vector%2Fdefault-profile-picture&psig=AOvVaw01gw01T6Fw6C2uN5tAo8DP&ust=1730293096913000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMChkKvSs4kDFQAAAAAdAAAAABAJ");

        User savedUser = userService.saveUser(user);

        System.out.println("User Saved :");

        // message="Registration Successful"

        //add the message

        Message message = Message.builder().content("Registration Succesful").type(MessageType.green).build();

        session.setAttribute("message", message);

        // redirect to login page
        return "redirect:/register";
    }
}
