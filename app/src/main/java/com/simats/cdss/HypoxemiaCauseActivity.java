package com.simats.cdss;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class HypoxemiaCauseActivity extends AppCompatActivity {

    private MaterialCardView selectedVerticalCard = null;
    private Button btnNext;
    private MaterialCardView cardVQ, cardHypo, cardDiff, cardShunt, cardUnknown;
    private ImageView ivCheckVQ, ivCheckHypo, ivCheckDiff, ivCheckShunt, ivCheckUnknown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hypoxemia_cause);

        btnNext = findViewById(R.id.btn_next);
        
        // Initialize Cards
        cardVQ = findViewById(R.id.card_vq_mismatch);
        cardHypo = findViewById(R.id.card_hypoventilation);
        cardDiff = findViewById(R.id.card_diffusion);
        cardShunt = findViewById(R.id.card_shunt);
        cardUnknown = findViewById(R.id.card_unknown);

        // Initialize Checkmarks
        ivCheckVQ = findViewById(R.id.iv_check_vq);
        ivCheckHypo = findViewById(R.id.iv_check_hypo);
        ivCheckDiff = findViewById(R.id.iv_check_diff);
        ivCheckShunt = findViewById(R.id.iv_check_shunt);
        ivCheckUnknown = findViewById(R.id.iv_check_unknown);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        setupSelectionListeners();
        
        btnNext.setOnClickListener(v -> {
            // Navigate to Oxygen Requirement Screen
            startActivity(new Intent(this, OxygenRequirementActivity.class));
        });

        setupBottomNav();
    }

    private void setupSelectionListeners() {
        View.OnClickListener listener = v -> {
            MaterialCardView clickedCard = (MaterialCardView) v;
            handleSelection(clickedCard);
        };

        cardVQ.setOnClickListener(listener);
        cardHypo.setOnClickListener(listener);
        cardDiff.setOnClickListener(listener);
        cardShunt.setOnClickListener(listener);
        cardUnknown.setOnClickListener(listener);
    }

    private void handleSelection(MaterialCardView card) {
        // Reset previous selection
        if (selectedVerticalCard != null) {
            resetCardStyle(selectedVerticalCard);
        }

        // Apply new selection
        selectedVerticalCard = card;
        applySelectedStyle(selectedVerticalCard);
        
        // Enable Next Button
        btnNext.setEnabled(true);
    }

    private void applySelectedStyle(MaterialCardView card) {
        card.setStrokeWidth(4);
        card.setStrokeColor(getResources().getColor(R.color.primary_teal));
        
        // Show respective checkmark
        if (card.getId() == R.id.card_vq_mismatch) ivCheckVQ.setVisibility(View.VISIBLE);
        else if (card.getId() == R.id.card_hypoventilation) ivCheckHypo.setVisibility(View.VISIBLE);
        else if (card.getId() == R.id.card_diffusion) ivCheckDiff.setVisibility(View.VISIBLE);
        else if (card.getId() == R.id.card_shunt) ivCheckShunt.setVisibility(View.VISIBLE);
        else if (card.getId() == R.id.card_unknown) ivCheckUnknown.setVisibility(View.VISIBLE);
    }

    private void resetCardStyle(MaterialCardView card) {
        card.setStrokeWidth(0);
        
        // Hide respective checkmark
        if (card.getId() == R.id.card_vq_mismatch) ivCheckVQ.setVisibility(View.GONE);
        else if (card.getId() == R.id.card_hypoventilation) ivCheckHypo.setVisibility(View.GONE);
        else if (card.getId() == R.id.card_diffusion) ivCheckDiff.setVisibility(View.GONE);
        else if (card.getId() == R.id.card_shunt) ivCheckShunt.setVisibility(View.GONE);
        else if (card.getId() == R.id.card_unknown) ivCheckUnknown.setVisibility(View.GONE);
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