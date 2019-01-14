package com.besheater;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chat {
	private final String uniqId;
	private List<ChatMessage> messageList = new ArrayList<>();
	
	public Chat (String uniqId) {
		this.uniqId = uniqId;
	}
	
	public void addMessage(ChatMessage message) {
		messageList.add(correctMessageTime(message));
	}
	
	public ChatMessage[] getMessages() {
		ChatMessage[] messages = new ChatMessage[messageList.size()];
		messages = messageList.toArray(messages);
		return messages;
	}
	
	public String getUniqId() {
		return uniqId;
	}
	
	private ChatMessage correctMessageTime(ChatMessage message) {
		return new ChatMessage(message.user, message.text, new Date().getTime());
	}
	
	public void clear() {
		messageList.clear();
	}
	public int size() {
		return messageList.size();
	}
}
