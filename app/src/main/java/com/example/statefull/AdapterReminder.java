package com.example.statefull;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

interface onSwitchListener {
    void onSwitchClick(int position);

    void onTimeClick(int id, String time);
}


public class AdapterReminder extends RecyclerView.Adapter<AdapterReminder.ViewHolder> {

    List<Reminder> reminders;
    onSwitchListener slistener;


    AdapterReminder(List<Reminder> reminders, onSwitchListener switchListener) {
        this.reminders = reminders;
        slistener = switchListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View reminderView = inflater.inflate(R.layout.item_reminder, parent, false);
        return new ViewHolder(reminderView);

    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterReminder.ViewHolder holder, final int p) {

        final Reminder reminder = reminders.get(p);
        String time = "";
        int h = reminder.getHour();
        int m = reminder.getMinute();

        if (h < 10) {
            time = "0" + h + ":";
        } else {
            time = h + ":";
        }
        if (m < 10) {
            time = time + "0" + m;
        } else {
            time = time + m;
        }
        holder.viewid = reminder._id;
        holder.time.setText(time);
        holder.meridian.setText(reminder.meridian);
        Log.d("Switch status", Boolean.toString(reminder.active));
        holder.aSwitch.setChecked(reminder.active);
    }

    @Override
    public int getItemCount() {
        int x = DatabaseManager.databaseManager.getReminderTableSize();
        Log.d("ItemCount", Integer.toString(x));
        return x;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int viewid;
        TextView time;
        TextView meridian;
        Switch aSwitch;

        View viewForeground;
        View viewBackground;

        public ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            meridian = itemView.findViewById(R.id.ampm);
            aSwitch = itemView.findViewById(R.id.a_switch);
            aSwitch.setOnClickListener(this);
            time.setOnClickListener(this);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.a_switch) {
                Log.d("Started Click", "Here");
                slistener.onSwitchClick(viewid);
                Log.d("Finished Click", "Here");

            } else if (view.getId() == R.id.time) {
                slistener.onTimeClick(viewid, time.getText().toString());
            }
        }
    }
}
