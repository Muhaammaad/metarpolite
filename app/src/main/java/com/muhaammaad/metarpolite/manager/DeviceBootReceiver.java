package com.muhaammaad.metarpolite.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DeviceBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("","ACTION_BOOT_COMPLETEDACTION_BOOT_COMPLETED");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            AlarmReceiver.setAlarm(context);

        }
    }
}