
/**
 * Homework 4 - ITCS/ITIS 5180
 * 1. Naga Bijesh Roy Raya
 * 2. James Budday
 * 3. Shyam Mohan Raman
 */
package com.mad.homework04;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StoryAdapter extends ArrayAdapter<Story> {
	List<Story> storyData;
	Context storyContext;
	int storyResource;

	// Color color;
	public StoryAdapter(Context context, int resource, List<Story> objects) {
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
		Story story = storyData.get(position);
		TextView storyTitleTextView = (TextView) convertView.findViewById(R.id.textViewStoriesTitle);
		TextView storyPublicationTextView = (TextView) convertView.findViewById(R.id.textViewPublicationDate);
		TextView storyMiniTeaserTextView = (TextView) convertView.findViewById(R.id.textViewMiniTeaser);
		ImageView imageViewStory = (ImageView) convertView.findViewById(R.id.imageViewStoryIcon);
		storyTitleTextView.setText(story.getStoryTitle());
		storyPublicationTextView.setText(story.getPublicationDate());
		storyMiniTeaserTextView.setText(story.getMiniTeaser());
		if (story.getImageUrl() != null && story.getImageUrl().length() != 0) {
			Picasso.with(storyContext).load(story.getImageUrl()).into(imageViewStory);
		}
		return convertView;
	}
}
