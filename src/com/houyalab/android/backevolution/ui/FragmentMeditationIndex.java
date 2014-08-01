package com.houyalab.android.backevolution.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
		View.OnClickListener {

	private RadioButton mRBtnMeditationSetting;
	private RadioButton mRBtnMeditationDo;
	private RadioButton mRBtnMeditationCheck;
	private MediaPlayer mPlayer;
	private Chronometer mChronometer;
	private boolean mMusicPlayMode;
	private boolean mMeditationDoState;
	private String mMusicUriRootPath;
	private String mMusicBeginName;
	private String mMusicEndName;
	private int mMeditationTimeDuration;
	private int mMeditationTimePrepare;
	private SharedPreferences mSharedPrefs;
	
	private ProgressBar mPbMeditationInfo;
	private int mProgressMeditationDuration;

	private Handler mHandler;
	
	public FragmentMeditationIndex() {
		mMeditationDoState = false;
		mMeditationTimeDuration = HelperUtil.DEFAULT_MEDITATION_TIME_DURATION;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getMeditationSettings();
		mPlayer = new MediaPlayer();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.w_meditation_index, container,
				false);
		mPbMeditationInfo = (ProgressBar) rootView
				.findViewById(R.id.pb_meditation_info);
		mPbMeditationInfo.setVisibility(View.INVISIBLE);
		
		mRBtnMeditationSetting = (RadioButton) rootView
				.findViewById(R.id.rb_meditation_settings);
		mRBtnMeditationDo = (RadioButton) rootView
				.findViewById(R.id.rb_meditation_do);
		//mRBtnMeditationCheck = (RadioButton) rootView
		//		.findViewById(R.id.rb_meditation_check);

		mRBtnMeditationSetting.setOnClickListener(this);
		mRBtnMeditationDo.setOnClickListener(this);
		//mRBtnMeditationCheck.setOnClickListener(this);

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
				Uri uriMusicBeginPath = null;
				Uri uriMusicEndPath = null;
				mMusicUriRootPath = "android.resource://"
						+ getActivity().getPackageName() + "/";
				if (mMusicBeginName.equalsIgnoreCase("bowl.mp3")) {
					uriMusicBeginPath = Uri.parse(mMusicUriRootPath + R.raw.bowl);
				}else if (mMusicBeginName.equalsIgnoreCase("gong.mp3")) {
					uriMusicBeginPath = Uri.parse(mMusicUriRootPath + R.raw.gong);
				}else {
					uriMusicBeginPath = Uri.parse(mMusicUriRootPath + R.raw.bowl);
				}
				if (mMusicEndName.equalsIgnoreCase("bowl.mp3")) {
					uriMusicEndPath = Uri.parse(mMusicUriRootPath + R.raw.bowl);
				}else if (mMusicEndName.equalsIgnoreCase("gong.mp3")) {
					uriMusicEndPath = Uri.parse(mMusicUriRootPath + R.raw.gong);
				}else {
					uriMusicEndPath = Uri.parse(mMusicUriRootPath + R.raw.gong);
				}
				
				if (mMeditationDoState == false) {
					Intent service = new Intent(HelperUtil.MEDITATION_SERVICE);
					Bundle extras = new Bundle();
					extras.putString("musicBeginPath",uriMusicBeginPath.toString());
					extras.putString("musicEndPath",uriMusicEndPath.toString());
					extras.putBoolean("musicLoopMode",mMusicPlayMode);
					extras.putString("action","start");
					service.putExtras(extras);
					getActivity().startService(service);

					mRBtnMeditationDo.setText(R.string.meditation_btn_stop);
					mMeditationDoState = true;
					
				} else {
					Intent service = new Intent(HelperUtil.MEDITATION_SERVICE);
					Bundle extras = new Bundle();
					extras.putString("musicBeginPath",uriMusicBeginPath.toString());
					extras.putString("musicEndPath",uriMusicEndPath.toString());
					extras.putBoolean("musicLoopMode",mMusicPlayMode);
					extras.putString("action","stop");
					service.putExtras(extras);
					getActivity().startService(service);
					
					mRBtnMeditationDo.setText(R.string.meditation_btn_start);
					mMeditationDoState = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		//} else if (view.getId() == R.id.rb_meditation_check) {
		//	Intent intentTarget = new Intent(getActivity(),
		//			ActivityMeditationCheck.class);
		//	startActivity(intentTarget);
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

}
