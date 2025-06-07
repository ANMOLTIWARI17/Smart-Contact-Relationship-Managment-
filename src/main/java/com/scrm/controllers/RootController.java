package com.scrm.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scrm.entities.User;
import com.scrm.helpers.Helper;
import com.scrm.services.UserService;

@ControllerAdvice
public class RootController {
    private Logger logger=org.slf4j.LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    
    @ModelAttribute
    public void addLoggedInUserInformation(Model model,Authentication authentication){
   
        if(authentication==null)
        {
            return;
        }
    System.out.println("Adding logged in ");
    String username= Helper.getEmailOfLoggedInUser(authentication);
      

    logger.info("User Logged in: {}",username);
    User user=userService.getUserByEmail(username);
    System.out.println(user);
         System.out.println(user.getName());
         System.out.println(user.getEmail());
         model.addAttribute("loggedInUser", user);
   
}
}
