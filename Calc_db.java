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
	public static final String TABLE_NAME = "gradest"; //table name
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_GRADE_VALUE = "grade";
	public static final String COLUMN_WEIGHT_VALUE = "weight";
	public static final String COLUMN_DESCRIPTION_VALUE = "description";
	private SQLiteDatabase db;
	private DbHelper oDB;
	private Context context;
	
	public Calc_db(Context context){
		oDB = new DbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
	public void open(){
		db = oDB.getWritableDatabase();
	}	
	public void read(){
		db = oDB.getReadableDatabase();
	}
	public void close(){
		oDB.close();
	}
	public SQLiteDatabase getBd(){
		return db;
	}
	public void delete(){
		context.deleteDatabase(DATABASE_NAME);
		if (DATABASE_NAME == null)
				System.out.println("YS");
	}
	
	public void addEntry(Entry marks){
		System.out.println("inside addEntry");
		//getWritableDatabase();
		try{
		ContentValues values = new ContentValues();
		values.put(COLUMN_DESCRIPTION_VALUE, marks.getDESCRIPT());
		System.out.println("description from calc_db" +  marks.getDESCRIPT());
		values.put(COLUMN_GRADE_VALUE, marks.getGRADE());
		values.put(COLUMN_WEIGHT_VALUE, marks.getWEIGHT());
		//values.put(COLUMN_ID,null);
		db.insert(TABLE_NAME, null, values);
		}
		catch(Exception e){
			e.printStackTrace();
			Log.e("Insertion", e.toString());
        }
	}
} 