package com.ptit.bookecommerce.utils;

import android.content.Context;
import android.graphics.Point;
import android.provider.Settings.Secure;
import android.view.Display;
import android.view.WindowManager;

public class Device {

	public String getIdDevice(Context context) {
		String android_id = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		return android_id;
	}

	public static Point getSizeWith(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}

	public static Point getSizeImage(Context context) {
		Point p = new Point();
		p.x = getSizeWith(context).x / 3;
		p.y = (4 * p.x) / 3;

		return p;
	}

	public static Point getSizeImageDetails(Context context) {
		Point p = new Point();
		p.x = getSizeWith(context).x / 2;
		p.y = (4 * p.x) / 3;

		return p;
	}
}
