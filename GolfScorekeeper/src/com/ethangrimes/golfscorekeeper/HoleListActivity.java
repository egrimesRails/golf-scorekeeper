package com.ethangrimes.golfscorekeeper;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class HoleListActivity extends SingleFragmentActivity 
		implements HoleListFragment.Callbacks, HoleFragment.Callbacks {

	@Override
	protected Fragment createFragment() {
		
		return new HoleListFragment();
	}
	
	@Override
	protected int getLayoutResId(){
		
		return R.layout.activity_masterdetail;
	}
	
	/**implemented to choose actions on tablet vs. phone*/
	@Override
	public void onHoleSelected(Hole hole) {
		
		if(findViewById(R.id.detailFragmentContainer) == null) {
			
			//start an instance of HolePagerActivity
			Intent i = new Intent(this, HolePagerActivity.class);
			i.putExtra(HoleFragment.EXTRA_HOLE_ID, hole.getId());
			startActivity(i);
		} else {
			
			FragmentManager fm = getSupportFragmentManager();
			
			FragmentTransaction ft = fm.beginTransaction();
			
			Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
			
			Fragment newDetail = HoleFragment.newInstance(hole.getId());
			
			if(oldDetail != null) {
				
				ft.remove(oldDetail);
			}
			
			ft.add(R.id.detailFragmentContainer, newDetail);
			ft.commit();
			
		}
		
	}

	@Override
	public void onHoleUpdated(Hole hole) {
		
		FragmentManager fm = getSupportFragmentManager();
		HoleListFragment listFragment = (HoleListFragment)fm.findFragmentById(R.id.fragmentContainer);
		listFragment.updateUI();
		
	}

}
