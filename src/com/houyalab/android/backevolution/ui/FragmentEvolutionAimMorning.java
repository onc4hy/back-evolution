package com.houyalab.android.backevolution.ui;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.houyalab.android.backevolution.R;

public class FragmentEvolutionAimMorning extends BaseFragment implements View.OnClickListener {

	private Button mBtnEvolutionPlan;
	private Button mBtnEvolutionCheck;
	private Spinner mSpnEvolutionAims;
	
	public FragmentEvolutionAimMorning() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.w_evolution,container,false);
		mBtnEvolutionPlan = (Button) rootView
				.findViewById(R.id.rb_evolution_plan);
		mBtnEvolutionCheck = (Button) rootView
				.findViewById(R.id.rb_evolution_check);
		mSpnEvolutionAims = (Spinner) rootView
				.findViewById(R.id.rb_evolution_aims);

		mBtnEvolutionPlan.setOnClickListener(this);
		mBtnEvolutionCheck.setOnClickListener(this);
		
		return rootView;
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.rb_evolution_plan) {
			AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(getActivity());
			dlgBuilder.setTitle(R.string.title_meditation_settings);
			dlgBuilder.show();
		} else if (view.getId() == R.id.rb_evolution_check) {
		} else if (view.getId() == R.id.rb_evolution_aims) {
		}
	}
	
}