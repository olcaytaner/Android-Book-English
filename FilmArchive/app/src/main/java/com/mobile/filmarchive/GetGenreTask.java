package com.mobile.filmarchive;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.CursorAdapter;

public class GetGenreTask extends AsyncTask<Object, Object, Cursor>{
	
	private FilmDatabase database;
	private CursorAdapter genreAdapter;
	
	public GetGenreTask(Context context, CursorAdapter genreAdapter){
		database = new FilmDatabase(context);
		this.genreAdapter = genreAdapter;
	}

    @Override
    protected Cursor doInBackground(Object... params){
 	   database.open();
 	   return database.getAllGenre();
    }

    @Override
    protected void onPostExecute(Cursor result){
 	   genreAdapter.changeCursor(result);
 	   database.close();
    } 

}
