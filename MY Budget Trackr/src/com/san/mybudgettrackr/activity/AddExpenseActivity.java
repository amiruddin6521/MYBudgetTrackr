package com.san.mybudgettrackr.activity;

import java.util.Calendar;
import java.util.List;

import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.db.DbAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
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

public class AddExpenseActivity extends Activity {

	private DbAdapter dbA;
	private EditText price, description;
	private Button save, reset;
	private Spinner category;
	private TextView lblDate;
	private ImageButton btnDate;
	private LinearLayout linEx;
	private ListView lisEx;

	private int year, month, day;
	static final int DATE_DIALOG_ID = 999;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expense);

		getActionBar().setTitle("Add Expense");
		getActionBar().setIcon(R.drawable.ic_home);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		price = (EditText) findViewById(R.id.price);
		category = (Spinner) findViewById(R.id.category);
		description = (EditText) findViewById(R.id.description);
		save = (Button) findViewById(R.id.save);
		reset = (Button) findViewById(R.id.reset);
		btnDate = (ImageButton) findViewById(R.id.btnDate);
		lblDate = (TextView) findViewById(R.id.lblDate);
		linEx = (LinearLayout) findViewById(R.id.linEx);
		lisEx = (ListView) findViewById(R.id.lisEx);
		
		dbA=new DbAdapter(this);
		setCurrentDate();
		loadSpinnerData();

		save.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(price.getText().toString().equals("")){
					Message.message(AddExpenseActivity.this, "Please fill in the blank!");
				}else{
				String pri = price.getText().toString();
				String cat = category.getSelectedItem().toString();
				String dat = lblDate.getText().toString();
				String des = description.getText().toString();

				long id = dbA.insertExpenseData(pri, cat, dat, des);
				if(id>0){
					Message.message(AddExpenseActivity.this, "Saved");
				}
				setResult(RESULT_OK);
				finish();
				}
			}
		});

		reset.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				price.setText("");
				description.setText("");
			}
		});

		btnDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		linEx.setOnTouchListener(new OnTouchListener() 
		{
			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View v, MotionEvent me) 
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
				return false;
			}
		});
		
		lisEx.setOnTouchListener(new OnTouchListener() 
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


	public void setCurrentDate() {
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		lblDate.setText(new StringBuilder().append(day).append("/")
				.append(month + 1).append("/").append(year));
	}


	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, year, month,day);
		}
		return null;
	}


	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker dp, int selectedYear, int selectedMonth,
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
}