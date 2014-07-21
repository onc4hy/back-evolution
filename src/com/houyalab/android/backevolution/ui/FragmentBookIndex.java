package com.houyalab.android.backevolution.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.houyalab.android.backevolution.R;

public class FragmentBookIndex extends BaseFragment 
implements View.OnClickListener {

	private ListView mLvBookCatalog;
	private ListView mLvBookBookmarks;
	private ListView mLvBookComments;
	
	private ListAdapter mCatalogAdapter;
	private ListAdapter mBookmarksAdapter;
	private ListAdapter mCommentsAdapter;
	private List<Map<String,Object>> mCatalogAdapterData;
	
	public FragmentBookIndex() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.w_book,container,false);
		initBookCatalog();
		initBookMarks();
		initBookComments();
		return rootView;
	}

	private void initBookCatalog() {
		mLvBookCatalog = (ListView)getActivity().findViewById(R.id.lv_book_catalog);
		mCatalogAdapterData = new ArrayList();
		//mCatalogAdapter = new SimpleAdapter(getActivity(), mCatalogAdapterData,
		//		R.layout.u_main_drawer,new String[] {"",""},new Integer[] {});
		mLvBookCatalog.setAdapter(mCatalogAdapter);		
	}

	private void initBookMarks() {
	}

	private void initBookComments() {
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.rb_book_catalog) {
			AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(getActivity());
			dlgBuilder.setTitle(R.string.title_meditation_settings);
			dlgBuilder.setMessage("a");
			dlgBuilder.show();
		} else if (view.getId() == R.id.rb_book_bookmark) {
		} else if (view.getId() == R.id.rb_book_comments) {
		}
	}

	
}
