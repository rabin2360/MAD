package com.example.rabin.testapplication;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by rabin on 8/10/16.
 */
public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener{

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(adapterView.getContext(),
                "OnItemSelectedListener : " + adapterView.getItemAtPosition(i).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Auto generated method
    }
}
