package com.example.workmanagerandfirebasetesting.Model;

public class Users {

    private String userName;
    private String userEmail;
    private String userPassword;
    private String userId;
    private String token;

    public Users() {
    }

    public Users(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public Users(String userName, String userEmail, String userPassword, String userId, String token) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userId = userId;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
