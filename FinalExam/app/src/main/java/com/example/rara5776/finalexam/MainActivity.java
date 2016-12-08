package com.example.rara5776.finalexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private EditText userEntry;
    private TextView display;
    private Button treatMeButton;
    private Spinner iceCreamTypes;
    private IceCreamShops shops;
    private Switch dairyFree;
    private ToggleButton cupCone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the widgets
        userEntry = (EditText) findViewById(R.id.userEntry);
        display = (TextView) findViewById(R.id.display);
        treatMeButton = (Button) findViewById(R.id.treatMeButton);
        iceCreamTypes = (Spinner) findViewById(R.id.iceCreamTypes);
        dairyFree = (Switch) findViewById(R.id.dairyFree);
        cupCone = (ToggleButton) findViewById(R.id.cupCone);
        shops = new IceCreamShops();
    }


    public void treatMeButtonClick(View view)
    {
        //get the ice cream type
        String iceCreamSelected = (String) iceCreamTypes.getSelectedItem();

        //get the user input
        String userInputText = userEntry.getText().toString();

        //get ice cream shop name
        String iceCreamShopName = shops.getShop(iceCreamSelected); //getIceCreamShopName(iceCreamSelected);

        //set the display
        StringBuilder str = new StringBuilder();
        str.append("My ");
        str.append(userInputText);
        str.append("is a ");
        str.append(iceCreamSelected);
        str.append(" ice cream ");

        if(cupCone.isChecked())
        {
            str.append("cup. ");
        }
        else
        {
            str.append("cone. ");
        }
        str.append("You should visit ");
        str.append(iceCreamShopName);
        str.append(".");

        if(dairyFree.isChecked())
        {
            str.append(" You have selected diary free option.");
        }
        display.setText(str.toString());

    }

    public void findTreatButtonClick(View view)
    {
        //get the ice cream type
        String iceCreamSelected = (String) iceCreamTypes.getSelectedItem();

        Intent intent = new Intent(this, IceCreamShop.class);
        intent.putExtra("iceCream", iceCreamSelected);
        startActivity(intent);
    }
}
