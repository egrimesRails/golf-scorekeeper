package com.ethangrimes.golfscorekeeper;

import android.support.v4.app.Fragment;

public class HoleListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		return new HoleListFragment();
	}

}
