package com.dahbi4s.lavagemobile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.dahbi4s.lavagemobile.Adapter.TimeAdapter;
import com.dahbi4s.lavagemobile.R;
import com.dahbi4s.lavagemobile.activities.BookingStep;
import com.dahbi4s.lavagemobile.models.TimeTaken;
import com.dahbi4s.lavagemobile.models.Tools;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.ArrayList;
import java.util.Calendar;

public class DateAndTime extends Fragment {

    public GridView timeListe;
    public Context context;
    public View root;
    public Button date;
    public ArrayList<String> timeArray;
    public ArrayList<String> timeArrayNew;

    public ArrayList<TimeTaken> timeTakenArray;

    public ProgressBar progress_circular;

    public DateAndTime() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_date_and_time, container, false);
        context = container.getContext();

        date = (Button) root.findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDatePickerLight();
            }
        });
        timeListe = (GridView) root.findViewById(R.id.timeListe);
        progress_circular = root.findViewById(R.id.progress_circular);
        progress_circular.setVisibility(View.VISIBLE);
        timeArray = new ArrayList<>();
        timeArrayNew = new ArrayList<>();
        timeTakenArray = new ArrayList<>();
        getTimeListe();
        getTimeTakenListe();

        ((Button) root.findViewById(R.id.date)).setText(BookingStep.order.getDay());

        BookingStep.order.setTime(null);

        return root;
    }

    private void getTimeListe() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("worktime");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                timeArray.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String time = snapshot.getValue(String.class);
                    timeArray.add(time);
                    timeArrayNew.add(time);
                }

                TimeAdapter adapter = new TimeAdapter(context, timeArray);
                timeListe.setAdapter(adapter);
                progress_circular.setVisibility(View.GONE);
                timeListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        BookingStep.order.setTime(timeArray.get(position));
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void getTimeTakenListe() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("timetaken");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                timeTakenArray.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TimeTaken time = snapshot.getValue(TimeTaken.class);
                    timeTakenArray.add(time);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void dialogDatePickerLight() {
        Calendar cur_calender = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();
                        ++monthOfYear;
                        String date = dayOfMonth + "/" + monthOfYear + "/" + year;
                        BookingStep.order.setDay(date);

                        ((TextView) root.findViewById(R.id.date)).setText(Tools.getFormattedDateSimple(date_ship_millis));
                        ((TextView) root.findViewById(R.id.date)).setBackgroundColor(getResources().getColor(R.color.colorAccent));

                        //initialise timeliste
                        BookingStep.order.setTime(null);
                        timeArray.clear();
                        timeArray.addAll(timeArrayNew);

                        //Remove times.
                        ArrayList<String> toRemove = new ArrayList<String>();
                        for (TimeTaken time : timeTakenArray) {

                            if (date.equals(time.getDay())) {
                                toRemove.add(time.getTime());
                            }
                        }
                        for (String time : toRemove) {
                            timeArray.remove(time);
                        }

                        //Reffresh View
                        TimeAdapter adapter = new TimeAdapter(context, timeArray);
                        timeListe.setAdapter(adapter);
                        timeListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                BookingStep.order.setTime(timeArray.get(position));
                            }
                        });

                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

}
