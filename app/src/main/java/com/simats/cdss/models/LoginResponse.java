package com.simats.cdss.models;

public class LoginResponse {
    private String access;
    private String refresh;
    private String role;
    private int user_id;

    public String getAccess() { return access; }
    public String getRefresh() { return refresh; }
    public String getRole() { return role; }
    public int getUserId() { return user_id; }
}
