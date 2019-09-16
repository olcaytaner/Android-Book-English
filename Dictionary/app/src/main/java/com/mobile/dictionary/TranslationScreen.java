package com.mobile.dictionary;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;

public class TranslationScreen extends ListFragment {
	private TranslationDictionary translationDictionary = null;
	private EditText wordBox;
	private Activity screen;
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        screen = this.getActivity();
        View translationScreen = inflater.inflate(R.layout.translation_screen, container, false);
        wordBox = (EditText) translationScreen.findViewById(R.id.searchTranslation);
        wordBox.addTextChangedListener(wordWatcher);
        return translationScreen;
    }
    
    public void setTranslationDictionary(TranslationDictionary translationDictionary){
    	this.translationDictionary = translationDictionary;
    }
    
	private TextWatcher wordWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (translationDictionary != null){
				ArrayList<SourceWord> words = translationDictionary.searchWord(s.toString());
				ArrayList<HashMap<String, String>> wordList = new ArrayList<HashMap<String, String>>();
				for (SourceWord word:words){
					for (int i = 0; i < word.numberOfTranslations(); i++){
						String meaningInfo;
						if (word.translation(i).getLexicalClass() != null){
							meaningInfo = word.translation(i).getLexicalClass() + ". ";
						} else {
							meaningInfo = "";
						}
						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("meaning", meaningInfo + word.translation(i).getMeaning().getMeaning());
						wordList.add(hashMap);
					}
				}
		        String[] fieldList = new String[] {"meaning"};
		        int[] showList = new int[] {R.id.dictionaryMeaning};
		        SimpleAdapter wordAdapter = new SimpleAdapter(screen, wordList, R.layout.dictionary_cell, fieldList, showList);
		        setListAdapter(wordAdapter);
			}
		} 
		@Override
		public void afterTextChanged(Editable s){			
		} 
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {			
		} 
	}; 	

}
