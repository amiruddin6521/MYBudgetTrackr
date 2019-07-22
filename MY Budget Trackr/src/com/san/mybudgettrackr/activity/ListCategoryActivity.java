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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ListCategoryActivity extends Activity{

	private DbAdapter dbA;
	private Cursor c;
	private ListView category_list;
	private Button add_category;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_category);
		
		getActionBar().setTitle("Category List");
		getActionBar().setIcon(R.drawable.ic_settings);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		dbA = new DbAdapter(this);
		
		category_list = (ListView) findViewById(R.id.category_list);
		add_category = (Button) findViewById(R.id.add_category);
		
		String[] from = { DbHelper.LIST_CATEGORY};
		int[] to = {R.id.txtEditCat};
		c = dbA.getCategoryData();
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
				R.layout.row_reminder_category, c, from, to);
		
		category_list.setAdapter(sca);
		category_list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle b = new Bundle();
				c = (Cursor) arg0.getItemAtPosition(arg2);
				int keyid = c.getInt(c
						.getColumnIndex(DbHelper.ID_CATEGORY));
				b.putInt("keyid", keyid);
				Intent i = new Intent(ListCategoryActivity.this,
						EditCategoryActivity.class);
				i.putExtras(b);
				startActivity(i);
			}
		});
		
		add_category.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ListCategoryActivity.this,
						AddCategoryActivity.class);
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
