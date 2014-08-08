package com.houyalab.android.backevolution.widget;

import java.util.ArrayList;
import java.util.List;

import com.houyalab.android.backevolution.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

public class EvolutionToolGrid extends PopupWindow implements
		AdapterView.OnItemClickListener {

	private GridView mGridView;
	private Context mContext;
	private BaseAdapter mAdapter;
	private List<String> mToolList;
	private TextView mToolTextView;
	private ImageView mToolImageView;

	public EvolutionToolGrid(Context context) {
		super();

		mContext = context;
		mGridView = new GridView(mContext);
		
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);
		setOutsideTouchable(true);
		
		mToolList = new ArrayList<String>();
		mToolList.add("no item");
		mAdapter = new BaseAdapter() {
			@Override
			public View getView(int position, View view, ViewGroup parent) {
				View rootView;
				LayoutInflater inflater = LayoutInflater.from(mContext);
				rootView = inflater.inflate(R.layout.u_evolution_tool,parent);
				mToolTextView = (TextView)rootView.findViewById(R.id.tv_evolution_tool);
				mToolImageView = (ImageView)rootView.findViewById(R.id.iv_evolution_tool);
				
				mToolTextView.setText(mToolList.get(position));
				return rootView;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public int getCount() {
				return mToolList.size();
			}
		};
		mGridView.setNumColumns(3);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(this);
		setContentView(mGridView);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long id) {

	}

}
