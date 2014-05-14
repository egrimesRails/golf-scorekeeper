/**
 * 
 */
package com.ethangrimes.golfscorekeeper;

import java.util.ArrayList;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Ethan
 * Fragment to hold list of holes.
 */
public class HoleListFragment extends ListFragment {

	private ArrayList<Hole> mHoles;
	private Callbacks mCallbacks;
	private Typeface font;
	
	//interface required for hosting activities
	public interface Callbacks {
		
		void onHoleSelected(Hole hole);
	}
	
	/** 
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//set the title in the action bar
		getActivity().setTitle(R.string.holes_list_title);
		
		//let fragment know menu options exist
		setHasOptionsMenu(true);
		
		mHoles = HoleSingleton.get(getActivity()).getHoles();
		
		HoleAdapter adapter = new HoleAdapter(mHoles);
		
		setListAdapter(adapter);
		
		setRetainInstance(true);
		
		
	}

	/**Logic when a list item is selected. 
	*/
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		//Get the hole from the adapter
		Hole c = ((HoleAdapter)getListAdapter()).getItem(position);
		
		mCallbacks.onHoleSelected(c);
	}
	
	
	/**INNER CLASS-Creates custom adapter to handle hole data.*/
	private class HoleAdapter extends ArrayAdapter<Hole> {
        
		//constructor
		public HoleAdapter(ArrayList<Hole> holes) {
			super(getActivity(), 0, holes);
			
		}
		
		/**Returns a custom layout populated with the hole data.*/
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			
			
			//inflate the custom layout for listview if one does not exist
			if(convertView == null){
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_holes, null);
				
			}
			
			//Configure the view for one hole list item
			Hole c = getItem(position);
			
			//set hole text and get variable
			TextView holeNumberTextView = (TextView) convertView.findViewById(R.id.list_hole);
			font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/bernhc.ttf");
			holeNumberTextView.setTypeface(font);
			
			//change hole number to green if hole has been played
			if(c.getScore() > 0) {
				holeNumberTextView.setTextColor(Color.GREEN);
				holeNumberTextView.setText(c.getHoleNumber());
			} else {
				holeNumberTextView.setTextColor(Color.WHITE);
				holeNumberTextView.setText(c.getHoleNumber());
			}
			
			//set score text and get score
			//TextView scoreTextView = (TextView) convertView.findViewById(R.id.list_score);
			//scoreTextView.setText("Score:" + c.getScore());
			//scoreTextView.setTypeface(font);
			//scoreTextView.setTextColor(Color.WHITE);
			
			//set putts score and get putts
			//TextView puttsTextView = (TextView) convertView.findViewById(R.id.list_putts);
			//puttsTextView.setText("Putts:" + c.getPutts());
			//puttsTextView.setTypeface(font);
			//puttsTextView.setTextColor(Color.WHITE);
			
			return convertView;
		}
	}


	/** updates the data changed in holeFragment.
	 * 
	 */
	@Override
	public void onResume() {
		
		super.onResume();
		
		//refresh the data set in HoleListFragment
		((HoleAdapter)getListAdapter()).notifyDataSetChanged();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu, android.view.MenuInflater)
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_hole_list, menu);
	}

	/**switch for selections of different menu items 
	 *
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
		switch(item.getItemId()) {
		
		//if selected create dialog and respond to click events
		case R.id.menu_item_new_hole:
			
			AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
			alert.setMessage("Reset Round?");
			alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					
				}
			});
			alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					for(int i = 0;i < mHoles.size(); i++) {
						Hole c = mHoles.get(i);
						c.setScore(0);
						c.setPutts(0);
						c.setTotalScore(0);
						c.setTotalPutts(0);
						
					}
					//update the data
					((HoleAdapter)getListAdapter()).notifyDataSetChanged();
					
				}
			});
			
			alert.show();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

	/**host the activity when fragment attaches to it
	 * 
	 */
	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		mCallbacks = (Callbacks)activity;
	}

	/** 
	 *set callbacks to null when fragment is detached 
	 */
	@Override
	public void onDetach() {
		
		super.onDetach();
		mCallbacks = null;
	}
	
	/**Reload HoleListFragments list*/
	public void updateUI() {
		
		((HoleAdapter)getListAdapter()).notifyDataSetChanged();
		
	}

	/**Forces first hole to display onStart()
	 * 
	 */
	@Override
	public void onStart() {
		
		super.onStart();
		
		Hole c = ((HoleAdapter)getListAdapter()).getItem(0);
		
		mCallbacks.onHoleSelected(c);
	}

	

	
	
	
	
	
	
	
	
}
