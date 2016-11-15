package com.example.rabin.testapplication;

//import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.preference.DialogPreference;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.Calendar;



public class LoginScreen extends AppCompatActivity {

    private EditText studentFirstname;
    private EditText studentLastname;
    private EditText studentId;
    private EditText studentDOB;
    private EditText studentEthnicity;
    private EditText studentPrimaryLang;
    private Spinner studentGradeLevel;
    private EditText studentConsentDate;
    private EditText student504Plan;

    private Spinner studentLangProficiency;
    private Spinner studentEthnicitySpinner;
    private Spinner studentPrimaryDisabilityCat;

    private Button okButton;
    private Button clearButton;
    private Button cancelButton;
    private Button editButton;
    private Button observationButton;

    private CheckBox consentCheckBox;
    private CheckBox studentIEPCheckBox;
    private CheckBox studentPlan504CheckBox;

    private Calendar calendar;
    private int yearDOB, monthDOB, dayDOB;
    private int yearConsent, monthConsent, dayConsent;

    private Student studentInfo;
    private Observation observationInfo;

    private boolean editMode;

    private ListView previousObservation;

    private static final String CLEAR_BUTTON = "clear_button";
    private static final String STUDENT_ID_NOT_FOUND = "not_found";

    private Utilities utility;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        InitStep();
        DatePickerMethod();

        //creating the instance of utilities class
        utility = new Utilities();

        //utility.dateWatcher(findViewById(R.id.studentDOB));

        //populating the spinners (utilities class)
        utility.addItemsOnEthnicitySpinner(studentEthnicitySpinner, this);
        utility.addItemsOnLanguageProficiency(studentLangProficiency, this);
        utility.addItemsForGrade(studentGradeLevel, this);
        utility.addItemsForIEP(studentPrimaryDisabilityCat, this);

        studentDOB.addTextChangedListener(dateFormatter);

        observationInfo = new Observation();
        observationInfo.setObservationId(1);
        observationInfo.setObservationOffTask(50);
        observationInfo.setObservationOnTask(50);
        observationInfo.setObservationStudentId("1");


