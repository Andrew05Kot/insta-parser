package com.kot.instaparser.service;

import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowingRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

	public void getCommonFollowers(String firstAccount, String secondAccount) throws IOException {
		List<String> firstFollowers = new ArrayList<>();
		List<String> secondFollowers = new ArrayList<>();
		FileWriter writer1 = new FileWriter("firstFollowers.txt");
		FileWriter writer2 = new FileWriter("secondFollowers.txt");

		InstagramSearchUsernameResult cheResult = loginService.doLogin().sendRequest(
				new InstagramSearchUsernameRequest(firstAccount));

		InstagramSearchUsernameResult karnaResult = loginService.doLogin().sendRequest(
				new InstagramSearchUsernameRequest(secondAccount));

		String nextMaxId = null;
		while (true) {
			InstagramGetUserFollowersResult fr = loginService.doLogin()
					.sendRequest(new InstagramGetUserFollowersRequest(cheResult.getUser().getPk(), nextMaxId));
			for(InstagramUserSummary user: fr.getUsers()){
				firstFollowers.add(user.username);
				writer1.write(user.username + System.lineSeparator());
			}
			nextMaxId = fr.getNext_max_id();
			if (nextMaxId == null) {
				break;
			}
		}
		writer1.close();

		while (true) {
			InstagramGetUserFollowersResult fr = loginService.doLogin()
					.sendRequest(new InstagramGetUserFollowersRequest(karnaResult.getUser().getPk(), nextMaxId));
			for(InstagramUserSummary user: fr.getUsers()){
				secondFollowers.add(user.username);
				writer2.write(user.username + System.lineSeparator());
			}
			nextMaxId = fr.getNext_max_id();
			if (nextMaxId == null) {
				break;
			}
		}
		writer2.close();

		List<String> commonFollowers = new ArrayList<>();

		firstFollowers.stream()
				.filter(secondFollowers::contains)
				.map(String::valueOf)
				.sorted()
				.forEach(s -> {
					commonFollowers.add(s);
					System.out.println(s);
				});

		FileWriter writer = new FileWriter("output.txt");
		for(Object str: commonFollowers) {
			writer.write(str + System.lineSeparator());
		}

		writer.close();
	}

}
