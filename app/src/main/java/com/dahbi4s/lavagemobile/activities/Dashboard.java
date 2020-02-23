package com.dahbi4s.lavagemobile.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.dahbi4s.lavagemobile.R;
import com.dahbi4s.lavagemobile.models.Booking;

import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        (findViewById(R.id.client)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Dashboard.this, BookingStep.class);
                startActivity(intent);
            }
        });

        (findViewById(R.id.admin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, BookingListe.class);
                startActivity(intent);
            }
        });
    }









}
