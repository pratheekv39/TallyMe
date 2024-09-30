package com.example.taxloancalci;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Switch;

import com.example.taxloancalci.data.AppDatabase;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private ImageView gstTaxImage;
    private ImageView emiImage;
    private Button viewHistoryButton;
    private Button sendFeedbackButton;
    private Switch darkModeSwitch;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply the theme before calling super.onCreate and setContentView
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("DarkMode", false);
        setTheme(isDarkMode);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database
        db = AppDatabase.getDatabase(this);

        // Initialize views
        gstTaxImage = findViewById(R.id.gsttax);
        emiImage = findViewById(R.id.emic);
        viewHistoryButton = findViewById(R.id.history_button);
        sendFeedbackButton = findViewById(R.id.feedback_button);
        darkModeSwitch = findViewById(R.id.theme_switch);

        // Set the initial state of the switch
        darkModeSwitch.setChecked(isDarkMode);

        // Set click listeners
        gstTaxImage.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, gst_calculator.class)));
        emiImage.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, emi.class)));
        viewHistoryButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HistoryActivity.class)));
        sendFeedbackButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FeedbackActivity.class)));

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setTheme(isChecked);
            sharedPreferences.edit().putBoolean("DarkMode", isChecked).apply();
            recreate();
        });
    }

    private void setTheme(boolean isDarkMode) {
        AppCompatDelegate.setDefaultNightMode(isDarkMode ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void recreate() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(getIntent());
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if the theme has changed while in another activity
        boolean currentTheme = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        if (currentTheme != sharedPreferences.getBoolean("DarkMode", false)) {
            recreate();
        }
    }
}