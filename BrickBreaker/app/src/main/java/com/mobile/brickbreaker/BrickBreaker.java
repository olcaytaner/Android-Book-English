package com.mobile.brickbreaker;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.app.Activity;

public class BrickBreaker extends Activity {
	
	private Game game;
	private int levelNo;
	private int screenWidth;
	private int screenHeight;
	private Screen screen;
	private Timer timer;
	
	private void newLevel(){
	    levelNo++;
	    screen.parameter.newLevel(game.level(levelNo));
	}
	
    final Handler h = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Ball ball;
            DroppingBrick droppingBrick;
            for (int i = 0; i < screen.parameter.numberOfBalls(); i++){
                ball = screen.parameter.ball(i);
                ball.move();
                screen.parameter.ballContactsWithPaddle(ball);
                if (screen.parameter.ballContactsWithBrick(ball)){
                    newLevel();
                    return true;
                }
                if (!screen.parameter.ballContactsWithScreenBounds(ball)){
                    timer.cancel();
                }
            }
            for (int i = 0; i < screen.parameter.numberOfDroppingBricks(); i++){
                droppingBrick = screen.parameter.droppingBrick(i);
                droppingBrick.move();
                if (!screen.parameter.droppingBrickContactsWithPaddle())
                    timer.cancel();
            }
            screen.invalidate();
            return false;
        }
    });

    class TimerClass extends TimerTask {
        @Override
        public void run() {
            h.sendEmptyMessage(0);
        }
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Parameter gameParameter;
		setContentView(R.layout.main_screen);
        screen = (Screen) findViewById(R.id.screen);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels - 130;
        levelNo = 0;
        game = new Game(this.getApplicationContext(), screenWidth);
        gameParameter = new Parameter(screenWidth, screenHeight, game.level(0));
        screen.parameter = gameParameter;
        timer = new Timer();
        timer.schedule(new TimerClass(), 0, 20);
	}

}
