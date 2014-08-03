package com.houyalab.android.backevolution.dao;

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
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.preference.Preference;

public class BookDao extends BaseDao {

	public BookDao(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

}
