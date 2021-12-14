package com.example.finalapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EventHistoryMain extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView HistoryDate, HistoryDateSearch;
    RecyclerView HistoryRecycler;
    ArrayList<HistoryEvent> HistoryEventList;
    HistoryEventAdapter historyEventAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_event);

        HistoryDate = (TextView) findViewById(R.id.HisSearchDate);
        HistoryDateSearch = (TextView) findViewById(R.id.HisSearchShedBtn);

        HistoryDateSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        Calendar c = Calendar.getInstance();
        String Today = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        HistoryDate.setText(Today);

        HistoryRecycler = (RecyclerView) findViewById(R.id.HisSheduledRecyclerView);
        HistoryRecycler.setHasFixedSize(true);
        HistoryRecycler.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        HistoryEventList = new ArrayList<HistoryEvent>();
        historyEventAdapter = new HistoryEventAdapter(EventHistoryMain.this, HistoryEventList);



        HistoryRecycler.setAdapter(historyEventAdapter);

        EventChangeListner(Today);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, i);
        ca.set(Calendar.MONTH, i1);
        ca.set(Calendar.DAY_OF_MONTH, i2);
        String currentDateStr = DateFormat.getDateInstance(DateFormat.FULL).format(ca.getTime());

        HistoryDate.setText(currentDateStr);
        EventChangeListner(currentDateStr);
    }

    private void EventChangeListner(String str) {

        HistoryEventList.clear();
        db.collection("Event")
                .whereEqualTo("dateClass", str)
                .whereEqualTo("doneClass", true)
                .orderBy("timeClass", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error != null){
                    Log.d("Error Occurred", error.getMessage());
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        HistoryEventList.add(dc.getDocument().toObject(HistoryEvent.class));
                        Log.d("heee", dc.getDocument().toObject(HistoryEvent.class).toString());
                    }
                }

                historyEventAdapter.notifyDataSetChanged();

            }
        });

    }

}