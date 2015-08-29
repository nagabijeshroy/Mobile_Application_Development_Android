/**
 * Homework 2 - ITCS/ITIS 5180
 * 1. Naga Bijesh Roy Raya
 * 2. James Budday
 * 3. Shyam Mohan Raman
 */
package com.mad.homework2;

import java.util.ArrayList;
import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener {

	static ArrayList<Task> taskList;
	static LinkedList<Task> tasks;
	final static String TASK_LIST = "taskList";
	final static int RESPONSE = 100;
	final static int RESPONSE_DISPLAY = 101;
	final static String TASK_NAME = "taskName";
	static int id = 100;
	LinearLayout linearLayout;
	TextView textViewTaskName;
	TextView textViewTaskDate;
	TextView textViewTaskTime;
	TextView textViewTasks;
	ScrollView scroll;
	RelativeLayout rv;
	static Task t1;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textViewTasks = (TextView) findViewById(R.id.textViewTasks);
		textViewTasks.setText("0" + " " + getResources().getString(R.string.textview_tasks));
		textViewTasks.setTypeface(Typeface.DEFAULT_BOLD);
		rv = (RelativeLayout) findViewById(R.id.mainLayout);
		tasks = new LinkedList<Task>();
		taskList = new ArrayList<Task>();
		ImageView imageViewNew = (ImageView) findViewById(R.id.imageViewNew);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.MATCH_PARENT,
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.BELOW, textViewTasks.getId());
		scroll = new ScrollView(this);
		scroll.setId(View.generateViewId());
		scroll.setFillViewport(true);
		scroll.setScrollContainer(false);
		scroll.setLayoutParams(layoutParams);
		rv.addView(scroll);
		linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setId(1000);
		scroll.addView(linearLayout);
		imageViewNew.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Log.d("Test", "Inside On click +");
		try {
			Intent intent = new Intent(MainActivity.this, TaskActivity.class);
			startActivityForResult(intent, MainActivity.RESPONSE);
		} catch (Exception ex) {
			Log.d("test", ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("test", "Inside Return Values " + "request Code : " + requestCode);

		try {
			if (resultCode == RESULT_OK && requestCode == MainActivity.RESPONSE) {
				id++;
				Task task = (Task) data.getExtras().getSerializable(TaskActivity.TASK_KEY);
				Log.d("test", "task list id added" + id + " Task name " + task.getTitle());
				task.setId(id + "");
				taskList.add(task);
				makeLinkedList(taskList);
				updateView(tasks);
			} else if (resultCode == RESULT_OK && requestCode == MainActivity.RESPONSE_DISPLAY) {
				ArrayList<Task> taskl = (ArrayList<Task>) data.getExtras().get(MainActivity.TASK_LIST);
				linearLayout.removeAllViews();
				taskList = taskl;
				makeLinkedList(taskList);
				updateView(tasks);
			}
			if (tasks.size() == 1){
				textViewTasks.setText(tasks.size() + " " + getResources().getString(R.string.textview_task));
			}else{
				textViewTasks.setText(tasks.size() + " " + getResources().getString(R.string.textview_tasks));
			}
			Log.d("test", "Inside Return Text Values" + taskList.size());
		} catch (Exception ex) {
			Log.d("test", "Error" + ex.getMessage());
		}
	}

	private void updateView(LinkedList<Task> taskList2) {
		linearLayout.removeAllViews();
		for (Task t : taskList2) {
			t1 = t;
			LinearLayout sublinearLayout = new LinearLayout(this);
			sublinearLayout.setOrientation(LinearLayout.VERTICAL);
			sublinearLayout.setId(Integer.parseInt(t.getId()));
			TextView textViewTitle = new TextView(this);
			textViewTitle.setText(t.getTitle().toString());
			textViewTitle.setTextSize((float) 20);
			textViewTitle.setTypeface(Typeface.DEFAULT_BOLD);
			sublinearLayout.addView(textViewTitle);
			TextView textViewDate = new TextView(this);
			textViewDate.setText(t.getMonth().toString() + "/" + t.getDay().toString() + "/" + t.getYear());
			sublinearLayout.addView(textViewDate);
			TextView textViewTime = new TextView(this);
			textViewTime.setText(t.getTimeText());
			sublinearLayout.addView(textViewTime);
			sublinearLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
					Log.d("test", "Clicked This view" + v.getId());
					Task t3 = null;
					for (Task tsk : taskList) {
						if (tsk.getId().equalsIgnoreCase(v.getId() + "")) {
							t3 = tsk;
						}
					}
					intent.putExtra(MainActivity.TASK_NAME, t3);
					intent.putExtra(MainActivity.TASK_LIST, taskList);
					startActivityForResult(intent, MainActivity.RESPONSE_DISPLAY);
				}
			});
			linearLayout.addView(sublinearLayout);
			sublinearLayout = new LinearLayout(this);
			TextView textViewBlank = new TextView(this);
			textViewBlank.setText("");
			sublinearLayout.addView(textViewBlank);
			linearLayout.addView(sublinearLayout);
			
		}
		this.setContentView(rv);

	}
	private void makeLinkedList(ArrayList<Task> arL){
		tasks = new LinkedList<Task>();
		for (Task t : arL) {
			tasks.add(t);
		}
	}
}
