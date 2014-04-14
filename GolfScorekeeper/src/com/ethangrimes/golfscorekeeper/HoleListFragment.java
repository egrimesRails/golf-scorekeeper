/**
 * 
 */
package com.ethangrimes.golfscorekeeper;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
		
		ArrayAdapter<Hole> adapter = new ArrayAdapter<Hole>(getActivity(), 
				android.R.layout.simple_list_item_1, mHoles);
		
		setListAdapter(adapter);
	}

	/**Logic when a list item is selected. 
	*/
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		Hole c = (Hole)(getListAdapter()).getItem(position);
		Toast.makeText(getActivity(), c.getHoleNumber() + " clicked", Toast.LENGTH_SHORT).show();
	}
	
	
	
	

}
