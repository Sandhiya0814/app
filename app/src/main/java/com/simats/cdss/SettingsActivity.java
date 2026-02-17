package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupBottomNav();

        // Navigation to Profile Information
        findViewById(R.id.card_profile).setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });

        // Navigation to Clinical Guidelines
        findViewById(R.id.card_guidelines).setOnClickListener(v -> {
            startActivity(new Intent(this, ClinicalGuidelinesActivity.class));
        });

        // Navigation to Notifications
        findViewById(R.id.card_notifications).setOnClickListener(v -> {
            startActivity(new Intent(this, NotificationsActivity.class));
        });

        // Navigation to Help & Support
        findViewById(R.id.card_help).setOnClickListener(v -> {
            startActivity(new Intent(this, HelpSupportActivity.class));
        });

        findViewById(R.id.btn_logout).setOnClickListener(v -> {
            // Navigate back to Role selection or Login screen
            Intent intent = new Intent(this, RoleActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_settings);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(this, DoctordashboardActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_patients) {
                startActivity(new Intent(this, PatientListActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_alerts) {
                startActivity(new Intent(this, AlertsActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                finish();
                return true;
            }
            return itemId == R.id.nav_settings;
        });
    }
}