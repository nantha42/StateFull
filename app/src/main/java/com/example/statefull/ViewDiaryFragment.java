package com.example.statefull;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewDiaryFragment extends Fragment implements itemClickListener {

    RecyclerView rvjournal;
    AdapterDiary adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        ((MindActivity)getActivity()).setActionBarTitle("Journal");
        rvjournal = view.findViewById(R.id.rvjournal);
        adapter = new AdapterDiary(this);
        rvjournal.setAdapter(adapter);
        rvjournal.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvjournal.setItemAnimator(new DefaultItemAnimator());
        rvjournal.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onNoteClick(int id) {
        Log.d("CLided:", id + "");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Log.d("Fragments", fragmentTransaction.toString());
        fragmentTransaction.replace(R.id.fragment_container, new ReviewFragment(id));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}