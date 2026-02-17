package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DoctordashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        // Navigation to Patient List (All)
        findViewById(R.id.card_total_patients).setOnClickListener(v -> {
            startActivity(new Intent(this, PatientListActivity.class));
        });

        findViewById(R.id.card_patient_list).setOnClickListener(v -> {
            startActivity(new Intent(this, PatientListActivity.class));
        });

        findViewById(R.id.tv_view_all).setOnClickListener(v -> {
            startActivity(new Intent(this, PatientListActivity.class));
        });

        // Navigation to Critical Patients
        findViewById(R.id.card_critical_patients).setOnClickListener(v -> {
            Intent intent = new Intent(this, PatientListActivity.class);
            intent.putExtra("filter", "critical");
            startActivity(intent);
        });

        // Navigation to Warning Patients
        findViewById(R.id.card_warning_patients).setOnClickListener(v -> {
            Intent intent = new Intent(this, PatientListActivity.class);
            intent.putExtra("filter", "warning");
            startActivity(intent);
        });

        // Add click listener for Robert Chen card
        findViewById(R.id.home_patient_card_1).setOnClickListener(v -> {
            startActivity(new Intent(this, PatientDetailsActivity.class));
        });

        // Add click listener for Maria Garcia card (linking to same details for now or you can create another)
        findViewById(R.id.home_patient_card_2).setOnClickListener(v -> {
            startActivity(new Intent(this, PatientDetailsActivity.class));
        });

        // Add click listener for the notification bell icon using its ID
        findViewById(R.id.card_notifications).setOnClickListener(v -> {
             startActivity(new Intent(this, AlertsActivity.class));
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
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