package com.simats.cdss;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class HelpSupportActivity extends AppCompatActivity {

    private MaterialCardView cardEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support);

        initializeViews();
        setupEmailSupport();
        setupBottomNavigation();
    }

    private void initializeViews() {
        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());
        cardEmail = findViewById(R.id.cardEmail);
    }

    private void setupEmailSupport() {

        cardEmail.setOnClickListener(v -> {

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:support@cdss.com"));

            emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                    "Clinical Support Request - CDSS");

            emailIntent.putExtra(Intent.EXTRA_TEXT,
                    "Doctor Name:\n" +
                            "Hospital/Institution:\n" +
                            "Patient ID (if applicable):\n\n" +
                            "Describe the issue:\n");

            startActivity(Intent.createChooser(emailIntent,
                    "Contact Clinical Support"));
        });
    }

    private void setupBottomNavigation() {

        BottomNavigationView bottomNav =
                findViewById(R.id.bottom_navigation);

        bottomNav.setSelectedItemId(R.id.nav_settings);

        bottomNav.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                startActivity(new Intent(this,
                        DoctordashboardActivity.class));
                finish();
                return true;

            } else if (itemId == R.id.nav_patients) {
                startActivity(new Intent(this,
                        PatientListActivity.class));
                finish();
                return true;

            } else if (itemId == R.id.nav_alerts) {
                startActivity(new Intent(this,
                        DoctorAlertsActivity.class));
                finish();
                return true;

            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(this,
                        SettingsActivity.class));
                finish();
                return true;
            }

            return false;
        });
    }
}