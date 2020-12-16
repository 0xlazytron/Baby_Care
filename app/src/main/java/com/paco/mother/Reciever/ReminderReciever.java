package com.paco.mother.Reciever;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import com.paco.mother.NewReminder;

public class ReminderReciever extends BroadcastReceiver{
    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    private final long[] pattern ={100,300,300,300};

//    public static Uri getAlarmSound() {
//        return alarmSound;
//    }
//    public static void setAlarmSound(Uri alarmSound) {
//        ReminderReciever.alarmSound = alarmSound;
//    }
    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra("notificationId",0);
        String message = intent.getStringExtra("todo");
        Intent mainIntent = new Intent(context, NewReminder.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,mainIntent,0);
        NotificationManager myNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle("Alarm")
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
        .setSound(soundUri)
        .setVibrate(pattern);
        myNotificationManager.notify(notificationId, builder.build());
    }
}
