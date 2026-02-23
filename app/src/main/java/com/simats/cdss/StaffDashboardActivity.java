package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class StaffDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);

        // Corrected: Single click listener for Add Patient card
        findViewById(R.id.card_add_patient).setOnClickListener(v -> {
            startActivity(new Intent(StaffDashboardActivity.this, AddNewPatientActivity.class));
        });

        // Find the cards by their unique IDs
        MaterialCardView spo2Card = findViewById(R.id.card_spo2_due);
        MaterialCardView abgCard = findViewById(R.id.card_abg_due);

        // Navigation to ReassessmentChecklistActivity
        if (spo2Card != null) {
            spo2Card.setOnClickListener(v -> {
                Intent intent = new Intent(StaffDashboardActivity.this, ReassessmentChecklistActivity.class);
                startActivity(intent);
            });
        }

        if (abgCard != null) {
            abgCard.setOnClickListener(v -> {
                Intent intent = new Intent(StaffDashboardActivity.this, ReassessmentChecklistActivity.class);
                startActivity(intent);
            });
        }

        setupBottomNav();
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_home);
            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_patients) {
                    startActivity(new Intent(this, PatientListActivity.class));
                    return true;
                } else if (itemId == R.id.nav_alerts) {
                    startActivity(new Intent(this, AlertsActivity.class));
                    return true;
                } else if (itemId == R.id.nav_settings) {
                    startActivity(new Intent(this, SettingsActivity.class));
                    return true;
                }
                return itemId == R.id.nav_home;
            });
        }
    }
}