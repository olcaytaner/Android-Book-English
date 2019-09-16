package com.mobile.filmarchive;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

public class GenreList extends ListFragment {
	private CursorAdapter genreAdapter;
	private Context context;
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View genreListView = inflater.inflate(R.layout.genre_list, container, false);
    	context = this.getActivity();
        String[] fieldList = new String[] {"name"};
        int[] showList = new int[] {R.id.genreName};
        genreAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.genre_cell, null, fieldList, showList, 0);
        setListAdapter(genreAdapter);
        new GetGenreTask(context, genreAdapter).execute((Object[]) null);
        return genreListView;
    }
    
}
