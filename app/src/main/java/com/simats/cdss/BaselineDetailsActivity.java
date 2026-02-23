package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class BaselineDetailsActivity extends AppCompatActivity {

    private MaterialCardView selectedCard = null;
    private Button btnNext;
    private MaterialCardView cardYes, cardNo;
    private ImageView ivCheckYes, ivIconNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseline_details);

        btnNext = findViewById(R.id.btn_next);
        btnNext.setEnabled(false); // Initially disabled

        cardYes = findViewById(R.id.card_yes);
        cardNo = findViewById(R.id.card_no);
        ivCheckYes = findViewById(R.id.iv_check_yes);
        ivIconNo = findViewById(R.id.iv_icon_no);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        cardYes.setOnClickListener(v -> handleSelection(cardYes));
        cardNo.setOnClickListener(v -> handleSelection(cardNo));

        btnNext.setOnClickListener(v -> {
            // Navigate to GOLD Classification screen
            startActivity(new Intent(this, GoldClassificationActivity.class));
        });

        setupBottomNav();
    }

    private void handleSelection(MaterialCardView card) {
        // Reset previous selection
        if (selectedCard != null) {
            selectedCard.setStrokeWidth(0);
            if (selectedCard.getId() == R.id.card_yes) {
                ivCheckYes.setVisibility(View.GONE);
            } else {
                ivIconNo.setVisibility(View.GONE);
            }
        }

        selectedCard = card;
        selectedCard.setStrokeWidth(4);
        selectedCard.setStrokeColor(getResources().getColor(R.color.primary_teal));

        // Show respective icon
        if (card.getId() == R.id.card_yes) {
            ivCheckYes.setVisibility(View.VISIBLE);
        } else {
            ivIconNo.setVisibility(View.VISIBLE);
        }

        btnNext.setEnabled(true);
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
}