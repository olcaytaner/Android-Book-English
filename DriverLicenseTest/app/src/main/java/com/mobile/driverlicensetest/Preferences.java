package com.mobile.driverlicensetest;

import java.io.Serializable;

public class Preferences implements Serializable{
	private boolean traffic;
	private boolean engine;
	private boolean firstAid;
	private int trafficNumberOfQuestions;
	private int engineNumberOfQuestions;
	private int firstAidNumberOfQuestions;
	
	public Preferences(boolean traffic, boolean engine, boolean firstAid, int trafficNumberOfQuestions, int engineNumberOfQuestions, int firstAidNumberOfQuestions){
		this.traffic = traffic;
		this.engine = engine;
		this.firstAid = firstAid;
		this.trafficNumberOfQuestions = trafficNumberOfQuestions;
		this.engineNumberOfQuestions = engineNumberOfQuestions;
		this.firstAidNumberOfQuestions = firstAidNumberOfQuestions;
	}
	
	public boolean traffic(){
		return traffic;
	}
	
	public boolean engine(){
		return engine;
	}
	
	public boolean firstAid(){
		return firstAid;
	}
	
	public int getTrafficNumberOfQuestions(){
		return trafficNumberOfQuestions;
	}
	
	public int getEngineNumberOfQuestions(){
		return engineNumberOfQuestions;
	}
	
	public int getFirstAidNumberOfQuestions(){
		return firstAidNumberOfQuestions;
	}
	
}
