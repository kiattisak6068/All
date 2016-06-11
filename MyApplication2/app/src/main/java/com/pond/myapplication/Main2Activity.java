package com.pond.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    Button report,accidents,logout,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        report = (Button)findViewById(R.id.report);
        accidents = (Button)findViewById(R.id.accident);
        logout = (Button)findViewById(R.id.logout);
        user = (Button)findViewById(R.id.user);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user = new Intent(getApplicationContext(),Users.class);
                startActivity(user);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ne = new Intent(getApplicationContext(),FormAccident.class);
                ne.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(ne);
            }
        });
        accidents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ac = new Intent(getApplicationContext(),Accident.class);
                startActivity(ac);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent out = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(out);
            }
        });
    }
}
