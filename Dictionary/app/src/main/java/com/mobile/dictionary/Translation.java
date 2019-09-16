package com.mobile.dictionary;

public class Translation {
	private String lexicalClass = null;
	private Meaning meaning;
	
	public Translation(String lexicalClass, Meaning meaning){
		this.lexicalClass = lexicalClass;
		this.meaning = meaning;
	}
	
	public Translation(Meaning meaning){
		this.meaning = meaning;
	}
	
	public String getLexicalClass(){
		return lexicalClass;
	}
	
	public Meaning getMeaning(){
		return meaning;
	}

}
