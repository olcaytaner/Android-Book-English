package com.mobile.filmarchive;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.os.Bundle;
import android.app.Activity;

public class FilmArchive extends Activity implements
		ActionBar.TabListener {
	private Fragment filmList;
	private Fragment addFilm;
	private Fragment genreList;
	private Fragment addGenre;
	private Fragment directorList;
	private Fragment addDirector;
		
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText(R.string.filmList)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.addFilm)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.genreList)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.addGenre)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.directorList)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.addDirector)
				.setTabListener(this));
	}
	
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		switch (tab.getPosition()){
			case 0:
				filmList = Fragment.instantiate(this, FilmList.class.getName());
				fragmentTransaction.add(android.R.id.content, filmList, "FilmListesi");
				break;
			case 1:
				addFilm = Fragment.instantiate(this, FilmAdd.class.getName());
				fragmentTransaction.add(android.R.id.content, addFilm, "FilmEkle");
				break;
			case 2:
				genreList = Fragment.instantiate(this, GenreList.class.getName());
				fragmentTransaction.add(android.R.id.content, genreList, "TurListesi");
				break;
			case 3:
				addGenre = Fragment.instantiate(this, GenreAdd.class.getName());
				fragmentTransaction.add(android.R.id.content, addGenre, "TurEkle");
				break;
			case 4:
				directorList = Fragment.instantiate(this, DirectorList.class.getName());
				fragmentTransaction.add(android.R.id.content, directorList, "YonetmenListesi");
				break;
			case 5:
				addDirector = Fragment.instantiate(this, DirectorAdd.class.getName());
				fragmentTransaction.add(android.R.id.content, addDirector, "YonetmenEkle");
				break;
		}
	}

	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		switch (tab.getPosition()){
			case 0:
				fragmentTransaction.detach(filmList);
				break;
			case 1:
				fragmentTransaction.detach(addFilm);
				break;
			case 2:
				fragmentTransaction.detach(genreList);
				break;
			case 3:
				fragmentTransaction.detach(addGenre);
				break;
			case 4:
				fragmentTransaction.detach(directorList);
				break;
			case 5:
				fragmentTransaction.detach(addDirector);
				break;
		}
	}

	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

}
