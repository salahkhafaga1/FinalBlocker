package com.example.finalblocker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnLock, btnPerm;
    TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLock = findViewById(R.id.btnLock);
        btnPerm = findViewById(R.id.btnPermission);
        txtStatus = findViewById(R.id.txtStatus);

        updateStatus();

        btnPerm.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)));

        btnLock.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("BlockerPrefs", MODE_PRIVATE);
            // ضبط الوقت لدقيقتين فقط للتجربة
            long targetTime = System.currentTimeMillis() + (2 * 60 * 1000);
            prefs.edit().putLong("UNLOCK_TIME", targetTime).apply();
            updateStatus();
        });
    }

    private void updateStatus() {
        long unlockTime = getSharedPreferences("BlockerPrefs", MODE_PRIVATE).getLong("UNLOCK_TIME", 0);
        if (System.currentTimeMillis() < unlockTime) {
            txtStatus.setText("⛔ محظور!");
            btnLock.setEnabled(false);
        }
    }
}