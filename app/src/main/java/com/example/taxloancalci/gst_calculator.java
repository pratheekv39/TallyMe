package com.example.taxloancalci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.taxloancalci.data.AppDatabase;
import com.example.taxloancalci.data.CalculationEntity;

public class gst_calculator extends AppCompatActivity {

    private AppDatabase db;
    private float totalPrice, gstPercentage, netPrice, gstPrice;
    private SeekBar totalPriceSeekBar, gstPercentageSeekBar;
    private TextView totalPriceTextView, gstPercentageTextView, netPriceTextView, gstPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gst_calculator);

        db = AppDatabase.getDatabase(this);

        totalPriceSeekBar = findViewById(R.id.total_price_seekbar);
        gstPercentageSeekBar = findViewById(R.id.gst_percentage_seekbar);
        totalPriceTextView = findViewById(R.id.total_price_textview);
        gstPercentageTextView = findViewById(R.id.gst_percentage_textview);
        netPriceTextView = findViewById(R.id.net_price_textview);
        gstPriceTextView = findViewById(R.id.gst_price_textview);
        Button backButton = findViewById(R.id.back_to_main_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCalculation();
                Intent intent = new Intent(gst_calculator.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        totalPriceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateCalculation(progress, gstPercentageSeekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        gstPercentageSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateCalculation(totalPriceSeekBar.getProgress(), progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void saveCalculation() {
        String details = String.format("Total Price: ₹%.2f, GST: %.2f%%, Net Price: ₹%.2f, GST Price: ₹%.2f",
                totalPrice, gstPercentage, netPrice, gstPrice);
        CalculationEntity calculation = new CalculationEntity("GST", details, System.currentTimeMillis());

        new Thread(() -> db.calculationDao().insert(calculation)).start();
    }

    private void updateCalculation(float totalPrice, float gstPercentage) {
        this.totalPrice = totalPrice;
        this.gstPercentage = gstPercentage;
        this.gstPrice = totalPrice * (gstPercentage / 100);
        this.netPrice = totalPrice / (1 + (gstPercentage / 100));

        totalPriceTextView.setText("Total Price: ₹" + totalPrice);
        gstPercentageTextView.setText("GST Percentage: " + gstPercentage + "%");
        netPriceTextView.setText("Net Price: ₹" + String.format("%.2f", netPrice));
        gstPriceTextView.setText("GST Price: ₹" + String.format("%.2f", gstPrice));
    }
}