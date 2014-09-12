package com.example.Gradest;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	//database name
	public static final String DATABASE_NAME = "GradeCalculator";
	//table name
	public static final String TABLE_NAME = "gradest";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_GRADE_VALUE = "grade";
	public static final String COLUMN_WEIGHT_VALUE = "weight";
	public static final String COLUMN_DESCRIPTION_VALUE = "description";
	
	public static final String TEXT_TYPE = " INTEGER";
	public static final String TEXT_TYPE_2 = " TEXT";
	public static final String COMMA_SEP = ",";
	
	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" 
			+ COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_DESCRIPTION_VALUE + TEXT_TYPE_2 + COMMA_SEP + COLUMN_GRADE_VALUE  + TEXT_TYPE + COMMA_SEP + 
			COLUMN_WEIGHT_VALUE + TEXT_TYPE + ")";
	
	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
	

	
	public DbHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		System.out.println("Create a Databse");
		
    }
		
	//executed on creation when the database doesn't exist yet
	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
		db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);   
    }
	
	
	
}