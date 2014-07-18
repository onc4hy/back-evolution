package com.houyalab.android.backevolution.ui;

import com.houyalab.android.backevolution.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class DialogAbout extends DialogFragment {

	public DialogAbout() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View rootView = inflater.inflate(R.layout.w_about, null);
		dlgBuilder.setView(rootView);

		TextView tvAboutBody = (TextView) rootView
				.findViewById(R.id.tv_about_body);
		tvAboutBody.setText(R.string.txt_about_body);

		dlgBuilder.setPositiveButton(R.string.lbl_ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		return dlgBuilder.create();
	}

}
