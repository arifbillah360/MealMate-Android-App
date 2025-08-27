package com.example.mealmate_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;

public class Logup extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mRegister; // for signup page link
    SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.login);
        mRegister = findViewById(R.id.register);

        dbHelper = new SQLiteHelper(this);

        // LOGIN
        mLoginBtn.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required.");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is Required.");
                return;
            }

            if (dbHelper.checkUser(email, password)) {
                Toast.makeText(Logup.this, "Login Successful", Toast.LENGTH_SHORT).show();

                // -------- SESSION SAVE --------
                SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                // Go to MainActivity
                startActivity(new Intent(Logup.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(Logup.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            }
        });

        // REGISTER (signup page)
        mRegister.setOnClickListener(v -> {
            Intent intent = new Intent(Logup.this, Signup.class); // <- your signup activity
            startActivity(intent);
        });
    }
}
