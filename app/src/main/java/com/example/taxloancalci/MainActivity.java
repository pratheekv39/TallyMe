package com.example.taxloancalci;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView1 = findViewById(R.id.gsttax);
        ImageView imageView2 = findViewById(R.id.emic);
        Button viewHistoryButton = findViewById(R.id.history_button);
        Button sendFeedbackButton = findViewById(R.id.feedback_button);

        imageView1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, gst_calculator.class);
            startActivity(intent);
        });

        imageView2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, emi.class);
            startActivity(intent);
        });

        viewHistoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        sendFeedbackButton.setOnClickListener(v -> sendFeedback());
    }

    private void sendFeedback() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:your.email@example.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Tax-Loan Calci App");

        try {
            startActivity(Intent.createChooser(intent, "Send Feedback"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}