        //values for the list
        String [] values = new String []
                {"value 1",
                        "value 2",
                        "value 3"
                };

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, android.R.id.text1, values);
        previousObservation.setAdapter(adapter);

    }

    private void InitStep() {

        studentId = (EditText) findViewById(R.id.studentId);

        studentFirstname = (EditText) findViewById(R.id.studentFirstName);
        studentLastname = (EditText) findViewById(R.id.studentLastName);
        studentDOB = (EditText) findViewById(R.id.studentDOB);
        studentPrimaryLang = (EditText) findViewById(R.id.studentPrimaryLang);
        studentConsentDate = (EditText) findViewById(R.id.studentConsentDate);
        studentLangProficiency = (Spinner) findViewById(R.id.studentLangProficiency);
        studentEthnicitySpinner = (Spinner) findViewById(R.id.studentEthnicity);
        studentGradeLevel = (Spinner) findViewById(R.id.studentGrade);
        studentIEPCheckBox = (CheckBox) findViewById(R.id.iepChecked);
        studentPrimaryDisabilityCat = (Spinner) findViewById(R.id.primaryDisabilityCat);
        studentPlan504CheckBox = (CheckBox) findViewById(R.id.plan504Checkbox);

        student504Plan = (EditText) findViewById(R.id.plan504);
        student504Plan.setEnabled(false);

        if(studentIEPCheckBox.isChecked())
            studentPrimaryDisabilityCat.setEnabled(true);
        else
            studentPrimaryDisabilityCat.setEnabled(false);

        //creating the student object
        studentInfo = new Student();

        //buttons
        editButton = (Button) findViewById(R.id.editInfo);

        //Initially edit mode is set to false
        editMode = false;

        //checkBox for consent date
        consentCheckBox = (CheckBox) findViewById(R.id.consentNA);

        //list view for previous observation
        previousObservation = (ListView) findViewById(R.id.observationList);

        observationButton = (Button) findViewById(R.id.startObservation);
    }

    public void navigateToObservation(View view)
    {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("What kind of observation would you prefer?")
                .setPositiveButton("Automatic", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startAutomaticObservation();
                    }
                })
                .setNegativeButton("Manual", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startManualObservation();
                    }
                })
                .show();
    }

    private void startAutomaticObservation()
    {
        Intent intent = new Intent(this, ObservationScreen.class);
        startActivity(intent);
    }

    private void startManualObservation()
    {
        Intent intent = new Intent(this, SummaryScreen.class);
        startActivity(intent);
    }

    //on pressing the clear button
    public void clearAllInfo(View view) {
        clearFields(CLEAR_BUTTON);
    }

    private void DatePickerMethod()
    {
        calendar = Calendar.getInstance();
        yearConsent = yearDOB = calendar.get(Calendar.YEAR);
        monthConsent= monthDOB = calendar.get(Calendar.MONTH);
        dayConsent = dayDOB = calendar.get(Calendar.DAY_OF_MONTH);

    }

    @SuppressWarnings("depracation")
    public void setDate(View view)
    {
        if(view.getId() == R.id.studentDOB)
        showDialog(998);
        else
            showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 998) {
            return new DatePickerDialog(this, myDateListenerDOB, yearDOB, monthDOB, dayDOB);
        }

        if (id == 999) {
            return new DatePickerDialog(this, myDateListenerConsent, yearConsent, monthConsent, dayConsent);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListenerConsent = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            monthConsent = arg2;
            dayConsent = arg3;
            yearConsent = arg1;


            //setting the student's consent
            studentConsentDate.setText(Integer.toString(yearConsent)+"/"+Integer.toString(monthConsent+1)+"/"+Integer.toString(dayConsent));
        }

    };

    private DatePickerDialog.OnDateSetListener myDateListenerDOB = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            monthDOB = arg2;
            dayDOB = arg3;
            yearDOB = arg1;


            //setting the student's date of birth
            studentDOB.setText(Integer.toString(yearDOB)+"/"+Integer.toString(monthDOB+1)+"/"+Integer.toString(dayDOB));

        }

    };

    public void exitStudentInfoScreen(View view) {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to exit? Information will not be saved.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void showDatePickerDialog(View view)
    {
        DialogFragment newFragment = new DialogFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    //when okay button is pressed
    public void saveInformation(View view) {
        //connecting the code the display elements

        String strTeacherFirstname = "";
        String strTeacherLastname = "";
        String strSubjectTaught = "";


        String strObserverFirstname = "";
        String strObserverLastname = "";
        String strObserverTitle = "";

        boolean success = true;

        //checking student ID
        if(studentId.getText().toString().isEmpty())
        {
            success = false;
            studentId.setError("Student Id is a required field.");
        }
        else {

            studentInfo.setStrStudentId(studentId.getText().toString().toUpperCase().trim());
            studentId.setError(null);
        }

        //student name
        studentInfo.setStrStudentFirstname(studentFirstname.getText().toString());
        if(studentInfo.getStrStudentFirstname().isEmpty())
        {
            success = false;
            studentFirstname.setError("Student's first name is a required field.");
        }
        else
            studentFirstname.setError(null);


        studentInfo.setStrStudentLastname(studentLastname.getText().toString());
        if(studentInfo.getStrStudentLastname().isEmpty())
        {
            success = false;
            studentLastname.setError("Student's last name is a required field.");
        }
        else
            studentLastname.setError(null);

        //checking DOB
        if(studentDOB.getText().toString().length() != 10 || studentDOB.getText().charAt(4) !='/' || studentDOB.getText().charAt(7) !='/' ) {
            studentDOB.setError("Student DOB is a required field. Format YYYY/MM/DD");
            success = false;
        }
        else if(!utility.validateDate(studentDOB.getText().toString()))
        {
            studentDOB.setError("Invalid date entered for student DOB");
            success = false;
        }
        else
        {
            studentInfo.setStrStudentDOB(studentDOB.getText().toString());
            studentDOB.setError(null);
        }


        studentInfo.setStrStudentPrimaryLang(studentPrimaryLang.getText().toString());

        //ethnicity
        studentInfo.setStrStudentEthinicity(String.valueOf(studentEthnicitySpinner.getSelectedItem()));

        studentInfo.setStrStudentGradeLevel(String.valueOf(studentGradeLevel.getSelectedItem()));

        studentInfo.setStrStudentPrimaryDisability(String.valueOf(studentPrimaryDisabilityCat.getSelectedItem()));

        studentInfo.setStrStudentPlan504(student504Plan.getText().toString());
        //student consent date

        if((studentConsentDate.getText().toString().isEmpty() && !consentCheckBox.isChecked())) {
            studentConsentDate.setError("Student Consent Date is a required field.");
            success = false;
        }
        else {

            if(!consentCheckBox.isChecked())
                studentInfo.setStrStudentConsentDate(studentConsentDate.getText().toString());
            else
                studentInfo.setStrStudentConsentDate("N/A");

            studentConsentDate.setError(null);
        }

        if(studentIEPCheckBox.isChecked() && studentPrimaryDisabilityCat.getSelectedItemPosition() == 0)
        {
            success = false;

            ((TextView)studentPrimaryDisabilityCat.getSelectedView()).setError("When IEP check box is selected, this is a required field");
            //errorText.setText("Disability cat. required!");
        }
        else
            ((TextView)studentPrimaryDisabilityCat.getSelectedView()).setError(null);


        if(studentPlan504CheckBox.isChecked() && student504Plan.getText().toString().isEmpty())
        {
            student504Plan.setError("504 plan required when the box is checked.");
        }
        else
            student504Plan.setError(null);

        if(success) {
            Toast.makeText(this, "Success!", Toast.LENGTH_LONG).show();

            DatabaseHandler db = new DatabaseHandler(this);

            //if in editMode, update info, otherwise, add info
            if(!editMode)
                db.addStudent(studentInfo);
            else
                db.updateStudentInfo(studentInfo);

            db.addObservation(observationInfo);
        }
        else {
            Toast.makeText(this, "Please enter all the required fields", Toast.LENGTH_LONG).show();
        }

    }

    //listens for when the checkbox is clicked and then enables/disables the student consent text box accordingly
    public void consentCheckBoxListener(View view)
    {
        if(consentCheckBox.isChecked()){
            studentConsentDate.setEnabled(false);
            studentConsentDate.setText("N/A");
        }

        else {
            studentConsentDate.setEnabled(true);
            studentConsentDate.setText("");
        }

    }

    //listens for the checkbox for IEP
    public void iepCheckedListener(View view)
    {
        if(studentIEPCheckBox.isChecked()){
            studentPrimaryDisabilityCat.setEnabled(true);


        }
        else {
            studentPrimaryDisabilityCat.setEnabled(false);
            studentPrimaryDisabilityCat.setSelection(0);
        }
    }

    public void plan504CheckedListener(View view)
    {
        if(studentPlan504CheckBox.isChecked()) {
            student504Plan.setEnabled(true);

        }
        else {
            student504Plan.setEnabled(false);
            student504Plan.setText("");
        }
    }

    //When 'Find' button is pressed
    public void retrieveStudentInfo(View view)
    {
        //reset the error displayed
        resetErrors();

        DatabaseHandler db = new DatabaseHandler(this);
        Student studentInfo = db.getStudentInfo(studentId.getText().toString().trim());

        if(studentInfo != null) {
            Log.v("Firstname", studentInfo.getStrStudentFirstname());
            Log.v("Lastname", studentInfo.getStrStudentLastname());
            Log.v("DOB", studentInfo.getStrStudentDOB());
            Log.v("Ethnicity", studentInfo.getStrStudentEthinicity());
            Log.v("Primary Lang", studentInfo.getStrStudentPrimaryLang());
            Log.v("Consent Date", studentInfo.getStrStudentConsentDate());

            //display database info
            studentFirstname.setText(studentInfo.getStrStudentFirstname());
            studentLastname.setText(studentInfo.getStrStudentLastname());
            studentDOB.setText(studentInfo.getStrStudentDOB());
            studentPrimaryLang.setText(studentInfo.getStrStudentPrimaryLang());
            studentConsentDate.setText(studentInfo.getStrStudentConsentDate());

            utility.setSpinnerVal(studentInfo.getStrStudentEthinicity(), studentEthnicitySpinner);
            utility.setSpinnerVal(studentInfo.getStrStudentPrimaryDisability(), studentPrimaryDisabilityCat);

            if(studentPrimaryDisabilityCat.getSelectedItemPosition() == 0) {
                studentIEPCheckBox.setChecked(false);
                studentPrimaryDisabilityCat.setSelection(0);
            }
            else
                studentIEPCheckBox.setChecked(true);


            student504Plan.setText(studentInfo.getStrStudentPlan504());

            if(studentInfo.getStrStudentPlan504().isEmpty())
                studentPlan504CheckBox.setChecked(false);
            else
                studentPlan504CheckBox.setChecked(true);

            if(studentInfo.getStrStudentConsentDate().equals("N/A"))
            {
                consentCheckBox.setChecked(true);
            }
            else
            {
                consentCheckBox.setChecked(false);
            }

            utility.setSpinnerVal(studentInfo.getStrStudentGradeLevel(), studentGradeLevel);
            //disable the fields
            enableText(false);

            //show edit button
            editButton.setVisibility(View.VISIBLE);

        }
        else
        {
            Toast.makeText(this, "Student Information not found in the database", Toast.LENGTH_LONG).show();
            clearFields(STUDENT_ID_NOT_FOUND);
        }
        db.close();
    }


    private void resetErrors()
    {
        studentId.setError(null);
        studentFirstname.setError(null);
        studentLastname.setError(null);
        studentDOB.setError(null);
        studentConsentDate.setError(null);
        student504Plan.setError(null);

        ((TextView)studentPrimaryDisabilityCat.getSelectedView()).setError(null);
    }

    public void editButtonPressed(View view)
    {
        enableText(true);
        editMode = true;
        Toast.makeText(this, "WARNING: Any changes will also be saved in the database", Toast.LENGTH_LONG).show();

        //make edit button invisible
        editButton.setVisibility(View.INVISIBLE);
    }

    private void enableText(boolean boolVal)
    {
        studentFirstname.setEnabled(boolVal);
        studentLastname.setEnabled(boolVal);
        studentDOB.setEnabled(boolVal);
        studentPrimaryLang.setEnabled(boolVal);
        studentEthnicitySpinner.setEnabled(boolVal);
        studentLangProficiency.setEnabled(boolVal);
        studentPrimaryDisabilityCat.setEnabled(boolVal);
        studentIEPCheckBox.setEnabled(boolVal);

        if(boolVal && studentIEPCheckBox.isChecked())
            studentPrimaryDisabilityCat.setEnabled(true);
        else
            studentPrimaryDisabilityCat.setEnabled(false);


        student504Plan.setEnabled(boolVal);

        if(boolVal && studentPlan504CheckBox.isChecked())
            student504Plan.setEnabled(true);
        else
            student504Plan.setEnabled(false);

        studentPlan504CheckBox.setEnabled(boolVal);

        if(consentCheckBox.isChecked())
            studentConsentDate.setEnabled(false);
        else
            studentConsentDate.setEnabled(boolVal);

        studentGradeLevel.setEnabled(boolVal);
        consentCheckBox.setEnabled(boolVal);
    }

    //inputType variable differentiates if the clearFields action is initiated by the 'Clear Button'or all the fields are cleared after not finding student Id
    //in the database
    private void clearFields(String inputType)
    {
        resetErrors();

        switch (inputType)
        {
            case CLEAR_BUTTON:
                studentId.setText("");

                break;

            case STUDENT_ID_NOT_FOUND:
                break;

            default:

        }

        //remaining fields cleared
        studentFirstname.setText("");
        studentLastname.setText("");
        studentDOB.setText("");
        studentPrimaryLang.setText("");
        studentConsentDate.setText("");
        studentGradeLevel.setSelection(0);
        studentEthnicitySpinner.setSelection(0);
        studentLangProficiency.setSelection(0);
        studentPrimaryDisabilityCat.setSelection(0);
        studentIEPCheckBox.setChecked(false);
        consentCheckBox.setChecked(false);
        student504Plan.setText("");
        studentPlan504CheckBox.setChecked(false);

        //disable the 504 field after clearing the checkbox
        student504Plan.setEnabled(false);
        studentPrimaryDisabilityCat.setEnabled(false);

        //after clearing the fields, re-enable all the fields for editing
        enableText(true);
        editMode = false;
        
        //Edit button disappears
        editButton.setVisibility(View.INVISIBLE);


    }

    private TextWatcher dateFormatter = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count)
        {
            String inputString = charSequence.toString();
            boolean isValid = true;

            //check whether day has been entered and determine validity
            //if correct, setting the selection on the correct index spot
            if(inputString.length() == 4 && before == 0)
            {
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);

                try {
                    if (Integer.parseInt(inputString) > currentYear) {
                        isValid = false;
                    }
                        inputString+="/";
                        studentDOB.setText(inputString);
                        studentDOB.setSelection(inputString.length());

                }
                catch (NumberFormatException e)
                {
                    isValid = false;
                }
            }

            else if(inputString.length() == 7 && before == 0)
            {
                try {
                    String temp = inputString.substring(5);
                    if (Integer.parseInt(temp) < 1 || Integer.parseInt(temp) > 12) {
                        isValid = false;
                    }

                    else
                    {
                        inputString+="/";
                        studentDOB.setText(inputString);
                        studentDOB.setSelection(inputString.length());

                    }
                }
                catch (NumberFormatException e)
                {
                    isValid = false;
                }
            }
            else if(inputString.length() == 10 && before == 0)
            {

                String temp = inputString.substring(8);
                try{
                    if(Integer.parseInt(temp) < 1 || Integer.parseInt(temp) > 31)
                    {
                        isValid = false;
                    }
                }
                catch (NumberFormatException e)
                {
                    isValid = false;
                }

            }

            else if(inputString.length() > 10)
                isValid = false;

            if(!isValid)
                studentDOB.setError("Enter a valid date YYYY/MM/DD");
            else
                studentDOB.setError(null);

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}