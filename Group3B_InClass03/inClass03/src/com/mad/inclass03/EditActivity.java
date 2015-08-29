package com.mad.inclass03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class EditActivity extends Activity implements OnClickListener {
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
	Student student = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		student = (Student) getIntent().getExtras().getSerializable(MainActivity.STUD_KEY);
		int response = getIntent().getExtras().getInt(DisplayActivity.response_key);
		name = (EditText) findViewById(R.id.editTextName);
		emailAddress = (EditText) findViewById(R.id.editTextEmail);
		searchableSwitch = (Switch) findViewById(R.id.switchSearchable);
		moodBar = (SeekBar) findViewById(R.id.seekBarMood);
		submitButton = (Button) findViewById(R.id.buttonSubmit);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroupPL);
		TextView displayMoodText = (TextView) findViewById(R.id.textViewMood);
		TextView displayPlText = (TextView) findViewById(R.id.textViewPL);
		displayMoodText.setVisibility(View.INVISIBLE);
		displayPlText.setVisibility(View.INVISIBLE);
		emailAddress.setVisibility(View.INVISIBLE);
		searchableSwitch.setVisibility(View.INVISIBLE);
		moodBar.setVisibility(View.INVISIBLE);
		radioGroup.setVisibility(View.INVISIBLE);
		name.setVisibility(View.INVISIBLE);
		name.setText(student.getName());
		emailAddress.setText(student.getEmailAddress());
		RadioButton btnJava = (RadioButton) findViewById(R.id.radioJava);
		RadioButton btnC = (RadioButton) findViewById(R.id.radioC);
		RadioButton btnCs = (RadioButton) findViewById(R.id.radioCs);
		moodBar.setProgress(student.getMood());
		searchableSwitch.setChecked(student.isSearchable());
		if (student.getProgrammingLanguage().equals(btnJava.getText().toString())) {
			btnJava.setChecked(true);
		} else if (student.getProgrammingLanguage().equals(btnC.getText().toString())) {
			btnC.setChecked(true);
		} else if (student.getProgrammingLanguage().equals(btnCs.getText().toString())) {
			btnCs.setChecked(true);
		}
		if (response == 101) {
			name.setVisibility(View.VISIBLE);
		} else if (response == 102) {
			emailAddress.setVisibility(View.VISIBLE);
		} else if (response == 103) {
			displayPlText.setVisibility(View.VISIBLE);
			radioGroup.setVisibility(View.VISIBLE);
		} else if (response == 104) {
			searchableSwitch.setVisibility(View.VISIBLE);
		} else if (response == 105) {
			displayMoodText.setVisibility(View.VISIBLE);
			moodBar.setVisibility(View.VISIBLE);
		}
		submitButton.setOnClickListener(this);
		moodBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			}
		});
		searchableSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				switchStatus = isChecked;
			}
		});
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

	@Override
	public void onClick(View v) {
		student.setEmailAddress(emailAddress.getText().toString());
		student.setName(name.getText().toString());
		student.setMood(moodBar.getProgress());
		student.setSearchable(switchStatus);
		RadioButton btn = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
		student.setProgrammingLanguage(btn.getText().toString());
		Intent returnIntent = new Intent();
		returnIntent.putExtra(MainActivity.STUD_KEY, student);
		setResult(RESULT_OK, returnIntent);
		finish();
	}
}
