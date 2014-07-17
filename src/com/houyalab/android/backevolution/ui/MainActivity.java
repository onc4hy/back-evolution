package com.houyalab.android.backevolution.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.R.id;
import com.houyalab.android.backevolution.R.layout;
import com.houyalab.android.backevolution.R.menu;
import com.houyalab.android.backevolution.adapter.BackendPagerAdapter;
import com.houyalab.android.backevolution.base.BaseActivity;

public class MainActivity extends BaseActivity implements ActionBar.TabListener {

	private BackendPagerAdapter mBackendPagerAdapter;
	private ViewPager mViewPager;
	private ActionBar mActionBar;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_main);

		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);

		mBackendPagerAdapter = new BackendPagerAdapter(
				getSupportFragmentManager(), getApplicationContext());

		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPager.setAdapter(mBackendPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						mActionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mBackendPagerAdapter.getCount(); i++) {
			mActionBar.addTab(mActionBar.newTab()
					.setText(mBackendPagerAdapter.getPageTitle(i))
					.setIcon(mBackendPagerAdapter.getPageIcon(i))
					.setTabListener(this));
		}
		mActionBar.setSelectedNavigationItem(0);

		// sharedPreferences
        //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		
		// DrawerLayout
		/*
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerStateChanged(int newState) {
				super.onDrawerStateChanged(newState);
			}

		};
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_setting) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
	}

}
