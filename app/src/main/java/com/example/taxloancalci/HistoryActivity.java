package com.example.taxloancalci;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taxloancalci.data.AppDatabase;
import com.example.taxloancalci.data.CalculationEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    private AppDatabase db;
    private ListView historyListView;
    private Button backButton;
    private Button clearHistoryButton;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        db = AppDatabase.getDatabase(this);
        historyListView = findViewById(R.id.history_list_view);
        backButton = findViewById(R.id.back_button);
        clearHistoryButton = findViewById(R.id.clear_history_button);

        backButton.setOnClickListener(v -> finish());

        clearHistoryButton.setOnClickListener(v -> showClearHistoryConfirmation());

        loadHistory();
    }

    private void loadHistory() {
        new Thread(() -> {
            List<CalculationEntity> calculations = db.calculationDao().getAllCalculations();
            List<String> historyItems = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

            for (CalculationEntity calc : calculations) {
                String dateString = sdf.format(new Date(calc.timestamp));
                historyItems.add(calc.type + " - " + dateString + "\n" + calc.details);
            }

            runOnUiThread(() -> {
                adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, historyItems);
                historyListView.setAdapter(adapter);
            });
        }).start();
    }

    private void showClearHistoryConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Clear History")
                .setMessage("Are you sure you want to clear all calculation history?")
                .setPositiveButton("Yes", (dialog, which) -> clearHistory())
                .setNegativeButton("No", null)
                .show();
    }

    private void clearHistory() {
        new Thread(() -> {
            db.calculationDao().deleteAllCalculations();
            runOnUiThread(() -> {
                adapter.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(HistoryActivity.this, "History cleared", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}