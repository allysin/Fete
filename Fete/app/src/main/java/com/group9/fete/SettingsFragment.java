package com.group9.fete;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Anubhav on 30-10-2014.
 */
public class SettingsFragment extends PreferenceFragment {
    public SettingsFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    //@Override
    //public void OnCreateView(android.view.LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState ){

    //}
}
