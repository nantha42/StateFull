package com.example.statefull;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import java.util.Date;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static com.example.statefull.App.CHANNEL_1_ID;

public class NotificationScheduler {
    public static final int DAILY_REMINDER_REQUEST_CODE = 100;
    public static final String TAG = "NotificationScheduler";
    public static Context contextforref;
    public static Class<?> clsforref;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void setReminder(Context context, Class<?> cls, Calendar setcalendar) {
        contextforref = context;
        clsforref = cls;

        Log.d("notification", "set");
        Log.d("Debuglx", setcalendar.getTime().toString() + "");
        Log.d("setCalendar = ", setcalendar.getTime().toString() + "");
        //if(setcalendar.before(calendar))
        //  setcalendar.add(Calendar.DATE,1);
        Log.d("setCalendar = ", setcalendar.getTime() + "");

        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                DAILY_REMINDER_REQUEST_CODE, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setExact(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(),
                pendingIntent);
        Log.d("notification", "set finished");

    }

    public static void showNotification(Context context, Class<?> cls) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent notificationIntent = new Intent(context, cls);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);

        Intent intent = new Intent(context, MindActivity.class);
        intent.putExtra("EXTRA_START_DIALOG", 1);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(
                DAILY_REMINDER_REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_insert_emoticon_black_24dp)
                .setContentTitle("Your State?")
                .setContentText("Note your State")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setSound(alarmSound)
                .build();

        //NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        //notificationManagerCompat.notify(DAILY_REMINDER_REQUEST_CODE,notification);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(DAILY_REMINDER_REQUEST_CODE, notification);

    }

    public static void cancelReminder(Context context, Class<?> cls) {
        // Disable a receiver
        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                DAILY_REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void setNextNotification() {
        List<Reminder> reminders = DatabaseManager.databaseManager.getReminders();
        Calendar c = Calendar.getInstance();
        Date curtime = c.getTime();
        Calendar curtime1 = Calendar.getInstance();
        int year = curtime.getYear();
        int month = curtime.getMonth();
        int day = curtime.getDate();

        Reminder nearestReminder = null;
        Calendar nearestTime = Calendar.getInstance();
        cancelReminder(contextforref, AlarmReceiver.class);
        boolean oneIsActive = false;
        for (Reminder r : reminders) {
            if (r.isActive()) {
                oneIsActive = true;
            }
        }

        do {
            int index = 0;
            for (Reminder r : reminders) {
                index++;
                int h;
                int m = r.getMinute();
                if (r.isActive()) {
                    oneIsActive = true;
                    if (r.meridian.equals("Am"))
                        h = r.getHour();
                    else h = r.getHour() + 12;

                    if (nearestReminder == null) {
                        Calendar temp = Calendar.getInstance();

                        temp.set(year + 1900, month, day, h, m, 0);
                        if (temp.getTimeInMillis() > curtime1.getTimeInMillis()) {
                            Log.d("Debuglx", "Null so setting " + index);
                            nearestReminder = r;
                            nearestReminder.hour = h;
                            nearestTime.set(year + 1900, month, day, h, m, 0);
                        }
                    } else {
                        c.set(year + 1900, month, day, h, m, 0);
                        Log.d("Debuglx", c.getTimeInMillis() + "," + nearestTime.getTimeInMillis() + "," + curtime1.getTimeInMillis());
                        if (c.getTimeInMillis() < nearestTime.getTimeInMillis() && c.getTimeInMillis() > curtime1.getTimeInMillis()) {
                            Log.d("Debuglx", "Not Null but setting " + index);
                            nearestReminder = r;
                            nearestReminder.hour = h;
                            nearestTime = c;
                        }
                    }
                }
            }
            day++;
        } while (oneIsActive && nearestReminder == null && reminders.size() > 0);


        if (nearestReminder != null) {
            Log.d("Setting Notification", nearestReminder.getHour() + " " + nearestReminder.getMinute());
            setReminder(contextforref, clsforref, nearestTime);
        }
    }
}
