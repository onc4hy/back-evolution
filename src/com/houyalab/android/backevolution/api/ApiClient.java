package com.houyalab.android.backevolution.api;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;

import com.houyalab.android.backevolution.util.JsonUtil;

public class ApiClient {
	public static final String ROOT_URL = "";
	public static final String CATALOG_REMOTE_GET_URL = "";
	public static final String CATALOG_LOCAL_GET_URL = "data/books/catalog.json";

	public static List getBookCatalog(Context ctx) {
		List result = new ArrayList();
		JSONArray items;
		JSONObject item;
		try {
			InputStream is = ctx.getResources().getAssets()
					.open(CATALOG_LOCAL_GET_URL);
			items = JsonUtil.getJsonArrayFromStream(is);
			for(int i=0;i<items.length();i++) {
				item = items.getJSONObject(i);
				result.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List getBookMarks(Context ctx, int userId) {
		List result = new ArrayList();
		return result;
	}

	public static List getBookNotes(Context ctx, int bookId) {
		List result = new ArrayList();
		return result;
	}
}
