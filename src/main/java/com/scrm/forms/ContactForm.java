package com.scrm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {

    @NotBlank(message = "name is required")
    private String name;
    
    @NotBlank(message = "email is required")
    @Email(message = "Invalid Email Address")
    private String email;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phoneNumber;
    
    @NotBlank(message = "Address is required")

    private String address;

    private String description;

    private boolean favorite;

    private String websiteLink;

    private String linkedinLink;
    

    // we will create annotation to validate the file
    //size
    //resolution
    private MultipartFile contactImage;

    private String picture;

}
