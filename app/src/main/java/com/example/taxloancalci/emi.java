package com.example.taxloancalci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class emi extends AppCompatActivity {

    private SeekBar totalPriceSeekBar;
    private SeekBar downPaymentSeekBar;
    private SeekBar interestRateSeekBar;
    private SeekBar tenureSeekBar;

    private TextView totalPriceTextView;
    private TextView downPaymentTextView;
    private TextView interestRateTextView;
    private TextView tenureTextView;
    private TextView emiTextView;
    private  TextView loanTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi);

        totalPriceSeekBar = findViewById(R.id.total_price_seekbar);
        downPaymentSeekBar = findViewById(R.id.down_payment_seekbar);
        interestRateSeekBar = findViewById(R.id.interest_rate_seekbar);
        tenureSeekBar = findViewById(R.id.tenure_seekbar);

        totalPriceTextView = findViewById(R.id.total_price_textview);
        downPaymentTextView = findViewById(R.id.down_payment_textview);
        interestRateTextView = findViewById(R.id.interest_rate_textview);
        tenureTextView = findViewById(R.id.tenure_textview);
        emiTextView = findViewById(R.id.emi_textview);
        loanTextview=findViewById(R.id.loan_amount_textview);

        totalPriceSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        downPaymentSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        interestRateSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        tenureSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        Button backButton = findViewById(R.id.back_to_main_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(emi.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optionally call finish() to close the current activity
            }
        });

    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            if (seekBar == totalPriceSeekBar) {
                totalPriceTextView.setText("Total Price: ₹" + progress);
                float totalPrice = totalPriceSeekBar.getProgress();
                float downPayment = downPaymentSeekBar.getProgress();
                float principalAmount = totalPrice - downPayment;
                loanTextview.setText("loan_amount:₹ " +principalAmount);

            } else if (seekBar == downPaymentSeekBar) {
                downPaymentTextView.setText("Down Payment: ₹" + progress);
                float totalPrice = totalPriceSeekBar.getProgress();
                float downPayment = downPaymentSeekBar.getProgress();
                float principalAmount = totalPrice - downPayment;
                loanTextview.setText("loan_amount: " +principalAmount);
            } else if (seekBar == interestRateSeekBar) {
                interestRateTextView.setText("Interest Rate: " + progress + "%");
            } else if (seekBar == tenureSeekBar) {
                tenureTextView.setText("Tenure: " + progress + " years");
            }

            float totalPrice = totalPriceSeekBar.getProgress();
            float downPayment = downPaymentSeekBar.getProgress();
            float principalAmount = totalPrice - downPayment;


            float interestRate = interestRateSeekBar.getProgress();
            float tenure = tenureSeekBar.getProgress();
            float monthlyInterestRate = (interestRate / 12) / 100;
            float numPayments = tenure * 12;
            float emi = (principalAmount * monthlyInterestRate * (float) Math.pow(1 + monthlyInterestRate, numPayments))
                    / (float) (Math.pow(1 + monthlyInterestRate, numPayments) - 1);

            emiTextView.setText("EMI: ₹" + String.format("%.2f", emi));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };
}