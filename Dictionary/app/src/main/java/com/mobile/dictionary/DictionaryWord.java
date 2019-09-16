package com.mobile.dictionary;

import java.util.ArrayList;

public class DictionaryWord extends Word {
	private String meaningClass;
	private String origin;
	private ArrayList<Meaning> meanings;
	
	public DictionaryWord(String literal){
		super(literal);
		meanings = new ArrayList<Meaning>();
	}
	
	public void setMeaningClass(String meaningClass){
		this.meaningClass = meaningClass;
	}
	
	public void setOrigin(String origin){
		this.origin = origin;
	}
	
	public String getMeaningClass(){
		return meaningClass;
	}
	
	public String getOrigin(){
		return origin;
	}
		
	public void addMeaning(Meaning meaning){
		meanings.add(meaning);
	}
	
	public int numberOfMeanings(){
		return meanings.size();
	}
	
	public Meaning meaning(int position){
		return meanings.get(position);
	}

}
