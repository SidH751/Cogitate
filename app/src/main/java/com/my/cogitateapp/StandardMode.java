package com.my.cogitateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.my.cogitateapp.R;

public class StandardMode extends AppCompatActivity {

    private EditText mEditText;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private Button mButtonSet;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mStartTimeMillis;
    private long mTimeLeftMillis;
    private long mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_mode);

        mEditText = findViewById(R.id.text_input);
        mTextViewCountDown = findViewById(R.id.view_counter);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_stop);
        mButtonSet = findViewById(R.id.button_set);

        mButtonSet.setOnClickListener( p -> {
            String input = mEditText.getText().toString();
            if(input.length() == 0) {
                Toast.makeText(this, "field can't be empty", Toast.LENGTH_SHORT).show();
                return;

            }
            long millisInput = Long.parseLong(input)*60000;
            if(millisInput == 0){
                Toast.makeText(this, "please Enter positive number", Toast.LENGTH_SHORT).show();
                return;
            }
            setTime(millisInput);
            mEditText.setText("");
            mEditText.setVisibility(View.GONE);
            mButtonSet.setVisibility(View.GONE);
            mButtonStartPause.setVisibility(View.VISIBLE);
        });

        mButtonStartPause.setOnClickListener(p -> {

            mButtonStartPause.setText("Resume");
            if(mTimerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        mButtonReset.setOnClickListener(p -> {
            stopTimer();
        });
    }

    private void setTime(long milliseconds) {
        mStartTimeMillis = milliseconds;
        resetTimer();
    }
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.GONE);

            }
        }.start();
        mTimerRunning = true;
        mButtonStartPause.setText("Pause");
        mButtonReset.setVisibility(View.GONE);
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Resume");
        mButtonReset.setVisibility(View.VISIBLE);

    }
    private void resetTimer() {
        mTimeLeftMillis = mStartTimeMillis;
        updateCountDownText();
        mButtonReset.setVisibility(View.GONE);
    }

    private void stopTimer() {
        mTimeLeftMillis = 0;
        updateCountDownText();
        mButtonStartPause.setText("Start");
        mButtonStartPause.setVisibility(View.GONE);
        mButtonReset.setVisibility(View.GONE);
        mButtonSet.setVisibility(View.VISIBLE);
        mEditText.setVisibility(View.VISIBLE);
    }
    private void updateCountDownText() {
        int hours = (int) (mTimeLeftMillis/1000)/3600;
        int minutes = (int) ((mTimeLeftMillis/1000)%3600)/60;
        int seconds = (int) (mTimeLeftMillis/1000)%60;
        String timeLeftFormatted;

        if(hours>0){
            timeLeftFormatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }
}