package com.paco.mother;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
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
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.allyants.notifyme.NotifyMe;
import com.paco.mother.Reciever.ReminderReciever;

import java.util.Calendar;

public class NewReminder extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    EditText remName;
    Spinner remBaby,remType;
    Button addRem;
    private int notificationId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reminder);
//        remBaby = findViewById(R.id.rem_for_baby);
//        remType = findViewById(R.id.rem_type);
        remName = findViewById(R.id.rem_name);
        findViewById(R.id.addRem).setOnClickListener(this);
//        remTime = findViewById(R.id.rem_time);
//        ArrayAdapter<CharSequence> reminderType =
//                ArrayAdapter.createFromResource(this,
//                R.array.reminder_types,
//                android.R.layout.simple_spinner_item);
//        reminderType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        remType.setAdapter(reminderType);
//        remType.setOnItemSelectedListener(this);
//        ArrayAdapter<CharSequence> ReminderBaby =
//                ArrayAdapter.createFromResource(this,
//                        R.array.reminder_baby,
//                        android.R.layout.simple_spinner_item);
//        ReminderBaby.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        remBaby.setAdapter(ReminderBaby);
//        remBaby.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String type = parent.getItemAtPosition(position).toString();
        String babies = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), ""+type, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
    @Override
    public void onClick(View view) {
        TimePicker timeNow = findViewById(R.id.timeNow);
//        sending Notification Data To reciever
        Intent intent = new Intent(NewReminder.this, ReminderReciever.class);
        intent.putExtra("notificationId",notificationId);
        intent.putExtra("todo",remName.getText().toString());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(
                NewReminder.this,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarm =(AlarmManager) getSystemService(ALARM_SERVICE);
        switch(view.getId()) {
            case R.id.addRem:
                int hour = timeNow.getCurrentHour();
                int minute = timeNow.getCurrentMinute();
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.HOUR_OF_DAY, 0);
                long alarmStartTime = startTime.getTimeInMillis();
                alarm.set(AlarmManager.RTC_WAKEUP,alarmStartTime,alarmIntent);
                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}