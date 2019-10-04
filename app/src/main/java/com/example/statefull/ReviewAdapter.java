package com.example.statefull;

import android.content.Context;
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
