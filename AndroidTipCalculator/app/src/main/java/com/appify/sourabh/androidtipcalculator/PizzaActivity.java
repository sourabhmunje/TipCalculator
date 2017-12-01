package com.appify.sourabh.androidtipcalculator;

import android.content.Context;
import android.content.Intent;

import android.support.annotation.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.text.DecimalFormat;

public class PizzaActivity extends AppCompatActivity {

    private int selectedServiceRadioBtn;
    private boolean isLessThanMin=false;
    private float pizzaTip = 0;
    private float pizzaCost;
    private float serviceTip;
    private float badWeatherTip = 0;
    private float reallyBadWeatherTip = 0;
    private float moreThanThreeMilesTip = 0;
    private float moreThanFiveMilesTip = 0;
    private float mPoor;
    private float mCommon;
    private float mExcellent;
    private EditText pizzaBillEditText;
    private RadioGroup serviceRadioGroup;
    private RadioButton poorServiceRadioBtn;
    private RadioButton commonServiceRadioBtn;
    private RadioButton excellentServiceRadioBtn;
    private CheckBox badWeatherCheckbox;
    private CheckBox reallyBadWeatherCheckbox;
    private CheckBox threeMilesCheckbox;
    private CheckBox fiveMilesCheckbox;
    private CheckBox minTipCheckbox;
    private CheckBox largeOrderCheckbox;
    private EditText pizzaTipEditText;
    private EditText pizzaTotalEditText;
    DecimalFormat df = new DecimalFormat("#0.00");

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, PizzaActivity.class);
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);
        pizzaBillEditText = (EditText) findViewById(R.id.pizzaEditText);
        //using text changed listener
        //reference - https://stackoverflow.com/questions/18503809/set-addtextchangedlistener-in-a-function-android
        pizzaBillEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try{
                    pizzaCost = Float.valueOf(pizzaBillEditText.getText().toString());
                } catch (NumberFormatException e){
                    pizzaCost = 0;
                }
                calculateTip(selectedServiceRadioBtn);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    pizzaCost = Float.valueOf(pizzaBillEditText.getText().toString());
                } catch (NumberFormatException e){
                    pizzaCost = 0;
                }
                calculateTip(selectedServiceRadioBtn);
            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    pizzaCost = Float.valueOf(pizzaBillEditText.getText().toString());
                } catch (NumberFormatException e){
                    pizzaCost = 0;
                }

                if(pizzaCost >= 100){
                    EnableLargeOrderMode();
                }
                else {
                    EnableSmallOrderMode();
                }
                calculateTip(selectedServiceRadioBtn);
            }
        });

        //https://developer.android.com/reference/android/widget/CompoundButton.OnCheckedChangeListener.html
        serviceRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        serviceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                calculateTip(checkedId);
            }
        });
        poorServiceRadioBtn = (RadioButton) findViewById(R.id.poorRadioButton);
        commonServiceRadioBtn = (RadioButton) findViewById(R.id.commonRadioButton);
        excellentServiceRadioBtn = (RadioButton) findViewById(R.id.excellentRadioButton);

        threeMilesCheckbox = (CheckBox) findViewById(R.id.threeMileCheckBox);
        threeMilesCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                moreThanThreeMilesTip = isChecked ? 1 : 0;
                calculateTip(selectedServiceRadioBtn);
            }
        });

        fiveMilesCheckbox = (CheckBox) findViewById(R.id.fiveMileCheckBox);
        fiveMilesCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                moreThanFiveMilesTip = isChecked ? 2 : 0;
                calculateTip(selectedServiceRadioBtn);
            }
        });
        badWeatherCheckbox = (CheckBox) findViewById(R.id.badWeatherCheckBox);
        badWeatherCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                badWeatherTip = isChecked ? 1 : 0;
                calculateTip(selectedServiceRadioBtn);
            }
        });

        reallyBadWeatherCheckbox = (CheckBox) findViewById(R.id.reallyBadWeatherCheckBox);
        reallyBadWeatherCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                reallyBadWeatherTip = isChecked ? 2 : 0;
                calculateTip(selectedServiceRadioBtn);
            }
        });


        minTipCheckbox = (CheckBox) findViewById(R.id.minimumCheckBox);
        largeOrderCheckbox = (CheckBox) findViewById(R.id.largeOrderCheckBox);
        pizzaTipEditText = (EditText) findViewById(R.id.pizzaTipEditText);
        pizzaTotalEditText = (EditText) findViewById(R.id.pizzaTotalEditText);

    }

    private void EnableLargeOrderMode(){
        poorServiceRadioBtn.setText("Poor 5%");
        commonServiceRadioBtn.setText("Common 10%");
        excellentServiceRadioBtn.setText("Excellent 15%");
        mPoor = 0.05f;
        mCommon = 0.10f;
        mExcellent = 0.15f;
        largeOrderCheckbox.setChecked(true);
    }
    public void EnableSmallOrderMode(){
        poorServiceRadioBtn.setText("Poor 10%");
        commonServiceRadioBtn.setText("Common 15%");
        excellentServiceRadioBtn.setText("Excellent 20%");
        mPoor = 0.10f;
        mCommon = 0.15f;
        mExcellent = 0.20f;
        largeOrderCheckbox.setChecked(false);
    }

    private void calculateTip(int serviceCategory){
        selectedServiceRadioBtn = serviceCategory;
        if(serviceCategory == poorServiceRadioBtn.getId()){
            serviceTip = pizzaCost * mPoor;
        }
        else if(serviceCategory == commonServiceRadioBtn.getId()){
            serviceTip = pizzaCost * mCommon;
        }
        else if(serviceCategory == excellentServiceRadioBtn.getId()){
            serviceTip = pizzaCost * mExcellent;
        }

        pizzaTip = serviceTip + badWeatherTip + reallyBadWeatherTip + moreThanThreeMilesTip + moreThanFiveMilesTip;
        pizzaTipEditText.setText("$"+df.format(pizzaTip));
        float pizzaTotal = pizzaCost + pizzaTip;
        pizzaTotalEditText.setText("$"+df.format(pizzaTotal));

      //  serviceTip = (pizzaCost == 0)? 0 : ((serviceTip < 3) ? 3:serviceTip);

        if(pizzaCost !=0){
            if(serviceTip<3){
               // isLessThanMin = true;
                minTipCheckbox.setChecked(true);
                pizzaTip = 3 + badWeatherTip + reallyBadWeatherTip + moreThanThreeMilesTip + moreThanFiveMilesTip;
                pizzaTipEditText.setText("$"+df.format(pizzaTip));
                pizzaTotal = pizzaCost + pizzaTip;
                pizzaTotalEditText.setText("$"+df.format(pizzaTotal));
                pizzaTipEditText.setText("$"+df.format(pizzaTip));
            } else if(serviceTip>=3){
                minTipCheckbox.setChecked(false);
                pizzaTip = serviceTip + badWeatherTip + reallyBadWeatherTip + moreThanThreeMilesTip + moreThanFiveMilesTip;
                pizzaTipEditText.setText("$"+df.format(pizzaTip));
                pizzaTotal = pizzaCost + pizzaTip;
                pizzaTotalEditText.setText("$"+df.format(pizzaTotal));
                pizzaTipEditText.setText("$"+df.format(pizzaTip));
            }
        }else{
            minTipCheckbox.setChecked(false);
            serviceTip = 0;
            pizzaTotalEditText.setText("$"+df.format(0));
            pizzaTipEditText.setText("$"+df.format(0));
        }

    }
}
