package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        findViewById(R.id.btn_login).setOnClickListener(v -> {
            // Save role in session
            SessionManager session = new SessionManager(this);
            session.setRole("admin");

            // Navigate to Admin Dashboard
            Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.tv_signup).setOnClickListener(v -> {
            startActivity(new Intent(this, SignupActivity.class));
        });
    }
}