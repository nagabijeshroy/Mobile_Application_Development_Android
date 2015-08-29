/**
 * Homework - 3
 * Naga Bijesh Roy Raya
 * James Budday
 * Shyam Mohan Raman
 */

package com.example.homework3;

import java.math.BigDecimal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
		int totalnumberOfquestions = getIntent().getExtras().getInt(TriviaActivity.QUE_COUNT);
		int totalnoOfcorrectans = getIntent().getExtras().getInt(TriviaActivity.ANS_COUNT);
		
		double percentage = (double)totalnoOfcorrectans/(double)totalnumberOfquestions * 100;
		BigDecimal percent = new BigDecimal(percentage);
		percent = percent.setScale(2, BigDecimal.ROUND_UP);
		
		((TextView)findViewById(R.id.result_tv_percentage)).setText(percent + " %");
		((ProgressBar)findViewById(R.id.result_pb_percentage)).setMax(totalnumberOfquestions);
		((ProgressBar)findViewById(R.id.result_pb_percentage)).setProgress(totalnoOfcorrectans);
		
		
		findViewById(R.id.result_b_quit).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				intent.putExtra("msg", "quit");
				finish();
			}
		});
		
		
		findViewById(R.id.result_b_tryagain).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				intent.putExtra("msg", "try again");
				finish();
			}
		});
	}
}
