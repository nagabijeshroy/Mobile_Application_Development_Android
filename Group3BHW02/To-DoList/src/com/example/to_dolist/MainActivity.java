package com.example.to_dolist;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener{
	LinkedList<Task> tasks = new LinkedList<Task>();
	int numberOfTasks;
	TextView tv;
	ScrollView sv;
	LinearLayout l;
	String titleText;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        tv = (TextView) findViewById(R.id.textViewTitl);
        sv = (ScrollView) findViewById(R.id.scrollView1);
        l = (LinearLayout) sv.getChildAt(0);
        
          
        
        updateLayout();
        
        
        //on new task click
        findViewById(R.id.imageButtonNewTask).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, CreateTask.class);
				startActivityForResult(intent, AppConstants.REQ_CODE_CREATE);
				
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //handles all clicks of existing tasks and sends intent to DisplayTask
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
		intent.putExtra(AppConstants.TASK_CODE, tasks.get(v.getId()));
		intent.putExtra(AppConstants.INDEX_CODE, v.getId());
		startActivityForResult(intent, AppConstants.REQ_CODE_DISPLAY);
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == AppConstants.REQ_CODE_CREATE){
			if(resultCode == RESULT_OK){
				Task task = (Task) data.getExtras().getSerializable(AppConstants.TASK_CODE);
				//Toast.makeText(this, "Value received is: " + task, Toast.LENGTH_LONG).show();
				tasks.add(task);
				updateLayout();
			}else if (resultCode == RESULT_CANCELED){
				Toast.makeText(this, "No task created" , Toast.LENGTH_LONG).show();
			}
		}else if(requestCode == AppConstants.REQ_CODE_DISPLAY){
			if(resultCode == RESULT_OK){
				Log.d("test", "got to before if boolean");
				if(data.getBooleanExtra(AppConstants.DISCARD_CODE, false)){
					Log.d("test", "inside if boolean");
					int removeIndex = data.getIntExtra(AppConstants.INDEX_CODE, -1);
					if (removeIndex != -1){
						tasks.remove(removeIndex);
					}
				}else{
					//TODO fixerino
					Toast.makeText(this, data.getSerializableExtra(AppConstants.TASK_CODE_EDIT).toString(), Toast.LENGTH_LONG).show();
					/*Log.d("test", "got to before index");
					int updateIndex = data.getIntExtra(AppConstants.INDEX_CODE, -1);
					Log.d("test", "got to before if != -1");
					if(updateIndex != -1){
						Log.d("test", "got to inside of update");
						tasks.remove(updateIndex);
						Log.d("test", "got to removing task");
						tasks.add(updateIndex, (Task) data.getSerializableExtra(AppConstants.TASK_CODE));
						Log.d("test", "reinserted updated task");
					}*/
				}Log.d("test", "skipped else");
			}
			updateLayout();
		}
	}
	
	public void updateLayout(){
		if (tasks.size() == 1){
			titleText = "1 Task";
		}else{
			titleText = tasks.size() + " Tasks";
		}
		tv.setText(titleText);
		l.removeAllViews();
        for(int i = 0 ; i < tasks.size() ; i++){
        	//create the four lines necessary for data and spacing
        	LinearLayout lHorizontal0 = new LinearLayout(this);
        	LinearLayout lHorizontal1 = new LinearLayout(this);
        	LinearLayout lHorizontal2 = new LinearLayout(this);
        	LinearLayout lHorizontal3 = new LinearLayout(this);
        	
        	//create the textviews to populate lines
        	TextView taskName = new TextView(this);
        	TextView dateName = new TextView(this);
        	TextView timeName = new TextView(this);
        	TextView blankLine = new TextView(this);
        	
        	//add lines to vertical layout
        	l.addView(lHorizontal0);
        	l.addView(lHorizontal1);
        	l.addView(lHorizontal2);
        	l.addView(lHorizontal3);
        	
        	//add text to textviews
        	taskName.setText(tasks.get(i).getTitle());
        	dateName.setText("     " + tasks.get(i).timeString());
        	timeName.setText("     " + tasks.get(i).dateString());
        	blankLine.setText(" ");
        	
        	//set attributes of task title
        	taskName.setId(i);
        	taskName.setTextAppearance(this, R.style.mediumText);
        	taskName.setOnClickListener(this);
        	
        	//add text to lines
        	lHorizontal0.addView(taskName);
        	lHorizontal1.addView(timeName);
        	lHorizontal2.addView(dateName);
        	lHorizontal3.addView(blankLine);
        	
        }
	}
}
