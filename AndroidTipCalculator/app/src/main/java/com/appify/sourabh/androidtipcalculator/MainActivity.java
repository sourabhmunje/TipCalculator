package com.appify.sourabh.androidtipcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button restaurantButton;
    private Button pizzaButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pizzaButton = (Button) findViewById(R.id.pizzaButton);
        restaurantButton = (Button) findViewById(R.id.restaurantButton);

        //Pizza Activity
        pizzaButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent i = PizzaActivity.newIntent(MainActivity.this);
                startActivity(i);
            }
        });
        //Restaurant Activity
        restaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent i = RestaurantActivity.newIntent(MainActivity.this);
                startActivity(i);
            }
        });
    }
}

