package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdminProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        findViewById(R.id.iv_back).setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        findViewById(R.id.btn_logout_admin).setOnClickListener(v -> showLogoutConfirmation());
    }

    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout from Admin Panel?")
                .setPositiveButton("Logout", (dialog, which) -> {
                    // Navigate back to Role selection or Login screen
                    Intent intent = new Intent(AdminProfileActivity.this, RoleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}