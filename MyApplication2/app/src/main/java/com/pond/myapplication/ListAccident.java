package com.pond.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class ListAccident extends ListActivity  {
    public final Context context=this;
    public ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_list_accident);
        if (!checkNetwork()) {
          //  logger.info("not network connect...");
            return;
        }

        new listname().execute();
    }
    //Oncreate
    private boolean checkNetwork() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }


    private class listname extends AsyncTask<Void, Integer, JSONArray> {
        @Override
        protected JSONArray doInBackground(Void... voids) {
            // assuming one id supplied.
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("http://10.80.4.140:9000/accident");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //connection.setRequestMethod("GET");
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ( (line = br.readLine()) != null) {
                        result.append(line+"\n");
                    }
                    return new JSONArray(result.toString());
                } finally {
                    connection.disconnect();
                }
            } catch (Exception e) {
                //logger.info(e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(final JSONArray acc) {
            try {
                //list
                int size=acc.length();
                String[] name =new String[size];
                final String[] acclat =new String[size];
                final String[] acclng =new String[size];
                final int[] accId =new int[size];

                for (int i = 0; i < size; ++i) {
                    JSONObject data = acc.getJSONObject(i);
                    accId[i]=data.getInt("ID");
                    acclat[i] = data.getString("lat");
                    acclng[i] = data.getString("lng");
                    name[i]=data.getString("description")+"\n"+data.getString("time");
                }


                ArrayList<String> listname =new ArrayList<String>(Arrays.asList(name));
                adapter=new ArrayAdapter<String>(ListAccident.this,
                        android.R.layout.simple_list_item_1, listname);
                setListAdapter(adapter);
                AdapterView.OnItemClickListener clickListener=new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String lat=" ";
                        String lng=" ";
                        for (int i = 0; i < acc.length(); ++i) {
                            if (i==position){
                                lat=acclat[i];
                                lng=acclng[i];
                            }
                        }

                        Intent map = new Intent(getApplicationContext(),MapsActivity.class);
                        map.putExtra("lat",lat);
                        map.putExtra("lng",lng);
                        startActivity(map);
                    }
                };
                getListView().setOnItemClickListener(clickListener);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
