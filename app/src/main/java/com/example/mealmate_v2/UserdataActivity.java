package com.example.mealmate_v2;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class UserdataActivity extends AppCompatActivity {

    RecyclerView recView;
    HistoryAdapter adapter;
    ArrayList<HistoryItem> itemList;
    SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdata);

        recView = findViewById(R.id.rec_view);
        recView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new SQLiteHelper(this);
        itemList = new ArrayList<>();


        Cursor cursor = dbHelper.getAllDonations();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String donorName = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_DONOR_NAME));
                String foodItem = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_FOOD_ITEM));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_DESCRIPTION));

                // Add to list
                itemList.add(new HistoryItem(donorName, foodItem, description));
            }
            cursor.close();
        }

        adapter = new HistoryAdapter(this, itemList);
        recView.setAdapter(adapter);
    }
}
