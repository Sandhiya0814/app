package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PatientDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        // Handle click on SpO2 gauge card to move to OxygenActivity
        findViewById(R.id.card_gauge).setOnClickListener(v -> {
            startActivity(new Intent(this, OxygenActivity.class));
        });

        // Handle click on "Run AI Risk Analysis" button
        findViewById(R.id.btn_run_ai).setOnClickListener(v -> {
            startActivity(new Intent(this, AIAnalysisActivity.class));
        });

        // Handle click on "View ABG Trends" button
        findViewById(R.id.btn_view_trends).setOnClickListener(v -> {
            startActivity(new Intent(this, ABGTrendsActivity.class));
        });

        // Handle click on "Escalation Criteria" button
        findViewById(R.id.btn_escalation).setOnClickListener(v -> {
            startActivity(new Intent(this, EscalationCriteriaActivity.class));
        });

        setupBottomNav();
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_patients);
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
            return false;
        });
    }
}
