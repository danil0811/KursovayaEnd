package com.example.kursovayaend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivitySpisokUpazneniKardio extends AppCompatActivity
{

    private Button startTrenirovkiKardio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spisok_upazneni_kardio);

        startTrenirovkiKardio = findViewById(R.id.BtnStartTrenirovkiKardio);

        startTrenirovkiKardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(ActivitySpisokUpazneniKardio.this, SkalolazActivity.class);
                startActivity(Intent);
            }
        });

    }
}