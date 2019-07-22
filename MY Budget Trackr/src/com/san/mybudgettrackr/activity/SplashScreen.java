package com.san.mybudgettrackr.activity;

import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.db.DbAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
	
	private DbAdapter dbA;
	private String pass;

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);

		dbA = new DbAdapter(this);
		
		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				pass = dbA.getPasswordData();
				if(pass.equals("")){
					Intent i = new Intent(SplashScreen.this,
							MainActivity.class);
					startActivity(i);
					Message.message(SplashScreen.this, "Welcome to MY Budget Trackr");
				}
				else{
					Intent i = new Intent(SplashScreen.this,
							PasswordActivity.class);
					startActivity(i);
				}

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

}