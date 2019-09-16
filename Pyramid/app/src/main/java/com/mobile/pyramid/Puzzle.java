package com.mobile.pyramid;

public class Puzzle {
	private int puzzleSize;
	private int[][] numbers;
	private int[] played;
	
	public Puzzle(String puzzleInfo){
		int k = 0;
		puzzleSize = (int) Math.sqrt(2 * puzzleInfo.length());
		played = new int[puzzleSize];
		numbers = new int[puzzleSize][];
		for (int i = 0; i < puzzleSize; i++){
			played[i] = -1;
			numbers[i] = new int[i + 1];
			for (int j = 0; j <= i; j++){
				numbers[i][j] = puzzleInfo.charAt(k) - 48;
				k++;
			}
		}		
	}
	
	public int getNumber(int row, int column){
		return numbers[row][column];
	}
	
	public int playedValue(int row){
		return played[row];
	}
	
	public void play(int row, int value){
		played[row] = value;
	}
	
	public int puzzleSize(){
		return puzzleSize;
	}
}
