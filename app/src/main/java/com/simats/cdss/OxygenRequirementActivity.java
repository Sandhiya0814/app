package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.simats.cdss.models.GenericResponse;
import com.simats.cdss.models.PatientDetailResponse;
import com.simats.cdss.network.ApiService;
import com.simats.cdss.network.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OxygenRequirementActivity extends AppCompatActivity {

    private int patientId = -1;
    private double spo2Value = 0.0;
    private String hypoxemiaLevel = "";
    private String symptomsLevel = "";
    private String oxygenRequired = "";

    private TextView tvSpo2Value, tvHypoxemiaLevel, tvSymptomsLevel;
    private TextView tvTherapyIndicated, tvTherapyDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oxygen_requirement);

        patientId = getIntent().getIntExtra("patient_id", -1);

        // Initialize UI references
        tvSpo2Value = findViewById(R.id.tv_spo2_value);
        tvHypoxemiaLevel = findViewById(R.id.tv_hypoxemia_level);
        tvSymptomsLevel = findViewById(R.id.tv_symptoms_level);
        tvTherapyIndicated = findViewById(R.id.tv_therapy_indicated);
        tvTherapyDesc = findViewById(R.id.tv_therapy_desc);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        // Fetch SpO2 from patient vitals via existing patient detail API
        fetchSpo2FromVitals();

        findViewById(R.id.btn_proceed).setOnClickListener(v -> {
            if (patientId == -1) {
                Toast.makeText(this, "Invalid patient ID", Toast.LENGTH_SHORT).show();
                return;
            }
            if (hypoxemiaLevel.isEmpty()) {
                Toast.makeText(this, "Evaluation not ready yet", Toast.LENGTH_SHORT).show();
                return;
            }
            saveOxygenRequirement();
        });

        setupBottomNav();
    }

    private void fetchSpo2FromVitals() {
        if (patientId == -1) {
            Toast.makeText(this, "Invalid patient ID", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        apiService.getPatientDetails(patientId).enqueue(new Callback<PatientDetailResponse>() {
            @Override
            public void onResponse(Call<PatientDetailResponse> call, Response<PatientDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PatientDetailResponse details = response.body();
                    String spo2Str = details.getSpo2();

                    try {
                        spo2Value = Double.parseDouble(spo2Str);
                    } catch (Exception e) {
                        spo2Value = 0.0;
                    }

                    evaluateAndPopulateUI();
                } else {
                    Toast.makeText(OxygenRequirementActivity.this, "Failed to fetch patient data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PatientDetailResponse> call, Throwable t) {
                Toast.makeText(OxygenRequirementActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void evaluateAndPopulateUI() {
        // Determine hypoxemia level
        if (spo2Value < 88) {
            hypoxemiaLevel = "Severe";
        } else if (spo2Value <= 92) {
            hypoxemiaLevel = "Moderate";
        } else {
            hypoxemiaLevel = "Normal";
        }

        // Determine symptoms level
        if (spo2Value < 88) {
            symptomsLevel = "Severe";
        } else if (spo2Value <= 92) {
            symptomsLevel = "Moderate";
        } else {
            symptomsLevel = "Mild";
        }

        // Determine oxygen requirement
        if (spo2Value < 88) {
            oxygenRequired = "Yes";
        } else if (spo2Value <= 92) {
            oxygenRequired = "Monitor";
        } else {
            oxygenRequired = "No";
        }

        // Update UI
        tvSpo2Value.setText((int) spo2Value + " %");
        tvHypoxemiaLevel.setText(hypoxemiaLevel);
        tvSymptomsLevel.setText(symptomsLevel);

        if ("Yes".equals(oxygenRequired)) {
            tvTherapyIndicated.setText("Oxygen Therapy Indicated");
            tvTherapyDesc.setText("Patient meets criteria for supplemental oxygen therapy due to persistent hypoxemia (SpO\u2082 < 88%).");
        } else if ("Monitor".equals(oxygenRequired)) {
            tvTherapyIndicated.setText("Monitoring Required");
            tvTherapyDesc.setText("Patient SpO\u2082 is borderline (88\u201392%). Close monitoring and reassessment recommended.");
        } else {
            tvTherapyIndicated.setText("Oxygen Not Required");
            tvTherapyDesc.setText("Patient SpO\u2082 is within normal range. Continue standard care.");
        }
    }

    private void saveOxygenRequirement() {
        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        Map<String, Object> body = new HashMap<>();
        body.put("patient_id", patientId);
        body.put("spo2", spo2Value);
        body.put("hypoxemia_level", hypoxemiaLevel);
        body.put("symptoms_level", symptomsLevel);
        body.put("oxygen_required", oxygenRequired);

        apiService.setOxygenRequirement(body).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(OxygenRequirementActivity.this, "Oxygen requirement saved successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OxygenRequirementActivity.this, DeviceSelectionActivity.class);
                    intent.putExtra("patient_id", patientId);
                    startActivity(intent);
                } else {
                    Toast.makeText(OxygenRequirementActivity.this, "Failed to save oxygen requirement", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(OxygenRequirementActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                startActivity(new Intent(this, DoctorAlertsActivity.class));
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