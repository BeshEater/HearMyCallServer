package com.besheater;

public class ResponseWithMessages {
	private final String version = "1.0";
	private ChatMessage[] messages;
	
	public ResponseWithMessages(ChatMessage[] messages) {
		this.messages = messages;
	}

}
