package com.example.kursovayaend;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kursovayaend.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Button LoginButton;
    private EditText phoneInput, passwordInput;
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = findViewById(R.id.LoginButton);
        phoneInput = findViewById(R.id.LoginPhoneInput);
        passwordInput = findViewById(R.id.LoginPasswordInput);
        loadingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
    }

    private void LoginUser() {
        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Вход в приложение");
            loadingBar.setMessage("Пожалуйста, подождите...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateUser(phone, password);
            loadingBar.dismiss();
        }
    }

    private void ValidateUser(String phone, String password) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(parentDbName).child(phone);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Users userData = dataSnapshot.getValue(Users.class);
                    if (userData != null) {
                        String userPassword = userData.getPassword();
                        if (userPassword != null && userPassword.equals(password)) {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Успешный вход!", Toast.LENGTH_SHORT).show();
                            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Неверный пароль!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Аккаунт с номером " + phone + " не существует", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingBar.dismiss();
                Toast.makeText(LoginActivity.this, "Ошибка: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}