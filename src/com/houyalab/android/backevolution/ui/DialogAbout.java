package com.houyalab.android.backevolution.ui;

import com.houyalab.android.backevolution.R;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

public class DialogAbout extends Dialog {

	private Context mContext;
	
	public DialogAbout(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public DialogAbout(Context context, int theme) {
		super(context, theme);
	}

	public DialogAbout(Context context) {
		super(context);
		
		setContentView(R.layout.w_about);
		TextView tvAboutTitle = (TextView) findViewById(R.id.tv_about_title);
		tvAboutTitle.setText(R.string.txt_about_title);
		TextView tvAboutBody = (TextView) findViewById(R.id.tv_about_body);
		tvAboutBody.setText(R.string.txt_about_body);
	}

}
