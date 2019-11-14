package com.example.statefull;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;

public class SettingsFragment extends PreferenceFragment {

    public void onCreate(@Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);
        addPreferencesFromResource(R.xml.preferences);
    }
}
