package com.san.mybudgettrackr.fragment;

import com.san.mybudgettrackr.R;
import com.san.mybudgettrackr.activity.ListCategoryActivity;
import com.san.mybudgettrackr.activity.MainActivity;
import com.san.mybudgettrackr.activity.OffPasswordActivity;
import com.san.mybudgettrackr.activity.OnPasswordActivity;
import com.san.mybudgettrackr.db.DbAdapter;
import com.san.mybudgettrackr.dialogfragment.ResetDatabaseDialog;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingsFragment extends Fragment {

	private DbAdapter dbA;
	private Button resetAll, manage;
	private Switch passOnOff;
	private String pass;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_settings, container, false);

		resetAll = (Button) v.findViewById(R.id.resetAll);
		manage = (Button) v.findViewById(R.id.manage);
		passOnOff = (Switch) v.findViewById(R.id.passOnOff);

		dbA = new DbAdapter(getActivity());

		pass = dbA.getPasswordData();
		if(pass.equals("")){
			passOnOff.setChecked(false);
		}
		else{
			passOnOff.setChecked(true);
		}


		passOnOff.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if(isChecked){
					pass = dbA.getPasswordData();
					if(pass.equals("")){
						Intent i = new Intent((MainActivity)getActivity(),
								OnPasswordActivity.class);
						startActivityForResult(i,1);
					}
					else{

					}
				}
				else{
					pass = dbA.getPasswordData();
					if(pass.equals("")){

					}
					else{
						Intent i = new Intent((MainActivity)getActivity(),
								OffPasswordActivity.class);
						startActivityForResult(i,2);
					}
				}

			}
		});

		resetAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ResetDatabaseDialog edd = new ResetDatabaseDialog ();
				edd.show(ft, null);
			}
		});

		manage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent((MainActivity)getActivity(),
						ListCategoryActivity.class);
				startActivity(i);
			}
		});

		return v;
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == 1){
			if (resultCode == 0){
				passOnOff.setChecked(false);
			}
			else{
				passOnOff.setChecked(true);
			}
		}
		else{
			if (resultCode == -1){
				passOnOff.setChecked(false);
			}
			else{
				passOnOff.setChecked(true);
			}
		}
	}
}
