package com.mobile.brickbreaker;

import java.util.ArrayList;

public class Parameter {

	private int numberOfLives;
	private Paddle paddle;
	private ArrayList<DroppingBrick> droppingBricks;
	private ArrayList<Ball> balls;
	private Level level;
	private int points;
	private int screenWidth;
	private int screenHeight;

	public void newLevel(Level level){
	    this.level = level;
	    paddle = new Paddle(screenWidth, screenHeight);
	    balls = new ArrayList<Ball>();
	    balls.add(new Ball(paddle.place().left, paddle.place().top, screenWidth));
	    droppingBricks = new ArrayList<DroppingBrick>();
	}

	public Parameter(int width, int height, Level level){
	    screenWidth = width;
	    screenHeight = height;
	    numberOfLives = 3;
	    points = 0;
	    newLevel(level);
	}
	
	public Level level(){
		return level;
	}
	
	public Paddle paddle(){
		return paddle;
	}
	
	public int points(){
		return points;
	}
	
	public int screenWidth(){
		return screenWidth;
	}
	
	public int screenHeight(){
		return screenHeight;
	}
	
	public int numberOfLives(){
		return numberOfLives;
	}

	public int numberOfBalls(){
	    return balls.size();
	}

	public Ball ball(int position){
	    return balls.get(position);
	}

	public int numberOfDroppingBricks(){
	    return droppingBricks.size();
	}

	DroppingBrick droppingBrick(int position){
	    return droppingBricks.get(position);
	}

	public boolean ballContactsWithScreenBounds(Ball ball){
	    Ball newBall;
	    if (ball.contactsWithScreenBounds(screenWidth, screenHeight)){
	        balls.remove(ball);
	        if (balls.size() == 0){
	            numberOfLives--;
	            if (numberOfLives > 0){
	                newBall = new Ball(paddle.place().left, paddle.place().top, screenWidth);
	                balls.add(newBall);
	            } else {
	                return false;
	            }
	        }
	    }
	    return true;
	}

	public boolean ballContactsWithBrick(Ball ball){
	    Brick brick;
	    DroppingBrick droppingBrick;
	    for (int i = 0; i < level.row(); i++){
	        for (int j = 0; j < level.column(); j++){
	            brick = level.brick(i, j);
	            if (!brick.isBroken() && ball.contactsWithBrick(brick)){
	                brick.hit();
	                if (brick.isBroken()){
	                    if (brick.type() != BrickType.HARD)
	                        points += 10;
	                    else
	                        points += 20;
	                    if (brick.type() != BrickType.NORMAL && brick.type() != BrickType.HARD){
	                        droppingBrick = new DroppingBrick(brick.type(), brick.place(), screenHeight);
	                        droppingBricks.add(droppingBrick);
	                    }
	                    if (level.isFinished())
	                        return true;
	                }
	            }
	        }
	    }
	    return false;
	}

	public boolean ballContactsWithPaddle(Ball ball){
	    return ball.contactsWithPaddle(paddle);
	}

	public boolean contactsWithPaddle(int x, int y){
	    if (paddle.place().contains(x, y)){
	        return true;
	    } else {
	        return false;
	    }
	}

	public void paddleSetNewPosition(int x){
	    if (x > 0 && x < screenWidth - paddle.place().width()){
	        paddle.setNewPosition(x);
	    }
	}

	public boolean droppingBrickContactsWithPaddle(){
	    Ball newBall;
	    for (DroppingBrick droppingBrick : droppingBricks){
	        if (droppingBrick.contactsWithPaddle(paddle)){
	            switch (droppingBrick.type()){
	                case FASTER:
	                    for (Ball ball : balls){
	                        ball.speedUp();
	                    }
	                    break;
	                case SLOWER:
	                    for (Ball ball : balls){
	                        ball.slowDown();
	                    }
	                    break;
	                case LARGER:
	                    paddle.grow();
	                    break;
	                case SMALLER:
	                    paddle.shrink();
	                    break;
	                case LIFE:
	                    numberOfLives++;
	                    break;
	                case DEATH:
	                    numberOfLives--;
	                    if (numberOfLives == 0)
	                        return false;
	                    break;
	                case MULTIPLE:
	                    newBall = new Ball(paddle.place().left, paddle.place().bottom, screenWidth);
	                    balls.add(newBall);
	                    break;
	                default:
	                    break;
	            }
	            droppingBricks.remove(droppingBrick);
	            break;
	        }
	    }
	    return true;
	}

}
