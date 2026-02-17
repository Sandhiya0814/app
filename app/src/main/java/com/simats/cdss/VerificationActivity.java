package com.simats.cdss;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    private EditText otp1, otp2, otp3, otp4, otp5, otp6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        otp1 = findViewById(R.id.otp_1);
        otp2 = findViewById(R.id.otp_2);
        otp3 = findViewById(R.id.otp_3);
        otp4 = findViewById(R.id.otp_4);
        otp5 = findViewById(R.id.otp_5);
        otp6 = findViewById(R.id.otp_6);

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            onBackPressed();
        });

        findViewById(R.id.tv_resend).setOnClickListener(v -> {
            Toast.makeText(this, "verification code sent successfully", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_verify).setOnClickListener(v -> {
            startActivity(new Intent(VerificationActivity.this, TermsActivity.class));
        });

        setupOtpInputs();
    }

    private void setupOtpInputs() {
        otp1.addTextChangedListener(new OtpTextWatcher(otp1, otp2));
        otp2.addTextChangedListener(new OtpTextWatcher(otp2, otp3));
        otp3.addTextChangedListener(new OtpTextWatcher(otp3, otp4));
        otp4.addTextChangedListener(new OtpTextWatcher(otp4, otp5));
        otp5.addTextChangedListener(new OtpTextWatcher(otp5, otp6));
        otp6.addTextChangedListener(new OtpTextWatcher(otp6, null));

        // Handle backspace
        otp2.setOnKeyListener(new OtpKeyListener(otp2, otp1));
        otp3.setOnKeyListener(new OtpKeyListener(otp3, otp2));
        otp4.setOnKeyListener(new OtpKeyListener(otp4, otp3));
        otp5.setOnKeyListener(new OtpKeyListener(otp5, otp4));
        otp6.setOnKeyListener(new OtpKeyListener(otp6, otp5));
    }

    private class OtpTextWatcher implements TextWatcher {
        private EditText currentView;
        private EditText nextView;

        public OtpTextWatcher(EditText currentView, EditText nextView) {
            this.currentView = currentView;
            this.nextView = nextView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 1 && nextView != null) {
                nextView.requestFocus();
            }
        }
    }

    private class OtpKeyListener implements android.view.View.OnKeyListener {
        private EditText currentView;
        private EditText previousView;

        public OtpKeyListener(EditText currentView, EditText previousView) {
            this.currentView = currentView;
            this.previousView = previousView;
        }

        @Override
        public boolean onKey(android.view.View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL
                    && currentView.getText().toString().isEmpty() && previousView != null) {
                previousView.requestFocus();
                return true;
            }
            return false;
        }
    }
}