package com.houyalab.android.backevolution.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.ui.FragmentBookIndex;
import com.houyalab.android.backevolution.ui.FragmentEvolutionIndex;
import com.houyalab.android.backevolution.ui.FragmentMeditationIndex;

public class BackendPagerAdapter extends FragmentPagerAdapter {

	private FragmentManager mFM;
	private Context mContext;
	private FragmentMeditationIndex mFragMeditation;
	private FragmentBookIndex mFragBook;
	private FragmentEvolutionIndex mFragEvolution;

	public BackendPagerAdapter(FragmentManager fm,Context ctx) {
		super(fm);
		mFM = fm;
		mContext = ctx;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Fragment getItem(int position) {
		if (mFragMeditation == null) {
			mFragMeditation = new FragmentMeditationIndex();
		}
		if (mFragBook == null) {
			mFragBook = new FragmentBookIndex();
		}
		if (mFragEvolution == null) {
			mFragEvolution = new FragmentEvolutionIndex();
		}
		switch (position) {
		case 0:
			return mFragMeditation;
		case 1:
			return mFragBook;
		case 2:
			return mFragEvolution;
		default:
			break;
		}
		return null;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return mContext.getResources().getString(R.string.title_meditation);
		case 1:
			return mContext.getResources().getString(R.string.title_book);
		case 2:
			return mContext.getResources().getString(R.string.title_evolution);
		default:
			break;
		}
		return null;
	}

	public int getPageIcon(int position) {
		switch (position) {
		case 0:
			return R.drawable.ic_drawer;
		case 1:
			return R.drawable.ic_drawer;
		case 2:
			return R.drawable.ic_drawer;
		default:
			break;
		}
		return R.drawable.ic_drawer;
	}
	
}
