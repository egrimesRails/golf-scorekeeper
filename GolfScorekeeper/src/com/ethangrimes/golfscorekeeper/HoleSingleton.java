/**
 * 
 */
package com.ethangrimes.golfscorekeeper;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

/**
 * @author Ethan Grimes
 * This singleton finds existing or creates 
 * an instance of itself to exist application wide.
 * 
 */
public class HoleSingleton {
	
	private static final String TAG = "HoleSingleton"; 
	private static final String FILENAME = "holes.json";
	private static HoleSingleton sHoleSingleton;
	
	private Context mAppContext;
	private ArrayList<Hole> mHoles;
	private ScorekeeperJSONSerializer mSerializer;
	
	//constructor for instance
	private HoleSingleton(Context appContext) {
		mAppContext = appContext;
		mHoles = new ArrayList<Hole>();
		mSerializer = new ScorekeeperJSONSerializer(mAppContext, FILENAME);
		
		try {
			mHoles = mSerializer.loadHoles();
		} catch (Exception e){
			
			//create 18 holes if data is not loaded by file
			for (int i = 1; i < 19; i++) {
				Hole c = new Hole();
				c.setHoleNumber(Integer.toString(i));
				mHoles.add(c);
				Log.e(TAG, "Error loading holes", e);
			}
			
		}
		
		
	}
	
	public boolean saveHoles() {
		try {
			mSerializer.saveHoles(mHoles);
			Log.d(TAG, "holes saved to file");
			return true;
		} catch(Exception e) {
			Log.e(TAG, "Error Saving File", e);
			return false;
		}
		
	}
	
	public static HoleSingleton get(Context c) {
		
		if(sHoleSingleton == null) {
			sHoleSingleton = new HoleSingleton(c.getApplicationContext());
		}
		return sHoleSingleton;
	}
	
	public ArrayList<Hole> getHoles() {
		return mHoles;
	}
	
	public Hole getHole(UUID id){
		
		for (Hole c : mHoles){
			if (c.getId().equals(id))
				return c;
		}
		return null;
	}
	/**calculate total score and putts entered in so far*/
	public void calculateTotals() {
		int totalScore = 0;
		int totalPutts = 0;
		for(int i = 0;i < mHoles.size();i++) {
		
			totalScore += mHoles.get(i).getScore();
			totalPutts += mHoles.get(i).getPutts();
			
			
		}
		for(int i = 0;i < mHoles.size();i++){
		
		mHoles.get(i).setTotalPutts(totalPutts);
		mHoles.get(i).setTotalScore(totalScore);
		
		}
		
		Log.d(TAG, "calculateTotals called");
	}
	
}
	
	

