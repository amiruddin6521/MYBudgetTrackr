package com.san.mybudgettrackr.activity;

import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.db.DbAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class PasswordActivity extends Activity{

	private DbAdapter dbA;
	private Button btnPass, btnForget;
	private EditText txtPass;
	private String pass, values;
	private RelativeLayout linLog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);

		btnPass = (Button) findViewById(R.id.btnPass);
		btnForget = (Button) findViewById(R.id.btnForget);
		txtPass = (EditText) findViewById(R.id.txtPass);
		linLog = (RelativeLayout) findViewById(R.id.linLog);
		
		dbA = new DbAdapter(this);
		
		btnPass.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				values = txtPass.getText().toString();
				pass = dbA.getPasswordData();
				if(values.equals(pass)){
					Intent i = new Intent(PasswordActivity.this,
							MainActivity.class);
					startActivity(i);
					Message.message(PasswordActivity.this, "Welcome to MY Budget Trackr");
					finish();
				}else{
					Message.message(PasswordActivity.this, "Wrong Password!");
				}
			}
		});
		
		btnForget.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
					Intent i = new Intent(PasswordActivity.this,
							ForgetPasswordActivity.class);
					startActivity(i);
					finish();
			}
		});
		
		linLog.setOnTouchListener(new OnTouchListener() 
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
}