package com.example.rabin.testapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Arrays;

import static com.example.rabin.testapplication.Config.I_CURRENT_CLASS;
import static com.example.rabin.testapplication.Config.I_OBSERVATION_TYPE;
import static com.example.rabin.testapplication.Config.I_OBSERVER_FIRSTNAME;
import static com.example.rabin.testapplication.Config.I_OBSERVER_LASTNAME;
import static com.example.rabin.testapplication.Config.I_OBSERVER_TITLE;
import static com.example.rabin.testapplication.Config.I_OBS_FREQ;
import static com.example.rabin.testapplication.Config.I_OBS_TOTAL_TIME;
import static com.example.rabin.testapplication.Config.I_PEER_DATA;
import static com.example.rabin.testapplication.Config.I_STUDENT_DATA;
import static com.example.rabin.testapplication.Config.I_STUDENT_ID;
import static com.example.rabin.testapplication.Config.I_TEACHER_FIRSTNAME;
import static com.example.rabin.testapplication.Config.I_TEACHER_LASTNAME;
import static com.example.rabin.testapplication.Config.OBS_AUTOMATIC;

public class ObservationScreen extends AppCompatActivity {

    private Button startButton;
    private Button stopButton;
    private ImageButton observationSettingsButton;

    private TextView timerDisplay;
    private RelativeLayout observationLayout;

    //stopwatch variables
    private long startTime;
    private long elapsedTime;
    private long timeSwapBuff;
    private long updatedTime;
    private int startButtonStat;
    private long timeInMilliSeconds;
    private int seconds;
    private int minutes;
    private int hours;
    private int totalStages;
    private int currentStage;
    private HashMap <String, Integer> stdMap;
    private HashMap <String, Integer> prMap;

    private int enteredTotalTime = 0;
    private int enteredObservationFreq = 0;
    private String enteredTeacherFirstName="";
    private String enteredTeacherLastName="";
    private String enteredCurrentClass="";
    private String enteredObserverFirstName="";
    private String enteredObserverLastName="";
    private String enteredObserverTitle="";

    private Handler handler;

    private PopupWindow teacherObserverPopup;
    private Utilities utility;

    private EditText teacherFirstName;
    private EditText teacherLastName;
    private EditText currentClass;

    private EditText observerFirstName;
    private EditText observerLastName;
    private EditText observerTitle;
    private TextView stageLabel;
    private TextView observationFrequencyLabel;
    private TextView observationTotalTimeLabel;

    private boolean startButtonPressed;

    //buttons for student being observed
    private ToggleButton stdObserving;
    private ToggleButton stdParticipating;
    private ToggleButton stdDisEngaged;
    private ToggleButton stdVerbal;
    private ToggleButton stdMotor;
    private ToggleButton stdAggressive;
    private ToggleButton stdOutOfSeat;

    //buttons for peer being observed
    private ToggleButton prObserving;
    private ToggleButton prParticipating;
    private ToggleButton prDisEngaged;
    private ToggleButton prVerbal;
    private ToggleButton prMotor;
    private ToggleButton prAggressive;
    private ToggleButton prOutOfSeat;

    private String recvStudentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        utility = new Utilities();
        observationInit();
        //enableObservationButtons(false);

        //((RadioGroup) findViewById(R.id.toggleRadioGroupStd)).setOnCheckedChangeListener(ToggleListenerStd);
        //((RadioGroup) findViewById(R.id.toggleRadioGroupPr)).setOnCheckedChangeListener(ToggleListenerStd);

