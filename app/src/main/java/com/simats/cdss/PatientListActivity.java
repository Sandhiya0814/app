package com.simats.cdss;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class PatientListActivity extends AppCompatActivity {

    private MaterialCardView btnAll, btnCritical, btnWarning, btnStable;
    private TextView tvAll, tvCritical, tvWarning, tvStable;
    private View card1, card2, card3, card4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

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
        card1 = findViewById(R.id.patient_card_1); // Robert Chen (Critical)
        card2 = findViewById(R.id.patient_card_2); // Maria Garcia (Warning)
        card3 = findViewById(R.id.patient_card_3); // James Wilson (Stable)
        card4 = findViewById(R.id.patient_card_4); // Sarah Johnson (Stable)

        // Set click listener for Robert Chen card
        card1.setOnClickListener(v -> {
            startActivity(new Intent(this, PatientDetailsActivity.class));
        });

        setupFilters();
        setupBottomNav();

        // Check if a filter was passed from the Dashboard
        String filter = getIntent().getStringExtra("filter");
        if (filter != null) {
            filterList(filter);
        } else {
            filterList("all"); // Default view
        }
    }

    private void setupFilters() {
        btnAll.setOnClickListener(v -> filterList("all"));
        btnCritical.setOnClickListener(v -> filterList("critical"));
        btnWarning.setOnClickListener(v -> filterList("warning"));
        btnStable.setOnClickListener(v -> filterList("stable"));
    }

    private void filterList(String status) {
        // Reset all buttons to unselected state
        resetButton(btnAll, tvAll);
        resetButton(btnCritical, tvCritical);
        resetButton(btnWarning, tvWarning);
        resetButton(btnStable, tvStable);

        // Hide all cards first
        card1.setVisibility(View.GONE);
        card2.setVisibility(View.GONE);
        card3.setVisibility(View.GONE);
        card4.setVisibility(View.GONE);

        switch (status) {
            case "all":
                selectButton(btnAll, tvAll);
                card1.setVisibility(View.VISIBLE);
                card2.setVisibility(View.VISIBLE);
                card3.setVisibility(View.VISIBLE);
                card4.setVisibility(View.VISIBLE);
                break;
            case "critical":
                selectButton(btnCritical, tvCritical);
                card1.setVisibility(View.VISIBLE);
                break;
            case "warning":
                selectButton(btnWarning, tvWarning);
                card2.setVisibility(View.VISIBLE);
                break;
            case "stable":
                selectButton(btnStable, tvStable);
                card3.setVisibility(View.VISIBLE);
                card4.setVisibility(View.VISIBLE);
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
        bottomNav.setSelectedItemId(R.id.nav_patients);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(this, DoctordashboardActivity.class));
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
            return itemId == R.id.nav_patients;
        });
    }
}