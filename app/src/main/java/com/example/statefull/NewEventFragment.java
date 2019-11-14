package com.example.statefull;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewEventFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    SeekBar seekbar;
    int current_mood_i = 5;
    String current_mood = "Happy";

    String[] moods = {"Awesome", "Cool", "Great", "Happy", "Good", "Blush", "Fine", "Yawn", "Meh", "Bored", "Angry", "Sad", "Muted", "Crying", "Ill"};
    TextView mood_name;
    ImageView mood_image;
    EditText event_desc;
    EditText event_impact;
    FloatingActionButton done;
    int[] allMoods = {R.drawable.ic_01_awesome, R.drawable.ic_02_cool, R.drawable.ic_03_great, R.drawable.ic_04_happy,
            R.drawable.ic_05_good, R.drawable.ic_06_blush, R.drawable.ic_07_fine, R.drawable.ic_08_yawn,
            R.drawable.ic_09_meh, R.drawable.ic_10_bored, R.drawable.ic_11_sad, R.drawable.ic_12_sad_2, R.drawable.ic_13_muted,
            R.drawable.ic_14_crying, R.drawable.ic_15_ill};
    int day_id;

    NewEventFragment(int day_id) {
        this.day_id = day_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_event, container, false);
        mood_name = view.findViewById(R.id.mood_name);
        mood_image = view.findViewById(R.id.mood_image);
        seekbar = view.findViewById(R.id.mood_control);
        seekbar.setOnSeekBarChangeListener(this);
        done = view.findViewById(R.id.fab_done);
        event_desc = view.findViewById(R.id.edit_text_desc);
        event_impact = view.findViewById(R.id.edit_text_impact);
        DatabaseManager.databaseManager.displayEvents();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desc = event_desc.getText().toString();
                String impact = event_impact.getText().toString();
                if (!desc.equals("") && !impact.equals("")) {
                    Log.d("Db", DatabaseManager.DATABASE_VERSION + "");
                    DatabaseManager.databaseManager.addEvent(desc, impact, current_mood);
                    DatabaseManager.databaseManager.displayEvents();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.popBackStackImmediate();
                }


            }
        });
        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int ia = seekbar.getProgress();
        current_mood_i = ia / 7;
        current_mood = moods[14 - current_mood_i];
        mood_name.setText(current_mood);
        mood_image.setImageResource(allMoods[14 - current_mood_i]);
        Log.d("Seekvalue", ia / 7 + "");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
