package com.example.statefull;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

interface itemClickListener {
    void onNoteClick(int id);

}

public class AdapterDiary extends RecyclerView.Adapter<AdapterDiary.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int id;
        TextView day;
        TextView month;
        TextView thought;
        PieChart graph;
        TextView charttitle;
        RelativeLayout layout;
        itemClickListener listener;

        public ViewHolder(@NonNull View itemView, itemClickListener listener) {
            super(itemView);
            layout = (RelativeLayout) itemView;
            layout.setOnClickListener(this);
            day = itemView.findViewById(R.id.day);
            thought = itemView.findViewById(R.id.daytext);
            month = itemView.findViewById(R.id.month);
            graph = itemView.findViewById(R.id.happynesschart);
            charttitle = itemView.findViewById(R.id.happycharttitle);
            this.listener = listener;
        }

        public void setMonth(String n) {
            String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            month.setText(months[Integer.parseInt(n)]);
        }

        @Override
        public void onClick(View view) {
            Log.d("Clickd ", "" + id);
            listener.onNoteClick(id);

        }
    }

    List<DayData> dayData;
    itemClickListener mitemClickListener;

    public AdapterDiary(itemClickListener listener) {
        dayData = DatabaseManager.databaseManager.getDays();
        mitemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View dayView = inflater.inflate(R.layout.item_day, parent, false);
        AdapterDiary.ViewHolder viewHolder = new AdapterDiary.ViewHolder(dayView, mitemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayData day = dayData.get(position);
        String[] components = day.date.split(" ");
        holder.id = day.id;
        if (components[0].length() < 2) {
            holder.day.setText("0" + components[0]);
        } else {
            holder.day.setText(components[0]);
        }
        String ths = DatabaseManager.databaseManager.getLatestThought(day.id);
        if (ths.length() > 100)
            holder.thought.setText(ths.substring(0, 80) + "...");
        else
            holder.thought.setText(ths);
        holder.setMonth(components[1]);

        float positivitypercentage = DatabaseManager.databaseManager.getDayMiniReport(day.id);
        if (positivitypercentage > 0) {
            Log.d("Positivity", "" + positivitypercentage);
            List<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(positivitypercentage * 100, ""));
            entries.add(new PieEntry(100 - positivitypercentage * 100, ""));
            PieDataSet dataset = new PieDataSet(entries, "");
            List<Integer> colors = new ArrayList<>();
            colors.add(Color.parseColor("#00C853"));
            colors.add(Color.GRAY);
            dataset.setColors(colors);
            PieData data = new PieData(dataset);
            holder.graph.setData(data);
            holder.graph.animateXY(1000, 1000);
            holder.graph.getLegend().setEnabled(false);
            holder.graph.getDescription().setEnabled(false);
            holder.graph.setHoleColor(Color.parseColor("#424242"));
        } else {
            holder.graph.setVisibility(View.GONE);
            holder.charttitle.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        int x = DatabaseManager.databaseManager.getDayTableSize();
        Log.d("DayItemCount", Integer.toString(x));
        return x;
    }

}