/*
 * Assignment # : HomeWork Assignment 1
 * FileName 	: Group3B_HW01
 * Group Members:
 * *James Budday
 * *Naga Brijesh Roy
 * *Shyam Mohan Raman
 */

package com.mad.homework;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private static Context mContext;
	private static ArrayList<Integer> fruitIdList;
	private static ArrayList<Integer> removedPositions = new ArrayList<Integer>();
	private static Integer[] mThumbIds = new Integer[25];
	public ImageAdapter(Context c, ArrayList<Integer> fruitIds) {
		mContext = c;
		removedPositions = new ArrayList<Integer>();
		fruitIdList = fruitIds;
		for(int i=0;i<25;i++){
			mThumbIds[i] = fruitIdList.get(i);
		}
	}

	public ImageAdapter(Context applicationContext, ArrayList<Integer> newShuffledFruits, ArrayList<Integer> removedPositionList) {
		mContext = applicationContext;
		//Log.d("test1","Inside Grid new Constructor");
		for(int i=0,j=0;i<25;i++){
			//Log.d("thumbs1","Thumbs actual value"+ mThumbIds[i]+"");
			if(removedPositionList.contains(i)==false){
				mThumbIds[i] = newShuffledFruits.get(j);
				//Log.d("thumbs","Thumbs replaced value"+ mThumbIds[i]+"");
				j++;
			}
		}
		removedPositions = removedPositionList;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(mContext);
			//imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setImageResource(mThumbIds[position]);
		if(removedPositions!=null&&removedPositions.contains(position)){
			View v = imageView;
			v.setAlpha((float) 0.4);
			v.setFocusable(true);
			v.setFocusableInTouchMode(true);
		}
		return imageView;
	}
	
	/*public void reset(){
		ArrayList<Integer> fruitIdList = ;
		ArrayList<Integer> removedPositions = new ArrayList<Integer>();
		Integer[] mThumbIds = new Integer[25];
	}*/
	// references to our images
	
}
