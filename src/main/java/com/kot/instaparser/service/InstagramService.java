package com.kot.instaparser.service;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Scanner;

@Service
public class InstagramService {

	@Autowired private LoginService loginService;

	private Instagram4j instagram;
	private final String userParser = "andriy5_kot";
	public static Scanner scanner = new Scanner(System.in);

	public void initInstaService() throws IOException {
		Instagram4j instagram = loginService.doLogin();
	}

	public void getUserInfo() throws IOException {
		InstagramSearchUsernameResult usernameResult = instagram.sendRequest(
				new InstagramSearchUsernameRequest(userParser));
		System.out.println(usernameResult.getUser().full_name);
	}




}
