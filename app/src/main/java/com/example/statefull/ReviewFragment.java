package com.example.statefull;


import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class ReviewFragment extends Fragment {

    int dayId;
    PieChart averageChart;
    BarChart dailyHistory;
    List<String> date;
    ArrayList<PieEntry> averageEntries = new ArrayList<>();
    ArrayList<BarEntry> historyEntries = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    TreeMap<Long, Integer> moods = new TreeMap<>();

    public ReviewFragment(int id) {
        // Required empty public constructor
        dayId = id;
        date = DatabaseManager.databaseManager.getDay(dayId);
    }

    void initPieGraph() {
        Log.d("InitGraph ", "Called");
        averageEntries.clear();
        moods = DatabaseManager.databaseManager.getMoodEntries(dayId);
        TreeMap<Integer, Integer> countofmoods = new TreeMap<Integer, Integer>();
        for (int i = 0; i < 15; i++) {
            countofmoods.put(i, 0);
        }
        List<Integer> timeConvertedMoods = getTimeConvertedMoods();
        int i = 0;
        int total = 0;
        Log.d("CountofMoods",""+countofmoods.keySet().size());
        for (int j=0;j<timeConvertedMoods.size();j++) {
            int x = timeConvertedMoods.get(j);
            if(countofmoods.get(x)!=null) {

                countofmoods.put(x, countofmoods.get(x) + 1);
                total = total + countofmoods.get(x);
            }else{
                Log.d("x is null",""+x);
            }
        }
        initiateNames();
        int[] colors = initiateColors();
        ArrayList<Integer> requiredColors = new ArrayList<>();
        i = 0;
        for (int x : countofmoods.keySet()) {

            float k = countofmoods.get(x);
            Log.d("PieEntry", "X:" + x + " Y:" + k);

            if (k > 0) {
                PieEntry e = new PieEntry(k * 100.0f / total, names.get(i));
                averageEntries.add(e);
                requiredColors.add(colors[i]);
            }
            i++;
        }

        Log.d("Average Entries", "" + averageEntries.size());
        PieDataSet dataSet = new PieDataSet(averageEntries, "Average Moods of the day");
        PieData pieData = new PieData(dataSet);
        dataSet.setColors(requiredColors);
        dataSet.setSliceSpace(2);
        dataSet.setValueTextSize(0);

        averageChart.setData(pieData);
        averageChart.setCenterText("All of your Moods");
        averageChart.setCenterTextColor(Color.BLUE);
        averageChart.setCenterTextSize(20);

        averageChart.animateXY(1000, 1000);
        averageChart.setEntryLabelColor(Color.WHITE);
        //averageChart.setEntryLabelTextSize();
    }

    private int[] initiateColors(){

        TypedArray ta = getContext().getResources().obtainTypedArray(R.array.moodcolors);
        int[] colors = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            colors[i] = ta.getColor(i, 0);
        }
        return colors;
    }
    int getcurtime(){
        Date d = new Date();
        return d.getHours()*6+d.getMinutes()/6;
    }
   List<Integer> getTimeConvertedMoods(){
        moods = DatabaseManager.databaseManager.getMoodEntries(dayId);

        TreeMap<Integer, Integer> time_converted_moods = new TreeMap<>();

        for (long t : moods.keySet()) {
            Date d = new Date(t);
            int x = d.getHours() * 6 + (int) (d.getMinutes() / 6);
            Log.d("Xvalue",x+" "+moods.get(t));
            time_converted_moods.put(x,moods.get(t));
        }

        List<Integer> lists = new ArrayList<Integer>();
        int lastmoodval=0;
        for(int i=0;i<=144;i++){
            if(time_converted_moods.containsKey(i)){
                lastmoodval = 16-time_converted_moods.get(i);
                lists.add(lastmoodval);
            }
            else{
                lists.add(0);
            }
        }
        for(int i=0;i<lists.size();i++){
            Log.d("List elem "+i,""+lists.get(i));
        }
        return lists;
    }
    private void initBarGraph() {
        historyEntries.clear();
        List<Integer> lists = getTimeConvertedMoods();
        for(int i=0;i<lists.size();i++){
            Log.d("List elem"+i,""+lists.get(i));
        }
        for(int i=0;i<lists.size();i++){
            historyEntries.add(new BarEntry(i,lists.get(i)));
        }

        BarDataSet barDataSet = new BarDataSet(historyEntries, "Today Log");
        barDataSet.setValueTextSize(0);
        barDataSet.setColors(Color.rgb(149,117,205));
        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);
        YAxis yaxis = dailyHistory.getAxisLeft();
        yaxis.setTextColor(Color.parseColor("#FFFFFF"));
        YAxis yaxis1 = dailyHistory.getAxisRight();
        XAxis xaxis = dailyHistory.getXAxis();
        xaxis.setTextColor(Color.parseColor("#FFFFFF"));

        yaxis.setAxisMaximum(20);
        yaxis1.setDrawLabels(false);
        dailyHistory.animateY(1500);
        dailyHistory.setData(data);

    }

    private void initiateNames() {
        names.clear();
        names.add("Awesome");
        names.add("Cool");
        names.add("Great");
        names.add("Happy");
        names.add("Good");
        names.add("Blush");
        names.add("Fine");
        names.add("Yawn");
        names.add("Meh");
        names.add("Bored");
        names.add("Angry");
        names.add("Sad");
        names.add("Muted");
        names.add("Crying");
        names.add("Ill");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rvreview);
        ReviewAdapter adapter = new ReviewAdapter(dayId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        averageChart = view.findViewById(R.id.rvpiecharttoday);
        dailyHistory = view.findViewById(R.id.rvbarcharttoday);
        TextView rdate = view.findViewById(R.id.reviewdate);
        TextView rmonth = view.findViewById(R.id.reviewmonth);
        rdate.setText(date.get(0));
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        rmonth.setText(months[Integer.parseInt(date.get(1))]);
        initPieGraph();
        initBarGraph();
        return view;
    }
}
