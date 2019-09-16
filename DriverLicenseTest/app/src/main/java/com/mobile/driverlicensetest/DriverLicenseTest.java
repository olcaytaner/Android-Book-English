package com.mobile.driverlicensetest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class DriverLicenseTest extends Activity {
	static final int RESTART_EXAM = 1;
	private TextView question;
	private Button choice1;
	private Button choice2;
	private Button choice3;
	private Button choice4;
	private ArrayList<Question> questions;
	private Preferences preferences;
	private int trafficNumberOfQuestions;
	private int engineNumberOfQuestions;
	private int firstAidNumberOfQuestions;
	private int numberOfCorrectAnswers;
	private int questionNo;
	private int correctAnswer;

	private void readPreferences(){
		SharedPreferences basicPreferences = getSharedPreferences("DriverLicenseTest", MODE_PRIVATE);
		preferences = new Preferences(basicPreferences.getBoolean("traffic", true), basicPreferences.getBoolean("engine", true),
				basicPreferences.getBoolean("firstAid", true), basicPreferences.getInt("trafficNumberOfQuestions", 10),
				basicPreferences.getInt("engineNumberOfQuestions", 10), basicPreferences.getInt("firstAidNumberOfQuestions", 10));
	}
	
	private void readQuestionsFromFile(){
		InputStream stream = getResources().openRawResource(R.raw.ehliyet);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-16")));
		Question newQuestion;
		String question;
		questions = new ArrayList<Question>();
	    try {
	        while ((question = reader.readLine()) != null) {
	        	String[] questionContent = question.split(";");
	        	if ((questionContent[0].equalsIgnoreCase("T") || questionContent[0].equalsIgnoreCase("M") || questionContent[0].equalsIgnoreCase("Y"))
	        		&& (questionContent[6].equalsIgnoreCase("A") || questionContent[6].equalsIgnoreCase("B") || questionContent[6].equalsIgnoreCase("C") || questionContent[6].equalsIgnoreCase("D"))){
		        	newQuestion = new Question(questionContent[0], questionContent[1], questionContent[2], questionContent[3], questionContent[4], questionContent[5], questionContent[6]);
		        	questions.add(newQuestion);
	        	}
	        }
	    } catch (IOException e) {
	    }
	}
	
	private Question getCurrentQuestion(){
		int whichQuestion, i;
		String questionType;
		if (questionNo < trafficNumberOfQuestions){
			whichQuestion = questionNo;
			questionType = "T";
		} else {
			if (questionNo < trafficNumberOfQuestions + engineNumberOfQuestions){
				whichQuestion = questionNo - trafficNumberOfQuestions;
				questionType = "M";
			} else {
				whichQuestion = questionNo - trafficNumberOfQuestions - engineNumberOfQuestions;
				questionType = "Y";
			}
		}
		i = 0;
		for (Question question: questions){
			if (question.getQuestionType().equalsIgnoreCase(questionType)){
				if (i == whichQuestion){
					return question;
				} else {
					i++;
				}
			}
		}
		return null;
	}
	
	private void showCurrentQuestion(){
		Question currentQuestion;
		currentQuestion = getCurrentQuestion();
		if (currentQuestion != null){
			question.setText((questionNo + 1) + ") " + currentQuestion.getQuestion());
			choice1.setText("A) " + currentQuestion.getChoice1());
			choice2.setText("B) " + currentQuestion.getChoice2());
			choice3.setText("C) " + currentQuestion.getChoice3());
			choice4.setText("D) " + currentQuestion.getChoice4());
			if (currentQuestion.getCorrectAnswer().equalsIgnoreCase("A")){
				correctAnswer = 1;
			} else {
				if (currentQuestion.getCorrectAnswer().equalsIgnoreCase("B")){
					correctAnswer = 2;
				} else {
					if (currentQuestion.getCorrectAnswer().equalsIgnoreCase("C")){
						correctAnswer = 3;
					} else {
						correctAnswer = 4;
					}
				}
			}
		}
	}
	
	private void startExam(){
		if (!preferences.traffic())
			trafficNumberOfQuestions = 0;
		else
			trafficNumberOfQuestions = preferences.getTrafficNumberOfQuestions();
		if (!preferences.engine())
			engineNumberOfQuestions = 0;
		else
			engineNumberOfQuestions = preferences.getEngineNumberOfQuestions();
		if (!preferences.firstAid())
			firstAidNumberOfQuestions = 0;
		else
			firstAidNumberOfQuestions = preferences.getFirstAidNumberOfQuestions();
		questionNo = 0;
		numberOfCorrectAnswers = 0;
		Collections.shuffle(questions);
		showCurrentQuestion();
		choice1.setEnabled(true);
		choice2.setEnabled(true);
		choice3.setEnabled(true);
		choice4.setEnabled(true);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		question = (TextView) findViewById(R.id.question);
		question.setEnabled(false);
		choice1 = (Button) findViewById(R.id.choice1);
		choice2 = (Button) findViewById(R.id.choice2);
		choice3 = (Button) findViewById(R.id.choice3);
		choice4 = (Button) findViewById(R.id.choice4);
		Button options = (Button) findViewById(R.id.subjects);
		Button startExam = (Button) findViewById(R.id.sinaviBaslat);
		choice1.setOnClickListener(choiceClick);
		choice2.setOnClickListener(choiceClick);
		choice3.setOnClickListener(choiceClick);
		choice4.setOnClickListener(choiceClick);
		options.setOnClickListener(subjectsClick);
		startExam.setOnClickListener(startExamClick);
		readQuestionsFromFile();
		readPreferences();
		startExam();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == RESTART_EXAM) {
	        if (resultCode == RESULT_OK) {
	    		readPreferences();
	        	startExam();
	        }
	    }
	}	
	public OnClickListener choiceClick = new OnClickListener() {
		public void onClick(View v){
			if (((String)v.getTag()).equalsIgnoreCase(Integer.toString(correctAnswer))){
				numberOfCorrectAnswers++;
			}
			questionNo++;
			if (questionNo < trafficNumberOfQuestions + engineNumberOfQuestions + firstAidNumberOfQuestions){
				showCurrentQuestion();
			} else {
				String message = "You answered " + numberOfCorrectAnswers + " questions correctly!";
		        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
		        choice1.setEnabled(false);
		        choice2.setEnabled(false);
		        choice3.setEnabled(false);
		        choice4.setEnabled(false);
			}
		}
	};

	public OnClickListener subjectsClick = new OnClickListener() {
		public void onClick(View v){
			Intent subjectsIntent = new Intent(DriverLicenseTest.this, Subjects.class);
			subjectsIntent.putExtra("preferences", preferences);
			startActivityForResult(subjectsIntent, RESTART_EXAM);
		}
	};

	public OnClickListener startExamClick = new OnClickListener() {
		public void onClick(View v){
			startExam();
		}
	};

}
