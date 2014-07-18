package com.houyalab.android.backevolution.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import com.houyalab.android.backevolution.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.Preference;

public class GlobalUtil {

	public static void confirmExit(Context ctx) {
		AlertDialog.Builder dlgBuild = new AlertDialog.Builder(ctx);
		dlgBuild.setTitle(R.string.title_exit);
		dlgBuild.setMessage(R.string.lbl_exit_confirm_info);
		dlgBuild.setPositiveButton(R.string.lbl_ok,new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dlg, int which) {
			}
		});
		dlgBuild.setNegativeButton(R.string.lbl_cancel,new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dlg, int which) {
			}
		});
	}
}
