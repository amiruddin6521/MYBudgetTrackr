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

public class OnPasswordActivity extends Activity {

	private DbAdapter dbA;
	private EditText txtSetPass, txtConfirmPass, txtSecurityWord;
	private Button savePass, resetPass;
	private String setPass, confirmPass, secWord;
	private LinearLayout linPas;
	private ListView lisPas;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_on_password);

		getActionBar().setTitle("Set Password Protection");
		getActionBar().setIcon(R.drawable.ic_settings);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		txtSetPass = (EditText) findViewById(R.id.txtSetPass);
		txtConfirmPass = (EditText) findViewById(R.id.txtConfirmPass);
		txtSecurityWord = (EditText) findViewById(R.id.txtSecurityWord);
		savePass = (Button) findViewById(R.id.savePass);
		resetPass = (Button) findViewById(R.id.resetPass);
		linPas = (LinearLayout) findViewById(R.id.linPas);
		lisPas = (ListView) findViewById(R.id.lisPas);

		dbA = new DbAdapter(this);

		savePass.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				setPass = txtSetPass.getText().toString();
				confirmPass = txtConfirmPass.getText().toString();
				secWord = txtSecurityWord.getText().toString();

				if((secWord.equals("")) || !(confirmPass.equals(setPass))){
					Message.message(OnPasswordActivity.this, "Please check your Password and Security Word");
				}
				else{
					dbA.insertPasswordData(setPass, secWord);
					Message.message(OnPasswordActivity.this, "Password Protection: ON");
					setResult(RESULT_OK);
					finish();
				}
			}
		});

		resetPass.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				txtSetPass.setText("");
				txtConfirmPass.setText("");
				txtSecurityWord.setText("");
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
}
