package com.maik.org.lab_04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MapsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}

        View view = new View(this);

        buscarInformacaoGPS(view);
    }

    public void buscarInformacaoGPS(View view) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},  1);
            ActivityCompat.requestPermissions(MapsActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},  1);
            ActivityCompat.requestPermissions(MapsActivity.this, new String[] {Manifest.permission.ACCESS_NETWORK_STATE},  1);

            return;
        }


            LocationManager mLocaManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            LocationListener mLocListener = new LocalizacaoListener();

            Location locationGPS = mLocaManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


            double lati = -3.10719;
            double longi = -60.0261;
//            double lati = locationGPS.getLatitude();
//            double longi = locationGPS.getLongitude();

            LocalizacaoListener.latitude = lati;
            LocalizacaoListener.longitude = longi;


            if(mLocaManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Log.d("TAG", "GPS is on");
                String txt = "Latitude.: " + LocalizacaoListener.latitude + "\n" +
                        "Longitude: " + LocalizacaoListener.longitude + "\n";
                Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS DESABILITADO", Toast.LENGTH_SHORT).show();
            }
        this.mostrarGoogleMaps(LocalizacaoListener.latitude, LocalizacaoListener.longitude);
    }

    public void mostrarGoogleMaps(double latitude, double longitude) {
        WebView wv = findViewById(R.id.webviewMaps);
        wv.getSettings().setJavaScriptEnabled(true);
//        wv.loadUrl("https://www.google.com");
        wv.loadUrl("https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude);

    }
}