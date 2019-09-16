package com.mobile.driverlicensetest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Subjects extends Activity {
	private CheckBox trafficCheckBox;
	private CheckBox engineCheckBox;
	private CheckBox firstAidCheckBox;
	private TextView trafficCounter;
	private TextView engineCounter;
	private TextView firstAidCounter;
	private SeekBar trafficSeekBar;
	private SeekBar engineSeekBar;
	private SeekBar firstAidSeekBar;
	private Preferences preferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subjects);
		Button restartExam = (Button) findViewById(R.id.restartExam);
		Button goBack = (Button) findViewById(R.id.goBack);
		trafficCheckBox = (CheckBox) findViewById(R.id.trafficCheckBox);
		engineCheckBox = (CheckBox) findViewById(R.id.engineCheckBox);
		firstAidCheckBox = (CheckBox) findViewById(R.id.firstAidCheckBox);
		trafficCounter = (TextView) findViewById(R.id.trafficCounter);
		engineCounter = (TextView) findViewById(R.id.engineCounter);
		firstAidCounter = (TextView) findViewById(R.id.firstAidCounter);
		trafficSeekBar = (SeekBar) findViewById(R.id.trafficSeekBar);
		engineSeekBar = (SeekBar) findViewById(R.id.engineSeekBar);
		firstAidSeekBar = (SeekBar) findViewById(R.id.firstAidSeekBar);
		restartExam.setOnClickListener(restartExamClick);
		goBack.setOnClickListener(goBackClick);
		trafficCheckBox.setOnCheckedChangeListener(optionsClick);
		engineCheckBox.setOnCheckedChangeListener(optionsClick);
		firstAidCheckBox.setOnCheckedChangeListener(optionsClick);
		trafficSeekBar.setOnSeekBarChangeListener(optionsValueChanged);
		engineSeekBar.setOnSeekBarChangeListener(optionsValueChanged);
		firstAidSeekBar.setOnSeekBarChangeListener(optionsValueChanged);
		Intent konularIntent = getIntent();
		preferences = (Preferences) konularIntent.getSerializableExtra("preferences");
		trafficCheckBox.setChecked(preferences.traffic());
		engineCheckBox.setChecked(preferences.engine());
		firstAidCheckBox.setChecked(preferences.firstAid());
		trafficSeekBar.setProgress(preferences.getTrafficNumberOfQuestions());
		engineSeekBar.setProgress(preferences.getEngineNumberOfQuestions());
		firstAidSeekBar.setProgress(preferences.getFirstAidNumberOfQuestions());
		trafficCounter.setText(String.format("%d", preferences.getTrafficNumberOfQuestions()));
		engineCounter.setText(String.format("%d", preferences.getEngineNumberOfQuestions()));
		firstAidCounter.setText(String.format("%d", preferences.getFirstAidNumberOfQuestions()));
	}
	
	public OnClickListener restartExamClick = new OnClickListener() {
		public void onClick(View v){
			((Subjects) v.getContext()).setResult(RESULT_OK);
			finish();
		}
	};
	
	public OnClickListener goBackClick = new OnClickListener() {
		public void onClick(View v){
			((Subjects) v.getContext()).setResult(RESULT_CANCELED);
			finish();
		}
	};

	public OnCheckedChangeListener optionsClick = new OnCheckedChangeListener(){
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){	
			SharedPreferences basicPreferences = getSharedPreferences("DriverLicenseTest", MODE_PRIVATE);
			SharedPreferences.Editor changeBasicPreferences = basicPreferences.edit();
			if (((String)buttonView.getTag()).equalsIgnoreCase("1")){
				changeBasicPreferences.putBoolean("traffic", isChecked);
			} else {
				if (((String)buttonView.getTag()).equalsIgnoreCase("2")){
					changeBasicPreferences.putBoolean("engine", isChecked);
				} else {
					if (((String)buttonView.getTag()).equalsIgnoreCase("3")){
						changeBasicPreferences.putBoolean("firstAid", isChecked);
					}
				}
			}
			changeBasicPreferences.apply();
		}		
	};
	
	public OnSeekBarChangeListener optionsValueChanged = new OnSeekBarChangeListener(){
		public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
			SharedPreferences basicPreferences = getSharedPreferences("DriverLicenseTest", MODE_PRIVATE);
			SharedPreferences.Editor changeBasicPreferences = basicPreferences.edit();
			if (((String)arg0.getTag()).equalsIgnoreCase("1")){
				changeBasicPreferences.putInt("trafficNumberOfQuestions", arg1);
				trafficCounter.setText(String.format("%d", arg1));
			} else {
				if (((String)arg0.getTag()).equalsIgnoreCase("2")){
					changeBasicPreferences.putInt("engineNumberOfQuestions", arg1);
					engineCounter.setText(String.format("%d", arg1));
				} else {
					if (((String)arg0.getTag()).equalsIgnoreCase("3")){
						changeBasicPreferences.putInt("firstAidNumberOfQuestions", arg1);
						firstAidCounter.setText(String.format("%d", arg1));
					}
				}
			}
			changeBasicPreferences.apply();
		}
		public void onStartTrackingTouch(SeekBar arg0) {
		}
		public void onStopTrackingTouch(SeekBar arg0) {
		}		
	};

}
