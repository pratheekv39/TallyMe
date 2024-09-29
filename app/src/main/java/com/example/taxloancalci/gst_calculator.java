package com.example.taxloancalci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class gst_calculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gst_calculator);
        SeekBar totalPriceSeekBar = findViewById(R.id.total_price_seekbar);
        SeekBar gstPercentageSeekBar = findViewById(R.id.gst_percentage_seekbar);
        TextView totalPriceTextView = findViewById(R.id.total_price_textview);
        TextView gstPercentageTextView = findViewById(R.id.gst_percentage_textview);
        TextView netPriceTextView = findViewById(R.id.net_price_textview);
        TextView gstPriceTextView = findViewById(R.id.gst_price_textview);
        Button backButton = findViewById(R.id.back_to_main_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gst_calculator.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        totalPriceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                totalPriceTextView.setText("Total Price: " + progress);

                float totalPrice = progress;
                float gstPercentage = (float)gstPercentageSeekBar.getProgress() / 100;
                float gstPrice =totalPrice*(gstPercentage);
                float netPrice = totalPrice/(1+(gstPercentage));

                netPriceTextView.setText("Net Price: " + netPrice);
                gstPriceTextView.setText("GST Price: " + gstPrice);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        gstPercentageSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gstPercentageTextView.setText("GST Percentage: " + progress + "%");


                float totalPrice = totalPriceSeekBar.getProgress();
                float gstPercentage = progress;
                float netPrice = totalPrice / (1 + (gstPercentage / 100));
                float gstPrice = totalPrice - netPrice;


                netPriceTextView.setText("Net Price: " + netPrice);
                gstPriceTextView.setText("GST Price: " + gstPrice);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}