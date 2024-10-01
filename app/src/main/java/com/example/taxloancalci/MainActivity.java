package com.example.taxloancalci;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
    }
}