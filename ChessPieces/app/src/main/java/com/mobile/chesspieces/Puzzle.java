package com.mobile.chesspieces;

import java.util.ArrayList;

public class Puzzle {
	
	public static final int EMPTY = -1;
	private ArrayList<Square> possiblePlaces;
	private ArrayList<Attack> attacks;
	
	public Puzzle(){
		possiblePlaces = new ArrayList<Square>();
		attacks = new ArrayList<Attack>();
	}
	
	void addPossiblePlace(Square place){
	    possiblePlaces.add(place);
	}

	int possiblePlaceNo(int x, int y){
	    int i;
	    for (i = 0; i < possiblePlaces.size(); i++){
	        Square square = possiblePlaces.get(i);
	        if (square.getX() == x && square.getY() == y){
	            return i;
	        }
	    }
	    return -1;
	}

	int numberOfPossiblePlaces(){
	    return possiblePlaces.size();
	}

	Square possiblePlace(int position){
	    return possiblePlaces.get(position);
	}

	int numberOfAttacks(){
	    return attacks.size();
	}

	void addAttack(Attack attack){
	    attacks.add(attack);
	}

	Attack attack(int position){
	    return attacks.get(position);
	}

	int kingControl(Solution solution, int x, int y){
	    int i, j, who;
	    for (i = -1; i < 2; i++)
	        for (j = -1; j < 2; j++){
	            who = possiblePlaceNo(x + i,y + j);
	            if (who != -1 && solution.pieceName(who).equalsIgnoreCase("king"))
	                return 1;
	        }
	    return 0;
	}

	int bishopControl(Solution solution, int x, int y){
	    int xIncrease[] = {1, 1, -1, -1};
	    int yIncrease[] = {1, -1, 1, -1};
	    int i, j, who, count = 0, a, b;
	    for (j = 0; j < 4; j++){
	        for (i = 1; i < 8; i++){
	            a = x + i * xIncrease[j];
	            b = y + i * yIncrease[j];
	            who = possiblePlaceNo(a, b);
	            if (who != EMPTY){
	                if (solution.pieceName(who).startsWith("bishop"))
	                    count++;
	                else
	                    break;
	            }
	        }
	    }
	    return count;
	}

	int rookControl(Solution solution, int x, int y){
	    int xIncrease[] = {1, -1, 0, 0};
	    int yIncrease[] = {0, 0, 1, -1};
	    int i, j, who, count = 0, a, b;
	    for (j = 0; j < 4; j++){
	        for (i = 1; i < 8; i++){
	            a = x + i * xIncrease[j];
	            b = y + i * yIncrease[j];
	            who = possiblePlaceNo(a, b);
	            if (who != EMPTY){
	                if (solution.pieceName(who).startsWith("rook"))
	                    count++;
	                else
	                    break;
	            }
	        }
	    }
	    return count;
	}

	int queenControl(Solution solution, int x, int y){
	    int xIncrease[] = {1, 1, -1, -1, 1, -1, 0, 0};
	    int yIncrease[] = {1, -1, 1, -1, 0, 0, 1, -1};
	    int i, j, who, a, b;
	    for (j = 0; j < 8; j++){
	        for (i = 1; i < 8; i++){
	            a = x + i * xIncrease[j];
	            b = y + i * yIncrease[j];
	            who = possiblePlaceNo(a, b);
	            if (who != EMPTY){
	                if (solution.pieceName(who).equalsIgnoreCase("queen"))
	                    return 1;
	                else
	                    break;
	            }
	        }
	    }
	    return 0;
	}

	int knightControl(Solution solution, int x, int y){
	    int xIncrease[] = {1, 2, 1, -1, -1, 2, -2, -2};
	    int yIncrease[] = {-2, -1, 2, 2, -2, 1, 1, -1};
	    int j, who, a, b, count = 0;
	    for (j = 0; j < 8; j++){
	        a = x + xIncrease[j];
	        b = y + yIncrease[j];
	        who = possiblePlaceNo(a, b);
	        if (who != EMPTY){
	            if (solution.pieceName(who).startsWith("knight"))
	                count++;
	            else
	                break;
	        }
	    }
	    return count;
	}

	boolean satisfiesConditions(Solution solution){
	    int i, x, y, count;
	    for (i = 0; i < attacks.size(); i++){
	        Attack attack = attacks.get(i);
	        x = attack.getX();
	        y = attack.getY();
	        count = kingControl(solution, x, y) + bishopControl(solution, x, y) + rookControl(solution, x, y) + queenControl(solution, x, y) + knightControl(solution, x, y);
	        if (count != attack.getNumberOfAttacks())
	            return false;
	    }
	    return true;
	}

	boolean satisfiesConditionsTemporarily(Solution solution){
	    int i, x, y, count;
	    for (i = 0; i < attacks.size(); i++){
	        Attack attack = attacks.get(i);
	        x = attack.getX();
	        y = attack.getY();
	        count = kingControl(solution, x, y) + bishopControl(solution, x, y) + rookControl(solution, x, y) + queenControl(solution, x, y) + knightControl(solution, x, y);
	        if (count > attack.getNumberOfAttacks())
	            return false;
	    }
	    return true;
	}

}
