package com.example.samson.githubusers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

  public static  class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

      @Override
      public void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);

          addPreferencesFromResource(R.xml.pref_visualizer);

          SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
          PreferenceScreen preferenceScreen = getPreferenceScreen();

          int prefCount = preferenceScreen.getPreferenceCount();

          for(int i = 0; i < prefCount ;i++ ){
              Preference pref = preferenceScreen.getPreference(i);
              if(!(pref instanceof CheckBoxPreference)) {
                  String value = sharedPreferences.getString(pref.getKey(), "");
                  setSummary(pref, value);
              }
          }

          getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

      }


      @Override
      public void onDestroy() {
          super.onDestroy();
          getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
      }
      void setSummary(Preference preferences, String value){
          if(preferences instanceof ListPreference){
              ListPreference listPreference = (ListPreference) preferences;
              int prefIndex = listPreference.findIndexOfValue(value);
              if(prefIndex >= 0){
                  listPreference.setSummary(listPreference.getEntries()[prefIndex]);
              }
          }
          else if(preferences instanceof EditTextPreference){
              preferences.setSummary(value);
          }
      }

      @Override
      public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
          Preference preference = findPreference(key);

          if(preference != null){
              String value = sharedPreferences.getString(preference.getKey(), "");
              setSummary(preference, value);

          }
      }
  }

}
