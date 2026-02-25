package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StaffAlertsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_alerts);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        // Staff-specific actions
        findViewById(R.id.btnNotifyDoctor).setOnClickListener(v -> {
            // Logic to notify doctor
        });

        findViewById(R.id.btnViewPatient).setOnClickListener(v -> {
            startActivity(new Intent(this, PatientDetailsActivity.class));
        });

        findViewById(R.id.btnRecheckVitals).setOnClickListener(v -> {
            startActivity(new Intent(this, VitalsActivity.class));
        });

        findViewById(R.id.btnReenterABG).setOnClickListener(v -> {
            startActivity(new Intent(this, ABGEntryActivity.class));
        });

        findViewById(R.id.btnOpenResult).setOnClickListener(v -> {
            startActivity(new Intent(this, ABGTrendsActivity.class));
        });

        setupBottomNav();
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_alerts);
            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(this, StaffDashboardActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.nav_patients) {
                    startActivity(new Intent(this, StaffPatientListActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.nav_settings) {
                    startActivity(new Intent(this, SettingsActivity.class));
                    finish();
                    return true;
                }
                return itemId == R.id.nav_alerts;
            });
        }
    }
}