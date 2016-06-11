package com.pond.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class management extends AppCompatActivity {
    Button report,accidents,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

       report = (Button)findViewById(R.id.report);
        accidents = (Button)findViewById(R.id.accident);
        logout = (Button)findViewById(R.id.logout);

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
                Intent ac = new Intent(getApplicationContext(),ListAccident.class);
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
