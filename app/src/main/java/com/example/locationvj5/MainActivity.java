package com.example.locationvj5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Deklaracija varijabli
    Button button;
    TextView textLatitude, textLongitude, textDrzava, textGrad, textAdresa;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicijalizacija varijebli
        button = findViewById(R.id.button1);
        textLatitude = findViewById(R.id.text_view1);
        textLongitude = findViewById(R.id.text_view2);
        textDrzava = findViewById(R.id.text_view3);
        textGrad = findViewById(R.id.text_view4);
        textAdresa = findViewById(R.id.text_view5);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check premission
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //When premission granted
                    getLocation();
                } else {
                    //When premission denied
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize location
                Location location = task.getResult();
                if (location != null) {
                    try {
                        //Initialize geoCoder
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        //Initialize address list
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        //Set laditude textView
                        textLatitude.setText(Html.fromHtml("<font color='15ee00'><b>Latitude :</b><br></font>" + addresses.get(0).getLatitude()));
                        //Set longitude
                        textLongitude.setText(Html.fromHtml("<font color='15ee00'><b>Longitude :</b><br></font>" + addresses.get(0).getLongitude()));
                        //Set country name
                        textDrzava.setText(Html.fromHtml("<font color='15ee00'><b>Drzava :</b><br></font>" + addresses.get(0).getCountryName()));
                        //Set locality
                        textGrad.setText(Html.fromHtml("<font color='15ee00'><b>Grad :</b><br></font>" + addresses.get(0).getLocality()));
                        //Set address
                        textAdresa.setText(Html.fromHtml("<font color='15ee00'><b>Adresa :</b><br></font>" + addresses.get(0).getAddressLine(0)));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });


        }
    }