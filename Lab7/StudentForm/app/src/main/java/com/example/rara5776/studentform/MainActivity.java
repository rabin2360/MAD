package com.example.rara5776.studentform;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    EditText studentName;
    Spinner studentGrades;
    ToggleButton studentToggle;
    Switch studentSwitch;
    RadioGroup radioGroupVar;
    CheckBox checkBox1;
    CheckBox checkBox2;
    ImageView image;
    TextView display;

    //view variables
    String studentGradeStr;

    public void submitButtonPressed(View view)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(studentName.getText().toString());
        sb.append(", who is ");

        //submit button has been pressed
        Log.v("Name: ", studentName.getText().toString());

        studentGradeStr = String.valueOf(studentGrades.getSelectedItem());

        if(studentGradeStr.equals("Freshman"))
        {
            image.setImageResource(R.drawable.freshman);
        }
        else if(studentGradeStr.equals("Sophomore"))
        {
            image.setImageResource(R.drawable.sophomore);
        }
        else if(studentGradeStr.equals("Junior"))
        {
            image.setImageResource(R.drawable.junior);
        }
        else
        {
            image.setImageResource(R.drawable.senior);
        }

        Log.v("Student Grade: ", studentGradeStr);
        sb.append(studentGradeStr);
        sb.append(" and lives ");

        //processing the toggle button
        String toggleString;

        if(studentToggle.isChecked())
        {
            toggleString = studentToggle.getTextOn().toString();
        }
        else
        {
            toggleString = studentToggle.getTextOff().toString();
        }

        Log.v("Toggle: ", toggleString);
        sb.append(toggleString);

        //switches
        String switchString = "";
        boolean studentSwitchBool = studentSwitch.isChecked();

        if(studentSwitchBool)
        {
            switchString = ", meditates";
        }
        else
        {
            switchString = ", does not meditate";
        }

        Log.v("Switch:", switchString);
        sb.append(switchString);
        sb.append(", votes ");

        //radio buttons group
        String radioButtonType = "";
        int radioButtonId = radioGroupVar.getCheckedRadioButtonId();

        switch(radioButtonId)
        {
            case -1:
                radioButtonType = "Not defined ";
                break;

            case R.id.firstButton:
                radioButtonType = "Republican ";
                break;

            case R.id.secondButton:
                radioButtonType = "Democrat ";
                break;

            default:
                radioButtonType = "error ";
        }

        Log.v("Radio Button Type:", radioButtonType);
        sb.append(radioButtonType);
        sb.append("and plays ");

        boolean check1 = checkBox1.isChecked();
        boolean check2 = checkBox2.isChecked();

        if(check1 && check2)
        {
            sb.append(checkBox1.getText().toString());
            sb.append(" and ");
            sb.append(checkBox2.getText().toString());

        }
        else if(check2)
        {
            sb.append(checkBox2.getText().toString());
        }
        else if(check1)
        {
            sb.append(checkBox1.getText().toString());
        }

        sb.append(".");

        Log.v("FINAL", sb.toString());
        display.setText(sb.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //student text label
        studentName = (EditText) findViewById(R.id.editText);

        //spinner
        studentGrades = (Spinner)findViewById(R.id.spinner);

        //toggle
        studentToggle = (ToggleButton) findViewById(R.id.toggleStudent);

        //switch
        studentSwitch = (Switch) findViewById(R.id.switch1);

        //radio group
        radioGroupVar = (RadioGroup) findViewById(R.id.radioButtonGroup);

        //check box 1
        checkBox1 = (CheckBox)findViewById(R.id.checkBox);

        //check box 2
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);

        //imageview
        image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.drawable.freshman);

        //display
        display = (TextView) findViewById(R.id.displayLabel);
    }

}
