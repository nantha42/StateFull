package com.example.statefull;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

interface CallAnotherFragment {
    void forEditFragment();

    void forMoodFragment();

}

interface SaveThought {
    void storeThought(String s, int color);
}

interface SaveMood {
    void storeMood(int day_id, int currentMood);
}

public class TodayFragment extends Fragment implements CallAnotherFragment, SaveThought, SaveMood {

    int id = -1;
    ImageButton blue, red, orange, yellow, green, magenta, white;
    int colorButtonValue = -1;
    List<Thought> thoughts = new ArrayList<>();

    ThoughtFragment thoughtFragment;
    AnalysisFragment analysisFragment;

    TodayFragment(int id) {
        this.id = id;
    }

    @Override
    public void storeThought(String s, int colorValue) {

        int day_id = id;
        if (!s.isEmpty()) {
            day_id = DatabaseManager.databaseManager.addToday();
            Log.d("day_id =", Integer.toString(day_id));

            if (day_id != -1) {
                Log.d("Storing:", "Thought");
                DatabaseManager.databaseManager.addThought(day_id, s, colorValue);
                DatabaseManager.databaseManager.justForTest();
                thoughts = DatabaseManager.databaseManager.getThoughts(day_id);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStackImmediate();

            }
        }
    }

    @Override
    public void storeMood(int id, int currentMood) {
        int day_id = id;

        day_id = DatabaseManager.databaseManager.addToday();
        Log.d("day_id =", Integer.toString(day_id));

        if (day_id != -1) {
            Log.d("Storing:", "Mood");
            Calendar calendar = Calendar.getInstance();
            long time = calendar.getTimeInMillis();
            DatabaseManager.databaseManager.moodEntry(time, currentMood, day_id);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStackImmediate();
        }
    }

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        setUpViewPager(viewPager);
        TabLayout tabs = view.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_writing);
        tabs.getTabAt(1).setIcon(R.drawable.ic_pie_chart_black_24dp);

        return view;
    }

    public void forEditFragment() {
        Fragment editThoughtFragment = new EditThoughtFragment(this);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, editThoughtFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void forMoodFragment() {
        Fragment moodThoughtFragment = new MoodFragment(id, this);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, moodThoughtFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void setUpViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        thoughtFragment = new ThoughtFragment(id, this);
        analysisFragment = new AnalysisFragment(id, this);
        adapter.addFragment(thoughtFragment, "");
        adapter.addFragment(analysisFragment, "");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
