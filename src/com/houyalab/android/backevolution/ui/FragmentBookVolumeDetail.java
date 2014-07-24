package com.houyalab.android.backevolution.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;

import com.houyalab.android.backevolution.R;

public class FragmentBookVolumeDetail extends BaseFragment implements View.OnClickListener {

	public FragmentBookVolumeDetail() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.rb_book_catalog) {
		} else if (view.getId() == R.id.rb_book_bookmark) {
		} else if (view.getId() == R.id.rb_book_comments) {
		}
	}

	
}
