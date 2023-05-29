package com.example.kursovayaend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button joinButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinButton = (Button) findViewById(R.id.MainJoinBtn);
        loginButton = (Button) findViewById(R.id.MainLoginBtn);

        loginButton.setOnClickListener((v) -> {
            Intent LoginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(LoginIntent);
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegisterIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(RegisterIntent);
            }
        });
    }
}
