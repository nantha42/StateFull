package com.example.statefull;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class AdapterEventReview extends RecyclerView.Adapter<AdapterEventReview.ViewHolder> {
    int dayId;
    List<Event> events;
    int[] allMoods = {R.drawable.ic_01_awesome,
            R.drawable.ic_02_cool,
            R.drawable.ic_03_great,
            R.drawable.ic_04_happy,
            R.drawable.ic_05_good,
            R.drawable.ic_06_blush,
            R.drawable.ic_07_fine,
            R.drawable.ic_08_yawn,
            R.drawable.ic_09_meh,
            R.drawable.ic_10_bored,
            R.drawable.ic_11_sad,
            R.drawable.ic_12_sad_2,
            R.drawable.ic_13_muted,
            R.drawable.ic_14_crying,
            R.drawable.ic_15_ill};
    String[] allMoodsName = {"Awesome", "Cool", "Great", "Happy", "Good", "Blush", "Fine", "Yawn", "Meh", "Bored", "Angry", "Sad", "Muted", "Crying", "Ill"};
    List<String> allmoodsName;

    AdapterEventReview(int id) {
        dayId = id;
        events = DatabaseManager.databaseManager.getEvents(id);

        allmoodsName = Arrays.asList(allMoodsName);

        Log.d("Thouhtslen", events.size() + "");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View reviewView = inflater.inflate(R.layout.item_event, parent, false);
        return new AdapterEventReview.ViewHolder(reviewView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.desc.setText(event.desc);
        holder.impact.setText(event.impact);
        Log.d("MoodE", event.mood);
        int ind = allmoodsName.indexOf(event.mood);
        holder.eve_mood.setImageResource(allMoods[ind]);

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView desc;
        TextView impact;
        ImageView eve_mood;
        View viewForeground;
        View viewBackground;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.event_name);
            impact = itemView.findViewById(R.id.event_impact);
            eve_mood = itemView.findViewById(R.id.event_mood);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}
