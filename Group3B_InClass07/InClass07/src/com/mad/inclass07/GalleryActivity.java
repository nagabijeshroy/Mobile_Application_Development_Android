package com.mad.inclass07;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class GalleryActivity extends Activity {
	String url, keyWord;
	ListView listview;
	final static String PHOTO_DATA = "photoData";
	DatabaseDataManager dm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		Window w = getWindow();
		w.setTitle(getResources().getString(R.string.GalleryActivity));
		keyWord = getIntent().getExtras().getString(MainActivity.KEYWORD_KEY);
		dm = new DatabaseDataManager(this);
		url = "https://api.500px.com/v1/photos/search?consumer_key=aXKydj6e1yRydC3ZAjIIu7NpTuSj5SwuwM0aF8Xz&term=" + keyWord + "&image_size=4&rpp=50";
		Log.d("test", url);
		new AsyncTaskJSON(this).execute(url);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void populateList(final ArrayList<Photo> photo) {
		listview = (ListView) findViewById(R.id.listView1);
		PhotoAdapter adapter = new PhotoAdapter(GalleryActivity.this, R.layout.photo_list_layout, photo);
		listview.setAdapter(adapter);
		adapter.setNotifyOnChange(true);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(GalleryActivity.this, DetailsActivity.class);
				intent.putExtra(GalleryActivity.PHOTO_DATA, photo.get(position));
				startActivityForResult(intent, 101);
			}

		});
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("demo", "clicked here" + position);
				ImageView rightImage = (ImageView) view.findViewById(R.id.imageViewRightRating);
				Photo photo1 = photo.get(position);
				PhotoList photoList = dm.getPhoto(photo1.getId());
				if (photoList != null) {
					dm.deletePhoto(photoList);
					rightImage.setImageResource(R.drawable.favorites_grey);
				} else {
					photoList = new PhotoList(photo1.getId(), photo1.getTitle(), photo1.getUrl(), photo1.getOwner());
					dm.savePhoto(photoList);
					rightImage.setImageResource(R.drawable.favorites_yellow);
				}
				return false;
			}
		});

	}
}
