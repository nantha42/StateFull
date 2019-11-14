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

public class StatsDailyFragment extends Fragment {

    private class IndexAxisValueFormatter extends ValueFormatter {

        private String[] mValues;

        IndexAxisValueFormatter() {
            mValues = DatabaseManager.databaseManager.getDaysforAxis();
        }

        @Override
        public String getFormattedValue(float value) {
            // "value" represents the position of the label on the axis (x or y)
            Log.d("value", value + "");
            Log.d("mValues", mValues[0]);
            if (value / 10 >= 1) {
                Log.d("ValueFormattr", value / 10 + " " + mValues[((int) value / 10) - 1]);

                return mValues[(int) (value / 10) - 1];
            }
            return value + "";
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_stats_daily, container, false);
        initGraphs(view);
        return view;
    }

    void initGraphs(View view) {
        setAvgGraphs(view, 2, R.id.avg_enthusisasm_graph, DatabaseManager.databaseManager.getAvgEnthusiasmData(), "Enthusiasm");
        setAvgGraphs(view, 0, R.id.avg_confidence_graph, DatabaseManager.databaseManager.getAvgConfidenceData(), "Confidence");
        setAvgGraphs(view, 3, R.id.avg_ambition_graph, DatabaseManager.databaseManager.getAvgAmbitionData(), "Ambition");
        setAvgGraphs(view, 1, R.id.avg_satisfaaction_graph, DatabaseManager.databaseManager.getAvgSatisfactionData(), "Satisfaction");
        setAvgGraphs(view, 4, R.id.avg_energy_graph, DatabaseManager.databaseManager.getAvgEnergyData(), "Energy");
    }

    void setAvgGraphs(View view, int colorId, int id, TreeMap<Integer, Integer> data, String label) {
        LineChart chart = view.findViewById(id);
        //TreeMap<Integer, Integer> data = treedata;
        int color[] = {Color.GREEN, Color.BLUE, Color.CYAN, Color.RED, Color.YELLOW};
        List<Entry> entries = new ArrayList<>();
        for (int k : data.keySet()) {
            entries.add(new Entry(k * 10, data.get(k)));//multiplied by 10 for spacing in graph
        }
        LineDataSet dataSet = new LineDataSet(entries, label);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawFilled(true);
        dataSet.setColor(color[colorId]);
        dataSet.setFillColor(color[colorId]);
        LineData lineData = new LineData();
        lineData.addDataSet(dataSet);
        chart.setData(lineData);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setValueFormatter(new StatsDailyFragment.IndexAxisValueFormatter());

        chart.getAxisRight().setDrawLabels(false);
        chart.getLegend().setEnabled(false);
    }
}

