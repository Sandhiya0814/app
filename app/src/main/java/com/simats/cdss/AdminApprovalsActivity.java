package com.simats.cdss;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdminApprovalsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_approvals);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        // Setup Approve/Reject listeners for items (Mock data)
        setupRequestActions(R.id.btn_approve_1, R.id.btn_reject_1, "Dr. James Carter");
        setupRequestActions(R.id.btn_approve_2, R.id.btn_reject_2, "Staff Sarah Miller");
    }

    private void setupRequestActions(int approveId, int rejectId, String name) {
        findViewById(approveId).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Approve Request")
                .setMessage("Are you sure you want to approve " + name + "?")
                .setPositiveButton("Approve", (dialog, which) -> {
                    Toast.makeText(this, name + " approved", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });

        findViewById(rejectId).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Reject Request")
                .setMessage("Are you sure you want to reject " + name + "?")
                .setPositiveButton("Reject", (dialog, which) -> {
                    Toast.makeText(this, name + " rejected", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
        });
    }
}