        recvStudentId = getIntent().getStringExtra(I_STUDENT_ID);
        System.out.println("Student ID: "+recvStudentId);

    }

    private void enableDisableToggle(boolean flag)
    {
        stdObserving.setEnabled(flag);
        stdParticipating.setEnabled(flag);
        stdDisEngaged.setEnabled(flag);
        stdVerbal.setEnabled(flag);
        stdMotor.setEnabled(flag);
        stdAggressive.setEnabled(flag);
        stdOutOfSeat.setEnabled(flag);

        prObserving.setEnabled(flag);
        prParticipating.setEnabled(flag);
        prDisEngaged.setEnabled(flag);
        prVerbal.setEnabled(flag);
        prMotor.setEnabled(flag);
        prAggressive.setEnabled(flag);
        prOutOfSeat.setEnabled(flag);
    }

    private void observationInit()
    {
        //stop watch
        resetTimerVars();

        //handler for updating timer
        handler = new Handler();


        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        timerDisplay = (TextView) findViewById(R.id.timeDisplay);
        observationSettingsButton = (ImageButton) findViewById(R.id.observationSettingsButton);
        observationLayout = (RelativeLayout) findViewById(R.id.observationScreenLayout);

        stopButton.setEnabled(false);

        startButtonPressed = false;

        //initializing the buttons
        stdObserving = (ToggleButton) findViewById(R.id.stdObsButton);
        stdParticipating = (ToggleButton) findViewById(R.id.stdParticipatingButton);
        stdDisEngaged = (ToggleButton) findViewById(R.id.stdDisEngagedButton);
        stdVerbal = (ToggleButton) findViewById(R.id.stdVerbal);
        stdMotor = (ToggleButton) findViewById(R.id.stdMotor);
        stdAggressive = (ToggleButton) findViewById(R.id.stdAggressive);
        stdOutOfSeat = (ToggleButton) findViewById(R.id.stdOutOfSeat);

        prObserving = (ToggleButton) findViewById(R.id.prObsButton);
        prParticipating = (ToggleButton) findViewById(R.id.prParticipatingButton);
        prDisEngaged = (ToggleButton) findViewById(R.id.prDisEngagedButton);
        prVerbal = (ToggleButton) findViewById(R.id.prVerbal);
        prMotor = (ToggleButton) findViewById(R.id.prMotor);
        prAggressive = (ToggleButton) findViewById(R.id.prAggressive);
        prOutOfSeat = (ToggleButton) findViewById(R.id.prOutOfSeat);

        stdMap = new HashMap<String, Integer>();
        prMap = new HashMap<String, Integer>();

        stdMap.put("observing", 0);
        stdMap.put("participating", 0);
        stdMap.put("disengaged", 0);
        stdMap.put("verbal", 0);
        stdMap.put("motor", 0);
        stdMap.put("aggressive", 0);
        stdMap.put("outofseat", 0);

        prMap.put("observing", 0);
        prMap.put("participating", 0);
        prMap.put("disengaged", 0);
        prMap.put("verbal", 0);
        prMap.put("motor", 0);
        prMap.put("aggressive", 0);
        prMap.put("outofseat", 0);

        currentStage = 1;

        stageLabel = (TextView) findViewById(R.id.stageLabel);
        observationFrequencyLabel = (TextView) findViewById(R.id.observationFrequencyDisplay);
        observationTotalTimeLabel = (TextView) findViewById(R.id.observationTotalTimeDisplay);
        enableDisableToggle(false);

        popUpInit();
    }

    public void ObservationInfo(View view)
    {
        //enableObservationButtons(false);
        popUpInit();
        loadSettingsInfo();
    }

    private Button popUpOkayButton;
    private Button popUpCancelButton;
    private Spinner observationTotalTime;
    private Spinner observationFrequency;

    private void enableObservationButtons(boolean flag)
    {

        stopButton.setEnabled(flag);
        //change this
        startButton.setEnabled(true);
    }

    private void popUpInit()
    {

        LayoutInflater inflater = getLayoutInflater(); //(LayoutInflater) ObservationScreen.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.teacher_observer_popup,null);
        //teacherObserverPopup = new PopupWindow(layout,650, 800,false);

        //pop up screen fields
        teacherFirstName = (EditText) layout.findViewById(R.id.teacherFirstName);
        teacherLastName = (EditText) layout.findViewById(R.id.teacherLastName);
        currentClass = (EditText) layout.findViewById(R.id.currentClass);

        observerFirstName = (EditText) layout.findViewById(R.id.observerFirstName);
        observerLastName = (EditText) layout.findViewById(R.id.observerLastName);
        observerTitle = (EditText) layout.findViewById(R.id.observerTitle);

        //spinners for total time and time step
        observationTotalTime = (Spinner) layout.findViewById(R.id.observationTotalTime);
        observationFrequency = (Spinner) layout.findViewById(R.id.observationFrequency);

        if(startButtonPressed)
            observationFrequency.setEnabled(false);
        else
            observationFrequency.setEnabled(true);

        //loading values in the spinner
        utility.addTimesInSpinner(observationTotalTime, this);
        utility.addTimesInSpinner(observationFrequency,this);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Settings");
        alert.setView(layout);

        //disallow cancel of Alert dialog on click of back button or outside touch
        alert.setCancelable(false);
        /*alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
           @Override
            public void onClick(DialogInterface dialog, int which)
           {

           }
        });
*/
        alert.setPositiveButton("Enter settings", new DialogInterface.OnClickListener()
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
                boolean noError = true;

                if(observationTotalTime.getSelectedItem().toString().equals("0"))
                {
                    noError = false;
                    Toast.makeText(getBaseContext(), "Please select total time and frequency.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    enteredTotalTime = (Integer)observationTotalTime.getSelectedItem();
                    //Log.v("Total time: ", Integer.toString(enteredTotalTime));
                }

                if(observationFrequency.getSelectedItem().toString().equals("0"))
                {
                    noError = false;
                    Toast.makeText(getBaseContext(), "Please select total time and frequency.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    enteredObservationFreq = (Integer)observationFrequency.getSelectedItem();
                }

                if(teacherFirstName.getText().toString().isEmpty())
                {
                    noError = false;
                    teacherFirstName.setError("Required!");
                }
                else
                {
                    enteredTeacherFirstName = teacherFirstName.getText().toString();
                    teacherFirstName.setError(null);
                }

                if(teacherLastName.getText().toString().isEmpty())
                {
                    noError = false;
                    teacherLastName.setError("Required!");
                }
                else
                {
                    enteredTeacherLastName = teacherLastName.getText().toString();
                    teacherLastName.setError(null);
                }

                if(observerFirstName.getText().toString().isEmpty())
                {
                    noError = false;
                    observerFirstName.setError("Required!");
                }
                else
                {
                   enteredObserverFirstName = observerFirstName.getText().toString();
                   observerFirstName.setError(null);
                }

                if(observerLastName.getText().toString().isEmpty())
                {
                    noError = false;
                    observerLastName.setError("Required");
                }
                else
                {
                    enteredObserverLastName = observerLastName.getText().toString();
                    observerLastName.setError(null);
                }

                if(!currentClass.getText().toString().isEmpty())
                    enteredCurrentClass = currentClass.getText().toString();
                else
                    enteredCurrentClass = "";

                if(!observerTitle.getText().toString().isEmpty())
                    enteredObserverTitle = observerTitle.getText().toString();
                else
                    enteredObserverTitle = "";

                if(noError)
                {
                    totalStages = (enteredTotalTime *60)/enteredObservationFreq;
                    displayParamsAndStage();
                    dialog.dismiss();
                }
            }
        });


    }

    private void loadSettingsInfo()
    {
        if(!enteredObserverFirstName.isEmpty())
            observerFirstName.setText(enteredObserverFirstName);

        if(!enteredObserverLastName.isEmpty())
            observerLastName.setText(enteredObserverLastName);

        if(!enteredObserverTitle.isEmpty())
            observerTitle.setText(enteredObserverTitle);

        if(!enteredTeacherFirstName.isEmpty())
            teacherFirstName.setText(enteredTeacherFirstName);

        if(!enteredTeacherLastName.isEmpty())
            teacherLastName.setText(enteredTeacherLastName);

        if(!enteredCurrentClass.isEmpty())
            currentClass.setText(enteredCurrentClass);

        if(enteredTotalTime != 0)
        {
            //System.out.println("here"+ enteredTotalTime);
            utility.setSpinnerInt(enteredTotalTime, observationTotalTime);
        }


        if(enteredObservationFreq != 0) {
            //System.out.println("here"+enteredObservationFreq);
            utility.setSpinnerInt(enteredObservationFreq, observationFrequency);
        }
    }

    private void displayParamsAndStage()
    {
        //display the stage
        stageLabel.setText("Stage: "+Integer.toString(currentStage)+"/"+Integer.toString(totalStages));
        observationFrequencyLabel.setText("Frequency: "+Integer.toString(enteredObservationFreq)+" seconds");
        observationTotalTimeLabel.setText("Total Observation Time: "+Integer.toString(enteredTotalTime)+" minutes");
        //Log.v("FREQUENCY", Integer.toString(enteredObservationFreq));
        //Log.v("TOTAL TIME", Integer.toString(enteredTotalTime));
    }

    /*private View.OnClickListener popUpCancelButtonPressed = new View.OnClickListener()
    {

        public void onClick(View view)
        {
            if(checkForRequiredFields()) {
                enteredTotalTime = (int) observationTotalTime.getSelectedItem();
                enableObservationButtons(true);
                observationLayout.setVisibility(View.VISIBLE);
                teacherObserverPopup.dismiss();
            }
            else
            {
                Toast.makeText(observationLayout.getContext(), "Please fill all the fields with *", Toast.LENGTH_LONG).show();
                observationLayout.setVisibility(View.VISIBLE);
                teacherObserverPopup.dismiss();
            }
        }
    };
*/

    private boolean checkForRequiredFields()
    {
        boolean success = true;

        //check for teacher's first and last name
        if(teacherFirstName.getText().toString().isEmpty())
        {
            success = false;
            enteredTeacherFirstName = "";
            teacherFirstName.setError("Required!");
        }
        else
        {   teacherFirstName.setError(null);
            enteredTeacherFirstName = teacherFirstName.getText().toString();
        }

        if(teacherLastName.getText().toString().isEmpty())
        {
            success = false;
            enteredTeacherLastName = "";
            teacherLastName.setError("Required!");
        }
        else
        {
            teacherLastName.setError(null);
            enteredTeacherLastName = teacherLastName.getText().toString();
        }

        if(observerFirstName.getText().toString().isEmpty())
        {
            success = false;
            enteredObserverFirstName = "";
            observerFirstName.setError("Required!");
        }
        else
        {
            observerFirstName.setError(null);
            enteredObserverFirstName = observerFirstName.getText().toString();
        }

        if(observerLastName.getText().toString().isEmpty())
        {
            success = false;
            enteredObserverLastName = "";
            observerLastName.setError("Required!");
        }
        else
        {
            observerLastName.setError(null);
            enteredObserverLastName = observerLastName.getText().toString();
        }

        if(!currentClass.getText().toString().isEmpty())
            enteredCurrentClass = currentClass.getText().toString();
        else
            enteredCurrentClass = "";

        if(!observerTitle.getText().toString().isEmpty())
            enteredObserverTitle = observerTitle.getText().toString();
        else
            enteredObserverTitle = "";

        if(observationFrequency.isEnabled())
            enteredObservationFreq = (int)observationFrequency.getSelectedItem();

        enteredTotalTime = (int) observationTotalTime.getSelectedItem();

        if(enteredObservationFreq == 0 || enteredTotalTime == 0)
        {
            success = false;
        }

        //Log.v("Entered total freq:", Integer.toString(enteredObservationFreq));
        //Log.v("Entered total time:", Integer.toString(enteredTotalTime));

        return success;
    }

    private void resetTimerVars()
    {
        startTime = 0L;
        elapsedTime = 0L;
        timeSwapBuff = 0L;
        updatedTime = 0L;
        timeInMilliSeconds = 0L;
        startButtonStat = 1;

        seconds = 0;
        minutes = 0;
        hours = 0;
    }

    public void playAction()
    {
        if(startButtonStat != 0) {
            startButton.setText("Pause");
            startTime = SystemClock.uptimeMillis();
            handler.postDelayed(updateTimer, 0);
            startButtonStat = 0;
            stopButton.setEnabled(true);
            enableDisableToggle(true);
            observationSettingsButton.setEnabled(false);
            Log.v("START", "start/resume");

        }
    }

    public void pauseAction()
    {
        if(startButtonStat != 1) {
            startButton.setText("Start");
            timeSwapBuff += timeInMilliSeconds;
            handler.removeCallbacks(updateTimer);
            startButtonStat = 1;
            enableDisableToggle(false);
            observationSettingsButton.setEnabled(true);
            Log.v("PAUSING", String.valueOf(startButtonStat));
        }
    }

    //start-stop button that works like a toggle
    public void startButtonPressed(View view)
    {
        if(startButtonStat == 1)
        {
            playAction();
        }
        else
        {
            pauseAction();
        }

        startButtonPressed = true;
    }

    private HashMap<String, Integer> showMapContents(HashMap<String, Integer> map)
    {
        for(String key: map.keySet())
        {
            System.out.print(key+": "+map.get(key)+",");
            map.put(key, 0);
        }
        System.out.println();

        return map;
    }

    private void navigateToSummaryScreen()
    {
        Intent intent = new Intent(this, SummaryScreen.class);
        intent.putExtra(I_STUDENT_ID, recvStudentId);
        intent.putExtra(I_OBS_FREQ, Integer.toString(enteredObservationFreq));
        intent.putExtra(I_OBS_TOTAL_TIME, Integer.toString(enteredTotalTime));
        intent.putExtra(I_STUDENT_DATA, stdMap);
        intent.putExtra(I_PEER_DATA, prMap);
        intent.putExtra(I_OBSERVER_FIRSTNAME, enteredObserverFirstName);
        intent.putExtra(I_OBSERVER_LASTNAME, enteredObserverLastName);
        intent.putExtra(I_OBSERVER_TITLE, enteredObserverLastName);
        intent.putExtra(I_TEACHER_FIRSTNAME, enteredTeacherFirstName);
        intent.putExtra(I_TEACHER_LASTNAME, enteredTeacherLastName);
        intent.putExtra(I_CURRENT_CLASS, enteredCurrentClass);
        intent.putExtra(I_OBSERVATION_TYPE, OBS_AUTOMATIC);
        startActivity(intent);
    }

    public void stopButtonPressed(View view)
    {
        //will pause the timer when the prompt is displayed
        //startButtonPressed(null);
        pauseAction();

        //ask the question
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Do you want to stop the observation?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetTimerVars();
                        handler.removeCallbacks(updateTimer);
                        timerDisplay.setText("00:00:00");
                        startButton.setText("Start");
                        enableDisableToggle(false);
                        startButtonPressed = false;
                        currentStage = 1;
                        updateStages = false;
                        observationSettingsButton.setEnabled(true);
                        displayParamsAndStage();
                        System.out.println("FREQ: "+enteredObservationFreq);
                        System.out.println("TOTAL TIME: "+enteredTotalTime);
                        navigateToSummaryScreen();

                        stdMap = showMapContents(stdMap);
                        prMap = showMapContents(prMap);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //startButtonStat = 0
                    }
                })
                .show();
    }

    private void refreshMethod()
    {
        if(stdObserving.isChecked())
        {
            int temp = stdMap.get("observing");
            temp++;
            stdMap.put("observing", temp);
            stdObserving.setChecked(false);
        }

        if(stdParticipating.isChecked())
        {
            int temp = stdMap.get("participating");
            temp++;
            stdMap.put("participating", temp);
            stdParticipating.setChecked(false);
        }

        if(stdDisEngaged.isChecked())
        {
            int temp = stdMap.get("disengaged");
            temp++;
            stdMap.put("disengaged", temp);
            stdDisEngaged.setChecked(false);
        }

        if(stdVerbal.isChecked())
        {
            int temp = stdMap.get("verbal");
            temp++;
            stdMap.put("verbal", temp);
            stdVerbal.setChecked(false);
        }

        if(stdMotor.isChecked())
        {
            int temp = stdMap.get("motor");
            temp++;
            stdMap.put("motor", temp);
            stdMotor.setChecked(false);
        }

        if(stdAggressive.isChecked())
        {
            int temp = stdMap.get("aggressive");
            temp++;
            stdMap.put("aggressive", temp);
            stdAggressive.setChecked(false);
        }

        if(stdOutOfSeat.isChecked())
        {
            int temp = stdMap.get("outofseat");
            temp++;
            stdMap.put("outofseat", temp);
            stdOutOfSeat.setChecked(false);
        }

        //peer buttons
        if(prObserving.isChecked())
        {
            int temp = prMap.get("observing");
            temp++;
            prMap.put("observing", temp);
            prObserving.setChecked(false);
        }

        if(prParticipating.isChecked())
        {
            int temp = prMap.get("participating");
            temp++;
            prMap.put("participating", temp);
            prParticipating.setChecked(false);
        }

        if(prDisEngaged.isChecked())
        {
            int temp = prMap.get("disengaged");
            temp++;
            prMap.put("disengaged", temp);
            prDisEngaged.setChecked(false);
        }

        if(prVerbal.isChecked())
        {
            int temp = prMap.get("verbal");
            temp++;
            prMap.put("verbal", temp);
            prVerbal.setChecked(false);
        }

        if(prMotor.isChecked())
        {
            int temp = prMap.get("motor");
            temp++;
            prMap.put("motor", temp);
            prMotor.setChecked(false);
        }

        if(prAggressive.isChecked())
        {
            int temp = prMap.get("aggressive");
            temp++;
            prMap.put("aggressive", temp);
            prAggressive.setChecked(false);
        }

        if(prOutOfSeat.isChecked())
        {
            int temp = prMap.get("outofseat");
            temp++;
            prMap.put("outofseat", temp);
            prOutOfSeat.setChecked(false);
        }

    }

    private boolean updateStages = false;

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            timeInMilliSeconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliSeconds;

            seconds = (int) (updatedTime/1000);
            minutes = seconds/60;
            seconds = seconds % 60;
            hours = minutes/60;

            if((seconds % enteredObservationFreq) == 0 && startButtonStat == 0)
            {

                if(updateStages)
                {
                    Log.v("TIME", String.valueOf(seconds));
                    updateStages = false;
                    currentStage++;
                    refreshMethod();
                }
                displayParamsAndStage();

                //Log.v("CALL FUNCTION: ", "Call Refresh method");
                //modFactor += 15;
            }
            else
            {
                updateStages = true;
            }

            timerDisplay.setText(String.format("%02d", hours)+":"+String.format("%02d", minutes)+":"+String.format("%02d",seconds));
            handler.postDelayed(this,0);



        }
    };

    @Override
    public void onPause()
    {
        super.onPause();
        Log.v("APP PAUSED", "app paused");

        if(startButtonPressed)
        {
            pauseAction();
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.v("APP RESUMED", "app resumed");

        if(startButtonPressed) {
            //ask the question
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Resume Activity")
                    .setMessage("Do you want to resume the observation?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            playAction();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //startButtonStat = 0
                            pauseAction();
                        }
                    })
                    .show();
        }
    }

    /*static final RadioGroup.OnCheckedChangeListener ToggleListenerStd = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            for(int j = 0; j<group.getChildCount(); j++)
            {
                final ToggleButton view = (ToggleButton) group.getChildAt(j);
                view.setChecked(view.getId() == checkedId);
            }
        }
    };

    static final RadioGroup.OnCheckedChangeListener ToggleListenerPr = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            for(int j = 0; j<group.getChildCount(); j++)
            {
                final ToggleButton view = (ToggleButton) group.getChildAt(j);
                view.setChecked(view.getId() == checkedId);
            }
        }
    };*/


    public void stdGreenBts(View view)
    {
        stdAggressive.setChecked(false);
        stdDisEngaged.setChecked(false);
        stdMotor.setChecked(false);
        stdOutOfSeat.setChecked(false);
        stdVerbal.setChecked(false);
    }

    public void prGreenBts(View view)
    {
        prAggressive.setChecked(false);
        prDisEngaged.setChecked(false);
        prMotor.setChecked(false);
        prOutOfSeat.setChecked(false);
        prVerbal.setChecked(false);
    }

    public void stdRedBts(View view)
    {
        stdParticipating.setChecked(false);
        stdObserving.setChecked(false);
    }

    public void prRedBts(View view)
    {
        prParticipating.setChecked(false);
        prObserving.setChecked(false);
    }
    //this will override the back button press
    /*
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    */
}
