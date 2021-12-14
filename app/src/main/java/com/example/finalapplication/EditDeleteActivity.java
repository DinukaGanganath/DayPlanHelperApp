package com.example.finalapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;

public class EditDeleteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText EventName, Description;
    TextView Date, TimeTxt, Category;
    Button Edit, Delete;
    CheckBox checkBox;
    SheduledItem sdi;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        EventName = (EditText)findViewById(R.id.EditDeleteEventNameEdit);
        Description = (EditText)findViewById(R.id.EditDeleteDescriptionEdit);

        Date = (TextView) findViewById(R.id.EditDeleteDateEdit);
        TimeTxt = (TextView) findViewById(R.id.EditDeleteTimeEdit);
        Category = (TextView)findViewById(R.id.EditDeleteCategorySelect);

        checkBox = (CheckBox) findViewById(R.id.checky);

        Edit = (Button) findViewById(R.id.EditEventBtn);
        Delete = (Button) findViewById(R.id.DeleteEventBtn);

        sdi = (SheduledItem) getIntent().getSerializableExtra("sheduledItem");
        db = FirebaseFirestore.getInstance();

        EventName.setText(sdi.getEventNameClass());
        Category.setText(sdi.getCategoryClass());
        Description.setText(sdi.getDescriptionClass());
        Date.setText(sdi.getDateClass());
        TimeTxt.setText(sdi.getTimeClass());
        checkBox.setChecked(sdi.isDoneClass);

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        TimeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventUpdate();
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditDeleteActivity.this);
                builder.setTitle("Do you want to delete?")
                        .setMessage("Click on the Delete button will erase your all data on this event.\nAre you sure? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DeleteEvent();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });


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


        TimeTxt.setText(Hour +" : "+ Minute + " h");
    }

    private void EventUpdate(){
        if(ValidatingInputs()){
            boolean isdone;
            if(checkBox.isChecked()){
                isdone = true;
            }else{
                isdone = false;
            }

            SheduledItem s = new SheduledItem(
                  TimeTxt.getText().toString().trim(),
                  EventName.getText().toString().trim(),
                  Category.getText().toString().trim(),
                  Date.getText().toString().trim(),
                  Description.getText().toString().trim(),
                  isdone
            );

            db.collection("Event").document(sdi.getId())
                    .set(s)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(EditDeleteActivity.this,"Event updated successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditDeleteActivity.this, DayActivityShedules.class));
                        }
                    });
        }
    }

    public void DeleteEvent(){
        db.collection("Event").document(sdi.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(EditDeleteActivity.this,"Event deleted successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(EditDeleteActivity.this,DayActivityShedules.class));
                        }
                    }
                });
    }

    private boolean ValidatingInputs(){
        if (EventName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter the Event Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TimeTxt.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please select the time",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Date.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please select the Date",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Category.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please select the valid category",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Description.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter the Description",Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
    }
}