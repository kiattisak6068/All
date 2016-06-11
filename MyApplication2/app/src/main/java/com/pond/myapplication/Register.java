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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Register extends AppCompatActivity {
    EditText email, fname,lname,pwd,repwd;
    TextView show;
    Button submit,login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email=(EditText)findViewById(R.id.email);
        fname=(EditText)findViewById(R.id.fname);
        lname=(EditText)findViewById(R.id.lname);
        pwd=(EditText)findViewById(R.id.pwd);
        repwd=(EditText)findViewById(R.id.repwd);

        show=(TextView)findViewById(R.id.showtv);
        submit=(Button)findViewById(R.id.submit);
        login=(Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),MainActivity.class);
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
    public void submitClick(View view) {
        if (!checkNetwork()) {
            //
            return;
        }
        if (fname.getText().toString().isEmpty() || lname.getText().toString().isEmpty()){
            show.setText("First name or Last name Invalid");
            return;
        }
        if(pwd.getText().toString().isEmpty() || pwd.getText().toString().isEmpty()){
            show.setText("Password Invalid");
            return;
        }
        String email2 = email.getText().toString().trim();
        if (email2.matches(emailPattern)) {
            String acc = fname.getText().toString() + "," + lname.getText().toString() + "," + email.getText().toString() + "," + pwd.getText().toString() + "," + repwd.getText().toString();
            new ReportAccident().execute(new String(acc));
        }else {
           show.setText("Invalid email");
        }
    }
    private class ReportAccident extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("http://10.80.4.140:9000/setname/"+strings[0]);
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
        protected void onPostExecute(final String mas) {
            show.setText(mas);

        }
    }

}
