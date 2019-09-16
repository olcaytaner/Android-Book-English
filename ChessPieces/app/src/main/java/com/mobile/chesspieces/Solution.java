package com.mobile.chesspieces;

import java.util.ArrayList;

public class Solution {
	
	private ArrayList<Piece> pieces;
	private Piece allPieces[] = {new Piece("king"), new Piece("queen"), new Piece("rook1"), new Piece("rook2"),
			new Piece("bishop1"), new Piece("bishop2"), new Piece("knight1"), new Piece("knight2")};
	
	public Solution(){
		pieces = new ArrayList<Piece>();
	}
	
	String pieceName(int position){
	    if (position >= 0 && position < pieces.size())
	        return pieces.get(position).getName();
	    else
	        return "empty";
	}

	int pieceNo(int position){
	    for (int i = 0; i < allPieces.length; i++){
	        if (pieces.get(position).getName().equalsIgnoreCase(allPieces[i].getName())){
	            return i;
	        }
	    }
	    return -1;
	}

	int numberOfPieces(){
	    return pieces.size();
	}

	void addPiece(Piece piece){
	    pieces.add(piece);
	}

	void addPieceWithPieceNo(int pieceNo){
	    if (pieceNo >= 0 && pieceNo < 8){
	        pieces.add(allPieces[pieceNo]);
	    }
	}

	void removePiece(){
	    pieces.remove(pieces.size() - 1);
	}

	ArrayList<Piece> generateCandidates(){
	    boolean found;
	    ArrayList<Piece> candidate = new ArrayList<Piece>();
	    for (Piece possiblePiece : allPieces){
	        found = false;
	        for (Piece piece : pieces){
	            if (piece.getName().equalsIgnoreCase(possiblePiece.getName())){
	                found = true;
	                break;
	            }
	        }
	        if (!found){
	            candidate.add(possiblePiece);
	        }
	    }
	    return candidate;
	}

}
