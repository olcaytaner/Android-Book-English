package com.mobile.filmarchive;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class FilmAdd extends Fragment {
	private EditText name;
	private EditText runtime;
	private EditText year;
	private Spinner genre;
	private Spinner director;
	private Context context;
	private CursorAdapter genreAdapter;
	private CursorAdapter directorAdapter;
	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	String[] fieldList;
    	int[] showList;
        View filmAddView = inflater.inflate(R.layout.film_add, container, false);
    	context = this.getActivity();
		name = (EditText) filmAddView.findViewById(R.id.addName);
		runtime = (EditText) filmAddView.findViewById(R.id.addRuntime);
		year = (EditText) filmAddView.findViewById(R.id.addYear);
		genre = (Spinner) filmAddView.findViewById(R.id.addGenre);
		director = (Spinner) filmAddView.findViewById(R.id.addDirector);
		Button add = (Button) filmAddView.findViewById(R.id.add);
		add.setOnClickListener(addClick);
        fieldList = new String[] {"name"};
        showList = new int[] {R.id.genreName};
        genreAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.genre_cell, null, fieldList, showList, 0);
        genre.setAdapter(genreAdapter);
        new GetGenreTask(context, genreAdapter).execute((Object[]) null);
        fieldList = new String[] {"name", "surname"};
        showList = new int[] {R.id.directorName, R.id.directorSurname};
        directorAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.director_cell, null, fieldList, showList, 0);
        director.setAdapter(directorAdapter);
        new GetDirectorsTask(context, directorAdapter).execute((Object[]) null);
        return filmAddView;
    }
    
    AsyncTask<Object, Object, Object> AddFilmTask = new AsyncTask<Object, Object, Object>(){
    	@Override
        protected Object doInBackground(Object... params){
    		FilmDatabase database = new FilmDatabase(context);
	        Cursor genreCursor = (Cursor) genreAdapter.getItem(genre.getSelectedItemPosition());
	        String genreName = genreCursor.getString(genreCursor.getColumnIndex("name"));
	        Cursor directorCursor = (Cursor) directorAdapter.getItem(director.getSelectedItemPosition());
	        String directorName = directorCursor.getString(directorCursor.getColumnIndex("name")) + " " + directorCursor.getString(directorCursor.getColumnIndex("surname"));
    		database.addFilm(name.getText().toString(), Integer.parseInt(runtime.getText().toString()),
    				Integer.parseInt(year.getText().toString()), genreName, directorName);
    		return null;
        } 

        @Override
        protected void onPostExecute(Object result){
        } 
    };
    
    public OnClickListener addClick = new OnClickListener() {
		public void onClick(View v){
			AddFilmTask.execute((Object[]) null);
		}
	};
	
}
