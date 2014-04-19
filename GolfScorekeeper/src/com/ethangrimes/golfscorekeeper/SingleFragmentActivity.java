/**
 * 
 */
package com.ethangrimes.golfscorekeeper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * @author Ethan
 *
 */
public abstract class SingleFragmentActivity extends FragmentActivity {
	
	protected abstract android.support.v4.app.Fragment createFragment();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		
		//Get the activities fragment manager
		FragmentManager fm = getSupportFragmentManager();
				
		//create fragment variable, let fragment manager find fragment view.
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
				
		/*.add creates and commits a fragment transaction, beginTransaction creates and
		* returns an instance of fragment transaction.  container view ID specifies where in
		* the activity the fragment view should appear, also serves as a unique identifier for fragment manager.
		*/
		if(fragment == null) {
			fragment = createFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
	}
}
