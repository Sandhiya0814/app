package com.simats.cdss;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdminManageStaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_staff);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        // Setup actions for mock items
        setupStaffActions(R.id.btn_view_staff_1, R.id.btn_delete_staff_1, "Staff David Miller");
        setupStaffActions(R.id.btn_view_staff_2, R.id.btn_delete_staff_2, "Staff Emily Watson");
    }

    private void setupStaffActions(int viewId, int deleteId, String name) {
        findViewById(viewId).setOnClickListener(v -> {
            Toast.makeText(this, "Viewing details for " + name, Toast.LENGTH_SHORT).show();
        });

        findViewById(deleteId).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Remove Staff Account")
                .setMessage("Are you sure you want to permanently remove " + name + " from the system? This action cannot be undone.")
                .setPositiveButton("Remove", (dialog, which) -> {
                    Toast.makeText(this, name + " removed", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        });
    }
}