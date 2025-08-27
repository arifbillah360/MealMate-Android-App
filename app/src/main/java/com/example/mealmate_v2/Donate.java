package com.example.mealmate_v2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Donate extends AppCompatActivity {

    EditText donorName, foodItem, phone, description;
    Button submit;
    SQLiteHelper dbHelper;

    private GoogleMap mMap;
    private double selectedLat = 0.0, selectedLng = 0.0;
    private static final int LOCATION_PERMISSION_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        donorName = findViewById(R.id.donorname);
        foodItem = findViewById(R.id.fooditem);
        phone = findViewById(R.id.phone);
        description = findViewById(R.id.description);
        submit = findViewById(R.id.submit);
        dbHelper = new SQLiteHelper(this);

        // Map initialization
        FrameLayout mapContainer = findViewById(R.id.mapContainer);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapContainer);

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mapContainer, mapFragment)
                    .commit();
        }

        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(true);

            // Enable user location if permission granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);

                // Move camera to user’s current location
                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        LatLng userLoc = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 15));
                    }
                });
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST);
            }

            // Map click listener for selecting donation location
            mMap.setOnMapClickListener(latLng -> {
                mMap.clear(); // remove previous marker
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Donation Location"));
                selectedLat = latLng.latitude;
                selectedLng = latLng.longitude;
            });
        });

        // Submit donation
        submit.setOnClickListener(v -> {
            String name = donorName.getText().toString().trim();
            String item = foodItem.getText().toString().trim();
            String ph = phone.getText().toString().trim();
            String desc = description.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                donorName.setError("Donor Name is required");
                return;
            }
            if (TextUtils.isEmpty(item)) {
                foodItem.setError("Food Item is required");
                return;
            }
            if (TextUtils.isEmpty(ph)) {
                phone.setError("Phone is required");
                return;
            }
            if (TextUtils.isEmpty(desc)) {
                description.setError("Description is required");
                return;
            }
            if (selectedLat == 0.0 && selectedLng == 0.0) {
                Toast.makeText(Donate.this, "Please select a location on the map", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save donation in SQLite with location
            boolean inserted = dbHelper.insertDonation(name, item, ph, desc, selectedLat, selectedLng);
            if (inserted) {
                Toast.makeText(Donate.this, "Donation submitted!", Toast.LENGTH_SHORT).show();
                donorName.setText("");
                foodItem.setText("");
                phone.setText("");
                description.setText("");
                mMap.clear();
                selectedLat = 0.0;
                selectedLng = 0.0;
            } else {
                Toast.makeText(Donate.this, "Error submitting donation", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
