package com.ethangrimes.golfscorekeeper;

import android.app.Fragment;





public class HoleActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		return new HoleFragment();
	}
	
	
}
