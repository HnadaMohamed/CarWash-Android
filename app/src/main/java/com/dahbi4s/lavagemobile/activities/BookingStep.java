package com.dahbi4s.lavagemobile.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dahbi4s.lavagemobile.R;
import com.dahbi4s.lavagemobile.fragment.DateAndTime;
import com.dahbi4s.lavagemobile.fragment.UserInformation;
import com.dahbi4s.lavagemobile.fragment.WashInformation;
import com.dahbi4s.lavagemobile.models.Booking;

import java.util.concurrent.atomic.AtomicLong;


public class BookingStep extends AppCompatActivity {

    private static final AtomicLong atomicRefId = new AtomicLong();
    public static Booking order ;

    private enum State {
        WASHINF,
        DATETIME,
        USERINF
    }

    State[] array_state = new State[]{State.WASHINF, State.DATETIME, State.USERINF};

    private View line_first, line_second;
    private ImageView image_shipping, image_payment, image_confirm;
    private TextView tv_shipping, tv_payment, tv_confirm;

    private int idx_state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_step);
        initToolbar();
        initComponent();

        order = new Booking();
        //order.setBookId(5127+atomicRefId.incrementAndGet());

        displayFragment(State.WASHINF);
    }

    private void initComponent() {
        line_first = (View) findViewById(R.id.line_first);
        line_second = (View) findViewById(R.id.line_second);
        image_shipping = (ImageView) findViewById(R.id.image_shipping);
        image_payment = (ImageView) findViewById(R.id.image_payment);
        image_confirm = (ImageView) findViewById(R.id.image_confirm);

        tv_shipping = (TextView) findViewById(R.id.tv_shipping);
        tv_payment = (TextView) findViewById(R.id.tv_payment);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);

        image_payment.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);
        image_confirm.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);

        (findViewById(R.id.lyt_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(idx_state == 1){
                    if( BookingStep.order.getTime()==null || BookingStep.order.getDay()== null
                    ||   BookingStep.order.getTime().length()<4 || BookingStep.order.getDay().length()<4    ){
                        Toast.makeText(BookingStep.this ,"tous les champs doivent être remplis" , Toast.LENGTH_LONG ).show();
                    }else{
                        idx_state++;
                        displayFragment(array_state[idx_state]);
                    }
                }

                else if(idx_state == 2){
                    if( BookingStep.order.getName()==null ||  BookingStep.order.getPhone()== null || BookingStep.order.getAddress()==null  ||
                            BookingStep.order.getName().length()<3 ||  BookingStep.order.getPhone().length()<10 || BookingStep.order.getAddress().equals("")
                    ){
                        Toast.makeText(BookingStep.this ,"tous les champs doivent être remplis" , Toast.LENGTH_LONG ).show();
                    }else{
                        Intent intent = new Intent(BookingStep.this, Confirmation.class);
                        startActivity(intent);

                        return;

                    }
                }else{
                    idx_state++;
                    displayFragment(array_state[idx_state]);
                }



            }
        });

        (findViewById(R.id.lyt_previous)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (idx_state < 1) return;
                idx_state--;
                displayFragment(array_state[idx_state]);
            }
        });
    }

    private void initToolbar() {

    }

    private void displayFragment(State state) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        refreshStepTitle();

        if (state.name().equalsIgnoreCase(State.WASHINF.name())) {
            fragment = new WashInformation();
            tv_shipping.setTextColor(getResources().getColor(R.color.grey_90));
            image_shipping.clearColorFilter();
        } else if (state.name().equalsIgnoreCase(State.DATETIME.name())) {
            fragment = new DateAndTime();
            line_first.setBackgroundColor(getResources().getColor(R.color.mdtp_white));
            image_shipping.setColorFilter(getResources().getColor(R.color.mdtp_white), PorterDuff.Mode.SRC_ATOP);
            image_payment.clearColorFilter();
            tv_payment.setTextColor(getResources().getColor(R.color.grey_90));
        } else if (state.name().equalsIgnoreCase(State.USERINF.name())) {
            fragment = new UserInformation();
            line_second.setBackgroundColor(getResources().getColor(R.color.mdtp_white));
            image_payment.setColorFilter(getResources().getColor(R.color.mdtp_white), PorterDuff.Mode.SRC_ATOP);
            image_confirm.clearColorFilter();
            tv_confirm.setTextColor(getResources().getColor(R.color.grey_90));
        }

        if (fragment == null) return;
        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    private void refreshStepTitle() {
        tv_shipping.setTextColor(getResources().getColor(R.color.grey_20));
        tv_payment.setTextColor(getResources().getColor(R.color.grey_20));
        tv_confirm.setTextColor(getResources().getColor(R.color.grey_20));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_setting, menu);
        //Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_60));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

