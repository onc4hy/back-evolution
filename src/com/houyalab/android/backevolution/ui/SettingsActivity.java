package com.houyalab.android.backevolution.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;

import com.houyalab.android.backevolution.R;

public class SettingsActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);

		setListPrefSummary((ListPreference) findPreference("meditation_bg"));
		setListPrefSummary((ListPreference) findPreference("meditation_time_prepare"));
		setListPrefSummary((ListPreference) findPreference("meditation_time_duration_hour"));
		setListPrefSummary((ListPreference) findPreference("meditation_time_duration_minute"));
		setListPrefSummary((ListPreference) findPreference("meditation_time_duration_second"));
		setListPrefSummary((ListPreference) findPreference("meditation_music_begin"));
		setListPrefSummary((ListPreference) findPreference("meditation_music_end"));
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
		.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
		Preference pref = findPreference(key);
		if (pref instanceof EditTextPreference) {
			setEditTextPrefSummary((EditTextPreference) pref);
		} else if (pref instanceof ListPreference) {
			setListPrefSummary((ListPreference) pref);
		}
	}

	private void setEditTextPrefSummary(EditTextPreference editPref) {
		editPref.setSummary(editPref.getText());
	}

	private void setListPrefSummary(ListPreference listPref) {
		listPref.setSummary(listPref.getEntry());
	}

}
