package com.houyalab.android.backevolution.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
	public static String formatDate(Date date,String formatString) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(formatString,Locale.getDefault());
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		result = sdf.format(date);
		return result;
	}
	
	public static String formatDate(long milliseconds,String formatString) {
		String result = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(milliseconds);
		SimpleDateFormat sdf = new SimpleDateFormat(formatString,Locale.getDefault());
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		result = sdf.format(cal.getTime());
		return result;
	}
}
