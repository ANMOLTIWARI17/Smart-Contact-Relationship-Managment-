package com.scrm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
@Entity(name="user")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails{
    @Id
    private String userId;
    @Column(name="user_name",nullable=false)
    private String name;
    @Column(unique=true, nullable=false)
    private String email;
    @Getter(value = AccessLevel.NONE)
    private String password;
    @Column(length=1000)
    private String about;
    @Column(length=1000)
    private String profilePic;
    private String phoneNumber;

    @Getter(value=AccessLevel.NONE)
    // information
    private boolean enabled=true;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;
    @Enumerated(value = EnumType.STRING)
    // SELF, GOOGLE, FACEBOOK, TWITTER, LINKEDIN, GITHUB
    private Providers provider=Providers.SELF;
    private String providerUserId;
    //add more fields if needed
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL , fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts=new ArrayList<>();
     
    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> roleList=new ArrayList<>();
    
    // private String emailToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //list of roles[User, Admin]
        //collection of SimpleGrantedAuthority[roles{Admin, User}]
        Collection<SimpleGrantedAuthority> roles=roleList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

        return roles;
    }

    //For this project:
    //email id hai wahi hamari username
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
    
    @Override
    public String getPassword() {
        return this.password;
    }

    
}
