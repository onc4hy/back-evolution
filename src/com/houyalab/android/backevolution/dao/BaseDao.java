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
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.Preference;

public class BaseDao extends SQLiteOpenHelper{

	protected static String MEDITATION_TABLE_NAME = "meditations";
	protected static String BOOK_COMMENTS_TABLE_NAME = "comments";
	protected static String BOOK_BOOKMARKS_TABLE_NAME = "bookmarks";
	protected static String EVOLUTION_PLAN_TABLE_NAME = "evolution_plans";
	protected static String USER_TABLE_NAME = "user";
	
	public BaseDao(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	public BaseDao(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

}
