package com.example.statefull;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MoodFragment extends Fragment {
    private static final String DEBUG_TAG = "Gestures";
    int day_id;
    SaveMood saver;
    View view;
    int[] allMoods = {R.drawable.ic_01_awesome,
            R.drawable.ic_02_cool,
            R.drawable.ic_03_great,
            R.drawable.ic_04_happy,
            R.drawable.ic_05_good,
            R.drawable.ic_06_blush,
            R.drawable.ic_07_fine,
            R.drawable.ic_08_yawn,
            R.drawable.ic_09_meh,
            R.drawable.ic_10_bored,
            R.drawable.ic_11_sad,
            R.drawable.ic_12_sad_2,
            R.drawable.ic_13_muted,
            R.drawable.ic_14_crying,
            R.drawable.ic_15_ill};
    String[] allMoodsName = {"Awesome", "Cool", "Great", "Happy", "Good", "Blush", "Fine", "Yawn", "Meh", "Bored", "Angry", "Sad", "Muted", "Crying", "Ill"};
    int currentmood = 5;
    private GestureDetectorCompat mDetector;
    private GestureDetector gDetector;

    MoodFragment(int day_id, SaveMood mSaver) {
        this.day_id = day_id;
        saver = mSaver;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mood, container, false);

        gDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                Log.d("Gesture", "Down");
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.d("Gesture", "Scrolling " + distanceX + " " + distanceY);
                if (Math.abs(distanceY) > 10) {
                    if (distanceY < 0) {
                        if (currentmood < 14) {
                            currentmood++;
                            changeImage();
                        }
                    } else {
                        if (currentmood > 0) {
                            currentmood--;
                            changeImage();
                        }
                    }
                }
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                Log.i("Gesture", "onFling has been called!");
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        FloatingActionButton fabdone = view.findViewById(R.id.fab_add_mood_done);
        fabdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saver.storeMood(day_id, currentmood);
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gDetector.onTouchEvent(event);
            }
        });
        return view;
    }

    void changeImage() {
        ImageView mood = view.findViewById(R.id.mood_exibhitor);
        mood.setImageResource(allMoods[currentmood]);
        TextView mood_name = view.findViewById(R.id.mood_name);
        mood_name.setText(allMoodsName[currentmood]);

    }

}