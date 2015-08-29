package com.mad.inclass03;

import android.app.Activity;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;

public class EditActivity extends Activity {
	EditText name;
	EditText emailAddress;
	int programmingLanguageBtnId;
	Switch searchableSwitch;
	SeekBar moodBar;
	Button submitButton;
	int moodBarValue;
	boolean switchStatus;
	RadioButton progLang;
	RadioGroup radioGroup;
	Student student=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		 student=(Student) getIntent().getExtras().getSerializable(MainActivity.STUD_KEY);
		 int response = getIntent().getExtras().getInt(DisplayActivity.response_key);
		 	name = (EditText)findViewById(R.id.editTextName);
		 	emailAddress = (EditText)findViewById(R.id.editTextEmail);
		 	searchableSwitch = (Switch)findViewById(R.id.switchSearchable);
		 	moodBar = (SeekBar)findViewById(R.id.seekBarMood);
		 	submitButton = (Button)findViewById(R.id.buttonSubmit);
		 	radioGroup = (RadioGroup)findViewById(R.id.radioGroupPL);
		 if(response == 101){
			 name.setVisibility(View.VISIBLE);
			 emailAddress.setVisibility(View.INVISIBLE);
			 searchableSwitch.setVisibility(View.INVISIBLE);
			 moodBar.setVisibility(View.INVISIBLE);
			 radioGroup.setVisibility(View.INVISIBLE);
		 }else if(response == 102){
			 name.setVisibility(View.INVISIBLE);
			 emailAddress.setVisibility(View.VISIBLE);
			 searchableSwitch.setVisibility(View.INVISIBLE);
			 moodBar.setVisibility(View.INVISIBLE);
			 radioGroup.setVisibility(View.INVISIBLE);
		 }else if(response == 103){
			 name.setVisibility(View.INVISIBLE);
			 emailAddress.setVisibility(View.INVISIBLE);
			 searchableSwitch.setVisibility(View.VISIBLE);
			 moodBar.setVisibility(View.INVISIBLE);
		 }else if(response == 104){
			 name.setVisibility(View.INVISIBLE);
			 emailAddress.setVisibility(View.INVISIBLE);
			 searchableSwitch.setVisibility(View.INVISIBLE);
			 moodBar.setVisibility(View.VISIBLE);
			 radioGroup.setVisibility(View.INVISIBLE);
		 }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
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
}
