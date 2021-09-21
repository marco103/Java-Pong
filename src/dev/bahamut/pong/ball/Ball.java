package dev.bahamut.pong.ball;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import dev.bahamut.pong.main.Game;
import dev.bahamut.pong.paddles.Paddle;

import java.lang.Math;

import javax.sound.sampled.*;

public class Ball {
	
	Game game;

	public static float INITIAL_SPEED = 12.0f;
	public static int BALL_RADIUS = 5;
	
	private SecureRandom random;
	
	private float x, y;
	private float speed, speedX, speedY;
	private float angle;
	private int towardsEnemy;
	
	private AudioInputStream in;
	private Clip clip;
	
	public Ball(float x, float y, float speed, Game game) {
		try {
			in = AudioSystem.getAudioInputStream(new File("C:\\Users\\marco\\eclipse-workspacenew\\Pong\\sounds\\pong.wav"));
			clip = AudioSystem.getClip();
			clip.open(in);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.game = game;
		random = new SecureRandom();
		angle = random.nextFloat() * (float)Math.PI/4;
		
		if(random.nextInt(2) == 1)
			angle = -angle;
		
		if(random.nextInt(2) == 0)
			towardsEnemy = 1;
		else
			towardsEnemy = -1;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillOval((int)x, (int)y, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
	}
	
	public boolean collidesWithLeftPaddle() {
		if (x <= Paddle.PADDLE_WIDTH &&  y <= game.getGameState().getPaddle().getYEnd() && y >= game.getGameState().getPaddle().getYStart()) return true;
		return false;
	}
	
	public boolean collidesWithRightPaddle() {
		if (x >= game.getDisplay().getCanvas().getWidth() - Paddle.PADDLE_WIDTH &&  y <= game.getGameState().getEnemyPaddle().getYEnd() && y >= game.getGameState().getEnemyPaddle().getYStart()) return true;
		return false;
	}
	
	public boolean collidesWithWalls() {
		if (y <= 0 || y >= game.getDisplay().getCanvas().getHeight()) return true;
		return false;
	}
	
	public void calculateAngle(float yStart, float yEnd, float y) {
		
		float halfLength = Math.abs(yStart - yEnd)/2;
		float median = (yStart + yEnd)/2;
		float distanceFromCenter = y - median;
		
		if (angle + distanceFromCenter/halfLength * (float)Math.PI/4 < (float)Math.PI/4 && angle + distanceFromCenter/halfLength * (float)Math.PI/4 > -(float)Math.PI/4)
			angle += distanceFromCenter/halfLength * (float)Math.PI/4;
		else if(angle + distanceFromCenter/halfLength * (float)Math.PI/4 > (float)Math.PI/4)
			angle = (float)Math.PI/4;
		else if (angle + distanceFromCenter/halfLength * (float)Math.PI/4 < -(float)Math.PI/4)
			angle = -(float)Math.PI/4;
	}
	
	public void tick() {
		
		if(collidesWithLeftPaddle()) {
			try {
				clip.setFramePosition(0);
				clip.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			towardsEnemy *= -1;
			calculateAngle(game.getGameState().getPaddle().getYStart(), game.getGameState().getPaddle().getYEnd(), y);
			speedUp(1.0f);
			x = Paddle.PADDLE_WIDTH + 1;
		}
		
		if(collidesWithRightPaddle()) {
			try {
				clip.setFramePosition(0);
				clip.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			towardsEnemy *= -1;
			calculateAngle(game.getGameState().getEnemyPaddle().getYStart(), game.getGameState().getEnemyPaddle().getYEnd(), y);
			speedUp(1.0f);
			x =	game.getDisplay().getCanvas().getWidth() - Paddle.PADDLE_WIDTH - 1;
		}
		
		if(collidesWithWalls())
			angle = -angle;
		
		speedX = towardsEnemy * speed * (float)Math.cos(angle);
		speedY = speed * (float)Math.sin(angle);
		
		move();
			
	}
	
	public void move() {
		x += speedX;
		y += speedY;
	}
	
	public void speedUp(float speedPlus) {
		speed += speedPlus;
	}
	
	public void reset(float x, float y, float speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		
		angle = random.nextFloat() * (float)Math.PI/4;
		if(random.nextInt(2) == 1)
			angle = -angle;
		
		if(random.nextInt(2) == 0)
			towardsEnemy = 1;
		else
			towardsEnemy = -1;
	}
}
