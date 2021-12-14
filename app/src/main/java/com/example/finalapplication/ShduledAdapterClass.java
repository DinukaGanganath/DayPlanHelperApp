package com.example.finalapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShduledAdapterClass extends RecyclerView.Adapter<ShduledAdapterClass.MyViewHolder> {

    Context context;
    ArrayList<SheduledItem> sheduledItemsList;


    public interface OnItemClickListner{
        void onItemClick(int position);
    }


    public ShduledAdapterClass(Context context, ArrayList<SheduledItem> sheduledItemsList) {
        this.context = context;
        this.sheduledItemsList = sheduledItemsList;
    }

    @NonNull
    @Override
    public ShduledAdapterClass.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.seduled_item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShduledAdapterClass.MyViewHolder holder, int position) {

        SheduledItem sheduledItem = sheduledItemsList.get(position);

        holder.timeClass.setText(sheduledItem.timeClass);
        holder.eventNameClass.setText(sheduledItem.eventNameClass);

    }

    @Override
    public int getItemCount() {

        return sheduledItemsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView timeClass, eventNameClass;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timeClass = itemView.findViewById(R.id.ItemSheduleTime);
            eventNameClass = itemView.findViewById(R.id.ItemSheduleEvent);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            SheduledItem sdi = sheduledItemsList.get(getAdapterPosition());
            Intent intent = new Intent(context, EditDeleteActivity.class);
            intent.putExtra("sheduledItem", sdi);
            context.startActivity(intent);

        }
    }
}
