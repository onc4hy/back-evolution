package com.houyalab.android.backevolution.service;

import java.util.TimerTask;

import com.houyalab.android.backevolution.R;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;

public class MeditationService extends Service implements
		MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
		MediaPlayer.OnCompletionListener {
	
	private static final String TAG = "MEDITATION_SERVICE";
	private MediaPlayer mPlayer;
	private IBinder mBinder = new MeditationBind();
	private TimerTask timerTask = new TimerTask() {
		@Override
		public void run() {
		}
	}; 
	
	public class MeditationBind extends Binder {
		MeditationService getService() {
			return MeditationService.this;
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();

		mPlayer = new MediaPlayer();
		initMediaPlayer();
	}

	private void initMediaPlayer() {
		mPlayer.setWakeMode(getApplicationContext(),
				PowerManager.PARTIAL_WAKE_LOCK);
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

		mPlayer.setOnPreparedListener(this);
		mPlayer.setOnCompletionListener(this);
		mPlayer.setOnErrorListener(this);
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		Bundle extras = intent.getExtras();
		doAction(extras);
	}

	public void doAction(Bundle extras) {
		String musicBeginPath = extras.getString("musicBeginPath");
		String musicEndPath = extras.getString("musicEndPath");
		boolean musicLoopMode = extras.getBoolean("musicLoopMode");
		String action = extras.getString("action");
		try {
			if (action == null || action.trim().equals("")) {
				action = "start";
			}
			if (action.equalsIgnoreCase("start")) {
				mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bowl);
				//mPlayer.setDataSource(musicBeginPath);
				mPlayer.setLooping(true);
				mPlayer.prepare();
				mPlayer.start();
			}else if (action.equalsIgnoreCase("stop")) {
				if (mPlayer.isPlaying()) {
					mPlayer.stop();
					mPlayer.release();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		mPlayer.stop();
		mPlayer.release();
		return false;
	}

	@Override
	public void onDestroy() {
		if (mPlayer.isPlaying()) {
			mPlayer.stop();
		}
		mPlayer.release();
		
		super.onDestroy();
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
	}

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mp.start();
	}
}
