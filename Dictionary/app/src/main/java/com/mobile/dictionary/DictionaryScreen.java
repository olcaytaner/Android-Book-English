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

public class DictionaryScreen extends ListFragment {
	private Dictionary dictionary = null;
	private EditText wordBox;
	private Activity screen;
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        screen = this.getActivity();
        View dictionaryScreen = inflater.inflate(R.layout.dictionary_screen, container, false);
        wordBox = (EditText) dictionaryScreen.findViewById(R.id.searchWord);
        wordBox.addTextChangedListener(wordWatcher);
        return dictionaryScreen;
    }
    
    public void setDictionary(Dictionary dictionary){
    	this.dictionary = dictionary;
    }
        
	private TextWatcher wordWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (dictionary != null){
				ArrayList<DictionaryWord> words = dictionary.searchWord(s.toString());
				ArrayList<HashMap<String, String>> wordList = new ArrayList<HashMap<String, String>>();
				for (DictionaryWord word:words){
					String meaningInfo;
					if (word.getMeaningClass() != null){
						meaningInfo = word.getMeaningClass() + ". ";
					} else {
						meaningInfo = "";
					}
					if (word.getOrigin() != null){
						meaningInfo = meaningInfo + word.getOrigin() + ". ";
					}
					for (int i = 0; i < word.numberOfMeanings(); i++){
						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("meaning", meaningInfo + word.meaning(i).getMeaning());
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
