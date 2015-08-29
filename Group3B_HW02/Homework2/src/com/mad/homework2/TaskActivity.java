/**
 * Homework 2 - ITCS/ITIS 5180
 * 1. Naga Bijesh Roy Raya
 * 2. James Budday
 * 3. Shyam Mohan Raman
 */
package com.mad.homework2;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

public class TaskActivity extends Activity implements OnClickListener {

	static Task task;
	EditText editTextTitle;
	EditText editTextDate;
	EditText editTextTime;
	RadioGroup radioGroupPriority;
	final static String TASK_KEY = "taskKey";
	private int mYear = -1, mMonth, mDay, mHour, mMinute = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		editTextTitle = (EditText) findViewById(R.id.editTextTitle);
		editTextDate = (EditText) findViewById(R.id.editTextDate);
		editTextDate.setKeyListener(null);
		editTextTime = (EditText) findViewById(R.id.editTextTime);
		editTextTime.setKeyListener(null);
		radioGroupPriority = (RadioGroup) findViewById(R.id.radioGroupPriority);
		Button buttonSave = (Button) findViewById(R.id.buttonEditSave);
		Log.d("Test", "Inside Onclick Task");
		task = new Task();
		try {
			editTextDate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					final Calendar c = Calendar.getInstance();
					if (mYear == -1){
						mYear = c.get(Calendar.YEAR);
						mMonth = c.get(Calendar.MONTH);
						mDay = c.get(Calendar.DAY_OF_MONTH);
					}
					DatePickerDialog dpd = new DatePickerDialog(TaskActivity.this, new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
							editTextDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
							task.setDay(dayOfMonth + "");
							task.setMonth("" + (monthOfYear + 1));
							task.setYear("" + year);
							mDay = dayOfMonth;
							mMonth = monthOfYear;
							mYear = year;
						}
					}, mYear, mMonth, mDay);
					dpd.show();
				}
			});
			editTextTime.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					final Calendar c = Calendar.getInstance();
					if (mMinute == -1){
						mHour = c.get(Calendar.HOUR_OF_DAY);
						mMinute = c.get(Calendar.MINUTE);
					}
					// Launch Time Picker Dialog
					TimePickerDialog tpd = new TimePickerDialog(TaskActivity.this, new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
							String AMPM = "AM", minuteS;
							mHour = hourOfDay;
							mMinute = minute;
							if (hourOfDay > 12) {
								hourOfDay = hourOfDay - 12;
								AMPM = "PM";
							} else if (hourOfDay == 0) {
								hourOfDay = 12;
								AMPM = "AM";
							} else if (hourOfDay == 12) {
								AMPM = "PM";
							}
							if(minute < 10){
								minuteS = "0" + minute;
							}else{
								minuteS = minute + "";
							}
							task.setAmPm(AMPM);
							task.setHour(mHour + "");
							task.setMinutes(mMinute + "");
							editTextTime.setText(hourOfDay + ":" + minuteS + " " + AMPM);
							
						}
					}, mHour, mMinute, false);
					tpd.show();
				}
			});
		} catch (Exception ex) {
			Toast.makeText(TaskActivity.this, "Exception" + ex.getMessage(), Toast.LENGTH_LONG).show();
		}

		buttonSave.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task, menu);
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
		if (editTextTitle != null && editTextTitle.getText().toString().length() <= 20 && editTextTitle.getText().toString().length()!=0) {
			if (editTextTitle.length() != 0 && editTextDate.getText().toString() != null && editTextDate.getText().toString().length() != 0
					&& editTextTime.getText().toString() != null && editTextTime.getText().toString().length() != 0) {
				task.setTitle(editTextTitle.getText().toString());
				RadioButton radio = (RadioButton) findViewById(radioGroupPriority.getCheckedRadioButtonId());
				task.setPriority(radio.getText().toString());
				Intent intent = new Intent();
				intent.putExtra(TaskActivity.TASK_KEY, task);
				setResult(RESULT_OK, intent);
				finish();
			} else {
				Toast.makeText(TaskActivity.this, "" + getResources().getString(R.string.error_date_time), Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(TaskActivity.this, "" + getResources().getString(R.string.error_title), Toast.LENGTH_SHORT).show();
		}
	}

}
