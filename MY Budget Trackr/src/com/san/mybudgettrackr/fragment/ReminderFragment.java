package com.san.mybudgettrackr.fragment;

import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.activity.AddReminderActivity;
import com.san.mybudgettrackr.activity.ListReminderActivity;
import com.san.mybudgettrackr.activity.MainActivity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class ReminderFragment extends Fragment {
	private ImageButton addReminder, listReminder;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_reminder, container, false);
		addReminder = (ImageButton) v.findViewById(R.id.addReminder);
		listReminder = (ImageButton) v.findViewById(R.id.listReminder);

		addReminder.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent i = new Intent((MainActivity)getActivity(),
						AddReminderActivity.class);
				startActivity(i);
			}
		});

		listReminder.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent i = new Intent((MainActivity)getActivity(),
						ListReminderActivity.class);
				startActivity(i);
			}
		});

		return v;
	}
}
