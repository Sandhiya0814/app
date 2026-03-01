package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class SignupActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etPassword, etConfirmPassword;
    private Spinner spinnerRole;
    private MaterialButton btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize standard EditTexts to match XML
        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        spinnerRole = findViewById(R.id.spinner_role);
        btnSignup = findViewById(R.id.btn_signup);

        // Setup Role Spinner
        String[] roles = {"Select Role", "Doctor", "Clinical Staff", "Admin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.tv_login_link).setOnClickListener(v -> finish());

        btnSignup.setOnClickListener(v -> {
            if (validateInputs()) {
                // Mock success
                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private boolean validateInputs() {
        String name = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String role = spinnerRole.getSelectedItem().toString();

        if (name.isEmpty()) {
            etFullName.setError("Name is required");
            etFullName.requestFocus();
            return false;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            etEmail.requestFocus();
            return false;
        }
        if (role.equals("Select Role")) {
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty() || password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
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