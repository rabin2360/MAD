package com.example.rara5776.coffee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class CoffeeActivity extends AppCompatActivity {

    private CoffeeShop myCoffeeShop = new CoffeeShop();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);

        //button
        final Button button = (Button) findViewById(R.id.submitButton);

        View.OnClickListener onclick = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                findCoffee(view);
            }
        };

        button.setOnClickListener(onclick);
    }

    public void findCoffee(View view)
    {
        //get the spinner
        Spinner crowdSpinner = (Spinner) findViewById(R.id.crowdType);

        myCoffeeShop.setCoffeeShop((int) crowdSpinner.getSelectedItemId());

        String suggestedCoffeeShop = myCoffeeShop.getCoffeShop();
        String suggestedCoffeeShopURL = myCoffeeShop.getCoffeeShopURL();

        System.out.println(suggestedCoffeeShop);
        System.out.println(suggestedCoffeeShopURL);

        //create an Intent
        Intent intent = new Intent(this, ReceiveCoffee.class);

        //using put extra
        intent.putExtra("coffeeShopName", suggestedCoffeeShop);
        intent.putExtra("coffeeShopURL", suggestedCoffeeShopURL);

        //start intent
        startActivity(intent);

    }
}
