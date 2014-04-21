package com.ethangrimes.golfscorekeeper;

import java.util.UUID;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;


/**Controller which interacts with model
 * and views to allow score entry
 * */
public class HoleFragment extends Fragment {
	
	public static final String EXTRA_HOLE_ID = "com.ethangrimes.golfscorekeeper.hole_id";
	
	
	
	private Hole mHole;
	private NumberPicker mNpScore;
	private NumberPicker mNpPutts;
	private TextView mHoleNumber;
	
	
	

	
	/**
	 * fragment onCreate makes a new hole instance.
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		UUID holeID = (UUID)getArguments().getSerializable(EXTRA_HOLE_ID);
		
		mHole = HoleSingleton.get(getActivity()).getHole(holeID);
		
		setHasOptionsMenu(true);
	}


	/**
	 * onCreateView inflates fragment layout
	 * */
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		
		//inflate the proper view
		View v = inflater.inflate(R.layout.fragment_hole, parent, false);
		
		
		//wire the icon as an up button if supported
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			if(NavUtils.getParentActivityIntent(getActivity()) != null){
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		//Wire TextView for hole number
		mHoleNumber = (TextView)v.findViewById(R.id.holeNumber);
		mHoleNumber.setText(mHole.getHoleNumber());
		
		//Wire score NumberPicker
		mNpScore = (NumberPicker)v.findViewById(R.id.scorePicker1);
		
		mNpScore.setMinValue(1);
		mNpScore.setMaxValue(12);
		mNpScore.setValue(4);
		
		//listener does actions when score value changes
		mNpScore.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				//Toast.makeText(getActivity(), "Score is: " + Integer.toString(newVal), Toast.LENGTH_SHORT).show();
				mHole.setScore(mNpScore.getValue());
			}
		});
		
		
		
		//Wire putts NumberPicker
		mNpPutts = (NumberPicker)v.findViewById(R.id.puttsPicker);
		mNpPutts.setMinValue(0);
		mNpPutts.setMaxValue(8);
		mNpPutts.setValue(2);
		
		
		//listener does actions when score value changes
				mNpPutts.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
					
					@Override
					public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
						//Toast.makeText(getActivity(), "Putts: " + Integer.toString(newVal), Toast.LENGTH_SHORT).show();
						 mHole.setPutts(mNpPutts.getValue());
					}
				});
		
		
		
		return v;
	}
	
	
	/**Created new instance method to pass id through fragments*/
	public static HoleFragment newInstance(UUID holeId) {
		
		//create new bundle
		Bundle args = new Bundle();
		
		//add serializable data to the bundle
		args.putSerializable(EXTRA_HOLE_ID, holeId);
		
		//create a fragment instance
		HoleFragment fragment = new HoleFragment();
		
		//attach arguments to the fragment
		fragment.setArguments(args);
		
		return fragment;
	}


	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		super.onPause();
		HoleSingleton.get(getActivity()).saveHoles();
	}


	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()){
		
		case android.R.id.home:
			
			if(NavUtils.getParentActivityIntent(getActivity()) != null){
				
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			
			return true;
			
		default: return super.onOptionsItemSelected(item);	
		}
		
	}
	
	
	
	

}
