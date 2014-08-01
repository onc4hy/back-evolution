package com.houyalab.android.backevolution.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.json.JSONArray;
import org.json.JSONObject;

public class StringUtil {

	public static String getStringFromStream(InputStream is) {
		String result = "";
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuffer sb = new StringBuffer();
			String row = null;
			char[] buf = new char[1024];
			int len;
			while( (len = br.read(buf)) >= 0) {
				sb.append(buf);
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
