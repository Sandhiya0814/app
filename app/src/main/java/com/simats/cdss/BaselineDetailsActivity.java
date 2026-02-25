package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class BaselineDetailsActivity extends AppCompatActivity {

    private MaterialCardView cardYes, cardNo;
    private MaterialCardView cvIconYes, cvPlaceholderYes;
    private MaterialCardView cvIconNo, cvPlaceholderNo;
    private TextView tvYes, tvNo;
    private Button btnNext;
    private MaterialCardView selectedCard = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseline_details);

        // Initialize Views
        cardYes = findViewById(R.id.card_yes);
        cardNo = findViewById(R.id.card_no);
        cvIconYes = findViewById(R.id.cv_icon_yes);
        cvPlaceholderYes = findViewById(R.id.cv_placeholder_yes);
        cvIconNo = findViewById(R.id.cv_icon_no);
        cvPlaceholderNo = findViewById(R.id.cv_placeholder_no);
        tvYes = findViewById(R.id.tv_yes);
        tvNo = findViewById(R.id.tv_no);
        btnNext = findViewById(R.id.btn_next);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        cardYes.setOnClickListener(v -> handleSelection(cardYes));
        cardNo.setOnClickListener(v -> handleSelection(cardNo));

        btnNext.setOnClickListener(v -> {
            startActivity(new Intent(this, GoldClassificationActivity.class));
        });

        setupBottomNav();
    }

    private void handleSelection(MaterialCardView card) {
        // Reset all states
        resetStyles();

        selectedCard = card;
        btnNext.setEnabled(true);

        if (card.getId() == R.id.card_yes) {
            cardYes.setStrokeWidth(4);
            cardYes.setStrokeColor(getResources().getColor(R.color.primary_teal));
            cvIconYes.setVisibility(View.VISIBLE);
            cvPlaceholderYes.setVisibility(View.GONE);
            tvYes.setTextColor(getResources().getColor(R.color.primary_teal));
        } else {
            cardNo.setStrokeWidth(4);
            cardNo.setStrokeColor(getResources().getColor(R.color.primary_teal));
            cvIconNo.setVisibility(View.VISIBLE);
            cvPlaceholderNo.setVisibility(View.GONE);
            tvNo.setTextColor(getResources().getColor(R.color.primary_teal));
        }
    }

    private void resetStyles() {
        // Reset Yes Card
        cardYes.setStrokeWidth(0);
        cvIconYes.setVisibility(View.INVISIBLE);
        cvPlaceholderYes.setVisibility(View.VISIBLE);
        tvYes.setTextColor(getResources().getColor(R.color.text_secondary));

        // Reset No Card
        cardNo.setStrokeWidth(0);
        cvIconNo.setVisibility(View.INVISIBLE);
        cvPlaceholderNo.setVisibility(View.VISIBLE);
        tvNo.setTextColor(getResources().getColor(R.color.text_secondary));
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
