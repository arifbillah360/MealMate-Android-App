package com.example.mealmate_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class LandingPage extends AppCompatActivity {

    CardView cardLogin, cardRegister, cardAboutus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);

        cardLogin = findViewById(R.id.cardLogin);
        cardRegister = findViewById(R.id.cardRegister);
        cardAboutus = findViewById(R.id.cardAboutus);

        cardLogin.setOnClickListener(v -> startActivity(new Intent(LandingPage.this, Logup.class)));
        cardRegister.setOnClickListener(v -> startActivity(new Intent(LandingPage.this, Signup.class)));

        cardAboutus.setOnClickListener(v -> startActivity(new Intent(LandingPage.this, About.class)));
    }
}
