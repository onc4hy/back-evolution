package com.houyalab.android.backevolution.util;

import com.houyalab.android.backevolution.ui.FragmentBookVolumeDetail;
import com.houyalab.android.backevolution.ui.LoginActivity;
import com.houyalab.android.backevolution.ui.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

public class UIUtil {

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    
	public static void showHome(Activity activity) {
		Intent intent = new Intent(activity, MainActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}
    
	public static void showLoginDialog(Context context) {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	public static void showBookVolumeDetail(Context context, int volumeId) {
		Intent intent = new Intent(context, FragmentBookVolumeDetail.class);
		intent.putExtra("volume_id", volumeId);
		context.startActivity(intent);
	}
}
