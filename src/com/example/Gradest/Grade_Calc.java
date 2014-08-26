package com.example.Gradest;

import java.util.List;

//this class calculate the average mark based on the grades achieved so far
//in the future it will also calculate how many marks a student needs 
//to achieve a specified goal grade. 
public class Grade_Calc {
	
	private double avg_grade;
	private double marksSoFar=0;
	private double weightSoFar=0;
	//private double goal_grade=0;
	
	public Grade_Calc(List<Entry> grades){
		for (int i=0; i<grades.size(); i++ ){
				marksSoFar+=grades.get(i).getGRADE()*(grades.get(i).getWEIGHT());
				weightSoFar+=(grades.get(i).getWEIGHT());
		}
		avg_grade = marksSoFar/weightSoFar;
		//need to catch when goal is less than marks so far 
		//goal_grade = goal-marksSoFar;
		
	}

	
	//mark will be added to a list of other marks and divided by the sum of the weights
	public double getAvgGrade(){
					
		return avg_grade;
	}
	/*
	public double getGoalGrade(){
		return goal_grade;
	}
*/
	
	
	
}
