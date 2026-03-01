package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Profile Click
        findViewById(R.id.iv_admin_profile).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminProfileActivity.class));
        });

        // Navigation Buttons
        findViewById(R.id.btn_manage_doctors).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminManageDoctorsActivity.class));
        });

        findViewById(R.id.btn_manage_staff).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminManageStaffActivity.class));
        });

        findViewById(R.id.btn_approval_requests).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminApprovalsActivity.class));
        });

        // Summary Cards
        findViewById(R.id.card_total_doctors).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminManageDoctorsActivity.class));
        });

        findViewById(R.id.card_total_staff).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminManageStaffActivity.class));
        });

        findViewById(R.id.card_pending_requests).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminApprovalsActivity.class));
        });
    }
}