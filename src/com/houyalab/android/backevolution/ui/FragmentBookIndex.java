package com.houyalab.android.backevolution.ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.JsonUtil;

public class FragmentBookIndex extends BaseFragment implements
		View.OnClickListener {

	private ListView mLvBookCatalog;
	private ListView mLvBookBookmarks;
	private ListView mLvBookComments;

	private AssetManager mAM;

	private SimpleAdapter mCatalogAdapter;
	private SimpleAdapter mBookmarksAdapter;
	private SimpleAdapter mCommentsAdapter;
	private ArrayList<Map<String, String>> mCatalogData;

	public FragmentBookIndex() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mAM = getActivity().getAssets();
		View rootView = inflater.inflate(R.layout.w_book_index, container,
				false);
		mLvBookCatalog = (ListView) rootView.findViewById(R.id.lv_book_catalog);
		mLvBookBookmarks = (ListView) rootView
				.findViewById(R.id.lv_book_bookmarks);
		mLvBookComments = (ListView) rootView
				.findViewById(R.id.lv_book_comments);
		initBookCatalog(rootView);
		initBookMarks(rootView);
		initBookComments(rootView);
		return rootView;
	}

	private void initBookCatalog(View rootView) {
		mCatalogData = new ArrayList<Map<String, String>>();
		try {
			// mAM = getActivity().getAssets();
			InputStream is = mAM.open("data/books/catalog.json");
			JSONArray cats = JsonUtil.getJsonArrayFromStream(is);
			for (int i = 0; i < cats.length(); i++) {
				JSONObject cat = cats.getJSONObject(i);
				HashMap<String, String> mapEntry = new HashMap<String, String>();
				mapEntry.put("title", cat.getString("title"));
				mapEntry.put("description", cat.getString("description"));
				mapEntry.put("assets_path", cat.getString("assets_path"));
				mCatalogData.add(mapEntry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCatalogAdapter = new SimpleAdapter(getActivity(), mCatalogData,
				R.layout.u_book_catalog_listitem, new String[] { "title",
						"description" }, new int[] {
						R.id.catalog_listitem_title,
						R.id.catalog_listitem_description });
		mLvBookCatalog.setAdapter(mCatalogAdapter);

		mLvBookCatalog
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						/*
						 * FragmentBookVolumeTile fragTile = new
						 * FragmentBookVolumeTile();
						 * getFragmentManager().beginTransaction()
						 * .replace(R.layout.w_book_index, fragTile) .commit();
						 */
						Intent intentTarget = new Intent(getActivity(),
								FragmentBookVolumeTile.class);
						Bundle extras = new Bundle();
						String bookPath = mCatalogData.get(position).get(
								"assets_path");
						extras.putString("bookPath",bookPath);
						intentTarget.putExtras(extras);
						startActivity(intentTarget);
					}
				});
	}

	private void initBookMarks(View rootView) {
	}

	private void initBookComments(View rootView) {
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.rb_book_catalog) {

		} else if (view.getId() == R.id.rb_book_bookmark) {
		} else if (view.getId() == R.id.rb_book_comments) {
		}
	}

}
