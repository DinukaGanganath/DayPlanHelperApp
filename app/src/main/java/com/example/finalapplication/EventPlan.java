package com.example.finalapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class EventPlan extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    TextView Date, Time;
    EditText EventName, Description;
    Spinner Category;
    Button AddEvent;
    String CategoryStr, EventStr, TimeStr, DateStr, DescriptionStr;
    boolean isdone;
    List<EventClass> savedEventsList;

    FirebaseFirestore db, dbret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_plan);

        db = FirebaseFirestore.getInstance();
        dbret = FirebaseFirestore.getInstance();

        Date = (TextView) findViewById(R.id.DateEdit);
        Time = (TextView) findViewById(R.id.TimeEdit);
        Category = (Spinner) findViewById(R.id.CategorySelect);
        EventName = (EditText) findViewById(R.id.EventNameEdit);
        Description =(EditText) findViewById(R.id.DescriptionEdit);
        AddEvent = (Button) findViewById(R.id.AddEventBtn);

        AddEvent.setOnClickListener(this);

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Category.setAdapter(adapter);
        Category.setOnItemSelectedListener(this);

    }


    @Override
    public void onClick(View view) {

        EventStr = EventName.getText().toString().trim();
        TimeStr = Time.getText().toString().trim();
        DateStr = Date.getText().toString().trim();
        DescriptionStr = Description.getText().toString().trim();
        isdone = false;


        if(ValidatingInputs(EventStr, TimeStr, DateStr, CategoryStr, DescriptionStr)){
            dbret.collection("Event")
                    .whereEqualTo("dateClass", DateStr)
                    .whereEqualTo("timeClass", TimeStr)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> EventList = queryDocumentSnapshots.getDocuments();
                            if (EventList.isEmpty()){
                                CollectionReference dbEvent = db.collection("Event");
                                EventClass eventClass = new EventClass(EventStr, TimeStr, DateStr, CategoryStr, DescriptionStr, isdone);

                                dbEvent.add(eventClass).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(EventPlan.this, "Event Added Successfully!", Toast.LENGTH_SHORT).show();
                                        Intent toSelectActivity = new Intent(EventPlan.this, SelectActivity.class);
                                        startActivity(toSelectActivity);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EventPlan.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(), "Date and Time you entered are already used", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH, i1);
        c.set(Calendar.DAY_OF_MONTH, i2);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        Date.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String Hour, Minute;
        Hour = Integer.toString(i);
        Minute = Integer.toString(i1);
        if(i<10){
            Hour = "0"+Hour;
        }
        if(i1<10){
            Minute = "0"+Minute;
        }


        Time.setText(Hour +" : "+ Minute + " h");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       if (i==0){
           CategoryStr = "Meeting";
       }else if(i==1){
           CategoryStr = "Lecture";
       }else if(i == 2){
           CategoryStr = "Shopping";
       }else{
           CategoryStr = "Other";
       }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean ValidatingInputs(String Event, String Time, String Date, String Category, String Description){
        if (EventStr.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter the Event Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TimeStr.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please select the time",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (DateStr.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please select the Date",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (CategoryStr.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please select the valid category",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (DescriptionStr.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter the Description",Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
    }

}