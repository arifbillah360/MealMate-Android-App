package com.example.mealmate_v2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class Receive extends AppCompatActivity implements OnMapReadyCallback {

    private EditText receiverName, description;
    private Button submit;
    private SQLiteHelper dbHelper;

    private GoogleMap mMap;
    private LatLng selectedLocation = null;
    private Marker selectedMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        // Initialize views
        receiverName = findViewById(R.id.receivername);
        description = findViewById(R.id.description);
        submit = findViewById(R.id.submit);

        // Initialize database helper
        dbHelper = new SQLiteHelper(this);

        // Initialize map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapContainer);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Submit button listener
        submit.setOnClickListener(v -> {
            String name = receiverName.getText().toString().trim();
            String desc = description.getText().toString().trim();

            if (name.isEmpty() || desc.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedLocation == null) {
                Toast.makeText(this, "Please select a location on the map", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert donation into database
            boolean inserted = dbHelper.insertDonation(
                    name,
                    "Received Food",
                    null, // No phone for receiver
                    desc,
                    selectedLocation.latitude,
                    selectedLocation.longitude
            );

            if (inserted) {
                Toast.makeText(this, "Submitted successfully", Toast.LENGTH_SHORT).show();
                receiverName.setText("");
                description.setText("");
                selectedLocation = null;

                // Remove marker after submission
                if (selectedMarker != null) {
                    selectedMarker.remove();
                    selectedMarker = null;
                }
            } else {
                Toast.makeText(this, "Failed to submit", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Default location: Dhaka
        LatLng dhaka = new LatLng(23.8103, 90.4125);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 12));

        // Handle map clicks
        mMap.setOnMapClickListener(latLng -> {
            selectedLocation = latLng;

            // Remove old marker
            if (selectedMarker != null) {
                selectedMarker.remove();
            }

            // Add new marker
            selectedMarker = mMap.addMarker(new com.google.android.gms.maps.model.MarkerOptions()
                    .position(latLng)
                    .title("Selected Location")
                    .snippet("Tap Submit to save")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            selectedMarker.showInfoWindow();
        });
    }
}
