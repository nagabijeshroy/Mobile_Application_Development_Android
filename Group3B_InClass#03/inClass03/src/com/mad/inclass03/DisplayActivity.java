package com.mad.inclass03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends Activity  implements OnClickListener{

	TextView nameText;
	TextView emaiText;
	TextView programLangText;
	TextView accountStateText;
	TextView moodText;
	ImageView editImage;
	Student student1 = new Student();
	int requestCode = 100;
	final static String response_key="response";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		try{
		Student student = (Student)getIntent().getExtras().getSerializable(MainActivity.STUD_KEY);
		nameText = (TextView)findViewById(R.id.textViewrow12);
		emaiText = (TextView)findViewById(R.id.textViewrow22);
		programLangText = (TextView)findViewById(R.id.textViewrow32);
		accountStateText = (TextView)findViewById(R.id.textViewrow42);
		moodText = (TextView)findViewById(R.id.textViewrow52);
		nameText.setText(student.getName().toString());
		emaiText.setText(student.getEmailAddress().toString());
		programLangText.setText(student.getProgrammingLanguage().toString());
		student1 = student;
		String str = "";
		if(student.isSearchable()){
			str="Searchable";
			}
		else{
			str="Unsearchable";
		}
		accountStateText.setText(str.toString());
		moodText.setText(student.getMood()+" % Positive");
		editImage = (ImageView)findViewById(R.id.imageViewrow1);
		editImage.setOnClickListener(this);
		editImage = (ImageView)findViewById(R.id.imageViewrow2);
		editImage.setOnClickListener(this);
		editImage = (ImageView)findViewById(R.id.imageViewrow3);
		editImage.setOnClickListener(this);
		editImage = (ImageView)findViewById(R.id.imageViewrow4);
		editImage.setOnClickListener(this);
		editImage = (ImageView)findViewById(R.id.imageViewrow5);
		editImage.setOnClickListener(this);
		}catch(Exception ex){
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display, menu);
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
		Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
		intent.putExtra(MainActivity.STUD_KEY, student1);
		if(v.getId() == R.id.imageViewrow1){
			requestCode = 101;
			intent.putExtra(response_key, requestCode);
			startActivityForResult(intent, requestCode);
		}else if(v.getId() == R.id.imageViewrow2){
			requestCode = 102;
			intent.putExtra(response_key, requestCode);
			startActivityForResult(intent, requestCode);
		}else if(v.getId() == R.id.imageViewrow3){
			requestCode = 103;
			intent.putExtra(response_key, requestCode);
			startActivityForResult(intent, requestCode);
		}else if(v.getId() == R.id.imageViewrow4){
			requestCode = 104;
			intent.putExtra(response_key, requestCode);
			startActivityForResult(intent, requestCode);
		}else if(v.getId() == R.id.imageViewrow5){
			requestCode = 105;
			intent.putExtra(response_key, requestCode);
			startActivityForResult(intent, requestCode);
		}
	}
}
