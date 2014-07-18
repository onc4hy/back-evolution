package com.houyalab.android.backevolution.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;

import com.houyalab.android.backevolution.R;

public class SettingsActivity extends PreferenceActivity 
	implements OnPreferenceChangeListener,OnPreferenceClickListener
{

	public SettingsActivity() {
		super();
		addPreferencesFromResource(R.xml.settings);
	}

	@Override
	public boolean onPreferenceClick(Preference pref) {
		return false;
	}

	@Override
	public boolean onPreferenceChange(Preference pref, Object arg1) {
		return false;
	}
	
}
