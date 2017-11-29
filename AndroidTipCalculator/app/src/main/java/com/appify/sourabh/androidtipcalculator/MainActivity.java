package com.appify.sourabh.androidtipcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mRestaurantButton;
    private Button mPizzaButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPizzaButton = (Button) findViewById(R.id.pizzaButton);
        mRestaurantButton = (Button) findViewById(R.id.restaurantButton);

        //To launch the PizzaActivity
        mPizzaButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent i = PizzaActivity.newIntent(MainActivity.this);
                startActivity(i);
            }
        });
        //To launch the RestaurantActivity
        mRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent i = RestaurantActivity.newIntent(MainActivity.this);
                startActivity(i);
            }
        });

    }
}

