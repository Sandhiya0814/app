package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.simats.cdss.adapters.NeedsAttentionAdapter;
import com.simats.cdss.models.DoctorDashboardResponse;
import com.simats.cdss.network.ApiService;
import com.simats.cdss.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctordashboardActivity extends AppCompatActivity {

    private TextView tvTotalCount, tvCriticalCount, tvWarningCount;
    private RecyclerView rvNeedsAttention;
    private NeedsAttentionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        // Initialize UI
        tvTotalCount = findViewById(R.id.tv_total_count);
        tvCriticalCount = findViewById(R.id.tv_critical_count);
        tvWarningCount = findViewById(R.id.tv_warning_count);
        rvNeedsAttention = findViewById(R.id.rv_needs_attention);

        // Setup RecyclerView
        rvNeedsAttention.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NeedsAttentionAdapter(this, new ArrayList<>());
        rvNeedsAttention.setAdapter(adapter);

        // Navigation to Patient List (All)
        findViewById(R.id.card_total_patients).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorPatientsActivity.class));
        });

        findViewById(R.id.card_patient_list).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorPatientsActivity.class));
        });

        findViewById(R.id.tv_view_all).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorPatientsActivity.class));
        });

        // Navigation to Critical Patients
        findViewById(R.id.card_critical_patients).setOnClickListener(v -> {
            Intent intent = new Intent(this, DoctorPatientsActivity.class);
            intent.putExtra("filter", "critical");
            startActivity(intent);
        });

        // Navigation to Warning Patients
        findViewById(R.id.card_warning_patients).setOnClickListener(v -> {
            Intent intent = new Intent(this, DoctorPatientsActivity.class);
            intent.putExtra("filter", "warning");
            startActivity(intent);
        });

        // Notification bell explicitly to DoctorAlertsActivity
        findViewById(R.id.card_notifications).setOnClickListener(v -> {
             startActivity(new Intent(this, DoctorAlertsActivity.class));
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_patients) {
                startActivity(new Intent(this, DoctorPatientsActivity.class));
                return true;
            } else if (itemId == R.id.nav_alerts) {
                startActivity(new Intent(this, DoctorAlertsActivity.class));
                return true;
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
            return itemId == R.id.nav_home;
        });

        // Fetch Dashboard Data
        fetchDashboardData();
    }

    private void fetchDashboardData() {
        ApiService apiService = RetrofitClient.getClient(this).create(ApiService.class);
        apiService.getDoctorDashboard().enqueue(new Callback<DoctorDashboardResponse>() {
            @Override
            public void onResponse(Call<DoctorDashboardResponse> call, Response<DoctorDashboardResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DoctorDashboardResponse data = response.body();
                    
                    // Update Counts
                    tvTotalCount.setText(String.valueOf(data.getTotalPatients()));
                    tvCriticalCount.setText(String.valueOf(data.getCriticalCount()));
                    tvWarningCount.setText(String.valueOf(data.getWarningCount()));
                    
                    // Update List
                    if (data.getNeedsAttentionPatients() != null) {
                        adapter.updateList(data.getNeedsAttentionPatients());
                    }
                } else {
                    Toast.makeText(DoctordashboardActivity.this, "Failed to load dashboard statistics", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DoctorDashboardResponse> call, Throwable t) {
                Toast.makeText(DoctordashboardActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}