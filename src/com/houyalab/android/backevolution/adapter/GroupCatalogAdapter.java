package com.houyalab.android.backevolution.adapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.JsonUtil;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class GroupCatalogAdapter extends BaseExpandableListAdapter {

	private ArrayList<String> mGroupCatalogHeadData;
	private ArrayList<List<String>> mGroupCatalogChildData;
	private Context mContext;

	public GroupCatalogAdapter(Context ctx) {
		super();

		mContext = ctx;
		mGroupCatalogHeadData = new ArrayList<String>();
		mGroupCatalogChildData = new ArrayList<List<String>>();
		try {
			InputStream is = ctx.getAssets().open(
					"data/books/other_catalog.json");
			JSONArray jsonCats = JsonUtil.getJsonArrayFromStream(is);
			for (int i = 0; i < jsonCats.length(); i++) {
				JSONObject cat = jsonCats.getJSONObject(i);
				String title = cat.getString("title");
				JSONArray jsonChilds = cat.getJSONArray("child");
				List<String> lists = new ArrayList<String>();
				for (int j = 0; j < jsonChilds.length(); j++) {
					lists.add(jsonChilds.getJSONObject(j).getString("title"));
				}
				mGroupCatalogHeadData.add(title);
				mGroupCatalogChildData.add(lists);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mGroupCatalogChildData.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		String title = getChild(groupPosition, childPosition).toString();
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.u_book_level_catalog_sub_listitem,null);
		}
		
		TextView tv = (TextView)convertView.findViewById(R.id.tv_level_catalog_sub_listitem_title);
		tv.setText(title);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mGroupCatalogChildData.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mGroupCatalogHeadData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return mGroupCatalogHeadData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String title = getGroup(groupPosition).toString();
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.u_book_level_catalog_listitem,null);
			convertView.setPadding(60, 0, 0, 0);
		}
		TextView tv = (TextView)convertView.findViewById(R.id.tv_level_catalog_listitem_title);
		tv.setText(title);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
