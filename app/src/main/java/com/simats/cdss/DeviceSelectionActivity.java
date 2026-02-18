package com.simats.cdss;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class DeviceSelectionActivity extends AppCompatActivity {

    private MaterialCardView selectedCard = null;
    private Button btnConfirm;
    private MaterialCardView cardVenturi, cardNasal, cardHighFlow, cardNonRebreather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_selection);

        btnConfirm = findViewById(R.id.btn_confirm);
        btnConfirm.setEnabled(false); // Initially disabled

        // Initialize Cards
        cardVenturi = findViewById(R.id.card_venturi);
        cardNasal = findViewById(R.id.card_nasal);
        cardHighFlow = findViewById(R.id.card_high_flow);
        cardNonRebreather = findViewById(R.id.card_non_rebreather);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        setupSelectionListeners();

        btnConfirm.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReviewRecommendationActivity.class);
            startActivity(intent);
        });

        setupBottomNav();
    }

    private void setupSelectionListeners() {
        View.OnClickListener listener = v -> {
            if (v instanceof MaterialCardView) {
                handleSelection((MaterialCardView) v);
            }
        };

        cardVenturi.setOnClickListener(listener);
        cardNasal.setOnClickListener(listener);
        cardHighFlow.setOnClickListener(listener);
        cardNonRebreather.setOnClickListener(listener);
    }

    private void handleSelection(MaterialCardView card) {
        // Reset previous selection
        if (selectedCard != null) {
            selectedCard.setStrokeWidth(0);
        }

        // Apply new selection
        selectedCard = card;
        selectedCard.setStrokeWidth(4);
        selectedCard.setStrokeColor(getResources().getColor(R.color.primary_teal));

        // Enable Confirm Button
        btnConfirm.setEnabled(true);
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