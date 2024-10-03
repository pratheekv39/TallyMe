package com.example.taxloancalci;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.taxloancalci.data.AppDatabase;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private CardView gstCalculatorCard;
    private CardView emiCalculatorCard;
    private Button viewHistoryButton;
    private Button sendFeedbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database
        db = AppDatabase.getDatabase(this);

        // Initialize views
        gstCalculatorCard = findViewById(R.id.gst_calculator_card);
        emiCalculatorCard = findViewById(R.id.emi_calculator_card);
        viewHistoryButton = findViewById(R.id.history_button);
        sendFeedbackButton = findViewById(R.id.feedback_button);

        // Set click listeners
        gstCalculatorCard.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, gst_calculator.class)));
        emiCalculatorCard.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, emi.class)));
        viewHistoryButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HistoryActivity.class)));
        sendFeedbackButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FeedbackActivity.class)));

        // Handle back press
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitDialog();
            }
        });
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
}