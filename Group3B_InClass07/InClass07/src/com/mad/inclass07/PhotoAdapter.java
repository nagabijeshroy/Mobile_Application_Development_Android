package com.mad.inclass07;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoAdapter extends ArrayAdapter<Photo> {
	List<Photo> photoList;
	Context photoContext;
	int photoResource;

	// Color color;
	public PhotoAdapter(Context context, int resource, List<Photo> objects) {
		super(context, resource, objects);
		this.photoContext = context;
		this.photoList = objects;
		this.photoResource = resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) photoContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(photoResource, parent, false);
		}
		Photo photo = photoList.get(position);
		ImageView leftImage = (ImageView)convertView.findViewById(R.id.imageViewleftIcon);
		TextView photoTitleTextView = (TextView) convertView.findViewById(R.id.textViewImageText);
		photoTitleTextView.setText(photo.getTitle());
		Picasso.with(photoContext).load(photo.getUrl()).into(leftImage);
		return convertView;
	}
}
