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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.JsonUtil;

public class ActivityBookVolumeTile extends BaseActivity implements OnClickListener {

	private ExpandableListView mElvTiles;
	private TextView mTvBookTitle;
	private SimpleExpandableListAdapter mTilesAdapter;
	private ArrayList<Map<String, String>> mTilesGroupData;
	private ArrayList<List<Map<String, String>>> mTilesChildData;
	private AssetManager mAM;
	private String mBookTitle;
	private String mBookPath;
	private Button mBtnExpandAll;
	private Button mBtnCollapseAll;

	public ActivityBookVolumeTile() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_book_volume_tile);
		mElvTiles = (ExpandableListView) findViewById(R.id.elv_book_volumne_tile);
		mBtnCollapseAll = (Button) findViewById(R.id.btn_book_volume_collapse_all);
		mBtnExpandAll = (Button) findViewById(R.id.btn_book_volume_expand_all);
		mTilesGroupData = new ArrayList<Map<String, String>>();
		mTilesChildData = new ArrayList<List<Map<String, String>>>();
		try {
			mAM = getAssets();
			mBookPath = getIntent().getExtras().getString("bookPath");
			mBookTitle = getIntent().getExtras().getString("bookTitle");

			InputStream is = mAM.open(mBookPath);
			JSONObject book = JsonUtil.getJsonObjectFromStream(is);
			JSONArray sections = book.getJSONArray("sections");
			for (int i = 0; i < sections.length(); i++) {
				JSONObject cat = sections.getJSONObject(i);

				HashMap<String, String> mapGroupEntry = new HashMap<String, String>();
				List<Map<String, String>> childList = new ArrayList<Map<String, String>>();
				HashMap<String, String> mapChildEntry = new HashMap<String, String>();
				mapChildEntry.put("bookTitle", mBookTitle);
				mapChildEntry.put("title", cat.getString("title"));
				mapChildEntry.put("content_short",
						cat.getString("content_short"));
				mapChildEntry.put("content", cat.getString("content"));

				mapGroupEntry.put("title", cat.getString("title"));
				mTilesGroupData.add(mapGroupEntry);
				childList.add(mapChildEntry);
				mTilesChildData.add(childList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mTilesAdapter = new SimpleExpandableListAdapter(this, mTilesGroupData,
				R.layout.u_book_volume_listitem_expand,
				R.layout.u_book_volume_listitem_col, new String[] { "title" },
				new int[] { R.id.tv_book_volume_listitem_title }, mTilesChildData,
				R.layout.u_book_volume_listitem_subitem, new String[] { "content_short" },
				new int[] { R.id.tv_volume_listitem_content_short });
		mElvTiles.setAdapter(mTilesAdapter);
		mElvTiles.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View view, int groupPosition,
					int childPosition, long id) {
				Intent intentTarget = new Intent(parent.getContext(),
						ActivityBookVolumeDetail.class);
				Bundle extras = new Bundle();
				String volumeTitle = mTilesChildData.get(groupPosition).get(childPosition).get("title");
				String volumeContent = mTilesChildData.get(groupPosition).get(childPosition).get("content");
				extras.putString("bookTitle", mBookTitle);
				extras.putString("volumeTitle", volumeTitle);
				extras.putString("volumeContent", volumeContent);
				intentTarget.putExtras(extras);
				startActivity(intentTarget);
				return false;
			}
		});
		
		mTvBookTitle = (TextView) findViewById(R.id.tv_book_volume_head_title);
		mTvBookTitle.setText(mBookTitle);
		
		mBtnExpandAll.setOnClickListener(this);
		mBtnCollapseAll.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.btn_book_volume_collapse_all:
			for(int i=0;i<mTilesGroupData.size();i++) {
				mElvTiles.collapseGroup(i);
			}
			break;
		case R.id.btn_book_volume_expand_all:
			for(int i=0;i<mTilesGroupData.size();i++) {
				mElvTiles.expandGroup(i);
			}
			break;
		}
	}
	
}
