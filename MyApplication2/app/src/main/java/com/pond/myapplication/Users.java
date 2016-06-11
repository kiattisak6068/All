package com.pond.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class Users extends ListActivity {
    public final Context context=this;
    public ArrayAdapter<String> adapter;
    String lat;
    String lng;

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
                URL url = new URL("http://10.80.4.140:9000/getuser");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //connection.setRequestMethod("GET");
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        result.append(line + "\n");
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
                int size = acc.length();
                String[] name = new String[size];
                final int[] accId =new int[size];

                for (int i = 0; i < size; ++i) {
                    JSONObject data = acc.getJSONObject(i);
                    name[i] = data.getString("fname")+"\n"+data.getString("email");
                    accId[i] = data.getInt("ID");
                }


                ArrayList<String> listname = new ArrayList<String>(Arrays.asList(name));
                adapter = new ArrayAdapter<String>(Users.this,
                        android.R.layout.simple_list_item_1, listname);
                setListAdapter(adapter);

                AdapterView.OnItemLongClickListener clicklong = new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                        final String selecteditem = ((TextView)view).getText().toString();

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Do you wan to remove" + selecteditem);

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.remove(selecteditem);
                                adapter.notifyDataSetChanged();
                                //delete
                                if (!checkNetwork()) {
                                    //logger.info("not network connect...");
                                    return;
                                }
                                int xx=0;
                                for (int i = 0; i < acc.length(); ++i) {
                                    if (i==position){
                                        xx=accId[i];
                                    }
                                }
                                new Deleteplace().execute(xx);
                            }
                            class Deleteplace extends AsyncTask<Integer, Integer, Void> {
                                @Override
                                protected Void doInBackground(Integer... ids) {
                                    try {
                                        URL url = new URL("http://10.80.4.140:9000/deleteuser/"+ids[0]);
                                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                        //connection.setRequestMethod("GET");
                                        try {
                                            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                            String line;
                                            while ( (line = br.readLine()) != null) {

                                            }
                                        } finally {
                                            connection.disconnect();
                                        }
                                    } catch (Exception e) {
                                        //logger.info(e.toString());
                                    }
                                    return null;
                                }
                            }


                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                        return true;
                    }
                };

                getListView().setOnItemLongClickListener(clicklong);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
