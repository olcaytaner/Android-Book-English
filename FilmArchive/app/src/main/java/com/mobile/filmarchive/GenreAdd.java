package com.mobile.filmarchive;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class GenreAdd extends Fragment{
	private EditText name;
	private Context context;
	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) { 
        View addGenreView = inflater.inflate(R.layout.genre_add, container, false);
    	context = this.getActivity();
		name = (EditText) addGenreView.findViewById(R.id.addGenreName);
		Button add = (Button) addGenreView.findViewById(R.id.addGenre);
		add.setOnClickListener(addClick);
        return addGenreView;
    }
    
    AsyncTask<Object, Object, Object> AddGenreTask = new AsyncTask<Object, Object, Object>(){
    	@Override
        protected Object doInBackground(Object... params){
    		FilmDatabase database = new FilmDatabase(context);
    		database.addGenre(name.getText().toString());
    		return null;
        } 

        @Override
        protected void onPostExecute(Object result){
        } 
    };
    
    public OnClickListener addClick = new OnClickListener() {
		public void onClick(View v){
			AddGenreTask.execute((Object[]) null);
		}
	};

}
