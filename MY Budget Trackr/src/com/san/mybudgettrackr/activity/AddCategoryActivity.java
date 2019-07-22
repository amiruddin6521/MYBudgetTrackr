package com.san.mybudgettrackr.activity;

import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.db.DbAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

public class AddCategoryActivity extends Activity {
	
	private DbAdapter dbA;
	private EditText category_add;
	private Button save, reset;
	private LinearLayout linCat;
	private ListView lisCat;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_category);

		getActionBar().setTitle("Add Category");
		getActionBar().setIcon(R.drawable.ic_settings);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		category_add = (EditText) findViewById(R.id.category_add);
		save = (Button) findViewById(R.id.save);
		reset = (Button) findViewById(R.id.reset);
		linCat = (LinearLayout) findViewById(R.id.linCat);
		lisCat = (ListView) findViewById(R.id.lisCat);
		
		dbA= new DbAdapter(this);

		save.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(category_add.equals("")){
					Message.message(AddCategoryActivity.this, "Please fill in the blank!");
				}else{
				String cat = category_add.getText().toString();

				long id = dbA.insertCategoryData(cat);
				if(id>0){
					Message.message(AddCategoryActivity.this, "Saved");
				}
				setResult(RESULT_OK);
				finish();
				}
			}
		});

		reset.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				category_add.setText("");
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
