package com.houyalab.android.backevolution.util;

import com.houyalab.android.backevolution.R;

public class HelperUtil {
	public static String DEFAULT_BEGIN_MUSIC_PATH = "bowl.mp3";
	public static String DEFAULT_END_MUSIC_PATH = "bowl.mp3";
	public static boolean DEFAULT_MUSIC_PLAY_MODE = true;
	public static int DEFAULT_MEDITATION_TIME_DURATION = 10;
	public static int DEFAULT_MEDITATION_TIME_PREPARE = 3;
	public static String[] DEFAULT_SQL_CREATE_LIST = new String[] { 
		"",
		"" };
	public static String[] DEFAULT_SQL_DROP_LIST = new String[] {
			"drop table if exists meditations;", 
			"drop table if exists users;",
			"drop table if exists books;",
			"drop table if exists book_bookmarks;",
			"drop table if exists book_commens;"};
}
