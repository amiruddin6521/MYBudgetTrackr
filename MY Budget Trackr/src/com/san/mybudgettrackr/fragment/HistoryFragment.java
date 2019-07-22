package com.san.mybudgettrackr.fragment;

import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.activity.ListExpenseHisActivity;
import com.san.mybudgettrackr.activity.ListIncomeHisActivity;
import com.san.mybudgettrackr.activity.MainActivity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class HistoryFragment extends Fragment{
	private ImageButton hisIn, hisEx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_history, container, false);

		hisIn = (ImageButton) v.findViewById(R.id.hisIn);
		hisEx = (ImageButton) v.findViewById(R.id.hisEx);

		hisIn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent i = new Intent((MainActivity)getActivity(),
						ListIncomeHisActivity.class);
				startActivity(i);
			}
		});

		hisEx.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent i = new Intent((MainActivity)getActivity(),
						ListExpenseHisActivity.class);
				startActivity(i);
			}
		});

		return v;
	}
}