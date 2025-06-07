
package com.scrm.controllers;

// import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scrm.entities.Contact;
import com.scrm.entities.User;
import com.scrm.forms.ContactForm;
import com.scrm.forms.ContactSearchForm;
import com.scrm.helpers.AppConstants;
import com.scrm.helpers.Helper;
import com.scrm.helpers.Message;
import com.scrm.helpers.MessageType;
import com.scrm.services.ContactService;
import com.scrm.services.ImageService;
import com.scrm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private UserService userService;
     
    @Autowired
    private ImageService imageService;

    @RequestMapping("/add")
    //add contact page handler
    public String addContactView(Model model)
    {
        ContactForm contactForm=new ContactForm();
        // contactForm.setName("Abhay Yadav");
        // contactForm.setFavorite(true);
        model.addAttribute("contactForm",contactForm);
        return "user/add_contact";
    }
    
    @RequestMapping(value = "/add",method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult bindingResult,Authentication authentication, HttpSession session){
        // process the form data
        String  username = Helper.getEmailOfLoggedInUser(authentication);
        

        //validate the form data
        if(bindingResult.hasErrors()){
            session.setAttribute("message",Message.builder()
            .content("please correct the following errors")
            .type(MessageType.red)
            .build());
            return "user/add_contact";
        }

        //form -> contact
        User user=userService.getUserByEmail(username);
        Contact contact=new Contact();

        //process the contact picture

        //image process

        // for upload code

     



        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setEmail(contactForm.getEmail());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedinLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());


        if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()){
            String filename=UUID.randomUUID().toString();
            String fileURL=imageService.uploadImage(contactForm.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);
        }
        //set the contact picture url


        //redirect with success message
        
        contactService.save(contact);

        System.out.println(contactForm);

        session.setAttribute("message", Message.builder()
            .content("You have successfully added a new contact")
            .type(MessageType.green)
            .build());
        return "redirect:/user/contacts/add";
    }

    //view contacts 
    
    @RequestMapping
    public String viewContacts(
    @RequestParam(value = "page",defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
    @RequestParam(value = "sortBy", defaultValue="name") String sortBy,
    @RequestParam(value = "direction", defaultValue = "asc") String direction
    ,Model model,Authentication authentication){
        //load all the contacts
        String username=Helper.getEmailOfLoggedInUser(authentication);

        User user=userService.getUserByEmail(username);

        Page<Contact> pageContact=contactService.getByUser(user, page, size, sortBy, direction);
        
        
        model.addAttribute("pageContact",pageContact );
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        model.addAttribute("contactSearchForm", new ContactSearchForm());
     
        return "user/contacts";
    }

    //search handler

    @RequestMapping("/search")    
    public String searchHandler(
        @ModelAttribute ContactSearchForm contactSearchForm,
        @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
        @RequestParam(value = "page",defaultValue = "0") int page,
        @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
        @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model, Authentication authentication
    ){
        logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());
        
        var user=userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact=null;
        if(contactSearchForm.getField().equalsIgnoreCase("name")){
            pageContact=contactService.searchByName(contactSearchForm.getValue(),size, page, sortBy, direction, user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("email")){
            pageContact=contactService.searchByEmail(contactSearchForm.getValue(),size, page, sortBy, direction, user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("phone")){
            pageContact=contactService.searchByPhoneNumber(contactSearchForm.getValue(),size, page, sortBy, direction, user);
        }


        logger.info("pageContact {}",pageContact);

        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/search";
    }


    //delete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
        @PathVariable("contactId") String contactId,
        HttpSession session
        )
    {
        contactService.delete(contactId);
        logger.info("contactId {} deleted", contactId);
        session.setAttribute("message", Message.builder()
        .content("Contact is deleted Successfully")
        .type(MessageType.green)
        .build());
       return "redirect:/user/contacts";
    }

    //updating the contacts
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(
        @PathVariable("contactId") String contactId, 
        Model model
    ){

        var contact=contactService.getById(contactId);
        ContactForm contactForm=new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedinLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);
        return  "user/update_contact_view";


    }

    @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    public String  updateContact(@PathVariable("contactId") String contactId,@Valid @ModelAttribute ContactForm contactForm, 
    BindingResult bindingResult,
    Model model){

        //update the contact
        if (bindingResult.hasErrors()){
            return "user/update_contact_view";
        }




        var con=contactService.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setLinkedInLink(contactForm.getLinkedinLink());
        // con.setPicture(contactForm.getPicture());
        //process image
        if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()){
            logger.info("file is not empty");
            String fileName=UUID.randomUUID().toString();
            String imageUrl=imageService.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);
        }
        else
        {
            logger.info("file is empty");
        }

        var updatedCon=contactService.update(con);
        logger.info("updated contact{}", updatedCon);
        model.addAttribute("message", Message.builder().content("Contact Updated").type(MessageType.green).build());
        return "redirect:/user/contacts/view/"+contactId;
    }

}
