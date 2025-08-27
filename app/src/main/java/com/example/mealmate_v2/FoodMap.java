package com.example.mealmate_v2;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FoodMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_map);

        dbHelper = new SQLiteHelper(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Fetch all donations from database
        Cursor cursor = dbHelper.getAllDonations();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String donor = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_DONOR_NAME));
                String food = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_FOOD_ITEM));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_DESCRIPTION));
                double lat = cursor.getDouble(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_LAT));
                double lng = cursor.getDouble(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_LNG));

                LatLng loc = new LatLng(lat, lng);

                // Check if this is a receiver record (food_item = "Received Food")
                boolean isReceiver = food.equalsIgnoreCase("Received Food");

                mMap.addMarker(new MarkerOptions()
                        .position(loc)
                        .title(donor + " - " + food)
                        .snippet(desc)
                        .icon(isReceiver ? BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                                : BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
            cursor.close();
        }

        // Move camera to Dhaka as default
        LatLng dhaka = new LatLng(23.8103, 90.4125);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 12));
    }
}
