package com.mobile.calculator;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class Calculator extends Activity {
	private EditText result;
	private char previousOperation;
	private int previousNumber;
	private int number;
	private int memory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		result = (EditText) findViewById(R.id.Result);
		Button button0 = (Button) findViewById(R.id.Button0);
		Button button1 = (Button) findViewById(R.id.Button1);
		Button button2 = (Button) findViewById(R.id.Button2);
		Button button3 = (Button) findViewById(R.id.Button3);
		Button button4 = (Button) findViewById(R.id.Button4);
		Button button5 = (Button) findViewById(R.id.Button5);
		Button button6 = (Button) findViewById(R.id.Button6);
		Button button7 = (Button) findViewById(R.id.Button7);
		Button button8 = (Button) findViewById(R.id.Button8);
		Button button9 = (Button) findViewById(R.id.Button9);
		Button buttonPlus = (Button) findViewById(R.id.ButtonPlus);
		Button buttonMinus = (Button) findViewById(R.id.ButtonMinus);
		Button buttonMultiply = (Button) findViewById(R.id.ButtonMultiply);
		Button buttonDivide = (Button) findViewById(R.id.ButtonDivide);
		Button buttonEqual = (Button) findViewById(R.id.ButtonEqual);
		Button buttonDelete = (Button) findViewById(R.id.ButtonClear);
		Button buttonAddToMemory = (Button) findViewById(R.id.ButtonAddToMemory);
		Button buttonRemoveFromMemory = (Button) findViewById(R.id.ButtonRemoveFromMemory);
		Button buttonShowMemory = (Button) findViewById(R.id.ButtonShowMemory);
		Button buttonClearMemory = (Button) findViewById(R.id.ButtonClearMemory);
		button0.setOnClickListener(button0Click);
		button1.setOnClickListener(button1Click);
		button2.setOnClickListener(button2Click);
		button3.setOnClickListener(button3Click);
		button4.setOnClickListener(button4Click);
		button5.setOnClickListener(button5Click);
		button6.setOnClickListener(button6Click);
		button7.setOnClickListener(button7Click);
		button8.setOnClickListener(button8Click);
		button9.setOnClickListener(button9Click);
		buttonPlus.setOnClickListener(buttonPlusClick);
		buttonMinus.setOnClickListener(buttonMinusClick);
		buttonMultiply.setOnClickListener(buttonMultiplyClick);
		buttonDivide.setOnClickListener(buttonDivideClick);
		buttonEqual.setOnClickListener(buttonEqualClick);
		buttonDelete.setOnClickListener(buttonDeleteClick);
		buttonAddToMemory.setOnClickListener(buttonAddToMemoryClick);
		buttonRemoveFromMemory.setOnClickListener(buttonRemoveFromMemoryClick);
		buttonShowMemory.setOnClickListener(buttonShowMemoryClick);
		buttonClearMemory.setOnClickListener(buttonClearMemoryClick);
		previousOperation = '=';
		previousNumber = 0;
		number = 0;
		memory = 0;
	}
	
	private void showInScreen(int number){
		result.setText(String.format("%d", number));
	}
	
	private void addDigit(int digit){
		number = number * 10 + digit;
		showInScreen(number);
	}
	
	private void doOperation(char operation){
		switch (previousOperation) {
		case '+':
			previousNumber = previousNumber + number;
			break;
		case '-':
			previousNumber = previousNumber - number;
			break;
		case 'x':
			previousNumber = previousNumber * number;
			break;
		case '/':
			if (number != 0)
				previousNumber = previousNumber / number;
			break;
		case '=':
			previousNumber = number;
			break;
		}
		previousOperation = operation;
		number = 0;
		showInScreen(previousNumber);
	}
	
	public OnClickListener button0Click = new OnClickListener() {
		public void onClick(View v){
			addDigit(0);
		}
	};

	public OnClickListener button1Click = new OnClickListener() {
		public void onClick(View v){
			addDigit(1);
		}
	};

	public OnClickListener button2Click = new OnClickListener() {
		public void onClick(View v){
			addDigit(2);
		}
	};

	public OnClickListener button3Click = new OnClickListener() {
		public void onClick(View v){
			addDigit(3);
		}
	};

	public OnClickListener button4Click = new OnClickListener() {
		public void onClick(View v){
			addDigit(4);
		}
	};

	public OnClickListener button5Click = new OnClickListener() {
		public void onClick(View v){
			addDigit(5);
		}
	};

	public OnClickListener button6Click = new OnClickListener() {
		public void onClick(View v){
			addDigit(6);
		}
	};

	public OnClickListener button7Click = new OnClickListener() {
		public void onClick(View v){
			addDigit(7);
		}
	};

	public OnClickListener button8Click = new OnClickListener() {
		public void onClick(View v){
			addDigit(8);
		}
	};

	public OnClickListener button9Click = new OnClickListener() {
		public void onClick(View v){
			addDigit(9);
		}
	};

	public OnClickListener buttonPlusClick = new OnClickListener() {
		public void onClick(View v){
			doOperation('+');
		}
	};

	public OnClickListener buttonMinusClick = new OnClickListener() {
		public void onClick(View v){
			doOperation('-');
		}
	};

	public OnClickListener buttonMultiplyClick = new OnClickListener() {
		public void onClick(View v){
			doOperation('x');
		}
	};

	public OnClickListener buttonDivideClick = new OnClickListener() {
		public void onClick(View v){
			doOperation('/');
		}
	};

	public OnClickListener buttonEqualClick = new OnClickListener() {
		public void onClick(View v){
			doOperation('=');
			number = previousNumber;
			previousNumber = 0;
		}
	};

	public OnClickListener buttonDeleteClick = new OnClickListener() {
		public void onClick(View v){
			number = 0;
			showInScreen(number);
		}
	};

	public OnClickListener buttonAddToMemoryClick = new OnClickListener() {
		public void onClick(View v){
			 memory += number;
			 number = 0;
		}
	};

	public OnClickListener buttonRemoveFromMemoryClick = new OnClickListener() {
		public void onClick(View v){
			 memory -= number;
			 number = 0;
		}
	};

	public OnClickListener buttonShowMemoryClick = new OnClickListener() {
		public void onClick(View v){
			 number = memory;
			 showInScreen(number);
		}
	};

	public OnClickListener buttonClearMemoryClick = new OnClickListener() {
		public void onClick(View v){
			 memory = 0;
		}
	};
}
