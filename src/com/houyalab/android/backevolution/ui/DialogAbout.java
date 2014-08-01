package com.houyalab.android.backevolution.ui;

import java.io.InputStream;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.StringUtil;

import android.app.Dialog;
import android.content.Context;
import android.webkit.WebView;
import android.widget.TextView;

public class DialogAbout extends Dialog {

	private Context mContext;
	private TextView mTvAbout;
	
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
		mTvAbout = (TextView)findViewById(R.id.tv_about_body);
		try {
			String arcUrl = "data/articles/about.txt";
			try {
				InputStream is = getContext().getAssets().open(arcUrl);
				String arcContent = "";
				arcContent = StringUtil.getStringFromStream(is);
				is.close();
				mTvAbout.setText(arcContent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
