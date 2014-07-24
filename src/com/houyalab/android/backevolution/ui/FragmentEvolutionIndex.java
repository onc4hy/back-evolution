package com.houyalab.android.backevolution.ui;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.JsonUtil;

public class FragmentEvolutionIndex extends BaseFragment implements View.OnClickListener {

	private RadioButton mRBtnEvolutionPlan;
	private RadioButton mRBtnEvolutionCheck;
	private RadioButton mRBtnEvolutionAims;
	
	private AssetManager mAM;
	private TextView mTvEvolutionHeadline;
	private TextView mTvEvolutionRemark;
	
	public FragmentEvolutionIndex() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.w_evolution_index,container,false);
		mRBtnEvolutionPlan = (RadioButton) rootView
				.findViewById(R.id.rb_evolution_plan);
		mRBtnEvolutionCheck = (RadioButton) rootView
				.findViewById(R.id.rb_evolution_check);
		mRBtnEvolutionAims = (RadioButton) rootView
				.findViewById(R.id.rb_evolution_aims);

		mRBtnEvolutionPlan.setOnClickListener(this);
		mRBtnEvolutionCheck.setOnClickListener(this);
		
		mTvEvolutionHeadline = (TextView)rootView.findViewById(R.id.tv_evolution_headline);
		mTvEvolutionRemark = (TextView)rootView.findViewById(R.id.tv_evolution_remark);
		try {
			mAM = getActivity().getAssets();
			InputStream is = mAM.open("data/articles/articles.json");
			JSONArray arcs =  JsonUtil.getJsonArrayFromStream(is);
			for(int i=0;i<arcs.length();i++) {
				JSONObject arc = arcs.getJSONObject(i);
				if (arc.has("name")) {
					if (arc.getString("name").equalsIgnoreCase("evolution-headline")) {
						mTvEvolutionHeadline.setText(arc.getString("content"));
					}
					if (arc.getString("name").equalsIgnoreCase("evolution-remark")) {
						mTvEvolutionRemark.setText(arc.getString("content"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rootView;
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.rb_evolution_plan) {
		} else if (view.getId() == R.id.rb_evolution_check) {
		} else if (view.getId() == R.id.rb_evolution_aims) {
		}
	}
	
}
