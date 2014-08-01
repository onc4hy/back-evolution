package com.houyalab.android.backevolution.receiver;

import com.houyalab.android.backevolution.util.HelperUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;

public class MessageReceiver extends BroadcastReceiver {

	private ProgressBar mPbMeditationInfo;
	private int mMeditationTimeDuration;
	
	public MessageReceiver() {
		super();
	}

	@Override
	public void onReceive(Context ctx, Intent intent) {
		String action = intent.getAction();
		Bundle extras = intent.getExtras();
		if (action.equalsIgnoreCase(Intent.ACTION_BATTERY_CHANGED)) {
			int bat_cur;
			int bat_total;
			bat_cur = extras.getInt(BatteryManager.EXTRA_LEVEL);
			bat_total = extras.getInt(BatteryManager.EXTRA_SCALE);
		}else if (action.equalsIgnoreCase(Intent.ACTION_TIME_CHANGED)) {
		}else if (action.equalsIgnoreCase(HelperUtil.MESSAGE_MEDITATION_START)) {
		}else if (action.equalsIgnoreCase(HelperUtil.MESSAGE_MEDITATION_RUNNING)) {
			long elapsedBaseTime = SystemClock.elapsedRealtime();
			long elapsedCurrentTime = SystemClock.elapsedRealtime();
			long elapsedTime = SystemClock.elapsedRealtime();
			
			elapsedTime = elapsedCurrentTime - elapsedBaseTime;
			
			//mPbMeditationInfo = ctx.getResources().
			if (elapsedTime >= mMeditationTimeDuration * 60 * 1000) {
				//mPbMeditationInfo.setVisibility(View.VISIBLE);
			} else {
			}
		}else if (action.equalsIgnoreCase(HelperUtil.MESSAGE_MEDITATION_STOP)) {
		}
	}

}
