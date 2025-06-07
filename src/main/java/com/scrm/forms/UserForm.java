package com.scrm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
    @NotBlank(message = "Name is required")
    @Size(min=3,message = "Name must be between 3 and 50 characters")
    private String name;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    // @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min=6,message = "Password must be between 6 and 50 characters")
    private String password;
    @NotBlank(message = "About is required")
    private String about;
    @Size(min=8,max=12,message = "Invalid phone number")
    private String phoneNumber;
}
