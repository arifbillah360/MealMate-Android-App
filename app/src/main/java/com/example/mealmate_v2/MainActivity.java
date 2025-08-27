package com.example.mealmate_v2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    CardView donate, receive, logout, foodmap, about, contact, mypin, history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        donate = findViewById(R.id.cardDonate);
        receive = findViewById(R.id.cardReceive);
        logout = findViewById(R.id.cardLogout);
        foodmap = findViewById(R.id.cardFoodmap);
        mypin = findViewById(R.id.cardMyPin);
        history = findViewById(R.id.cardHistory);
        about = findViewById(R.id.cardAboutus);
        contact = findViewById(R.id.cardContact);

        // -------- SESSION CHECK ----------
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, Logup.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        // --------- CARD CLICK EVENTS ---------
        donate.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Donate.class)));

        receive.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Receive.class)));

        foodmap.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), FoodMap.class)));

        about.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), About.class)));

        mypin.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MyPin.class)));

        history.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserdataActivity.class)));

        contact.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Contact.class)));

        // --------- LOGOUT ---------
        logout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, Logup.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}
