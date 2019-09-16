package com.mobile.bodymassindex;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;

public class BodyMassIndex extends Activity {
	private double height;
	private double weight;
	private double bodyMassIndex;
	private EditText bme;
	private TextView situation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		height = 0.0;
		weight = 0.0;
		EditText heightInput = (EditText) findViewById(R.id.HeightInput);
		EditText weightInput = (EditText) findViewById(R.id.WeightInput);
		bme = (EditText) findViewById(R.id.BMEInput);
		situation = (TextView) findViewById(R.id.Situation);
		bme.setEnabled(false);
		heightInput.addTextChangedListener(heightWatcher);
		weightInput.addTextChangedListener(weightWatcher);
	}
	
	private void showResult(){
		bodyMassIndex = weight / (height * height);
		bme.setText(String.format("%.2f", bodyMassIndex));
		if (bodyMassIndex < 20){
			situation.setText(R.string.underweight);
	    } else {
			if (bodyMassIndex < 25){
				situation.setText(R.string.normal);
			} else {
				if (bodyMassIndex < 30){
					situation.setText(R.string.overweight);
				} else {
					if (bodyMassIndex < 35){
						situation.setText(R.string.obese);
					} else {
						if (bodyMassIndex < 45){
							situation.setText(R.string.moderatelyobese);
						} else {
							if (bodyMassIndex < 50){
								situation.setText(R.string.severlyobese);
							} else {
								situation.setText(R.string.deadlyobese);
							}
						}
					}
				}
			}
		}
	}
	
	private TextWatcher heightWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (s.toString().length() > 0){
				height = Double.parseDouble(s.toString());
				showResult();
			}
		} 
		@Override
		public void afterTextChanged(Editable s){			
		} 
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {			
		} 
	}; 	

	private TextWatcher weightWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (s.toString().length() > 0){
				weight = Double.parseDouble(s.toString());
				showResult();
			}
		} 
		@Override
		public void afterTextChanged(Editable s){			
		} 
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {			
		} 
	}; 	

}
