package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            onBackPressed();
        });

        findViewById(R.id.btn_send_reset_link).setOnClickListener(v -> {
            showSuccessBottomSheet();
        });
    }

    private void showSuccessBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.layout_success_bottom_sheet, null);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCancelable(false);

        view.findViewById(R.id.btn_done).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            finish(); // Go back to login
        });

        bottomSheetDialog.show();
    }
}