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

public class DirectorAdd extends Fragment{
	private EditText name;
	private EditText surname;
	private Context context;
	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) { 
        View addDirectorView = inflater.inflate(R.layout.director_add, container, false);
    	context = this.getActivity();
		name = (EditText) addDirectorView.findViewById(R.id.addDirectorName);
		surname = (EditText) addDirectorView.findViewById(R.id.addDirectorSurname);
		Button add = (Button) addDirectorView.findViewById(R.id.addDirector);
		add.setOnClickListener(addClick);
        return addDirectorView;
    }
    
    AsyncTask<Object, Object, Object> AddDirectorTask = new AsyncTask<Object, Object, Object>(){
    	@Override
        protected Object doInBackground(Object... params){
    		FilmDatabase database = new FilmDatabase(context);
    		database.addDirector(name.getText().toString(), surname.getText().toString());
    		return null;
        } 

        @Override
        protected void onPostExecute(Object result){
        } 
    };
    
    public OnClickListener addClick = new OnClickListener() {
		public void onClick(View v){
			AddDirectorTask.execute((Object[]) null);
		}
	};

}
