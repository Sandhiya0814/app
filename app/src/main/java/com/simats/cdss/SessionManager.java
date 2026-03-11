package com.simats.cdss;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "CDSS_Session";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_ROLE = "user_role";
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