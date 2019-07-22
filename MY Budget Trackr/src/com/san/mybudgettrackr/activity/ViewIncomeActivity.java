package com.san.mybudgettrackr.activity;

import java.text.NumberFormat;

import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.db.DbAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ViewIncomeActivity extends Activity {

	private DbAdapter dbA;
	private Cursor c;
	private TextView viewInCat, viewInAmo, viewInDat, viewInDes;
	private Button manageViewIn;

	private String pri, cat, dat, des;
	private int rowId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_income);

		getActionBar().setTitle("View Income");
		getActionBar().setIcon(R.drawable.ic_history);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		viewInCat = (TextView) findViewById(R.id.viewInCat);
		viewInAmo = (TextView) findViewById(R.id.viewInAmo);
		viewInDat = (TextView) findViewById(R.id.viewInDat);
		viewInDes = (TextView) findViewById(R.id.viewInDes);
		manageViewIn = (Button) findViewById(R.id.manageViewIn);

		Bundle b = getIntent().getExtras();
		rowId = b.getInt("keyid");
		dbA = new DbAdapter(this);

		getIncomeView();

		manageViewIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle b = new Bundle();
				b.putInt("keyid", rowId);
				Intent i = new Intent(ViewIncomeActivity.this,
						EditHisInActivity.class);
				i.putExtras(b);
				startActivityForResult(i,1);
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
	
	
	public void getIncomeView(){
		c = dbA.getAllIncomeData(rowId);
		if (c.moveToFirst()) {
			do {
				pri = c.getString(1);
				cat = c.getString(2);
				dat = c.getString(3);
				des = c.getString(4);
			} while (c.moveToNext());
		}
		
		double value = Double.parseDouble(pri);
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String amo = nf.format(value);

		viewInCat.setText(cat);
		viewInAmo.setText("RM " + amo);
		viewInDat.setText(dat);
		viewInDes.setText(des);
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == 1){
			if (resultCode == -1){
				finish();
			}
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		getIncomeView();
	}
}
