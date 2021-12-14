package com.example.finalapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

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

import javax.annotation.Nullable;

public class DayActivityShedules extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView SearchDate, SearchDateClick;
    RecyclerView recyclerView;
    ArrayList<SheduledItem> sheduledItemArrayList;
    ShduledAdapterClass shduledAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_shedules);

        SearchDate = (TextView) findViewById(R.id.SearchDate);
        SearchDateClick = (TextView) findViewById(R.id.SearchShedBtn);

        SearchDateClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        Calendar c = Calendar.getInstance();
        String Today = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        SearchDate.setText(Today);

        recyclerView = (RecyclerView) findViewById(R.id.SheduledRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        sheduledItemArrayList = new ArrayList<SheduledItem>();
        shduledAdapter = new ShduledAdapterClass(DayActivityShedules.this, sheduledItemArrayList);

        recyclerView.setAdapter(shduledAdapter);

        EventChangeListner(Today);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, i);
        ca.set(Calendar.MONTH, i1);
        ca.set(Calendar.DAY_OF_MONTH, i2);
        String currentDateStr = DateFormat.getDateInstance(DateFormat.FULL).format(ca.getTime());

        SearchDate.setText(currentDateStr);
        EventChangeListner(currentDateStr);
    }

    private void EventChangeListner(String str) {

        sheduledItemArrayList.clear();
        db.collection("Event")
                .whereEqualTo("dateClass",str)
                .orderBy("timeClass", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Error occured", error.getMessage());
                            return;
                        }

                        if(!value.isEmpty()) {
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    SheduledItem sDI = dc.getDocument().toObject(SheduledItem.class);
                                    sDI.setId(dc.getDocument().getId());
                                    sheduledItemArrayList.add(sDI);
                                }

                                shduledAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

}