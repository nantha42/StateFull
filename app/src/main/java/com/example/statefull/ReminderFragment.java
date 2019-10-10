package com.example.statefull;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;


public class ReminderFragment extends Fragment implements onSwitchListener, RecyclerReminderItemTouchHelper.RecyclerItemTouchHelperListener {

    private List<Reminder> reminders;
    private ReminderAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        RecyclerView rvReminders = view.findViewById(R.id.rvreminders);
        ((MindActivity)getActivity()).setActionBarTitle("StateFull");
        reminders = DatabaseManager.databaseManager.getReminders();
        adapter = new ReminderAdapter(reminders, this);
        rvReminders.setAdapter(adapter);
        rvReminders.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvReminders.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerReminderItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvReminders);
        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        if (fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Calendar c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                            new TimePickerDialog.OnTimeSetListener() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {
                                    String meridian = "Am";
                                    if (hourOfDay > 12) {
                                        hourOfDay = hourOfDay - 12;
                                        meridian = "Pm";
                                    }
                                    DatabaseManager.databaseManager.fabAddReminder(hourOfDay, minute, meridian);
                                    adapter.reminders = DatabaseManager.databaseManager.getReminders();
                                    adapter.notifyDataSetChanged();
                                    NotificationScheduler.contextforref = getActivity();
                                    NotificationScheduler.clsforref = AlarmReceiver.class;
                                    NotificationScheduler.setNextNotification();

                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
            });
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSwitchClick(int p) {
        Log.d("Logd Clicked Switch", Integer.toString(p));
        DatabaseManager.databaseManager.negatereminder(p);
        Log.d("1", "1");
        adapter.reminders = DatabaseManager.databaseManager.getReminders();
        Log.d("2", "1");
        adapter.notifyDataSetChanged();
        Log.d("3", "1");
        NotificationScheduler.contextforref = getActivity();
        Log.d("4", "1");
        NotificationScheduler.clsforref = AlarmReceiver.class;
        Log.d("5", "1");
        NotificationScheduler.setNextNotification();
        Log.d("6", "1");

    }

    @Override
    public void onTimeClick(final int id, String time) {
        String[] splittime = time.split(":");
        int hour = Integer.parseInt(splittime[0]);
        int min = Integer.parseInt(splittime[1]);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String meridian = "Am";
                        if (hourOfDay > 12) {
                            hourOfDay = hourOfDay - 12;
                            meridian = "Pm";
                        }
                        DatabaseManager.databaseManager.editReminder(id, hourOfDay, minute, meridian);
                        adapter.reminders = DatabaseManager.databaseManager.getReminders();
                        adapter.notifyDataSetChanged();
                        NotificationScheduler.contextforref = getActivity();
                        NotificationScheduler.clsforref = AlarmReceiver.class;
                        NotificationScheduler.setNextNotification();

                    }
                }, hour, min, false);
        timePickerDialog.show();
    }

    /*
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDeleteClick(int p) {
        Log.d("Logd Clicked Delete", Integer.toString(p));
        DatabaseManager.databaseManager.deletereminder(p);
        adapter.reminders = DatabaseManager.databaseManager.getReminders();
        adapter.notifyDataSetChanged();
        NotificationScheduler.contextforref = getActivity();
        NotificationScheduler.clsforref = AlarmReceiver.class;
        NotificationScheduler.setNextNotification();
    }*/

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        DatabaseManager.databaseManager.deletereminder(((ReminderAdapter.ViewHolder) viewHolder).viewid);
        adapter.reminders = DatabaseManager.databaseManager.getReminders();
        adapter.notifyDataSetChanged();
        NotificationScheduler.contextforref = getActivity();
        NotificationScheduler.clsforref = AlarmReceiver.class;
        NotificationScheduler.setNextNotification();
    }
}