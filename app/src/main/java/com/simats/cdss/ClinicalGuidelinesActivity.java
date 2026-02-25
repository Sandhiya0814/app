package com.simats.cdss;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ClinicalGuidelinesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinical_guidelines);

        findViewById(R.id.iv_back).setOnClickListener(v -> onBackPressed());

        // Handle click on "View PDF" for GOLD 2024 Report
        findViewById(R.id.btn_view_gold).setOnClickListener(v -> {
            openPdfFromDrawable("GOLD-2024.pdf");
        });

        setupBottomNav();
    }

    private void openPdfFromDrawable(String fileName) {
        try {
            // Copy file from drawable to internal storage to open it via Intent
            // Note: Standard way is to put PDFs in 'assets' or 'raw', 
            // but since it's in drawable as requested:
            InputStream is = getResources().openRawResource(
                    getResources().getIdentifier("gold_2024", "drawable", getPackageName())
            );
            
            File file = new File(getExternalFilesDir(null), fileName);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
            fos.flush();
            fos.close();
            is.close();

            // Create URI using FileProvider
            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);

            // Create Intent to view PDF
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);

            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(this, "Unable to open PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_settings);
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