package com.kot.instaparser.service;

import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowingRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class InstagramService {

	InstagramSearchUsernameResult usernameResult = new InstagramSearchUsernameResult();
	private LoginService loginService = new LoginService();
	private static final String userParser = "andriy5_kot";

	public void initService() throws IOException {
		this.usernameResult = loginService.doLogin().sendRequest(
				new InstagramSearchUsernameRequest(userParser));
	}

	public void getUserFullName() throws IOException {
		System.out.println(usernameResult.getUser().full_name);
	}

	public void getUserFollowers() throws IOException {
		InstagramGetUserFollowersResult followersRequest = loginService.doLogin().sendRequest(
				new InstagramGetUserFollowersRequest(
						usernameResult.getUser().getPk()
				)
		);

		for (InstagramUserSummary user: followersRequest.getUsers()){
			System.out.println("username: " + user.username);
		}
	}

	public void getUserReaders() throws IOException {
		try {
			InstagramGetUserFollowersResult followers = loginService.doLogin().sendRequest(
					new InstagramGetUserFollowingRequest(usernameResult.getUser().getPk()));
			for (InstagramUserSummary user : followers.getUsers()) {
				System.out.println(user.username);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}


}
