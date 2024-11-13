package com.example.taxloancalci;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taxloancalci.data.AppDatabase;
import com.example.taxloancalci.data.CalculationEntity;

public class ManualEmiCalculatorActivity extends AppCompatActivity {

    private AppDatabase db;
    private EditText totalPriceInput, downPaymentInput, interestRateInput, tenureInput;
    private TextView loanAmountResult, emiResult;
    private Button calculateButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_emi_calculator);

        db = AppDatabase.getDatabase(this);

        totalPriceInput = findViewById(R.id.total_price_input);
        downPaymentInput = findViewById(R.id.down_payment_input);
        interestRateInput = findViewById(R.id.interest_rate_input);
        tenureInput = findViewById(R.id.tenure_input);
        loanAmountResult = findViewById(R.id.loan_amount_result);
        emiResult = findViewById(R.id.emi_result);
        calculateButton = findViewById(R.id.calculate_button);
        backButton = findViewById(R.id.back_button);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateEmi();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void calculateEmi() {
        String totalPriceStr = totalPriceInput.getText().toString();
        String downPaymentStr = downPaymentInput.getText().toString();
        String interestRateStr = interestRateInput.getText().toString();
        String tenureStr = tenureInput.getText().toString();

        if (totalPriceStr.isEmpty() || downPaymentStr.isEmpty() || interestRateStr.isEmpty() || tenureStr.isEmpty()) {
            Toast.makeText(this, "Please enter all values", Toast.LENGTH_SHORT).show();
            return;
        }

        float totalPrice = Float.parseFloat(totalPriceStr);
        float downPayment = Float.parseFloat(downPaymentStr);
        float interestRate = Float.parseFloat(interestRateStr);
        float tenure = Float.parseFloat(tenureStr);

        float principalAmount = totalPrice - downPayment;
        float monthlyInterestRate = (interestRate / 12) / 100;
        float numPayments = tenure * 12;
        float emi = (principalAmount * monthlyInterestRate * (float) Math.pow(1 + monthlyInterestRate, numPayments))
                / (float) (Math.pow(1 + monthlyInterestRate, numPayments) - 1);

        loanAmountResult.setText(String.format("Loan Amount: ₹%.2f", principalAmount));
        emiResult.setText(String.format("EMI: ₹%.2f", emi));

        saveCalculation(totalPrice, downPayment, principalAmount, interestRate, tenure, emi);
    }

    private void saveCalculation(float totalPrice, float downPayment, float principalAmount, float interestRate, float tenure, float emi) {
        String details = String.format("Total Price: ₹%.2f, Down Payment: ₹%.2f, Loan Amount: ₹%.2f, Interest Rate: %.2f%%, Tenure: %.0f years, EMI: ₹%.2f",
                totalPrice, downPayment, principalAmount, interestRate, tenure, emi);
        CalculationEntity calculation = new CalculationEntity("EMI", details, System.currentTimeMillis());

        new Thread(() -> db.calculationDao().insert(calculation)).start();
    }
}