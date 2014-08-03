package com.houyalab.android.backevolution.ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.JsonUtil;

public class FragmentBookIndex extends BaseFragment implements
		View.OnClickListener {

	private ListView mLvBookZenCatalog;
	private ListView mLvBookPureCatalog;
	private ExpandableListView mElvBookOtherCatalog;

	private AssetManager mAM;

	private SimpleAdapter mZenCatalogAdapter;
	private SimpleAdapter mPureCatalogAdapter;
	private ExpandableListAdapter mOtherCatalogAdapter;
	private List<Map<String, String>> mZenCatalogData;
	private List<Map<String, String>> mPureCatalogData;
	private List<Map<String, String>> mOtherCatalogGroupData;
	private List<List<Map<String, String>>> mOtherCatalogChildData;

	private FrameLayout mFlCatalog;
	private RadioButton mRbZenCatalog;
	private RadioButton mRbPureCatalog;
	private RadioButton mRbAllCatalog;

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

		mLvBookZenCatalog = (ListView) rootView
				.findViewById(R.id.lv_book_zen_catalog);
		mLvBookPureCatalog = (ListView) rootView
				.findViewById(R.id.lv_book_pure_catalog);
		mElvBookOtherCatalog = (ExpandableListView) rootView
				.findViewById(R.id.ev_book_other_catalog);
		mFlCatalog = (FrameLayout) rootView.findViewById(R.id.fl_book_catalog);

		mRbZenCatalog = (RadioButton) rootView
				.findViewById(R.id.rb_book_zen_catalog);
		mRbPureCatalog = (RadioButton) rootView
				.findViewById(R.id.rb_book_pure_catalog);
		mRbAllCatalog = (RadioButton) rootView
				.findViewById(R.id.rb_book_all_catalog);

		mRbZenCatalog.setOnClickListener(this);
		mRbPureCatalog.setOnClickListener(this);
		mRbAllCatalog.setOnClickListener(this);

		mRbZenCatalog.setChecked(true);

		initBookZenCatalog(rootView);
		initBookPureCatalog(rootView);
		initBookOtherCatalog(rootView);
		return rootView;
	}

	private void initBookZenCatalog(View rootView) {
		mZenCatalogData = new ArrayList<Map<String, String>>();
		try {
			InputStream is = mAM.open("data/books/zen_catalog.json");
			JSONArray cats = JsonUtil.getJsonArrayFromStream(is);
			for (int i = 0; i < cats.length(); i++) {
				JSONObject cat = cats.getJSONObject(i);
				HashMap<String, String> mapEntry = new HashMap<String, String>();
				mapEntry.put("title", cat.getString("title"));
				mapEntry.put("description", cat.getString("description"));
				mapEntry.put("assets_path", cat.getString("assets_path"));
				mZenCatalogData.add(mapEntry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mZenCatalogAdapter = new SimpleAdapter(getActivity(), mZenCatalogData,
				R.layout.u_book_catalog_listitem, new String[] { "title",
						"description" }, new int[] {
						R.id.catalog_listitem_title,
						R.id.catalog_listitem_description });
		mLvBookZenCatalog.setAdapter(mZenCatalogAdapter);

		mLvBookZenCatalog
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intentTarget = new Intent(getActivity(),
								ActivityBookVolumeTile.class);
						Bundle extras = new Bundle();
						String bookPath = mZenCatalogData.get(position).get(
								"assets_path");
						String bookTitle = mZenCatalogData.get(position).get(
								"title");
						extras.putString("bookTitle", bookTitle);
						extras.putString("bookPath", bookPath);
						intentTarget.putExtras(extras);
						startActivity(intentTarget);
					}
				});
	}

	private void initBookPureCatalog(View rootView) {
		mPureCatalogData = new ArrayList<Map<String, String>>();
		try {
			InputStream is = mAM.open("data/books/pure_catalog.json");
			JSONArray cats = JsonUtil.getJsonArrayFromStream(is);
			for (int i = 0; i < cats.length(); i++) {
				JSONObject cat = cats.getJSONObject(i);
				HashMap<String, String> mapEntry = new HashMap<String, String>();
				mapEntry.put("title", cat.getString("title"));
				mapEntry.put("description", cat.getString("description"));
				mapEntry.put("assets_path", cat.getString("assets_path"));
				mPureCatalogData.add(mapEntry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mPureCatalogAdapter = new SimpleAdapter(getActivity(),
				mPureCatalogData, R.layout.u_book_catalog_listitem,
				new String[] { "title", "description" }, new int[] {
						R.id.catalog_listitem_title,
						R.id.catalog_listitem_description });
		mLvBookPureCatalog.setAdapter(mPureCatalogAdapter);

		mLvBookPureCatalog
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intentTarget = new Intent(getActivity(),
								ActivityBookVolumeTile.class);
						Bundle extras = new Bundle();
						String bookTitle = mPureCatalogData.get(position).get(
								"title");
						extras.putString("bookTitle", bookTitle);
						String bookPath = mPureCatalogData.get(position).get(
								"assets_path");
						extras.putString("bookPath", bookPath);
						intentTarget.putExtras(extras);
						startActivity(intentTarget);
					}
				});
	}

	private void initBookOtherCatalog(View rootView) {
		mOtherCatalogGroupData = new ArrayList<Map<String,String>>();
		mOtherCatalogChildData = new ArrayList<List<Map<String, String>>>();
		try {
			InputStream is = mAM.open(
					"data/books/other_catalog.json");
			JSONArray jsonCats = JsonUtil.getJsonArrayFromStream(is);
			for (int i = 0; i < jsonCats.length(); i++) {
				HashMap<String, String> mapGroupEntry = new HashMap<String, String>();
				JSONObject cat = jsonCats.getJSONObject(i);
				JSONArray jsonChilds = cat.getJSONArray("child");
				String groupTitle = cat.getString("title");
				List<Map<String,String>> childLists = new ArrayList<Map<String,String>>();
				for (int j = 0; j < jsonChilds.length(); j++) {
					HashMap<String, String> mapChildEntry = new HashMap<String, String>();
					JSONObject child = jsonChilds.getJSONObject(j);
					mapChildEntry.put("title", child.getString("title"));
					mapChildEntry.put("description", child.getString("description"));
					mapChildEntry.put("assets_path", child.getString("assets_path"));
					childLists.add(mapChildEntry);
				}
				mapGroupEntry.put("title",groupTitle);
				mOtherCatalogGroupData.add(mapGroupEntry);
				mOtherCatalogChildData.add(childLists);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//mOtherCatalogAdapter = new GroupCatalogAdapter(getActivity());

		mOtherCatalogAdapter = new SimpleExpandableListAdapter(getActivity(), mOtherCatalogGroupData,
				R.layout.u_book_catalog_level_listitem_expand,
				R.layout.u_book_catalog_level_listitem_col,
				new String[] { "title" },
				new int[] { R.id.tv_book_catalog_level_listitem_title },
				mOtherCatalogChildData,
				R.layout.u_book_catalog_level_sub_listitem,
				new String[] { "title" },
				new int[] { R.id.tv_book_catalog_level_sub_listitem_title });
		mElvBookOtherCatalog.setAdapter(mOtherCatalogAdapter);

		mElvBookOtherCatalog
				.setOnChildClickListener(new OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View view, int groupPosition, int childPosition,
							long id) {
						Intent intentTarget = new Intent(getActivity(),
								ActivityBookVolumeTile.class);
						Bundle extras = new Bundle();
						String bookTitle = mOtherCatalogChildData.get(
								groupPosition).get(childPosition).get("title");
						String bookPath = mOtherCatalogChildData.get(
								groupPosition).get(childPosition).get("assets_path");
						extras.putString("bookTitle", bookTitle);
						extras.putString("bookPath", bookPath);
						intentTarget.putExtras(extras);
						startActivity(intentTarget);
						return false;
					}
				});
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.rb_book_zen_catalog) {
			mRbZenCatalog.setChecked(true);
			mRbPureCatalog.setChecked(false);
			mRbAllCatalog.setChecked(false);
			mLvBookZenCatalog.setVisibility(View.VISIBLE);
			mLvBookPureCatalog.setVisibility(View.INVISIBLE);
			mElvBookOtherCatalog.setVisibility(View.INVISIBLE);
		} else if (view.getId() == R.id.rb_book_pure_catalog) {
			mRbZenCatalog.setChecked(false);
			mRbPureCatalog.setChecked(true);
			mRbAllCatalog.setChecked(false);
			mLvBookZenCatalog.setVisibility(View.INVISIBLE);
			mLvBookPureCatalog.setVisibility(View.VISIBLE);
			mElvBookOtherCatalog.setVisibility(View.INVISIBLE);
		} else if (view.getId() == R.id.rb_book_all_catalog) {
			mRbZenCatalog.setChecked(false);
			mRbPureCatalog.setChecked(false);
			mRbAllCatalog.setChecked(true);
			mLvBookZenCatalog.setVisibility(View.INVISIBLE);
			mLvBookPureCatalog.setVisibility(View.INVISIBLE);
			mElvBookOtherCatalog.setVisibility(View.VISIBLE);
		}
	}

}
