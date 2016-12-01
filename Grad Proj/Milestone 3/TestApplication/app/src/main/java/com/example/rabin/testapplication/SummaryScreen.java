package com.example.rabin.testapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.example.rabin.testapplication.Config.*;

public class SummaryScreen extends AppCompatActivity {

    //firebase variable
    private Firebase ref;

    //intent variables
    private String recvStudentId;
    private HashMap <String, Integer> recvStdMap;
    private HashMap <String, Integer> recvPrMap;
    private String recvObsFreq;
    private String recvObsTotalTime;
    private String recvObserverFirstname;
    private String recvObserverLastname;
    private String recvObserverTitle;
    private String recvTeacherFirstname;
    private String recvTeacherLastname;
    private String recvCurrentClass;
    private String recvObservationType;

    //student variables
    private TextView studentId;
    private TextView studentFirstName;
    private TextView studentLastName;
    private ValueContainer<Student> studentInfo;

    private EditText sumStdFirstname;
    private EditText sumStdLastname;
    private EditText sumStdConsentDate;
    private EditText sumStdDOB;

    //teacher variables
    private EditText sumTeachFirstname;
    private EditText sumTeachLastname;
    private EditText sumTeachCurrentClass;

    private TextView teacherFirstname;
    private TextView teacherLastname;
    private TextView currentClass;

    //observer variables
    private EditText sumObsFirstname;
    private EditText sumObsLastname;
    private EditText sumObsTitle;

    private TextView observerFirstname;
    private TextView observerLastname;
    private TextView observerTitle;

    //observation data variables
    private TextView stdObservingData;
    private TextView stdParticipatingData;
    private TextView stdDisengagedData;
    private TextView stdVerbalData;
    private TextView stdMotorData;
    private TextView stdAggressiveData;
    private TextView stdOutOfSeatData;

    private TextView prObservingData;
    private TextView prParticipatingData;
    private TextView prDisengagedData;
    private TextView prVerbalData;
    private TextView prMotorData;
    private TextView prAggressiveData;
    private TextView prOutOfSeatData;

    private EditText sumStdObservingData;
    private EditText sumStdParticipatingData;
    private EditText sumStdDisengagedData;
    private EditText sumStdVerbalData;
    private EditText sumStdMotorData;
    private EditText sumStdAggressiveData;
    private EditText sumStdOutOfSeatData;

    private EditText sumPrObservingData;
    private EditText sumPrParticipatingData;
    private EditText sumPrDisengagedData;
    private EditText sumPrVerbalData;
    private EditText sumPrMotorData;
    private EditText sumPrAggressiveData;
    private EditText sumPrOutOfSeatData;

