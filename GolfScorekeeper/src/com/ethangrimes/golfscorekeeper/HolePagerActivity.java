/**
 * 
 */
package com.ethangrimes.golfscorekeeper;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
/**
 * @author Ethan
 *
 */
public class HolePagerActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private ArrayList<Hole> mHoles;

	/** 
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//new instance of ViewPager
		mViewPager = new ViewPager(this);
		
		//set this instance id from ids.xml
		mViewPager.setId(R.id.viewPager);
		
		//set view to this instance of ViewPager
		setContentView(mViewPager);
		
		//get the data set from HoleSingleton
		mHoles = HoleSingleton.get(this).getHoles();
		
		//get activities instance of fragment manager
		FragmentManager fm = getSupportFragmentManager();
		
		//set adapter as an unnamed instance of FragmentStatePagerAdapter
		//FragmentStatePagerAdapter manages conversation with ViewPager
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

			@Override
			public int getCount() {
				
				return mHoles.size();
			}

			@Override
			public Fragment getItem(int pos) {
				Hole hole = mHoles.get(pos);
				return HoleFragment.newInstance(hole.getId());
			}
			
			
		});
		
		//set up listview so proper hole is brought up on touch
		UUID holeId = (UUID)getIntent().getSerializableExtra(HoleFragment.EXTRA_HOLE_ID);
		
		for(int i = 0; i < mHoles.size(); i++){
			
			if(mHoles.get(i).getId().equals(holeId)){
				
				mViewPager.setCurrentItem(i);
				
				break;
			}
		}
		
	}
	
	
	
}
