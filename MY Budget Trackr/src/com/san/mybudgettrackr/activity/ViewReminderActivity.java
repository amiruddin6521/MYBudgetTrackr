package com.san.mybudgettrackr.activity;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.db.DbAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ViewReminderActivity extends Activity {

	private DbAdapter dbA;
	private Cursor c;
	private TextView viewRemTitle, viewRemTrans, viewRemCateg, viewRemDates, viewRemTimes, viewRemAmoun, viewRemDescr;
	private Button manageViewRem;

	private String pri, cat, dat, des, tim, tra, tit;
	private int rowId, hour, minute;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_reminder);

		getActionBar().setTitle("View Reminder");
		getActionBar().setIcon(R.drawable.ic_reminder);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		viewRemTitle = (TextView) findViewById(R.id.viewRemTitle);
		viewRemTrans = (TextView) findViewById(R.id.viewRemTrans);
		viewRemCateg = (TextView) findViewById(R.id.viewRemCateg);
		viewRemDates = (TextView) findViewById(R.id.viewRemDates);
		viewRemTimes = (TextView) findViewById(R.id.viewRemTimes);
		viewRemAmoun = (TextView) findViewById(R.id.viewRemAmoun);
		viewRemDescr = (TextView) findViewById(R.id.viewRemDescr);
		manageViewRem = (Button) findViewById(R.id.manageViewRem);

		Bundle b = getIntent().getExtras();
		rowId = b.getInt("keyid");
		dbA = new DbAdapter(this);

		getReminderView();

		manageViewRem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle b = new Bundle();
				b.putInt("keyid", rowId);
				Intent i = new Intent(ViewReminderActivity.this,
						EditReminderActivity.class);
				i.putExtras(b);
				startActivityForResult(i,1);
			}
		});
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem mi) 
	{
		switch (mi.getItemId()) 
		{
		case android.R.id.home: 
			onBackPressed();
			break;

		default:
			return super.onOptionsItemSelected(mi);
		}
		return super.onOptionsItemSelected(mi);
	}


	// Used to convert 24hr format to 12hr format with AM/PM values
	private void updateTime(int hours, int mins) {

		String timeSet = "";
		if (hours > 12) {
			hours -= 12;
			timeSet = "PM";
		} else if (hours == 0) {
			hours += 12;
			timeSet = "AM";
		} else if (hours == 12)
			timeSet = "PM";
		else
			timeSet = "AM";


		String minutes = "";
		if (mins < 10)
			minutes = "0" + mins;
		else
			minutes = String.valueOf(mins);

		// Append in a StringBuilder
		String aTime = new StringBuilder().append(hours).append(':')
				.append(minutes).append(" ").append(timeSet).toString();

		viewRemTimes.setText(aTime);
	}
	
	
	@SuppressLint("SimpleDateFormat") public void getReminderView() {
		c = dbA.getAllReminderData(rowId);
		if (c.moveToFirst()) {
			do {
				pri = c.getString(1);
				cat = c.getString(2);
				dat = c.getString(3);
				des = c.getString(4);
				tim = c.getString(5);
				tra = c.getString(6);
				tit = c.getString(7);
			} while (c.moveToNext());
		}
		
		double value = Double.parseDouble(pri);
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String amo = nf.format(value);
		
		try{
			SimpleDateFormat stf = new SimpleDateFormat("KK:mm");
			Date t = stf.parse(tim);

			hour = t.getHours();
			minute = t.getMinutes();
			
			updateTime(hour, minute);
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}

		viewRemTitle.setText(tit);
		viewRemTrans.setText(tra);
		viewRemCateg.setText(cat);
		viewRemDates.setText(dat);
		viewRemAmoun.setText("RM " + amo);
		viewRemDescr.setText(des);
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == 1){
			if (resultCode == -1){
				finish();
			}
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		getReminderView();
	}
}
