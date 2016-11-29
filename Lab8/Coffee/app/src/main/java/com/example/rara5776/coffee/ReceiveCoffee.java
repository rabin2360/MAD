package com.example.rara5776.coffee;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ReceiveCoffee extends AppCompatActivity {

    private String coffeeShop;
    private String coffeeShopURL;

    public void initialize()
    {
        TextView message = (TextView) findViewById(R.id.labelForCoffeeShop);
        message.setText("You should check out: "+coffeeShop);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_coffee);

        Intent intent = getIntent();
        coffeeShop = intent.getStringExtra("coffeeShopName");
        coffeeShopURL = intent.getStringExtra("coffeeShopURL");

        initialize();

        final ImageButton imageButton = (ImageButton) findViewById(R.id.earthImage);
        //listener to the image view
        View.OnClickListener onclick = new View.OnClickListener()
        {
            public void onClick(View view)
            {
                loadWebsite(view);
            }
        };

        imageButton.setOnClickListener(onclick);
    }

    //implicit intent
    public void loadWebsite(View view)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(coffeeShopURL));
        startActivity(intent);
    }
}
