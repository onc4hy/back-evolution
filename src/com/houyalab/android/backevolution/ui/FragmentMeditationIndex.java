package com.houyalab.android.backevolution.ui;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.houyalab.android.backevolution.R;

public class FragmentMeditationIndex extends BaseFragment implements
		View.OnClickListener {

	private Button mBtnMeditationSetting;
	private Button mBtnMeditationDo;
	private Button mBtnMeditationCheck;
	private MediaPlayer mPlayer;
	private boolean mPlayerLoopMode;
	private boolean mMeditationDoState;
	private int mBeginMusicResId;
	private int mEndMusicResId;
	private SharedPreferences mSharedPrefs;
	
	public FragmentMeditationIndex() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.w_meditation, container,
				false);
		mBtnMeditationSetting = (Button) rootView
				.findViewById(R.id.btn_meditation_settings);
		mBtnMeditationDo = (Button) rootView
				.findViewById(R.id.btn_meditation_do);
		mBtnMeditationCheck = (Button) rootView
				.findViewById(R.id.btn_meditation_check);

		mBtnMeditationSetting.setOnClickListener(this);
		mBtnMeditationDo.setOnClickListener(this);
		mBtnMeditationCheck.setOnClickListener(this);

		mMeditationDoState = false;
		mBtnMeditationDo.setText(R.string.meditation_btn_start);
		
		return rootView;
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.btn_meditation_settings) {
			AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(getActivity());
			dlgBuilder.setTitle(R.string.title_meditation_settings);
			dlgBuilder.setMessage("a");
			dlgBuilder.show();
		} else if (view.getId() == R.id.btn_meditation_do) {
			try {
				mBeginMusicResId = mSharedPrefs.getInt("beginMusicResId", R.raw.zenbell);
				if (mMeditationDoState == false) {
					mPlayer = MediaPlayer.create(getActivity(), mBeginMusicResId);
					mPlayerLoopMode = true;
					mPlayer.setLooping(mPlayerLoopMode);
					mPlayer.start();
					mBtnMeditationDo.setText(R.string.meditation_btn_stop);
				}else {
					if (mPlayer.isPlaying()) {
						mPlayer.stop();
						mPlayer.release();
					}
					mBtnMeditationDo.setText(R.string.meditation_btn_start);
				}
			} catch (Exception e) {
			}
		} else if (view.getId() == R.id.btn_meditation_check) {
			AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(getActivity());
			dlgBuilder.setTitle(R.string.title_meditation_check);
			dlgBuilder.setMessage("b");
			dlgBuilder.show();
		}
	}

}
