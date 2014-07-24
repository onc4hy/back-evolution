package com.houyalab.android.backevolution.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.RadioButton;
import android.widget.TextView;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.HelperUtil;
import com.houyalab.android.backevolution.util.UIUtil;

public class FragmentMeditationIndex extends BaseFragment implements
		View.OnClickListener {

	private RadioButton mRBtnMeditationSetting;
	private RadioButton mRBtnMeditationDo;
	private RadioButton mRBtnMeditationCheck;
	private MediaPlayer mPlayer;
	private Chronometer mChronometer;
	private TextView mTvMeditationInfo;
	private boolean mMusicPlayMode;
	private boolean mMeditationDoState;
	private String mMusicUriRootPath;
	private String mMusicBeginName;
	private String mMusicEndName;
	private int mMeditationTimeDuration;
	private int mMeditationTimePrepare;
	private SharedPreferences mSharedPrefs;

	public FragmentMeditationIndex() {
		mMeditationDoState = false;
		mMeditationTimeDuration = HelperUtil.DEFAULT_MEDITATION_TIME_DURATION;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getMeditationSettings();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.w_meditation_index, container,
				false);
		mTvMeditationInfo = (TextView) rootView
				.findViewById(R.id.tv_meditation_info);
		mRBtnMeditationSetting = (RadioButton) rootView
				.findViewById(R.id.rb_meditation_settings);
		mRBtnMeditationDo = (RadioButton) rootView
				.findViewById(R.id.rb_meditation_do);
		mRBtnMeditationCheck = (RadioButton) rootView
				.findViewById(R.id.rb_meditation_check);

		mRBtnMeditationSetting.setOnClickListener(this);
		mRBtnMeditationDo.setOnClickListener(this);
		mRBtnMeditationCheck.setOnClickListener(this);

		mMeditationDoState = false;
		mRBtnMeditationDo.setText(R.string.meditation_btn_start);

		mChronometer = (Chronometer) rootView
				.findViewById(R.id.meditation_chronometer);
		if (mChronometer != null) {
			mChronometer
					.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
						@Override
						public void onChronometerTick(Chronometer chronometer) {
							long elapsedTime = SystemClock.elapsedRealtime()
									- chronometer.getBase();
							if (elapsedTime >= mMeditationTimeDuration * 60 * 1000) {
								stopTimer();
								mTvMeditationInfo.setVisibility(View.GONE);
							} else {
								if (mTvMeditationInfo != null) {
									mTvMeditationInfo
											.setVisibility(View.VISIBLE);
									mTvMeditationInfo.setText(String
											.valueOf(elapsedTime / (60 * 1000)));
								}
							}
						}
					});
		}
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
					String uriMusicBeginPath = "";
					String uriMusicEndPath = "";
					mMusicUriRootPath = "android.resource://"
							+ getActivity().getPackageName() + "/raw/";
					uriMusicBeginPath = mMusicUriRootPath + mMusicBeginName;
					uriMusicEndPath = mMusicUriRootPath + mMusicEndName;
					if (mPlayer != null) {
						if (mPlayer.isPlaying()) {
							mPlayer.stop();
						}
						mPlayer.release();
						mPlayer = null;
					}
					/*
					 * if (mMusicBeginName.equalsIgnoreCase("bowl.mp3")) {
					 * mPlayer = MediaPlayer.create(getActivity(), R.raw.bowl);
					 * } else if (mMusicBeginName.equalsIgnoreCase("gong.mp3"))
					 * { mPlayer = MediaPlayer.create(getActivity(),
					 * R.raw.gong); } else { mPlayer =
					 * MediaPlayer.create(getActivity(), R.raw.bowl); }
					 */
					mPlayer = MediaPlayer.create(getActivity(), R.raw.bowl);
					mPlayer.setLooping(mMusicPlayMode);
					mPlayer.start();
					mRBtnMeditationDo.setText(R.string.meditation_btn_stop);
					mMeditationDoState = true;

					startTimer();
				} else {
					if (mPlayer.isPlaying()) {
						mPlayer.stop();
						mPlayer.release();
						mPlayer = null;
					}
					mRBtnMeditationDo.setText(R.string.meditation_btn_start);
					mMeditationDoState = false;

					stopTimer();
					mTvMeditationInfo.setVisibility(View.GONE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (view.getId() == R.id.rb_meditation_check) {
			FragmentMeditationCheck fragMeditationCheck = new FragmentMeditationCheck();
			getFragmentManager().beginTransaction()
					.replace(R.layout.w_meditation_index, fragMeditationCheck)
					.commit();
		}
	}

	private void getMeditationSettings() {
		mSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		if (mSharedPrefs.contains("meditation_music_end")) {
			mMusicBeginName = mSharedPrefs.getString("meditation_music_end",
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
		if (mSharedPrefs.contains("meditation_music_play_mode")) {
			mMusicPlayMode = mSharedPrefs.getBoolean(
					"meditation_music_play_mode",
					HelperUtil.DEFAULT_MUSIC_PLAY_MODE);
		} else {
			mMusicPlayMode = true;
		}
		// mMeditationTimeDuration =
		// mSharedPrefs.getInt("meditation_time_duration",HelperUtil.DEFAULT_MEDITATION_TIME_DURATION);
		// mMeditationTimePrepare =
		// mSharedPrefs.getInt("meditation_time_prepare",HelperUtil.DEFAULT_MEDITATION_TIME_PREPARE);
	}

	private void startTimer() {
		if (mChronometer != null) {
			mChronometer.setBase(SystemClock.elapsedRealtime());
			mChronometer.start();
		}
	}

	private void stopTimer() {
		if (mChronometer != null) {
			mChronometer.stop();
			if (mPlayer != null) {
				if (mPlayer.isPlaying()) {
					mPlayer.stop();
					mPlayer.release();
					mPlayer = null;
				}
				if (mMusicEndName != null
						&& mMusicEndName.equalsIgnoreCase("gong.mp3")) {
					mPlayer = MediaPlayer.create(getActivity(), R.raw.gong);
				} else {
					mPlayer = MediaPlayer.create(getActivity(), R.raw.gong);
				}
				mPlayer.setLooping(false);
				mPlayer.start();
				mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						mp.stop();
						//mp.release();
					}
				});
			}
		}
	}
}
