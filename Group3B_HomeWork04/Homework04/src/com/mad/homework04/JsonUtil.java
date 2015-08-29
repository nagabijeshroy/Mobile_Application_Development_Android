
/**
 * Homework 4 - ITCS/ITIS 5180
 * 1. Naga Bijesh Roy Raya
 * 2. James Budday
 * 3. Shyam Mohan Raman
 */

package com.mad.homework04;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonUtil {

	static ArrayList<TopicPerson> parseTopicOrPersonJson(String in) {

		ArrayList<TopicPerson> topicOrPersonList = new ArrayList<TopicPerson>();

		try {
			JSONObject root = new JSONObject(in);

			JSONArray topicOrPersonJSONArray = root.getJSONArray("item");

			for (int i = 0; i < topicOrPersonJSONArray.length(); i++) {
				JSONObject PhotoJSONObject = topicOrPersonJSONArray.getJSONObject(i);
				TopicPerson topicPerson = new TopicPerson();
				try {
					topicPerson.setTitle(PhotoJSONObject.getJSONObject("title").getString("$text"));
					topicPerson.setId(Integer.parseInt(PhotoJSONObject.getString("id")));
				} catch (Exception ex) {
					Log.d("demo", ex.getMessage());
					continue;
				}
				topicOrPersonList.add(topicPerson);
			}
			return topicOrPersonList;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	static ArrayList<Story> parseStoryJson(String in) {

		ArrayList<Story> storyList = new ArrayList<Story>();

		try {
			JSONObject root = new JSONObject(in);
			JSONObject listObject = root.getJSONObject("list");
			JSONArray storiesArray = listObject.getJSONArray("story");
			for (int i = 0; i < storiesArray.length(); i++) {
				JSONObject storyJSONObject = storiesArray.getJSONObject(i);
				Story story = new Story();
				try {
					if (storyJSONObject.has("title")) {
						if (storyJSONObject.getJSONObject("title").has("$text")) {
							story.setStoryTitle(storyJSONObject.getJSONObject("title").getString("$text"));
						}
					}
					if (storyJSONObject.has("id")) {
						story.setStoryId(Integer.parseInt(storyJSONObject.getString("id")));
					}
					if (storyJSONObject.has("pubDate")) {
						if (storyJSONObject.getJSONObject("pubDate").has("$text")) {
							story.setPublicationDate(storyJSONObject.getJSONObject("pubDate").getString("$text"));
						}
					}
					if (storyJSONObject.has("miniTeaser")) {
						if (storyJSONObject.getJSONObject("miniTeaser").has("$text")) {
							String str = storyJSONObject.getJSONObject("miniTeaser").getString("$text");
							String upToNCharacters = str.substring(0, Math.min(str.length(), 60));
							story.setMiniTeaser(upToNCharacters);
						}
					}
					if(storyJSONObject.has("teaser")){
						story.setTeaserText(storyJSONObject.getJSONObject("teaser").getString("$text"));
					}
					if (storyJSONObject.has("audio")) {
						if (storyJSONObject.getJSONArray("audio").getJSONObject(0).has("format")) {
							if (storyJSONObject.getJSONArray("audio").getJSONObject(0).getJSONObject("format").has("mp3")) {
								if (storyJSONObject.getJSONArray("audio").getJSONObject(0).getJSONObject("format").getJSONArray("mp3").getJSONObject(0)
										.has("$text")) {
									String url = storyJSONObject.getJSONArray("audio").getJSONObject(0).getJSONObject("format").getJSONArray("mp3")
											.getJSONObject(0).getString("$text");
									story.setMusicUrl(url);
								}
							}
						}
						
					}
					if (storyJSONObject.has("link")) {
						if (storyJSONObject.getJSONArray("link").getJSONObject(0).has("$text")) {
							story.setWebUrl(storyJSONObject.getJSONArray("link").getJSONObject(0).getString("$text"));
						}
					}
					if (storyJSONObject.has("image")) {
						if (storyJSONObject.getJSONArray("image").getJSONObject(0).has("src")) {
							story.setImageUrl(storyJSONObject.getJSONArray("image").getJSONObject(0).getString("src"));
						}
					}
					if (storyJSONObject.has("audioRunByDate")) {
						if (storyJSONObject.getJSONObject("audioRunByDate").has("$text")) {
							story.setDateAired(storyJSONObject.getJSONObject("audioRunByDate").getString("$text"));
						}
					}
					if (storyJSONObject.has("audio")) {
						if (storyJSONObject.getJSONArray("audio").getJSONObject(0).has("duration")) {
							story.setDuration(storyJSONObject.getJSONArray("audio").getJSONObject(0).getJSONObject("duration").getString("$text"));
						}
					}
					if(storyJSONObject.has("byline")){
						if (storyJSONObject.getJSONArray("byline").getJSONObject(0).has("name")) {
							if (storyJSONObject.getJSONArray("byline").getJSONObject(0).getJSONObject("name").has("$text")) {
								story.setReporterName(storyJSONObject.getJSONArray("byline").getJSONObject(0).getJSONObject("name").getString("$text"));
							}
						}
					}
				} catch (Exception ex) {
					Log.d("demo1", ex.getMessage());
					continue;
				}
				storyList.add(story);
			}
			return storyList;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
