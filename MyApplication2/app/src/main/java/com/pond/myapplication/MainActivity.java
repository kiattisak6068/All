package com.pond.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    static Logger logger = Logger.getLogger("MainActivity");
    EditText emailet, passet;
    TextView show;
    Button chk,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailet=(EditText)findViewById(R.id.login);
        passet=(EditText)findViewById(R.id.pass);
        show=(TextView)findViewById(R.id.showtv);
        chk=(Button)findViewById(R.id.btlogin);
        register=(Button)findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next=new Intent(getApplicationContext(),Register.class);
                startActivity(next);
            }
        });
    }

    private boolean checkNetwork() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void loginClick(View view) {
        if (!checkNetwork()) {
            logger.info("not network connect...");
            return;
        }
        if(emailet.getText().toString().isEmpty() || passet.getText().toString().isEmpty()){
            show.setText("Email or Password Invalid");
            return;
        }
        String xx = emailet.getText().toString() + "," + passet.getText().toString();
        new ChkLongin().execute(new String(xx));


    }
    private class ChkLongin extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            // assuming one id supplied.
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("http://10.80.4.140:9000/getname/"+strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //connection.setRequestMethod("GET");
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ( (line = br.readLine()) != null) {
                        result.append(line);
                    }
                    return new String(result.toString());
                } finally {
                    connection.disconnect();
                }
            } catch (Exception e) {
                logger.info(e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(final String acc) {

            if(acc.equals("user")){
                Intent next = new Intent(getApplicationContext(),management.class);
                next.putExtra("email",emailet.getText().toString());
                startActivity(next);
            }else if(acc.equals("admin")){
                Intent next = new Intent(getApplicationContext(),Main2Activity.class);
                next.putExtra("email",emailet.getText().toString());
                startActivity(next);
            }else {
                show.setText(acc);
            }
        }
    }

}
