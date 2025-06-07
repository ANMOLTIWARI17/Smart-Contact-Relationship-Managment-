package com.scrm.controllers;

import java.security.Principal;
import java.util.logging.Logger;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod; // Add this import statement

import com.scrm.entities.User;
import com.scrm.helpers.Helper;
import com.scrm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private org.slf4j.Logger logger=org.slf4j.LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

   
    // user dashboard page
    @RequestMapping(value="/dashboard")
    public String userDashboard() {
        System.out.println("Dashboard");
        return "user/dashboard";
    }
    //user profile page
     @RequestMapping(value="/profile")
    public String userProfile(Model model,Authentication authentication) {
        // String name=principal.getName();

        System.out.println("User profile");
        return "user/profile";
    }
}