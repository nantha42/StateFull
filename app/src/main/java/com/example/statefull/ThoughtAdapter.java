package com.example.statefull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//E-POJO,G-ViewHolder
interface DataProvider<E, G> {


    void bindData(G holder, int position);

    G createView(View v);

    int getCount();

    void removeItem(int p);
}

public class ThoughtAdapter<G extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<G> {


    public DataProvider mdataProvider;
    private int layout_item_thought;

    ThoughtAdapter(int item_layout, DataProvider dataProvider) {
        layout_item_thought = item_layout;
        mdataProvider = dataProvider;

    }

    @NonNull
    @Override
    public G onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout_item_thought, parent, false);
        return (G) mdataProvider.createView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull G holder, int position) {
        mdataProvider.bindData(holder, position);

    }

    @Override
    public int getItemCount() {
        return mdataProvider.getCount();
    }

    public void removeItem(int position) {
        mdataProvider.removeItem(position);

        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyDataSetChanged();
    }

}
