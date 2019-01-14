package com.besheater;

public class User {
    public final int id; // public id
    public final String name;
    public final double latitude;
    public final double longitude;
    public final int avatarImageNum;
    public final String callMessage;
    public final int[] connectedUsersId;
    public final long time;

    public User(int id, String name, double latitude, double longitude,
                int avatarImageNum, String callMessage, int[] connectedUsersId, long time) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.avatarImageNum = avatarImageNum;
        this.callMessage = callMessage;
        this.connectedUsersId = connectedUsersId;
        this.time = time;  
    }
}
