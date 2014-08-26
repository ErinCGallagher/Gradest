package com.example.Gradest;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
   
    public void launchCourses(View view){
    	Intent j = new Intent(
    			MainActivity.this,
    			Courses.class);
    	startActivity(j);
    }
    
    
    public void launchGPA(View view){
    	Intent i = new Intent(
    			MainActivity.this,
    			GPAcalc.class);
    	startActivity(i);
    }
}
