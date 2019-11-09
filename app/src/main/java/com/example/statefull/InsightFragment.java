package com.example.statefull;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class InsightFragment extends Fragment {

    StatsDailyFragment statsDailyFragment;
    StatsFullFragment statsFullFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insight, container, false);
        //View view = inflater.inflate(R.layout.fragment_today, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        setUpViewPager(viewPager);
        ((MindActivity) getActivity()).setActionBarTitle("Today");
        TabLayout tabs = view.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_writing);
        tabs.getTabAt(1).setIcon(R.drawable.ic_pie_chart_black_24dp);
        return view;
    }


    private void setUpViewPager(ViewPager viewPager) {
        InsightFragment.Adapter adapter = new InsightFragment.Adapter(getChildFragmentManager());
        statsDailyFragment = new StatsDailyFragment();
        statsFullFragment = new StatsFullFragment();
        adapter.addFragment(statsFullFragment, "");
        adapter.addFragment(statsDailyFragment, "");
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
