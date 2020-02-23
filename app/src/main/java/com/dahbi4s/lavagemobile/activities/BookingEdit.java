package com.dahbi4s.lavagemobile.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dahbi4s.lavagemobile.Adapter.BookingListeAdapter;
import com.dahbi4s.lavagemobile.R;
import com.dahbi4s.lavagemobile.models.Booking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.places.picker.PlacePicker;
import com.mapbox.mapboxsdk.plugins.places.picker.model.PlacePickerOptions;

import java.util.HashMap;

public class BookingEdit extends AppCompatActivity {
    String idBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_edit);

        Mapbox.getInstance(this,"pk.eyJ1IjoiaG5hZGEiLCJhIjoiY2s1anV5a3J5MDZ2dzNscWlmMXYzaGw3ZSJ9.3ZUT88xIW9LBHBY7rF8mQA");

        Intent intent = getIntent();
        idBook = intent.getStringExtra("idBook");

        getBooking();



        (findViewById(R.id.valider)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBooking(Booking.State.RUNNING);
                comfirmBookingTimeTaken();
            }
        });
        (findViewById(R.id.terminer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBooking(Booking.State.DONE);
            }
        });
        (findViewById(R.id.map)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(BookingEdit.this,ShowLocation.class);
                //startActivity(intent);

                //String uri = "http://maps.google.com/maps?daddr=" + 40.7544 + "," + -73.9862 + " (" + "Where the party is at" + ")";
                String uri = "http://maps.google.com/maps?q=loc:" + BookingStep.order.getAddress().getCoordinates().get(1) + "," + BookingStep.order.getAddress().getCoordinates().get(0)  + " (" + BookingStep.order.getAddress().getType() + ")"; ;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
                //goToPickerActivity();
            }
        });


    }

    private void updateBooking(Booking.State state){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Wait ...");
        pd.show();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("booking");

            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("BookId", BookingStep.order.getBookId());
            hashMap.put("UserId", BookingStep.order.getPhone());
            hashMap.put("Phone", BookingStep.order.getPhone());
            hashMap.put("Name", BookingStep.order.getName());
            hashMap.put("Day", BookingStep.order.getDay());
            hashMap.put("Time", BookingStep.order.getTime());
            hashMap.put("Price", BookingStep.order.getPrice());
            hashMap.put("LavageType", BookingStep.order.getLavageType());
            hashMap.put("CarType", BookingStep.order.getCarType());
            hashMap.put("Address", BookingStep.order.getAddress());
            hashMap.put("Status", state);
            reference.child(BookingStep.order.getBookId()+"").setValue(hashMap);
            pd.dismiss();

            finish();


    }

    private void getBooking(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("booking").child(idBook);
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                    BookingStep.order = dataSnapshot.getValue(Booking.class);
                    System.out.print(dataSnapshot.getValue(Booking.class));
                    if(BookingStep.order.getStatus()== Booking.State.WAITING){
                        (findViewById(R.id.terminer)).setVisibility(View.INVISIBLE);
                    }else if (BookingStep.order.getStatus()== Booking.State.RUNNING){
                        (findViewById(R.id.valider)).setVisibility(View.INVISIBLE);
                    }else{
                        (findViewById(R.id.valider)).setVisibility(View.INVISIBLE);
                        (findViewById(R.id.terminer)).setVisibility(View.INVISIBLE);

                    }


                ((TextView)findViewById(R.id.idBook)).setText((BookingStep.order.getBookId() +""));
                ((TextView)findViewById(R.id.price)).setText((BookingStep.order.getPrice() +" DH"));
                ((TextView)findViewById(R.id.nom)).setText((BookingStep.order.getName() +""));
                ((TextView)findViewById(R.id.tele)).setText((BookingStep.order.getPhone() +""));
                if(BookingStep.order.getAddress()!=null)
                    ((TextView)findViewById(R.id.address)).setText((BookingStep.order.getAddress().getType() +""));
                ((TextView)findViewById(R.id.vihecule)).setText((BookingStep.order.getCarType() +""));
                ((TextView)findViewById(R.id.pack)).setText((BookingStep.order.getLavageType() +""));
                ((TextView)findViewById(R.id.date)).setText((BookingStep.order.getDay() +"  :  " +BookingStep.order.getTime() ));


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void comfirmBookingTimeTaken(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Running ...");
        pd.show();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("timetaken");

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("day", BookingStep.order.getDay());
        hashMap.put("time", BookingStep.order.getTime());



        reference.child(BookingStep.order.getBookId()+"").setValue(hashMap);
        pd.dismiss();




        finish();


    }

    private void goToPickerActivity() {
        startActivityForResult(
                new PlacePicker.IntentBuilder()
                        .accessToken("pk.eyJ1IjoiaG5hZGEiLCJhIjoiY2s1anV5a3J5MDZ2dzNscWlmMXYzaGw3ZSJ9.3ZUT88xIW9LBHBY7rF8mQA")
                        .placeOptions(PlacePickerOptions.builder()
                                .statingCameraPosition(new CameraPosition.Builder()
                                        .target(new LatLng(BookingStep.order.getAddress().getCoordinates().get(1), BookingStep.order.getAddress().getCoordinates().get(0))).zoom(12).build())
                                .build()
                        )
                        .build(this), 15);
    }
}
