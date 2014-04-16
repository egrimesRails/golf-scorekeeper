package com.ethangrimes.golfscorekeeper;

import java.util.UUID;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


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
		
		UUID holeID = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_HOLE_ID);
		
		mHole = HoleSingleton.get(getActivity()).getHole(holeID);
	}


	/**
	 * onCreateView inflates fragment layout
	 * */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		
		//inflate the proper view
		View v = inflater.inflate(R.layout.fragment_hole, parent, false);
		
		
		
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
	
	

}
