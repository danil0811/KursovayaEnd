package com.example.kursovayaend;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BarpiActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private Button StartTimeBarpi;
    private Button goToActivityJump;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barpi);

        timePicker = findViewById(R.id.timePicker);
        StartTimeBarpi = findViewById(R.id.startBarpi);
        goToActivityJump = findViewById(R.id.GoToJump);

        StartTimeBarpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int minutes = timePicker.getCurrentMinute();
                long totalTimeInMillis = minutes * 60 * 1000;
                startTimer(totalTimeInMillis);
            }
        });

        goToActivityJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BarpiActivity.this, JumpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startTimer(long totalTimeInMillis) {
        countDownTimer = new CountDownTimer(totalTimeInMillis, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                // Действия, которые нужно выполнить по завершении таймера
                Toast.makeText(BarpiActivity.this, "Тренировка завершена", Toast.LENGTH_SHORT).show();
                playNotificationSound();
            }
        };
        countDownTimer.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);

        // Обновление TextView с таймером
        Toast.makeText(BarpiActivity.this, timeLeftFormatted, Toast.LENGTH_SHORT).show();
    }

    private void playNotificationSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.notification_sound);
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}