package com.mobile.dictionary;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Dictionary extends DefaultHandler{
	private ArrayList<DictionaryWord> words;
	private DictionaryWord word;
	private String value = null;
	private String meaningClass = null;
	
	public Dictionary(){
		words = new ArrayList<DictionaryWord>();
	}
	
	public ArrayList<DictionaryWord> searchWord(String wordToBeSearched){
		ArrayList<DictionaryWord> resultList;
		resultList = new ArrayList<DictionaryWord>();
		for (DictionaryWord word: words){
			if (word.getLiteral().equals(wordToBeSearched)){
				resultList.add(word);
			}
		}
		return resultList;
	}
	
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	if (localName.equals("word")){
    		String literal = attributes.getValue("name");
    		word = new DictionaryWord(literal);
    		String lexicalClass = attributes.getValue("lex_class");
    		if (lexicalClass != null){
    			word.setMeaningClass(lexicalClass);
    		}
    		String origin = attributes.getValue("origin");
    		if (origin != null){
    			word.setOrigin(origin);
    		}
    		value = null;
    	} else {
    		if (localName.equals("meaning")){
        		meaningClass = attributes.getValue("class");
        		value = null;
    		}
    	}
    }
 
    public void endElement(String uri, String localName, String qName) throws SAXException {
    	Meaning meaning;
    	if (localName.equals("lexicon")){
    		return;
    	} else {
    		if (localName.equals("word")){
    			words.add(word);
    		} else {
    			if (localName.equals("meaning")){
    				if (meaningClass != null){
    					meaning = new Meaning(meaningClass, value);
    				} else {
    					meaning = new Meaning(value);
    				}
    				word.addMeaning(meaning);
    			}
    		}
    	}
    }
 
    public void characters(char[] ch, int start, int length) throws SAXException {
    	if (value != null){
    		value = value + new String(ch, start, length);
    	} else {
    		value = new String(ch, start, length);
    	}
    }

}
