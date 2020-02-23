package com.dahbi4s.lavagemobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dahbi4s.lavagemobile.R;
import com.dahbi4s.lavagemobile.models.VehicleType;

import java.util.ArrayList;

public class TimeAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> times;

    LayoutInflater inflter;

    public TimeAdapter(Context applicationContext, ArrayList<String> times) {
        this.context = applicationContext;
        this.times = times;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return times.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_time_item, null);
        TextView name = (TextView) view.findViewById(R.id.textView);
        name.setText(String.valueOf(times.get(i)));
        view.setEnabled(false);
        return view;
    }

}
