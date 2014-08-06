package com.houyalab.android.backevolution.service;

import android.os.CountDownTimer;

public class PrepareCountDownTimer extends CountDownTimer {

	public PrepareCountDownTimer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	@Override
	public void onFinish() {
	}

	@Override
	public void onTick(long millisUntilFinished) {
	}

}
