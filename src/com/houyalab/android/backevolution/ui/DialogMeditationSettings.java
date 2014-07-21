package com.houyalab.android.backevolution.ui;

import com.houyalab.android.backevolution.R;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

public class DialogMeditationSettings extends Dialog 
{

	public DialogMeditationSettings(Context context) {
		super(context);
		
		setContentView(R.layout.w_meditation_setting);
	}

	public DialogMeditationSettings(Context context, int theme) {
		super(context, theme);
	}

	public DialogMeditationSettings(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}


}
