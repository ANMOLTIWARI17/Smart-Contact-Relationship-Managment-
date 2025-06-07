package com.scrm.repsitories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scrm.entities.Contact;
import com.scrm.entities.User;

import java.util.List;


@Repository
public interface ContactRepo extends JpaRepository<Contact, String>{

    //find the contact by user


    //custom finder method
    Page<Contact> findByUser(User user, Pageable pageable);
    
    //custom query method

    @Query("Select c FROM Contact c WHERE c.user.id = :userId")

    List<Contact> findByUserId(@Param("userId")String userId);

    //for search

    Page<Contact>findByUserAndNameContaining(User user,String namekeyword,Pageable pageable);   
    Page<Contact> findByUserAndEmailContaining(User user,String emailkeyword,Pageable pageable);
    Page<Contact> findByUserAndPhoneNumberContaining(User user,String phonenumberkeyword,Pageable pageable);

}
