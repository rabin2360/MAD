package com.example.rabin.testapplication;

import android.os.Handler;
import android.os.SystemClock;

/**
 * Created by rabin on 8/23/16.
 */
public class TimerClass {

    private long startTime;
    private long timeInMilliSeconds;
    private long timeSwapBuffer;
    private long updatedTime;
    private Handler customHandler = new Handler();

    public TimerClass()
    {
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread,0);
    }

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliSeconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuffer + timeInMilliSeconds;

            int seconds = (int) (updatedTime/1000);
            int mins = seconds/60;
            seconds = seconds % 60;

            int milliseconds = (int) (updatedTime % 1000);

            customHandler.postDelayed(this,0);
        }
    };
}
