package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ABGTrendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abg_trends);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        setupBottomNav();
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_patients);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            SessionManager session = new SessionManager(this);
            String role = session.getRole();

            if (itemId == R.id.nav_home) {
                if ("staff".equals(role)) {
                    startActivity(new Intent(this, StaffDashboardActivity.class));
                } else {
                    startActivity(new Intent(this, DoctordashboardActivity.class));
                }
                finish();
                return true;
            } else if (itemId == R.id.nav_patients) {
                if ("staff".equals(role)) {
                    startActivity(new Intent(this, StaffPatientListActivity.class));
                } else {
                    startActivity(new Intent(this, PatientListActivity.class));
                }
                finish();
                return true;
            } else if (itemId == R.id.nav_alerts) {
                if ("staff".equals(role)) {
                    startActivity(new Intent(this, StaffAlertsActivity.class));
                } else {
                    startActivity(new Intent(this, DoctorAlertsActivity.class));
                }
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
