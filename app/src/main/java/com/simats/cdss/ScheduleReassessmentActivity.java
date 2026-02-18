package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;

public class ScheduleReassessmentActivity extends AppCompatActivity {

    private MaterialCardView selectedCard = null;
    private Button btnConfirm;
    private MaterialCardView card30m, card1h, card2h, card4h;
    private ImageView ivCheck30, ivCheck1h, ivCheck2h, ivCheck4h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_reassessment);

        btnConfirm = findViewById(R.id.btn_confirm);
        
        card30m = findViewById(R.id.card_30_min);
        card1h = findViewById(R.id.card_1_hour);
        card2h = findViewById(R.id.card_2_hour);
        card4h = findViewById(R.id.card_4_hour);

        ivCheck30 = findViewById(R.id.iv_check_30);
        ivCheck1h = findViewById(R.id.iv_check_1h);
        ivCheck2h = findViewById(R.id.iv_check_2h);
        ivCheck4h = findViewById(R.id.iv_check_4h);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        setupSelectionListeners();

        btnConfirm.setOnClickListener(v -> {
            showSuccessBottomSheet();
        });

        setupBottomNav();
    }

    private void showSuccessBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.layout_schedule_success, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.setCancelable(false);

        bottomSheetView.findViewById(R.id.btn_done).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            Intent intent = new Intent(this, DoctordashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        bottomSheetDialog.show();
    }

    private void setupSelectionListeners() {
        View.OnClickListener listener = v -> handleSelection((MaterialCardView) v);

        card30m.setOnClickListener(listener);
        card1h.setOnClickListener(listener);
        card2h.setOnClickListener(listener);
        card4h.setOnClickListener(listener);
    }

    private void handleSelection(MaterialCardView card) {
        if (selectedCard != null) {
            resetCardStyle(selectedCard);
        }

        selectedCard = card;
        applySelectedStyle(selectedCard);
        btnConfirm.setEnabled(true);
    }

    private void applySelectedStyle(MaterialCardView card) {
        card.setStrokeWidth(4);
        card.setStrokeColor(getResources().getColor(R.color.primary_teal));
        
        if (card.getId() == R.id.card_30_min) ivCheck30.setVisibility(View.VISIBLE);
        else if (card.getId() == R.id.card_1_hour) ivCheck1h.setVisibility(View.VISIBLE);
        else if (card.getId() == R.id.card_2_hour) ivCheck2h.setVisibility(View.VISIBLE);
        else if (card.getId() == R.id.card_4_hour) ivCheck4h.setVisibility(View.VISIBLE);
    }

    private void resetCardStyle(MaterialCardView card) {
        card.setStrokeWidth(0);
        
        if (card.getId() == R.id.card_30_min) ivCheck30.setVisibility(View.GONE);
        else if (card.getId() == R.id.card_1_hour) ivCheck1h.setVisibility(View.GONE);
        else if (card.getId() == R.id.card_2_hour) ivCheck2h.setVisibility(View.GONE);
        else if (card.getId() == R.id.card_4_hour) ivCheck4h.setVisibility(View.GONE);
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
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