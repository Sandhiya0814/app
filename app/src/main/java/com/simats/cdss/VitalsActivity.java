package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VitalsActivity extends AppCompatActivity {

    private EditText etSpo2, etRespRate, etHeartRate, etTemperature, etBp;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals);

        etSpo2 = findViewById(R.id.et_spo2);
        etRespRate = findViewById(R.id.et_resp_rate);
        etHeartRate = findViewById(R.id.et_heart_rate);
        etTemperature = findViewById(R.id.et_temperature);
        etBp = findViewById(R.id.et_bp);
        btnNext = findViewById(R.id.btn_next);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

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

        etSpo2.addTextChangedListener(watcher);
        etRespRate.addTextChangedListener(watcher);
        etHeartRate.addTextChangedListener(watcher);
        etTemperature.addTextChangedListener(watcher);
        etBp.addTextChangedListener(watcher);

        // Initially disable button
        btnNext.setEnabled(false);
        btnNext.setAlpha(0.5f);

        btnNext.setOnClickListener(v -> {
            // Navigate to ABG Entry Screen
            startActivity(new Intent(this, ABGEntryActivity.class));
        });

        setupBottomNav();
    }

    private void checkValidation() {
        boolean isValid = !etSpo2.getText().toString().trim().isEmpty() &&
                         !etRespRate.getText().toString().trim().isEmpty() &&
                         !etHeartRate.getText().toString().trim().isEmpty() &&
                         !etTemperature.getText().toString().trim().isEmpty() &&
                         !etBp.getText().toString().trim().isEmpty();
        
        btnNext.setEnabled(isValid);
        btnNext.setAlpha(isValid ? 1.0f : 0.5f);
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setOnItemSelectedListener(item -> {
                // Return to Staff Dashboard for any bottom navigation click
                Intent intent = new Intent(this, StaffDashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                return true;
            });
        }
    }
}