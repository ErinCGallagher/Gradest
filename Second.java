package com.example.Gradest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.database.Cursor;
import android.support.v4.app.NavUtils;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Second extends Activity {
	private TableLayout t1;
	private Grade_Calc  calculator;
	Calc_db db = new Calc_db(this);


	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.HONEYCOMB){
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		//dbHelper = new DbHelper(this);
		//Intent intent = getIntent();
		t1 = (TableLayout) findViewById(R.id.mainTable);
		System.out.println("before");
		
		
		
		
		
	//check to see if database is null
	//if not call  populateList()
		//populate table using addRow()
	}
	public void validate(View v){
		int child_count = t1.getChildCount();
		//check to see if dbHelper is null
		db.open();
			
		//2 children per row
		double[] grades = new double[2];
		String value = "a";
		for (int i=0; i<child_count; i++){
			TableRow row = (TableRow)t1.getChildAt(i);
			System.out.println("child" + row.getChildCount());
			for (int j=0; j<row.getChildCount();j++){
				EditText text = (EditText)row.getChildAt(j);
				if (j == 0){//first textfield accepts letters
					value = isDescriptValid(text);
				}
				else{
					grades[j-1] = isNumValid(text);
				}
			}
			System.out.println("getting text from EditTexts");
			Entry e1 = new Entry (value, grades[0], grades[1]);
			db.addEntry(e1);			
			}
		
		List<Entry> grade_weight = populateList();
		calculator = new Grade_Calc(grade_weight);
		double avg = calculator.getAvgGrade();
		DecimalFormat df = new DecimalFormat("#.##");
		//System.out.println("avgerage"+avg);
		String sAvg=String.valueOf(df.format(avg));
		EditText editTextGrade = (EditText) findViewById(R.id.EditTextGrade);
		editTextGrade.setText(sAvg);
		//db.delete();
		db.close();
		//double NeedToAcheive = calculator.getGoalGrade();
		
	}
	
	
	private double isNumValid(EditText text){
		if (text.getText().toString().trim().length() == 0){
			text.setError("Cannot be empty");
			System.out.println("empty");
			return -1.0;
		}
		else if (Double.parseDouble(text.getText().toString()) > 1 ){
			text.setError("Must be less than 1");
			return -1.0;
			}

		else {

			return Double.parseDouble(text.getText().toString());
			}
	}
	
	private String isDescriptValid(EditText text){
		if (text.getText().toString().trim().length() == 0){
			text.setError("Cannot be empty");
		return "no";
		}
		else
			return text.getText().toString();
	}
	@Override
	public void onPause(){
		super.onPause();
		db.close();	
	}
	@Override
	protected void onResume() {
	    super.onResume();
	    db.open();
	    //if(mRowId != -1L)
	    List<Entry> pre_pop = populateList();
		System.out.println("after");
		if (pre_pop.size() > 0){
			System.out.println("after after");
			for (int i = 0; i<2 ;i++){
				System.out.println("grade::::::" +pre_pop.get(i).getGRADE());
				System.out.println("weight:::::" +pre_pop.get(i).getWEIGHT());
			}
		}
	    populateFields();
	}
	
	public void addRow(View v){
		TableRow row = new TableRow(this);
		row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
		EditText description = new EditText(this);
		description.setHint("assignment");
		description.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
		description.setGravity(Gravity.CENTER);
		description.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
		row.addView(description);
		row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
		EditText grade = new EditText(this);
		grade.setHint("0.0");
		grade.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
		grade.setGravity(Gravity.CENTER);
		grade.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
		row.addView(grade);
		EditText weight = new EditText(this);
		weight.setHint("0.0");
		weight.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
		weight.setGravity(Gravity.CENTER);
		weight.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
		row.addView(weight);
		t1.addView(row);
					
	}

	public List<Entry> populateList(){
		List<Entry> gradeWeightList = new ArrayList<Entry>();
		//db.read();
		System.out.println("made it here");
		//Cursor required to read database query
		//String[] columns = {Calc_db.COLUMN_ID, Calc_db.COLUMN_DESCRIPTION_VALUE, Calc_db.COLUMN_GRADE_VALUE, Calc_db.COLUMN_WEIGHT_VALUE};
		Cursor cursor = db.getBd().query(db.TABLE_NAME, null, null, null, null, null, null);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			String dbDescript = cursor.getString(cursor.getColumnIndex(Calc_db.COLUMN_DESCRIPTION_VALUE));
			Double dbGrade = cursor.getDouble(cursor.getColumnIndex(Calc_db.COLUMN_GRADE_VALUE));
			Double dbWeight = cursor.getDouble(cursor.getColumnIndex(Calc_db.COLUMN_WEIGHT_VALUE));
			Entry e1 = new Entry (dbDescript, dbGrade, dbWeight);
			gradeWeightList.add(e1);	
			System.out.println("description" + dbDescript);
			System.out.println("grade:"+ dbGrade);
			System.out.println("weight:"+ dbWeight);
		} 
		cursor.close();
		db.close();
		
		return gradeWeightList;
		
	}
	public 	void populateFields(){
		Cursor cursor = db.getBd().query(db.TABLE_NAME, null, null, null, null, null, null);
		String dbDescript = cursor.getString(cursor.getColumnIndex(Calc_db.COLUMN_DESCRIPTION_VALUE));
		System.out.println("String" + dbDescript);
		cursor.close();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	

}
