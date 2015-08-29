package com.example.inclass08;

import com.example.inclass08.R;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.signpost.signature.SigningStrategy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity implements SignupFragment.IntfSignupFragment, LoginFragment.IntfonLoginFragment, TodoFragment.IntfTodoFragment,
		SplashScreenFragment.IntfSplash {

	EditText emailText;
	EditText passwordText;
	Button loginButton;
	Button createAccountButton;
	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		handler = new Handler(new Handler.Callback() {

			@Override
			public boolean handleMessage(Message msg) {

				switch (msg.what) {
				case DoWork.STATUS_START:
					getFragmentManager().beginTransaction().add(R.id.container, new SplashScreenFragment(), "Splash").commit();
					break;
				case DoWork.STATUS_STEP:
					break;
				case DoWork.STATUS_STOP:
					ParseUser currentUser = ParseUser.getCurrentUser();
					if (currentUser == null) {

						getFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(), "Login").addToBackStack(null).commit();
					} else {
						getFragmentManager().beginTransaction().replace(R.id.container, new TodoFragment(), "Todo").addToBackStack(null).commit();
					}

					break;
				}
				return false;
			}
		});

		new Thread(new DoWork()).start();

	}

	@Override
	public void onLoginClick() {
		getFragmentManager().beginTransaction().replace(R.id.container, new TodoFragment(), "Todo").addToBackStack(null).commit();
	}

	@Override
	public void onSignup() {
		getFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(), "Login").addToBackStack(null).commit();

	}

	@Override
	public void onCreateAccountClick() {
		// TODO Auto-generated method stub

		getFragmentManager().beginTransaction().replace(R.id.container, new SignupFragment(), "Signup").addToBackStack(null).commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (getFragmentManager().findFragmentByTag("Todo") != null) {
			int id = item.getItemId();
			if (id == R.id.action_add) {
				final ParseUser currentUser = ParseUser.getCurrentUser();
				AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
				// Get the layout inflater
				builder.setTitle("Add an Item");
				LayoutInflater inflater = LoginActivity.this.getLayoutInflater();

				// Inflate and set the layout for the dialog
				// Pass null as the parent view because its going in the dialog
				// layout
				final View alertView = inflater.inflate(R.layout.alert_layout, null);
				builder.setView(alertView)
				// Add action buttons
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {

								EditText itemText = (EditText) alertView.findViewById(R.id.itemName);
								if (itemText.getText() != null && !itemText.getText().toString().equals("")) {
									ParseObject todoObject = new ParseObject("ToDo");
									todoObject.put("item", itemText.getText().toString());
									todoObject.put("username", currentUser.getUsername());
									todoObject.saveInBackground();
									getFragmentManager().beginTransaction().replace(R.id.container, new TodoFragment(), "Todo").commit();
								} else {
									Toast.makeText(LoginActivity.this, "Item Text Cannot be empty", Toast.LENGTH_LONG).show();
								}
							}
						}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
							}
						}).show();
			} else if (id == R.id.action_logout) {
				ParseUser.logOut();
				/*
				 * Intent intent = new Intent(ToDoActivity.this,
				 * LoginActivity.class); startActivity(intent); finish(); return
				 * true;
				 */
				getFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(), "Login").addToBackStack(null).commit();
			}
		} else {
			Toast.makeText(LoginActivity.this, "Functionality diabled", Toast.LENGTH_LONG).show();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (getFragmentManager().getBackStackEntryCount() > 0)
			getFragmentManager().popBackStack();
		else
			super.onBackPressed();
	}

	@Override
	public void menuitemclick(Uri uri) {
		// TODO Auto-generated method stub

	}

	class DoWork implements Runnable {
		static final int STATUS_START = 0x01;
		static final int STATUS_STEP = 0x02;
		static final int STATUS_STOP = 0x03;

		@Override
		public void run() {
			Message msg = new Message();
			msg.what = STATUS_START;
			handler.sendMessage(msg);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			msg = new Message();
			msg.what = STATUS_STEP;
			Bundle data = new Bundle();
			msg.setData(data);
			handler.sendMessage(msg);
			msg = new Message();
			msg.what = STATUS_STOP;
			handler.sendMessage(msg);
		}

	}

	@Override
	public void onSplashReturn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCancelClick() {
		getFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment(), "Login").addToBackStack(null).commit();
	}
}
