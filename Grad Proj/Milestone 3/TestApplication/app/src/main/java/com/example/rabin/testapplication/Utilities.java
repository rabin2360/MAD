package com.example.rabin.testapplication;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

//import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rabin on 8/11/16.
 */
public class Utilities {


    //checks if the given EditText box is empty
    //if empty, it puts the error sign
    //otherwise, it extracts the string
    public String isEmpty(EditText input) {
        String returnString = "";

        if (input.getText().toString().isEmpty()) {
            input.setError(input.getHint().toString());
            returnString = "false";
        } else {
            int val = 132;
            returnString = input.getText().toString();
            input.setError(null);
        }

        return returnString;
    }


    //adding items on the ethnicity drop down menu
    public void addItemsOnEthnicitySpinner(Spinner studentEthnicitySpinner, LoginScreen object) {
        //list of ethnicities
        List<String> ethnicities = new ArrayList<String>();
        ethnicities.add("Ethnicity - Not Selected");
        ethnicities.add("White");
        ethnicities.add("African American");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(object, android.R.layout.simple_spinner_item, ethnicities);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentEthnicitySpinner.setAdapter(dataAdapter);
    }

    public void addItemsOnLanguageProficiency(Spinner studentLangProficiency, LoginScreen object) {
        //list of ethnicities
        List<String> langProf = new ArrayList<String>();
        langProf.add("Proficiency - Not Selected");
        langProf.add("Proficient");
        langProf.add("Not Proficient");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(object, android.R.layout.simple_spinner_item, langProf);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentLangProficiency.setAdapter(dataAdapter);
    }

    public void addItemsForGrade(Spinner studentGradeLevel, LoginScreen object) {
        //list of ethnicities
        List<String> gradeLevel = new ArrayList<String>();
        gradeLevel.add("Grade - Not Selected");
        gradeLevel.add("Pre-K");
        gradeLevel.add("K");
        gradeLevel.add("1");
        gradeLevel.add("12");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(object, android.R.layout.simple_spinner_item, gradeLevel);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentGradeLevel.setAdapter(dataAdapter);
    }

    public void addItemsForIEP(Spinner iepCategories, LoginScreen object) {
        //list of ethnicities
        List<String> primaryDisabilityCat = new ArrayList<String>();
        primaryDisabilityCat.add("Primary Disability Category - Not Selected");
        primaryDisabilityCat.add("Autism");
        primaryDisabilityCat.add("Deaf-Blindness");
        primaryDisabilityCat.add("Deafness");
        primaryDisabilityCat.add("Emotional Disturbance");
        primaryDisabilityCat.add("Hearing Impairment");
        primaryDisabilityCat.add("Intellectual Disability");
        primaryDisabilityCat.add("Multiple Disabilities");
        primaryDisabilityCat.add("Orthopedic Impairment");
        primaryDisabilityCat.add("Other Health Impairments");
        primaryDisabilityCat.add("Specific Learning Disability");
        primaryDisabilityCat.add("Speech or Language Impairment");
        primaryDisabilityCat.add("Traumatic Brian Injury");
        primaryDisabilityCat.add("Visual Impairment");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(object, android.R.layout.simple_spinner_item, primaryDisabilityCat);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iepCategories.setAdapter(dataAdapter);
    }

    public void addTimesInSpinner(Spinner observationTotalTime, ObservationScreen object) {
        //list of total observation times
        List<Integer> observationTotalTimes = new ArrayList<Integer>();
        observationTotalTimes.add(0);
        observationTotalTimes.add(15);
        observationTotalTimes.add(30);
        observationTotalTimes.add(45);
        observationTotalTimes.add(60);

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(object, android.R.layout.simple_spinner_item, observationTotalTimes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        observationTotalTime.setAdapter(dataAdapter);
    }


    public void setSpinnerVal(String stringToSetTo, Spinner spinner) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();

        int spinnerPos = adapter.getPosition(stringToSetTo);

        spinner.setSelection(spinnerPos);

    }

    public void setSpinnerInt(int integerValue, Spinner spinner)
    {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();

        int spinnerPos = adapter.getPosition(integerValue);

        spinner.setSelection(spinnerPos);

    }

    public void clearAllFormFields(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);

            if (view instanceof EditText)
                ((EditText) view).setText("");
        }
    }


    //validating the date
    public boolean validateDate(String dateString)
    {
        boolean validDate = true;

        char [] characterArray = dateString.toCharArray();

        String temp = "";
        for(int i = 0; i<characterArray.length; i++)
        {
            if(characterArray[i] == '/')
            {

                //get current year
                if(temp.length() == 4)
                {
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

                    if(Integer.parseInt(temp) > currentYear)
                    {
                        System.out.println("temps is greater than current");
                        return false;
                    }
                }

                if(temp.length() == 2)
                {
                    if((Integer.parseInt(temp) < 1 || Integer.parseInt(temp) > 12))
                            return false;
                }

                //reset temp
                temp = "";

            }else
            {
                temp += characterArray[i];
            }

        }

        if(temp.length() == 2)
        {
            if((Integer.parseInt(temp) < 1 || Integer.parseInt(temp) > 31))
            return false;

        }

        return validDate;
    }
}

