package com.example.kursovayaend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityViborTrenirovki extends AppCompatActivity
{
    private Button goToKardio, goToGym;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibor_trenirovki);

        goToKardio = findViewById(R.id.GoToKardio);
        goToGym = findViewById(R.id.GoToGym);

        goToKardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(ActivityViborTrenirovki.this,ActivitySpisokUpazneniKardio.class);
                startActivity(Intent);
            }
        });

        goToGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(ActivityViborTrenirovki.this, SpisokUprazneniy.class);
                startActivity(Intent);
            }
        });
    }
}