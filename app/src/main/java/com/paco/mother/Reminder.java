package com.paco.mother;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.allyants.notifyme.NotifyMe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Reminder extends AppCompatActivity {
private FloatingActionButton addNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);
        addNew = findViewById(R.id.add_new_reminder);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newReminder = new Intent(Reminder.this, NewReminder.class);
                startActivity(newReminder);
            }
        });
    }
}
