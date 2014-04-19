package com.ethangrimes.golfscorekeeper;

import java.util.UUID;

import android.support.v4.app.Fragment;





public class HoleActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		//get a holeId variable to pass to new instance method
		UUID holeId = (UUID)getIntent().getSerializableExtra(HoleFragment.EXTRA_HOLE_ID);
		
		//return a fragment with its id
		return HoleFragment.newInstance(holeId);
	}
	
	
}
