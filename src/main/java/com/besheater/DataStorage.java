package com.besheater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DataStorage {

	private final Map<String, User> users = new HashMap<>();
	private final Map<String, Integer> usersId = new HashMap<>();
	private final Map<String, Chat> chats = new HashMap<>();
	public static final String DATA_STORAGE = "dataStorage";
	private final long USER_LIFE_TIME = 10000; // ms
	private static int IDpointer = 1;
	
	public DataStorage() {
		addMockUsers();
	}
	
	private void addMockUsers() {
		// add mock users data
		String uniqId1 = getNewUserUniqId();
		String uniqId2 = getNewUserUniqId();
		String uniqId3 = getNewUserUniqId();
		
		users.put(uniqId1,new User(getUserId(uniqId1), "Omar", 53.211615, 63.629681, 9, null, null, new Date().getTime()));
		users.put(uniqId2, new User(getUserId(uniqId2), "Alex", 53.212434, 63.630132, 2, "You wanna see it... or not", null, new Date().getTime()));
		users.put(uniqId3, new User(getUserId(uniqId3), "Vasya", 53.211843, 63.632100, 6, null, new int[] {getUserId(uniqId2)}, new Date().getTime()));
		
		Chat chat = getChat(uniqId2);
		chat.addMessage(new ChatMessage(getUser(uniqId2), "First message in this test chat", new Date().getTime()));
		chat.addMessage(new ChatMessage(getUser(uniqId2), "Secong message in this test chat", new Date().getTime()));
		chat.addMessage(new ChatMessage(getUser(uniqId3), "It's not gonna end", new Date().getTime()));
		chats.put(uniqId3, chat);
		
	}
	
	public static synchronized int getNextId() {
		IDpointer++;
		return IDpointer;
	}
	
	public synchronized boolean addUser(String uniqId, User user) {
		if (users.containsKey(uniqId) && isUserValid(user)) {
			user = correctUser(uniqId, user);
			users.put(uniqId, user);
			// check if need to clear chat
			if (user.callMessage == null) {
				chats.get(uniqId).clear();;
			}
			return true;
		}
		return false;
	}
	
	public synchronized boolean isUserValid(String uniqId, User user) {
		if (users.containsKey(uniqId) && isUserValid(user)) {
			return true;
		}
		return false;
	}
	
	public synchronized User getUser(String uniqId) {
		return users.get(uniqId);
	}
	
	public synchronized Chat getChat(String uniqId) {
		return chats.get(uniqId);
	}
	
	public synchronized String getNewUserUniqId() {
		String uniqId = UUID.randomUUID().toString();
		int id = getNextId();
		// Create holders
		users.put(uniqId, null);
		chats.put(uniqId, new Chat(uniqId));
		usersId.put(uniqId, id);
		
		return uniqId;
	}
	
	public synchronized String getUserUniqId(User user) {
		String userUniqId = null;
		Integer userId = user.id;
		List<String> allUniqId = new ArrayList<>(usersId.keySet());
		List<Integer> allId = new ArrayList<>(usersId.values());
		
		for (int i = 0; i < allId.size(); i++) {
			if (allId.get(i) == userId) {
				userUniqId = allUniqId.get(i);
				break;
			}
		}
		
		return userUniqId;
	}
	
	public synchronized String getUserUniqId(int id) {
		String userUniqId = null;
		Integer userId = id;
		List<String> allUniqId = new ArrayList<>(usersId.keySet());
		List<Integer> allId = new ArrayList<>(usersId.values());
		
		for (int i = 0; i < allId.size(); i++) {
			if (allId.get(i) == userId) {
				userUniqId = allUniqId.get(i);
				break;
			}
		}
		
		return userUniqId;
	}
	
	public synchronized int getUserId(String uniqId) {
		Integer id = usersId.get(uniqId);
		if (id == null) {
			id = -1;
		}
		return id;
	}
	
	public synchronized User[] getUsers() {
		List<User> usersList = new ArrayList<>();
		List<String> allUniqId = new ArrayList<>(usersId.keySet());
		for (String uniqId : allUniqId) {
			User user = users.get(uniqId);
			if (user != null) {
				usersList.add(user);
			}
		}
		User[] usersArr = new User[usersList.size()];
		usersArr = usersList.toArray(usersArr);
		return usersArr;
	}
	
	private User correctUser(String uniqId, User user) {
		return new User(
				getUserId(uniqId),
				user.name,
				user.latitude,
				user.longitude,
				user.avatarImageNum,
				user.callMessage,
				user.connectedUsersId,
				new Date().getTime()
				);
	}
	
	public synchronized int deleteOldUsers() {
		int numOfDeletedUsers = 0;
		long currTime = new Date().getTime();
		List<String> usersUniqId = new ArrayList<>(usersId.keySet());
		for (String userUniqId : usersUniqId) {
			User user = users.get(userUniqId);
			if (isOld(user)) {
				deleteUser(userUniqId);
				numOfDeletedUsers++;
			}
		}
		// Add mock users
		addMockUsers();
		return numOfDeletedUsers;
	}
	
	private boolean isOld(User user) {
		if (user == null) {
			return true;
		}
		long currTime = new Date().getTime();
		long userLastTime = user.time;
		
		if (currTime - userLastTime > USER_LIFE_TIME) {
			return true;
		}
		return false;
		
	}
	
	private void deleteUser(String uniqId) {
		users.remove(uniqId);
		usersId.remove(uniqId);
		chats.remove(uniqId);
	}
	
	private boolean isUserValid(User user) {
		// Quick check for object existence
		if (user == null) {
			return false;
		}
		// Check id
		// Doesn't need to check
		
		// Check name
		if (user.name == null || user.name.equals("")) {
			return false;
		}
		// Check latitude
		if (user.latitude > 90.0 && user.latitude < -90.0) {
			return false;
		}
		// Check longitude
		if (user.longitude > 180.0 && user.longitude < -180.0) {
			return false;
		}
		// Check avatarImageNum
		if (user.avatarImageNum < 0 && user.avatarImageNum > 20) {
			return false;
		}
		// Check callMessage
		if (user.callMessage != null && (
				user.callMessage.equals("") ||
				user.connectedUsersId == null ||
				user.connectedUsersId.length == 0 ||
				user.connectedUsersId[0] != user.id) 
				) {
			return false;
		}
		
		return true;
	}
}