    //calculate data for data display
    HashMap<String, Double> percent_StdMap;
    HashMap<String, Double> percent_PrMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_screen);

        studentInfo = new ValueContainer<Student>(new Student());

        getIntentValues();
        getStudentInfo();
        initViewElements();

    }

    private void initViewElements()
    {
        //get the widget ids for student summary screen
        studentId = (TextView) findViewById(R.id.summaryStdId);
        studentFirstName = (TextView) findViewById(R.id.summaryStdFirstname);
        studentLastName = (TextView) findViewById(R.id.summaryStdLastname);

        //get the widget ids for teacher summary screen
        teacherFirstname = (TextView) findViewById(R.id.summaryTeachFirstName);
        teacherLastname = (TextView) findViewById(R.id.summaryTeachLastName);
        currentClass = (TextView) findViewById(R.id.summaryCurrentClass);

        //get the widget ids for teacher summary screen
        observerFirstname = (TextView) findViewById(R.id.summaryObsFirstName);
        observerLastname = (TextView) findViewById(R.id.summaryObsLastName);
        observerTitle = (TextView) findViewById(R.id.summaryObsTitle);

        //get the widget ids for the observation data
        stdObservingData = (TextView) findViewById(R.id.stdObsData);
        stdParticipatingData = (TextView) findViewById(R.id.stdPartData);
        stdDisengagedData = (TextView) findViewById(R.id.stdDisEngData);
        stdMotorData = (TextView) findViewById(R.id.stdMotorData);
        stdVerbalData = (TextView) findViewById(R.id.stdVerbalData);
        stdAggressiveData = (TextView) findViewById(R.id.stdAggressiveData);
        stdOutOfSeatData = (TextView) findViewById(R.id.stdOutOfSeatData);

        prObservingData = (TextView) findViewById(R.id.prObsData);
        prParticipatingData = (TextView) findViewById(R.id.prPartData);
        prDisengagedData = (TextView) findViewById(R.id.prDisEngData);
        prMotorData = (TextView) findViewById(R.id.prMotorData);
        prVerbalData = (TextView) findViewById(R.id.prVerbalData);
        prAggressiveData = (TextView) findViewById(R.id.prAggressiveData);
        prOutOfSeatData = (TextView) findViewById(R.id.prOutOfSeatData);

        //setting the main view for teacher if data is present
        setTextView(teacherFirstname, recvTeacherFirstname, STR_FIRSTNAME);
        setTextView(teacherLastname, recvTeacherLastname, STR_LASTNAME);
        setTextView(currentClass, recvCurrentClass, STR_CLASS);

        //setting the view for observer, if data is present
        setTextView(observerFirstname, recvObserverFirstname, STR_FIRSTNAME);
        setTextView(observerLastname, recvObserverLastname, STR_LASTNAME);
        setTextView(observerTitle, recvObserverTitle, STR_TITLE);

        //setting the observation table data
        hashMap_setTextView(stdObservingData,percent_StdMap, CAT_OBSERVING);
        hashMap_setTextView(stdParticipatingData,percent_StdMap, CAT_PARTICIPATING);
        hashMap_setTextView(stdDisengagedData,percent_StdMap, CAT_DISENGAGED);
        hashMap_setTextView(stdMotorData,percent_StdMap, CAT_MOTOR);
        hashMap_setTextView(stdVerbalData,percent_StdMap, CAT_VERBAL);
        hashMap_setTextView(stdAggressiveData,percent_StdMap, CAT_AGGRESSIVE);
        hashMap_setTextView(stdOutOfSeatData,percent_StdMap, CAT_OUT_OF_SEAT);

        hashMap_setTextView(prObservingData,percent_PrMap, CAT_OBSERVING);
        hashMap_setTextView(prParticipatingData,percent_PrMap, CAT_PARTICIPATING);
        hashMap_setTextView(prDisengagedData,percent_PrMap, CAT_DISENGAGED);
        hashMap_setTextView(prMotorData,percent_PrMap, CAT_MOTOR);
        hashMap_setTextView(prVerbalData,percent_PrMap, CAT_VERBAL);
        hashMap_setTextView(prAggressiveData,percent_PrMap, CAT_AGGRESSIVE);
        hashMap_setTextView(prOutOfSeatData,percent_PrMap, CAT_OUT_OF_SEAT);
    }

    //retrieving info from the database
    //FIGURE out how to assign values to outer method variables in inner method
    private void getStudentInfo()
    {
        if(recvStudentId == null || studentInfo == null)
        {
            Log.v("ERROR:","Student Id is null");
            return;
        }
        else
        {
            Log.v("APPARENTLY NOT NULL", recvStudentId);
        }

        Firebase.setAndroidContext(this);
        ref = new Firebase(Config.FIREBASE_URL);

        ref.child(recvStudentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Log.v("STUDENT INFO NOT NULL", studentInfo)
                if(studentInfo != null && studentInfo.getVal() != null)
                {
                    studentInfo.setVal(dataSnapshot.child(STUDENT_INFO_TABLE).getValue(Student.class));
                    System.out.println("Inside - student info:" + studentInfo);

                    if(studentInfo != null && studentInfo.getVal() != null && studentInfo.getVal().getStrStudentId() != null)
                        studentId.setText("Student ID: "+studentInfo.getVal().getStrStudentId());

                    if(studentInfo != null && studentInfo.getVal() != null && studentInfo.getVal().getStrStudentFirstname() != null)
                        studentFirstName.setText(STR_FIRSTNAME+studentInfo.getVal().getStrStudentFirstname());

                    if(studentInfo != null && studentInfo.getVal() != null && studentInfo.getVal().getStrStudentLastname() != null)
                        studentLastName.setText(STR_LASTNAME+studentInfo.getVal().getStrStudentLastname());
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void getIntentValues()
    {
        recvStudentId = getIntent().getStringExtra(I_STUDENT_ID);
        recvObsFreq = getIntent().getStringExtra(I_OBS_FREQ);
        recvObsTotalTime = getIntent().getStringExtra(I_OBS_TOTAL_TIME);
        recvObserverFirstname = getIntent().getStringExtra(I_OBSERVER_FIRSTNAME);
        recvObserverLastname = getIntent().getStringExtra(I_OBSERVER_LASTNAME);
        recvObserverTitle = getIntent().getStringExtra(I_OBSERVER_TITLE);
        recvTeacherFirstname = getIntent().getStringExtra(I_TEACHER_FIRSTNAME);
        recvTeacherLastname = getIntent().getStringExtra(I_TEACHER_LASTNAME);
        recvCurrentClass = getIntent().getStringExtra(I_CURRENT_CLASS);
        recvStdMap = (HashMap<String, Integer>)getIntent().getSerializableExtra(I_STUDENT_DATA);
        recvPrMap = (HashMap<String, Integer>)getIntent().getSerializableExtra(I_PEER_DATA);
        recvObservationType = getIntent().getStringExtra(I_OBSERVATION_TYPE);

        percent_StdMap = calculateDataPercentage(recvStdMap, recvObsTotalTime, recvObsFreq);
        percent_PrMap = calculateDataPercentage(recvPrMap, recvObsTotalTime, recvObsFreq);

        System.out.println("Total time: "+recvObsTotalTime);
        System.out.println("Total Frequency: "+recvObsFreq);
        System.out.println(recvStdMap);
        System.out.println(recvPrMap);
        System.out.println(percent_StdMap);
        System.out.println(percent_PrMap);

        System.out.println(recvStudentId);

        System.out.println(recvObserverFirstname);
        System.out.println(recvObserverLastname);
        System.out.println(recvObserverTitle);

        System.out.println(recvTeacherFirstname);
        System.out.println(recvTeacherLastname);
        System.out.println(recvCurrentClass);
    }

    //TO DO: Remove this method eventually. Only for debugging purposes
    private void setStudentSummaryDisplay()
    {
        System.out.println("SHOW Std Id: "+studentInfo.getVal().getStrStudentId());
        System.out.println("SHOW Std First: "+studentInfo.getVal().getStrStudentFirstname());
        System.out.println("SHOW Std Last: "+studentInfo.getVal().getStrStudentLastname());

    }

    //displays the pop up screen when the textView is clicked
    private void showStudentSummaryPopUp()
    {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.summary_student,null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Student Summary");
        alert.setView(layout);

        sumStdFirstname = (EditText) layout.findViewById(R.id.summaryStudentFirstname);
        sumStdLastname = (EditText) layout.findViewById(R.id.summaryStudentLastname);
        sumStdDOB = (EditText) layout.findViewById(R.id.summaryStudentDOB);
        sumStdConsentDate = (EditText) layout.findViewById(R.id.summaryStudentConsentDate);
        //disallow cancel of Alert dialog on click of back button or outside touch
        alert.setCancelable(false);

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                System.out.println("TESTING: "+studentInfo.getVal().getStrStudentFirstname());
            }
        });

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener()
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
                boolean success = true;
                //saving the information on the form
                if(!sumStdFirstname.getText().toString().isEmpty())
                {
                    sumStdFirstname.setError(null);
                    studentInfo.getVal().setStrStudentFirstname(sumStdFirstname.getText().toString());
                }
                else
                {
                    sumStdFirstname.setError("Required!");
                    return;
                }

                if(!sumStdLastname.getText().toString().isEmpty())
                {
                    sumStdLastname.setError(null);
                    studentInfo.getVal().setStrStudentLastname(sumStdLastname.getText().toString());
                }
                else
                {
                    sumStdLastname.setError("Required!");
                    return;
                }

                if(!sumStdDOB.getText().toString().isEmpty())
                {
                    sumStdDOB.setError(null);
                    studentInfo.getVal().setStrStudentDOB(sumStdDOB.getText().toString());
                }
                else
                {
                    sumStdDOB.setError("Required!");
                    return;
                }

                if(!sumStdConsentDate.getText().toString().isEmpty())
                {
                    sumStdConsentDate.setError(null);
                    studentInfo.getVal().setStrStudentConsentDate(sumStdConsentDate.getText().toString());
                }
                else
                {
                    sumStdConsentDate.setError("Required!");
                    return;
                }

                studentId.setText("Student ID: "+studentInfo.getVal().getStrStudentId());
                studentFirstName.setText(STR_FIRSTNAME+studentInfo.getVal().getStrStudentFirstname());
                studentLastName.setText(STR_LASTNAME+studentInfo.getVal().getStrStudentLastname());

                dialog.dismiss();
            }
        });

    }

    //after displaying the pop, this method will populate it
    private void loadStudentSummaryPage()
    {
        if(studentInfo.getVal() == null)
        {
            Log.v("ERROR: ", "Student info variable is null.");
            return;
        }

        setEditTextFields(sumStdFirstname, studentInfo.getVal().getStrStudentFirstname());
        setEditTextFields(sumStdLastname, studentInfo.getVal().getStrStudentLastname());
        setEditTextFields(sumStdDOB, studentInfo.getVal().getStrStudentDOB());
        setEditTextFields(sumStdConsentDate, studentInfo.getVal().getStrStudentConsentDate());


        /*if(sumStdFirstname != null && sumStdFirstname.getText().toString().isEmpty())
        {
            sumStdFirstname.setText(studentInfo.getVal().getStrStudentFirstname());
        }

        if(sumStdLastname != null && sumStdLastname.getText().toString().isEmpty())
        {
            sumStdLastname.setText(studentInfo.getVal().getStrStudentLastname());
        }

        if(sumStdDOB != null && sumStdDOB.getText().toString().isEmpty())
        {
            sumStdDOB.setText(studentInfo.getVal().getStrStudentDOB());
        }


        if(sumStdConsentDate != null && sumStdConsentDate.getText().toString().isEmpty())
        {
            sumStdConsentDate.setText(studentInfo.getVal().getStrStudentConsentDate());
        }*/


        //disable the buttons after that
        studentSummaryEditTextEnable(false);

    }

    //sequence of actions when pressing the TextView
    public void studentSummaryClick(View view)
    {
        showStudentSummaryPopUp();
        loadStudentSummaryPage();

    }

    //enable editing when pressing the edit button
    public void studentSummaryEditButton(View view)
    {
        studentSummaryEditTextEnable(true);
    }

    private void studentSummaryEditTextEnable(boolean flag)
    {
        sumStdFirstname.setEnabled(flag);
        sumStdLastname.setEnabled(flag);
        sumStdDOB.setEnabled(flag);
        sumStdConsentDate.setEnabled(flag);
    }

    ////////--------------------------Teacher summary-------------------------
    public void teacherSummaryClick(View view)
    {
        showTeacherSummaryPopUp();
        loadTeacherInfo();
    }

    private void showTeacherSummaryPopUp()
    {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.summary_teacher,null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Teacher Summary");
        alert.setView(layout);

        sumTeachFirstname = (EditText) layout.findViewById(R.id.popUpTeachFirstname);
        sumTeachLastname = (EditText) layout.findViewById(R.id.popUpTeachLastname);
        sumTeachCurrentClass = (EditText) layout.findViewById(R.id.popUpTeachCurrentClass);

        //disallow cancel of Alert dialog on click of back button or outside touch
        alert.setCancelable(false);

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener()
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
                if((recvTeacherFirstname = isEditTextEmpty(sumTeachFirstname, recvTeacherFirstname)) == null)
                    return;

                if((recvTeacherLastname = isEditTextEmpty(sumTeachLastname, recvTeacherLastname)) == null)
                    return;

                if(!sumTeachCurrentClass.getText().toString().isEmpty())
                {
                    recvCurrentClass = sumTeachCurrentClass.getText().toString();
                }

                //updating the display in the main summary page
                setTextView(teacherFirstname, recvTeacherFirstname, STR_FIRSTNAME);
                setTextView(teacherLastname, recvTeacherLastname, STR_LASTNAME);
                setTextView(currentClass, recvCurrentClass, STR_CLASS);

                dialog.dismiss();
            }
        });

    }

    private void loadTeacherInfo()
    {
        setEditTextFields(sumTeachFirstname, recvTeacherFirstname);
        setEditTextFields(sumTeachLastname, recvTeacherLastname);
        setEditTextFields(sumTeachCurrentClass, recvCurrentClass);

        //disable the buttons after that
        teacherSummaryEditTextEnable(false);
    }

    public void teacherSummaryEditButton(View view)
    {   //enable the buttons after that
        teacherSummaryEditTextEnable(true);
    }

    private void teacherSummaryEditTextEnable(boolean flag)
    {
        sumTeachFirstname.setEnabled(flag);
        sumTeachLastname.setEnabled(flag);
        sumTeachCurrentClass.setEnabled(flag);
    }

    ////////--------------------------Observer summary-------------------------
    public void observerSummaryClick(View view)
    {
        showObserverSummaryPopUp();
        loadObserverInfo();
    }

    private void showObserverSummaryPopUp()
    {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.summary_observer,null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Observer Summary");
        alert.setView(layout);

        sumObsFirstname = (EditText) layout.findViewById(R.id.popUpObsFirstname);
        sumObsLastname = (EditText) layout.findViewById(R.id.popUpObsLastname);
        sumObsTitle = (EditText) layout.findViewById(R.id.popUpObsTitle);

        //disallow cancel of Alert dialog on click of back button or outside touch
        alert.setCancelable(false);

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener()
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
                if((recvObserverFirstname = isEditTextEmpty(sumObsFirstname, recvObserverFirstname)) == null)
                    return;

                if((recvObserverLastname = isEditTextEmpty(sumObsLastname, recvObserverLastname)) == null)
                    return;


                if(!sumObsTitle.getText().toString().isEmpty())
                {
                    recvObserverTitle = sumObsTitle.getText().toString();
                }

                //updating the display in the main summary page
                setTextView(observerFirstname, recvObserverFirstname, STR_FIRSTNAME);
                setTextView(observerLastname, recvObserverLastname, STR_LASTNAME);
                setTextView(observerTitle, recvObserverTitle, STR_TITLE);

                dialog.dismiss();
            }
        });

    }

    private void loadObserverInfo()
    {
        setEditTextFields(sumObsFirstname, recvObserverFirstname);
        setEditTextFields(sumObsLastname, recvObserverLastname);
        setEditTextFields(sumObsTitle, recvObserverTitle);

        //disable the buttons after that
        observerEditFieldsEnable(false);

    }

    public void observerSummaryEditButton(View view)
    {
        //enable the buttons after that
        observerEditFieldsEnable(true);
    }

    private void observerEditFieldsEnable(boolean flag)
    {
        sumObsFirstname.setEnabled(flag);
        sumObsLastname.setEnabled(flag);
        sumObsTitle.setEnabled(flag);

    }

    //----------Observation summary
    public void observationSummaryClick(View view)
    {
        System.out.println("Observation Type"+recvObservationType);
        if(recvObservationType.equals(OBS_MANUAL))
        {
            observationSummaryPopUp();
            loadObservationSummaryInfo();
        }
        else
        {
            showAlert("Automatic observation data cannot be changed.");
        }
    }

    private void observationSummaryPopUp()
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.summary_observation,null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Observation Summary");
        alert.setView(layout);

        sumStdObservingData = (EditText) layout.findViewById(R.id.stdObsData);
        sumStdParticipatingData = (EditText) layout.findViewById(R.id.stdPartData);
        sumStdDisengagedData = (EditText) layout.findViewById(R.id.stdDisEngData);
        sumStdVerbalData = (EditText) layout.findViewById(R.id.stdVerbalData);
        sumStdMotorData = (EditText) layout.findViewById(R.id.stdMotorData);
        sumStdAggressiveData = (EditText) layout.findViewById(R.id.stdAggressiveData);
        sumStdOutOfSeatData = (EditText) layout.findViewById(R.id.stdOutOfSeatData);

        sumPrObservingData = (EditText) layout.findViewById(R.id.prObsData);
        sumPrParticipatingData = (EditText) layout.findViewById(R.id.prPartData);
        sumPrDisengagedData = (EditText) layout.findViewById(R.id.prDisEngData);
        sumPrVerbalData = (EditText) layout.findViewById(R.id.prVerbalData);
        sumPrMotorData = (EditText) layout.findViewById(R.id.prMotorData);
        sumPrAggressiveData = (EditText) layout.findViewById(R.id.prAggressiveData);
        sumPrOutOfSeatData = (EditText) layout.findViewById(R.id.prOutOfSeatData);


        //disallow cancel of Alert dialog on click of back button or outside touch
        alert.setCancelable(false);

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        final AlertDialog dialog = alert.create();
        dialog.show();
        dialog.getWindow().setLayout(1300,900);

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(percent_StdMap == null)
                    percent_StdMap = new HashMap<String, Double>();

                if(percent_PrMap == null)
                    percent_PrMap = new HashMap<String, Double>();

                if(!(hashMap_isEditTextEmpty(sumStdObservingData, percent_StdMap, CAT_OBSERVING)))
                 return;

                if(!(hashMap_isEditTextEmpty(sumStdParticipatingData, percent_StdMap, CAT_PARTICIPATING)))
                    return;

                if(!(hashMap_isEditTextEmpty(sumStdDisengagedData, percent_StdMap, CAT_DISENGAGED)))
                    return;

                if(!(hashMap_isEditTextEmpty(sumStdVerbalData, percent_StdMap, CAT_VERBAL)))
                    return;

                if(!(hashMap_isEditTextEmpty(sumStdMotorData, percent_StdMap, CAT_MOTOR)))
                    return;

                if(!(hashMap_isEditTextEmpty(sumStdAggressiveData, percent_StdMap, CAT_AGGRESSIVE)))
                    return;

                if(!(hashMap_isEditTextEmpty(sumStdOutOfSeatData, percent_StdMap, CAT_OUT_OF_SEAT)))
                    return;


                System.out.println("Before peer check");

                if(!(hashMap_isEditTextEmpty(sumPrObservingData, percent_PrMap, CAT_OBSERVING)))
                {
                    System.out.println("Failed - observing");
                    return;
                }

                if(!(hashMap_isEditTextEmpty(sumPrParticipatingData, percent_PrMap, CAT_PARTICIPATING)))
                {
                    System.out.println("Failed - participating");
                    return;
                }

                if(!(hashMap_isEditTextEmpty(sumPrDisengagedData, percent_PrMap, CAT_DISENGAGED)))
                {
                    System.out.println("Failed - disengaged");
                    return;
                }

                if(!(hashMap_isEditTextEmpty(sumPrVerbalData, percent_PrMap, CAT_VERBAL)))
                {
                    System.out.println("Failed - verbal");
                    return;
                }
                if(!(hashMap_isEditTextEmpty(sumPrMotorData, percent_PrMap, CAT_MOTOR)))
                {
                    System.out.println("Failed - motor");
                    return;
                }
                if(!(hashMap_isEditTextEmpty(sumPrAggressiveData, percent_PrMap, CAT_AGGRESSIVE)))
                {
                    System.out.println("Failed - aggressive");
                    return;
                }

                if(!(hashMap_isEditTextEmpty(sumPrOutOfSeatData, percent_PrMap, CAT_OUT_OF_SEAT)))
                {
                    System.out.println("Failed - outofseat");
                    return;
                };


                System.out.println("Before adding data");
                hashMap_setTextView(stdObservingData, percent_StdMap, CAT_OBSERVING);
                hashMap_setTextView(stdParticipatingData, percent_StdMap, CAT_PARTICIPATING);
                hashMap_setTextView(stdDisengagedData, percent_StdMap, CAT_DISENGAGED);
                hashMap_setTextView(stdVerbalData, percent_StdMap, CAT_VERBAL);
                hashMap_setTextView(stdMotorData, percent_StdMap, CAT_MOTOR);
                hashMap_setTextView(stdAggressiveData, percent_StdMap, CAT_AGGRESSIVE);
                hashMap_setTextView(stdOutOfSeatData, percent_StdMap, CAT_OUT_OF_SEAT);

                hashMap_setTextView(prObservingData, percent_PrMap, CAT_OBSERVING);
                hashMap_setTextView(prParticipatingData, percent_PrMap, CAT_PARTICIPATING);
                hashMap_setTextView(prDisengagedData, percent_PrMap, CAT_DISENGAGED);
                hashMap_setTextView(prVerbalData, percent_PrMap, CAT_VERBAL);
                hashMap_setTextView(prMotorData, percent_PrMap, CAT_MOTOR);
                hashMap_setTextView(prAggressiveData, percent_PrMap, CAT_AGGRESSIVE);
                hashMap_setTextView(prOutOfSeatData, percent_PrMap, CAT_OUT_OF_SEAT);


                System.out.println("Before dismiss");
                dialog.dismiss();
            }
        });

    }

    private void loadObservationSummaryInfo()
    {
        hashMap_setEditTextView(sumStdObservingData, percent_StdMap, CAT_OBSERVING);
        hashMap_setEditTextView(sumStdParticipatingData, percent_StdMap, CAT_PARTICIPATING);
        hashMap_setEditTextView(sumStdDisengagedData, percent_StdMap, CAT_DISENGAGED);
        hashMap_setEditTextView(sumStdVerbalData, percent_StdMap, CAT_VERBAL);
        hashMap_setEditTextView(sumStdMotorData, percent_StdMap, CAT_MOTOR);
        hashMap_setEditTextView(sumStdAggressiveData, percent_StdMap, CAT_AGGRESSIVE);
        hashMap_setEditTextView(sumStdOutOfSeatData, percent_StdMap, CAT_OUT_OF_SEAT);

        
        hashMap_setEditTextView(sumPrObservingData, percent_PrMap, CAT_OBSERVING);
        hashMap_setEditTextView(sumPrParticipatingData, percent_PrMap, CAT_PARTICIPATING);
        hashMap_setEditTextView(sumPrDisengagedData, percent_PrMap, CAT_DISENGAGED);
        hashMap_setEditTextView(sumPrVerbalData, percent_PrMap, CAT_VERBAL);
        hashMap_setEditTextView(sumPrMotorData, percent_PrMap, CAT_MOTOR);
        hashMap_setEditTextView(sumPrAggressiveData, percent_PrMap, CAT_AGGRESSIVE);
        hashMap_setEditTextView(sumPrOutOfSeatData, percent_PrMap, CAT_OUT_OF_SEAT);

        summaryDataEditFieldsEnable(false);
    }

    private void summaryDataEditFieldsEnable(boolean flag)
    {
        sumStdObservingData.setEnabled(flag);
        sumStdParticipatingData.setEnabled(flag);
        sumStdDisengagedData.setEnabled(flag);
        sumStdVerbalData.setEnabled(flag);
        sumStdMotorData.setEnabled(flag);
        sumStdAggressiveData.setEnabled(flag);
        sumStdOutOfSeatData.setEnabled(flag);

        sumPrObservingData.setEnabled(flag);
        sumPrParticipatingData.setEnabled(flag);
        sumPrDisengagedData.setEnabled(flag);
        sumPrVerbalData.setEnabled(flag);
        sumPrMotorData.setEnabled(flag);
        sumPrAggressiveData.setEnabled(flag);
        sumPrOutOfSeatData.setEnabled(flag);

    }

    public void dataSummaryEditButton(View view)
    {
        summaryDataEditFieldsEnable(true);
    }

    //------------Save data to Database
    public void saveToDatabase(View view)
    {
        //check for nulls
        if(checkForNecessaryFields())
        {
            //save teacher info
            saveInformationToDatabase();
        }


        //save observer info
        //saveObserverInfo();

        //save observation data
    }

    private void showAlert(String message)
    {

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ERROR: Missing Information.");

        alert.setMessage(message);

        /*alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
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

    }

    private boolean checkStudentInfo()
    {
        boolean success = true;

        //check for student name, DOB, Consent date
        if((studentInfo == null) || (studentInfo.getVal() == null))
        {
            showAlert("Cannot save to the database. Student Info missing. Please check student info.");
            Log.v("ERROR: ","Student Info is either null or not instantiated.");
            success = false;
            return success;
        }

        if(studentInfo.getVal().getStrStudentId() == null || studentInfo.getVal().getStrStudentId().isEmpty())
        {
            showAlert("Cannot save to the database. Student Info missing. Please check student info.");
            Log.v("ERROR: ","Student Id is either null or empty string.");
            success = false;
            return success;
        }

        if(studentInfo.getVal().getStrStudentFirstname() == null || studentInfo.getVal().getStrStudentFirstname().isEmpty())
        {
            showAlert("Cannot save to the database. Student Info missing. Please check student info.");
            Log.v("ERROR: ","Student Firstname is either null or empty string.");
            success = false;
            return success;
        }

        if(studentInfo.getVal().getStrStudentLastname() == null || studentInfo.getVal().getStrStudentLastname().isEmpty())
        {
            showAlert("Cannot save to the database. Student Info missing. Please check student info.");
            Log.v("ERROR: ","Student Lastname is either null or empty string.");
            success = false;
            return success;
        }

        if(studentInfo.getVal().getStrStudentDOB() == null || studentInfo.getVal().getStrStudentDOB().isEmpty())
        {
            showAlert("Cannot save to the database. Student Info missing. Please check student info.");
            Log.v("ERROR: ","Student DOB is either null or empty string.");
            success = false;
            return success;
        }

        if(studentInfo.getVal().getStrStudentConsentDate() == null && studentInfo.getVal().getStrStudentConsentDate().isEmpty())
        {
            showAlert("Cannot save to the database. Student Info missing. Please check student info.");
            Log.v("ERROR: ","Student Consent Date is either null or empty string.");
            success = false;
            return success;
        }

        return success;
    }

    private boolean checkTeacherInfo()
    {
        boolean success = true;

        if(recvTeacherFirstname == null || recvTeacherFirstname.trim().isEmpty())
        {
            showAlert("Cannot save to the database. Teacher Info missing. Please check teacher info.");
            Log.v("ERROR: ", "Teacher Firstname is either null or empty string.");
            success = false;
            return success;
        }

        if(recvTeacherLastname == null || recvTeacherLastname.trim().isEmpty())
        {
            showAlert("Cannot save to the database. Teacher Info missing. Please check teacher info.");
            Log.v("ERROR: ", "Teacher Lastname is either null or empty string.");
            success = false;
            return success;
        }

        if(recvCurrentClass == null || recvCurrentClass.trim().isEmpty())
        {
            recvCurrentClass = NO_DATA;
        }

        return success;
    }

    private boolean checkObserverInfo()
    {
        boolean success = true;

        if(recvObserverFirstname == null || recvObserverFirstname.trim().isEmpty())
        {
            showAlert("Cannot save to the database. Observer Info missing. Please check observer info.");
            Log.v("ERROR: ", "Observer Firstname is either null or empty string.");
            success = false;
            return success;
        }

        if(recvObserverLastname == null || recvObserverLastname.trim().isEmpty())
        {
            showAlert("Cannot save to the database. Observer Info missing. Please check observer info.");
            Log.v("ERROR: ", "Observer Lastname is either null or empty string.");
            success = false;
            return success;
        }

        if(recvObserverTitle == null || recvObserverTitle.trim().isEmpty())
        {
            recvObserverTitle = NO_DATA;
        }

        return success;
    }

    private boolean checkForObservationData()
    {
        boolean success = true;

        if(percent_PrMap == null || percent_StdMap == null)
        {
            showAlert("Cannot save to the database. Observating data missing. Please check data observation table.");
            Log.v("ERROR:", "Either Peer or Student data map has not instantiated.");
            success = false;
            return success;
        }

        return success;
    }

    private boolean checkForNecessaryFields()
    {
        boolean success = true;


        if(!checkStudentInfo())
        {
            success = false;
            return success;
        }
        else
        {
            Log.v("PASSED: ", "Passed Student Info checks");
        }

        if(!checkObserverInfo())
        {
            success = false;
            return success;
        }
        else
        {
            Log.v("PASSED: ", "Passed Observer Info checks");
        }

        if(!checkTeacherInfo())
        {
            success = false;
            return success;
        }
        else
        {
            Log.v("PASSED: ", "Passed Teacher Info checks");
        }

        if(!checkForObservationData())
        {
            success = false;
            return success;
        }
        else
        {
            Log.v("PASSED: ", "Passed Observation Info checks");
        }

        return  success;
    }

    private void saveInformationToDatabase()
    {
        long testing;
        ref.child(studentInfo.getVal().getStrStudentId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long index = dataSnapshot.child(STUDENT_OBS_TABLE).getChildrenCount();
                index++;
                System.out.println("Children: "+index);
                //teacher info
                ref.child((studentInfo.getVal().getStrStudentId())).child(STUDENT_OBS_TABLE).child(Long.toString(index)).child(TEACHER_INFO_TABLE).child(STR_FIRSTNAME).setValue(recvTeacherFirstname);
                ref.child((studentInfo.getVal().getStrStudentId())).child(STUDENT_OBS_TABLE).child(Long.toString(index)).child(TEACHER_INFO_TABLE).child(STR_LASTNAME).setValue(recvTeacherLastname);
                ref.child((studentInfo.getVal().getStrStudentId())).child(STUDENT_OBS_TABLE).child(Long.toString(index)).child(TEACHER_INFO_TABLE).child(STR_CLASS).setValue(recvCurrentClass);

                //observer info
                ref.child((studentInfo.getVal().getStrStudentId())).child(STUDENT_OBS_TABLE).child(Long.toString(index)).child(OBSERVER_INFO_TABLE).child(STR_FIRSTNAME).setValue(recvObserverFirstname);
                ref.child((studentInfo.getVal().getStrStudentId())).child(STUDENT_OBS_TABLE).child(Long.toString(index)).child(OBSERVER_INFO_TABLE).child(STR_LASTNAME).setValue(recvObserverLastname);
                ref.child((studentInfo.getVal().getStrStudentId())).child(STUDENT_OBS_TABLE).child(Long.toString(index)).child(OBSERVER_INFO_TABLE).child(STR_CLASS).setValue(recvObserverTitle);

                //student data
                ref.child((studentInfo.getVal().getStrStudentId())).child(STUDENT_OBS_TABLE).child(Long.toString(index)).child(STUDENT_DATA).setValue(percent_StdMap);

                //peer data
                ref.child((studentInfo.getVal().getStrStudentId())).child(STUDENT_OBS_TABLE).child(Long.toString(index)).child(PEER_DATA).setValue(percent_PrMap);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("ERROR: ", "Error saving student info, student data, peer data, teacher info and observer info.");
            }
        });

    }

    //-----------Utility functions for this class

    private void setTextView(TextView view, String input, String displayTag)
    {
        if(view != null && input != null)
        {
            view.setText(displayTag+input);
        }
        else
        {
            view.setText(displayTag);
        }
    }

    private void setEditTextFields(EditText view, String input)
    {
        if(input != null && view.getText().toString().isEmpty())
            view.setText(input);
    }


    private String isEditTextEmpty(EditText view, String input)
    {
        if(!view.getText().toString().isEmpty())
        {
            view.setError(null);
            input = view.getText().toString();
        }
        else
        {
            view.setError("Required!");
            input = null;
        }

        return input;
    }

    private boolean hashMap_isEditTextEmpty(EditText view, HashMap<String, Double> map, String keyValue)
    {
        if (map == null || view == null || keyValue == null)
            return false;

        if(!view.getText().toString().trim().isEmpty())
        {
            map.put(keyValue, Double.parseDouble(view.getText().toString()));
        }
        else
        {
            view.setError("Required!");
            map.put(keyValue, 0.00);
            return false;
        }

        return true;
    }

    private HashMap<String, Double> calculateDataPercentage(HashMap<String, Integer> input, String totalTime, String frequency)
    {
        if(input == null || totalTime == null || frequency == null)
            return null;

        HashMap<String, Double> result = new HashMap<String, Double>();
        int int_totalTime = Integer.parseInt(totalTime);
        int int_frequency = Integer.parseInt(frequency);
        int totalObservationStages = (60*int_totalTime)/int_frequency;

        Iterator iterate = input.entrySet().iterator();

        while(iterate.hasNext())
        {
            Map.Entry pair = (Map.Entry) iterate.next();
            double keyValue = (int) pair.getValue();
            keyValue = (keyValue/totalObservationStages)*100;
            result.put((String)pair.getKey(),keyValue);
        }

        return result;
    }

    private void hashMap_setTextView(TextView view,HashMap<String, Double> map, String keyName)
    {
        if(map == null)
        {
            view.setText("null");
            return;
        }

        double data = map.get(keyName);

        StringBuilder percentData = new StringBuilder();
        percentData.append(String.format("%.2f", data));
        percentData.append("%");

        System.out.println(percentData.toString());

        view.setText(percentData.toString());
    }

    private void hashMap_setEditTextView(EditText view, HashMap <String, Double> map, String keyName)
    {
        if(map == null || keyName == null || view == null)
            return;

        if(map.get(keyName) != null)
        {
            StringBuilder data = new StringBuilder();
            data.append(String.format("%.2f",map.get(keyName)));
            //data.append("%");
            view.setText(data.toString());
        }
    }

}
