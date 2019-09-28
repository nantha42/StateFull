package com.example.statefull;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditThoughtFragment extends Fragment implements View.OnClickListener {


    SaveThought saver;
    ImageButton blue, red, orange, yellow, green, magenta, white;
    int colorButtonValue = -1;
    private EditText editText;
    private FloatingActionButton floatingActionButton;

    EditThoughtFragment(SaveThought saveThought) {
        saver = saveThought;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup root, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_edit_thought, root, false);
        editText = view.findViewById(R.id.thoughtbody);
        floatingActionButton = view.findViewById(R.id.fab_done);
        floatingActionButton.setOnClickListener(this);
        blue = view.findViewById(R.id.blue);
        red = view.findViewById(R.id.red);
        orange = view.findViewById(R.id.orange);
        yellow = view.findViewById(R.id.yellow);
        green = view.findViewById(R.id.green);
        white = view.findViewById(R.id.white);
        magenta = view.findViewById(R.id.magenta);

        blue.setOnClickListener(this);
        red.setOnClickListener(this);
        orange.setOnClickListener(this);
        magenta.setOnClickListener(this);
        green.setOnClickListener(this);
        white.setOnClickListener(this);
        blue.setOnClickListener(this);
        yellow.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.fab_done) {
            Log.d("Clicked", "Submit");
            String s = editText.getText().toString();
            Log.d("Clicked", "Submit String = " + s);
            saver.storeThought(s, colorButtonValue);
        } else {
            switch (view.getId()) {
                case R.id.blue:
                    colorButtonValue = 0;
                    blue.setImageResource(R.drawable.ic_done_black_24dp);
                    removeExceptImageResource(blue);
                    break;
                case R.id.red:
                    colorButtonValue = 1;
                    red.setImageResource(R.drawable.ic_done_black_24dp);
                    removeExceptImageResource(red);
                    break;
                case R.id.orange:
                    colorButtonValue = 2;
                    orange.setImageResource(R.drawable.ic_done_black_24dp);
                    removeExceptImageResource(orange);
                    break;
                case R.id.yellow:
                    colorButtonValue = 3;
                    yellow.setImageResource(R.drawable.ic_done_black_24dp);
                    removeExceptImageResource(yellow);
                    break;
                case R.id.green:
                    colorButtonValue = 4;
                    green.setImageResource(R.drawable.ic_done_black_24dp);
                    removeExceptImageResource(green);
                    break;
                case R.id.magenta:
                    colorButtonValue = 5;
                    magenta.setImageResource(R.drawable.ic_done_black_24dp);
                    removeExceptImageResource(magenta);
                    break;
                case R.id.white:
                    colorButtonValue = 6;
                    white.setImageResource(R.drawable.ic_done_black_24dp);
                    removeExceptImageResource(white);
                    break;
                default:
                    colorButtonValue = -1;

            }

        }
    }

    private void removeExceptImageResource(ImageButton button) {
        if (button.getId() != blue.getId())
            blue.setImageResource(0);
        if (button.getId() != red.getId())
            red.setImageResource(0);
        if (button.getId() != orange.getId())
            orange.setImageResource(0);
        if (button.getId() != yellow.getId())
            yellow.setImageResource(0);
        if (button.getId() != green.getId())
            green.setImageResource(0);
        if (button.getId() != magenta.getId())
            magenta.setImageResource(0);
        if (button.getId() != white.getId())
            white.setImageResource(0);
    }

}