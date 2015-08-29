package com.mad.inclass07;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity {

	String URL;
	String Owner;
	String Title;
	Photo photo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
	     
		photo = (Photo)getIntent().getExtras().getSerializable(GalleryActivity.PHOTO_DATA);
		
		URL = photo.getUrl();
		Owner = photo.getOwner();
		Title = photo.getTitle();
		
		
		new AsyncGetImage(this).execute(URL);
		
		
	}
	public void loadimage(Bitmap image) {
		TextView tv_title = (TextView)findViewById(R.id.tv_details_title);
		TextView tv_owner = (TextView)findViewById(R.id.tv_details_owner);
		
		ImageView im_photo = (ImageView)findViewById(R.id.iv_details_photo);
		tv_title.setText(Title);
		tv_owner.setText("By: " + Owner);
		if(image != null){
			im_photo.setImageBitmap(image);
		}
	}
}
