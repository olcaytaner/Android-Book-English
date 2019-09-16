package com.mobile.chesspieces;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ChessPieces extends Activity {
	
    private long startTime;
    private TextView timeLabel;
    private Screen screen;
    private boolean stopped = false, finished = false;
    private Solution solution;
    private Puzzle puzzle;
    private Puzzle[] puzzles;
    private int numberOfPuzzles;
    
    final Handler h = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            long milliseconds = System.currentTimeMillis() - startTime;
            if (!stopped){
                timeLabel.setText(String.format("%02d:%02d", ((int) (milliseconds / 1000)) / 60, ((int) (milliseconds / 1000)) % 60));
            }
            return false;
        }
    });

    class TimeTask extends TimerTask {
        @Override
        public void run() {
            h.sendEmptyMessage(0);
        }
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
        timeLabel = (TextView) findViewById(R.id.timeLabel);
        readFile();
        screen = (Screen) findViewById(R.id.screen);
        newPuzzle();
        Timer timer = new Timer();
        startTime = System.currentTimeMillis();
        timer.schedule(new TimeTask(), 0, 500);
        screen.invalidate();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);
		return true;
	}
	   
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.solve:
                solve();
                screen.invalidate();
                stopped = true;
				break;
			case R.id.control:
				controlSolution();
				break;
			case R.id.newGame:
                newPuzzle();
                screen.invalidate();
                startTime = System.currentTimeMillis();
                stopped = false;
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	void readFile(){
		InputStream stream = getResources().openRawResource(R.raw.bulmacalar);
		Scanner scanner = new Scanner(stream);
	    int i, j, x, y, numberOfSquares, numberOfAttacks;
	    numberOfPuzzles = scanner.nextInt();
	    puzzles = new Puzzle[numberOfPuzzles];
	    for (i = 0; i < numberOfPuzzles; i++){
	        Puzzle puzzle = new Puzzle();
	        for (j = 0; j < 8; j++){
	            x = scanner.nextInt();
	            y = scanner.nextInt();
	            Square square = new Square(x - 1, y - 1);
	            puzzle.addPossiblePlace(square);
	        }
	        numberOfSquares = scanner.nextInt();
	        for (j = 0; j < numberOfSquares; j++){
	            x = scanner.nextInt();
	            y = scanner.nextInt();
	            numberOfAttacks = scanner.nextInt();
	            Attack attack = new Attack(x - 1, y - 1, numberOfAttacks);
	            puzzle.addAttack(attack);
	        }
	        puzzles[i] = puzzle;
	    }
	}

	void newPuzzle(){
		Random random;
		random = new Random();
	    int i;
	    finished = false;
	    puzzle = puzzles[random.nextInt(puzzles.length)];
	    screen.table = new int[8];
	    screen.placeOnBoard = new boolean[8];
	    for (i = 0; i < 8; i++){
	        screen.table[i] = Puzzle.EMPTY;
	        screen.placeOnBoard[i] = false;
	    }
	    screen.isMoving = false;
	    screen.puzzle = puzzle;
	}

	void controlSolution(){
	    String message;
	    solution = new Solution();
	    for (int i = 0; i < 8; i++)
	        solution.addPieceWithPieceNo(screen.table[i]);
	    if (!puzzle.satisfiesConditions(solution)){
	        message = "Your solution is wrong!";
	    } else {
	        message = "Your solution is correct!";
	    }
	    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}

	void searchForSolution(){
	    ArrayList<Piece> candidates = new ArrayList<Piece>();
	    if (!puzzle.satisfiesConditionsTemporarily(solution)){
	        return;
	    } else {
	        if (solution.numberOfPieces() == 8){
	            if (puzzle.satisfiesConditions(solution)){
	                finished = true;
	            }
	        }
	        else{
	            candidates = solution.generateCandidates();
	            for (Piece piece :candidates){
	                solution.addPiece(piece);
	                searchForSolution();
	                if (finished){
	                    return;
	                }
	                solution.removePiece();
	            }
	        }
	    }
	}

	void solve(){
	    int i;
	    finished = false;
	    solution = new Solution();
	    searchForSolution();
	    for (i = 0; i < 8; i++){
	        screen.table[i] = solution.pieceNo(i);
	        screen.placeOnBoard[i] = true;
	    }
	}

}
