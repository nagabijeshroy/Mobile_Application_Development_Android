/*
 * Naga Bijesh Roy Raya
 * Shyam Mohan Raman
 */

package com.example.homework5;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ApplicationAdapter extends ArrayAdapter<Application> {
	List<Application> storyData;
	Context storyContext;
	int storyResource;

	public ApplicationAdapter(Context context, int resource, List<Application> objects) {
		super(context, resource, objects);
		this.storyContext = context;
		this.storyData = objects;
		this.storyResource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) storyContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(storyResource, parent, false);
		}
		Application application = storyData.get(position);
		ImageView smallIconImage = (ImageView)convertView.findViewById(R.id.imageViewsmallIcon);
		TextView appTitle = (TextView)convertView.findViewById(R.id.textViewAppTitle);
		appTitle.setText(application.getAppTitle());
		TextView developerName = (TextView)convertView.findViewById(R.id.textViewDeveloperName);
		developerName.setText(application.getDeveloperName());
		TextView price = (TextView)convertView.findViewById(R.id.textViewPrice);
		if(application.getPrice()!=0){
			price.setText(application.getPrice()+"");
		}
		if (application.getLargePhotoUrl() != null && application.getLargePhotoUrl().length() != 0) {
			Picasso.with(storyContext).load(application.getLargePhotoUrl()).into(smallIconImage);
		}
		return convertView;
	}
}
