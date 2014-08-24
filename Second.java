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
import android.widget.TextView.BufferType;

public class Second extends Activity {
	private TableLayout t1;
	private Grade_Calc  calculator;
	Calc_db data = new Calc_db(Second.this);


	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.HONEYCOMB){
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		t1 = (TableLayout) findViewById(R.id.mainTable);

		System.out.println("before");
		data.open();
		List<Entry> grade_w = populateList();
		if (grade_w.size()>0){
			populateTable();
			System.out.println("yahh");
		}
		
		data.close();
		}
	
	public void validate(View v){
		data.open();
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
	
	public void retrieveData() {
		int child_count = t1.getChildCount();
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
			data.addEntry(e1);			
			}
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

	public void addRowListener(View v){
		addRow();
	}
	public void addRow(){
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
		Cursor cursor = data.getBd().query(Calc_db.TABLE_NAME, null, null, null, null, null, null);

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
		
		return gradeWeightList;
		
	}

	public void populateTable(){
		List<Entry> grade_weight = populateList();
		for (int i = 0; i<(grade_weight.size()-1); i++){
			addRow();
		}	
		int child_count = t1.getChildCount();
		int j = 0;
		for (int i=0; i<child_count; i++){
			TableRow row = (TableRow)t1.getChildAt(i);
			for (int m=0; m<row.getChildCount();m++){
				EditText text = (EditText)row.getChildAt(m);
				if (m == 0){//first textfield accepts letters
					text.setText((grade_weight.get(j).getDESCRIPT()));
				}
				else if (m==1){
					text.setText(String.valueOf(grade_weight.get(j).getGRADE()));
				}
				else
					text.setText(String.valueOf(grade_weight.get(j).getWEIGHT()));
			}
		j++;
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
