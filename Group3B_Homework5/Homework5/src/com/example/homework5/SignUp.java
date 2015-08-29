/*
 * Naga Bijesh Roy Raya
 * Shyam Mohan Raman
 */
package com.example.homework5;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends Activity {
	Button cancelButton;
	EditText firstNameText;
	EditText lastNameText;
	EditText emailText;
	EditText passwordText;
	EditText confirmPasswordText;
	Button signupButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		firstNameText = (EditText)findViewById(R.id.editTextUserName);
		lastNameText = (EditText)findViewById(R.id.editTextLastName);
		emailText = (EditText)findViewById(R.id.editTextEmail);
		passwordText = (EditText)findViewById(R.id.editTextPassword);
		confirmPasswordText = (EditText)findViewById(R.id.editTextPasswordConfirm);
		signupButton = (Button)findViewById(R.id.buttonSignup);
		signupButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(firstNameText.getText().toString().trim().equals("")||emailText.getText().toString().trim().equals("")||lastNameText.getText().toString().trim().equals("")
						||passwordText.getText().toString().trim().equals("")||confirmPasswordText.getText().toString().trim().equals("")){
					Toast.makeText(SignUp.this, "Form fields cannot be empty", Toast.LENGTH_LONG).show();
				}else{
					if(passwordText.getText().toString().trim().length()!=0&&passwordText.getText().toString().equals(confirmPasswordText.getText().toString())){
						final ParseUser user = new ParseUser();
						user.setUsername(emailText.getText().toString());
						user.setPassword(passwordText.getText().toString());
						user.setEmail(emailText.getText().toString());
						//Log.d("signup",firstNameText.getText().toString()+lastNameText.getText().toString());
						// other fields can be set just like with ParseObject
						user.signUpInBackground(new SignUpCallback() {
							@Override
							public void done(ParseException e) {
								if(e==null){
									
									Toast.makeText(SignUp.this, "Sign Up Successful", Toast.LENGTH_LONG).show();
									final Intent intent = new Intent(SignUp.this, ToDoActivity.class);
									ParseUser.logInInBackground(emailText.getText().toString(), passwordText.getText().toString(),new LogInCallback() {
										@Override
										public void done(ParseUser user, ParseException e) {
											ParseQuery<ParseUser> query = ParseUser.getQuery();
											ParseUser currentUser = ParseUser.getCurrentUser();
											query.whereEqualTo("username", currentUser.getUsername());
											query.findInBackground(new FindCallback<ParseUser>() {
												@Override
												public void done(List<ParseUser> objects, ParseException e) {
													if(e==null){
														ParseUser obj = objects.get(0);
														obj.put("firstName", firstNameText.getText().toString());
														obj.put("lastName", lastNameText.getText().toString());
														obj.saveInBackground();
													}
												}
											});
											startActivity(intent);
											finish();
										}
									});
								}else{
									//Log.d("exception",e.getMessage()+"");
									Toast.makeText(SignUp.this,e.getMessage()+"", Toast.LENGTH_LONG).show();
								}
							}
						});
					}else{
						Toast.makeText(SignUp.this, "Password and confirm password didn't match", Toast.LENGTH_LONG).show();
					}
				}
			}
		});
			
		
		cancelButton = (Button)findViewById(R.id.buttonCancel);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SignUp.this, LoginActivity.class);
	        	startActivity(intent);
	        	finish();
			}
		});
	}

}
