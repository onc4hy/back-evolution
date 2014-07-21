package com.houyalab.android.backevolution.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;

import com.houyalab.android.backevolution.R;

public class FragmentBookComments extends BaseFragment implements View.OnClickListener {

	public FragmentBookComments() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.w_book,container,false);
		return rootView;
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.rb_book_catalog) {
			AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(getActivity());
			dlgBuilder.setTitle(R.string.title_meditation_settings);
			dlgBuilder.show();
		} else if (view.getId() == R.id.rb_book_bookmark) {
		} else if (view.getId() == R.id.rb_book_comments) {
		}
	}

	
}
