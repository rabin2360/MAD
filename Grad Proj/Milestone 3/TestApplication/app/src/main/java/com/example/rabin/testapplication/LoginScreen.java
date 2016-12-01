package com.example.rabin.testapplication;

//import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.rabin.testapplication.Config.*;


public class LoginScreen extends AppCompatActivity {

    private EditText studentFirstname;
    private EditText studentLastname;
    private EditText studentId;
    private EditText studentDOB;
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
    private TextView previousObsLabel;

    private static final String CLEAR_BUTTON = "clear_button";
    private static final String STUDENT_ID_NOT_FOUND = "not_found";

    private Utilities utility;

    //Firebase info
    Firebase ref;

    //retrieving stored info
    private String storedObserverFirstname;
    private String storedObserverLastname;
    private String storedObserverTitle;

    private String storedTeacherFirstname;
    private String storedTeacherLastname;
    private String storedClass;

    private double storedStdObserving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //creating firebase object -testing
        Firebase.setAndroidContext(this);
        ref = new Firebase(Config.FIREBASE_URL);

        setContentView(R.layout.activity_login_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        InitStep();
        DatePickerMethod();

        //creating the instance of utilities class
        utility = new Utilities();

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
        previousObsLabel = (TextView) findViewById(R.id.previousObservation);
        previousObsLabel.setVisibility(View.INVISIBLE);

        previousObservation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleListClick(parent, view, position, id);
            }
        });

        observationButton = (Button) findViewById(R.id.startObservation);
    }

    public void navigateToObservation(View view)
    {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Choose Observation Type")
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
        if(validateAndSave())
        {
            System.out.println("Student info: "+studentInfo);
            Intent intent = new Intent(this, ObservationScreen.class);
            intent.putExtra(I_STUDENT_ID, studentInfo.getStrStudentId());
            intent.putExtra(I_OBSERVATION_TYPE,OBS_AUTOMATIC);
            clearFields(CLEAR_BUTTON);

            startActivity(intent);
        }
    }

    private void startManualObservation()
    {
        if(validateAndSave()) {
            Intent intent = new Intent(this, SummaryScreen.class);
            intent.putExtra(I_STUDENT_ID, studentInfo.getStrStudentId());
            intent.putExtra(I_OBSERVATION_TYPE, OBS_MANUAL);
            clearFields(CLEAR_BUTTON);

            startActivity(intent);
        }
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


    private boolean validateAndSave()
    {

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

        studentInfo.setStrStudentLangProficiency(String.valueOf(studentLangProficiency.getSelectedItem()));

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
            //Toast.makeText(this, "Success!", Toast.LENGTH_LONG).show();

            //DatabaseHandler db = new DatabaseHandler(this);

            //if in editMode, update info, otherwise, add info
            /*if(!editMode)
                db.addStudent(studentInfo);
            else
                db.updateStudentInfo(studentInfo);

            db.addObservation(observationInfo);
            */

            //determine if the child already exists
            ref.child(studentInfo.getStrStudentId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    ref.child(studentInfo.getStrStudentId()).child(STUDENT_INFO_TABLE).setValue(studentInfo);
                    if(dataSnapshot.hasChild(STUDENT_INFO_TABLE))
                    {
                        Log.v("MESSAGE", "updating...");
                        Toast.makeText(LoginScreen.this, "UPDATING: Student Information!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Log.v("MESSAGE", "adding...");
                        Toast.makeText(LoginScreen.this, "ADDING: Student Information!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            //ref.child(studentInfo.getStrStudentId()).child("student_observation").setValue("obs3");
        }
        else {
            Toast.makeText(this, "Please enter all the required fields", Toast.LENGTH_LONG).show();
        }

        return success;
    }

    //when okay button is pressed
    public void saveInformation(View view) {

        boolean success = validateAndSave();

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

            //display data from firebase
        if(studentId.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please enter the student ID.",Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            studentInfo.setStrStudentId(studentId.getText().toString());
        }


        ref.child(studentInfo.getStrStudentId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {
                        Student student = dataSnapshot.child("student_info").getValue(Student.class);


                        if(student == null)
                        {
                            System.out.println("Student information is not stored. Please enter one for the student.");
                            return;
                        }


                        studentFirstname.setText(student.getStrStudentFirstname());
                        studentLastname.setText(student.getStrStudentLastname());
                        studentDOB.setText(student.getStrStudentDOB());
                        studentPrimaryLang.setText(student.getStrStudentPrimaryLang());
                        studentConsentDate.setText(student.getStrStudentConsentDate());

                        utility.setSpinnerVal(student.getStrStudentEthinicity(), studentEthnicitySpinner);
                        utility.setSpinnerVal(student.getStrStudentPrimaryDisability(), studentPrimaryDisabilityCat);


                        if (studentPrimaryDisabilityCat.getSelectedItemPosition() == 0) {
                            studentIEPCheckBox.setChecked(false);
                            studentPrimaryDisabilityCat.setSelection(0);
                        } else
                            studentIEPCheckBox.setChecked(true);


                        student504Plan.setText(student.getStrStudentPlan504());

                        if (student.getStrStudentPlan504().isEmpty())
                            studentPlan504CheckBox.setChecked(false);
                        else
                            studentPlan504CheckBox.setChecked(true);

                        if (student.getStrStudentConsentDate().equals("N/A")) {
                            consentCheckBox.setChecked(true);
                        } else {
                            consentCheckBox.setChecked(false);
                        }

                        utility.setSpinnerVal(student.getStrStudentGradeLevel(), studentGradeLevel);
                        utility.setSpinnerVal(student.getStrStudentLangProficiency(), studentLangProficiency);

                        //disable the fields
                        enableText(false);

                        //show edit button
                        editButton.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Toast.makeText(LoginScreen.this, "Student Information not found in the database", Toast.LENGTH_LONG).show();
                        clearFields(STUDENT_ID_NOT_FOUND);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


        ref.child(studentInfo.getStrStudentId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(STUDENT_OBS_TABLE)) {
                    Log.v("OBS RECORDS EXIST", "YES");
                    previousObsLabel.setVisibility(View.VISIBLE);

                    long numOfObs = dataSnapshot.child(STUDENT_OBS_TABLE).getChildrenCount();
                    ArrayList <String> recordedObs = new ArrayList<String>();


                    for(DataSnapshot data: dataSnapshot.child(STUDENT_OBS_TABLE).getChildren())
                    {
                        System.out.println(data);
                        recordedObs.add("Observation: "+data.getKey().toString());
                    }

                    ArrayAdapter adapter = new ArrayAdapter<String>(LoginScreen.this, R.layout.layout_listview, recordedObs);
                    //ListView listView = (ListView) findViewById(R.id.observationList);
                    previousObservation.setAdapter(adapter);
                }
                else
                {
                    Log.v("OBS RECORDS EXIST", "NO");
                    previousObservation.setAdapter(null);
                    previousObsLabel.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //TODO: what do I do here? On Cancelled methods.
            }
        });
    }


    private void handleListClick(AdapterView<?> parent, View view, final int position, long id)
    {
        ref.child(studentInfo.getStrStudentId()).child(STUDENT_OBS_TABLE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String path = STUDENT_OBS_TABLE+Integer.toString(position+1);
                if(dataSnapshot.hasChild(Integer.toString(position+1)))
                {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.login_observation_summary,null);

                    //System.out.println("SHOW THIS: "+dataSnapshot.child(Integer.toString(position+1)).getValue().toString());
                    //System.out.println("SHOW Observer: "+ dataSnapshot.child(Integer.toString(position+1)).child(OBSERVER_INFO_TABLE).getValue().toString());
                    //getting observer info
                    storedObserverFirstname = dataSnapshot.child(Integer.toString(position+1)).child(OBSERVER_INFO_TABLE).child(STR_FIRSTNAME).getValue().toString();
                    storedObserverLastname = dataSnapshot.child(Integer.toString(position+1)).child(OBSERVER_INFO_TABLE).child(STR_LASTNAME).getValue().toString();
                    storedObserverTitle = dataSnapshot.child(Integer.toString(position+1)).child(OBSERVER_INFO_TABLE).child(STR_CLASS).getValue().toString();

                    //getting teacher info
                    storedTeacherFirstname = dataSnapshot.child(Integer.toString(position+1)).child(TEACHER_INFO_TABLE).child(STR_FIRSTNAME).getValue().toString();
                    storedTeacherLastname = dataSnapshot.child(Integer.toString(position+1)).child(TEACHER_INFO_TABLE).child(STR_LASTNAME).getValue().toString();
                    storedClass = dataSnapshot.child(Integer.toString(position+1)).child(TEACHER_INFO_TABLE).child(STR_CLASS).getValue().toString();

                    System.out.println("Data: "+dataSnapshot.child(Integer.toString(position+1)).child(STUDENT_DATA).child(CAT_OBSERVING).getValue().toString());
                    storedStdObserving =  (double) dataSnapshot.child(Integer.toString(position+1)).child(STUDENT_DATA).child(CAT_OBSERVING).getValue();

                    //TODO: Finish the pop up display
                    showAlert(layout, "Observation Summary");

                }
                else
                {
                    Log.v("ERROR: ", "No observation");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    //TODO: finish this generic alert method
    public void showAlert(View layout, String title)
    {

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setView(layout);

        //disallow cancel of Alert dialog on click of back button or outside touch
        alert.setCancelable(false);

        TextView observerFirstname = (TextView) layout.findViewById(R.id.observerFirstName);
        TextView observerLastname = (TextView) layout.findViewById(R.id.observerLastName);
        TextView observerTitle = (TextView) layout.findViewById(R.id.observerTitle);

        observerFirstname.setText(STR_FIRSTNAME+storedObserverFirstname);
        observerLastname.setText(STR_LASTNAME+storedObserverLastname);
        observerTitle.setText(STR_TITLE+storedObserverTitle);

        TextView teacherFirstname = (TextView) layout.findViewById(R.id.teacherFirstName);
        TextView teacherLastname = (TextView) layout.findViewById(R.id.teacherLastName);
        TextView currentClass = (TextView) layout.findViewById(R.id.currentClass);

        teacherFirstname.setText(STR_FIRSTNAME+storedTeacherFirstname);
        teacherLastname.setText(STR_LASTNAME+storedTeacherLastname);
        currentClass.setText(STR_CLASS+storedClass);

        TextView stdObservingData = (TextView) layout.findViewById(R.id.stdObsData);

        stdObservingData.setText(String.format("%.2f",storedStdObserving)+"%");

        /*
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        */

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        final AlertDialog dialog = alert.create();
        dialog.show();


        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                dialog.dismiss();
            }
        });

    }

    private void resetErrors()
    {
        ViewGroup group = (ViewGroup) findViewById(R.id.rootLayout);

        for(int i = 0, count = group.getChildCount(); i<count; ++i)
        {
            View view = group.getChildAt(i);

            if(view instanceof EditText)
            {
                ((EditText) view).setError(null);
            }
        }

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

        //reset the list
        previousObservation.setAdapter(null);
        previousObsLabel.setVisibility(View.INVISIBLE);

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