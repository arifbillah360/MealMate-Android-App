package com.example.mealmate_v2;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Context;
import android.view.ViewGroup;

public class History extends AppCompatActivity {

    LinearLayout showData;
    SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        showData = findViewById(R.id.showdata);
        dbHelper = new SQLiteHelper(this);
        Context context = this;

        // Fetch all donations from database
        Cursor cursor = dbHelper.getAllDonations();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No history found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                // Columns from your donations table
                String donorName = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_DONOR_NAME));
                String foodItem = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_FOOD_ITEM));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_DESCRIPTION));

                // Combine info for display
                String dataTextString = donorName + " - " + foodItem + "\n" + description;

                // Create CardView dynamically
                CardView cardView = new CardView(context);
                LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                cardParams.setMargins(0, 0, 0, 10);
                cardView.setLayoutParams(cardParams);
                cardView.setRadius(12f);
                cardView.setCardElevation(5f);
                cardView.setCardBackgroundColor(getResources().getColor(R.color.gray));
                cardView.setClickable(true);

                TextView dataText = new TextView(context);
                dataText.setText(dataTextString);
                dataText.setTextColor(getResources().getColor(R.color.white));
                dataText.setTextSize(16);
                dataText.setPadding(20, 20, 20, 20);

                cardView.addView(dataText);
                showData.addView(cardView);
            }
        }
        cursor.close();

        // Delete button
        CardView delete = findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            dbHelper.getWritableDatabase().delete(SQLiteHelper.TABLE_DONATIONS, null, null);
            showData.removeAllViews(); // remove all cards from UI
            Toast.makeText(History.this, "History deleted", Toast.LENGTH_SHORT).show();
        });
    }
}
