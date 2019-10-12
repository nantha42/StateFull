package com.example.statefull;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ThoughtFragment extends Fragment implements View.OnClickListener/*, EditThoughtFragment.SaveThought*/, DataProvider<Thought, ThoughtFragment.ThoughtViewHolder>, RecyclerThoughtItemTouchHelper.RecyclerItemTouchHelperListener {

    List<Thought> thoughts;
    CallAnotherFragment mCaller;
    ThoughtAdapter<ThoughtViewHolder> thoughtAdapter = new ThoughtAdapter<>(R.layout.item_thought, this);
    int day_id;

    ThoughtFragment() {

    }

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setRetainInstance(true);
    }

    ThoughtFragment(int day_id, CallAnotherFragment caller) {
        if (day_id == -1)
            this.day_id = DatabaseManager.databaseManager.getToDay(day_id);
        mCaller = caller;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ThoughtViewHolder) {
            thoughtAdapter.removeItem(viewHolder.getAdapterPosition());
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup root, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_thought, root, false);
        //DayData daydata = DatabaseManager.databaseManager.getTodayData();
        ImageView addthought = view.findViewById(R.id.add_thought);
        addthought.setOnClickListener(this);
        FloatingActionButton add_mood = view.findViewById(R.id.fab_add_mood);
        add_mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCaller.forMoodFragment();
            }
        });
        thoughts = DatabaseManager.databaseManager.getThoughts(day_id);
        RecyclerView rvthoughts = view.findViewById(R.id.rvthoughtstores);

        rvthoughts.setAdapter(thoughtAdapter);
        rvthoughts.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvthoughts.setItemAnimator(new DefaultItemAnimator());

        rvthoughts.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerThoughtItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvthoughts);
        return view;

    }

    @Override
    public void onClick(View view) {
        //Code for editText dialog
        mCaller.forEditFragment();
    }

    /*
    @Override
    public void storeThought(String s,int colorValue) {
        if(!s.isEmpty()) {
            day_id = DatabaseManager.databaseManager.addToday();
            Log.d("day_id =",Integer.toString(day_id));

            if ( day_id != -1) {

                DatabaseManager.databaseManager.addThought(day_id, s,colorValue);
                DatabaseManager.databaseManager.justForTest();
                thoughts = DatabaseManager.databaseManager.getThoughts(day_id);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStackImmediate();

            }
        }
    }
    */
    @Override
    public void bindData(ThoughtViewHolder holder, int position) {
        holder.body.setText(thoughts.get(position).getText());
        holder.time.setText(thoughts.get(position).getTime());
        int[] colorId = {R.color.t_color_blue, R.color.t_color_red, R.color.t_color_orange, R.color.t_color_yellow, R.color.t_color_green, R.color.t_color_magenta, R.color.t_color_white};
        Log.d("ThoughtColor", "" + thoughts.get(position).thoughtColor);
        if (thoughts.get(position).thoughtColor != -1) {
            holder.ambience.setBackground(getResources().getDrawable(colorId[thoughts.get(position).thoughtColor]));
            holder.ambience.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public ThoughtViewHolder createView(View v) {
        return new ThoughtViewHolder(v);
    }

    @Override
    public int getCount() {
        return DatabaseManager.databaseManager.getThoughtsCount(day_id);
    }

    @Override
    public void removeItem(int p) {
        int id = thoughts.get(p).i;
        DatabaseManager.databaseManager.removeThought(id);
        thoughts = DatabaseManager.databaseManager.getThoughts(day_id);

    }

    public class ThoughtViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout viewBackground, viewForeground;
        TextView body;
        TextView time;
        TextView ambience;

        ThoughtViewHolder(View view) {
            super(view);
            body = view.findViewById(R.id.thoughtbody);
            ambience = view.findViewById(R.id.thoughtambience);
            time = view.findViewById(R.id.timeofthought);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }
}
