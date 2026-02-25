package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DoctorAlertsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_alerts);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        // Actions for Robert Chen (Critical)
        findViewById(R.id.btnReview1).setOnClickListener(v -> {
            // Logic to mark as reviewed
        });

        findViewById(R.id.btnViewPatient1).setOnClickListener(v -> {
            startActivity(new Intent(this, PatientDetailsActivity.class));
        });

        findViewById(R.id.btnAcceptAI).setOnClickListener(v -> {
            // Logic to accept AI recommendation
        });

        findViewById(R.id.btnOverrideAI).setOnClickListener(v -> {
            // Logic to override AI recommendation
        });

        // Actions for Maria Garcia (Moderate)
        findViewById(R.id.btnViewResults).setOnClickListener(v -> {
            startActivity(new Intent(this, ABGTrendsActivity.class));
        });

        setupBottomNav();
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_alerts);
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
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                finish();
                return true;
            }
            return itemId == R.id.nav_alerts;
        });
    }
}