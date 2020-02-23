package com.dahbi4s.lavagemobile.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dahbi4s.lavagemobile.R;
import com.dahbi4s.lavagemobile.activities.BookingEdit;
import com.dahbi4s.lavagemobile.activities.BookingListe;
import com.dahbi4s.lavagemobile.models.Booking;

import java.util.ArrayList;
import java.util.List;


public class BookingListeAdapterR extends RecyclerView.Adapter<BookingListeAdapterR.ViewHolder>{

    List<Booking> items = new ArrayList<Booking>();
    private Context context;

    public BookingListeAdapterR(List<Booking> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.booking_item,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.idBook.setText(items.get(i).getBookId());
        viewHolder.price.setText(items.get(i).getPrice() + " DH");
        //viewHolder.cardview.setEnabled(false);
        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardView card =(CardView) view;
                //card.setBackgroundResource(R.color.amber_50);
                Intent intent = new Intent(context, BookingEdit.class);
                intent.putExtra("idBook", items.get(viewHolder.getAdapterPosition()).getBookId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardview ;
        private TextView idBook;
        private TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardview=itemView.findViewById(R.id.cardView);
            idBook=itemView.findViewById(R.id.idBook);
            price=itemView.findViewById(R.id.price);
        }
    }
}

