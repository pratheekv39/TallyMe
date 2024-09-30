package com.example.taxloancalci;
import com.google.firebase.firestore.FirebaseFirestore;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taxloancalci.data.AppDatabase;
import com.example.taxloancalci.data.FeedbackEntity;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, messageEditText;
    private Button submitButton, rateButton, backButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        db = FirebaseFirestore.getInstance(); // Initialize Firestore

        nameEditText = findViewById(R.id.name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        messageEditText = findViewById(R.id.message_edit_text);
        submitButton = findViewById(R.id.submit_button);
        rateButton = findViewById(R.id.rate_button);
        backButton = findViewById(R.id.back_button);

        submitButton.setOnClickListener(v -> submitFeedback());
        rateButton.setOnClickListener(v -> openGooglePlay());
        backButton.setOnClickListener(v -> navigateBackHome());
    }

    private void submitFeedback() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a feedback object
        Map<String, Object> feedback = new HashMap<>();
        feedback.put("name", name);
        feedback.put("email", email);
        feedback.put("message", message);
        feedback.put("timestamp", System.currentTimeMillis());

        // Store feedback in Firestore
        db.collection("feedback")
                .add(feedback)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(FeedbackActivity.this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(FeedbackActivity.this, "Error submitting feedback", Toast.LENGTH_SHORT).show();
                });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void openGooglePlay() {
        String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void navigateBackHome() {
        Intent intent = new Intent(FeedbackActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
