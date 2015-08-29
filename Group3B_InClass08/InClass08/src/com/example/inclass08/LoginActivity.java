package com.example.inclass08;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class LoginActivity extends Activity {

	EditText emailText;
	EditText passwordText;
	Button loginButton;
	Button createAccountButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getActionBar().setTitle("Login");
        // Initialize Crash Reporting.
        //ParseCrashReporting.enable(this);

        // Enable Local Datastore.
        //Parse.enableLocalDatastore(this);

        // Add your initialization code here
        //Parse.initialize(this, "wXJFUoge2GekhJnjzOZEE6ILcrKYCy7rIXSLGwJb", "jqdg8tlrVVGazdiu9WC8h5TzkWdh8qKlm2CofGuH");
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
        	Intent intent = new Intent(LoginActivity.this, ToDoActivity.class);
        	startActivity(intent);
        	finish();
        } else {
        	 emailText= (EditText)findViewById(R.id.editTextEmail);
             passwordText= (EditText)findViewById(R.id.editTextPassword);
             loginButton= (Button)findViewById(R.id.buttonLogin);
             loginButton.setOnClickListener(new OnClickListener() {
     			@Override
     			public void onClick(View v) {
     				if(emailText.getText().toString().length()!=0&&!emailText.getText().toString().trim().equals("")
     						&&passwordText.getText().toString().length()!=0&&!passwordText.getText().toString().trim().equals("")){
     					ParseUser.logInInBackground(emailText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
							
							@Override
							public void done(ParseUser user, ParseException e) {
								if(user!=null){
									Intent intent = new Intent(LoginActivity.this, ToDoActivity.class);
						        	startActivity(intent);
						        	finish();
								}else{
									Toast.makeText(LoginActivity.this, "Incorrect Email or Password", Toast.LENGTH_LONG).show();
								}
							}
						});
     				}else{
     					Toast.makeText(LoginActivity.this, "Email and password cannot be empty", Toast.LENGTH_LONG).show();
     				}
     			}
     		});
             
            createAccountButton = (Button)findViewById(R.id.buttonCreateNewAccount);
            createAccountButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(LoginActivity.this, SignUp.class);
		        	startActivity(intent);
		        	finish();
				}
			});
        }
       
        
    }
}
