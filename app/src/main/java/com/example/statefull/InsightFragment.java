package com.example.statefull;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class InsightFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insight,container,false);
        ((MindActivity)getActivity()).setActionBarTitle("Insight");
        DecimalFormat formatter = new DecimalFormat("#.##");
        formatter.setRoundingMode(RoundingMode.HALF_UP);

        double num = DatabaseManager.databaseManager.getConfidenceDeviation();
        num = new  Double(formatter.format(num));

        TextView confi = view.findViewById(R.id.level_confidence_devi_value);
        confi.setText(""+num);
        ProgressBar bar = view.findViewById(R.id.progress_confidence);
        bar.setProgress((int)(100*(1-num)));

        TextView satis = view.findViewById(R.id.level_satisfaction_devi_value);
        num = DatabaseManager.databaseManager.getSatisfactionDeviation();
        num = new  Double(formatter.format(num));
        satis.setText(""+num);
        bar = view.findViewById(R.id.progress_satisfaction);
        bar.setProgress((int)(100*(1-num)));

        TextView energy = view.findViewById(R.id.level_energy_devi_value);
        num = DatabaseManager.databaseManager.getEnergyDeviation();
        num = new  Double(formatter.format(num));
        energy.setText(""+num);
        bar = view.findViewById(R.id.progress_energy);
        bar.setProgress((int)(100*(1-num)));

        TextView enthu = view.findViewById(R.id.level_enthusiasm_devi_value);
        num = DatabaseManager.databaseManager.getEnthusiasmDeviation();
        num = new  Double(formatter.format(num));
        enthu.setText(""+num);
        bar = view.findViewById(R.id.progress_enthusiasm);
        bar.setProgress((int)(100*(1-num)));
        List<Integer> list = setGraphData(view);
        TextView thoughttype = view.findViewById(R.id.thoughts_action_count);
        thoughttype.setText(list.get(0)+"");
        thoughttype = view.findViewById(R.id.thoughts_negative_count);
        thoughttype.setText(list.get(1)+"");
        thoughttype = view.findViewById(R.id.thoughts_positive_count);
        thoughttype.setText(list.get(2)+"");
        thoughttype = view.findViewById(R.id.thoughts_ideas_count);
        thoughttype.setText(list.get(3)+"");
        return view;
    }
    List<Integer> setGraphData(View view){
        PieChart chart = view.findViewById(R.id.chart_thought_type);
        List<Integer> list = DatabaseManager.databaseManager.getThoughtTypes();
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(list.get(0),"Action"));
        pieEntries.add(new PieEntry(list.get(1),"Negative"));
        pieEntries.add(new PieEntry(list.get(2),"Positive"));
        pieEntries.add(new PieEntry(list.get(3),"Ideas"));
        PieDataSet dataSet = new PieDataSet(pieEntries,"Data");
        int[] colors = {Color.parseColor("#CD1E90FF"),Color.parseColor("#CDD50000"),Color.parseColor("#CD00C853"),Color.parseColor("#CDAA00FF")};
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        chart.setHoleColor(Color.parseColor("#303030"));
        chart.setData(data);
        chart.getLegend().setTextColor(Color.parseColor("#FFFFFF"));
        return list;
    }
}
