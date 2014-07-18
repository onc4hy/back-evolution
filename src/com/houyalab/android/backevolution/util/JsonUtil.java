package com.houyalab.android.backevolution.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtil {

	public static JSONObject getJsonObjectFromStream(InputStream is) {
		JSONObject result = new JSONObject();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringWriter sw = new StringWriter();
			String row = null;
			while( (row = br.readLine()) != null ) {
				sw.write(row);
			}
			result = new JSONObject(sw.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static JSONArray getJsonArrayFromStream(InputStream is) {
		JSONArray result = null;
		
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(is));
			StringWriter sw = new StringWriter();
			String row = null;
			while( (row = br.readLine()) != null ) {
				sw.write(row);
			}
			result = new JSONArray(sw.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
