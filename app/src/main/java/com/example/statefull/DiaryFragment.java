package com.example.statefull;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class DiaryFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View view = inflater.inflate(R.layout.fragment_mydiary, container, false);
        //RecyclerView rv = view.findViewById(R.id.rvNotes);
        //NotesAdapter notesAdapter = new NotesAdapter();
        return view;
    }
}
