package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.simats.cdss.adapters.TrendIndicatorsAdapter;
import com.simats.cdss.models.TrendAnalysisResponse;
import com.simats.cdss.network.ApiService;
import com.simats.cdss.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendAnalysisActivity extends AppCompatActivity {

    private int patientId;
    private TextView tvStatus;
    private ImageView ivTrendTop;
    private RecyclerView rvTrendIndicators;
    private TrendIndicatorsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_analysis);
        
        patientId = getIntent().getIntExtra("patient_id", -1);

        tvStatus = findViewById(R.id.tv_status);
        ivTrendTop = findViewById(R.id.iv_trend_top);
        rvTrendIndicators = findViewById(R.id.rv_trend_indicators);

        rvTrendIndicators.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrendIndicatorsAdapter(new ArrayList<>());
        rvTrendIndicators.setAdapter(adapter);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        // Navigation to Cause of Hypoxemia screen
        findViewById(R.id.btn_proceed).setOnClickListener(v -> {
            Intent intent = new Intent(this, HypoxemiaCauseActivity.class);
            intent.putExtra("patient_id", patientId);
            startActivity(intent);
        });

        setupBottomNav();
        fetchTrendData();
    }

    private void fetchTrendData() {
        if (patientId == -1) {
            Toast.makeText(this, "Invalid patient ID", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        apiService.getPatientTrendAnalysis(patientId).enqueue(new Callback<TrendAnalysisResponse>() {
            @Override
            public void onResponse(Call<TrendAnalysisResponse> call, Response<TrendAnalysisResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    populateUI(response.body());
                } else {
                    Toast.makeText(TrendAnalysisActivity.this, "Failed to load Trend Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TrendAnalysisResponse> call, Throwable t) {
                Toast.makeText(TrendAnalysisActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateUI(TrendAnalysisResponse data) {
        tvStatus.setText(data.getTrendStatus());
        
        String status = data.getTrendStatus();
        if ("WORSENING".equalsIgnoreCase(status)) {
            ivTrendTop.setImageResource(R.drawable.trend);
        } else if ("STABLE".equalsIgnoreCase(status)) {
            // Placeholder: Assume they have a stable graphic or we can clear tint
            ivTrendTop.setImageResource(R.drawable.trend); 
        } else if ("IMPROVING".equalsIgnoreCase(status)) {
             // Placeholder: Assume they have an improving graphic or we can adjust tint
            ivTrendTop.setImageResource(R.drawable.trend); 
        }

        if (data.getTrendIndicators() != null) {
            adapter.setIndicators(data.getTrendIndicators());
        }
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
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
                if ("staff".equals(role)) {
                    startActivity(new Intent(this, StaffPatientsActivity.class));
                } else {
                    startActivity(new Intent(this, PatientListActivity.class));
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