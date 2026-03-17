package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.simats.cdss.models.GenericResponse;
import com.simats.cdss.network.ApiService;
import com.simats.cdss.network.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReassessmentChecklistActivity extends AppCompatActivity {

    private static final String TAG = "ReassessmentChecklist";

    private int patientId = -1;
    private EditText etSpo2, etRespiratoryRate, etHeartRate, etNotes;
    private Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reassessment_checklist);

        patientId = getIntent().getIntExtra("patient_id", -1);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        etSpo2 = findViewById(R.id.et_spo2);
        etRespiratoryRate = findViewById(R.id.et_respiratory_rate);
        etHeartRate = findViewById(R.id.et_heart_rate);
        etNotes = findViewById(R.id.et_notes);
        btnComplete = findViewById(R.id.btn_complete);

        btnComplete.setOnClickListener(v -> saveReassessment());

        setupBottomNav();
    }

    private void saveReassessment() {
        // Validate required fields
        String spo2Str = etSpo2.getText().toString().trim();
        String rrStr = etRespiratoryRate.getText().toString().trim();
        String hrStr = etHeartRate.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        if (spo2Str.isEmpty()) {
            etSpo2.setError("SpO₂ is required");
            etSpo2.requestFocus();
            return;
        }

        if (rrStr.isEmpty()) {
            etRespiratoryRate.setError("Respiratory Rate is required");
            etRespiratoryRate.requestFocus();
            return;
        }

        if (patientId == -1) {
            Toast.makeText(this, "Invalid patient ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Build request body
        String reassessmentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        Map<String, Object> body = new HashMap<>();
        body.put("patient_id", patientId);
        body.put("spo2", Double.parseDouble(spo2Str));
        body.put("respiratory_rate", Double.parseDouble(rrStr));
        body.put("heart_rate", hrStr.isEmpty() ? 0 : Double.parseDouble(hrStr));
        body.put("notes", notes);
        body.put("reassessment_time", reassessmentTime);

        Log.d(TAG, "API_REQUEST: " + body.toString());

        // Send POST request
        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        apiService.saveReassessmentChecklist(body).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                Log.d(TAG, "API_RESPONSE code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "API_RESPONSE body: " + response.body().getMessage());
                    Toast.makeText(ReassessmentChecklistActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                    showSuccessBottomSheet();
                } else {
                    Log.e(TAG, "API_RESPONSE error: " + response.code());
                    Toast.makeText(ReassessmentChecklistActivity.this, "Failed to save reassessment", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Log.e(TAG, "API_FAILURE: " + t.getMessage());
                Toast.makeText(ReassessmentChecklistActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSuccessBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.layout_reassessment_success, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.setCancelable(false);

        bottomSheetView.findViewById(R.id.btn_done).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            SessionManager session = new SessionManager(this);
            String role = session.getRole();
            Intent intent;
            if ("staff".equals(role)) {
                intent = new Intent(this, StaffDashboardActivity.class);
            } else {
                intent = new Intent(this, DoctordashboardActivity.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        bottomSheetDialog.show();
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_home);
            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                SessionManager session = new SessionManager(this);
                String role = session.getRole();

                if (itemId == R.id.nav_home) {
                    if ("staff".equals(role)) {
                        startActivity(new Intent(this, StaffDashboardActivity.class));
                    } else {
                        startActivity(new Intent(this, DoctordashboardActivity.class));
                    }
                    finish();
                    return true;
                } else if (itemId == R.id.nav_patients) {
                    if ("staff".equals(role)) {
                        startActivity(new Intent(this, StaffPatientsActivity.class));
                    } else {
                        startActivity(new Intent(this, DoctorPatientsActivity.class));
                    }
                    finish();
                    return true;
                } else if (itemId == R.id.nav_alerts) {
                    if ("staff".equals(role)) {
                        startActivity(new Intent(this, StaffAlertsActivity.class));
                    } else {
                        startActivity(new Intent(this, DoctorAlertsActivity.class));
                    }
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