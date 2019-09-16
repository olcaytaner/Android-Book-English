package com.mobile.dictionary;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.res.Resources.NotFoundException;

public class DictionaryApplication extends Activity implements
		ActionBar.TabListener {
	private DictionaryScreen dictionaryScreen;
	private TranslationScreen translationScreen;
	private Dictionary dictionary = null;
	private TranslationDictionary translationDictionary = null;
		
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		
		actionBar.addTab(actionBar.newTab().setText(R.string.dictionary)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.translation_dictionary)
				.setTabListener(this));
		new LoadDictionaries().execute();
	}
	
    private class LoadDictionaries extends AsyncTask<Void, Void, Boolean>{
		@Override
		protected Boolean doInBackground(Void... params) {
    		try {
    			XMLReader dictionaryParser = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    	        dictionary = new Dictionary();
    	        dictionaryParser.setContentHandler(dictionary);
    	        dictionaryParser.parse(new InputSource(getResources().openRawResource(R.raw.turkish_dictionary)));
    			XMLReader translationDictionaryParser = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    	        translationDictionary = new TranslationDictionary();
    	        translationDictionaryParser.setContentHandler(translationDictionary);
    	        translationDictionaryParser.parse(new InputSource(getResources().openRawResource(R.raw.english_turkish)));
    		} catch (SAXException e) {
    			e.printStackTrace();
    		} catch (ParserConfigurationException e) {
    			e.printStackTrace();
    		} catch (NotFoundException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
			return true;
		}
		
        protected void onPostExecute(Boolean complete){
	        Toast.makeText(getApplicationContext(), "Loaded Dictionaries!", Toast.LENGTH_LONG).show();
        } 
    }

	
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		if (tab.getPosition() == 0){
			dictionaryScreen = (DictionaryScreen) Fragment.instantiate(this, DictionaryScreen.class.getName());
			dictionaryScreen.setDictionary(dictionary);
			fragmentTransaction.add(android.R.id.content, dictionaryScreen, "Turkish Dictionary");
		} else {
			if (tab.getPosition() == 1){
				translationScreen = (TranslationScreen) Fragment.instantiate(this, TranslationScreen.class.getName());
				translationScreen.setTranslationDictionary(translationDictionary);
				fragmentTransaction.add(android.R.id.content, translationScreen, "English-Turkish Dictionary");
			}
		}
	}

	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		if (tab.getPosition() == 0){
			fragmentTransaction.detach(dictionaryScreen);
		} else {
			if (tab.getPosition() == 1){
				fragmentTransaction.detach(translationScreen);
			}
		}
	}

	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

}

