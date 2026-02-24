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

        // Notification Bell Navigation
        findViewById(R.id.card_notifications).setOnClickListener(v -> {
            startActivity(new Intent(this, AlertsActivity.class));
        });

        // Quick Actions Navigation
        findViewById(R.id.card_add_patient).setOnClickListener(v -> {
            startActivity(new Intent(this, AddNewPatientActivity.class));
        });

        findViewById(R.id.card_enter_vitals).setOnClickListener(v -> {
            startActivity(new Intent(this, VitalsActivity.class));
        });

        findViewById(R.id.card_symptoms).setOnClickListener(v -> {
            startActivity(new Intent(this, CurrentSymptomsActivity.class));
        });

        findViewById(R.id.card_enter_abg).setOnClickListener(v -> {
            startActivity(new Intent(this, ABGEntryActivity.class));
        });

        // Reassessment Due Cards Navigation
        MaterialCardView spo2Card = findViewById(R.id.card_spo2_due);
        MaterialCardView abgCard = findViewById(R.id.card_abg_due);

        if (spo2Card != null) {
            spo2Card.setOnClickListener(v -> {
                startActivity(new Intent(this, ReassessmentChecklistActivity.class));
            });
        }

        if (abgCard != null) {
            abgCard.setOnClickListener(v -> {
                startActivity(new Intent(this, ReassessmentChecklistActivity.class));
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