package com.ethangrimes.golfscorekeeper;

import android.app.Fragment;

public class HoleListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		return new HoleListFragment();
	}

}
