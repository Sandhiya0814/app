package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ReassessmentChecklistActivity extends AppCompatActivity {

    private CheckBox cb1, cb2, cb3, cb4, cb5;
    private Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reassessment_checklist);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        cb1 = findViewById(R.id.cb_spo2);
        cb2 = findViewById(R.id.cb_resp_rate);
        cb3 = findViewById(R.id.cb_consciousness);
        cb4 = findViewById(R.id.cb_device_fit);
        cb5 = findViewById(R.id.cb_abg);
        btnComplete = findViewById(R.id.btn_complete);

        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> checkAll();

        cb1.setOnCheckedChangeListener(listener);
        cb2.setOnCheckedChangeListener(listener);
        cb3.setOnCheckedChangeListener(listener);
        cb4.setOnCheckedChangeListener(listener);
        cb5.setOnCheckedChangeListener(listener);

        btnComplete.setOnClickListener(v -> {
            showSuccessBottomSheet();
        });

        setupBottomNav();
    }

    private void showSuccessBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.layout_reassessment_success, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.setCancelable(false);

        bottomSheetView.findViewById(R.id.btn_done).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            Intent intent = new Intent(this, StaffDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        bottomSheetDialog.show();
    }

    private void checkAll() {
        boolean allChecked = cb1.isChecked() && cb2.isChecked() && cb3.isChecked() && 
                             cb4.isChecked() && cb5.isChecked();
        btnComplete.setEnabled(allChecked);
        btnComplete.setAlpha(allChecked ? 1.0f : 0.5f);
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