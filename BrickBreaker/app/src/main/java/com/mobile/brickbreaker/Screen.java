package com.mobile.brickbreaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Screen extends View{
	
	public Parameter parameter = null;
	private int difference;
	private boolean moveStarted = false;
	
	Paint brickColor(BrickType type){
		Paint paint = new Paint();
	    switch (type){
	        case NORMAL:
	        	paint.setColor(Color.BLACK);
	            break;
	        case HARD:
	        	paint.setColor(Color.rgb(165, 42, 42));
	            break;
	        case FASTER:
	        	paint.setColor(Color.BLUE);
	            break;
	        case SLOWER:
	        	paint.setColor(Color.CYAN);
	            break;
	        case LARGER:
	        	paint.setColor(Color.rgb(255, 165, 0));
	            break;
	        case SMALLER:
	        	paint.setColor(Color.GREEN);
	            break;
	        case LIFE:
	        	paint.setColor(Color.MAGENTA);
	            break;
	        case DEATH:
	        	paint.setColor(Color.RED);
	            break;
	        case MULTIPLE:
	        	paint.setColor(Color.YELLOW);
	            break;
	    }
	    paint.setStyle(Style.FILL);
	    return paint;
	}

    @Override
    public void onDraw(Canvas canvas) {
        Level level;
        Brick brickToBeDrawn;
        Paddle paddle;
        Ball ball;
        Paint brickColor, border, paddleColor, ballColor, text;
        DroppingBrick droppingBrick;
        int fontSize;
        Rect textSize;
        if (parameter == null){
        	return;
        }
        border = new Paint();
        border.setStyle(Style.STROKE);
        border.setColor(Color.GRAY);
        level = parameter.level();
        for (int i = 0; i < level.row(); i++){
            for (int j = 0; j < level.column(); j++){
                brickToBeDrawn = level.brick(i, j);
                if (!brickToBeDrawn.isBroken()){
                	brickColor = brickColor(brickToBeDrawn.type());
                    canvas.drawRect(brickToBeDrawn.place(), brickColor);
                    canvas.drawRect(brickToBeDrawn.place(), border);
                }
            }
        }
        paddle = parameter.paddle();
        paddleColor = new Paint();
        paddleColor.setStyle(Style.FILL);
        paddleColor.setColor(Color.DKGRAY);
        canvas.drawRect(paddle.place(), paddleColor);
        canvas.drawRect(paddle.place(), border);
        ballColor = new Paint();
        ballColor.setStyle(Style.FILL);
        ballColor.setColor(Color.rgb(128, 0, 128));
        for (int i = 0; i < parameter.numberOfBalls(); i++){
            ball = parameter.ball(i);
            RectF alan = new RectF(ball.center().x - ball.radius(), ball.center().y - ball.radius(), ball.center().x + ball.radius(), ball.center().y + ball.radius());
            canvas.drawOval(alan, ballColor);
        }
        for (int i = 0; i < parameter.numberOfDroppingBricks(); i++){
            droppingBrick = parameter.droppingBrick(i);
            brickColor = brickColor(droppingBrick.type());
            canvas.drawOval(new RectF(droppingBrick.place()), brickColor);
        }
        ball = parameter.ball(0);
        for (int i = 0; i < parameter.numberOfLives(); i++){
            RectF alan = new RectF((int)(5 + i * 2 * ball.radius()), (int) (parameter.paddle().place().bottom + 1.4 * parameter.paddle().place().height()), (int) (5 + i * 2 * ball.radius() + 1.6 * ball.radius()), (int) (parameter.paddle().place().bottom + 1.4 * parameter.paddle().place().height() + 1.6 * ball.radius()));
            canvas.drawOval(alan, ballColor);
        }
        String points = "" + parameter.points();
        fontSize = parameter.screenWidth() * 12 / 300;
        text = new Paint();
        text.setTextSize(fontSize);
        text.setColor(Color.rgb(128, 0, 128));
        textSize = new Rect();
        text.getTextBounds(points, 0, points.length(), textSize);
        canvas.drawText(points, parameter.screenWidth() - textSize.width(), parameter.screenHeight() - textSize.height(), text);
        String levelText = "Level " + (parameter.level().levelNo() + 1);
        fontSize = parameter.screenWidth() * 12 / 150;
        text.setTextSize(fontSize);
        textSize = new Rect();
        text.getTextBounds(levelText, 0, levelText.length(), textSize);
        canvas.drawText(levelText, parameter.screenWidth() / 2 - textSize.width() / 2, parameter.screenHeight() / 2 - textSize.height() / 2, text);
    }

    public Screen(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (action == MotionEvent.ACTION_DOWN) {
            if (parameter.contactsWithPaddle(x, y)){
                difference = x - parameter.paddle().place().left;
                moveStarted = true;
            } else {
                moveStarted = false;
            }
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            if (moveStarted){
                parameter.paddleSetNewPosition(x - difference);
                invalidate();
            }
            moveStarted = false;
            return true;
        } else if (action == MotionEvent.ACTION_MOVE){
            if (moveStarted){
                parameter.paddleSetNewPosition(x - difference);
                invalidate();
            }
        }
        return false;
    }

}
