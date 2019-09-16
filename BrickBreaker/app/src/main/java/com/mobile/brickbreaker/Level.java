package com.mobile.brickbreaker;

import android.graphics.Rect;

public class Level {

	private Brick[][] rows;
	private int row;
	private int column;
	private int levelNo;
	private int brickWidth;
	private int brickHeight;
	
	public Level(int row, int column, int width, int levelNo){
        this.row = row;
        this.column = column;
        this.levelNo = levelNo;
        rows = new Brick[row][column];
        brickWidth = width / column;
        brickHeight = (int) (0.7 * brickWidth);
	}
	
	public int row(){
		return row;
	}
	
	public int column(){
		return column;
	}
	
	public int levelNo(){
		return levelNo;
	}

	public boolean isFinished(){
	    Brick brick;
	    for (int i = 0; i < row; i++){
	        for (int j = 0; j < column; j++){
	            brick = rows[i][j];
	            if (!brick.isBroken()){
	                return false;
	            }
	        }
	    }
	    return true;
	}

	public void constructRow(int rowNo, String rowInfo){
	    for (int i = 0; i < rowInfo.length(); i++){
	        Rect place;
	        place = new Rect(i * brickWidth, rowNo * brickHeight, (i + 1) * brickWidth, (rowNo + 1) * brickHeight);
	        switch (rowInfo.charAt(i)){
	            case '1':
	                rows[rowNo][i] = new Brick(BrickType.NORMAL, place);
	                break;
	            case '2':
	                rows[rowNo][i] = new Brick(BrickType.HARD, place);
	                break;
	            case '3':
	                rows[rowNo][i] = new Brick(BrickType.FASTER, place);
	                break;
	            case '4':
	                rows[rowNo][i] = new Brick(BrickType.SLOWER, place);
	                break;
	            case '5':
	                rows[rowNo][i] = new Brick(BrickType.LARGER, place);
	                break;
	            case '6':
	                rows[rowNo][i] = new Brick(BrickType.SMALLER, place);
	                break;
	            case '7':
	                rows[rowNo][i] = new Brick(BrickType.LIFE, place);
	                break;
	            case '8':
	                rows[rowNo][i] = new Brick(BrickType.DEATH, place);
	                break;
	            case '9':
	                rows[rowNo][i] = new Brick(BrickType.MULTIPLE, place);
	                break;
	        }
	    }
	}

	public Brick brick(int row, int column){
	    return rows[row][column];
	}


}
