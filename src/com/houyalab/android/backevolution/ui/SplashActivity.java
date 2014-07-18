package com.houyalab.android.backevolution.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.houyalab.android.backevolution.R;

public class SplashActivity extends Activity {
	private Handler mHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_splash);

		mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		}, 1000);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

}
