package com.muhaammaad.metarpolite.manager;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.muhaammaad.metarpolite.persistence.AviationData;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AviationData.getAviationDataFromNetwork(null);
    }

    public static void setAlarm(Context context) {
        // Intent to start the Broadcast Receiver
        Intent broadcastIntent = new Intent(context, AlarmReceiver.class);

        // The Pending Intent to pass in AlarmManager
        PendingIntent pIntent = PendingIntent.getBroadcast(
                context,
                0,
                broadcastIntent,
                0
        );

        // Setting up AlarmManager
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        // Set an alarm to trigger 60 second after the button is pressed
        if (alarmMgr != null) {
            alarmMgr.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 60000,
                    3600000,
                    pIntent);
        }
    }
}