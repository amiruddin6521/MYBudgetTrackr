package com.san.mybudgettrackr.fragment;

import java.text.NumberFormat;
import java.util.Calendar;
import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.activity.AddExpenseActivity;
import com.san.mybudgettrackr.activity.AddIncomeActivity;
import com.san.mybudgettrackr.activity.MainActivity;
import com.san.mybudgettrackr.db.DbAdapter;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	private DbAdapter dbA;
	private ImageButton btnIncome, btnExpense;
	private TextView Date, Balance;

	private int year, month, day;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_home, container, false);

		btnIncome = (ImageButton) v.findViewById(R.id.btnIncome);
		btnExpense = (ImageButton) v.findViewById(R.id.btnExpense);
		Date = (TextView) v.findViewById(R.id.Date);
		Balance = (TextView) v.findViewById(R.id.Balance);

		CurrentDate();
		CurrentBalance();

		btnIncome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent((MainActivity)getActivity(),
						AddIncomeActivity.class);
				startActivityForResult(i,1);
			}
		});

		btnExpense.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent((MainActivity)getActivity(),
						AddExpenseActivity.class);
				startActivityForResult(i,1);
			}
		});
		
		return v;
	}


	public void CurrentDate() {
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		Date.setText(new StringBuilder()
		.append(day).append("/").append(month + 1).append("/")
		.append(year));
	}


	public void CurrentBalance(){
		dbA = new DbAdapter(getActivity());
		double sum = dbA.getIncomeBalance() - dbA.getExpenseBalance();

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String value = nf.format(sum);

		Balance.setText("RM " + value);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == 1){
			if (resultCode == -1){
				CurrentBalance();
			}
		}
	}
}