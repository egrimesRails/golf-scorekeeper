package com.ethangrimes.golfscorekeeper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;





public class HoleActivity extends Activity {
	
	/**Called when activity is first created*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hole);
		
		//Get the activities fragment manager
		FragmentManager fm = getFragmentManager();
		
		//create fragment variable, let fragment manager find fragment view.
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		/*.add creates and commits a fragment transaction, beginTransaction creates and
		 * returns an instance of fragment transaction.  container view ID specifies where in
		 * the activity the fragment view should appear, also serves as a unique identifier for fragment manager.
		*/
		if(fragment == null) {
			fragment = new HoleFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
	}
}
