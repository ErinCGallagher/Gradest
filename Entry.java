package com.example.Gradest;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class Entry {
	private double GRADE;
	private double WEIGHT;
	private String DESCRIPT;
	
	public Entry(){
		//ID = 0;
		GRADE = 0;
		WEIGHT = 0;
		DESCRIPT = " ";
	}
	
	public Entry(String description, double gRADE, double wEIGHT){
		GRADE = gRADE;
		WEIGHT = wEIGHT;
		DESCRIPT = description;
		System.out.printf("grade:%f",GRADE);
		System.out.printf("weight:%f",WEIGHT);
		System.out.printf("description%s\n", DESCRIPT);
	}

	public String getDESCRIPT(){
		return DESCRIPT;
	}
	public double getGRADE(){
		return GRADE;
	}
	
	public double getWEIGHT(){
		return WEIGHT;
	}
	
	public void setDESCRIPT(String description){
		DESCRIPT = description;
	}
	
	public void setGRADE(double gRADE){
		GRADE = gRADE;
	}
	
	public void setWEIGHT(double wEIGHT){
		WEIGHT = wEIGHT;
	}
		
}