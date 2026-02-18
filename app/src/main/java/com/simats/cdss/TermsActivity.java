package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;

public class TermsActivity extends AppCompatActivity {

    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        userRole = getIntent().getStringExtra("role");

        CheckBox cbAgree = findViewById(R.id.cb_agree);
        Button btnAccept = findViewById(R.id.btn_accept);

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            onBackPressed();
        });

        cbAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnAccept.setEnabled(isChecked);
        });

        btnAccept.setOnClickListener(v -> {
            Intent intent;
            if ("staff".equals(userRole)) {
                intent = new Intent(TermsActivity.this, StaffDashboardActivity.class);
            } else {
                intent = new Intent(TermsActivity.this, DoctordashboardActivity.class);
            }
            startActivity(intent);
            finish();
        });
    }
}