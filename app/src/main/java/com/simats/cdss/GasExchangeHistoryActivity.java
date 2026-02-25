package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class GasExchangeHistoryActivity extends AppCompatActivity {

    private MaterialCardView cardHypoYes, cardHypoNo, cardHypoUnknown;
    private MaterialCardView cardOxyYes, cardOxyNo;
    private MaterialCardView selectedHypo = null;
    private MaterialCardView selectedOxy = null;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_exchange_history);

        cardHypoYes = findViewById(R.id.card_hypoxemia_yes);
        cardHypoNo = findViewById(R.id.card_hypoxemia_no);
        cardHypoUnknown = findViewById(R.id.card_hypoxemia_unknown);
        
        cardOxyYes = findViewById(R.id.card_oxygen_yes);
        cardOxyNo = findViewById(R.id.card_oxygen_no);
        
        btnNext = findViewById(R.id.btn_next);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        cardHypoYes.setOnClickListener(v -> handleHypoSelection(cardHypoYes));
        cardHypoNo.setOnClickListener(v -> handleHypoSelection(cardHypoNo));
        cardHypoUnknown.setOnClickListener(v -> handleHypoSelection(cardHypoUnknown));

        cardOxyYes.setOnClickListener(v -> handleOxySelection(cardOxyYes));
        cardOxyNo.setOnClickListener(v -> handleOxySelection(cardOxyNo));

        btnNext.setOnClickListener(v -> {
            // Corrected: Navigate to Current Symptoms screen
            startActivity(new Intent(GasExchangeHistoryActivity.this, CurrentSymptomsActivity.class));
        });

        setupBottomNav();
    }

    private void handleHypoSelection(MaterialCardView card) {
        if (selectedHypo != null) {
            selectedHypo.setStrokeWidth(0);
        }
        selectedHypo = card;
        selectedHypo.setStrokeWidth(4);
        selectedHypo.setStrokeColor(getResources().getColor(R.color.primary_teal));
        checkNextButton();
    }

    private void handleOxySelection(MaterialCardView card) {
        if (selectedOxy != null) {
            selectedOxy.setStrokeWidth(0);
        }
        selectedOxy = card;
        selectedOxy.setStrokeWidth(4);
        selectedOxy.setStrokeColor(getResources().getColor(R.color.primary_teal));
        checkNextButton();
    }

    private void checkNextButton() {
        boolean isReady = selectedHypo != null && selectedOxy != null;
        btnNext.setEnabled(isReady);
        btnNext.setAlpha(isReady ? 1.0f : 0.5f);
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_home);
            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(this, StaffDashboardActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.nav_patients) {
                    startActivity(new Intent(this, PatientListActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.nav_alerts) {
                    startActivity(new Intent(this, DoctorAlertsActivity.class));
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
}