package com.example.kursovayaend;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BicepsActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private Button StartTimeBiceps;
    private Button goToActivityPlechi;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biceps);

        timePicker = findViewById(R.id.timePicker);
        StartTimeBiceps = findViewById(R.id.startBiceps);
        goToActivityPlechi = findViewById(R.id.GoToPlechi);

        StartTimeBiceps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int minutes = timePicker.getCurrentMinute();
                long totalTimeInMillis = minutes * 60 * 1000;
                startTimer(totalTimeInMillis);
            }
        });

        goToActivityPlechi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BicepsActivity.this, PlechiActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startTimer(long totalTimeInMillis) {
        countDownTimer = new CountDownTimer(totalTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                // Действия, которые нужно выполнить по завершении таймера
                Toast.makeText(BicepsActivity.this, "Тренировка завершена", Toast.LENGTH_SHORT).show();
            }
        };
        countDownTimer.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);

        // Обновление TextView с таймером
        Toast.makeText(BicepsActivity.this, timeLeftFormatted, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}