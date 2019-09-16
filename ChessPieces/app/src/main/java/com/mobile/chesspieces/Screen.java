package com.mobile.chesspieces;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Screen extends View{
	
	private Bitmap king, rook, bishop, knight, queen, digits[];
	public int table[];
	public boolean placeOnBoard[];
	public Puzzle puzzle = null;
	public boolean isMoving;
	public int moveX, moveY;
	public int whichPiece;
	public int cellWidth;
	
    Bitmap piecePicture(int piece){
        switch (piece){
            case 0:return king;
            case 1:return queen;
            case 2:
            case 3:return rook;
            case 4:
            case 5:return bishop;
            case 6:
            case 7:return knight;
            default:return king;
        }
    }
    
    public void drawDigit(Canvas canvas, int x, int y, int digit){
        int W, H, w = digits[digit].getWidth(), h = digits[digit].getHeight(), l = cellWidth / 2;
        if (h > w){
            W = w * l / h;
            canvas.drawBitmap(digits[digit], null, new RectF(x + (cellWidth - l) / 2 + l / 2 - W / 2, y + (cellWidth - l) / 2, x + (cellWidth - l) / 2 + l / 2 + W / 2, y + (cellWidth + l) / 2), null);
        } else {
            H = h * l / w;
            canvas.drawBitmap(digits[digit], null, new RectF(x + (cellWidth - l) / 2, y + (cellWidth - l) / 2 + l / 2 - H / 2, x + (cellWidth + l) / 2, y + (cellWidth - l) / 2 + l / 2 + H / 2), null);
        }
    }
	
    @Override
    public void onDraw(Canvas canvas) {
        Paint black = new Paint(), blue = new Paint();
        int i, posx, posy;
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.STROKE);
        black.setTextSize(cellWidth / 2);
        super.onDraw(canvas);
        for (i = 0; i < 9; i++){
            canvas.drawLine((i + 1) * cellWidth, cellWidth, (i + 1) * cellWidth, 9 * cellWidth, black);
            canvas.drawLine(cellWidth, (i + 1) * cellWidth, 9 * cellWidth, (i + 1) * cellWidth, black);
        }
        if (puzzle != null){
            blue.setColor(Color.BLUE);
            blue.setStyle(Paint.Style.STROKE);
            blue.setStrokeWidth(3);
            for (i = 0; i < puzzle.numberOfPossiblePlaces(); i++){
            	Square square = puzzle.possiblePlace(i);
                posx = square.getX();
                posy = square.getY();
                if (table[i] == Puzzle.EMPTY){
                    canvas.drawRect(new Rect((posy + 1) * cellWidth, (posx + 1) * cellWidth, (posy + 2) * cellWidth, (posx + 2) * cellWidth), blue);
                } else {
                    if (isMoving && whichPiece >= 8 && whichPiece - 8 == i){
                        canvas.drawBitmap(piecePicture(table[i]), null, new RectF(moveX, moveY, moveX + cellWidth - 2, moveY + cellWidth - 2), null);
                    } else {
                        canvas.drawBitmap(piecePicture(table[i]), null, new RectF((posy + 1) * cellWidth + 1, (posx + 1) * cellWidth + 1, (posy + 2) * cellWidth - 1, (posx + 2) * cellWidth - 1), null);
                    }
                }
            }
            for (i = 0; i < puzzle.numberOfAttacks(); i++){
            	Attack attack = puzzle.attack(i);
                posx = attack.getX();
                posy = attack.getY();
                drawDigit(canvas, (posy + 1) * cellWidth, (posx + 1) * cellWidth, attack.getNumberOfAttacks());
            }        	
            for (i = 0; i < 8; i++){
                if (!placeOnBoard[i]){
                    if (isMoving && whichPiece < 8 && whichPiece == i){
                        canvas.drawBitmap(piecePicture(i), null, new RectF(moveX, moveY, moveX + cellWidth - 2, moveY + cellWidth - 2), null);
                    } else {
                        canvas.drawBitmap(piecePicture(i), null, new RectF((i + 1) * cellWidth + 1, 9 * cellWidth + 1, (i + 2) * cellWidth - 1, 10 * cellWidth - 1), null);
                    }
                }
            }
        }
    }

    public Screen(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources mRes;
        mRes = context.getResources();
        king = BitmapFactory.decodeResource(mRes, R.drawable.king);
        queen = BitmapFactory.decodeResource(mRes, R.drawable.queen);
        rook = BitmapFactory.decodeResource(mRes, R.drawable.rook);
        bishop = BitmapFactory.decodeResource(mRes, R.drawable.bishop);
        knight = BitmapFactory.decodeResource(mRes, R.drawable.knight);
        digits = new Bitmap[9];
        digits[0] = BitmapFactory.decodeResource(mRes, R.drawable.digit0);
        digits[1] = BitmapFactory.decodeResource(mRes, R.drawable.digit1);
        digits[2] = BitmapFactory.decodeResource(mRes, R.drawable.digit2);
        digits[3] = BitmapFactory.decodeResource(mRes, R.drawable.digit3);
        digits[4] = BitmapFactory.decodeResource(mRes, R.drawable.digit4);
        digits[5] = BitmapFactory.decodeResource(mRes, R.drawable.digit5);
        digits[6] = BitmapFactory.decodeResource(mRes, R.drawable.digit6);
        digits[7] = BitmapFactory.decodeResource(mRes, R.drawable.digit7);
        digits[8] = BitmapFactory.decodeResource(mRes, R.drawable.digit8);
        cellWidth = 130;
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int posx, posy, position;
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (action == MotionEvent.ACTION_DOWN) {
            if (y < 9 * cellWidth){
                posx = x / cellWidth - 1;
                posy = y / cellWidth - 1;
                position = puzzle.possiblePlaceNo(posy, posx);
                if (position != -1 && table[position] != Puzzle.EMPTY){
                    whichPiece = position + 8;
                    isMoving = true;
                }
            } else {
                posx = x / cellWidth - 1;
                if (posx >= 0 && posx < 8 && !placeOnBoard[posx]){
                    whichPiece = posx;
                    isMoving = true;
                }
            }
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            isMoving = false;
            if (y < 9 * cellWidth){
                posx = x / cellWidth - 1;
                posy = y / cellWidth - 1;
                if (posx >= 0 && posx < 8 && posy >= 0 && posy < 8){
                    position = puzzle.possiblePlaceNo(posy, posx);
                    if (position != -1 && table[position] == Puzzle.EMPTY && whichPiece >= 0 && whichPiece < 8 && !placeOnBoard[whichPiece]){
                        placeOnBoard[whichPiece] = true;
                        table[position] = whichPiece;
                        invalidate();
                    }
                }
            } else {
                posx = x / cellWidth - 1;
                if (posx >= 0 && posx < 8 && whichPiece >= 8 && placeOnBoard[table[whichPiece - 8]]){
                    placeOnBoard[table[whichPiece - 8]] = false;
                    table[whichPiece - 8] = Puzzle.EMPTY;
                    invalidate();
                }
            }
            invalidate();
            return true;
        } else if (action == MotionEvent.ACTION_MOVE && isMoving){
            moveX = (int) event.getX() - cellWidth / 2;
            moveY = (int) event.getY() - cellWidth / 2;
            invalidate();
        }
        return false;
    }

}
