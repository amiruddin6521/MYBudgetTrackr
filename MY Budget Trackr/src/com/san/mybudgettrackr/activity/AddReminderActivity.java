package com.san.mybudgettrackr.activity;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.db.DbAdapter;
import com.san.mybudgettrackr.service.NotificationService;

@SuppressLint("SimpleDateFormat") 
public class AddReminderActivity extends Activity{

	private DbAdapter dbA;
	private EditText priceRem, descriptionRem, titleRem;
	private Spinner categoryRem, addRem;
	private TextView lblDateRem, lblTimeRem;
	private Button saveRem, resetRem;
	private ImageButton btnDateRem, btnTimeRem;
	private LinearLayout linRem;
	private ListView lisRem;
	private StringBuilder aTime;

	private int year, month, day, hour, minute;
	private long id;
	static final int DATE_DIALOG_ID = 999;
	static final int TIME_DIALOG_ID = 1111;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_reminder);

		getActionBar().setTitle("Add Reminder");
		getActionBar().setIcon(R.drawable.ic_reminder);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		priceRem = (EditText) findViewById(R.id.priceRem);
		titleRem = (EditText) findViewById(R.id.titleRem);
		categoryRem = (Spinner) findViewById(R.id.categoryRem);
		addRem = (Spinner) findViewById(R.id.addRem);
		descriptionRem = (EditText) findViewById(R.id.descriptionRem);
		saveRem = (Button) findViewById(R.id.saveRem);
		resetRem = (Button) findViewById(R.id.resetRem);
		btnTimeRem = (ImageButton) findViewById(R.id.btnTimeRem);
		btnDateRem = (ImageButton) findViewById(R.id.btnDateRem);
		lblTimeRem = (TextView) findViewById(R.id.lblTimeRem);
		lblDateRem = (TextView) findViewById(R.id.lblDateRem);
		linRem = (LinearLayout) findViewById(R.id.linRem);
		lisRem = (ListView) findViewById(R.id.lisRem);

		dbA = new DbAdapter(this);
		setCurrentDateTime();
		loadSpinnerData();

		saveRem.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if((priceRem.getText().toString().equals("")) || (titleRem.getText().toString().equals(""))){
					Message.message(AddReminderActivity.this, "Please fill in the blank!");
				}else{
					String pri = priceRem.getText().toString();
					String cat = categoryRem.getSelectedItem().toString();
					String dat = lblDateRem.getText().toString();
					String des = descriptionRem.getText().toString();
					String tim = aTime.toString();
					String add = addRem.getSelectedItem().toString();
					String tit = titleRem.getText().toString();

					id = dbA.insertReminderData(pri, cat, dat, des, tim, add, tit);
					if(id>0){
						Message.message(AddReminderActivity.this, "Saved");
					}

					double value = Double.parseDouble(pri);
					NumberFormat nf = NumberFormat.getNumberInstance();
					nf.setMinimumFractionDigits(2);
					nf.setMaximumFractionDigits(2);
					String amount = nf.format(value);
					String message = "RM" + amount + " added to your " + add + ".";

					Calendar c = Calendar.getInstance();
					c.set(Calendar.MONTH, month);
					c.set(Calendar.DAY_OF_MONTH, day);
					c.set(Calendar.YEAR, year);
					c.set(Calendar.HOUR_OF_DAY, hour);
					c.set(Calendar.MINUTE, minute);

					Bundle b = new Bundle();
					String longString = Long.toString(id);
					int keyid = Integer.parseInt(longString);
					b.putInt("keyid", keyid);

					Intent intent = new Intent(AddReminderActivity.this,
							NotificationService.class);
					intent.putExtra("msg", message);
					intent.putExtras(b);

					PendingIntent pendingIntent = PendingIntent.getService(AddReminderActivity.this, keyid, intent, PendingIntent.FLAG_UPDATE_CURRENT);
					AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
					alarmManager.set(AlarmManager.RTC, c.getTimeInMillis(), pendingIntent);
					finish();
				}
			}
		});

		resetRem.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				priceRem.setText("");
				descriptionRem.setText("");
				titleRem.setText("");
			}
		});

		btnDateRem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		btnTimeRem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});

		linRem.setOnTouchListener(new OnTouchListener() 
		{
			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View v, MotionEvent me) 
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
				return false;
			}
		});

		lisRem.setOnTouchListener(new OnTouchListener() 
		{
			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View v, MotionEvent me) 
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
				return false;
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


	public void setCurrentDateTime() {
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		lblDateRem.setText(new StringBuilder().append(day).append("/")
				.append(month + 1).append("/").append(year));

		updateTime(hour,minute);

		aTime = new StringBuilder().append(hour).append(':')
				.append(minute).append(" ");
	}


	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, year, month,day);

		case TIME_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(this, timePickerListener, hour, minute,
					false);
		}
		return null;
	}


	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker dp, int selectedYear, int selectedMonth,
				int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			lblDateRem.setText(new StringBuilder().append(day).append("/")
					.append(month + 1).append("/").append(year));
		}
	};


	public TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {


		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
			hour   = hourOfDay;
			minute = minutes;

			updateTime(hour,minute);

			aTime = new StringBuilder().append(hour).append(':')
					.append(minute).append(" ");
		}
	};


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

		lblTimeRem.setText(aTime);
	}


	private void loadSpinnerData() {
		// database handler
		dbA= new DbAdapter(this);

		// Spinner Drop down elements
		List<String> lables = dbA.getAllLabels();

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);

		// Drop down layout style - list view with radio button
		dataAdapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		categoryRem.setAdapter(dataAdapter);
	}
}
