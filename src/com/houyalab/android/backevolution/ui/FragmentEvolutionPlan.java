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
import android.widget.Spinner;
import android.widget.TextView;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.JsonUtil;

public class FragmentEvolutionPlan extends BaseFragment implements
		View.OnClickListener {

	private Button mBtnEvolutionPlan;
	private Button mBtnEvolutionCheck;
	private Spinner mSpnEvolutionAims;

	public FragmentEvolutionPlan() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.w_evolution_index, container,
				false);

		return rootView;
	}

	@Override
	public void onClick(View view) {
	}

}
