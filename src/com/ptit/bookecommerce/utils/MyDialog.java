package com.ptit.bookecommerce.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class MyDialog {
	ProgressDialog dialog;

	public void startDialog(Context context, String title, String message) {
		dialog = new ProgressDialog(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setIndeterminate(false);
		dialog.show();
	}

	public void endDialog() {
		dialog.dismiss();
	}
}
