package com.houyalab.android.backevolution.service;

import java.util.TimerTask;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.UIUtil;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;

public class MeditationService extends Service implements
		MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
		MediaPlayer.OnCompletionListener {
	
	private static final String TAG = "MEDITATION_SERVICE";
	private MediaPlayer mPlayer;
	private IBinder mBinder = new ServiceBinder();
	private TimerTask timerTask = new TimerTask() {
		@Override
		public void run() {
		}
	}; 
	
	private class ServiceBinder extends Binder {
		MeditationService getService() {
			return MeditationService.this;
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();

		//mPlayer = new MediaPlayer();
		initMediaPlayer();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Bundle extras = intent.getExtras();
		doAction(extras);
		return super.onStartCommand(intent, flags, startId);
	}


	private void initMediaPlayer() {
		//mPlayer.setWakeMode(getApplicationContext(),
		//		PowerManager.PARTIAL_WAKE_LOCK);
		//mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

		//mPlayer.setOnPreparedListener(this);
		//mPlayer.setOnCompletionListener(this);
		//mPlayer.setOnErrorListener(this);
	}

	public void doAction(Bundle extras) {
		final String musicBeginPath = extras.getString("musicBeginPath");
		final String musicEndPath = extras.getString("musicEndPath");
		final boolean musicLoopMode = extras.getBoolean("musicLoopMode");
		String action = extras.getString("action");
		try {
			if (action == null || action.trim().equals("")) {
				action = "start";
			}
			if (action.equalsIgnoreCase("start")) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.singbowl_basu);
							//mPlayer.setDataSource(musicBeginPath);
							mPlayer.setLooping(musicLoopMode);
							mPlayer.setVolume(100,100);
							mPlayer.prepare();
							mPlayer.start();
							UIUtil.message(getApplicationContext(),String.valueOf(mPlayer.isPlaying()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				thread.start();
			}else if (action.equalsIgnoreCase("stop")) {
				if (mPlayer != null) {
					if (mPlayer.isPlaying()) {
						mPlayer.stop();
					}
					mPlayer.release();
					mPlayer = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		try {
			if (mPlayer != null) {
				if (mPlayer.isPlaying()) {
					mPlayer.stop();
				}
				mPlayer.release();
				mPlayer = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void onDestroy() {
		try {
			if (mPlayer != null) {
				if (mPlayer.isPlaying()) {
					mPlayer.stop();
				}
				mPlayer.release();
				mPlayer = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.onDestroy();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
	}

	@Override
	public boolean onError(MediaPlayer mp, int arg1, int arg2) {
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
	}
	
	private void startMeditationMusic(Bundle extras) {
		final int musicBeginResId = extras.getInt("musicBeginResId");
		try {
			if (mPlayer != null) {
				if (mPlayer.isPlaying()) {
					mPlayer.stop();
				}
				mPlayer.release();
				mPlayer = null;
			}
			mPlayer = new MediaPlayer();
			mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mPlayer.setDataSource(
					getApplicationContext(),
					Uri.parse("android.resource://"
							+ getPackageName() + "/"
							+ musicBeginResId));
			mPlayer.prepare();
			//mPlayer.setLooping(mMusicLoopMode);
			mPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void stopMeditationMusic(Bundle extras) {
		final int musicEndResId = extras.getInt("musicEndResId");
		try {
			if (mPlayer != null) {
				if (mPlayer.isPlaying()) {
					mPlayer.stop();
				}
				mPlayer.release();
				mPlayer = null;
			}
			mPlayer = new MediaPlayer();
			mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mPlayer.setDataSource(
					getApplicationContext(),
					Uri.parse("android.resource://"
							+ getPackageName() + "/"
							+ musicEndResId));
			mPlayer.setLooping(false);
			mPlayer.prepare();
			mPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
