package com.houyalab.android.backevolution.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.houyalab.android.backevolution.R;
import com.houyalab.android.backevolution.model.MeditationRecord;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.preference.Preference;

public class MeditationDao extends BaseDao {

	public MeditationDao(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public MeditationRecord findById(Long id) {
		MeditationRecord record = new MeditationRecord();
		return record;
	}
	
	public MeditationRecord save(MeditationRecord record) {
		if (record.getId() == null) {
			return update(record);
		}else {
			return create(record);
		}
	}

	public MeditationRecord create(MeditationRecord record) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("title",record.getTitle());
		long id = db.insertOrThrow(BaseDao.MEDITATION_TABLE_NAME, null, values);
		return null;
	}

	public MeditationRecord update(MeditationRecord record) {
		SQLiteDatabase db = getWritableDatabase();
		return null;
	}

	public void delete(Long id) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(MEDITATION_TABLE_NAME, "",new String[] {});
	}
	
	public List<MeditationRecord> findAll() {
		SQLiteDatabase db = getReadableDatabase();
		//db.query(MEDITATION_TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy)
		List<MeditationRecord> lists = new ArrayList<MeditationRecord>();
		return lists;
	}
}
