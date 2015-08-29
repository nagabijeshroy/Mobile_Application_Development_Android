/**
 * Homework 2 - ITCS/ITIS 5180
 * 1. Naga Bijesh Roy Raya
 * 2. James Budday
 * 3. Shyam 
 */

package com.mad.homework2;

import java.util.ArrayList;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class EditActivity extends Activity implements OnClickListener {
	static Task task;
	static ArrayList<Task> tasks;
	EditText editTextTitle;
	EditText editTextDate;
	EditText editTextTime;
	RadioGroup radioGroupPriority;
	final static String TASK_KEY = "taskKey";
	private int mYear, mMonth, mDay, mHour, mMinute;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		task = (Task) getIntent().getExtras().getSerializable(MainActivity.TASK_NAME);
		tasks = (ArrayList<Task>) getIntent().getExtras().get(MainActivity.TASK_LIST);
		editTextTitle = (EditText) findViewById(R.id.editTextTitle);
		editTextDate = (EditText) findViewById(R.id.editTextDate);
		editTextDate.setKeyListener(null);
		editTextTime = (EditText) findViewById(R.id.editTextTime);
		editTextTime.setKeyListener(null);
		radioGroupPriority = (RadioGroup) findViewById(R.id.radioGroupPriority);
		Button buttonSave = (Button) findViewById(R.id.buttonEditSave);
		editTextTitle.setText(task.getTitle());
		editTextDate.setText(task.getMonth() + "/" + task.getDay() + "/" + task.getYear());
		editTextTime.setText(task.getTimeText());
		RadioButton radioHigh = (RadioButton) findViewById(R.id.radioHigh);
		RadioButton radioMedium = (RadioButton) findViewById(R.id.radioMedium);
		RadioButton radioLow = (RadioButton) findViewById(R.id.radioLow);
		if (task.getPriority().equalsIgnoreCase("High")) {
			radioHigh.setChecked(true);
		} else if (task.getPriority().equalsIgnoreCase("Medium")) {
			radioMedium.setChecked(true);
		} else if (task.getPriority().equalsIgnoreCase("Low")) {
			radioLow.setChecked(true);
		}
		try {
			editTextDate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mYear = Integer.parseInt(task.getYear());
					mMonth = Integer.parseInt(task.getMonth()) - 1;
					mDay = Integer.parseInt(task.getDay());
					DatePickerDialog dpd = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
							editTextDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
							task.setDay(dayOfMonth + "");
							task.setMonth("" + (monthOfYear + 1));
							task.setYear("" + year);
						}
					}, mYear, mMonth, mDay);
					dpd.show();
				}
			});
		} catch (Exception ex) {
			Toast.makeText(EditActivity.this, "Exception" + ex.getMessage(), Toast.LENGTH_LONG).show();
		}
		try {
			editTextTime.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					mHour = Integer.parseInt(task.getHour());
					mMinute = Integer.parseInt(task.getMinutes());
					TimePickerDialog tpd = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {

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
							task.setMinutes(minute + "");
							editTextTime.setText(hourOfDay + ":" + minuteS + " " + AMPM);
						}
					}, mHour, mMinute, false);
					tpd.show();
				}
			});
		} catch (Exception ex) {
			Toast.makeText(EditActivity.this, "Exception" + ex.getMessage(), Toast.LENGTH_LONG).show();
		}

		buttonSave.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (editTextTitle != null && editTextTitle.getText().toString().length() <= 20 && editTextTitle.getText().toString().length() != 0) {
			if (editTextTitle.length() != 0 && editTextDate.getText().toString() != null && editTextDate.getText().toString().length() != 0
					&& editTextTime.getText().toString() != null && editTextTime.getText().toString().length() != 0) {
				task.setTitle(editTextTitle.getText().toString());
				RadioButton radio = (RadioButton) findViewById(radioGroupPriority.getCheckedRadioButtonId());
				task.setPriority(radio.getText().toString());
				Intent intent = new Intent();
				intent.putExtra(TaskActivity.TASK_KEY, task);
				ArrayList<Task> tlist = new ArrayList<Task>();
				for (int j = 0; j < tasks.size(); j++) {
					Task t = tasks.get(j);
					if (t.getId().equalsIgnoreCase(task.getId())) {
						tlist.add(task);
						continue;
					}
					tlist.add(t);
				}
				intent.putExtra(MainActivity.TASK_LIST, tlist);
				setResult(RESULT_OK, intent);
				finish();
			} else {
				Toast.makeText(EditActivity.this, "" + getResources().getString(R.string.error_date_time), Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(EditActivity.this, "" + getResources().getString(R.string.error_title), Toast.LENGTH_SHORT).show();
		}
	}
}
