package com.example.Gradest;

//This class stores all the data stored in each row of the table
//description, grade and weight
public class Entry {
	private double GRADE;
	private double WEIGHT;
	private String DESCRIPT;
	
	public Entry(){
		GRADE = 0;
		WEIGHT = 0;
		DESCRIPT = " ";
	}
	
	public Entry(String description, double grade, double weight){
		GRADE = grade;
		WEIGHT = weight;
		DESCRIPT = description;
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
	
	public void setGRADE(double grade){
		GRADE = grade;
	}
	
	public void setWEIGHT(double weight){
		WEIGHT = weight;
	}
		
}