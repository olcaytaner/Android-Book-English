package com.mobile.dictionary;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TranslationDictionary extends DefaultHandler{
	private ArrayList<SourceWord> words;
	private SourceWord word;
	private String meaningClass;
	private String translationClass;
	private String value;
	
	public TranslationDictionary(){
		words = new ArrayList<SourceWord>();
	}
	
	public ArrayList<SourceWord> searchWord(String wordToBeSearched){
		ArrayList<SourceWord> resultList;
		resultList = new ArrayList<SourceWord>();
		for (SourceWord word: words){
			if (word.getLiteral().equals(wordToBeSearched)){
				resultList.add(word);
			}
		}
		return resultList;
	}

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	if (localName.equals("word")){
    		String literal = attributes.getValue("name");
    		word = new SourceWord(literal);
    		translationClass = null;
    		value = null;
    	} else {
    		if (localName.equals("lexical")){
        		translationClass = attributes.getValue("class");
        		value = null;
    		} else {
        		if (localName.equals("meaning")){
            		meaningClass = attributes.getValue("class");
            		value = null;
        		}    			
    		}
    	}
    }
 
    public void endElement(String uri, String localName, String qName) throws SAXException {
    	Translation translation;
    	Meaning meaning;
    	if (localName.equals("lexicon")){
    		return;
    	} else {
    		if (localName.equals("word")){
    			words.add(word);
    		} else {
    			if (localName.equals("lexical")){
    				translationClass = null;
    			} else {
    				if (localName.equals("meaning")){
    					if (meaningClass != null){
    						meaning = new Meaning(meaningClass, value);
    					} else {
    						meaning = new Meaning(value);
    					}
    					if (translationClass != null){
    						translation = new Translation(translationClass, meaning);
    					} else {
    						translation = new Translation(meaning);
    					}
    					word.addTranslation(translation);
    				}
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
