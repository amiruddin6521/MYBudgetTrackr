package com.san.mybudgettrackr.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "mybudgettrackr";
	private static final int DATABASE_VERSION = 8;

	//Table Income
	public static final String TABLE_INCOME = "Income";
	public static final String ID_INCOME = "_id";
	public static final String AMOUNT_INCOME = "Amount";
	public static final String CATEGORY_INCOME = "Category";
	public static final String DATE_INCOME = "Date";
	public static final String DESCRIPTION_INCOME = "Description";

	//Table Expense
	public static final String TABLE_EXPENSE = "Expense";
	public static final String ID_EXPENSE = "_id";
	public static final String AMOUNT_EXPENSE = "Amount";
	public static final String CATEGORY_EXPENSE = "Category";
	public static final String DATE_EXPENSE = "Date";
	public static final String DESCRIPTION_EXPENSE = "Description";

	//Table Reminder
	public static final String TABLE_REMINDER = "Reminder";
	public static final String ID_REMINDER = "_id";
	public static final String AMOUNT_REMINDER = "Amount";
	public static final String CATEGORY_REMINDER = "Category";
	public static final String DATE_REMINDER = "Date";
	public static final String DESCRIPTION_REMINDER = "Description";
	public static final String TIME_REMINDER = "Time";
	public static final String ADDTO_REMINDER = "AddTo";
	public static final String TITLE_REMINDER = "Title";

	//Table Category
	public static final String TABLE_CATEGORY = "Category";
	public static final String ID_CATEGORY = "_id";
	public static final String LIST_CATEGORY = "ListCategory";

	//Table Password
	public static final String TABLE_PASSWORD = "Password";
	public static final String ID_PASSWORD = "_id";
	public static final String SET_PASSWORD = "SetPassword";
	public static final String SECURITY_WORD = "SecurityWord";


	//Create Table Income
	public static final String CREATE_INCOME = "CREATE TABLE " + TABLE_INCOME + "(" + ID_INCOME
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + AMOUNT_INCOME + " REAL NOT NULL, " + CATEGORY_INCOME
			+ " TEXT NOT NULL, " + DATE_INCOME + " TEXT NOT NULL, " + DESCRIPTION_INCOME + " TEXT NOT NULL);";

	//Create Table Expense
	public static final String CREATE_EXPENSE = "CREATE TABLE " + TABLE_EXPENSE + "(" + ID_EXPENSE
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + AMOUNT_EXPENSE + " REAL NOT NULL, " + CATEGORY_EXPENSE
			+ " TEXT NOT NULL, " + DATE_EXPENSE + " TEXT NOT NULL, " + DESCRIPTION_EXPENSE + " TEXT NOT NULL);";

	//Create Table Reminder
	public static final String CREATE_REMINDER = "CREATE TABLE " + TABLE_REMINDER + "(" + ID_REMINDER
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + AMOUNT_REMINDER + " REAL NOT NULL, " + CATEGORY_REMINDER
			+ " TEXT NOT NULL, " + DATE_REMINDER + " TEXT NOT NULL, " + DESCRIPTION_REMINDER + " TEXT NOT NULL, "
			+ TIME_REMINDER + " TEXT NOT NULL, " + ADDTO_REMINDER + " TEXT NOT NULL, " + TITLE_REMINDER + " TEXT NOT NULL);";

	//Create Table Category
	public static final String CREATE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + "(" 
			+ ID_CATEGORY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LIST_CATEGORY + " TEXT NOT NULL);";
	
	//Create Table Password
	public static final String CREATE_PASSWORD = "CREATE TABLE " + TABLE_PASSWORD + "(" 
				+ ID_PASSWORD + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SET_PASSWORD + " TEXT NOT NULL, " 
				+ SECURITY_WORD + " TEXT NOT NULL);";


	//Drop Table Income
	public static final String DROP_INCOME = "DROP TABLE IF EXISTS " + TABLE_INCOME; 

	//Drop Table Expense
	public static final String DROP_EXPENSE = "DROP TABLE IF EXISTS " + TABLE_EXPENSE; 

	//Drop Table Reminder
	public static final String DROP_REMINDER = "DROP TABLE IF EXISTS " + TABLE_REMINDER;

	//Drop Table Category
	public static final String DROP_CATEGORY = "DROP TABLE IF EXISTS " + TABLE_CATEGORY;
	
	//Drop Table Password
	public static final String DROP_PASSWORD = "DROP TABLE IF EXISTS " + TABLE_PASSWORD;


	public DbHelper (Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db){
		try{
			//Message.message(context, "onCreate called");
			db.execSQL(CREATE_INCOME);
			db.execSQL(CREATE_EXPENSE);
			db.execSQL(CREATE_REMINDER);
			db.execSQL(CREATE_CATEGORY);
			db.execSQL(CREATE_PASSWORD);
			db.execSQL("INSERT INTO " + TABLE_CATEGORY + " (" + LIST_CATEGORY + ") VALUES" +
					"('Allowance')," +
					"('Auto')," +
					"('Bill Payments')," +
					"('Bonus')," +
					"('Book/Stationary')," +
					"('Clothing')," +
					"('Debt')," +
					"('Electronic')," +
					"('Entertaiment')," +
					"('Food')," +
					"('Gift')," +
					"('Groceries')," +
					"('Medical')," +
					"('Miscellaneous')," +
					"('Personal Care')," +
					"('Salary')," +
					"('Travel')," +
					"('Vehicle');");
		}catch (SQLException e){
			//Message.message(context, "onCreate not called" + e);
		}
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		try{
			//Message.message(context, "onUpgrade called");
			db.execSQL(DROP_INCOME);
			db.execSQL(DROP_EXPENSE);
			db.execSQL(DROP_REMINDER);
			db.execSQL(DROP_CATEGORY);
			db.execSQL(DROP_PASSWORD);
			onCreate(db);
		}catch (SQLException e){
			//Message.message(context, "onUpgrade not called" + e);
		}
	}
}
