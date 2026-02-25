package com.simats.cdss;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class StaffPatientListActivity extends AppCompatActivity {

    private MaterialCardView btnAll, btnCritical, btnWarning, btnStable;
    private TextView tvAll, tvCritical, tvWarning, tvStable;
    private View cardRobert, cardMaria, cardJames, cardSarah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_patient_list);

        // Initialize Filter Buttons
        btnAll = findViewById(R.id.btn_all);
        btnCritical = findViewById(R.id.btn_critical);
        btnWarning = findViewById(R.id.btn_warning);
        btnStable = findViewById(R.id.btn_stable);

        // Initialize Filter Texts
        tvAll = findViewById(R.id.tv_all);
        tvCritical = findViewById(R.id.tv_critical);
        tvWarning = findViewById(R.id.tv_warning);
        tvStable = findViewById(R.id.tv_stable);

        // Initialize Patient Cards
        cardRobert = findViewById(R.id.patient_card_robert);
        cardMaria = findViewById(R.id.patient_card_maria);
        cardJames = findViewById(R.id.patient_card_james);
        cardSarah = findViewById(R.id.patient_card_sarah);

        // Set click listeners for patient cards to move to Vitals screen
        View.OnClickListener patientClickListener = v -> {
            startActivity(new Intent(StaffPatientListActivity.this, VitalsActivity.class));
        };

        cardRobert.setOnClickListener(patientClickListener);
        cardMaria.setOnClickListener(patientClickListener);
        cardJames.setOnClickListener(patientClickListener);
        cardSarah.setOnClickListener(patientClickListener);

        setupFilters();
        setupBottomNav();
    }

    private void setupFilters() {
        btnAll.setOnClickListener(v -> filterList("all"));
        btnCritical.setOnClickListener(v -> filterList("critical"));
        btnWarning.setOnClickListener(v -> filterList("warning"));
        btnStable.setOnClickListener(v -> filterList("stable"));
    }

    private void filterList(String status) {
        resetButton(btnAll, tvAll);
        resetButton(btnCritical, tvCritical);
        resetButton(btnWarning, tvWarning);
        resetButton(btnStable, tvStable);

        cardRobert.setVisibility(View.GONE);
        cardMaria.setVisibility(View.GONE);
        cardJames.setVisibility(View.GONE);
        cardSarah.setVisibility(View.GONE);

        switch (status) {
            case "all":
                selectButton(btnAll, tvAll);
                cardRobert.setVisibility(View.VISIBLE);
                cardMaria.setVisibility(View.VISIBLE);
                cardJames.setVisibility(View.VISIBLE);
                cardSarah.setVisibility(View.VISIBLE);
                break;
            case "critical":
                selectButton(btnCritical, tvCritical);
                cardRobert.setVisibility(View.VISIBLE);
                break;
            case "warning":
                selectButton(btnWarning, tvWarning);
                cardMaria.setVisibility(View.VISIBLE);
                break;
            case "stable":
                selectButton(btnStable, tvStable);
                cardJames.setVisibility(View.VISIBLE);
                cardSarah.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void selectButton(MaterialCardView card, TextView text) {
        card.setCardBackgroundColor(getResources().getColor(R.color.primary_teal));
        card.setStrokeWidth(0);
        text.setTextColor(Color.WHITE);
    }

    private void resetButton(MaterialCardView card, TextView text) {
        card.setCardBackgroundColor(Color.WHITE);
        card.setStrokeWidth(1);
        card.setStrokeColor(Color.parseColor("#E2E8F0"));
        text.setTextColor(Color.parseColor("#64748B"));
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
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
                return itemId == R.id.nav_patients;
            });
        }
    }
}