package com.example.statefull;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view =  inflater.inflate(R.layout.fragment_about, container, false);
        TextView tv = view.findViewById(R.id.textView6);
        tv.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }
}
