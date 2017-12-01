package com.appify.sourabh.androidtipcalculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class RestaurantActivity extends AppCompatActivity {

    private double totalBillPreTax = 0.0;
    private int seekValue = 16;
    private double totalTipAmount = 0.0;
    private double totalTax = 0;
    private double grandTotal = 0;
    private int splitSpinnerValue = 1;
    private EditText initialBillEditText;
    private EditText taxEditText;
    private EditText splitBillEditText;
    private SeekBar tipSeekBar;
    private EditText totalTipEditText;
    private TextView percentTextView;
    private EditText totalBillEditText;
    private Spinner splitSpinner;
    DecimalFormat df = new DecimalFormat("#0.00");

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, RestaurantActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        initialBillEditText = (EditText) findViewById(R.id.initialBillEditText);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        totalTipEditText = (EditText) findViewById(R.id.totalTipEditText);
        taxEditText = (EditText) findViewById(R.id.taxEditText);
        totalBillEditText = (EditText) findViewById(R.id.totalEditText);
        tipSeekBar = (SeekBar)findViewById(R.id.tipSeekBar);
        tipSeekBar.setMax(10);
        tipSeekBar.incrementProgressBy(1);
        seekValue = tipSeekBar.getProgress() + 15;
        splitSpinner = (Spinner) findViewById(R.id.splitSpinner);
        splitBillEditText = (EditText) findViewById(R.id.splitAmountEditText);
        totalBillPreTax = Double.parseDouble(initialBillEditText.getText().toString());
        totalTipAmount = Double.parseDouble(totalTipEditText.getText().toString().replace("$",""));
        totalTax = Double.parseDouble(taxEditText.getText().toString());
        grandTotal = totalBillPreTax + totalTipAmount + totalTax;
        splitSpinnerValue = Integer.parseInt(splitSpinner.getSelectedItem().toString());
        initialBillEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s){
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String initialBill = initialBillEditText.getText().toString();
                totalBillPreTax = (!initialBill.isEmpty() && initialBill != null) ? Double.parseDouble(initialBill) : 0;
                totalTipEditText.setText("$"+df.format(totalBillPreTax * (((double)seekValue)/100)));
                totalTipAmount = Double.parseDouble(totalTipEditText.getText().toString().replace("$",""));
                totalBillEditText.setText("$"+df.format(totalBillPreTax + totalTipAmount + totalTax));
                grandTotal = Double.parseDouble(totalBillEditText.getText().toString().replace("$",""));
                splitBillEditText.setText("$"+df.format(grandTotal/splitSpinnerValue));
            }
        });

        tipSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //https://developer.android.com/reference/android/widget/SeekBar.OnSeekBarChangeListener.html
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                seekValue = progress + 15;
                percentTextView.setText(seekValue+"%");
                totalTipEditText.setText("$"+df.format(totalBillPreTax * (((double)seekValue)/100)));
                String totalTip = totalTipEditText.getText().toString().replace("$","");
                totalTipAmount = (totalTip != null && !totalTip.isEmpty()) ? Double.parseDouble(totalTip) : 0;
                totalBillEditText.setText("$"+df.format(totalBillPreTax + totalTipAmount + totalTax));
                grandTotal = Double.parseDouble(totalBillEditText.getText().toString().replace("$",""));
                splitBillEditText.setText("$"+df.format(grandTotal/splitSpinnerValue));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        taxEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tax = taxEditText.getText().toString();
                totalTax = (tax != null && !tax.isEmpty()) ? Double.parseDouble(tax) : 0;
                totalBillEditText.setText("$"+df.format(totalBillPreTax + totalTipAmount + totalTax));
                grandTotal = Double.parseDouble(totalBillEditText.getText().toString().replace("$",""));
                splitBillEditText.setText("$"+df.format(grandTotal/splitSpinnerValue));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s){
            }
        });

        splitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                splitSpinnerValue = Integer.parseInt(splitSpinner.getSelectedItem().toString());
                splitBillEditText.setText("$"+df.format(grandTotal/splitSpinnerValue));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

}

