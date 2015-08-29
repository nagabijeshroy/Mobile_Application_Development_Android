package com.mad.inclass03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText name;
	EditText emailAddress;
	static int programmingLanguageBtnId;
	Switch searchableSwitch;
	SeekBar moodBar;
	Button submitButton;
	static int moodBarValue;
	static boolean switchStatus;
	RadioButton progLang;
	final static String STUD_KEY = "STUDENT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		name = (EditText) findViewById(R.id.editTextName);
		emailAddress = (EditText) findViewById(R.id.editTextEmail);
		final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupPL);
		
		moodBar = (SeekBar) findViewById(R.id.seekBarMood);
		moodBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				moodBarValue = progress;
			}
		});

		searchableSwitch = (Switch) findViewById(R.id.switchSearchable);
		searchableSwitch.setChecked(true);
		searchableSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				switchStatus = isChecked;
			}
		});

		findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String nameText = name.getText().toString();
					String emailText = emailAddress.getText().toString();
					if (nameText == null || nameText.length() == 0 || emailText == null || emailText.length() == 0) {
						Toast.makeText(MainActivity.this, "Name or Email Empty", Toast.LENGTH_SHORT).show();
					} else {
						programmingLanguageBtnId = radioGroup.getCheckedRadioButtonId();
						progLang = (RadioButton) findViewById(programmingLanguageBtnId);
						Student student = new Student(nameText, emailText, progLang.getText().toString(), switchStatus, moodBarValue);
						Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
						intent.putExtra(STUD_KEY, student);
						startActivity(intent);
					}
				} catch (Exception e) {
					Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

}
