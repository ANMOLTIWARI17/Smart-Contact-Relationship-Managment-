package com.scrm.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scrm.entities.User;
import com.scrm.forms.UserForm;
import com.scrm.helpers.Message;
import com.scrm.helpers.MessageType;
import com.scrm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class Pagecontroller {
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }
    @RequestMapping("/home")
   public String home(Model model)
   {    System.out.println("Home page handler");
   // sending dynamic data to home.html page
   model.addAttribute("name","Ayush srivastava");
   model.addAttribute("project","Team members");
   model.addAttribute("PSIT","https://erp.psit.ac.in/");
        return "home";
   }
// about route
@RequestMapping("/about")
public String aboutPage()
{    System.out.println("About page loading");
     return "about";
}
// services route
@RequestMapping("/services")
public String servicesPage()
{    System.out.println("services  page loading");
     return "services";
}
// contact page
@GetMapping("/contact")
public String contact() {
    return new String("contact");
}


//this is login page
//Login
@GetMapping("/login")
public String login() {
    return new String("login");
}


//registration page
//Signup
@GetMapping("/register")
public String register(Model model) {
    UserForm userForm=new UserForm();
    // default data bhi fill kar sakte hain
    // userForm.setName("Ayush");
    // userForm.setAbout("This is about : write something about yourself");
    model.addAttribute("userForm", userForm);
    // return new String("register");
    return "register";
}

// Processing register 
@RequestMapping(value="/do-register", method=RequestMethod.POST)
public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult,  HttpSession session)
{
    System.out.println("Processing");
    // fetch form data
    // User Form 
    System.out.println(userForm);
    // validate form data

    if(rBindingResult.hasErrors())
    {
        return "register";
    }
    // TODO:: Validate userForm[next Video]
    // save to database
    // userservice
    // UserForm-> User
    // User user=User.builder()
    // .name(userForm.getName())
    // .email(userForm.getEmail())
    // .password(userForm.getPassword())
    // .about(userForm.getAbout())
    // .phoneNumber(userForm.getPhoneNumber())
    // .profilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Ffacultytick.com%2F2022%2F03%2F30%2Fpranveer-singh-institute-of-technology-applications-are-invited-from-eligible-candidates-for-the-following-post-of-teaching-faculty-recruitment%2F&psig=AOvVaw1MrKpLWXfhs5EOq0Sopwv7&ust=1719651532448000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCKDWoqj3_YYDFQAAAAAdAAAAABAE")
    // .build();
    //We can add more columns in the future if needed
    User user=new User();
    user.setName(userForm.getName());
    user.setEmail(userForm.getEmail());
    user.setPassword(userForm.getPassword());
    user.setAbout(userForm.getAbout());
    user.setPhoneNumber(userForm.getPhoneNumber());
    // user.setEnabled(false);
    user.setProfilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Ffacultytick.com%2F2022%2F03%2F30%2Fpranveer-singh-institute-of-technology-applications-are-invited-from-eligible-candidates-for-the-following-post-of-teaching-faculty-recruitment%2F&psig=AOvVaw1MrKpLWXfhs5EOq0Sopwv7&ust=1719651532448000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCKDWoqj3_YYDFQAAAAAdAAAAABAE");
    User savedUser=userService.saveUser(user);
    System.out.println("user saved:");
    // message="Registration Successful"

    // add the message

    Message message=Message.builder().content("Registration Successful").type(MessageType.green).build();
    session.setAttribute("message", message);

    // redirect to login page
    return "redirect:/register";
}

}

// 17:17