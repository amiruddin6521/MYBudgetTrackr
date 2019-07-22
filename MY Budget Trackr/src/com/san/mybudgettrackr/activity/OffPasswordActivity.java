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

public class OffPasswordActivity extends Activity {

	private DbAdapter dbA;
	private Cursor c;
	private EditText txtCurPass, txtSecWord;
	private Button removePass, resPass;
	private String passDb, wordDb, curPass, secWord;
	private LinearLayout linPas;
	private ListView lisPas;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_off_password);

		getActionBar().setTitle("Remove Password Protection");
		getActionBar().setIcon(R.drawable.ic_settings);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		txtCurPass = (EditText) findViewById(R.id.txtCurPass);
		txtSecWord = (EditText) findViewById(R.id.txtSecWord);
		removePass = (Button) findViewById(R.id.removePass);
		resPass = (Button) findViewById(R.id.resPass);
		linPas = (LinearLayout) findViewById(R.id.linPas);
		lisPas = (ListView) findViewById(R.id.lisPas);

		dbA= new DbAdapter(this);

		c = dbA.getAllPasswordData();
		if (c.moveToFirst()) {
			do {
				passDb = c.getString(1);
				wordDb = c.getString(2);
			} while (c.moveToNext());
		}

		removePass.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				confirmationRemove();
			}
		});

		resPass.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				txtCurPass.setText("");
				txtSecWord.setText("");
			}
		});

		linPas.setOnTouchListener(new OnTouchListener() 
		{
			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View v, MotionEvent me) 
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
				return false;
			}
		});

		lisPas.setOnTouchListener(new OnTouchListener() 
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
			setResult(RESULT_CANCELED);
			break;

		default:
			return super.onOptionsItemSelected(mi);
		}
		return super.onOptionsItemSelected(mi);
	}


	public Dialog confirmationRemove() {
		return new AlertDialog.Builder(this)
		.setMessage("Are you sure want to remove password?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				curPass = txtCurPass.getText().toString();
				secWord = txtSecWord.getText().toString();

				if(!(curPass.equals(passDb)) || !(secWord.equals(wordDb))){
					Message.message(OffPasswordActivity.this, "Please check your Current Password and Security Word");

				}
				else{
					dbA.deleteAllPasswordData();
					Message.message(OffPasswordActivity.this, "Password Protection: OFF");
					setResult(RESULT_OK);
					finish();
				}
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
}
