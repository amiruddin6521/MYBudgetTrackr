package com.san.mybudgettrackr.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.db.DbAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
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

public class EditHisInActivity extends Activity {
	
	private DbAdapter dbA;
	private Cursor c;
	private EditText price, description;
	private Spinner category;
	private Button update, delete;
	private ImageButton btnDate;
	private TextView lblDate;
	private LinearLayout lin;
	private ListView lis;

	private int rowId, year, month, day;
	static final int DATE_DIALOG_ID = 999;

	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_income_his);

		getActionBar().setTitle("Edit Income");
		getActionBar().setIcon(R.drawable.ic_history);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		price = (EditText) findViewById(R.id.price);
		category = (Spinner) findViewById(R.id.category);
		description = (EditText) findViewById(R.id.description);
		update = (Button) findViewById(R.id.update);
		delete = (Button) findViewById(R.id.delete);
		btnDate = (ImageButton) findViewById(R.id.btnDate);
		lblDate = (TextView) findViewById(R.id.lblDate);
		lin = (LinearLayout) findViewById(R.id.lin);
		lis = (ListView) findViewById(R.id.lis);

		Bundle b = getIntent().getExtras();
		rowId = b.getInt("keyid");
		dbA = new DbAdapter(this);
		loadSpinnerData();

		c = dbA.getAllIncomeData(rowId);
		if (c.moveToFirst()) {
			do {
				price.setText(c.getString(1));
				category.setSelection(((ArrayAdapter<String>)category.getAdapter()).getPosition(c.getString(2)));
				lblDate.setText(c.getString(3));
				description.setText(c.getString(4));
			} while (c.moveToNext());
		}

		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				confirmationUpdate();
			}
		});

		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				confirmationDelete();
			}
		});

		btnDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		lin.setOnTouchListener(new OnTouchListener() 
		{
			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View v, MotionEvent me) 
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
				return false;
			}
		});
		
		lis.setOnTouchListener(new OnTouchListener() 
		{
			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View v, MotionEvent me) 
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
				return false;
			}
		});
		getCurrentDate();
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


	@SuppressLint("SimpleDateFormat")
	public void getCurrentDate() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date d = sdf.parse(lblDate.getText().toString());

			year = d.getYear();
			month = d.getMonth();
			day = d.getDate();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}


	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, (year + 1900), month, day);
		}
		return null;
	}


	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker v, int selectedYear, int selectedMonth,
				int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			lblDate.setText(new StringBuilder().append(day).append("/")
					.append(month + 1).append("/").append(year));
		}
	};


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
		category.setAdapter(dataAdapter);
	}


	public Dialog confirmationUpdate() {
		return new AlertDialog.Builder(this)
		.setMessage("Are you sure want to update this transaction?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dbA.updateIncomeData(rowId, price.getText().toString(),
						category.getSelectedItem().toString(), lblDate.getText().toString(), description.getText().toString());
				finish();
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
	
	
	public Dialog confirmationDelete() {
		return new AlertDialog.Builder(this)
		.setMessage("Are you sure want to delete this transaction?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dbA.deleteIncomeData(rowId);
				setResult(RESULT_OK);
				finish();
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
}
