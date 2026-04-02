package com.example.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner fromCurrency, toCurrency;
    EditText amount;
    TextView result;

    String[] currencies = {"INR", "USD", "JPY", "EUR"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.amount);
        fromCurrency = findViewById(R.id.fromCurrency);
        toCurrency = findViewById(R.id.toCurrency);
        result = findViewById(R.id.result);

        Button convertBtn = findViewById(R.id.convertBtn);
        Button settingsBtn = findViewById(R.id.settingsBtn);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, currencies);

        fromCurrency.setAdapter(adapter);
        toCurrency.setAdapter(adapter);

        convertBtn.setOnClickListener(v -> convertCurrency());

        settingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void convertCurrency() {
        String from = fromCurrency.getSelectedItem().toString();
        String to = toCurrency.getSelectedItem().toString();

        if (amount.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double input = Double.parseDouble(amount.getText().toString());
        double output = input;

        // Conversion rates (fixed for simplicity)
        if (from.equals("INR")) {
            if (to.equals("USD")) output = input * 0.012;
            else if (to.equals("JPY")) output = input * 1.8;
            else if (to.equals("EUR")) output = input * 0.011;
        }
        else if (from.equals("USD")) {
            if (to.equals("INR")) output = input * 83;
            else if (to.equals("JPY")) output = input * 150;
            else if (to.equals("EUR")) output = input * 0.92;
        }
        else if (from.equals("JPY")) {
            if (to.equals("INR")) output = input * 0.55;
            else if (to.equals("USD")) output = input * 0.0067;
            else if (to.equals("EUR")) output = input * 0.0061;
        }
        else if (from.equals("EUR")) {
            if (to.equals("INR")) output = input * 90;
            else if (to.equals("USD")) output = input * 1.08;
            else if (to.equals("JPY")) output = input * 162;
        }

        result.setText("Converted: " + output);
    }
}