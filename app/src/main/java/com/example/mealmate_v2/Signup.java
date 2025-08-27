package com.example.mealmate_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

public class Signup extends AppCompatActivity {

    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFullName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mRegisterBtn = findViewById(R.id.register);
        mLoginBtn = findViewById(R.id.login);

        dbHelper = new SQLiteHelper(this);

        // REGISTER
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mFullName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    mFullName.setError("Name is Required.");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                boolean inserted = dbHelper.insertUser(name, email, phone, password);
                if (inserted) {
                    Toast.makeText(Signup.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Signup.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(Signup.this, "User Already Exists!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Go to Login
        mLoginBtn.setOnClickListener(v -> {
            startActivity(new Intent(Signup.this, Logup.class));
        });
    }
}
