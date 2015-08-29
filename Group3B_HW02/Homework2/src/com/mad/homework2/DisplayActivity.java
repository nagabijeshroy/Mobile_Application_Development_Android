/**
 * Homework 2 - ITCS/ITIS 5180
 * 1. Naga Bijesh Roy Raya
 * 2. James Budday
 * 3. Shyam Mohan Raman
 */

package com.mad.homework2;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends Activity implements OnClickListener {

	TextView textViewName, textViewDate, textViewTime, textViewPriority, textViewTitle;
	static ArrayList<Task> tasks = new ArrayList<Task>();
	static int i=0;
	static Task task = new Task();

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		textViewTitle = (TextView) findViewById(R.id.textViewTaskInfo);
		textViewTitle.setTypeface(Typeface.DEFAULT_BOLD);
		task = (Task) getIntent().getExtras().getSerializable(MainActivity.TASK_NAME);
		tasks = (ArrayList<Task>) getIntent().getExtras().get(MainActivity.TASK_LIST);
		textViewName = (TextView) findViewById(R.id.textViewNameValue);
		textViewDate = (TextView) findViewById(R.id.textViewDateValue);
		textViewTime = (TextView) findViewById(R.id.textViewTimeValue);
		textViewPriority = (TextView) findViewById(R.id.textViewPriorityValue);
		textViewName.setText(task.getTitle());
		textViewDate.setText(task.getMonth() + "/" + task.getDay() + "/" + task.getYear());
		textViewTime.setText(task.getTimeText());
		textViewPriority.setText(task.getPriority());
		ImageButton discardBtn = (ImageButton) findViewById(R.id.imageButtonDiscard);
		discardBtn.setOnClickListener(this);
		ImageButton editButton = (ImageButton) findViewById(R.id.imageButtonEdit);
		editButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.display, menu);
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
		try {

			i = 0;
			for (Task taks : tasks) {
				i++;
				if (taks.getId().equals((task.getId() + ""))) {
					i--;
					break;
				}
			}
			if (v.getId() == R.id.imageButtonDiscard) {
				new AlertDialog.Builder(DisplayActivity.this).setTitle(getResources().getString(R.string.alert_discard_title))
				.setMessage(getResources().getString(R.string.alert_discard))
				.setPositiveButton(getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d("test", "removed Element" + tasks.get(i).getTitle());
						tasks.remove(i);
						Intent intent = new Intent();
						setResult(RESULT_OK, intent);
						intent.putExtra(MainActivity.TASK_LIST, tasks);
						finish();
					}
				}).setNegativeButton(getResources().getString(R.string.alert_cancel), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
				
			} else if (v.getId() == R.id.imageButtonEdit) {
				Log.d("test1", "fetched Element" + tasks.get(i).getTitle());
				Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
				intent.putExtra(MainActivity.TASK_LIST, tasks);
				intent.putExtra(MainActivity.TASK_NAME, tasks.get(i));
				startActivityForResult(intent, MainActivity.RESPONSE);
			}
		} catch (Exception ex) {
			Toast.makeText(DisplayActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == MainActivity.RESPONSE) {
			Log.d("test1", "inside activity result display");
			Task t = (Task) data.getExtras().getSerializable(TaskActivity.TASK_KEY);
			textViewName.setText(t.getTitle());
			textViewDate.setText(t.getMonth() + "/" + t.getDay() + "/" + t.getYear());
			textViewTime.setText(t.getTimeText());
			textViewPriority.setText(t.getPriority());
			tasks = (ArrayList<Task>) data.getExtras().get(MainActivity.TASK_LIST);
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		intent.putExtra(MainActivity.TASK_LIST, tasks);
		finish();
	}
}
