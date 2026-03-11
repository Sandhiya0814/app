package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etNewPassword, etConfirmPassword;
    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        userRole = getIntent().getStringExtra("role");

        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        findViewById(R.id.btn_reset_password).setOnClickListener(v -> {
            if (validatePassword()) {
                Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                
                // Navigate back to the specific login screen based on role
                Intent intent;
                if ("staff".equals(userRole)) {
                    intent = new Intent(this, StaffLoginActivity.class);
                } else if ("admin".equals(userRole)) {
                    intent = new Intent(this, AdminLoginActivity.class);
                } else {
                    intent = new Intent(this, DoctorLoginActivity.class);
                }
                
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validatePassword() {
        String password = etNewPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (password.length() < 8) {
            etNewPassword.setError("Minimum 8 characters required");
            etNewPassword.requestFocus();
            return false;
        }

        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            etNewPassword.setError("At least 1 uppercase letter required");
            etNewPassword.requestFocus();
            return false;
        }

        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            etNewPassword.setError("At least 1 lowercase letter required");
            etNewPassword.requestFocus();
            return false;
        }

        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            etNewPassword.setError("At least 1 digit required");
            etNewPassword.requestFocus();
            return false;
        }

        if (!Pattern.compile("[!@#$%^&*(),.?\":{}|<>_]").matcher(password).find()) {
            etNewPassword.setError("At least 1 special character required");
            etNewPassword.requestFocus();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
}