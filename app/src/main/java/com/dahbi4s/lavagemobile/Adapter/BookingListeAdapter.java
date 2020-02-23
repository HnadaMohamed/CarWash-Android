package com.dahbi4s.lavagemobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dahbi4s.lavagemobile.R;
import com.dahbi4s.lavagemobile.models.Booking;

import java.util.ArrayList;
import java.util.List;


public class BookingListeAdapter extends BaseAdapter{

    Context c;
    List<Booking> items = new ArrayList<Booking>();

    public BookingListeAdapter(Context c , List<Booking> items){
        this.c=c;
        this.items=items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView== null){
            convertView= LayoutInflater.from(c).inflate(R.layout.booking_item,parent,false);

            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.idBook = convertView.findViewById(R.id.idBook);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.price.setText(items.get(position).getPrice() + "DH");
        viewHolder.idBook.setText(items.get(position).getBookId());




        convertView.setTag(viewHolder);

        return convertView;
    }
    static class ViewHolder{
        TextView idBook;
         TextView price;


    }
}
