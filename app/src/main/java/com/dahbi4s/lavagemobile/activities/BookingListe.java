package com.dahbi4s.lavagemobile.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import android.widget.ProgressBar;


import com.dahbi4s.lavagemobile.Adapter.BookingListeAdapterR;
import com.dahbi4s.lavagemobile.R;
import com.dahbi4s.lavagemobile.models.Booking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookingListe extends AppCompatActivity {

    public ArrayList<Booking> bookingList;
    public RecyclerView recyclerView;
    public BookingListeAdapterR bookingListAdapter;
    public ProgressBar progress_circular;
    private BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_liste);

        progress_circular = findViewById(R.id.progress_circular);
        progress_circular.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recyclerView);


        bookingList = new ArrayList<>();
        getBookingliste(Booking.State.WAITING);


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.running:
                        getBookingliste(Booking.State.RUNNING);
                        return true;
                    case R.id.waiting:
                        getBookingliste(Booking.State.WAITING);
                        return true;
                    case R.id.done:
                        getBookingliste(Booking.State.DONE);
                        return true;
                }
                return false;
            }
        });


    }

    private void getBookingliste(final Booking.State statue){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("booking");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookingList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Booking booking = snapshot.getValue(Booking.class);
                    if(statue.equals(booking.getStatus())){
                        bookingList.add(booking);
                    }

                }
                recyclerView.setHasFixedSize(true);
                GridLayoutManager gridmanager = new GridLayoutManager(BookingListe.this, 1);
                gridmanager.setOrientation(GridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(gridmanager);
                bookingListAdapter=new BookingListeAdapterR(bookingList,BookingListe.this);
                recyclerView.setAdapter(bookingListAdapter);
                progress_circular.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
