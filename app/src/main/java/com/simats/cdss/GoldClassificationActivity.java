package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class GoldClassificationActivity extends AppCompatActivity {

    private MaterialCardView selectedCard = null;
    private Button btnNext;
    private MaterialCardView cardGold1, cardGold2, cardGold3, cardGold4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_classification);

        btnNext = findViewById(R.id.btn_next);
        btnNext.setEnabled(false);

        cardGold1 = findViewById(R.id.card_gold_1);
        cardGold2 = findViewById(R.id.card_gold_2);
        cardGold3 = findViewById(R.id.card_gold_3);
        cardGold4 = findViewById(R.id.card_gold_4);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        View.OnClickListener listener = v -> handleSelection((MaterialCardView) v);

        cardGold1.setOnClickListener(listener);
        cardGold2.setOnClickListener(listener);
        cardGold3.setOnClickListener(listener);
        cardGold4.setOnClickListener(listener);

        btnNext.setOnClickListener(v -> {
            // Navigate to Spirometry Data screen
            startActivity(new Intent(this, SpirometryDataActivity.class));
        });

        setupBottomNav();
    }

    private void handleSelection(MaterialCardView card) {
        if (selectedCard != null) {
            selectedCard.setStrokeWidth(0);
        }

        selectedCard = card;
        selectedCard.setStrokeWidth(4);
        selectedCard.setStrokeColor(getResources().getColor(R.color.primary_teal));

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