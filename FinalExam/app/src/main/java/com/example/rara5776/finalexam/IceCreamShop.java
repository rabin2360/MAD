package com.example.rara5776.finalexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by rara5776 on 12/8/16.
 */
public class IceCreamShop extends AppCompatActivity {

    private String iceCreamShopName;
    private String iceCreamName;
    private TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_icecream_shop);

        Intent intent = getIntent();
        iceCreamName = intent.getStringExtra("iceCream");

        IceCreamShops shops  = new IceCreamShops();
        display = (TextView) findViewById(R.id.iceShopNameDisp);

        display.setText("You selected "+iceCreamName+", you should check out "+shops.getShop(iceCreamName));
    }
}
