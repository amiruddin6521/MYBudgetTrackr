package com.san.mybudgettrackr.activity;

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

public class ListReminderActivity extends Activity {
	private DbAdapter dbA;
	private Cursor c;
	private ListView listRem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_reminder);

		getActionBar().setTitle("Reminder List");
		getActionBar().setIcon(R.drawable.ic_reminder);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		dbA = new DbAdapter(this);
		
		listRem = (ListView) findViewById(R.id.listRem);

		String[] from = {DbHelper.TITLE_REMINDER};
		int[] to = {R.id.txtEditCat};
		c = dbA.getReminderData();
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
				R.layout.row_reminder_category, c, from, to);

		listRem.setAdapter(sca);
		listRem.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle b = new Bundle();
				c = (Cursor) arg0.getItemAtPosition(arg2);
				int keyid = c.getInt(c
						.getColumnIndex(DbHelper.ID_REMINDER));
				b.putInt("keyid", keyid);
				Intent i = new Intent(ListReminderActivity.this,
						ViewReminderActivity.class);
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


	@Override
	public void onResume() {
		super.onResume();
		c.requery();
	}
}
