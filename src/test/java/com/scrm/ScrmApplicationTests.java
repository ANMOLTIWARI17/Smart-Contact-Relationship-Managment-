package com.scrm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scrm.services.EmailService;

@SpringBootTest
class ScrmApplicationTests {

	@Test
	void contextLoads() {
	}
    
	@Autowired
	private EmailService emailService;

	@Test
	void sendEmailTest(){
		emailService.sendEmail("2k23.mca2313040@gmail.com", "testing", "this is for project");
	}

}
