package com.example.statefull;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEventsFragment extends Fragment {
    CallAnotherFragment mCaller;

    AddEventsFragment(CallAnotherFragment mCaller) {
        this.mCaller = mCaller;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_events, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab_add_event);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCaller.forNewEventFragment();
            }
        });
        return view;
    }
}
