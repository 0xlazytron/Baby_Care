package com.paco.mother;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        CardView addBaby = findViewById(R.id.addBabies);
        CardView addReminder = findViewById(R.id.addReminders);
        CardView babyLytics = findViewById(R.id.babyLytics);
        CardView motherGuide = findViewById(R.id.motherGuide);
        addBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(Home.this, manageBaby.class);
                startActivity(add);
            }
        });

        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goReminder = new Intent(Home.this, Reminder.class);
                startActivity(goReminder);
            }
        });
        babyLytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goAnalytics = new Intent(Home.this, babyLytic.class);
                startActivity(goAnalytics);
            }
        });
        motherGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goGuide = new Intent(Home.this, Guide.class);
                startActivity(goGuide);
            }
        });
    }
}
