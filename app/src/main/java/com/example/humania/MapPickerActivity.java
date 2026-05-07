package com.example.humania;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapPickerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView tvSelectedAddress;
    private LatLng currentLatLng;
    private String selectedAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_picker);

        tvSelectedAddress = findViewById(R.id.tvSelectedAddress);
        Button btnConfirmLocation = findViewById(R.id.btnConfirmLocation);
        
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        btnConfirmLocation.setOnClickListener(v -> {
            if (currentLatLng != null) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("address", selectedAddress);
                resultIntent.putExtra("latitude", currentLatLng.latitude);
                resultIntent.putExtra("longitude", currentLatLng.longitude);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Please select a location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Default location: Davao City or Digos City
        LatLng defaultLocation = new LatLng(6.7496, 125.3551); // Digos City
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f));

        mMap.setOnCameraIdleListener(() -> {
            currentLatLng = mMap.getCameraPosition().target;
            updateAddress(currentLatLng);
        });
    }

    private void updateAddress(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                selectedAddress = address.getAddressLine(0);
                tvSelectedAddress.setText(selectedAddress);
            } else {
                selectedAddress = "Unknown Location";
                tvSelectedAddress.setText(selectedAddress);
            }
        } catch (IOException e) {
            selectedAddress = "Error getting address";
            tvSelectedAddress.setText(selectedAddress);
            e.printStackTrace();
        }
    }
}
