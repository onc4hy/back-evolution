package com.houyalab.android.backevolution.ui;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.util.JsonUtil;
import com.houyalab.android.backevolution.util.StringUtil;
import com.houyalab.android.backevolution.widget.EvolutionToolGrid;

public class FragmentEvolutionIndex extends BaseFragment implements
		View.OnClickListener {

	private RadioButton mRBtnEvolutionPlan;
	private RadioButton mRBtnEvolutionCheck;
	private RadioButton mRBtnEvolutionAims;

	private AssetManager mAM;
	private TextView mTvEvolutionPreface;
	private EvolutionToolGrid mToolGrid;

	public FragmentEvolutionIndex() {
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
		mRBtnEvolutionAims = (RadioButton) rootView
				.findViewById(R.id.rb_evolution_aims);
		mRBtnEvolutionAims.setOnClickListener(this);

		mTvEvolutionPreface = (TextView) rootView
				.findViewById(R.id.tv_evolution_preface);
		try {
			String arcUrl = "data/articles/evolution_preface.txt";
			try {
				InputStream is = getActivity().getAssets().open(arcUrl);
				String arcContent = "";
				arcContent = StringUtil.getStringFromStream(is);
				is.close();
				mTvEvolutionPreface.setText(arcContent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rootView;
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.rb_evolution_aims) {
			// mToolGrid = new EvolutionToolGrid(getActivity());
			// mToolGrid.showAsDropDown(view);
			AlertDialog.Builder dlgBld = new AlertDialog.Builder(getActivity());
			dlgBld.setMessage(getResources().getString(R.string.tip_wait_build));
			dlgBld.setTitle(getResources().getString(R.string.lbl_tip));
			dlgBld.setPositiveButton(getResources().getString(R.string.lbl_ok),new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dlg, int which) {
					dlg.dismiss();
				}
			});
			dlgBld.create().show();
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {

		} else {

		}
	}

}
