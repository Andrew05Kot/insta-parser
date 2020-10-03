package com.kot.instaparser;

import com.kot.instaparser.service.InstagramService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class InstaParserApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(InstaParserApplication.class, args);
		InstagramService instagramService = new InstagramService();
		instagramService.initInstaService();
		instagramService.getUserInfo();
	}

}
