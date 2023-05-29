package com.example.kursovayaend;

import com.example.kursovayaend.Model.Users;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button RegistrBtn;
    private EditText usernameInput, phoneInput, passwordInput;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegistrBtn = (Button) findViewById(R.id.RegisterButton);
        usernameInput = (EditText) findViewById(R.id.RegisterUsernameInput);
        phoneInput = (EditText) findViewById(R.id.RegisterPhoneInput);
        passwordInput = (EditText) findViewById(R.id.RegisterPasswordInput);
        loadingBar = new ProgressDialog(this);

        RegistrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String username = usernameInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Введите Имя", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Создание аккаунта");
            loadingBar.setMessage("Пожалуйста, подождите...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            // Создаем экземпляр класса Users и устанавливаем значения полей
            Users user = new Users(username, phone, password, "");

            // Вызываем метод для создания аккаунта и передаем экземпляр класса Users
            ValidatePhone(user);
        }
    }

    private void ValidatePhone(Users user) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(user.getPhone()).exists())) {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("name", user.getName());
                    userDataMap.put("phone", user.getPhone());
                    userDataMap.put("password", user.getPassword());

                    RootRef.child("Users").child(user.getPhone()).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                updateUserData(user); // Обновляем данные пользователя

                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Регистрация прошла успешно!\nМожете войти", Toast.LENGTH_SHORT).show();

                                Intent intentAuth = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intentAuth);
                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Ошибка при регистрации", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Номер " + user.getPhone() + " уже зарегистрирован", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Intent intentAuth = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intentAuth);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateUserData(Users user) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getPhone());

        userRef.child("name").setValue(user.getName());
        userRef.child("password").setValue(user.getPassword());
        userRef.child("number").setValue(user.getNumber());
    }
}