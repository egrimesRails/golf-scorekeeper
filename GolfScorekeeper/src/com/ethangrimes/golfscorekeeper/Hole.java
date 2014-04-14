package com.ethangrimes.golfscorekeeper;

import java.util.Date;
import java.util.UUID;

public class Hole {
	
	//member variables for unique holes
	private UUID mHoleId;
	private int mScore;
	private int mPutts;
	private int mHoleNumber;
	private Date mDate;
	
	//textView labels
	
	
	/**
	 * Constructor for a hole
	 */
	public Hole() {
		//generate unique id
		mHoleId = UUID.randomUUID();
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
	public int getHoleNumber() {
		return mHoleNumber;
	}

	/**
	 * @param holeNumber the holeNumber to set
	 */
	public void setHoleNumber(int holeNumber) {
		mHoleNumber = holeNumber;
	}

	/**
	 * @return the holeId
	 */
	public UUID getHoleId() {
		return mHoleId;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return mDate;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		mDate = date;
	}
}
