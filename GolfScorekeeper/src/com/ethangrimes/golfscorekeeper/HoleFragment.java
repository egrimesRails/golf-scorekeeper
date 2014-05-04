package com.ethangrimes.golfscorekeeper;

import java.util.UUID;

import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**Controller which interacts with model
 * and views to allow score entry
 * */
public class HoleFragment extends Fragment  {
	private static final String TAG = "HoleFragment";
	public static final String EXTRA_HOLE_ID = "com.ethangrimes.golfscorekeeper.hole_id";
	
	private Hole mHole;
	private TextView mHoleNumber;
	private TextView mTotals;
	private TextView mPuttsTotals;
	private Button mScoreUp;
	private Button mScoreDown;
	private Button mPuttsUp;
	private Button mPuttsDown;
	private TextView mScore;
	private TextView mPutts;
	private int mScoreNumber;
	private int mPuttsNumber;
	private Typeface font;
	private TextView tvScoreLabel;
	private TextView tvPuttsLabel;
	private Callbacks mCallbacks;
	
	/**Required for hosting activities*/
	public interface Callbacks {
		
		void onHoleUpdated(Hole hole);
	}
	
	
	
	/**
	 * fragment onCreate makes a new hole fragment.
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
		View v = inflater.inflate(R.layout.new_fragment_hole, parent, false);
		
		//calculate any totals that may exist on startup
		HoleSingleton.get(getActivity()).calculateTotals();
		
		//wire the icon as an up button if supported
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			if(NavUtils.getParentActivityIntent(getActivity()) != null){
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		
		
//------BEGIN WIRING OF HOLEFRAGMENT WIDGETS---------------------------------------------
		
		//set scoring variable to a default value
		mScoreNumber = 3;
		mPuttsNumber = 1;
		
		//Wire TextView for hole number
		mHoleNumber = (TextView)v.findViewById(R.id.holeNumber);
		font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/bernhc.ttf");
		mHoleNumber.setTypeface(font);
		mHoleNumber.setText(mHole.getHoleNumber());
		
		//change color if hole has been played
		if(mHole.getScore() > 0) {
			
			mHoleNumber.setTextColor(Color.GREEN);
		} else {
			mHoleNumber.setTextColor(Color.WHITE);
		}
		
		//setting typeface for labels
		tvScoreLabel = (TextView)v.findViewById(R.id.scoreLabel);
		tvScoreLabel.setTypeface(font);
		tvPuttsLabel = (TextView)v.findViewById(R.id.puttsLabel);
		tvPuttsLabel.setTypeface(font);
		
		
		//Wire score TextView and set to "-" if score has not been entered
		mScore = (TextView)v.findViewById(R.id.score);
		if(mHole.getScore() == 0){
			mScore.setText("-");
		}else{
			mScore.setText(Integer.toString(mHole.getScore()));
		}
		
		//Wire scoring up button and change TextView on click
		mScoreUp = (Button)v.findViewById(R.id.scoreUp);
		
		mScoreUp.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				if(mScoreNumber <= 11) {
					mScoreNumber++;
					mHole.setScore(mScoreNumber);
					mScore.setText(Integer.toString(mHole.getScore()));
				}else if(mHole.getScore() == 0){
					mScoreNumber = 3;
					mHole.setScore(mScoreNumber);
					mScore.setText(Integer.toString(mHole.getScore()));
				} else {
					mScoreNumber = -1;
					mScoreNumber++;
					mHole.setScore(mScoreNumber);
					mScore.setText("-");
				}
				
				
				
				HoleSingleton.get(getActivity()).calculateTotals();
				mCallbacks.onHoleUpdated(mHole);
				mTotals.setText(Integer.toString(mHole.getTotalScore()));
				mPuttsTotals.setText(Integer.toString(mHole.getTotalPutts()));
				
				//if score is entered it can't be 0, change the text color
				if(mHole.getScore() != 0) {
					mHoleNumber.setTextColor(Color.GREEN);
				} else if (mHole.getScore() == 0){
					mHoleNumber.setTextColor(Color.WHITE);
				}
			}
		});
		
		//Wire scoring down button and change TextView onClick
		mScoreDown = (Button)v.findViewById(R.id.scoreDown);
		
		mScoreDown.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(mHole.getScore() > 1) {
					mScoreNumber = mHole.getScore();
					mScoreNumber--;
					mHole.setScore(mScoreNumber);
					mScore.setText(Integer.toString(mHole.getScore()));
				}else if(mHole.getScore() == 1) {
					mScoreNumber = 0;
					mHole.setScore(mScoreNumber);
					mScore.setText("-");
				} else {
					mScoreNumber = 5;
					mScoreNumber--;
					mHole.setScore(mScoreNumber);
					mScore.setText(Integer.toString(mHole.getScore()));
				}
				HoleSingleton.get(getActivity()).calculateTotals();
				mCallbacks.onHoleUpdated(mHole);
				mTotals.setText(Integer.toString(mHole.getTotalScore()));
				mPuttsTotals.setText(Integer.toString(mHole.getTotalPutts()));
				
				//if score is entered it can't be 0, change the text color
				if(mHole.getScore() != 0) {
					mHoleNumber.setTextColor(Color.GREEN);
				} else if (mHole.getScore() == 0){
					mHoleNumber.setTextColor(Color.WHITE);
				}
			}
		});
		
		//Wire putt score up button and putts textView
		mPuttsUp = (Button)v.findViewById(R.id.puttsUp);
		mPutts = (TextView)v.findViewById(R.id.putts);
		if(mHole.getPutts() == 0){
			mPutts.setText("-");
		}else{
			mPutts.setText(Integer.toString(mHole.getPutts()));
		}
		mPuttsUp.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				if(mHole.getPutts() <= 6) {
					mPuttsNumber++;
					mHole.setPutts(mPuttsNumber);
					mPutts.setText(Integer.toString(mHole.getPutts()));
					
				}else if(mHole.getPutts() == 0){
					mPuttsNumber = 1;
					mHole.setPutts(mPuttsNumber);
					mPutts.setText(Integer.toString(mHole.getPutts()));
				
				}else{
					mPuttsNumber = -1;
					mPuttsNumber++;
					mHole.setPutts(mPuttsNumber);
					mPutts.setText("-");
				}
				HoleSingleton.get(getActivity()).calculateTotals();
				mCallbacks.onHoleUpdated(mHole);
				mTotals.setText(Integer.toString(mHole.getTotalScore()));
				mPuttsTotals.setText(Integer.toString(mHole.getTotalPutts()));
				
				//if score is entered it can't be 0, change the text color
				if(mHole.getScore() != 0) {
					mHoleNumber.setTextColor(Color.GREEN);
				} else if (mHole.getScore() == 0){
					mHoleNumber.setTextColor(Color.WHITE);
				}
			}
		});
		
		//wire putts score down button
		mPuttsDown = (Button)v.findViewById(R.id.puttsDown);
		mPuttsDown.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				if(mHole.getPutts() > 1){
					mPuttsNumber = mHole.getPutts();
					mPuttsNumber--;
					mHole.setPutts(mPuttsNumber);
					mPutts.setText(Integer.toString(mHole.getPutts()));
					
				}else if(mHole.getPutts() == 1) {
					mPuttsNumber = 0;
					mHole.setPutts(mPuttsNumber);
					mPutts.setText("-");
				}else {
					mPuttsNumber = 3;
					mPuttsNumber--;
					mHole.setPutts(mPuttsNumber);
					mPutts.setText(Integer.toString(mHole.getPutts()));
				}
				HoleSingleton.get(getActivity()).calculateTotals();
				mCallbacks.onHoleUpdated(mHole);
				mTotals.setText(Integer.toString(mHole.getTotalScore()));
				mPuttsTotals.setText(Integer.toString(mHole.getTotalPutts()));
				
				//if score is entered it can't be 0, change the text color
				if(mHole.getScore() != 0) {
					mHoleNumber.setTextColor(Color.GREEN);
				} else if (mHole.getScore() == 0){
					mHoleNumber.setTextColor(Color.WHITE);
				}
				
			}
			
			
		});
		
		
		//wire total putts 
		mPuttsTotals = (TextView)v.findViewById(R.id.tvPuttsTotals);
		
		//wire total score calculation
		mTotals = (TextView)v.findViewById(R.id.totals);
		HoleSingleton.get(getActivity()).calculateTotals();
		mTotals.setText(Integer.toString(mHole.getTotalScore()));
		mPuttsTotals.setText(Integer.toString(mHole.getTotalPutts()));
//--------------END OF WIRING WIDGETS--------------------------------------------------------------------		
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
		HoleSingleton.get(getActivity()).calculateTotals();
		mTotals.setText(Integer.toString(mHole.getTotalScore()));
		mPuttsTotals.setText(Integer.toString(mHole.getTotalPutts()));
	}
	
	


	/** 
	 *Makes icon return to parent activity.
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


	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		
		super.onResume();
		HoleSingleton.get(getActivity()).calculateTotals();
		mTotals.setText(Integer.toString(mHole.getTotalScore()));
		mPuttsTotals.setText(Integer.toString(mHole.getTotalPutts()));
	}
	
	


	/** get access to hosting activity when fragments attached
	 * 
	 */
	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
		mCallbacks = (Callbacks)activity;
	}


	/**
	 * reset callbacks to null when fragment is detached
	 */
	@Override
	public void onDetach() {
		
		super.onDetach();
		mCallbacks = null;
		
	}
	
}


	


	
	
	
	
	
	
	


