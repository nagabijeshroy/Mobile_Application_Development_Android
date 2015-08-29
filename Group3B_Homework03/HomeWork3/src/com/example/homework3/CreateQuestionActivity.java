/**
 * Homework - 3
 * Naga Bijesh Roy Raya
 * James Budday
 * Shyam Mohan Raman
 */

package com.example.homework3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CreateQuestionActivity extends Activity {
	RadioGroup radiogroup;

	EditText et_option;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final String baseUrl = "http://dev.theappsdr.com/apis/trivia/saveNew.php";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_question);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		final StringBuilder sb_main = new StringBuilder();
		final StringBuilder sb_options = new StringBuilder();
		radiogroup = (RadioGroup) findViewById(R.id.create_rg_ans);
		findViewById(R.id.create_iv_addnew).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				et_option = (EditText) findViewById(R.id.create_et_option);
				if (et_option.getText().toString().equals("") || et_option.getText().toString() == null) {
				} else {
					RadioButton radiobutton = new RadioButton(getBaseContext());
					radiobutton.setText(et_option.getText().toString());
					radiobutton.setTextColor(Color.BLACK);
					radiogroup.addView(radiobutton);
					sb_options.append(et_option.getText().toString());
					sb_options.append(";");
					Log.d("current value in the options string", sb_options.toString());
					et_option.setText("");
					// send this string to a http post connection to server.
					// Michael Jordan is the principal owner for which of the
					// below teams?;NC TarHeels;Chicago Bulls;Washington
					// Wizards;Charlotte
					// Hornets;http://ecelebrityfacts.com/uploads/pages/1413445944michael%20jordon.jpg;3

				}
			}
		});

		findViewById(R.id.create_b_submit).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("test",
						"The child count in the radio group and the currently selected radiobutton index is :"
								+ ((RadioGroup) findViewById(R.id.create_rg_ans)).getChildCount() + "  "
								+ ((RadioGroup) findViewById(R.id.create_rg_ans)).getCheckedRadioButtonId());
				if(!(((EditText) findViewById(R.id.create_et_question)).getText().toString()).equals("")){
				if ((((RadioGroup) findViewById(R.id.create_rg_ans)).getChildCount() >= 2)
						&& (((RadioGroup) findViewById(R.id.create_rg_ans)).getCheckedRadioButtonId()) != -1) {
					Log.d("current value in the options string", sb_options.toString());

					sb_main.append((((EditText) findViewById(R.id.create_et_question)).getText().toString())); // 1
					sb_main.append(";"); // 1
					Log.d("current value in the main string with questions", sb_main.toString());
					sb_main.append(sb_options.toString()); // 2
					Log.d("current value in the main string with questions and options", sb_main.toString());
					sb_main.append(((TextView) findViewById(R.id.create_et_imgurl)).getText().toString());
					sb_main.append(";");

					Log.d("current value in the main string with options ", sb_main.toString());

					int answerchoice = ((RadioGroup) findViewById(R.id.create_rg_ans))
							.indexOfChild((findViewById(((RadioGroup) findViewById(R.id.create_rg_ans)).getCheckedRadioButtonId())));

					sb_main.append(answerchoice);
					Log.d("current value in the options string", sb_main.toString());
					Log.d("question to send over to server by post HTTP method", sb_main.toString() + "");

					RequestParams params = new RequestParams("POST", baseUrl);
					params.addParam("gid", "qdkk1TFaWODX6vpQdQYr");
					params.addParam("q", sb_main.toString());

					new SendQuestion().execute(params);

				}
				}else{
					Toast.makeText(CreateQuestionActivity.this, "Question cannot be empty", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	class SendQuestion extends AsyncTask<RequestParams, Void, String> {
		@Override
		protected void onPostExecute(String result) {
			Log.d("done", "Done");
			System.exit(0);
		}

		BufferedReader reader;

		@Override
		protected String doInBackground(RequestParams... params) {
			try {
				HttpURLConnection con = params[0].setupConnection();
				reader = new BufferedReader(new InputStreamReader(con.getInputStream())); // used
																							// for
																							// xmlparsing
																							// Json
																							// parsing
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				sb.append("\nend");
				return sb.toString();

			} catch (Exception e) {
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

	}

}
