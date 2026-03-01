package com.simats.cdss;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdminManageDoctorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_doctors);

        findViewById(R.id.iv_back).setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        // Setup actions for mock items - Using updated IDs from XML
        setupDoctorActions(R.id.btn_details_1, R.id.btn_remove_1, "Dr. Sarah Wilson");
        setupDoctorActions(R.id.btn_details_2, R.id.btn_remove_2, "Dr. Michael Brown");
    }

    private void setupDoctorActions(int detailsId, int removeId, String name) {
        findViewById(detailsId).setOnClickListener(v -> 
            Toast.makeText(this, "Viewing clinical statistics for " + name, Toast.LENGTH_SHORT).show()
        );

        findViewById(removeId).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Revoke Access")
                .setMessage("Are you sure you want to permanently remove " + name + " from the physician directory? This action cannot be undone and will disable their login.")
                .setPositiveButton("Remove Permanently", (dialog, which) -> 
                    Toast.makeText(this, name + " access revoked", Toast.LENGTH_SHORT).show()
                )
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        });
    }
}