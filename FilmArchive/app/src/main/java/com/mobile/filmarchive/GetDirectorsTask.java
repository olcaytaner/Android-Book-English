package com.mobile.filmarchive;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.CursorAdapter;

public class GetDirectorsTask extends AsyncTask<Object, Object, Cursor>{
	
	FilmDatabase database;
	CursorAdapter directorAdapter;
	
	public GetDirectorsTask(Context context, CursorAdapter directorAdapter){
		database = new FilmDatabase(context);
		this.directorAdapter = directorAdapter;
	}

    @Override
    protected Cursor doInBackground(Object... params){
 	   database.open();
 	   return database.getAllDirectors();
    }

    @Override
    protected void onPostExecute(Cursor result){
 	   directorAdapter.changeCursor(result);
 	   database.close();
    } 

}
