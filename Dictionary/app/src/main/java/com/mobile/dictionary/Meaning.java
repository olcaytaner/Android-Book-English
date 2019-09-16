package com.mobile.dictionary;

public class Meaning {
	private String lexicalClass = null;
	private String meaning;
	
	public Meaning(String lexicalClass, String meaning){
		this.lexicalClass = lexicalClass;
		this.meaning = meaning;
	}
	
	public Meaning(String meaning){
		this.meaning = meaning;
	}
	
	public String getLexicalClass(){
		return lexicalClass;
	}
	
	public String getMeaning(){
		return meaning;
	}

}
