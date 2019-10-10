package com.example.statefull;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class AddDetailsFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    DetailSaver detailSaver;
    View view;
    PersonalityParams params = null;

    AddDetailsFragment(DetailSaver detailSaver, PersonalityParams p) {
        this.detailSaver = detailSaver;
        if (p != null) {
            params = p;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adddetails, container, false);
        FloatingActionButton fab = view.findViewById(R.id.details_done);
        fab.setOnClickListener(this);
        this.view = view;
        SeekBar satisfaction = view.findViewById(R.id.seek_satisfaction);
        SeekBar ambitious = view.findViewById(R.id.seek_ambitious);
        SeekBar confidence = view.findViewById(R.id.seek_confidence);
        SeekBar energy = view.findViewById(R.id.seek_energy);
        SeekBar enthusiasm = view.findViewById(R.id.seek_enthusiasm);
        List<SeekBar> seekBarList = new ArrayList<>();
        seekBarList.add(satisfaction);
        seekBarList.add(ambitious);
        seekBarList.add(confidence);
        seekBarList.add(energy);
        seekBarList.add(enthusiasm);
        for(SeekBar x:seekBarList){
            x.setOnSeekBarChangeListener(this);
        }

        if (params != null) {
            satisfaction.setProgress((int) params.satisfaction);
            ambitious.setProgress((int) params.ambition);
            confidence.setProgress((int) params.confidence);
            enthusiasm.setProgress((int) params.enthusiasm);
            energy.setProgress((int) params.energy);

        }
        return view;
    }

    @Override
    public void onClick(View view) {
        SeekBar satisfaction = this.view.findViewById(R.id.seek_satisfaction);
        SeekBar ambitious = this.view.findViewById(R.id.seek_ambitious);
        SeekBar confidence = this.view.findViewById(R.id.seek_confidence);
        SeekBar energy = this.view.findViewById(R.id.seek_energy);
        SeekBar enthusiasm = this.view.findViewById(R.id.seek_enthusiasm);
        double sat = satisfaction.getProgress();
        double amb = ambitious.getProgress();
        double conf = confidence.getProgress();
        double ene = energy.getProgress();
        double enth = enthusiasm.getProgress();
        PersonalityParams p = new PersonalityParams(sat, conf, enth, amb, ene);
        detailSaver.saveDetails(p);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStackImmediate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        Log.d(""+seekBar.getId(),""+R.id.seek_confidence);
        if(seekBar.getId()==R.id.seek_confidence){
            TextView t = view.findViewById(R.id.n_confidence);
            t.setText(""+i);
        }
        if(seekBar.getId()==R.id.seek_ambitious){
            TextView t = view.findViewById(R.id.n_ambitious);
            t.setText(""+i);
        }
        if(seekBar.getId()==R.id.seek_energy){
            TextView t = view.findViewById(R.id.n_energy);
            t.setText(""+i);
        }
        if(seekBar.getId()==R.id.seek_satisfaction){
            TextView t = view.findViewById(R.id.n_satisfaction);
            t.setText(""+i);
        }
        if(seekBar.getId()==R.id.seek_enthusiasm){
            TextView t = view.findViewById(R.id.n_enthusiasm);
            t.setText(""+i);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}