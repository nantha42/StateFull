package com.example.statefull;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
interface DetailSaver{
    void saveDetails(PersonalityParams p);
}
public class MoodFragment extends Fragment implements DetailSaver, View.OnClickListener {
    private static final String DEBUG_TAG = "Gestures";
    int day_id;
    SaveMood saver;
    View view;
    PersonalityParams pParams = null;

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
    int currentmood;
    private GestureDetectorCompat mDetector;
    private GestureDetector gDetector;
    MindActivity mindActivity;
    MoodFragment(int day_id, SaveMood mSaver) {
        this.day_id = day_id;
        saver = mSaver;
    }

    @NonNull
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentMood", currentmood);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
            currentmood = savedInstanceState.getInt("currentmood");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mood, container, false);
        mindActivity = (MindActivity) getActivity();
        currentmood = mindActivity.currentmood;
        changeImage();
        Log.d("CreatedAgain", "Again");
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
                            mindActivity.currentmood = currentmood;
                        }
                    } else {
                        if (currentmood > 0) {
                            currentmood--;
                            changeImage();
                            mindActivity.currentmood = currentmood;
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
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        Button addDetail = view.findViewById(R.id.more_detail);
        addDetail.setOnClickListener(this);
        FloatingActionButton fabdone = view.findViewById(R.id.fab_add_mood_done);
        fabdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saver.storeMood(day_id, currentmood,pParams);
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

    @Override
    public void saveDetails(PersonalityParams params) {
        this.pParams = params;
    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Log.d("Fragments", fragmentTransaction.toString());

        fragmentTransaction.replace(R.id.fragment_container, new AddDetailsFragment(this,pParams));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
class PersonalityParams{
    double confidence = .3;
    double satisfaction = .3;
    double enthusiasm = .3;
    double ambition = .3;
    double energy = .3;

    PersonalityParams(double s,double c,double e,double a,double en){
        confidence =c;
        satisfaction = s;
        enthusiasm = e;
        energy =en;
        ambition=a;
    }
}