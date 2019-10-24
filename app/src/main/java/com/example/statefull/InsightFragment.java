package com.example.statefull;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


public class InsightFragment extends Fragment {

    private class IndexAxisValueFormatter extends ValueFormatter {

        private String[] mValues;

        public IndexAxisValueFormatter() {
            mValues = DatabaseManager.databaseManager.getDaysforAxis();
        }

        @Override
        public String getFormattedValue(float value) {
            // "value" represents the position of the label on the axis (x or y)
            Log.d("ValueFormattr", value / 10 + " " + mValues[((int) value / 10) - 1]);
            return mValues[(int) (value / 10) - 1];
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insight, container, false);
        ((MindActivity) getActivity()).setActionBarTitle("Insight");
        DecimalFormat formatter = new DecimalFormat("#.##");
        formatter.setRoundingMode(RoundingMode.HALF_UP);

        double num = DatabaseManager.databaseManager.getConfidenceDeviation();
        try {
            num = new Double(formatter.format(num));
        } catch (Exception e) {

        }

        TextView confi = view.findViewById(R.id.level_confidence_devi_value);
        confi.setText("" + num);
        ProgressBar bar = view.findViewById(R.id.progress_confidence);
        bar.setProgress((int) (100 * (num)));

        TextView satis = view.findViewById(R.id.level_satisfaction_devi_value);
        num = DatabaseManager.databaseManager.getSatisfactionDeviation();

        try {
            num = new Double(formatter.format(num));
        } catch (Exception e) {

        }

        satis.setText("" + num);
        bar = view.findViewById(R.id.progress_satisfaction);
        bar.setProgress((int) (100 * (num)));

        TextView energy = view.findViewById(R.id.level_energy_devi_value);
        num = DatabaseManager.databaseManager.getEnergyDeviation();
        try {
            num = new Double(formatter.format(num));
        } catch (Exception e) {

        }

        energy.setText("" + num);
        bar = view.findViewById(R.id.progress_energy);
        bar.setProgress((int) (100 * (num)));

        TextView enthu = view.findViewById(R.id.level_enthusiasm_devi_value);
        num = DatabaseManager.databaseManager.getEnthusiasmAverage();
        try {
            num = new Double(formatter.format(num));
        } catch (Exception e) {

        }

        enthu.setText("" + num);
        bar = view.findViewById(R.id.progress_enthusiasm);
        bar.setProgress((int) (100 * (num)));
        List<Integer> list = setPieGraphData(view);
        TextView thoughttype = view.findViewById(R.id.thoughts_action_count);
        thoughttype.setText(list.get(0) + "");
        thoughttype = view.findViewById(R.id.thoughts_negative_count);
        thoughttype.setText(list.get(1) + "");
        thoughttype = view.findViewById(R.id.thoughts_positive_count);
        thoughttype.setText(list.get(2) + "");
        thoughttype = view.findViewById(R.id.thoughts_ideas_count);
        thoughttype.setText(list.get(3) + "");
        //setCharacteristicsData(view);
        setAvgConfidenceGraph(view);
        setAvgEnthusiasmGraph(view);
        return view;
    }

    void filterData(List<List<Long>> data) {
        boolean broken = true;
        while (broken) {
            broken = false;
            for (int i = 1; i < data.get(0).size(); i++) {
                long diff = (data.get(data.size() - 1).get(i) - data.get(data.size() - 1).get(i - 1)) / (1000);
                if (diff < 5600) {
                    for (int j = 0; j < data.size(); j++) {
                        data.get(j).remove(i);
                    }
                    broken = true;
                    break;
                }
            }
        }
    }

    void setAvgConfidenceGraph(View view) {
        LineChart chart = view.findViewById(R.id.avg_confidence_graph);
        TreeMap<Integer, Integer> data = DatabaseManager.databaseManager.getAvgConfidenceData();
        int color[] = {Color.GREEN, Color.BLUE, Color.CYAN, Color.RED, Color.YELLOW};
        List<Entry> entries = new ArrayList<>();
        for (int k : data.keySet()) {
            entries.add(new Entry(k * 10, data.get(k)));
        }
        LineDataSet dataSet = new LineDataSet(entries, "confidence");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawFilled(true);
        dataSet.setColor(color[0]);
        dataSet.setFillColor(color[0]);
        LineData lineData = new LineData();
        lineData.addDataSet(dataSet);
        chart.setData(lineData);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter());

        chart.getAxisRight().setDrawLabels(false);
        chart.getLegend().setEnabled(false);
    }

    void setAvgEnthusiasmGraph(View view) {
        LineChart chart = view.findViewById(R.id.avg_enthusisasm_graph);
        TreeMap<Integer, Integer> data = DatabaseManager.databaseManager.getAvgEnthusiasmData();
        int color[] = {Color.GREEN, Color.BLUE, Color.CYAN, Color.RED, Color.YELLOW};
        List<Entry> entries = new ArrayList<>();
        for (int k : data.keySet()) {
            entries.add(new Entry(k * 10, data.get(k)));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Enthusiasm");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawFilled(true);
        dataSet.setColor(color[2]);
        dataSet.setFillColor(color[2]);
        LineData lineData = new LineData();
        lineData.addDataSet(dataSet);
        chart.setData(lineData);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter());

        chart.getAxisRight().setDrawLabels(false);
        chart.getLegend().setEnabled(false);
    }

    List<Integer> setPieGraphData(View view) {
        PieChart chart = view.findViewById(R.id.chart_thought_type);
        List<Integer> list = DatabaseManager.databaseManager.getThoughtTypes();
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(list.get(0), "Action"));
        pieEntries.add(new PieEntry(list.get(1), "Negative"));
        pieEntries.add(new PieEntry(list.get(2), "Positive"));
        pieEntries.add(new PieEntry(list.get(3), "Ideas"));
        PieDataSet dataSet = new PieDataSet(pieEntries, "Data");
        int[] colors = {Color.parseColor("#CD1E90FF"), Color.parseColor("#CDD50000"), Color.parseColor("#CD00C853"), Color.parseColor("#CDAA00FF")};
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        chart.setHoleColor(Color.parseColor("#303030"));
        chart.setData(data);
        chart.getLegend().setTextColor(Color.parseColor("#FFFFFF"));
        return list;
    }
}
