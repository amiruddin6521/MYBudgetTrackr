package com.san.mybudgettrackr.activity;

import android.content.Context;
import android.widget.Toast;

public class Message {
	public static void message(Context c, String message){
		Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
	}
}
