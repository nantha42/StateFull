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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class AnalysisFragment extends Fragment {
    int day_id;
    PieChart averageChart;
    BarChart dailyHistory;
    LineChart lineChart;
    CallAnotherFragment mCaller;

    TreeMap<Long, Integer> moods = new TreeMap<>();
    ArrayList<PieEntry> averageEntries = new ArrayList<>();
    ArrayList<BarEntry> historyEntries = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();


    AnalysisFragment(int id, CallAnotherFragment caller) {
        if (id == -1)
            this.day_id = DatabaseManager.databaseManager.getToDay(day_id);
        else
            this.day_id = id;
        mCaller = caller;
    }
    AnalysisFragment(){
        this.day_id=DatabaseManager.databaseManager.getToDay(-1);

    }

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setRetainInstance(true);
    }
    void initPieGraph() {
        Log.d("InitGraph ", "Called");
        averageEntries.clear();
        moods = DatabaseManager.databaseManager.getMoodEntries(day_id);
        TreeMap<Integer, Integer> time_converted_moods = new TreeMap<>();
        for (long t : moods.keySet()) {
            Date d = new Date(t);
            int x = d.getHours() * 6 + (int) (d.getMinutes() / 6);
            if(moods.get(t)!=0) {
                time_converted_moods.put(x, moods.get(t));
            }
        }

        int curperiod = getcurtime();
        int lastmoodval = 0;
        for(int i=0;i<=curperiod;i++){
            if(time_converted_moods.containsKey(i)){
                lastmoodval = 16-time_converted_moods.get(i);
                historyEntries.add(new BarEntry(i,16-time_converted_moods.get(i)));
            }
            else{
                historyEntries.add(new BarEntry(i,0));
            }
        }
        TreeMap<Integer, Integer> countofmoods = new TreeMap<Integer, Integer>();
        for (int i = 0; i < 15; i++) {
            countofmoods.put(i, 0);
        }
        int i = 0;
        int total = 0;
        for (long x : moods.keySet()) {
            int j = moods.get(x);
            Log.d("Values", x + " " + j);
            countofmoods.put(j, countofmoods.get(j) + 1);
            total = total + countofmoods.get(j);
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
        averageChart.setCenterText("Today Average");
        averageChart.setCenterTextColor(Color.BLUE);
        averageChart.setCenterTextSize(20);
        averageChart.getLegend().setTextColor(Color.WHITE);
        averageChart.animateY(500);
        averageChart.setEntryLabelColor(Color.BLACK);

        //averageChart.setEntryLabelTextSize();
    }

    private int[] initiateColors() {

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

    private void initBarGraph(View view) {
        historyEntries.clear();
        moods = DatabaseManager.databaseManager.getMoodEntries(day_id);
        dailyHistory.setVisibility(View.VISIBLE);
        if (moods.size() != 0) {
            Log.d("EmptyData", moods.size() + " ");
            ArrayList<Integer> requiredColors = new ArrayList<>();

            TreeMap<Integer, Integer> time_converted_moods = new TreeMap<>();
            for (long t : moods.keySet()) {
                Date d = new Date(t);
                int x = d.getHours() * 6 + (int) (d.getMinutes() / 6);
                time_converted_moods.put(x, moods.get(t));
            }

            int curperiod = getcurtime();
            int lastmoodval = 0;
            for (int i = 0; i <= curperiod; i++) {
                if (time_converted_moods.containsKey(i)) {

                    if (time_converted_moods.get(i) != 0) {
                        lastmoodval = 16 - time_converted_moods.get(i);
                        historyEntries.add(new BarEntry(i, 16 - time_converted_moods.get(i)));
                    }
                } else {
                    historyEntries.add(new BarEntry(i, 0));
                }
            }

            int i = 0;
            int[] colors = initiateColors();
        /*for (Long k : moods.keySet()) {
            //requiredColors.add(colors[moods.get(k)]);
            //requiredColors.add(getContext().getResources().obtainTypedArray(R.array.moodcolors).getColor());
            historyEntries.add(new BarEntry(k, 16 - moods.get(k)));
            i++;
        }*/

            BarDataSet barDataSet = new BarDataSet(historyEntries, "Today Log");
            barDataSet.setValueTextSize(0);
            barDataSet.setColor(Color.rgb(149, 117, 205));
            BarData data = new BarData(barDataSet);
            data.setBarWidth(0.9f);
            //XAxis xaxis = dailyHistory.getXAxis();
            //xaxis.setValueFormatter(new valueFormatter());

            YAxis yaxis = dailyHistory.getAxisLeft();
            YAxis yaxis1 = dailyHistory.getAxisRight();
            dailyHistory.getXAxis().setTextColor(Color.WHITE);

            yaxis.setDrawGridLinesBehindData(false);
            yaxis.setDrawGridLines(false);
            yaxis.setTextColor(Color.WHITE);
            yaxis.setAxisMaximum(20);
            yaxis1.setDrawLabels(false);
            dailyHistory.getLegend().setTextColor(Color.WHITE);
            dailyHistory.animateY(1500);
            dailyHistory.setData(data);
        } else {
            dailyHistory.setVisibility(View.GONE);
            TextView barChartTextView = view.findViewById(R.id.barchartText);
            barChartTextView.setVisibility(View.GONE);
        }
    }

    void initLineGraph(View view) {
        List<List<Integer>> data = DatabaseManager.databaseManager.getDatForCharacteristics(day_id);
        List<Entry> confidence = new ArrayList<>();
        List<Entry> satisfaction = new ArrayList<>();
        List<Entry> ambition =  new ArrayList<>();
        List<Entry> energy  = new ArrayList<>();
        List<Entry> enthusiasm = new ArrayList<>();
        lineChart.setVisibility(View.VISIBLE);
        TextView textView = view.findViewById(R.id.no_data_text);
        textView.setVisibility(View.GONE);
        if (data.size() != 0) {
            Log.d("EmptyData", data.size() + " ");
            for (int i = 0; i < data.size(); i++) {
                confidence.add(new Entry(i * 5, data.get(i).get(0)));
                satisfaction.add(new Entry(i * 5, data.get(i).get(1)));
                enthusiasm.add(new Entry(i * 5, data.get(i).get(2)));
                ambition.add(new Entry(i * 5, data.get(i).get(3)));
                energy.add(new Entry(i * 5, data.get(i).get(4)));
            }
            LineDataSet conf_dataset = new LineDataSet(confidence, "Confidence");
            conf_dataset.setColor(Color.GREEN);
            conf_dataset.setCircleColor(Color.GREEN);
            conf_dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            LineDataSet sats_dataset = new LineDataSet(satisfaction, "Satisfaction");
            sats_dataset.setColor(Color.BLUE);
            sats_dataset.setCircleColor(Color.BLUE);
            sats_dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            LineDataSet enth_dataset = new LineDataSet(enthusiasm, "Enthusiasm");
            enth_dataset.setColor(Color.CYAN);
            //enth_dataset.setDrawFilled(true);
            enth_dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            enth_dataset.setColor(Color.CYAN);
            enth_dataset.setCircleColor(Color.CYAN);
            enth_dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            LineDataSet amb_dataset = new LineDataSet(ambition, "Ambition");
            amb_dataset.setColor(Color.RED);
            amb_dataset.setCircleColor(Color.RED);
            //amb_dataset.setDrawFilled(true);
            amb_dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            amb_dataset.setFillColor(Color.RED);
            LineDataSet ene_dataset = new LineDataSet(energy, "Energy");
            ene_dataset.setColor(Color.YELLOW);
            ene_dataset.setCircleColor(Color.YELLOW);
            //ene_dataset.setDrawFilled(true);
            ene_dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            ene_dataset.setFillColor(Color.YELLOW);

            LineData linedata = new LineData();
            linedata.addDataSet(conf_dataset);
            linedata.addDataSet(sats_dataset);
            linedata.addDataSet(enth_dataset);
            linedata.addDataSet(amb_dataset);
            linedata.addDataSet(ene_dataset);
            lineChart.setData(linedata);
            lineChart.getLegend().setTextColor(Color.WHITE);
            lineChart.getXAxis().setTextColor(Color.WHITE);
            lineChart.getAxisLeft().setTextColor(Color.WHITE);

            lineChart.getAxisRight().setDrawLabels(false);
        } else {
            lineChart.setVisibility(View.GONE);
            TextView lineChartTextView = view.findViewById(R.id.linechart_text);
            lineChartTextView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);
        //averageChart = view.findViewById(R.id.piecharttoday);
        dailyHistory = view.findViewById(R.id.barcharttoday);
        lineChart = view.findViewById(R.id.linechart_characteristics);
//        this.initPieGraph();
        this.initBarGraph(view);
        this.initLineGraph(view);
        return view;
    }

    /*
    class valueFormatter extends ValueFormatter{
        private SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm");
        int count =0;
        @Override
        public String getFormattedValue(float value) {
            Date  date = new Date((long)value);
            Log.d("formatted",""+ mFormat.format(date));
            return mFormat.format(date);
        }
    }*/
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

}