package com.example.hyeon_dongjin.raspberrypi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.contrib.driver.pwmservo.Servo;
import java.io.IOException;

public class ServoActivity extends Activity {
    private static final String TAG = ServoActivity.class.getSimpleName();
    private Servo mServo;

    Thread mThread;

    Runnable mRunnable = new Runnable() {
        public void run() {
            while (true) {
                try {
                    mServo.setAngle(-90);
                } catch (IOException e) {}
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {}
                try {
                    mServo.setAngle(90);
                } catch (IOException e) {}
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {}
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            mServo = new Servo("PWM0");
            mServo.setPulseDurationRange(0.75,2.6);
            mServo.setAngleRange(-90, 90);
            mServo.setEnabled(true);
        } catch (IOException e) {}
        mThread = new Thread(mRunnable);
        mThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mServo != null) {
            try {
                mServo.close();
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            }
        }
    }

}
