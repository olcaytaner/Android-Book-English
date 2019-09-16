package com.mobile.filmarchive;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
 
public class FilmList extends ListFragment {
	protected static final String ROW_ID = "row_id";
	private CursorAdapter filmAdapter;
	private Context context;
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View filmListView = inflater.inflate(R.layout.film_list, container, false);
    	context = this.getActivity();
        String[] fieldList = new String[] {"name", "year"};
        int[] showList = new int[] {R.id.filmName, R.id.filmYear};
        filmAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.film_cell, null, fieldList, showList, 0);
        setListAdapter(filmAdapter);
        new GetAllFilmsTask().execute((Object[]) null);
        return filmListView;
    }
    
    private class GetAllFilmsTask extends AsyncTask<Object, Object, Cursor>{
    	FilmDatabase database = new FilmDatabase(context);

        @Override
        protected Cursor doInBackground(Object... params){
     	   database.open();
     	   return database.getAllFilms();
        }

        @Override
        protected void onPostExecute(Cursor result){
     	   filmAdapter.changeCursor(result);
     	   database.close();
        } 
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	Intent filmChangeDelete = new Intent(l.getContext(), FilmChangeDelete.class);
    	filmChangeDelete.putExtra(ROW_ID, id);
    	startActivity(filmChangeDelete);
    }
 
}