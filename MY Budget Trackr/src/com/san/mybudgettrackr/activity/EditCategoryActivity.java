package com.san.mybudgettrackr.activity;

import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.db.DbAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class EditCategoryActivity extends Activity {

	private DbAdapter dbA;
	private Cursor c;
	private EditText category_edit;
	private Button updateCat, deleteCat;
	private LinearLayout linCat;
	private ListView lisCat;

	private int rowId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_category);

		getActionBar().setTitle("Edit Category");
		getActionBar().setIcon(R.drawable.ic_settings);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		category_edit = (EditText) findViewById(R.id.category_edit);
		updateCat = (Button) findViewById(R.id.updateCat);
		deleteCat = (Button) findViewById(R.id.deleteCat);
		linCat = (LinearLayout) findViewById(R.id.linCat);
		lisCat = (ListView) findViewById(R.id.lisCat);

		Bundle b = getIntent().getExtras();
		rowId = b.getInt("keyid");
		dbA = new DbAdapter(this);

		c = dbA.getAllCategoryData(rowId);
		if (c.moveToFirst()) {
			do {
				category_edit.setText(c.getString(1));
			} while (c.moveToNext());
		}

		updateCat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				confirmationUpdate();
			}
		});

		deleteCat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				confirmationDelete();
			}
		});
		
		linCat.setOnTouchListener(new OnTouchListener() 
		{
			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View v, MotionEvent me) 
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
				return false;
			}
		});
		
		lisCat.setOnTouchListener(new OnTouchListener() 
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
	
	
	public Dialog confirmationUpdate() {
		return new AlertDialog.Builder(this)
		.setMessage("Are you sure want to update this transaction?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dbA.updateCategoryData(rowId, category_edit.getText().toString());
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
				dbA.deleteCategoryData(rowId);
				finish();
			}
		})
		.setNegativeButton("No", null)
		.show();
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
}