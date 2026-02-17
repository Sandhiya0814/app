package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        CheckBox cbAgree = findViewById(R.id.cb_agree);
        Button btnAccept = findViewById(R.id.btn_accept);

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            onBackPressed();
        });

        cbAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnAccept.setEnabled(isChecked);
        });

        btnAccept.setOnClickListener(v -> {
            Intent intent = new Intent(TermsActivity.this, DoctordashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }
}