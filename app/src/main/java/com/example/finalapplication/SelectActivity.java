package com.example.finalapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        CardView DayPlan = (CardView)findViewById(R.id.Today);
        CardView EventPlan = (CardView)findViewById(R.id.Event);
        CardView History = (CardView)findViewById(R.id.History);

        DayPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toDayActivityShedules = new Intent(SelectActivity.this, DayActivityShedules.class);
                startActivity(toDayActivityShedules);
            }
        });

        EventPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toEventPlan = new Intent(SelectActivity.this, EventPlan.class);
                startActivity(toEventPlan);
            }
        });
    }
}