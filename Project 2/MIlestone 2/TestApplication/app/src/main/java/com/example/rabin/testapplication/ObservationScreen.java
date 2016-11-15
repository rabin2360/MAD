package com.example.rabin.testapplication;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ObservationScreen extends AppCompatActivity {

    private Button startButton;
    private Button stopButton;
    private Button observationSettingsButton;

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

    private int enteredTotalTime = 15;
    private int enteredObservationFreq = 15;
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

    private boolean startButtonPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        observationInit();
        enableObservationButtons(false);
        utility = new Utilities();
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
        observationSettingsButton = (Button) findViewById(R.id.observationSettingsButton);
        observationLayout = (RelativeLayout) findViewById(R.id.observationScreenLayout);

        stopButton.setEnabled(false);

        startButtonPressed = false;
    }

    public void ObservationInfo(View view)
    {
        enableObservationButtons(false);
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
        startButton.setEnabled(flag);
    }

    private void popUpInit()
    {
        LayoutInflater inflater = (LayoutInflater) ObservationScreen.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.teacher_observer_popup,(ViewGroup) findViewById(R.id.popup_element));
        teacherObserverPopup = new PopupWindow(layout,650, 800,false);

        //teacherObserverPopup = new PopupWindow(layout, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT,false);
        teacherObserverPopup.showAtLocation(layout, Gravity.CENTER, 0,0);
        teacherObserverPopup.setFocusable(true);
        teacherObserverPopup.update();

        popUpOkayButton = (Button) layout.findViewById(R.id.popUpOkayButton);
        popUpCancelButton = (Button) layout.findViewById(R.id.popUpCancelButton);

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

        popUpCancelButton.setOnClickListener(popUpCancelButtonPressed);

        observationLayout.setVisibility(View.GONE);
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

        if(enteredTotalTime != 15)
        {
            System.out.println("here"+ enteredTotalTime);
            utility.setSpinnerInt(enteredTotalTime, observationTotalTime);
        }


        if(enteredObservationFreq != 15) {
            System.out.println("here"+enteredObservationFreq);
            utility.setSpinnerInt(enteredObservationFreq, observationFrequency);
        }
    }

    private View.OnClickListener popUpCancelButtonPressed = new View.OnClickListener()
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
            }
        }
    };


    private boolean checkForRequiredFields()
    {
        boolean success = true;

        //check for teacher's first and last name
        if(teacherFirstName.getText().toString().isEmpty())
        {
            success = false;
            enteredTeacherFirstName = "";
            //teacherFirstName.setBackgroundColor(Color.RED);
            //teacherFirstName.setError("Teacher's first name is required!");
        }
        else
            enteredTeacherFirstName = teacherFirstName.getText().toString();

        if(teacherLastName.getText().toString().isEmpty())
        {
            success = false;
            enteredTeacherLastName = "";
            //teacherLastName.setError("Teacher's last name is required!");
        }
        else
            enteredTeacherLastName = teacherLastName.getText().toString();


        if(observerFirstName.getText().toString().isEmpty())
        {
            success = false;
            enteredObserverFirstName = "";
            //observerFirstName.setError("Observer's first name is required!");
        }
        else
            enteredObserverFirstName = observerFirstName.getText().toString();


        if(observerLastName.getText().toString().isEmpty())
        {
            success = false;
            enteredObserverLastName = "";
            //observerLastName.setError("Observer's last name is required!");
        }
        else
            enteredObserverLastName = observerLastName.getText().toString();


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

        Log.v("Entered total freq:", Integer.toString(enteredObservationFreq));
        Log.v("Entered total time:", Integer.toString(enteredTotalTime));

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

    //start-stop button that works like a toggle
    public void startButtonPressed(View view)
    {
        if(startButtonStat == 1)
        {
            startButton.setText("Pause");
            startTime = SystemClock.uptimeMillis();
            handler.postDelayed(updateTimer, 0);
            startButtonStat = 0;
            stopButton.setEnabled(true);
        }
        else
        {
            startButton.setText("Start");
            timeSwapBuff += timeInMilliSeconds;
            handler.removeCallbacks(updateTimer);
            startButtonStat = 1;
        }

     startButtonPressed = true;
    }

    public void stopButtonPressed(View view)
    {
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
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startButtonStat = 0;
                        startButtonPressed(null);
                    }
                })
                .show();
    }


    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            timeInMilliSeconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliSeconds;

            seconds = (int) (updatedTime/1000);
            minutes = seconds/60;
            seconds = seconds % 60;
            hours = minutes/60;

            timerDisplay.setText(String.format("%02d", hours)+":"+String.format("%02d", minutes)+":"+String.format("%02d",seconds));
            handler.postDelayed(this,0);
        }
    };

    //this will override the back button press
    /*
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
    */
}
