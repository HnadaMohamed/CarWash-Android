package com.dahbi4s.lavagemobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dahbi4s.lavagemobile.R;
import com.dahbi4s.lavagemobile.models.Package;

public class PackageAdapter extends BaseAdapter {

    Context context;
    Package[] packages;
    LayoutInflater inflter;

    public PackageAdapter(Context applicationContext, Package[] packages) {
        this.context = applicationContext;
        this.packages=packages;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void  reffresh(Package[] packages) {

        packages=packages;
        this.notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return packages.length;
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
        view = inflter.inflate(R.layout.custom_package_type_item, null);
        ImageView flag = (ImageView) view.findViewById(R.id.flag);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView price = (TextView) view.findViewById(R.id.price);
        flag.setImageResource(packages[i].getFlag());
        name.setText(packages[i].getName()+"");
        price.setText(String.valueOf(packages[i].getPrice()) + " DH");
        return view;
    }

}
