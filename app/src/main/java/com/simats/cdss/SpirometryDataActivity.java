package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SpirometryDataActivity extends AppCompatActivity {

    private EditText etFev1, etFev1Fvc;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spirometry_data);

        etFev1 = findViewById(R.id.et_fev1);
        etFev1Fvc = findViewById(R.id.et_fev1_fvc);
        btnNext = findViewById(R.id.btn_next);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        // Real-time validation to enable the "Next" button
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkValidation();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etFev1.addTextChangedListener(watcher);
        etFev1Fvc.addTextChangedListener(watcher);

        // EXPLICIT Navigation to Gas Exchange History Screen
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(SpirometryDataActivity.this, GasExchangeHistoryActivity.class);
            startActivity(intent);
        });

        setupBottomNav();
    }

    private void checkValidation() {
        boolean isValid = !etFev1.getText().toString().trim().isEmpty() &&
                         !etFev1Fvc.getText().toString().trim().isEmpty();
        btnNext.setEnabled(isValid);
        btnNext.setAlpha(isValid ? 1.0f : 0.5f);
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