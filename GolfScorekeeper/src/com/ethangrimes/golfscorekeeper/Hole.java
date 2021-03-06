package com.ethangrimes.golfscorekeeper;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Hole {
	
	//constants for json keys
	private static final String JSON_ID = "id";
	private static final String JSON_SCORE = "score";
	private static final String JSON_PUTTS = "putts";
	private static final String JSON_HOLE = "holenumber";
	
	
	//member variables for unique holes
	private UUID mHoleId;
	private int mScore;
	private int mPutts;
	private String mHoleNumber;
	private int mTotalScore;
	private int mTotalPutts;
	
	
	/**Constructor for saved hole data*/
	public Hole(JSONObject json) throws JSONException {
		
		mHoleId = UUID.fromString(json.getString(JSON_ID));
		mScore = json.getInt(JSON_SCORE);
		mPutts = json.getInt(JSON_PUTTS);
		mHoleNumber = json.getString(JSON_HOLE);
		
	}
	/**
	 * Constructor for a hole
	 */
	public Hole() {
		//generate unique id
		mHoleId = UUID.randomUUID();
	}
	
	/**setter for total putts*/
	public void setTotalPutts(int totalPutts) {
		mTotalPutts = totalPutts;
	}
	
	/**getter for total putts*/
	public int getTotalPutts() {
		return mTotalPutts;
	}
	
	/**method allows setting of a total score*/
	public void setTotalScore(int totalScore) {
		
		mTotalScore = totalScore;
	}
	
	/**method calculates the total score by looping through array
	 * of holes*/
	public int getTotalScore() {
		
		return mTotalScore;
	}
	
	/**
	 * @return the score
	 */
	public int getScore() {
		return mScore;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		mScore = score;
	}

	/**
	 * @return the putts
	 */
	public int getPutts() {
		return mPutts;
	}

	/**
	 * @param putts the putts to set
	 */
	public void setPutts(int putts) {
		mPutts = putts;
	}

	/**
	 * @return the holeNumber
	 */
	public String getHoleNumber() {
		return mHoleNumber;
	}

	/**
	 * @param holeNumber the holeNumber to set
	 */
	public void setHoleNumber(String holeNumber) {
		mHoleNumber = holeNumber;
	}

	/**
	 * @return the holeId
	 */
	public UUID getId() {
		return mHoleId;
	}

	

	public JSONObject toJSON() throws JSONException {
		
		JSONObject json = new JSONObject();
		
		json.put(JSON_ID, mHoleId.toString());
		json.put(JSON_SCORE, String.valueOf(mScore));
		json.put(JSON_PUTTS, String.valueOf(mPutts));
		json.put(JSON_HOLE, mHoleNumber);
		
		return json;
	}
	@Override
	public String toString() {
		return mHoleNumber;
	}
	
	
}
