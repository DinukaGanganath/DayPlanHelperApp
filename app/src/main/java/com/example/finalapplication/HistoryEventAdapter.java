package com.example.finalapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryEventAdapter extends RecyclerView.Adapter<HistoryEventAdapter.HistoryViewHolder> {

    Context contextHistory;

    public HistoryEventAdapter(Context contextHistory, ArrayList<HistoryEvent> historyArrayList) {
        this.contextHistory = contextHistory;
        HistoryArrayList = historyArrayList;
    }

    ArrayList<HistoryEvent> HistoryArrayList;

    @NonNull
    @Override
    public HistoryEventAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(contextHistory).inflate(R.layout.history_item, parent, false);

        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryEventAdapter.HistoryViewHolder holder, int position) {
        HistoryEvent historyEvent = HistoryArrayList.get(position);
        holder.HistoryTime.setText(historyEvent.timeClass);
        holder.HistoryEvent.setText(historyEvent.eventNameClass);
    }

    @Override
    public int getItemCount() {
        return HistoryArrayList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder{

        TextView HistoryTime, HistoryEvent;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            HistoryEvent= itemView.findViewById(R.id.HistoryItemEvent);
            HistoryTime = itemView.findViewById(R.id.HistoryItemTime);
        }
    }
}
