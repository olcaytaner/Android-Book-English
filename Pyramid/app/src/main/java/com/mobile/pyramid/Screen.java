package com.mobile.pyramid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Screen extends View{
	public Puzzle puzzle;
	public int cellWidth;
	
    public Screen(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
    }
    
    @Override
    public void onDraw(Canvas canvas) {
        int x1, y1, x2, y2;
        Paint black = new Paint(), blue = new Paint(), text = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.STROKE);
        black.setStrokeWidth(1);
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.STROKE);
        blue.setStrokeWidth(3);
        if (puzzle != null){
            for (int i = 0; i < puzzle.puzzleSize(); i++){
                for (int j = 0; j <= i; j++){
                    x1 = (int) ((puzzle.puzzleSize() - i + 1 + 2 * j) * cellWidth / 2.0);
                    y1 = (i + 1) * cellWidth;
                    x2 = x1 + cellWidth;
                    y2 = y1 + cellWidth;
                    Rect area = new Rect(x1, y1, x2, y2);
                    if (puzzle.playedValue(i) != j){
                        canvas.drawRect(area, black);
                    } else {
                        canvas.drawRect(area, blue);
                    }
                    String number = "" + puzzle.getNumber(i, j);
                    int fontSize = (int) (cellWidth / 1.5);
                    text.setTextSize(fontSize);
                    Rect textSize = new Rect();
                    text.getTextBounds(number, 0, 1, textSize);
                    canvas.drawText(number, area.left + (cellWidth - textSize.width()) / 2, (float) (area.top + cellWidth - textSize.height() / 2), text);
                }
            }
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x1, y1, x2, y2;
        int x = (int) event.getX();
        int y = (int) event.getY();
        for (int i = 0; i < puzzle.puzzleSize(); i++){
            for (int j = 0; j <= i; j++){
                x1 = (int) ((puzzle.puzzleSize() - i + 1 + 2 * j) * cellWidth / 2.0);
                y1 = (i + 1) * cellWidth;
                x2 = x1 + cellWidth;
                y2 = y1 + cellWidth;
                Rect alan = new Rect(x1, y1, x2, y2);
                if (alan.contains(x,  y)){
                	puzzle.play(i, j);
                	invalidate();
                	return true;
                }
            }
        }
    	return true;
    }
    
}
