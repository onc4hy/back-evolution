package com.houyalab.android.backevolution.util;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbUtil extends SQLiteOpenHelper {

	public DbUtil(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DbUtil(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String[] sqls = HelperUtil.DEFAULT_SQL_CREATE_LIST;
		String sql = "";
		for(int i=0;i<sqls.length;i++) {
			sql= sqls[i];
			db.execSQL(sql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String[] drop_sqls = HelperUtil.DEFAULT_SQL_CREATE_LIST;
		String[] create_sqls = HelperUtil.DEFAULT_SQL_CREATE_LIST;
		String sql = "";
		for(int i=0;i<drop_sqls.length;i++) {
			sql= drop_sqls[i];
			db.execSQL(sql);
		}
		for(int i=0;i<create_sqls.length;i++) {
			sql= create_sqls[i];
			db.execSQL(sql);
		}
	}

}
