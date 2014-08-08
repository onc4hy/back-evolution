package com.houyalab.android.backevolution.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.TextView;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.HelperUtil;

public class PrepareActivity extends Activity {
	private TextView mTvPrepare;
	private CountDownTimer mPrepareTimer;
	private int mPrepareTime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_prepare);

		mTvPrepare = (TextView) findViewById(R.id.tv_prepare);

		mPrepareTime = getIntent().getExtras().getInt(
				"prepareTime",
				Integer.valueOf(HelperUtil.DEFAULT_MEDITATION_TIME_PREPARE)
						.intValue());
		mPrepareTimer = new CountDownTimer(mPrepareTime, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				mTvPrepare
						.setText(String.valueOf((millisUntilFinished / 1000)));
			}

			@Override
			public void onFinish() {
				PrepareActivity.this.setResult(HelperUtil.STATUS_DO_MEDITATION);
				PrepareActivity.this.finish();
			}
		};

		mPrepareTimer.start();
	}

}
