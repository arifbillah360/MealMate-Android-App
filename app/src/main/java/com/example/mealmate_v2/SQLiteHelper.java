package com.example.mealmate_v2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MealMate.db";
    private static final int DATABASE_VERSION = 3; // bumped version for schema change

    // Users table
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_NAME = "name";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_PHONE = "phone";
    public static final String COL_USER_PASSWORD = "password";

    // Donations table
    public static final String TABLE_DONATIONS = "donations";
    public static final String COL_DONATION_ID = "id";
    public static final String COL_DONOR_NAME = "donor_name";
    public static final String COL_FOOD_ITEM = "food_item";
    public static final String COL_DONOR_PHONE = "donor_phone";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_LAT = "latitude";   // New column
    public static final String COL_LNG = "longitude";  // New column

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users table
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_NAME + " TEXT, " +
                COL_USER_EMAIL + " TEXT UNIQUE, " +
                COL_USER_PHONE + " TEXT, " +
                COL_USER_PASSWORD + " TEXT)");

        // Donations table with lat/lng
        db.execSQL("CREATE TABLE " + TABLE_DONATIONS + " (" +
                COL_DONATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DONOR_NAME + " TEXT, " +
                COL_FOOD_ITEM + " TEXT, " +
                COL_DONOR_PHONE + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_LAT + " REAL, " +
                COL_LNG + " REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONATIONS);
        onCreate(db);
    }

    // -------- Users methods --------
    public boolean insertUser(String name, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, name);
        values.put(COL_USER_EMAIL, email);
        values.put(COL_USER_PHONE, phone);
        values.put(COL_USER_PASSWORD, password);

        long result = -1;
        try {
            result = db.insertOrThrow(TABLE_USERS, null, values);
        } catch (Exception e) {
            return false; // duplicate email or error
        }
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                        " WHERE " + COL_USER_EMAIL + "=? AND " + COL_USER_PASSWORD + "=?",
                new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // -------- Donations methods --------
    public boolean insertDonation(String donorName, String foodItem, String phone, String description, double lat, double lng) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DONOR_NAME, donorName);
        values.put(COL_FOOD_ITEM, foodItem);
        values.put(COL_DONOR_PHONE, phone);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_LAT, lat);   // save latitude
        values.put(COL_LNG, lng);   // save longitude

        long result = db.insert(TABLE_DONATIONS, null, values);
        return result != -1;
    }

    public Cursor getAllDonations() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_DONATIONS, null);
    }
}
