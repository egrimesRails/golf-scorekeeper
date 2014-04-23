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
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author Ethan
 *
 */
public class HolePagerActivity extends FragmentActivity {
	
	private ViewPager mViewPager;
	private ArrayList<Hole> mHoles;
	
	private String mTotals;
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
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				
				
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
				
			}

			@Override
			public void onPageSelected(int pos) {
			calculateTotals();	
				
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
	
}
