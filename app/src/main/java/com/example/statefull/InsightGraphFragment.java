package com.example.statefull;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class InsightGraphFragment extends Fragment {
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph_insight, container, false);
        return view;
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
        chart.getXAxis().setValueFormatter(new InsightGraphFragment.IndexAxisValueFormatter());

        chart.getAxisRight().setDrawLabels(false);
        chart.getLegend().setEnabled(false);
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
        chart.getXAxis().setValueFormatter(new InsightGraphFragment.IndexAxisValueFormatter());

        chart.getAxisRight().setDrawLabels(false);
        chart.getLegend().setEnabled(false);
    }
}