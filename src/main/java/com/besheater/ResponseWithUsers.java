package com.besheater;

public class ResponseWithUsers {
	private final String version = "1.0";
	private int yourId;
	private User[] users;
	
	public ResponseWithUsers(int yourId, User[] users) {
		this.yourId = yourId;
		this.users = users;
	}

}
