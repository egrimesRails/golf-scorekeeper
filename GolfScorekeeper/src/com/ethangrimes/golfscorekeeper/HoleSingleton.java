/**
 * 
 */
package com.ethangrimes.golfscorekeeper;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

/**
 * @author Ethan Grimes
 * This singleton finds existing or creates 
 * an instance of itself to exist application wide.
 * 
 */
public class HoleSingleton {
	
	private ArrayList<Hole> mHoles;
	
	//static variable
	private static HoleSingleton sHoleSingleton;
	private Context mAppContext;
	
	
	//constructor for instance
	private HoleSingleton(Context appContext) {
		mAppContext = appContext;
		mHoles = new ArrayList<Hole>();
		
		//create 18 holes and set hole number
		for (int i = 1; i < 19; i++) {
			Hole c = new Hole();
			c.setHoleNumber(Integer.toString(i));
			mHoles.add(c);
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
}
