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

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    int dayId;
    List<Thought> thoughts;

    ReviewAdapter(int id) {
        dayId = id;
        thoughts = DatabaseManager.databaseManager.getThoughts(id);
        Log.d("Thouhtslen", thoughts.size() + "");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View reviewView = inflater.inflate(R.layout.item_thought, parent, false);
        return new ReviewAdapter.ViewHolder(reviewView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Thought thought = thoughts.get(position);
        holder.body.setText(thought.getText());
        holder.time.setText(thought.getTime());
        if(getColorValue(thought.thoughtColor)!=0)
            holder.ambience.setBackgroundColor(getColorValue(thought.thoughtColor));

    }
    int getColorValue(int tcolor){
        switch(tcolor) {
            case 0:
                return Color.parseColor("#CD1E90FF");
            case 1:
                return Color.parseColor("#CDD50000");
            case 2:
                return Color.parseColor("#CDFF6D00");
            case 3:
                return Color.parseColor("#CDFFD600");
            case 4:
                return Color.parseColor("#CD00C853");
            case 5:
                return Color.parseColor("#CDAA00FF");
            case 6:
                return Color.parseColor("#CDFFFFFF");
            default:
                return 0;
        }

    }
    @Override
    public int getItemCount() {
        return thoughts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout viewForeground;
        TextView body;
        TextView time;
        TextView ambience;

        public ViewHolder(View view) {
            super(view);
            body = view.findViewById(R.id.thoughtbody);
            ambience = view.findViewById(R.id.thoughtambience);
            time = view.findViewById(R.id.timeofthought);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }
}
