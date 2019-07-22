package com.san.mybudgettrackr.service;

import java.io.IOException;
import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.activity.ViewExpenseActivity;
import com.san.mybudgettrackr.activity.ViewIncomeActivity;
import com.san.mybudgettrackr.db.DbAdapter;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;

public class NotificationService extends Service {

	private DbAdapter dbA;
	private NotificationManager notificationManager;
	private PendingIntent contentIntent;
	private String pri, cat, dat, des, add;
	private int rowId;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		String message = intent.getStringExtra("msg");
		Bundle b = intent.getExtras();
		rowId = b.getInt("keyid");
		dbA = new DbAdapter(this);

		Cursor c = dbA.getAllReminderData(rowId);
		if (c.moveToFirst()) {
			do {
				pri = c.getString(1);
				cat = c.getString(2);
				dat = c.getString(3);
				des = c.getString(4);
				add = c.getString(6);
			} while (c.moveToNext());
		}

		if (add.equals("Income")){
			long id = dbA.insertIncomeData(pri, cat, dat, des);
			
			Bundle notificationBundle = new Bundle();
			String longString = Long.toString(id);
			int keyid = Integer.parseInt(longString);
			notificationBundle.putInt("keyid", keyid);
			
			notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
			Intent notificationIntent = new Intent(this,ViewIncomeActivity.class);
			notificationIntent.putExtras(notificationBundle);
			contentIntent=PendingIntent.getActivity(this, 0, notificationIntent, 0);
		}
		else{
			long id = dbA.insertExpenseData(pri, cat, dat, des);
			
			Bundle notificationBundle = new Bundle();
			String longString = Long.toString(id);
			int keyid = Integer.parseInt(longString);
			notificationBundle.putInt("keyid", keyid);
			
			notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
			Intent notificationIntent = new Intent(this,ViewExpenseActivity.class);
			notificationIntent.putExtras(notificationBundle);
			contentIntent=PendingIntent.getActivity(this, 0, notificationIntent, 0);
		}

		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = "You got a new notification.";
		long when = System.currentTimeMillis();
		CharSequence contentTitle="MY Budget Trackr";
		CharSequence contentText = message;

		Notification notification=new Notification(icon, tickerText, when);
		notification.setLatestEventInfo(this, contentTitle, contentText, contentIntent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify( 123, notification);
		

		Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(1000);

		try {
			Uri myUri =RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);; // initialize Uri here
			MediaPlayer mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setDataSource(getApplicationContext(), myUri);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		stopSelf();
	}
}
