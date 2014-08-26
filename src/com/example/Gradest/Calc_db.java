package com.example.Gradest;


import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Calc_db{
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "GradeCalculator";
	
	public static final String TABLE_NAME = "gradest";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_GRADE_VALUE = "grade";
	public static final String COLUMN_WEIGHT_VALUE = "weight";
	public static final String COLUMN_DESCRIPTION_VALUE = "description";
	
	private SQLiteDatabase ourDatabase;
	private DbHelper ourHelper;
	private final Context ourContext;
	
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" 
					+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					COLUMN_DESCRIPTION_VALUE + " TEXT NOT NULL, " + COLUMN_GRADE_VALUE  + 
					" INTEGER, "  + COLUMN_WEIGHT_VALUE + " INTEGER" + ")" );		
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
			System.out.println("upgrading");
		}
	}
	
	public Calc_db(Context c){
		ourContext = c;
	}
	
	//open DbHelper class
	public Calc_db open(){
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	//close DbHelper class
	public void close(){
		ourHelper.close();
	}
	
	public SQLiteDatabase getBd(){
		return ourDatabase;
	}
	
	//delete all the data in the table 
	public void delete(){
		if (DATABASE_NAME != null)
			ourDatabase.delete(TABLE_NAME, null, null);
		else
			System.out.println("it is null");
	}
	
	//insert all the column data for one row
	public void addEntry(Entry marks){
		try{
		ContentValues values = new ContentValues();
		values.put(COLUMN_DESCRIPTION_VALUE, marks.getDESCRIPT());
		values.put(COLUMN_GRADE_VALUE, marks.getGRADE());
		values.put(COLUMN_WEIGHT_VALUE, marks.getWEIGHT());
		ourDatabase.insert(TABLE_NAME, null, values);
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("Insertion", e.toString());
        }
	}
} 