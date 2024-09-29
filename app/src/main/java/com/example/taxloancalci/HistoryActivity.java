package com.example.taxloancalci;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<HistoryItem> historyItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        historyItems = getHistoryItems(); // You'll need to implement this method
        adapter = new HistoryAdapter(historyItems);
        recyclerView.setAdapter(adapter);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }

    private List<HistoryItem> getHistoryItems() {
        // This is where you'd typically fetch the history from a database or shared preferences
        // For now, we'll just return some dummy data
        List<HistoryItem> items = new ArrayList<>();
        items.add(new HistoryItem("GST Calculation", "Total: ₹1000, GST: ₹180", "2023-05-01"));
        items.add(new HistoryItem("EMI Calculation", "Loan: ₹100000, EMI: ₹2000", "2023-05-02"));
        return items;
    }
}