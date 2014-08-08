package com.houyalab.android.backevolution.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.DateUtil;
import com.houyalab.android.backevolution.util.HelperUtil;
import com.houyalab.android.backevolution.util.UIUtil;

public class FragmentMeditationIndex extends BaseFragment implements
		View.OnClickListener, ServiceConnection {

	private int MEDITATION_BEGIN = 0;
	private int MEDITATION_RUNING = 1;
	private int MEDITATION_END = 2;
	private int MEDITATION_PREPARE_RUNING = 3;

	private RadioButton mRBtnMeditationSetting;
	private RadioButton mRBtnMeditationDo;
	private RadioButton mRBtnMeditationCheck;
	private MediaPlayer mPlayer;
	private TextView mTvProgCur;
	private TextView mTvProgMax;
	private boolean mMusicLoopMode;
	private boolean mMeditationRuningState;
	private String mMusicUriRootPath;
	private String mMusicBeginPath;
	private String mMusicEndPath;
	private int mMusicBeginResId;
	private int mMusicEndResId;
	private String mMusicBeginName;
	private String mMusicEndName;
	private int mMeditationTimeDuration;
	private int mMeditationTimeDurationHour;
	private int mMeditationTimeDurationMinute;
	private int mMeditationTimeDurationSecond;
	private int mMeditationTimePrepare;
	private int mMeditationTimePrepareRest;
	private String mMeditationBg;
	private int mMeditationBgResId;
	private SharedPreferences mSharedPrefs;

	private ProgressBar mPbMeditationInfo;
	private int mProgressMeditationDuration;

	private Handler mHandler;

	private Timer mTimer;
	private TimerTask mMeditationTimerTask;
	private Runnable mMeditationTimerPrepareTask;

	private int mInProgress;
	private String mInProgressString;
	private long mPlanedEllapsed;
	private long mBaseEllapsed;
	private long mCurrentEllapsed;
	private long mDuringEllapsed;
	private long mRemainEllapsed;
	private Date mBaseMeditationTime;
	private Date mEndMeditationTime;
	private Date mDuringMeditationTime;
	private Date mRemainMeditationTime;
	private SimpleDateFormat mSdf;
	private String mProgMax;
	private String mProgCur;

	private ImageView mImageBg;
	private Bitmap mBmpBg;
	private BitmapDrawable mDrawBg;

	public FragmentMeditationIndex() {
		mMeditationRuningState = false;
		mMeditationTimeDuration = Integer
				.valueOf(HelperUtil.DEFAULT_MEDITATION_TIME_DURATION);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getMeditationSettings();
		
		mPlayer = new MediaPlayer();
		mSdf = new SimpleDateFormat("HH:mm:ss");
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == MEDITATION_RUNING) {
					mPbMeditationInfo.setVisibility(View.VISIBLE);
					mTvProgMax.setVisibility(View.VISIBLE);
					mTvProgCur.setVisibility(View.VISIBLE);

					mPbMeditationInfo.setProgress(mInProgress);
					mTvProgCur.setText(mInProgressString);
					mTvProgMax.setText(mProgMax);
				}
				if (msg.what == MEDITATION_END) {
					updateMeditationStatus("stop");
				}
			}
		};
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
		mTvProgCur = (TextView) rootView
				.findViewById(R.id.tv_meditation_prog_cur);
		mTvProgMax = (TextView) rootView
				.findViewById(R.id.tv_meditation_prog_max);

		mRBtnMeditationSetting.setOnClickListener(this);
		mRBtnMeditationDo.setOnClickListener(this);

		mMeditationRuningState = false;
		mRBtnMeditationDo.setText(R.string.meditation_btn_start);

		mImageBg = (ImageView) rootView.findViewById(
				R.id.iv_meditation_bg);
		mImageBg.setImageResource(mMeditationBgResId);

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

				if (mMeditationRuningState == false) {
					startMeditation();
				} else {
					stopMeditation();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == -1) {
			startMeditationMusic();
			startMeditationTimer();
		}
	}

	private void startMeditation() {
		updateMeditationStatus("start");
		
		startMeditationPrepareTimer();
	}

	private void startMeditationPrepareTimer() {
		Intent prepareIntent = new Intent(getActivity(), PrepareActivity.class);
		if (mMeditationTimePrepare > 0) {
			mMeditationTimePrepare = mMeditationTimePrepare * 1000;
			Bundle extras = new Bundle();
			extras.putInt("prepareTime", mMeditationTimePrepare);
			prepareIntent.putExtras(extras);
			startActivityForResult(prepareIntent, 0);
		} else {
			startMeditationMusic();
			startMeditationTimer();
		}
	}

	private void stopMeditation() {
		updateMeditationStatus("stop");
		
		stopMeditationMusic();

		stopMeditationTimer();

		/*
		 * Intent service = new Intent(HelperUtil.MEDITATION_SERVICE);
		 * service.putExtras(extras); getActivity().stopService(service);
		 */
	}

	private void startMeditationMusic() {
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
					getActivity().getApplicationContext(),
					Uri.parse("android.resource://"
							+ getActivity().getPackageName() + "/"
							+ mMusicBeginResId));
			mPlayer.prepare();
			mPlayer.setLooping(mMusicLoopMode);
			mPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stopMeditationMusic() {
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
					getActivity().getApplicationContext(),
					Uri.parse("android.resource://"
							+ getActivity().getPackageName() + "/"
							+ mMusicEndResId));
			mPlayer.setLooping(false);
			mPlayer.prepare();
			mPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startMeditationTimer() {
		mImageBg.setImageResource(mMeditationBgResId);

		mBaseEllapsed = System.currentTimeMillis();
		mBaseMeditationTime = new Date();
		mMeditationTimerTask = new TimerTask() {
			@Override
			public void run() {
				if (mMeditationTimeDuration != 0) {
					mPlanedEllapsed = mMeditationTimeDuration * 1000;
					mCurrentEllapsed = System.currentTimeMillis();
					mDuringEllapsed = mCurrentEllapsed - mBaseEllapsed;
					mRemainEllapsed = mPlanedEllapsed - mDuringEllapsed;
					mInProgress = Long.valueOf(
							(mDuringEllapsed * 100l) / mPlanedEllapsed)
							.intValue();
					String formatDateString = "";
					if (mMeditationTimeDurationHour > 0) {
						formatDateString = "HH:mm:ss";
					} else {
						formatDateString = "mm:ss";
					}
					mInProgressString = DateUtil.formatDate(mRemainEllapsed,
							formatDateString);
					Message msgProgress = new Message();
					msgProgress.what = MEDITATION_RUNING;
					mHandler.sendMessage(msgProgress);
					if (mDuringEllapsed >= mPlanedEllapsed) {
						msgProgress.what = MEDITATION_END;
						// mHandler.sendMessage(msgProgress);

						stopMeditationMusic();
						stopMeditationTimer();
					}
				} else {
					mInProgressString = getResources().getString(
							R.string.lbl_meditation_infinite);
				}
			}
		};
		mTimer = new Timer();
		mTimer.schedule(mMeditationTimerTask, 0, 1000);
	}

	private void stopMeditationTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}

	private void updateMeditationStatus(String statusCode) {
		if (statusCode.equalsIgnoreCase("start")) {
			mMeditationRuningState = true;
			mRBtnMeditationDo.setText(R.string.meditation_btn_stop);

			mPbMeditationInfo.setBackgroundResource(R.drawable.progressbar_bg);
			mPbMeditationInfo.setVisibility(View.VISIBLE);
			mTvProgCur.setVisibility(View.VISIBLE);

			if (mMeditationTimeDuration == 0) {
				mProgCur = getResources().getString(
						R.string.lbl_meditation_infinite);
			} else {
				mPlanedEllapsed = mMeditationTimeDuration * 1000;
				String formatDateString = "";
				if (mMeditationTimeDurationHour > 0) {
					formatDateString = "HH:mm:ss";
				} else {
					formatDateString = "mm:ss";
				}
				mProgCur = DateUtil.formatDate(mPlanedEllapsed,
						formatDateString);
			}
			mTvProgCur.setText(mProgCur);
			mProgMax = "";
			if (mMeditationTimeDuration == 0) {
				mProgMax = getResources().getString(
						R.string.lbl_meditation_infinite);
			} else {
				String formatDateString = "";
				if (mMeditationTimeDurationHour > 0) {
					formatDateString = "HH:mm:ss";
				} else {
					formatDateString = "mm:ss";
				}
				String planTime = "";
				planTime = DateUtil.formatDate(mMeditationTimeDuration * 1000,
						formatDateString);
				mProgMax = getResources().getString(
						R.string.lbl_meditation_plantime)
						+ " " + planTime;
			}
			mProgMax = mProgMax + "\n"
					+ getResources().getString(R.string.lbl_begin_in) + " "
					+ mSdf.format(mBaseMeditationTime);
			mTvProgMax.setVisibility(View.VISIBLE);
			mTvProgMax.setText(mProgMax);

		} else if (statusCode.equalsIgnoreCase("stop")) {
			mMeditationRuningState = false;
			mRBtnMeditationDo.setText(R.string.meditation_btn_start);

			mPbMeditationInfo.setProgress(0);
			mPbMeditationInfo.setVisibility(View.INVISIBLE);

			mTvProgCur.setVisibility(View.INVISIBLE);
			mTvProgMax.setVisibility(View.INVISIBLE);

			mEndMeditationTime = new Date();
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

		if (mMusicBeginName.equalsIgnoreCase("singbowl_basu.mp3")) {
			mMusicBeginResId = R.raw.singbowl_basu;
		} else if (mMusicBeginName.equalsIgnoreCase("singbowl_basu.mp3")) {
			mMusicBeginResId = R.raw.singbowl_sakya;
		} else if (mMusicBeginName.equalsIgnoreCase("woodblock.mp3")) {
			mMusicBeginResId = R.raw.woodblock;
		} else if (mMusicBeginName.equalsIgnoreCase("amitabha.mp3")) {
			mMusicBeginResId = R.raw.amitabha;
		} else if (mMusicBeginName.equalsIgnoreCase("amitabha_heart.mp3")) {
			mMusicBeginResId = R.raw.amitabha_heart;
		} else {
			mMusicBeginResId = R.raw.singbowl_basu;
		}

		if (mMusicEndName.equalsIgnoreCase("singbowl_basu.mp3")) {
			mMusicEndResId = R.raw.singbowl_basu;
		} else if (mMusicEndName.equalsIgnoreCase("singbowl_sakya.mp3")) {
			mMusicEndResId = R.raw.singbowl_sakya;
		} else if (mMusicEndName.equalsIgnoreCase("woodblock.mp3")) {
			mMusicEndResId = R.raw.woodblock;
		} else if (mMusicEndName.equalsIgnoreCase("amitabha.mp3")) {
			mMusicEndResId = R.raw.amitabha;
		} else if (mMusicEndName.equalsIgnoreCase("amitabha_heart.mp3")) {
			mMusicEndResId = R.raw.amitabha_heart;
		} else {
			mMusicEndResId = R.raw.singbowl_basu;
		}

		if (mSharedPrefs.contains("meditation_music_play_mode")) {
			mMusicLoopMode = mSharedPrefs.getBoolean(
					"meditation_music_play_mode",
					HelperUtil.DEFAULT_MUSIC_PLAY_MODE);
		} else {
			mMusicLoopMode = HelperUtil.DEFAULT_MUSIC_PLAY_MODE;
		}

		if (mSharedPrefs.contains("meditation_time_duration_hour")) {
			mMeditationTimeDurationHour = Integer.valueOf(mSharedPrefs
					.getString("meditation_time_duration_hour",
							HelperUtil.DEFAULT_MEDITATION_TIME_DURATION_HOUR));
		}
		if (mSharedPrefs.contains("meditation_time_duration_minute")) {
			mMeditationTimeDurationMinute = Integer
					.valueOf(mSharedPrefs.getString(
							"meditation_time_duration_minute",
							HelperUtil.DEFAULT_MEDITATION_TIME_DURATION_MINUTE));
		}
		if (mSharedPrefs.contains("meditation_time_duration_second")) {
			mMeditationTimeDurationSecond = Integer
					.valueOf(mSharedPrefs.getString(
							"meditation_time_duration_second",
							HelperUtil.DEFAULT_MEDITATION_TIME_DURATION_SECOND));
		}
		mMeditationTimeDuration = mMeditationTimeDurationHour * 60 * 60
				+ mMeditationTimeDurationMinute * 60
				+ mMeditationTimeDurationSecond;

		if (mSharedPrefs.contains("meditation_time_prepare")) {
			mMeditationTimePrepare = Integer.valueOf(mSharedPrefs.getString(
					"meditation_time_prepare",
					HelperUtil.DEFAULT_MEDITATION_TIME_PREPARE));
		}

		if (mSharedPrefs.contains("meditation_bg")) {
			mMeditationBg = mSharedPrefs.getString("meditation_bg",
					HelperUtil.DEFAULT_MEDITATION_BG);
			if (mMeditationBg.equalsIgnoreCase("bamboo")) {
				mMeditationBgResId = R.drawable.splash;
			} else if (mMeditationBg.equalsIgnoreCase("amitabha")) {
				mMeditationBgResId = R.drawable.amitabha;
			}
		}

	}

	@Override
	public void onServiceConnected(ComponentName component, IBinder binder) {
	}

	@Override
	public void onServiceDisconnected(ComponentName component) {
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
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
		}catch(Exception e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
}
