package io.pratheekv39.tallyme;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import io.pratheekv39.tallyme.data.AppDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if dark mode is enabled
        if (isDarkModeOn()) {
            showDarkModeAlert();
        }

        // Initialize database
        AppDatabase db = AppDatabase.getDatabase(this);

        // Initialize views
        CardView gstCalculatorCard = findViewById(R.id.gst_calculator_card);
        CardView emiCalculatorCard = findViewById(R.id.emi_calculator_card);
        Button viewHistoryButton = findViewById(R.id.history_button);
        Button sendFeedbackButton = findViewById(R.id.feedback_button);

        // Set click listeners
        gstCalculatorCard.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, gst_calculator.class)));
        emiCalculatorCard.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, emi.class)));
        viewHistoryButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HistoryActivity.class)));
        sendFeedbackButton.setOnClickListener(v -> showRatingDialog());

        // Handle back press
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitDialog();
            }
        });
    }

    private boolean isDarkModeOn() {
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }

    private void showDarkModeAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Dark Mode Detected")
                .setMessage("Dark mode is currently enabled. For a better experience, it's recommended to use light mode. Would you like to disable dark mode?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openDisplaySettings();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void openDisplaySettings() {
        Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
        startActivity(intent);
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Exit App")
                .setMessage("Do you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity(); // This will close all activities and exit the app
                    }
                })
                .setNegativeButton("No", null) // Null listener does nothing, which dismisses the dialog
                .show();
    }

    private void showRatingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rate Us");

        // Create a container layout to properly constrain the RatingBar
        LinearLayout container = new LinearLayout(this);
        container.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        container.setGravity(Gravity.CENTER);
        container.setPadding(24, 24, 24, 24);

        // Create and configure RatingBar with proper styling
        final RatingBar ratingBar = new RatingBar(this, null, android.R.attr.ratingBarStyle);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1);
        ratingBar.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Add RatingBar to container
        container.addView(ratingBar);
        builder.setView(container);

        builder.setPositiveButton("Rate Now", (dialog, which) -> {
            float rating = ratingBar.getRating();
            if (rating > 0) {
                openPlayStore();
            } else {
                Toast.makeText(MainActivity.this, "Please provide a rating", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Rate Later", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openPlayStore() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=io.pratheekv39.tallyme")));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=io.pratheekv39.tallyme")));
        }
    }
}
