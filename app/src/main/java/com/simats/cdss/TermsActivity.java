package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TermsActivity extends AppCompatActivity {

    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        userRole = getIntent().getStringExtra("role");

        // Fallback: get role from SessionManager if not passed via intent
        if (userRole == null || userRole.isEmpty()) {
            SessionManager session = new SessionManager(this);
            userRole = session.getRole();
        }

        CheckBox cbAgree = findViewById(R.id.cb_agree);
        Button btnAccept = findViewById(R.id.btn_accept);

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            onBackPressed();
        });

        cbAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnAccept.setEnabled(isChecked);
        });

        btnAccept.setOnClickListener(v -> {
            // User accepted Terms & Conditions
            // Navigate to the appropriate Dashboard based on role
            Toast.makeText(this, "Terms accepted", Toast.LENGTH_SHORT).show();

            Intent intent;
            if ("staff".equals(userRole)) {
                // Staff → StaffDashboardActivity
                intent = new Intent(TermsActivity.this, StaffDashboardActivity.class);
            } else {
                // Doctor → DoctorDashboardActivity
                intent = new Intent(TermsActivity.this, DoctordashboardActivity.class);
            }

            // Clear back stack so user can't go back to Terms/OTP screens
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        // Prevent going back to OTP screen after verification
        // User must accept terms or close the app
        Toast.makeText(this, "Please accept the terms to continue", Toast.LENGTH_SHORT).show();
    }
}