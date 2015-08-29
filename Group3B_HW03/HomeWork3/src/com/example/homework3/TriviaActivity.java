package com.example.homework3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TriviaActivity extends Activity {
	BufferedReader reader;
	HttpURLConnection con;
	ProgressDialog progressdialog;
	String line = "";
	ArrayList<QandA> al_questions;
	public static String ANS_COUNT = "NO OF CORRECT ANSWERS";
	public static String QUE_COUNT = "NO OF QUESTIONS";
	int ans_count = 0;
	int currentquestionNumber = 0;
	int totalNoOfQuestions = 0;
	QandA qanda;
	ProgressBar progressbar;
	TextView tv_imgprogress;
	TextView timerTextView;
	long startTime = 24;
	boolean resultactivityreached = false;

	HashMap<Integer, QandA> hm_questions;

	private boolean isConnectedOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = cm.getActiveNetworkInfo();

		if (networkinfo != null && networkinfo.isConnected()) {
			return true;
		}
		return false;
	}

	CountDownTimer time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trivia);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		if (!isConnectedOnline()) {
			Toast.makeText(getBaseContext(), "Internet Connectivity is unavailable,  quitting activity", Toast.LENGTH_LONG).show();
			finish();
		}

		timerTextView = (TextView) findViewById(R.id.trivia_tv_timer);
		// timerHandler.postDelayed(timerRunnable, 0);

		al_questions = new ArrayList<QandA>();

		String getquesURL = "http://dev.theappsdr.com/apis/trivia/getAll.php";
		RequestParams params = new RequestParams("GET", getquesURL);
		new getquestions().execute(params);

		findViewById(R.id.trivia_b_quit).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				time.cancel();
				if (time != null) {
					time = null;
				}
				// time=null;
				finish();
				System.exit(0);
			}
		});

		findViewById(R.id.trivia_b_next).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				NextQuestion();
			}
		});
	}

	public void NextQuestion() {

		if (time != null) {
			time.cancel();
			time = null;

		}
		// time = null;
		Log.d("Question number ", (ans_count + 1) + " <<< no of correct answers " + (currentquestionNumber + 1) + " <<<< current question number "
				+ totalNoOfQuestions + " total number of questions");
		RadioGroup rg_answers = ((RadioGroup) findViewById(R.id.trivia_rg_answers));
		if (rg_answers.getCheckedRadioButtonId() != -1) {
			RadioButton rb_answerselected = (RadioButton) findViewById((rg_answers.getCheckedRadioButtonId()));

			if (al_questions.get(currentquestionNumber).int_answeropt == rg_answers.indexOfChild(rb_answerselected)) {
				++ans_count;
			}
		}
		ImageView imgforquestion = (ImageView) findViewById(R.id.trivia_iv_qimg);
		imgforquestion.setImageBitmap(null);

		if (currentquestionNumber + 1 == totalNoOfQuestions) {
			Intent intent = new Intent(TriviaActivity.this, ResultActivity.class);
			intent.putExtra(ANS_COUNT, ans_count);
			intent.putExtra(QUE_COUNT, totalNoOfQuestions);
			if (time != null) {
				time.cancel();
				time = null;
			}
			resultactivityreached = true;
			startActivity(intent);
			finish();
		} else {
			questionGenerator(al_questions, ++currentquestionNumber);
		}
	}

	/** inner **/
	class getquestions extends AsyncTask<RequestParams, Void, ArrayList<QandA>> {

		public boolean checkStringFormatting(String question) {
			Log.d("next phase", "inside checkStringFormatting");
			String arr[] = question.split(";");
			if (arr.length >= 4) {
				try {
					Integer.parseInt(arr[arr.length - 1]);

				} catch (NumberFormatException e) {
					return false;
				} catch (Exception e) {
					return false;
				}
				if ((Integer.parseInt(arr[arr.length - 1]) <= (arr.length - 3) && (Integer.parseInt(arr[arr.length - 1]) >= 0)))
					return true;
			}
			return false;
		}

		@Override
		protected void onPostExecute(ArrayList<QandA> result) {
			Log.d("checkhere", "inside getquestions postexecute ");
			progressdialog.dismiss();
			totalNoOfQuestions = result.size();
			Log.d("Total number of questions is : ", totalNoOfQuestions + "");
			currentquestionNumber = 0;
			al_questions = result;
			questionGenerator(result, currentquestionNumber);

		}

		@Override
		protected void onPreExecute() {
			Log.d("check", "inside getquestions preexecute ");
			progressdialog = new ProgressDialog(TriviaActivity.this);
			progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressdialog.setCancelable(true);
			progressdialog.setMax(100);
			progressdialog.setTitle("Loading Questions");
			progressdialog.setMessage("Loading...");
			progressdialog.show();
		}

		@Override
		protected ArrayList<QandA> doInBackground(RequestParams... params) {
			Log.d("check", "inside getquestions do in background");
			ArrayList<String> arrLquestions = new ArrayList<String>();
			ArrayList<QandA> arrLQanAobjArray = new ArrayList<QandA>();
			try {
				HttpURLConnection con = params[0].setupConnection();

				reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				int int_arraylistIndex = 0;
				while ((line = reader.readLine()) != null) {
					if (!checkStringFormatting(line)) {
						continue;
					} else {
						arrLquestions.add(line);
						Log.d("check the value of str_eachquestion", line);
						qanda = new QandA(line, int_arraylistIndex);
						int_arraylistIndex++;
						arrLQanAobjArray.add(qanda);
					}
				}
				return arrLQanAobjArray;

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (reader != null) {
						reader.close();
					}
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

	} // end of questions

	public int nthOccurrence(String line, char semicolon, int n) {
		int pos = line.indexOf(semicolon, 0);
		while (n-- > 0 && pos != -1)
			pos = line.indexOf(semicolon, pos + 1);
		return pos;
	}

	class imageloader extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected void onPreExecute() {
			findViewById(R.id.trivia_iv_qimg).setVisibility(View.INVISIBLE);
			ImageView imgforquestion = (ImageView) findViewById(R.id.trivia_iv_qimg);
			imgforquestion.setImageBitmap(null);
			progressbar = (ProgressBar) findViewById(R.id.progressBarImg);
			tv_imgprogress = (TextView) findViewById(R.id.trivia_tv_imgprogress);
			progressbar.setVisibility(View.VISIBLE);
			progressbar.bringToFront();

			tv_imgprogress.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			ImageView imgforquestion = (ImageView) findViewById(R.id.trivia_iv_qimg);
			imgforquestion.setImageBitmap(null);
			if (result != null) {

				imgforquestion.setImageBitmap(result);
			} else if (result == null) {
				imgforquestion.setImageResource(R.drawable.no_image);
			}
			progressbar = (ProgressBar) findViewById(R.id.progressBarImg);
			tv_imgprogress = (TextView) findViewById(R.id.trivia_tv_imgprogress);
			progressbar.setVisibility(View.INVISIBLE);
			tv_imgprogress.setVisibility(View.INVISIBLE);
			findViewById(R.id.trivia_iv_qimg).setVisibility(View.VISIBLE);
			if (time != null) {
				time = null;
			}
			time = new CountDownTimer(24000, 1000) {

				public void onTick(long millisUntilFinished) {
					
					((TextView) findViewById(R.id.trivia_tv_timer)).setText("Time Left: " + millisUntilFinished / 1000 + " seconds");

				}

				public void onFinish() {
					this.cancel();
					if(!resultactivityreached){
					NextQuestion();
					}
				}
			}.start();

		}

		@Override
		protected Bitmap doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				con = (HttpURLConnection) url.openConnection();

				Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
				return image;

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

			}

			return null;
		}

	}

	public void questionGenerator(ArrayList<QandA> arrayListofQuestionObjects, int questioncount) {

		Log.d("check", arrayListofQuestionObjects.size() + " total size of the hashmap");

		hm_questions = new HashMap<Integer, QandA>();

		for (int i = 0; i < arrayListofQuestionObjects.size(); i++) {
			hm_questions.put(i, arrayListofQuestionObjects.get(i));
		}

		int i = questioncount; // This is gonna run for
								// every question.

		Log.d("tv_qnum has the value", ((TextView) findViewById(R.id.tv_qnum)).getText().toString());
		((TextView) findViewById(R.id.tv_qnum)).setText("Q" + (hm_questions.get(i).int_questionnumber + 1) + "");

		// I could have just used iterator T_T

		((TextView) findViewById(R.id.trivia_tv_ques)).setText(hm_questions.get(i).str_question);
		// easy ones are over

		RadioGroup rg_answers = (RadioGroup) findViewById(R.id.trivia_rg_answers);
		rg_answers.removeAllViews();
		// iterate for number of times there is an answer.
		for (int iterator = 0; iterator < hm_questions.get(i).strarr_ans.length; iterator++) {
			RadioButton rb_answer = new RadioButton(this);
			rb_answer.setText(hm_questions.get(i).strarr_ans[iterator]);
			rb_answer.setLayoutParams(new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
			rg_answers.addView(rb_answer);
		}
		new imageloader().execute(hm_questions.get(i).str_imgURL);

		Log.d("ed", "question " + i + " was processed");

	}
}
