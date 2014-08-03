package com.houyalab.android.backevolution.ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.model.MeditationCheckItem;
import com.houyalab.android.backevolution.util.JsonUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ActivityMeditationCheck extends BaseActivity {

	private ListView mLvCheckList;
	private SimpleAdapter mAdapterCheckList;
	private ArrayList<Map<String, String>> mListCheckList;
	private List<MeditationCheckItem> mList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_meditation_check);
		
		mLvCheckList = (ListView) findViewById(R.id.lv_meditation_check_list);
		mListCheckList = new ArrayList<Map<String, String>>();
		try {
			mList = new ArrayList();
			MeditationCheckItem data1 = new MeditationCheckItem();
			data1.setTitle("a1");
			data1.setContent("at1");
			mList.add(data1);
			mList.add(data1);
			mList.add(data1);
			//DbUtil dbutil = new DbUtil();
			//mList = dbutil.getMeditationCheckDataList();
			for (int i = 0; i < mList.size(); i++) {
				HashMap<String, String> mapEntry = new HashMap<String, String>();
				
				mapEntry.put("title", mList.get(i).getTitle());
				mapEntry.put("content",mList.get(i).getContent());
				mListCheckList.add(mapEntry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mAdapterCheckList = new SimpleAdapter(this, mListCheckList,
				R.layout.u_meditation_check_listitem, new String[] { "title",
						"content" }, new int[] { R.id.meditation_check_listitem_title,
						R.id.meditation_check_listitem_content });
		mLvCheckList.setAdapter(mAdapterCheckList);

		mLvCheckList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
			}
		});
	}

}
