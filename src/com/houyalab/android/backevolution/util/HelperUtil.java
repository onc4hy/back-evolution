package com.houyalab.android.backevolution.util;

import com.houyalab.android.backevolution.R;

public class HelperUtil {
	public static String MEDITATION_SERVICE = "com.houyalab.android.backevolution.meditationservice";
	
	public static String DEFAULT_BEGIN_MUSIC_PATH = "bowl.mp3";
	public static String DEFAULT_END_MUSIC_PATH = "bowl.mp3";
	public static boolean DEFAULT_MUSIC_PLAY_MODE = true;
	public static int DEFAULT_MEDITATION_TIME_DURATION = 10;
	public static int DEFAULT_MEDITATION_TIME_PREPARE = 3;
	public static float DEFAULT_TEXT_SIZE = 22;

	public static String MESSAGE_MEDITATION_START = "meditation_start";
	public static String MESSAGE_MEDITATION_RUNNING = "meditation_running";
	public static String MESSAGE_MEDITATION_STOP = "meditation_stop";

	public static String[] DEFAULT_SQL_CREATE_LIST = new String[] { "", "" };
	public static String[] DEFAULT_SQL_DROP_LIST = new String[] {
			"drop table if exists meditations;", "drop table if exists users;",
			"drop table if exists books;",
			"drop table if exists book_bookmarks;",
			"drop table if exists book_commens;" };
}
