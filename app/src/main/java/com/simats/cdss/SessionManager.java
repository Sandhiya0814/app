package com.simats.cdss;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "CDSS_Session";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_ROLE = "user_role";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_IMAGE_URI = "profile_image_uri";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveTokens(String access, String refresh, String role) {
        editor.putString(KEY_ACCESS_TOKEN, access);
        editor.putString(KEY_REFRESH_TOKEN, refresh);
        editor.putString(KEY_ROLE, role);
        editor.apply();
    }

    public void saveEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public void saveName(String name) {
        editor.putString(KEY_NAME, name);
        editor.apply();
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    public String getName() {
        return pref.getString(KEY_NAME, "");
    }

    public void saveProfileImageUri(String uri) {
        editor.putString(KEY_IMAGE_URI, uri);
        editor.apply();
    }

    public String getProfileImageUri() {
        return pref.getString(KEY_IMAGE_URI, null);
    }

    public void setRole(String role) {
        editor.putString(KEY_ROLE, role);
        editor.apply();
    }

    public String getAccessToken() {
        return pref.getString(KEY_ACCESS_TOKEN, null);
    }
    
    public String getRole() {
        return pref.getString(KEY_ROLE, "doctor");
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
