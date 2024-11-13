package io.pratheekv39.tallyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import io.pratheekv39.tallyme.data.AppDatabase;
import io.pratheekv39.tallyme.data.CalculationEntity;

public class emi extends AppCompatActivity {

    private AppDatabase db;
    private SeekBar totalPriceSeekBar, downPaymentSeekBar, interestRateSeekBar, tenureSeekBar;
    private TextView totalPriceTextView, downPaymentTextView, interestRateTextView, tenureTextView, emiTextView, loanTextview;
    private float totalPrice, downPayment, principalAmount, interestRate, tenure, emi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi);

        db = AppDatabase.getDatabase(this);

        totalPriceSeekBar = findViewById(R.id.total_price_seekbar);
        downPaymentSeekBar = findViewById(R.id.down_payment_seekbar);
        interestRateSeekBar = findViewById(R.id.interest_rate_seekbar);
        tenureSeekBar = findViewById(R.id.tenure_seekbar);

        totalPriceTextView = findViewById(R.id.total_price_textview);
        downPaymentTextView = findViewById(R.id.down_payment_textview);
        interestRateTextView = findViewById(R.id.interest_rate_textview);
        tenureTextView = findViewById(R.id.tenure_textview);
        emiTextView = findViewById(R.id.emi_textview);
        loanTextview = findViewById(R.id.loan_amount_textview);

        Button backButton = findViewById(R.id.back_to_main_button);
        Button addDetailsManuallyButton = findViewById(R.id.add_details_manually_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCalculation();
                Intent intent = new Intent(emi.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addDetailsManuallyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(emi.this, ManualEmiCalculatorActivity.class);
                startActivity(intent);
            }
        });

        totalPriceSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        downPaymentSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        interestRateSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        tenureSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    private void saveCalculation() {
        String details = String.format("Total Price: ₹%.2f, Down Payment: ₹%.2f, Loan Amount: ₹%.2f, Interest Rate: %.2f%%, Tenure: %.0f years, EMI: ₹%.2f",
                totalPrice, downPayment, principalAmount, interestRate, tenure, emi);
        CalculationEntity calculation = new CalculationEntity("EMI", details, System.currentTimeMillis());

        new Thread(() -> db.calculationDao().insert(calculation)).start();
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            updateFields();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    private void updateFields() {
        totalPrice = totalPriceSeekBar.getProgress();
        downPayment = downPaymentSeekBar.getProgress();
        principalAmount = totalPrice - downPayment;
        interestRate = interestRateSeekBar.getProgress();
        tenure = tenureSeekBar.getProgress();

        float monthlyInterestRate = (interestRate / 12) / 100;
        float numPayments = tenure * 12;
        emi = (principalAmount * monthlyInterestRate * (float) Math.pow(1 + monthlyInterestRate, numPayments))
                / (float) (Math.pow(1 + monthlyInterestRate, numPayments) - 1);

        totalPriceTextView.setText(String.format("Total Price: ₹%.2f", totalPrice));
        downPaymentTextView.setText(String.format("Down Payment: ₹%.2f", downPayment));
        loanTextview.setText(String.format("Loan Amount: ₹%.2f", principalAmount));
        interestRateTextView.setText(String.format("Interest Rate: %.2f%%", interestRate));
        tenureTextView.setText(String.format("Tenure: %.0f years", tenure));
        emiTextView.setText(String.format("EMI: ₹%.2f", emi));
    }
}