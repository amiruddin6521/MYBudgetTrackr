package com.san.mybudgettrackr.activity;

import java.text.NumberFormat;

import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.db.DbAdapter;
import com.san.mybudgettrackr.db.DbHelper;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ListIncomeHisActivity extends Activity{

	private DbAdapter dbA;
	private Cursor c;
	private ListView data;
	private TextView totalIncome;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_income_his);

		getActionBar().setTitle("Income History");
		getActionBar().setIcon(R.drawable.ic_history);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		dbA = new DbAdapter(this);
		
		data = (ListView) findViewById(R.id.data);
		totalIncome = (TextView) findViewById(R.id.totalIncome);
		
		getIncomeTotal();

		String[] from = {DbHelper.CATEGORY_INCOME, DbHelper.DATE_INCOME,
				DbHelper.AMOUNT_INCOME};
		int[] to = {R.id.txtCategory, R.id.txtDate, R.id.txtPrice};
		c = dbA.getIncomeData();
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
				R.layout.row_income_expense, c, from, to);

		data.setAdapter(sca);
		data.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle b = new Bundle();
				c = (Cursor) arg0.getItemAtPosition(arg2);
				int keyid = c.getInt(c
						.getColumnIndex(DbHelper.ID_INCOME));
				b.putInt("keyid", keyid);
				Intent i = new Intent(ListIncomeHisActivity.this,
						ViewIncomeActivity.class);
				i.putExtras(b);
				startActivity(i);
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
	
	
	public void getIncomeTotal(){
		double sum = dbA.getIncomeBalance();

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String value = nf.format(sum);

		totalIncome.setText("RM " + value);
	}


	@Override
	public void onResume() {
		super.onResume();
		c.requery();
	}
}