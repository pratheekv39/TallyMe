package io.pratheekv39.tallyme;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.pratheekv39.tallyme.data.AppDatabase;
import io.pratheekv39.tallyme.data.CalculationEntity;

public class ManualGstCalculatorActivity extends AppCompatActivity {

    private AppDatabase db;
    private EditText totalPriceInput, gstPercentageInput;
    private TextView gstPriceResult, netPriceResult;
    private Button calculateButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_gst_calculator);

        db = AppDatabase.getDatabase(this);

        totalPriceInput = findViewById(R.id.total_price_input);
        gstPercentageInput = findViewById(R.id.gst_percentage_input);
        gstPriceResult = findViewById(R.id.gst_price_result);
        netPriceResult = findViewById(R.id.net_price_result);
        calculateButton = findViewById(R.id.calculate_button);
        backButton = findViewById(R.id.back_button);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateGst();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void calculateGst() {
        String totalPriceStr = totalPriceInput.getText().toString();
        String gstPercentageStr = gstPercentageInput.getText().toString();

        if (totalPriceStr.isEmpty() || gstPercentageStr.isEmpty()) {
            Toast.makeText(this, "Please enter both values", Toast.LENGTH_SHORT).show();
            return;
        }

        float totalPrice = Float.parseFloat(totalPriceStr);
        float gstPercentage = Float.parseFloat(gstPercentageStr);

        float gstPrice = totalPrice * (gstPercentage / 100);
        float netPrice = totalPrice / (1 + (gstPercentage / 100));

        gstPriceResult.setText(String.format("GST Price: ₹%.2f", gstPrice));
        netPriceResult.setText(String.format("Net Price: ₹%.2f", netPrice));

        saveCalculation(totalPrice, gstPercentage, netPrice, gstPrice);
    }

    private void saveCalculation(float totalPrice, float gstPercentage, float netPrice, float gstPrice) {
        String details = String.format("Total Price: ₹%.2f, GST: %.2f%%, Net Price: ₹%.2f, GST Price: ₹%.2f",
                totalPrice, gstPercentage, netPrice, gstPrice);
        CalculationEntity calculation = new CalculationEntity("GST", details, System.currentTimeMillis());

        new Thread(() -> db.calculationDao().insert(calculation)).start();
    }
}