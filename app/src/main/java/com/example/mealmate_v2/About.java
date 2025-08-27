package com.example.mealmate_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class About extends AppCompatActivity {

    CardView instagram, facebook, twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        instagram = findViewById(R.id.instagram);
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);

        instagram.setOnClickListener(v -> {
            Intent myWebLink = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"));
            startActivity(myWebLink);
        });

        facebook.setOnClickListener(v -> {
            Intent myWebLink = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
            startActivity(myWebLink);
        });

        twitter.setOnClickListener(v -> {
            Intent myWebLink = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com"));
            startActivity(myWebLink);
        });
    }
}
