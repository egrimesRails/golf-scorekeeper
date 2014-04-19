/**
 * 
 */
package com.ethangrimes.golfscorekeeper;

import java.util.ArrayList;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Ethan
 * Fragment hold list of holes.
 */
public class HoleListFragment extends ListFragment {

	private ArrayList<Hole> mHoles;
	
	/** 
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//set the title in the action bar
		getActivity().setTitle(R.string.holes_list_title);
		
		mHoles = HoleSingleton.get(getActivity()).getHoles();
		
		HoleAdapter adapter = new HoleAdapter(mHoles);
		
		setListAdapter(adapter);
	}

	/**Logic when a list item is selected. 
	*/
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		//Get the hole from the adapter
		Hole c = ((HoleAdapter)getListAdapter()).getItem(position);
		
		//Start HolePagerActivity on a list item click, adding the hole id to intent
		Intent i = new Intent(getActivity(), HolePagerActivity.class);
		
		i.putExtra(HoleFragment.EXTRA_HOLE_ID, c.getId());
		
		startActivity(i);
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
			holeNumberTextView.setText("Hole# " + c.getHoleNumber());
			
			//set score text and get score
			TextView scoreTextView = (TextView) convertView.findViewById(R.id.list_score);
			scoreTextView.setText("Score:" + c.getScore());
			
			//set putts score and get putts
			TextView puttsTextView = (TextView) convertView.findViewById(R.id.list_putts);
			puttsTextView.setText("Putts:" + c.getPutts());
			
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
	
	
	
}
