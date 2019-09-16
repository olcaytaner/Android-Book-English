package com.mobile.dictionary;

import java.util.ArrayList;

public class SourceWord extends Word {
	
	private ArrayList<Translation> translations;
	
	public SourceWord(String literal){
		super(literal);
		translations = new ArrayList<Translation>();
	}
	
	public void addTranslation(Translation translation){
		translations.add(translation);
	}
	
	public int numberOfTranslations(){
		return translations.size();
	}
	
	public Translation translation(int position){
		return translations.get(position);
	}


}
