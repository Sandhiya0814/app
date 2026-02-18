package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class ReviewRecommendationActivity extends AppCompatActivity {

    private MaterialCardView cardAccept, cardOverride;
    private ImageView ivCheckAccept, ivIconOverride;
    private LinearLayout layoutOverrideReason;
    private EditText etOverrideReason;
    private Button btnConfirm;
    private boolean isOverrideSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_recommendation);

        // Initialize Views
        cardAccept = findViewById(R.id.card_accept);
        cardOverride = findViewById(R.id.card_override);
        ivCheckAccept = findViewById(R.id.iv_check_accept);
        ivIconOverride = findViewById(R.id.iv_icon_override);
        layoutOverrideReason = findViewById(R.id.layout_override_reason);
        etOverrideReason = findViewById(R.id.et_override_reason);
        btnConfirm = findViewById(R.id.btn_confirm);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        // Default state: Accept Selected
        selectAccept();

        cardAccept.setOnClickListener(v -> selectAccept());
        cardOverride.setOnClickListener(v -> selectOverride());

        etOverrideReason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateConfirmButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnConfirm.setOnClickListener(v -> {
            // Navigate to Therapy Recommendation Screen
            Intent intent = new Intent(this, TherapyRecommendationActivity.class);
            startActivity(intent);
        });

        setupBottomNav();
    }

    private void selectAccept() {
        isOverrideSelected = false;
        
        // Update Accept Card
        cardAccept.setStrokeWidth(4);
        cardAccept.setStrokeColor(getResources().getColor(R.color.primary_teal));
        ivCheckAccept.setVisibility(View.VISIBLE);

        // Update Override Card
        cardOverride.setStrokeWidth(0);
        ivIconOverride.setVisibility(View.GONE);
        layoutOverrideReason.setVisibility(View.GONE);
        
        updateConfirmButtonState();
    }

    private void selectOverride() {
        isOverrideSelected = true;

        // Update Accept Card
        cardAccept.setStrokeWidth(0);
        ivCheckAccept.setVisibility(View.GONE);

        // Update Override Card
        cardOverride.setStrokeWidth(4);
        cardOverride.setStrokeColor(getResources().getColor(R.color.orange_warning));
        ivIconOverride.setVisibility(View.VISIBLE);
        layoutOverrideReason.setVisibility(View.VISIBLE);
        
        updateConfirmButtonState();
    }

    private void updateConfirmButtonState() {
        if (!isOverrideSelected) {
            btnConfirm.setEnabled(true);
            btnConfirm.setAlpha(1.0f);
        } else {
            String reason = etOverrideReason.getText().toString().trim();
            boolean hasReason = !reason.isEmpty();
            btnConfirm.setEnabled(hasReason);
            btnConfirm.setAlpha(hasReason ? 1.0f : 0.5f);
        }
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_patients);
            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(this, DoctordashboardActivity.class));
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