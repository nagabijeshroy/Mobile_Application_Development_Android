package com.example.inclass;

/*Team Number : 3B
 1. Naga Bijesh Roy Raya
 2. Shyam Mohan Raman
 3. James Budday
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
		TextView textView = (TextView) findViewById(R.id.textView1);
		textView.setTextColor(Color.GRAY);
	}

	@Override
	public void onClick(View v) {
		TextView textView = (TextView) findViewById(R.id.textView1);
		EditText editText = (EditText) findViewById(R.id.editText1);
		if (editText.getText().toString().equals("")==false) {
			try{
			double textValue = Float.parseFloat(editText.getText().toString());
			String text = editText.getText().toString();
			RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
			RadioButton radioButton0 = (RadioButton) findViewById(R.id.radio0);
			RadioButton radioButton1 = (RadioButton) findViewById(R.id.radio1);
			RadioButton radioButton2 = (RadioButton) findViewById(R.id.radio2);
			RadioButton radioButton3 = (RadioButton) findViewById(R.id.radio3);
			RadioButton radioButton4 = (RadioButton) findViewById(R.id.radio4);
			int id = radioGroup.getCheckedRadioButtonId();
			if (id == radioButton0.getId()) {
				Log.d("inclass2", "EUR");
				textView.setTextColor(Color.BLACK);
				textView.setText(text + "USD = "
						+ (Math.round((textValue * 0.849282) * 100.0) / 100.0)
						+ "EUR");
			} else if (id == radioButton1.getId()) {
				Log.d("inclass2", "CAD");
				textView.setTextColor(Color.BLACK);
				textView.setText(text + "USD = "
						+ (Math.round((textValue * 1.19) * 100.0) / 100.0)
						+ "CAD");
			} else if (id == radioButton2.getId()) {
				Log.d("inclass2", "GBP");
				textView.setTextColor(Color.BLACK);
				textView.setText(text + "USD = "
						+ (Math.round((textValue * 0.65) * 100.0) / 100.0)
						+ "GBP");
			} else if (id == radioButton3.getId()) {
				Log.d("inclass2", "JPY");
				textView.setTextColor(Color.BLACK);
				textView.setText(text + "USD = "
						+ (Math.round((textValue * 117.62) * 100.0) / 100.0)
						+ "JPY");
			} else if (id == radioButton4.getId()) {
				Log.d("inclass2", "Clear");
				textView.setTextColor(Color.GRAY);
				textView.setText("");
				editText.setText("");
				textView.setHint("RESULT");
				editText.setHint("AMOUNT");
			}
			}catch(Exception e){
				Context context = getApplicationContext();
				CharSequence text = "Error";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		}else{
			Context context = getApplicationContext();
			CharSequence text = "Error";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}
}
