package com.paco.mother;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allyants.notifyme.NotifyMe;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.paco.mother.Adapters.babyAdapter;
import com.paco.mother.Reciever.ReminderReciever;
import com.paco.mother.helper.dbHelper;
import com.paco.mother.model.babyModel;
import com.paco.mother.model.reminderModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.style.Theme_Holo_Light_Dialog_MinWidth;

public class NewReminder extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        View.OnClickListener {
    EditText remName;
    TextView remTime,remDate;
    Spinner remBaby,remType;
    Button addRem;
    SQLiteDatabase DB;
    private dbHelper db;
//    int baby_id;
    RecyclerView.Recycler recycler1;
    TimePickerDialog mTimePicker;
    DatePickerDialog mDatePicker;
    String type,babies;
    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private String mTime;
    private String mDate;
    private int notificationId = 1;
    private ProgressDialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reminder);
        db = new dbHelper(this);
        remBaby = findViewById(R.id.rem_baby);
        remType = findViewById(R.id.rem_type);
        remName = findViewById(R.id.rem_name);
        remDate = findViewById(R.id.rem_date);
        addRem = findViewById(R.id.addRem);
        LoadingBar = new ProgressDialog(this);
        findViewById(R.id.addRem).setOnClickListener(this);
        remTime = findViewById(R.id.rem_time);
        List<babyModel> list = db.getBabyNames();
        //creating adapter for spinner
        String[] nameList = new String[list.size()];
        try{
            for(int i=0;i<list.size();i++){
                nameList[i]=list.get(i).getName(); //create array of name
            }
        }catch (Exception e){
            Toast.makeText(this, "Exception "+e, Toast.LENGTH_SHORT).show();
        }


        ArrayAdapter<String> ReminderBaby =
                new ArrayAdapter(this,
                        R.layout.style_spinner,
                        nameList);
        ReminderBaby.setDropDownViewResource(R.layout.style_spinner);
        remBaby.setAdapter(ReminderBaby);
        remBaby.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> reminderType =
                ArrayAdapter.createFromResource(this,
                        R.array.reminder_types,
                        R.layout.style_spinner);
        reminderType.setDropDownViewResource(R.layout.style_spinner);
        remType.setAdapter(reminderType);
        remType.setOnItemSelectedListener(this);
        mCalendar = Calendar.getInstance();

        remTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
                mMinute = mCalendar.get(Calendar.MINUTE);

                mTimePicker = new TimePickerDialog(NewReminder.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                //adds the 0, like : 10:6 AM --> 10:06 AM
                                mHour = selectedHour;
                                mMinute = selectedMinute;
                                if(selectedMinute<10){
                                    remTime.setText(selectedHour + ":0" + selectedMinute);
                                }
                                else {
                                    remTime.setText(selectedHour + ":" + selectedMinute);
                                }
                            }
                        }, mHour, mMinute, true);//Yes 24 hour time

                //more settings for dialog window
                mTimePicker.setTitle("Select Time");
                mTimePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mTimePicker.show();
            }
        });
   remDate.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           mYear = mCalendar.get(Calendar.YEAR);
           mMonth = mCalendar.get(Calendar.MONTH);
           mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
           mDatePicker = new DatePickerDialog(NewReminder.this, Theme_Holo_Light_Dialog_MinWidth,
               new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int day) {
                       mYear = year;
                       mMonth = month;
                       mDay = day;
                       remDate.setText(year+" / "+month+" "+day);
                   }
               },mYear,mMonth,mDay);
           mDatePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
           mDatePicker.show();
           mDatePicker.setTitle("Select Date");
       }});
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = parent.getItemAtPosition(position).toString();
       babies = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), ""+type, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(NewReminder.this, ReminderReciever.class);
        intent.putExtra("notificationId",notificationId);
        intent.putExtra("todo",remName.getText().toString());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(
                NewReminder.this,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        switch(view.getId()) {
            case R.id.addRem:
                // Set up calender for creating the notification
                mCalendar.set(Calendar.MONTH, --mMonth);
                mCalendar.set(Calendar.YEAR, mYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
                mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
                mCalendar.set(Calendar.MINUTE, mMinute);
                mCalendar.set(Calendar.SECOND, 0);
                long selectedTimestamp =  mCalendar.getTimeInMillis();
                if (Build.VERSION.SDK_INT >= 23) {

                    alarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, selectedTimestamp, alarmIntent);

                } else if (Build.VERSION.SDK_INT >= 19) {

                    alarm.setExact(AlarmManager.RTC_WAKEUP, selectedTimestamp, alarmIntent);

                } else {

                    alarm.set(AlarmManager.RTC_WAKEUP, selectedTimestamp, alarmIntent);

                }
                Toast.makeText(this, "Alarm set To : "+selectedTimestamp, Toast.LENGTH_SHORT).show();
                addRemToDb();
                break;
        }
    }

    private void addRemToDb() {
        String name1,baby,time1,date1,type1;
        name1 = remName.getText().toString();
        baby = remBaby.getSelectedItem().toString();
        time1 = remTime.getText().toString();
        date1 = remDate.getText().toString();
        type1 = remType.getSelectedItem().toString();
            LoadingBar.setTitle("Adding");
            LoadingBar.setMessage("Please Wait, While we are Adding Details....");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();
            reminderModel reminder = new reminderModel(name1,type1,time1,date1,baby);
            db.addReminder(reminder);
            LoadingBar.dismiss();
            finish();
            startActivity(getIntent());
        }


    }
