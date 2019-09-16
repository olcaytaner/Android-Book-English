package com.mobile.brickbreaker;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.Context;

public class Game {

	private ArrayList<Level> levels;
	private int numberOfLevels;

	public Game(Context context, int width){
		InputStream stream = context.getResources().openRawResource(R.raw.levels);
		Scanner scanner = new Scanner(stream);
	    int i, row, column;
	    String rowInfo;
	    numberOfLevels = Integer.parseInt(scanner.next());
	    levels = new ArrayList<Level>();
	    for (i = 0; i < numberOfLevels; i++){
		    row = Integer.parseInt(scanner.next());
		    column = Integer.parseInt(scanner.next());
	        Level level = new Level(row, column, width, i);
	        for (int j = 0; j < row; j++){
	           rowInfo = scanner.next();
	           level.constructRow(j, rowInfo);
	        }
	        levels.add(level);
	    }
	}

	public Level level(int position){
	    return levels.get(position);
	}

}
