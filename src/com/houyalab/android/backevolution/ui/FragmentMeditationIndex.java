package com.houyalab.android.backevolution.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.HelperUtil;
import com.houyalab.android.backevolution.util.UIUtil;

public class FragmentMeditationIndex extends BaseFragment implements
		View.OnClickListener, ServiceConnection {

	private RadioButton mRBtnMeditationSetting;
	private RadioButton mRBtnMeditationDo;
	private RadioButton mRBtnMeditationCheck;
	private MediaPlayer mPlayer;
	private TextView mTvProgCur;
	private TextView mTvProgMax;
	private boolean mMusicPlayMode;
	private boolean mMeditationDoState;
	private String mMusicUriRootPath;
	private String mMusicBeginPath;
	private String mMusicEndPath;
	private int mMusicBeginResId;
	private int mMusicEndResId;
	private String mMusicBeginName;
	private String mMusicEndName;
	private int mMeditationTimeDuration;
	private int mMeditationTimePrepare;
	private SharedPreferences mSharedPrefs;

	private ProgressBar mPbMeditationInfo;
	private int mProgressMeditationDuration;

	private Handler mHandler;

	private Timer mTimer;
	private TimerTask mMeditationTimerTask;

	private int mInProgress;
	private long mPlanedEllapsed;
	private long mDuringEllapsed;
	private long mBaseEllapsed;
	private long mCurrentEllapsed;

	public FragmentMeditationIndex() {
		mMeditationDoState = false;
		mMeditationTimeDuration = Integer.valueOf(HelperUtil.DEFAULT_MEDITATION_TIME_DURATION);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getMeditationSettings();
		// mPlayer = new MediaPlayer();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.w_meditation_index,
				container, false);
		mPbMeditationInfo = (ProgressBar) rootView
				.findViewById(R.id.pb_meditation_info);
		mPbMeditationInfo.setVisibility(View.INVISIBLE);

		mRBtnMeditationSetting = (RadioButton) rootView
				.findViewById(R.id.rb_meditation_settings);
		mRBtnMeditationDo = (RadioButton) rootView
				.findViewById(R.id.rb_meditation_do);
		// mRBtnMeditationCheck = (RadioButton) rootView
		// .findViewById(R.id.rb_meditation_check);
		mTvProgCur = (TextView) rootView
				.findViewById(R.id.tv_meditation_prog_cur);
		mTvProgMax = (TextView) rootView
				.findViewById(R.id.tv_meditation_prog_max);

		mRBtnMeditationSetting.setOnClickListener(this);
		mRBtnMeditationDo.setOnClickListener(this);
		// mRBtnMeditationCheck.setOnClickListener(this);

		mMeditationDoState = false;
		mRBtnMeditationDo.setText(R.string.meditation_btn_start);

		return rootView;
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.rb_meditation_settings) {
			Intent intentTarget = new Intent(getActivity(),
					SettingsActivity.class);
			startActivity(intentTarget);
		} else if (view.getId() == R.id.rb_meditation_do) {
			try {
				getMeditationSettings();

				if (mMeditationDoState == false) {
					Bundle extras = new Bundle();
					extras.putInt("musicBeginResId", mMusicBeginResId);
					extras.putBoolean("musicLoopMode", mMusicPlayMode);
					startMeditationMusic(extras);

					mBaseEllapsed = System.currentTimeMillis();
					startMeditationTimer();
					/*
					 * Intent service = new
					 * Intent(HelperUtil.MEDITATION_SERVICE);
					 * service.putExtras(extras);
					 * getActivity().startService(service);
					 */

					refreshMeditationStatus("start");
				} else {
					Bundle extras = new Bundle();
					extras.putInt("musicEndResId", mMusicEndResId);
					stopMeditationMusic(extras);

					stopMeditationTimer();
					
					refreshMeditationStatus("stop");
					/*
					 * Intent service = new
					 * Intent(HelperUtil.MEDITATION_SERVICE);
					 * service.putExtras(extras);
					 * getActivity().stopService(service);
					 */
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void refreshMeditationStatus(String statusCode) {
		if (statusCode.equalsIgnoreCase("start")) {
			mMeditationDoState = true;
			mRBtnMeditationDo.setText(R.string.meditation_btn_stop);

			mPbMeditationInfo.setBackgroundResource(R.drawable.progressbar_bg);
			//mPbMeditationInfo.setProgressDrawable(new ColorDrawable(android.R.color.black));
			mPbMeditationInfo.setVisibility(View.VISIBLE);
			
			String progMax = String.valueOf(mMeditationTimeDuration) + " " + getResources().getString(R.string.lbl_minute);
			mTvProgCur.setVisibility(View.VISIBLE);
			mTvProgMax.setVisibility(View.VISIBLE);
			mTvProgMax.setText(progMax);
			mTvProgCur.setText("%");
		}else if (statusCode.equalsIgnoreCase("stop")) {
			mMeditationDoState = false;
			mRBtnMeditationDo.setText(R.string.meditation_btn_start);

			mPbMeditationInfo.setProgress(0);
			mPbMeditationInfo.setVisibility(View.INVISIBLE);
			
			mTvProgCur.setVisibility(View.INVISIBLE);
			mTvProgMax.setVisibility(View.INVISIBLE);
		}
	}

	private void startMeditationMusic(Bundle extras) {
		final int musicBeginResId = extras.getInt("musicBeginResId");
		final boolean musicLoopMode = extras.getBoolean("musicLoopMode");
		try {
			// mPlayer.setDataSource(musicBeginPath);
			mPlayer = MediaPlayer.create(getActivity(), musicBeginResId);
			mPlayer.setLooping(musicLoopMode);
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
					mPlayer.release();
				}
				mPlayer = MediaPlayer.create(getActivity(), musicEndResId);
				mPlayer.setLooping(false);
				mPlayer.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startMeditationTimer() {
		mTimer = new Timer();
		mMeditationTimerTask = new TimerTask() {
			@Override
			public void run() {
				if (mMeditationTimeDuration != 0) {
					mPlanedEllapsed = mMeditationTimeDuration * 60 * 1000;
					mCurrentEllapsed = System.currentTimeMillis();
					mDuringEllapsed = mCurrentEllapsed - mBaseEllapsed;
					mInProgress = Long.valueOf(
							(mDuringEllapsed / mPlanedEllapsed) * 100).intValue();
					mPbMeditationInfo.setProgress(mInProgress);
					String progCur = String.valueOf(mInProgress)+"%";
					//mTvProgCur.setVisibility(View.VISIBLE);
					//mTvProgCur.setText(progCur);
					if (mDuringEllapsed >= mPlanedEllapsed) {
						Bundle extras = new Bundle();
						extras.putInt("musicEndResId", mMusicEndResId);
						stopMeditationMusic(extras);
						stopMeditationTimer();
						//refreshMeditationStatus("stop");
					}
				}else {
					
				}
			}
		};
		mTimer.schedule(mMeditationTimerTask, 1000, 1000);
	}

	private void stopMeditationTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}

	private void getMeditationSettings() {
		mSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		if (mSharedPrefs.contains("meditation_music_begin")) {
			mMusicBeginName = mSharedPrefs.getString("meditation_music_begin",
					HelperUtil.DEFAULT_BEGIN_MUSIC_PATH);
		} else {
			mMusicBeginName = HelperUtil.DEFAULT_BEGIN_MUSIC_PATH;
		}
		if (mSharedPrefs.contains("meditation_music_end")) {
			mMusicEndName = mSharedPrefs.getString("meditation_music_end",
					HelperUtil.DEFAULT_END_MUSIC_PATH);
		} else {
			mMusicEndName = HelperUtil.DEFAULT_END_MUSIC_PATH;
		}

		if (mMusicBeginName.equalsIgnoreCase("bowl.mp3")) {
			mMusicBeginResId = R.raw.bowl;
		} else if (mMusicBeginName.equalsIgnoreCase("bell.mp3")) {
			mMusicBeginResId = R.raw.bell;
		} else {
			mMusicBeginResId = R.raw.bowl;
		}

		if (mMusicEndName.equalsIgnoreCase("bowl.mp3")) {
			mMusicEndResId = R.raw.bowl;
		} else if (mMusicEndName.equalsIgnoreCase("bell.mp3")) {
			mMusicEndResId = R.raw.bell;
		} else {
			mMusicEndResId = R.raw.bowl;
		}

		if (mSharedPrefs.contains("meditation_music_play_mode")) {
			mMusicPlayMode = mSharedPrefs.getBoolean(
					"meditation_music_play_mode",
					HelperUtil.DEFAULT_MUSIC_PLAY_MODE);
		} else {
			mMusicPlayMode = true;
		}
		if (mSharedPrefs.contains("meditation_time_duration")) {
			mMeditationTimeDuration = Integer.valueOf(mSharedPrefs.getString(
					"meditation_time_duration",
					HelperUtil.DEFAULT_MEDITATION_TIME_DURATION));
		}
		if (mSharedPrefs.contains("meditation_time_prepare")) {
			mMeditationTimePrepare = Integer.valueOf(mSharedPrefs.getString(
					"meditation_time_prepare",
					HelperUtil.DEFAULT_MEDITATION_TIME_PREPARE));
		}
	}

	@Override
	public void onServiceConnected(ComponentName component, IBinder binder) {
	}

	@Override
	public void onServiceDisconnected(ComponentName component) {
	}

}
