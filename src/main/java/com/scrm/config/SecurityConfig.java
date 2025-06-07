package com.scrm.config;

import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scrm.services.impl.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    //user create and login using java code with in memory service
    // @Bean
    // public UserDetailsService userDetailsService() {

    //     UserDetails user1 = User
    //     .withDefaultPasswordEncoder()//*This is only for testing not for protection */
    //     .username("admin123")
    //     .password("admin123")
    //     .roles("ADMIN","USER")
    //     .build();

    //     UserDetails user2 = User
    //     .withDefaultPasswordEncoder()
    //     .username("user123")
    //     .password("password")
    //     // .roles("ADMIN","USER")
    //     .build();


    //     var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1, user2);
    //     return inMemoryUserDetailsManager;
    // }
    
    @Autowired
    private SecurityCustomUserDetailService userDetailService;
    
    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    @Autowired
    private AuthFailureHandler authFailureHandler;
    //configuration of authentication providers spring security
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //user detail service ka object:
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        //password encoder ka object:
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {   
        
         // configuration
         // urls ko configuration kiya hai or kyon se public rahge  or kyon se private rahge
         httpSecurity.authorizeHttpRequests(authorize->{
            // authorize.requestMatchers("/home","/register","/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
         });
          //form default login
          //agar hame kuchh bhi change karna hua to ham yaha ayenge :form login se related 
         httpSecurity.formLogin(formLogin->{
            //
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
            // formLogin.failureForwardUrl("/login?error=true");
            //formLogin.defaultSuccessUrl("/home");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

        //     formLogin.failureHandler(new AuthenticationFailureHandler() {

        //         @Override
        //         public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        //                 AuthenticationException exception) throws IOException, ServletException {
        //             // TODO Auto-generated method stub
        //             throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
        //         }
                
        //     });

        //     formLogin.successHandler(new AuthenticationSuccessHandler() {

        //         @Override
        //         public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        //                 Authentication authentication) throws IOException, ServletException {
        //             // TODO Auto-generated method stub
        //             throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
        //         }
                
        //     });

            formLogin.failureHandler(authFailureHandler);
        });
        
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
       

        //oauth2 configurations

        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });

         httpSecurity.logout(logoutForm->{
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        return httpSecurity.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}