package com.example.to_dolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent.OnFinished;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends Activity {
	Task task;
	TextView tv1, tv2, tv3, tv4;
	String taskName, date, time, priority;
	Intent inten;
	int index;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		task = (Task) getIntent().getExtras().getSerializable(AppConstants.TASK_CODE);
		index = getIntent().getExtras().getInt(AppConstants.INDEX_CODE);
		tv1 = (TextView) findViewById(R.id.textView12);
		tv2 = (TextView) findViewById(R.id.textView22);
		tv3 = (TextView) findViewById(R.id.textView32);
		tv4 = (TextView) findViewById(R.id.textView42);
		
		updateLayout();
		
		findViewById(R.id.imageButtonEdit).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
				intent.putExtra(AppConstants.TASK_CODE, task);
				startActivityForResult(intent, AppConstants.REQ_CODE_EDIT);
				
			}
		});
		
		findViewById(R.id.imageButtonDiscard).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(DisplayActivity.this)
				.setTitle("Discard?")
				.setMessage("Are you sure you want to discard this task?")
				
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent dat = new Intent();
						dat.putExtra(AppConstants.DISCARD_CODE, true);
						dat.putExtra(AppConstants.INDEX_CODE, index);
						setResult(RESULT_OK, dat);
						finish();
					}
				})
				.show();
			}
		});

	}
	@Override
	public void onBackPressed() {
	    //super.onBackPressed();
		//TODO fixerino
		inten = new Intent();
		Log.d("test", "before intent puts");
		inten.putExtra(AppConstants.TASK_CODE_EDIT, task);
		Log.d("test", "got to put task");
		inten.putExtra(AppConstants.INDEX_CODE, index);
		Log.d("test", "got to put index");
		setResult(RESULT_OK);
		Log.d("test", "got to result okay");
	    finish();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == AppConstants.REQ_CODE_EDIT){
			if(resultCode == RESULT_OK){
				task = (Task) data.getExtras().getSerializable(AppConstants.TASK_CODE);
				//Toast.makeText(this, "Value received is: " + task, Toast.LENGTH_LONG).show();
				updateLayout();
			}else if (resultCode == RESULT_CANCELED){
				Toast.makeText(this, "No edits made" , Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	private void updateLayout(){
		taskName = "    " + task.getTitle();
		date = "    " + task.dateString();
		time = "    " + task.timeString();
		priority = "    " +task.getPriority();
		
		tv1.setText(taskName);
		tv2.setText(date);
		tv3.setText(time);
		tv4.setText(priority);
	}
}
