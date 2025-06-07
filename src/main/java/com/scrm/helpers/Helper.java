package com.scrm.helpers;

// import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication)
    {

        // AuthenticationPrincipal principal=(AuthenticationPrincipal)authentication.getPrincipal() 
        //if email is password 
        if (authentication instanceof OAuth2AuthenticationToken)
        {
            var aOAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
            var clientid=aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2User=(OAuth2User)authentication.getPrincipal();
            String username="";

            if(clientid.equalsIgnoreCase("google"))
            {
                System.out.println("Getting email from google");
                username=oauth2User.getAttribute("email").toString();
            }
            else if(clientid.equalsIgnoreCase("github"))
            {
                System.out.println("Getting Email Fron Github");
                 username=oauth2User.getAttribute("email")!=null ? oauth2User.getAttribute("email").toString(): oauth2User.getAttribute("login").toString()+"@gmail.com";
            }
            return username;
        }
        else
        {
            System.out.println("Getting data from local database");
            return authentication.getName();
        }



        //sign with github
    }
 
    // public static String getLinkForEmailVerification(String emailToken){

    //     String link="http://localhost:8080/auth/verify-email?token="+emailToken;

    //     return link;

    // }

}

