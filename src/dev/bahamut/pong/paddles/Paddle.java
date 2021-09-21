package dev.bahamut.pong.paddles;

import java.awt.Color;
import java.awt.Graphics;

import dev.bahamut.pong.main.Game;

public class Paddle {
	
	public static int PADDLE_WIDTH = 30;
	public static float DEFSPEED = 15.0f;
	
	Game game;
	
	protected float yStart, yEnd;
	protected float x;
	
	protected float yMove;
	
	public Paddle(float yStart, float yEnd, Game game) {
		this.yStart = yStart;
		this.yEnd = yEnd;
		this.game = game;
	}
	
	public float getYStart() {
		return yStart;
	}
	
	public float getYEnd() {
		return yEnd;
	}
	
	public void move() {
		yStart += yMove;
		yEnd += yMove;
	}
	
	public boolean isCollidingWallsUp() {
		
		if(yStart <= 0) return true;
		return false;
		
	}
	
	public boolean isCollidingWallsDown() {
		
		if(yEnd >= game.getDisplay().getCanvas().getHeight()) return true;
		return false;
		
	}
	
	public void tick() {
		if(game.getKeyManager().up && !isCollidingWallsUp())
			yMove = -DEFSPEED;
		else if(game.getKeyManager().down && !isCollidingWallsDown())
			yMove = DEFSPEED;
		else
			yMove = 0;
		
		move();
	}
	
	public void render(Graphics g) {	
		
		g.setColor(Color.white);
		
		g.fillRect((int)x, (int)yStart, PADDLE_WIDTH, (int)(yEnd - yStart));
		
	}
	

}
