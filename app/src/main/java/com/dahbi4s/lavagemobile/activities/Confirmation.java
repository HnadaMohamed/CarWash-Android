package com.dahbi4s.lavagemobile.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dahbi4s.lavagemobile.R;
import com.dahbi4s.lavagemobile.models.Booking;
import com.dahbi4s.lavagemobile.models.Tools;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Confirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);


        BookingStep.order.setBookId(
                 BookingStep.order.getDay().replace("2020","").replace("/","")+
                 BookingStep.order.getTime().replace(":","").replace("0","")+ "-"+
                 BookingStep.order.getPhone().substring(BookingStep.order.getPhone().length()-2)


                         );

        ((TextView)findViewById(R.id.idBook)).setText((BookingStep.order.getBookId() +""));
        ((TextView)findViewById(R.id.price)).setText((BookingStep.order.getPrice() +" DH"));
        ((TextView)findViewById(R.id.nom)).setText((BookingStep.order.getName() +""));
        ((TextView)findViewById(R.id.tele)).setText((BookingStep.order.getPhone() +""));
        if(BookingStep.order.getAddress()!=null)
            ((TextView)findViewById(R.id.address)).setText((BookingStep.order.getAddress().getType() +""));
        ((TextView)findViewById(R.id.vihecule)).setText((BookingStep.order.getCarType() +""));
        ((TextView)findViewById(R.id.pack)).setText((BookingStep.order.getLavageType() +""));
        ((TextView)findViewById(R.id.date)).setText((BookingStep.order.getDay() +"  :  " +BookingStep.order.getTime() ));

        (findViewById(R.id.lyt_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comfirmBooking();

            }
        });
    }

    private void comfirmBooking(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Confirmation running ...");
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
            hashMap.put("Status", Booking.State.WAITING);


        reference.child(BookingStep.order.getBookId()+"").setValue(hashMap);
            pd.dismiss();


            Intent i = new Intent(this,BookingListe.class);
            startActivity(i);

            finish();


    }
}
