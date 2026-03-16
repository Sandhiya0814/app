package com.simats.cdss;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.simats.cdss.adapters.KeyFactorsAdapter;
import com.simats.cdss.models.AIRiskResponse;
import com.simats.cdss.network.ApiService;
import com.simats.cdss.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AIAnalysisActivity extends AppCompatActivity {

    private int patientId;
    private TextView tvRiskLevel, tvConfidenceScore;
    private RecyclerView rvKeyFactors;
    private KeyFactorsAdapter factorsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_analysis);

        patientId = getIntent().getIntExtra("patient_id", -1);

        tvRiskLevel = findViewById(R.id.tv_risk_level);
        tvConfidenceScore = findViewById(R.id.tv_confidence_score);
        rvKeyFactors = findViewById(R.id.rv_key_factors);

        rvKeyFactors.setLayoutManager(new LinearLayoutManager(this));
        factorsAdapter = new KeyFactorsAdapter(new ArrayList<>());
        rvKeyFactors.setAdapter(factorsAdapter);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        // Navigation to Trend Analysis screen
        findViewById(R.id.btn_view_trends).setOnClickListener(v -> {
            Intent intent = new Intent(this, TrendAnalysisActivity.class);
            intent.putExtra("patient_id", patientId);
            startActivity(intent);
        });

        setupBottomNav();
        fetchAIRiskData();
    }

    private void fetchAIRiskData() {
        if (patientId == -1) {
            Toast.makeText(this, "Invalid patient ID", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        apiService.getPatientAIRisk(patientId).enqueue(new Callback<AIRiskResponse>() {
            @Override
            public void onResponse(Call<AIRiskResponse> call, Response<AIRiskResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    populateUI(response.body());
                } else {
                    Toast.makeText(AIAnalysisActivity.this, "Failed to load AI Risk Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AIRiskResponse> call, Throwable t) {
                Toast.makeText(AIAnalysisActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateUI(AIRiskResponse data) {
        tvRiskLevel.setText(data.getRiskLevel());
        tvConfidenceScore.setText("Confidence Score: " + data.getConfidenceScore() + "%");

        String risk = data.getRiskLevel();
        if ("HIGH RISK".equalsIgnoreCase(risk)) {
            tvRiskLevel.setTextColor(Color.parseColor("#B91C1C"));
        } else if ("WARNING".equalsIgnoreCase(risk)) {
            tvRiskLevel.setTextColor(Color.parseColor("#F59E0B"));
        } else {
            tvRiskLevel.setTextColor(Color.parseColor("#10B981"));
        }

        if (data.getKeyFactors() != null) {
            factorsAdapter.setFactors(data.getKeyFactors());
        }
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_patients);
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
                    // Fix: Use role-specific patient activities as PatientListActivity was renamed/replaced
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
