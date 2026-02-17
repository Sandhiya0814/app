package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class EscalationCriteriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escalation_criteria);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        findViewById(R.id.btn_view_niv).setOnClickListener(v -> {
            startActivity(new Intent(this, NIVRecommendationActivity.class));
        });
    }
}