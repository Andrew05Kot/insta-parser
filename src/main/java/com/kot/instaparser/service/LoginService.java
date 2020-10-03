package com.kot.instaparser.service;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Scanner;

@Service
public class LoginService {

	private String username;
	private String password;

	public static Scanner scanner = new Scanner(System.in);

	public Instagram4j doLogin() {
		try {
			input();
			Instagram4j instagram = Instagram4j.builder().username(this.username).password(this.password).build();
			instagram.setup();
			instagram.login();
			return instagram;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void input() {
		System.out.println("username: ");
		this.username = scanner.nextLine();
		System.out.println("password: ");
		this.password = scanner.nextLine();
	}
}
