package com.example.to_dolist;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateTask extends Activity implements OnClickListener {
	private EditText et, etDate, etTime;
	private Button btn;
	private RadioGroup rgrp;
	private Calendar c = Calendar.getInstance();
	private int hour = -1, minute1 = -1, year = -1, month = -1, day = -1, priority = R.id.radioHigh;
	private String amPM, title = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_task);

		// set date listener and chooser
		etDate = (EditText) findViewById(R.id.editTextDateCT);
		etDate.setKeyListener(null);
		etDate.setOnClickListener(this);

		// set time listener and chooser
		etTime = (EditText) findViewById(R.id.editTextTimeCT);
		etTime.setKeyListener(null);
		etTime.setOnClickListener(this);

		// save button listener
		btn = (Button) findViewById(R.id.buttonSaveCT);
		btn.setOnClickListener(this);
		
		// edittext change listener
		et = (EditText)findViewById(R.id.editTextTitleCT);
	    et.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,int count) {}

			@Override
			public void afterTextChanged(Editable s) {
				title = s.toString();
				
			}
	    });
	    
	    //radiogroup click listener
	    rgrp = (RadioGroup) findViewById(R.id.radioGroupPriorityCT);
	    rgrp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				priority = checkedId;
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_task, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editTextDateCT:
			//if a value hasn't been entered, use current date
			if (year == -1 && month == -1 && day == -1) {
				year = c.get(Calendar.YEAR);
				month = c.get(Calendar.MONTH);
				day = c.get(Calendar.DAY_OF_MONTH);
			}
			new DatePickerDialog(this, datePickerListener, year, month, day)
					.show();
			break;
		case R.id.editTextTimeCT:
			//if a value hasn't been entered, use current time
			if (hour == -1 && minute1 == -1) {
				hour = c.get(Calendar.HOUR_OF_DAY);
				minute1 = c.get(Calendar.MINUTE);
			}
			new TimePickerDialog(this, timePickerListener, hour, minute1, false)
					.show();
			break;
		case R.id.buttonSaveCT:
			//if one of the fields hasn't been filled out or title is too long, error message
			if(hour == -1 || year == -1 || title == "" || title.length() > 20){
				if(title.length() > 20){
					Toast.makeText(this, "Your title must be less than 20 characters long", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(this, "Please finish filling out your task", Toast.LENGTH_SHORT).show();
				}
			}else{
				//get correct priority string
				String priorityS;
				if(priority == R.id.radioHigh){
					priorityS = "High";
				}else if(priority == R.id.radioMedium){
					priorityS = "Medium";
				}
				else{
					priorityS = "Low";
				}
				//make object and pass back
				Task task = new Task(title, priorityS, minute1, hour, day, month, year);
				//Toast.makeText(this, task.toString(), Toast.LENGTH_LONG).show();
				Intent data = new Intent();
				data.putExtra(AppConstants.TASK_CODE, task);
				setResult(RESULT_OK, data);
				finish();
			}
			break;
		default:
			break;
		}

	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			String minuteString;
			//correct for am/pm
			//correct for am/pm
			if (hourOfDay > 11) {
				if (hourOfDay == 12){
					hour = hourOfDay;
				}else{
					hour = hourOfDay - 12;
					
				}
				amPM = "PM";
			} else {
				hour = hourOfDay;
				//correct for hour zero describing 12AM
				if (hour == 0) {
					hour = 12;
				}
				amPM = "AM";
			}
			//correct for leading zero not displaying on small minutes
			if (minute < 10){
				minuteString = "0" + minute;
			}else{
				minuteString = minute + "";
			}
			etTime.setText(hour + " : " + minuteString + " " + amPM);
			//set to raw values
			hour = hourOfDay;
			minute1 = minute;
		}
	};

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			month = selectedMonth;
			day = selectedDay;
			year = selectedYear;
			//+1 corrects for month offset where January is 0
			etDate.setText((selectedMonth + 1) + " / " + selectedDay + " / "
					+ selectedYear);
		}
	};
}