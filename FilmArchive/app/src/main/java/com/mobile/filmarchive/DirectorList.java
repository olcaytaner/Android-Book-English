package com.mobile.filmarchive;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

public class DirectorList extends ListFragment{
	private CursorAdapter directorAdapter;
	private Context context;
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View directorListView = inflater.inflate(R.layout.director_list, container, false);
    	context = this.getActivity();
        String[] fieldList = new String[] {"name", "surname"};
        int[] showList = new int[] {R.id.directorName, R.id.directorSurname};
        directorAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.director_cell, null, fieldList, showList, 0);
        setListAdapter(directorAdapter);
        new GetDirectorsTask(context, directorAdapter).execute((Object[]) null);
        return directorListView;
    }
    
}
