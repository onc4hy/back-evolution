package com.houyalab.android.backevolution.ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.JsonUtil;

public class ActivityBookVolumeTile extends BaseActivity {

	private ListView mLvTiles;
	private TextView mTvBookTitle;
	private SimpleAdapter mTilesAdapter;
	private ArrayList<Map<String, String>> mTilesData;
	private AssetManager mAM;
	private String mBookTitle;
	private String mBookPath;
	
	public ActivityBookVolumeTile() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_book_volume_tile);
		mLvTiles = (ListView) findViewById(R.id.lv_book_volumne_tile);
		mTilesData = new ArrayList<Map<String, String>>();
		try {
			mAM = getAssets();
			mBookPath = getIntent().getExtras().getString("bookPath");
			mBookTitle = getIntent().getExtras().getString("bookTitle");
			//mBookPath = "data/books/buddhism/diamond_sutra.json";
			InputStream is = mAM.open(mBookPath);
			JSONObject book = JsonUtil.getJsonObjectFromStream(is);
			JSONArray sections = book.getJSONArray("sections");
			for (int i = 0; i < sections.length(); i++) {
				JSONObject cat = sections.getJSONObject(i);
				HashMap<String, String> mapEntry = new HashMap<String, String>();
				mapEntry.put("bookTitle", mBookTitle);
				mapEntry.put("title", cat.getString("title"));
				mapEntry.put("content_short", cat.getString("content_short"));
				mapEntry.put("content", cat.getString("content"));
				mTilesData.add(mapEntry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mTilesAdapter = new SimpleAdapter(this, mTilesData,
				R.layout.u_book_volume_listitem, new String[] { "title",
						"content_short" }, new int[] { R.id.volume_listitem_title,
						R.id.volume_listitem_description });
		mLvTiles.setAdapter(mTilesAdapter);

		mLvTiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intentTarget = new Intent(parent.getContext(),
						ActivityBookVolumeDetail.class);
				Bundle extras = new Bundle();
				String volumeTitle = mTilesData.get(position).get(
						"title");
				String volumeContent = mTilesData.get(position).get(
						"content");
				extras.putString("bookTitle",mBookTitle);
				extras.putString("volumeTitle",volumeTitle);
				extras.putString("volumeContent",volumeContent);
				intentTarget.putExtras(extras);
				startActivity(intentTarget);
				
			}
		});
		mTvBookTitle = (TextView) findViewById(R.id.tv_book_volume_head_title);
		mTvBookTitle.setText(mBookTitle);
	}

}
