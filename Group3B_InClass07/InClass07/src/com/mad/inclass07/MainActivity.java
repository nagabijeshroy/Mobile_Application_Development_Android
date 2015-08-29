package com.mad.inclass07;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static String KEYWORD_KEY = "searchTerm";
	EditText et;
	Button btn;
	String text;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window w = getWindow();
        w.setTitle(getResources().getString(R.string.MainActivity));
        
        et = (EditText) findViewById(R.id.et_search);
        btn = (Button) findViewById(R.id.btn_submit);
        
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				text = et.getText().toString();
				if(!text.equals("")){
				Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
				intent.putExtra(KEYWORD_KEY, text);
				startActivity(intent);
				}else{
					Toast.makeText(MainActivity.this, R.string.noText, Toast.LENGTH_LONG).show();
				}
				
			}
		});
        
    }
}
