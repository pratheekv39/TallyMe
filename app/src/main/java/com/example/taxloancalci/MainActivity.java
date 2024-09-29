package com.example.taxloancalci;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Switch themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView1 = findViewById(R.id.gsttax);
        ImageView imageView2 = findViewById(R.id.emic);
        themeSwitch = findViewById(R.id.theme_switch);
        Button historyButton = findViewById(R.id.history_button);
        Button feedbackButton = findViewById(R.id.feedback_button);
        TextView versionInfo = findViewById(R.id.version_info);

        // Set version info
        versionInfo.setText("Version " + BuildConfig.VERSION_NAME);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("DarkMode", false);

        // Set initial switch state
        themeSwitch.setChecked(isDarkMode);

        // Set theme based on saved preference
        setTheme(isDarkMode);

        imageView1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, gst_calculator.class);
            startActivity(intent);
        });

        imageView2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, emi.class);
            startActivity(intent);
        });

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setTheme(isChecked);
            sharedPreferences.edit().putBoolean("DarkMode", isChecked).apply();
            recreate(); // Recreate the activity to apply the new theme
        });

        historyButton.setOnClickListener(v -> {
            // TODO: Implement HistoryActivity and uncomment the following lines
            // Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            // startActivity(intent);
            Toast.makeText(this, "History feature coming soon!", Toast.LENGTH_SHORT).show();
        });

        feedbackButton.setOnClickListener(v -> sendFeedback());
    }

    private void setTheme(boolean isDarkMode) {
        AppCompatDelegate.setDefaultNightMode(isDarkMode ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void sendFeedback() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"your.email@example.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for TaxLoan Calci App");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }
}