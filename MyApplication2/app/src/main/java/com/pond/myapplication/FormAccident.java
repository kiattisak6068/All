package com.pond.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FormAccident extends AppCompatActivity implements LocationListener {
    EditText email, des,lat,lng;
    TextView show;
    Button submit;
    Location currentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_accident);

        email=(EditText)findViewById(R.id.email);
        des=(EditText)findViewById(R.id.des);
        lat =(EditText)findViewById(R.id.lat);
        lng=(EditText)findViewById(R.id.lng);
        show=(TextView)findViewById(R.id.showtv);
        submit=(Button)findViewById(R.id.submit);

        email.setText(getIntent().getStringExtra("email"));

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 2, this);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 2, this);



    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        if (currentLocation != null) {
            try {
                lat.setText("" + currentLocation.getLatitude());
                lng.setText("" + currentLocation.getLongitude());
            } catch (Exception ex) {

            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    private boolean checkNetwork() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    public void submitClick(View view) {
        if (!checkNetwork()) {
            //
            return;
        }
        if(email.getText().toString().isEmpty() || des.getText().toString().isEmpty() || lat.getText().toString().isEmpty() || lng.getText().toString().isEmpty()){
            show.setText("Missing data");
            return;
        }
            String acc = email.getText().toString()+","+des.getText().toString()+","+lat.getText().toString()+","+lng.getText().toString();
            new ReportAccident().execute(new String(acc));

    }
    private class ReportAccident extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("http://10.80.4.140:9000/report/"+strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ( (line = br.readLine()) != null) {
                        result.append(line+"\n");
                    }
                    return new String(result.toString());

                } finally {
                    connection.disconnect();
                }
            } catch (Exception e) {
                //logger.info(e.toString());
            }
            return null;
        }
        @Override
        protected void onPostExecute(final String acc) {
                show.setText(acc);

        }
    }

}
