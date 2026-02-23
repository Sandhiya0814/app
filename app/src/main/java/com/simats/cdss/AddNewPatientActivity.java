package com.simats.cdss;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import java.util.Calendar;

public class AddNewPatientActivity extends AppCompatActivity {

    private EditText etFullName, etWard, etBedNumber;
    private TextView tvDob;
    private MaterialCardView cardMale, cardFemale, cardOther;
    private String selectedSex = "";
    private MaterialCardView selectedSexCard = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_patient);

        // Initialize Views
        etFullName = findViewById(R.id.et_full_name);
        tvDob = findViewById(R.id.tv_dob);
        etWard = findViewById(R.id.et_ward);
        etBedNumber = findViewById(R.id.et_bed_number);
        cardMale = findViewById(R.id.card_male);
        cardFemale = findViewById(R.id.card_female);
        cardOther = findViewById(R.id.card_other);

        // Back button
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());

        // Date of Birth Picker
        findViewById(R.id.rl_dob).setOnClickListener(v -> showDatePicker());

        // Sex selection logic
        cardMale.setOnClickListener(v -> updateSexSelection("Male", cardMale));
        cardFemale.setOnClickListener(v -> updateSexSelection("Female", cardFemale));
        cardOther.setOnClickListener(v -> updateSexSelection("Other", cardOther));

        // Next Button
        findViewById(R.id.btn_next).setOnClickListener(v -> {
            if (validateInput()) {
                // Navigate to Baseline Details
                startActivity(new Intent(this, BaselineDetailsActivity.class));
            }
        });

        setupBottomNav();
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String date = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year1;
                    tvDob.setText(date);
                    tvDob.setTextColor(getResources().getColor(R.color.text_dark));
                }, year, month, day);
        datePickerDialog.show();
    }

    private void updateSexSelection(String sex, MaterialCardView card) {
        // Reset previous selection
        if (selectedSexCard != null) {
            selectedSexCard.setStrokeWidth(0);
        }

        selectedSex = sex;
        selectedSexCard = card;

        // Highlight selected card
        selectedSexCard.setStrokeWidth(4);
        selectedSexCard.setStrokeColor(getResources().getColor(R.color.primary_teal));
    }

    private boolean validateInput() {
        if (etFullName.getText().toString().trim().isEmpty()) {
            etFullName.setError("Full name is required");
            return false;
        }
        if (selectedSex.isEmpty()) {
            Toast.makeText(this, "Please select sex", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tvDob.getText().toString().equals("mm/dd/yyyy")) {
            Toast.makeText(this, "Please select date of birth", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etWard.getText().toString().trim().isEmpty()) {
            etWard.setError("Ward is required");
            return false;
        }
        if (etBedNumber.getText().toString().trim().isEmpty()) {
            etBedNumber.setError("Bed Number is required");
            return false;
        }
        return true;
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