
/**
 * Homework 4 - ITCS/ITIS 5180
 * 1. Naga Bijesh Roy Raya
 * 2. James Budday
 * 3. Shyam Mohan Raman
 */

package com.mad.homework04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class StoryActivity extends Activity implements MediaPlayerControl {

	TextView titleTextView;
	TextView reporterNameTextView;
	TextView duration;
	TextView teaserText;
	ImageView backButtonImage;
	ImageView websiteImage;
	ImageView musicImage;
	ImageView favoritesImage;
	private MediaController mMediaController;
	private MediaPlayer mediaPlayer;
	private int dur;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story);

		titleTextView = (TextView) findViewById(R.id.textViewStoriesTitle);
		reporterNameTextView = (TextView) findViewById(R.id.textViewReporterName);
		duration = (TextView) findViewById(R.id.textViewLength);
		teaserText = (TextView) findViewById(R.id.textViewTeaser);

		final Story story = (Story) getIntent().getExtras().getSerializable(StoriesActivity.STORY);
		if (story.getStoryTitle() != null) {
			titleTextView.setText(story.getStoryTitle());
		} else {
			titleTextView.setText("");
		}
		if (story.getReporterName() != null) {
			reporterNameTextView.setText(story.getReporterName());
		} else {
			reporterNameTextView.setText(getResources().getString(R.string.no_reporter));
		}
		if (story.getTeaserText() != null) {
			teaserText.setText(story.getTeaserText());
		} else {
			teaserText.setText("");
		}
		if (story.getDateAired() != null && story.getDuration() != null) {
			dur = Integer.parseInt(story.getDuration()) * 1000;
			duration.setText(story.getDateAired() + " " + (Integer.parseInt(story.getDuration()) / 60) + "min "
					+ (Integer.parseInt(story.getDuration()) % 60 + " sec"));
		} else {
			duration.setText("");
		}
		favoritesImage = (ImageView) findViewById(R.id.imageViewRating);
		boolean flag = false;
		if (MainActivity.storiesList != null && MainActivity.storiesList.size() != 0) {
			ArrayList<Story> storyList = MainActivity.storiesList;

			for (Story story1 : storyList) {
				if (story.getStoryId() == story1.getStoryId()) {
					flag = true;
					break;
				}
			}

		}
		if (flag == true) {
			favoritesImage.setImageResource(R.drawable.rating_important);
		}
		backButtonImage = (ImageView) findViewById(R.id.imageViewBack);
		backButtonImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mMediaController != null && mediaPlayer != null) {
					mediaPlayer.release();
					mediaPlayer = null;
				}
				setResult(RESULT_OK);
				finish();
			}
		});
		websiteImage = (ImageView) findViewById(R.id.imageViewWebsite);
		websiteImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (story.getWebUrl() != null) {
					Uri uri = Uri.parse(story.getWebUrl());
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
				} else {
					Toast.makeText(StoryActivity.this, getResources().getString(R.string.no_web), Toast.LENGTH_SHORT).show();
				}

			}
		});
		musicImage = (ImageView) findViewById(R.id.imageViewVolume);
		musicImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (story.getMusicUrl() != null) {
					String url = story.getMusicUrl(); // your URL here
					new AsyncMediaUrl().execute(url);
				} else {
					Toast.makeText(StoryActivity.this, getResources().getString(R.string.no_music), Toast.LENGTH_SHORT).show();
				}
			}
		});

		favoritesImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				boolean flag = false;
				int i = 0;
				if (MainActivity.storiesList != null && MainActivity.storiesList.size() != 0) {
					ArrayList<Story> storyList = MainActivity.storiesList;

					for (Story story1 : storyList) {
						if (story.getStoryId() == story1.getStoryId()) {
							flag = true;
							break;
						}
						i++;
					}
				}
				if (flag) {
					MainActivity.storiesList.remove(i);
					favoritesImage.setImageResource(R.drawable.rating_not_important);
				} else {
					favoritesImage.setImageResource(R.drawable.rating_important);
					MainActivity.storiesList.add(story);
				}
				if(MainActivity.storiesList!=null && MainActivity.storiesList.size()!=0){
					SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
					Editor editor = sharedpreferences.edit();
					Gson gson = new Gson();
					String str = gson.toJson(MainActivity.storiesList);
					editor.putString(MainActivity.SHARED_PREFERENCES_KEY, str);
					editor.commit();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.story, menu);
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

	class AsyncMediaUrl extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				Uri myUri = Uri.parse(result);
				mMediaController = new MediaController(StoryActivity.this);
				mMediaController.setMediaPlayer(StoryActivity.this);
				mMediaController.setAnchorView(findViewById(R.id.audioView));
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				try {
					mediaPlayer.setDataSource(StoryActivity.this, myUri);
					mediaPlayer.prepare();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} // might take long! (for buffering, etc)
				mediaPlayer.start();
				mMediaController.show(dur);
				mMediaController.setEnabled(true);
				mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						if (mMediaController != null) {
							mediaPlayer.reset();
							mediaPlayer.release();
							mediaPlayer = null;
						}
					}
				});

			} else {
				Toast.makeText(StoryActivity.this, getResources().getString(R.string.no_music), Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();

				con.setRequestMethod("GET");
				con.connect();

				int statusCode = con.getResponseCode();

				if (statusCode == HttpURLConnection.HTTP_OK) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

					StringBuilder sb = new StringBuilder();

					String line = "";

					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					return sb.toString();

				}
			} catch (Exception e) {

			}
			return null;

		}

	}

	@Override
	public int getBufferPercentage() {
		int percentage = (mediaPlayer.getCurrentPosition() * 100) / mediaPlayer.getDuration();
		return percentage;
	}

	@Override
	public boolean canPause() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getAudioSessionId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		return mediaPlayer.getCurrentPosition();
	}

	@Override
	public int getDuration() {
		return mediaPlayer.getDuration();
	}

	@Override
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	@Override
	public void pause() {
		if (mediaPlayer.isPlaying())
			mediaPlayer.pause();
	}

	@Override
	public void seekTo(int pos) {
		mediaPlayer.seekTo(pos);
	}

	@Override
	public void start() {
		mediaPlayer.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mMediaController != null) {
			mMediaController.show();
		}

		return false;
	}

}
