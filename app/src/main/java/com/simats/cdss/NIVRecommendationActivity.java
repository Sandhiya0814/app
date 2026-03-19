package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.simats.cdss.models.NIVRecommendationResponse;
import com.simats.cdss.network.ApiService;
import com.simats.cdss.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NIVRecommendationActivity extends AppCompatActivity {
    
    private static final String TAG = "NIVRecommendation";
    private int patientId;
    private TextView tvNivMode, tvNivIndication, tvIpapVal, tvEpapVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niv_recommendation);

        patientId = getIntent().getIntExtra("patient_id", -1);

        tvNivMode = findViewById(R.id.tv_niv_mode);
        tvNivIndication = findViewById(R.id.tv_niv_indication);
        tvIpapVal = findViewById(R.id.tv_ipap_val);
        tvEpapVal = findViewById(R.id.tv_epap_val);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        // Navigation to Urgent Action Screen
        findViewById(R.id.btn_check_icu).setOnClickListener(v -> {
            Intent intent = new Intent(this, UrgentActionActivity.class);
            intent.putExtra("patient_id", patientId);
            startActivity(intent);
        });

        setupBottomNav();
        
        if (patientId != -1) {
            setupNivData();
        } else {
            Toast.makeText(this, "Invalid patient ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupNivData() {
        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        apiService.getNIVRecommendation(patientId).enqueue(new Callback<NIVRecommendationResponse>() {
            @Override
            public void onResponse(Call<NIVRecommendationResponse> call, Response<NIVRecommendationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NIVRecommendationResponse data = response.body();
                    tvNivMode.setText(data.getMode() + " Indicated");
                    if (data.getIndication() != null && !data.getIndication().isEmpty()) {
                        tvNivIndication.setText(data.getIndication());
                    } else {
                        tvNivIndication.setText("Non-Invasive Ventilation is recommended.");
                    }
                    tvIpapVal.setText(String.valueOf((int) data.getIpap()));
                    tvEpapVal.setText(String.valueOf((int) data.getEpap()));
                } else {
                    Log.e(TAG, "Failed to load NIV recommendation");
                }
            }

            @Override
            public void onFailure(Call<NIVRecommendationResponse> call, Throwable t) {
                Log.e(TAG, "Error fetching NIV data", t);
                Toast.makeText(NIVRecommendationActivity.this, "Network error", Toast.LENGTH_SHORT).show();
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