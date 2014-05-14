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
import android.support.v4.view.ViewPager.OnPageChangeListener;
/**
 * @author Ethan
 *
 */
public class HolePagerActivity extends FragmentActivity implements HoleFragment.Callbacks {
	
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
		
		//set adapter for view pager
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

			/* (non-Javadoc)
			 * @see android.support.v4.view.PagerAdapter#getItemPosition(java.lang.Object)
			 */
			@Override
			public int getItemPosition(Object object) {
				
					return POSITION_NONE;
				}
		});
		
		
		
		/**Listener for page state changes*/
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}

			@Override
			public void onPageSelected(int pos) {
				
				mViewPager.getAdapter().notifyDataSetChanged();
			}
		});
		
		//set up list view so proper hole is brought up on touch
		UUID holeId = (UUID)getIntent().getSerializableExtra(HoleFragment.EXTRA_HOLE_ID);
		
		for(int i = 0; i < mHoles.size(); i++){
			
			if(mHoles.get(i).getId().equals(holeId)){
				
				mViewPager.setCurrentItem(i);
				
				break;
			}
		}
	}//END OF onCREATE
	
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
	}

	@Override
	public void onHoleUpdated(Hole hole) {
		// TODO Auto-generated method stub
		
	}

	

}
