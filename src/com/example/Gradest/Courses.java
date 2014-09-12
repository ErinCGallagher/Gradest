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
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;


//This class contains all the course information
//in the future you will be able to add tabs in order to store multiple courses
public class Courses extends Activity {
	private TableLayout t1;
	private Grade_Calc  calculator;
	Calc_db data = new Calc_db(Courses.this);


	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.HONEYCOMB){
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		t1 = (TableLayout) findViewById(R.id.mainTable);

		data.open();
		List<Entry> grade_w = populateList();
		//check and see if the database is empty
		if (grade_w.size()>0){
			//if it is not empty, populate the table with data
			populateTable();
		}
		data.close();
	}
	
	//submit button listener
	public void submit_button(View v){
		data.open();
		//if the table contains data, remove it before adding the updated data
		data.delete();
		retrieveData();
		List<Entry> grade_weight = populateList();
		data.close();
		
		calculator = new Grade_Calc(grade_weight);
		double avg = calculator.getAvgGrade();
		DecimalFormat df = new DecimalFormat("#.##");
		String sAvg=String.valueOf(df.format(avg));
		EditText editTextGrade = (EditText) findViewById(R.id.EditTextGrade);
		editTextGrade.setText(sAvg);
		
		//double NeedToAcheive = calculator.getGoalGrade();		
	}
	
	//loops through the rows and columns of the table to retrieve user input
	//stores this input in the Entry class
	public void retrieveData() {
		int child_count = t1.getChildCount();
		double grade = 0;
		double weight = 0;
		String value = " ";
		
		//loop through the rows of the table
		for (int i=0; i<child_count; i++){
			TableRow row = (TableRow)t1.getChildAt(i);
			
			//loop through the columns of each row
			for (int j=0; j<row.getChildCount();j++){
				EditText text = (EditText)row.getChildAt(j);
				
				if (j == 0){//first textfield accepts letters
					value = isDescriptValid(text);
				}
				else if (j==1){
					grade = isGradeValid(text);
				}
				else
					weight= isWeightValid(text);
			}
			Entry e1 = new Entry (value, grade, weight);
			data.addEntry(e1);			
			}
	}
		
	//checks to see if the description is valid
	//can only input letters
	private String isDescriptValid(EditText text){
		if (text.getText().toString().trim().length() == 0){
			text.setError("Cannot be empty");
		return "no";
		}
		else
			return text.getText().toString();
	}
	
	//checks to see if grade is valid
	//must be a decimal and not negative
	private double isGradeValid(EditText text){
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
	
	//checks to see if weight is valid
	//this value is a percentage 
	private double isWeightValid(EditText text){
		if (text.getText().toString().trim().length() == 0){
			text.setError("Cannot be empty");
			System.out.println("empty");
			return -1.0;
		}
		else if (Double.parseDouble(text.getText().toString()) < 0 ){
			text.setError("Must be larger than 1");
			return -1.0;
			}

		else {
			return Double.parseDouble(text.getText().toString());
			}
	}

	//addRow button listener
	public void addRowListener(View v){
		addRow();
	}
	
	//adds a row to the table
	public void addRow(){
		TableRow row = new TableRow(this);
		row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
		EditText description = new EditText(this);
		description.setHint("assignment");
		description.setLayoutParams(new TableRow.LayoutParams(100,LayoutParams.WRAP_CONTENT, 1f));
		description.setGravity(Gravity.CENTER);
		description.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
		description.setTextColor(Color.WHITE);
		description.setBackgroundResource(R.drawable.tableshape);
		row.addView(description);
		row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
		EditText grade = new EditText(this);
		grade.setHint("0.0");
		grade.setLayoutParams(new TableRow.LayoutParams(50,LayoutParams.WRAP_CONTENT, 1f));
		grade.setGravity(Gravity.CENTER);
		grade.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
		grade.setTextColor(Color.WHITE);
		grade.setBackgroundResource(R.drawable.tableshape);
		row.addView(grade);
		EditText weight = new EditText(this);
		weight.setHint("0.0");
		weight.setLayoutParams(new TableRow.LayoutParams(50,LayoutParams.WRAP_CONTENT, 1f));
		weight.setGravity(Gravity.CENTER);
		weight.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
		weight.setTextColor(Color.WHITE);
		weight.setBackgroundResource(R.drawable.tableshape);
		row.addView(weight);
		t1.addView(row);
	}			
	
	//uses a cursor to iterate through the database 
	//returns a list of type Entry containing all the data for each row in the table
	public List<Entry> populateList(){
		List<Entry> gradeWeightList = new ArrayList<Entry>();
		Cursor cursor = data.getBd().query(Calc_db.TABLE_NAME, null, null, null, null, null, null);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			String dbDescript = cursor.getString(cursor.getColumnIndex(Calc_db.COLUMN_DESCRIPTION_VALUE));
			Double dbGrade = cursor.getDouble(cursor.getColumnIndex(Calc_db.COLUMN_GRADE_VALUE));
			Double dbWeight = cursor.getDouble(cursor.getColumnIndex(Calc_db.COLUMN_WEIGHT_VALUE));
			Entry e1 = new Entry (dbDescript, dbGrade, dbWeight);
			gradeWeightList.add(e1);	
		} 
		cursor.close();
		
		return gradeWeightList;
		
	}

	//retrieves all the table data and stores in in a list of type Entry
	//adds the correct number of rows to the table based on the list size
	//inputs the data values into the new rows and columns
	public void populateTable(){
		List<Entry> grab_all = populateList();
		for (int i = 0; i<(grab_all.size()-1); i++){
			addRow();		
		}
		
		int child_count = t1.getChildCount();
		//loops through each row
		for (int i=0; i<child_count; i++){
			TableRow row = (TableRow)t1.getChildAt(i);
			//loops through each column to input data from the table
			for (int m=0; m<row.getChildCount();m++){
				EditText text = (EditText)row.getChildAt(m);
				
				if (m == 0){//first textfield accepts letters
					text.setText((grab_all.get(i).getDESCRIPT()));
				}
				else if (m==1){
					text.setText(String.valueOf(grab_all.get(i).getGRADE()));
				}
				else
					text.setText(String.valueOf(grab_all.get(i).getWEIGHT()));
			}
		}
		
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
