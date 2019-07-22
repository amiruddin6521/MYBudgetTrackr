package com.san.mybudgettrackr.activity;

import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.db.DbAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class ForgetPasswordActivity extends Activity {

	private DbAdapter dbA;
	private Cursor c;
	private Button btnRePass, btnBack;
	private EditText txtRePass;
	private String wordDb, values;
	private RelativeLayout linFor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);

		btnRePass = (Button) findViewById(R.id.btnRePass);
		btnBack = (Button) findViewById(R.id.btnBack);
		txtRePass = (EditText) findViewById(R.id.txtRePass);
		linFor = (RelativeLayout) findViewById(R.id.linFor);
		
		dbA = new DbAdapter(this);
		
		c = dbA.getAllPasswordData();
		if (c.moveToFirst()) {
			do {
				wordDb = c.getString(2);
			} while (c.moveToNext());
		}
		
		btnRePass.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				confirmationRemove();
			}
		});
		
		btnBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
					Intent i = new Intent(ForgetPasswordActivity.this,
							PasswordActivity.class);
					startActivity(i);
					finish();
			}
		});
		
		linFor.setOnTouchListener(new OnTouchListener() 
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
	
	
	public Dialog confirmationRemove() {
		return new AlertDialog.Builder(this)
		.setMessage("Are you sure want to remove password?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				values = txtRePass.getText().toString();
				if(values.equals(wordDb)){
					Intent i = new Intent(ForgetPasswordActivity.this,
							MainActivity.class);
					startActivity(i);
					dbA.deleteAllPasswordData();
					Message.message(ForgetPasswordActivity.this, "Password Protection: OFF");
					finish();
				}else{
					Message.message(ForgetPasswordActivity.this, "Wrong Security Word!");
				}
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent ke) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			Intent i = new Intent(ForgetPasswordActivity.this,
					PasswordActivity.class);
			startActivity(i);
			finish();
		}
		return false;
	}
}
