package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class DoctorLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            onBackPressed();
        });

        findViewById(R.id.tv_forgot_password).setOnClickListener(v -> {
            startActivity(new Intent(DoctorLoginActivity.this, ForgotPasswordActivity.class));
        });

        findViewById(R.id.btn_login).setOnClickListener(v -> {
            // Save role in session
            SessionManager session = new SessionManager(this);
            session.setRole("doctor");

            Intent intent = new Intent(DoctorLoginActivity.this, VerificationActivity.class);
            intent.putExtra("role", "doctor");
            startActivity(intent);
        });

        findViewById(R.id.tv_signup).setOnClickListener(v -> {
            startActivity(new Intent(DoctorLoginActivity.this, SignupActivity.class));
        });
    }
}