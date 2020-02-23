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

public class CarTypeAdapter extends BaseAdapter {

    Context context;
    VehicleType vehicleTypes[];

    LayoutInflater inflter;

    public CarTypeAdapter(Context applicationContext, VehicleType[] vehicleTypes) {
        this.context = applicationContext;
        this.vehicleTypes = vehicleTypes;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return vehicleTypes.length;
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
        view = inflter.inflate(R.layout.custom_car_type_item, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView name = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(vehicleTypes[i].getFlag());
        name.setText(String.valueOf(vehicleTypes[i].getType()));
        return view;
    }

}
