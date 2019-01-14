package com.besheater;

import java.util.Date;

public class ChatMessage {
	public final User user;
	public final String text;
	public final long time;
	
	public ChatMessage(User user, String text, long time) {
		this.user = user;
		this.text = text;
		this.time = time;
	}

}
