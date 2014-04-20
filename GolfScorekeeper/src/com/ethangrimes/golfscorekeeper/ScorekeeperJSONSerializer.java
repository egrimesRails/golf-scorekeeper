package com.ethangrimes.golfscorekeeper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

/**
 * @author Ethan
 * class handles serializing JSON to file
 *
 */
public class ScorekeeperJSONSerializer {
	
	private Context mContext;
	private String mFileName;
	
	public ScorekeeperJSONSerializer(Context c, String f) {
		mContext = c;
		mFileName = f;
		
	}
	
	/**reads holes on file, returns an ArrayList<Hole> to use in application*/
	public ArrayList<Hole> loadHoles() throws IOException, JSONException {
		
		ArrayList<Hole> holes = new ArrayList<Hole>();
		BufferedReader reader = null;
		try{
			//open and read file into stringBuilder
			InputStream in = mContext.openFileInput(mFileName);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			
			while((line = reader.readLine()) != null) {
				
				jsonString.append(line);
			}
			
			//parse JSON using tokener
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
			
			//build array of holes
			for (int i = 0;i < array.length();i++){
				
				holes.add(new Hole(array.getJSONObject(i)));
			}
		} catch (FileNotFoundException e) {
			
		} finally {
			
			if(reader != null)
				reader.close();
		}		
		return holes;
	}
	/**Writes hole data to file*/
	public void saveHoles(ArrayList<Hole> holes) throws JSONException, IOException {
		
		//building array in JSON
		JSONArray array = new JSONArray();
		
		for(Hole c : holes){
			
			array.put(c.toJSON());
			
			Writer writer = null;
			
			try {
				//open a file and write to it
				OutputStream out = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
				writer = new OutputStreamWriter(out);
				writer.write(array.toString());
			
			} finally {
				if(writer != null)
					writer.close();
			}	
		}
	}
}
