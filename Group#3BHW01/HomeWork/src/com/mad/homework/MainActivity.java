/*
 * Assignment # : HomeWork Assignment 1
 * FileName 	: Group3B_HW01
 * Group Members:
 * *James Budday
 * *Naga Bijesh Roy Raya
 * *Shyam Mohan Raman
 */

package com.mad.homework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("UseSparseArrays")
public class MainActivity extends Activity implements OnItemClickListener {

	static ArrayList<Integer> removedPositionList;
	static HashMap<Integer, Integer> actualMap ;
	static ArrayList<Integer> grid;
	static int numberOfFruit;
	static int randfruit, randId;
	GridView gridview;
	TextView textView;
	String text;

	@SuppressLint("UseSparseArrays")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		removedPositionList = new ArrayList<Integer>();
		actualMap = new HashMap<Integer, Integer>();
		grid = new ArrayList<Integer>();
		ArrayList<Integer> fruitsList = new ArrayList<Integer>();
		fruitsList.add(R.drawable.apple);
		fruitsList.add(R.drawable.lemon);
		fruitsList.add(R.drawable.mango);
		fruitsList.add(R.drawable.peach);
		fruitsList.add(R.drawable.strawberry);
		fruitsList.add(R.drawable.tomato);
		Random random = new Random();
		randfruit = random.nextInt(fruitsList.size());
		randId = fruitsList.get(randfruit);
		
		numberOfFruit = random.nextInt(8) + 1;
		for (int i = 0; i < numberOfFruit; i++) {
			grid.add(randId);
		}
		fruitsList.remove(randfruit);
		for (int i = numberOfFruit; i < 25; i++) {
			int tempFruit = fruitsList.get(random.nextInt(5));
			grid.add(tempFruit);
		}
		Collections.shuffle(grid);
		text = null;
		switch (randId) {
		case R.drawable.apple:
			text = "Apples";
			break;
		case R.drawable.lemon:
			text = "Lemons";
			break;
		case R.drawable.mango:
			text = "Mangoes";
			break;
		case R.drawable.peach:
			text = "Peaches";
			break;
		case R.drawable.strawberry:
			text = "Strawberries";
			break;
		case R.drawable.tomato:
			text = "Tomatoes";
			break;
		default:
			break;
		}
		textView = (TextView) findViewById(R.id.header);
		gridview = (GridView) findViewById(R.id.gridview);
		//
		gridview.setPadding(10, 0, 10, 10);
		textView.setText("Find All " + text +" ("+ (numberOfFruit)+")");
		for (int i = 0; i < 25; i++) {
			actualMap.put(i, grid.get(i));
		}
		gridview.setAdapter(new ImageAdapter(this, grid));
		
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				try {
					if (actualMap.get(position) == randId) {
						removedPositionList.add(position);
						textView.setText("Find all " + text+" (" + (numberOfFruit - removedPositionList.size())+")");
						actualMap.remove(position);
						ArrayList<Integer> newShuffledFruits = new ArrayList<Integer>();
						for (int i = 0; i < 25; i++) {
							if (removedPositionList.contains(i) == false) {
								newShuffledFruits.add(actualMap.get(i));
							}
						}
						Collections.shuffle(newShuffledFruits);
						for (int i = 0, j = 0; i < 25; i++) {
							if (removedPositionList.contains(i) == false) {
								actualMap.put(i, newShuffledFruits.get(j));
								// Log.d("test","Adding elemenrs to actual map again"+actualMap.get(j)+"in ith position"+i);
								j++;
							}
						}
						Log.d("test1", "Grid called again");
						gridview.setAdapter(new ImageAdapter(getApplicationContext(), newShuffledFruits, removedPositionList));
						if (removedPositionList.size() == numberOfFruit) {
							new AlertDialog.Builder(MainActivity.this)
							.setTitle("Found all shapes")
							.setMessage("Congratulations!! You have found all the " + text + "!!")
							.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									//gridview.setAdapter(new ImageAdapter(getApplicationContext(), grid));
									recreate();
								}
							})
							.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									/*finish();
									System.exit(0);*/
								}
							})
							.show();
//							Toast.makeText(MainActivity.this, "You won the game", Toast.LENGTH_SHORT).show();
						}
					} else {
//						Toast.makeText(MainActivity.this, "You lost the game", Toast.LENGTH_SHORT).show();
						new AlertDialog.Builder(MainActivity.this)
						.setTitle("LOST!")
						.setMessage("You lost the game!")
						.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//gridview.setAdapter(new ImageAdapter(getApplicationContext(), grid));
								recreate();
							}
						})
						/*.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								finish();
								System.exit(0);
								
							}
						})*/
						.show();
					}
				} catch (Exception ex) {
					Toast.makeText(MainActivity.this, "" + ex.getMessage() + "Id : " + id, Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		Button b1 = (Button) findViewById(R.id.reset);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//gridview.setAdapter(new ImageAdapter(getApplicationContext(), grid));
				recreate();
				
			}
		});
		
		Button b2 = (Button) findViewById(R.id.exit);
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				System.exit(0);
				
			}
		});
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}
}
