package com.paco.mother;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.allyants.notifyme.NotifyMe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.paco.mother.Adapters.babyAdapter;
import com.paco.mother.Adapters.reminderAdapter;
import com.paco.mother.helper.dbHelper;

public class Reminder extends AppCompatActivity {
private FloatingActionButton addNew;
RecyclerView recycler1;
    private dbHelper db;
    private reminderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);
        recycler1 = findViewById(R.id.reminder_list);
        addNew = findViewById(R.id.add_new_reminder);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newReminder = new Intent(Reminder.this, NewReminder.class);
                startActivity(newReminder);
            }
        });
        //        trying to get reminder list
        try{
            db = new dbHelper(this);
            adapter = new reminderAdapter(db.getAllrems(),this);
            recycler1.setLayoutManager(new LinearLayoutManager(this));
            recycler1.setHasFixedSize(true);
            recycler1.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch(Exception x){
            StyleableToast.makeText(Reminder.this,"ERROR GETTING THE LIST"+x.getMessage(), R.style.error).show();

        }
    }
}
