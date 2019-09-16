package com.mobile.filmarchive;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class FilmChangeDelete extends Activity {
	private long rowID;
	private EditText name;
	private EditText runtime;
	private EditText year;
	private Spinner genre;
	private Spinner director;
	private Context context;
	private CursorAdapter genreAdapter;
	private CursorAdapter directorAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
    	String[] fieldList;
    	int[] showList;
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();
		setContentView(R.layout.film_change_delete);
		Button saveChanges = (Button) findViewById(R.id.saveChanges);
		Button delete = (Button) findViewById(R.id.delete);
		Button goBack = (Button) findViewById(R.id.goBack);
		name = (EditText) findViewById(R.id.name);
		runtime = (EditText) findViewById(R.id.runtime);
		year = (EditText) findViewById(R.id.year);
		genre = (Spinner) findViewById(R.id.genre);
		director = (Spinner) findViewById(R.id.director);
        fieldList = new String[] {"name"};
        showList = new int[] {R.id.genreName};
        genreAdapter = new SimpleCursorAdapter(this, R.layout.genre_cell, null, fieldList, showList, 0);
        genre.setAdapter(genreAdapter);
        new GetGenreTask(context, genreAdapter).execute((Object[]) null);
        fieldList = new String[] {"name", "surname"};
        showList = new int[] {R.id.directorName, R.id.directorSurname};
        directorAdapter = new SimpleCursorAdapter(this, R.layout.director_cell, null, fieldList, showList, 0);
        director.setAdapter(directorAdapter);
        new GetDirectorsTask(context, directorAdapter).execute((Object[]) null);
		saveChanges.setOnClickListener(saveChangesClick);
		delete.setOnClickListener(deleteClick);
		goBack.setOnClickListener(goBackClick);
		Bundle extras = getIntent().getExtras();
        rowID = extras.getLong("row_id");
        new GetFilmTask().execute(rowID);
	}
	
	private class GetFilmTask extends AsyncTask<Long, Object, Cursor>{
		
		FilmDatabase database = new FilmDatabase(context);

		@Override
	     protected Cursor doInBackground(Long... params){
	        database.open();
	        return database.getFilm(params[0]);
	     }
		
	      @Override
	      protected void onPostExecute(Cursor result){
	         super.onPostExecute(result);
	         result.moveToFirst(); 
	         int namePos = result.getColumnIndex("name");
	         int runtimePos = result.getColumnIndex("runtime");
	         int yearPos = result.getColumnIndex("year");
	         int genrePos = result.getColumnIndex("genre");
	         int directorPos = result.getColumnIndex("director");
	         name.setText(result.getString(namePos));
	         runtime.setText("" + result.getInt(runtimePos));
	         year.setText("" + result.getInt(yearPos));
	         for (int i = 0; i < genreAdapter.getCount(); i++){
	        	 Cursor cursor = (Cursor) genreAdapter.getItem(i);
	        	 String genreName = cursor.getString(cursor.getColumnIndex("name"));
	        	 String resultGenreName = result.getString(genrePos);
	             if (genreName.equals(resultGenreName)){
	                 genre.setSelection(i);
	                 break;
	             }
	         }
	         for (int i = 0; i < directorAdapter.getCount(); i++){
	        	 Cursor cursor = (Cursor) directorAdapter.getItem(i);
	        	 String directorName = cursor.getString(cursor.getColumnIndex("name")) + " " + cursor.getString(cursor.getColumnIndex("surname"));
	        	 String resultDirectorName = result.getString(directorPos);
	             if (directorName.equals(resultDirectorName)){
	                 director.setSelection(i);
	                 break;
	             }
	         }
	         result.close(); 
	         database.close();
	      }
	}
	
    AsyncTask<Object, Object, Object> changeFilmTask = new AsyncTask<Object, Object, Object>(){
    	@Override
        protected Object doInBackground(Object... params){
    		FilmDatabase database = new FilmDatabase(context);
	        Cursor genreCursor = (Cursor) genreAdapter.getItem(genre.getSelectedItemPosition());
	        String genreName = genreCursor.getString(genreCursor.getColumnIndex("name"));
	        Cursor directorCursor = (Cursor) directorAdapter.getItem(director.getSelectedItemPosition());
	        String directorName = directorCursor.getString(directorCursor.getColumnIndex("name")) + " " + directorCursor.getString(directorCursor.getColumnIndex("surname"));
    		database.updateFilm(rowID, name.getText().toString(), Integer.parseInt(runtime.getText().toString()),
    				Integer.parseInt(year.getText().toString()), genreName, directorName);
    		return null;
        } 

        @Override
        protected void onPostExecute(Object result){
           finish();
        } 
    };
    
    AsyncTask<Long, Object, Object> deleteFilmTask = new AsyncTask<Long, Object, Object>(){
        @Override
        protected Object doInBackground(Long... params){
			FilmDatabase database = new FilmDatabase(context);
			database.deleteFilm(params[0]);
			return null;
        }

        @Override
        protected void onPostExecute(Object result){
           finish(); 
        } 
   }; 
	
	public OnClickListener saveChangesClick = new OnClickListener() {
		public void onClick(View v){
			changeFilmTask.execute((Object[]) null);
		}
	};
	
	public OnClickListener deleteClick = new OnClickListener() {
		public void onClick(View v){
			deleteFilmTask.execute(new Long[] { rowID });
		}
	};

	public OnClickListener goBackClick = new OnClickListener() {
		public void onClick(View v){
			finish();
		}
	};
	
}

