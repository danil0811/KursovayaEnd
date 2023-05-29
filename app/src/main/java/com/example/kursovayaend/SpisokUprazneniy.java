package com.example.kursovayaend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SpisokUprazneniy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spisok_uprazneniy);

        Button btnStartTrenirovki = findViewById(R.id.BtnStartTrenirovki);

        btnStartTrenirovki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpisokUprazneniy.this, GymChest.class);
                startActivity(intent);
            }
        });
    }
}