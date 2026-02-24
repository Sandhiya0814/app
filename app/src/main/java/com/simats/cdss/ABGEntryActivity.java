package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ABGEntryActivity extends AppCompatActivity {

    private EditText etPh, etPao2, etPaco2, etHco3, etFio2;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abg_entry);

        etPh = findViewById(R.id.et_ph);
        etPao2 = findViewById(R.id.et_pao2);
        etPaco2 = findViewById(R.id.et_paco2);
        etHco3 = findViewById(R.id.et_hco3);
        etFio2 = findViewById(R.id.et_fio2);
        btnSubmit = findViewById(R.id.btn_submit);

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

        etPh.addTextChangedListener(watcher);
        etPao2.addTextChangedListener(watcher);
        etPaco2.addTextChangedListener(watcher);
        etHco3.addTextChangedListener(watcher);
        etFio2.addTextChangedListener(watcher);

        btnSubmit.setEnabled(false);
        btnSubmit.setAlpha(0.5f);

        btnSubmit.setOnClickListener(v -> {
            Intent intent = new Intent(this, StaffDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        setupBottomNav();
    }

    private void checkValidation() {
        boolean isValid = !etPh.getText().toString().trim().isEmpty() &&
                         !etPao2.getText().toString().trim().isEmpty() &&
                         !etPaco2.getText().toString().trim().isEmpty() &&
                         !etHco3.getText().toString().trim().isEmpty() &&
                         !etFio2.getText().toString().trim().isEmpty();
        
        btnSubmit.setEnabled(isValid);
        btnSubmit.setAlpha(isValid ? 1.0f : 0.5f);
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