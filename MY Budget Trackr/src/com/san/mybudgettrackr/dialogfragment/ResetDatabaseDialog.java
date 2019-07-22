package com.san.mybudgettrackr.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.san.mybudgettrackr.db.DbAdapter;

public class ResetDatabaseDialog extends DialogFragment {

	private DbAdapter dbA;

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(getActivity())
		.setTitle("Reset All Database!")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setMessage("Are you sure want to reset all database?")
		.setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dbA = new DbAdapter(getActivity());
				dbA.deleteAllIncomeData();
				dbA.deleteAllExpenseData();
				dbA.deleteAllCategoryData();
				dbA.deleteAllReminderData();
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		})
		.create();
	}
}
