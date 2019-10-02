package com.example.statefull;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class AlarmReceiver extends BroadcastReceiver {
    String TAG = "AlarmReceiver";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        //Trigger the notification
        Log.d("Notification:", "Called");
        NotificationScheduler.setNextNotification();
        NotificationScheduler.showNotification(context, MindActivity.class);
    }
}
