package com.example.mealmate_v2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Contact extends AppCompatActivity {

    EditText name, email, message;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        message = findViewById(R.id.message);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(v -> {
            String n = name.getText().toString().trim();
            String e = email.getText().toString().trim();
            String m = message.getText().toString().trim();

            if(n.isEmpty() || e.isEmpty() || m.isEmpty()){
                Toast.makeText(Contact.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                sendEmail(n, e, m);
            }
        });
    }

    private void sendEmail(String name, String email, String message) {
        String subject = "Contact Form Message from " + name;
        String body = "Name: " + name + "\nEmail: " + email + "\n\nMessage:\n" + message;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:221002136@student.green.edu.bd")); // recipient
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(Intent.createChooser(intent, "Send email via"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }

        // Clear fields after sending
        this.name.setText("");
        this.email.setText("");
        this.message.setText("");
    }
}
