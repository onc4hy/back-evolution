package com.houyalab.android.backevolution.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.KeyEvent;

import com.houyalab.android.backevolution.R;

public class FragmentSettings extends PreferenceFragment 
{

	public FragmentSettings() {
		super();
		addPreferencesFromResource(R.xml.settings);
	}

}
