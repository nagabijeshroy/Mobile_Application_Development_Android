package com.example.inclass;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("demo1", "inside onCreate");

		final EditText ed = (EditText) findViewById(R.id.editText1);
		final TextView tv1 = (TextView) findViewById(R.id.textView1);

		Button btnEuro = (Button) findViewById(R.id.button1);

		btnEuro.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String textinput = ed.getText().toString();
				// add a toast message here
				try {
					if (textinput.equals("") == false) {
						double textout = Math.round(Float.parseFloat(textinput) * 0.849282 * 100.0) / 100.0;
						tv1.setText(textinput + " USD = " + textout + " EUR");
					} else {
						Context context = getApplicationContext();
						String errmsg = "no string entered";
						int duration = Toast.LENGTH_SHORT;
						Toast toast = Toast.makeText(context, errmsg, duration);
						toast.show();
					}
				} catch (Exception e) {
					Context context = getApplicationContext();
					String errmsg = "error";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, errmsg, duration);
					toast.show();
				}
			}

		});

		Button clrAll1 = (Button) findViewById(R.id.button5);

		clrAll1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				ed.setText("");
				tv1.setText("");

			}
		});

		Button candol = (Button) findViewById(R.id.button2);

		candol.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("demo1", "inside candollar on click");
				String textinput = ed.getText().toString();

				// add a toast message here
				if (textinput.equals("") == false) {

					double textout = Math.round(Float.parseFloat(textinput) * 1.19 * 100.0) / 100.0;

					tv1.setText(textinput + " USD = " + textout + " CAD");
				} else {
					Context context = getApplicationContext();
					String errmsg = "no string entered";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, errmsg, duration);
					toast.show();
				}
			}
		});

		Button btnGBP = (Button) findViewById(R.id.button3);

		btnGBP.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("demo1", "inside GBP on click");
				String textinput = ed.getText().toString();
				if (textinput.equals("") == false) {
					double textout = Math.round(Float.parseFloat(textinput) * 0.65 * 100.0) / 100.0;

					tv1.setText(textinput + " USD = " + textout + " GBP");
				} else {
					Context context = getApplicationContext();
					String errmsg = "no string entered";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, errmsg, duration);
					toast.show();
				}
			}
		});

		Button btnYen = (Button) findViewById(R.id.button4);

		btnYen.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("demo1", "inside JPY on click");
				String textinput = ed.getText().toString();
				if (textinput.equals("") == false) {
					double textout = Math.round(Float.parseFloat(textinput) * 117.62 * 100.0) / 100.0;

					tv1.setText(textinput + " USD = " + textout + " JPY");
				} else {
					Context context = getApplicationContext();
					String errmsg = "no string entered";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, errmsg, duration);
					toast.show();
				}
			}
		});
	}

}// onCreate end

