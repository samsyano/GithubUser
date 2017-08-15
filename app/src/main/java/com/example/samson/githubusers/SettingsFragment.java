package com.example.samson.githubusers;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

/**
 * Created by SAMSON on 7/27/2017.
 */

public class SettingsFragment extends PreferenceFragment  {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_visualizer);
    }

}
