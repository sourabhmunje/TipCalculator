package com.appify.sourabh.androidtipcalculator;

import android.content.Context;
import android.content.Intent;

import android.support.annotation.IdRes;
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

    DecimalFormat df = new DecimalFormat("#0.00");
    private float pizzaTip = 0.0f;
    private float pizzaCost;
    private float serviceTip;
    private float mIsBadWeather;
    private float mIsReallyBadWeather;
    private float mIsMoreThanThreeMiles;
    private float mIsMoreThanFiveMiles;
    private float mPoor;
    private float mCommon;
    private float mExcellent;
    private int mCurrentRadioButton;

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

    //private static final String TAG = "PizzaActivity";

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, PizzaActivity.class);
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);
        pizzaBillEditText = (EditText) findViewById(R.id.pizzaEditText);

        //


        //


        //using text changed listener
        //reference - https://stackoverflow.com/questions/18503809/set-addtextchangedlistener-in-a-function-android
        pizzaBillEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
                calculateServiceTip(mCurrentRadioButton);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This space intentionally left blank
                calculateServiceTip(mCurrentRadioButton);
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
                calculateServiceTip(mCurrentRadioButton);
                //checkMinTip();
                //displayTipAndTotal();
            }
        });

        serviceRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        serviceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                calculateServiceTip(checkedId);

                //checkMinTip();
                //displayTipAndTotal();
            }
        });
        // find the radio button by returned id
        poorServiceRadioBtn = (RadioButton) findViewById(R.id.poorRadioButton);
        commonServiceRadioBtn = (RadioButton) findViewById(R.id.commonRadioButton);
        excellentServiceRadioBtn = (RadioButton) findViewById(R.id.excellentRadioButton);

        badWeatherCheckbox = (CheckBox) findViewById(R.id.badWeatherCheckBox);
        badWeatherCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mIsBadWeather = 1;
                }
                else {
                    mIsBadWeather = 0;
                }
                calculateServiceTip(mCurrentRadioButton);
               // checkMinTip();
                //displayTipAndTotal();
            }
        });

        reallyBadWeatherCheckbox = (CheckBox) findViewById(R.id.reallyBadWeatherCheckBox);
        reallyBadWeatherCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mIsReallyBadWeather = 2;
                }
                else {
                    mIsReallyBadWeather = 0;
                }
                calculateServiceTip(mCurrentRadioButton);
               // checkMinTip();
               // displayTipAndTotal();
            }
        });

        threeMilesCheckbox = (CheckBox) findViewById(R.id.threeMileCheckBox);
        threeMilesCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mIsMoreThanThreeMiles = 1;
                }
                else {
                    mIsMoreThanThreeMiles = 0;
                }
                calculateServiceTip(mCurrentRadioButton);
              //  checkMinTip();
               // displayTipAndTotal();
            }
        });

        fiveMilesCheckbox = (CheckBox) findViewById(R.id.fiveMileCheckBox);
        fiveMilesCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mIsMoreThanFiveMiles = 2;
                }
                else {
                    mIsMoreThanFiveMiles = 0;
                }
                calculateServiceTip(mCurrentRadioButton);
              //  checkMinTip();
               // displayTipAndTotal();
            }
        });


        minTipCheckbox = (CheckBox) findViewById(R.id.minimumCheckBox);
        minTipCheckbox.setClickable(false);
        largeOrderCheckbox = (CheckBox) findViewById(R.id.largeOrderCheckBox);
        largeOrderCheckbox.setClickable(false);
        pizzaTipEditText = (EditText) findViewById(R.id.pizzaTipEditText);
        pizzaTotalEditText = (EditText) findViewById(R.id.pizzaTotalEditText);



       // checkLargeOrder();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
//    private void checkLargeOrder() {
//        if (pizzaCost >= 100) {
//
//        }
//    }
    private void calculateServiceTip(int tipId){
        mCurrentRadioButton = tipId;
        if(tipId == poorServiceRadioBtn.getId()){
            serviceTip = pizzaCost * mPoor;
        }
        else if(tipId == commonServiceRadioBtn.getId()){
            serviceTip = pizzaCost * mCommon;
        }
        else if(tipId == excellentServiceRadioBtn.getId()){
            serviceTip = pizzaCost * mExcellent;
        }

        pizzaTip = serviceTip + mIsBadWeather + mIsReallyBadWeather + mIsMoreThanThreeMiles + mIsMoreThanFiveMiles;
        pizzaTipEditText.setText("$"+df.format(pizzaTip));
        float pizzaTotal = pizzaCost + pizzaTip;
        pizzaTotalEditText.setText("$"+df.format(pizzaTotal));


      //  serviceTip = (pizzaCost == 0)? 0 : ((serviceTip < 3) ? 3:serviceTip);

        if(pizzaCost !=0){
            if(serviceTip<3){
                minTipCheckbox.setChecked(true);
                serviceTip = 3;
                pizzaTipEditText.setText("$"+df.format(pizzaTip));
            } else if(serviceTip>=3){
                minTipCheckbox.setChecked(false);
                pizzaTipEditText.setText("$"+df.format(pizzaTip));
            }
        }else{
            minTipCheckbox.setChecked(false);
            serviceTip = 0;
            pizzaTipEditText.setText("$"+df.format(pizzaTip));
        }

//        if (serviceTip < 3) {
//            if(pizzaCost != 0){
//                minTipCheckbox.setChecked(true);
//                serviceTip = 3;
//                pizzaTipEditText.setText("$"+df.format(pizzaTip));
//            }
//            else if (pizzaCost == 0){
//                minTipCheckbox.setChecked(false);
//                serviceTip = 0;
//                pizzaTipEditText.setText("$"+df.format(pizzaTip));
//            }
//        }
//        else {
//            minTipCheckbox.setChecked(false);
//            pizzaTipEditText.setText("$"+df.format(pizzaTip));
//        }
    }
}
