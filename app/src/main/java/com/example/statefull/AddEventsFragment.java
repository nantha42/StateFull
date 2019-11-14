package com.example.statefull;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddEventsFragment extends Fragment implements RecyclerEventItemTouchHelper.RecyclerItemTouchHelperListener {
    CallAnotherFragment mCaller;
    int day_id;

    AddEventsFragment(CallAnotherFragment mCaller, int day_id) {
        this.day_id = day_id;
        this.mCaller = mCaller;
    }

    AdapterEvent adapterEvent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_events, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvevent);
        day_id = DatabaseManager.databaseManager.getToDay(-1);
        ArrayList<Event> events = DatabaseManager.databaseManager.getEvents(day_id);
        Log.d("EventsLen", events.size() + "");
        adapterEvent = new AdapterEvent(events, day_id);
        recyclerView.setAdapter(adapterEvent);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerEventItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        FloatingActionButton fab = view.findViewById(R.id.fab_add_event);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCaller.forNewEventFragment();
            }
        });
        return view;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof AdapterEvent.ViewHolder) {
            adapterEvent.removeItem(viewHolder.getAdapterPosition());
        }
    }
}
