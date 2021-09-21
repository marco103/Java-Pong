package dev.bahamut.pong.states;

import java.awt.Graphics;

import dev.bahamut.pong.ball.Ball;
import dev.bahamut.pong.main.Game;
import dev.bahamut.pong.paddles.EnemyPaddle;
import dev.bahamut.pong.paddles.Paddle;

public class GameState extends State{
	
	Game game;
	
	int yourPoints = 0, enemyPoints = 0;
	
	Paddle yourPaddle;
	EnemyPaddle enemyPaddle;
	Ball ball;
	
	public GameState(Game game) {
		
		yourPaddle = new Paddle(game.getDisplay().getCanvas().getHeight()/2 - Paddle.PADDLE_WIDTH, game.getDisplay().getCanvas().getHeight()/2 + 2 * Paddle.PADDLE_WIDTH, game);
		enemyPaddle = new EnemyPaddle(game.getDisplay().getCanvas().getHeight()/2 - Paddle.PADDLE_WIDTH, game.getDisplay().getCanvas().getHeight()/2 + 2 * Paddle.PADDLE_WIDTH, game);
		ball = new Ball(game.getDisplay().getCanvas().getWidth() / 2 - Ball.BALL_RADIUS, game.getDisplay().getCanvas().getHeight() / 2 - Ball.BALL_RADIUS, Ball.INITIAL_SPEED, game);
		this.game = game;
		
	}
	
	public Paddle getPaddle() {
		return yourPaddle;
	}
	
	public Paddle getEnemyPaddle() {
		return enemyPaddle;
	}
	
	public Ball getBall() {
		return ball;
	}

	@Override
	public void render(Graphics g) {
		yourPaddle.render(g);
		enemyPaddle.render(g);
		ball.render(g);
		g.drawString(String.valueOf(yourPoints) + " - " + String.valueOf(enemyPoints), game.getDisplay().getCanvas().getWidth()/2 - 30, 50);
	}

	@Override
	public void tick() {
		yourPaddle.tick();
		enemyPaddle.tick();
		ball.tick();
		
		if(ball.getX() <= 0) {
			enemyPoints++;
			ball.reset(game.getDisplay().getCanvas().getWidth() / 2 - Ball.BALL_RADIUS, game.getDisplay().getCanvas().getHeight() / 2 - Ball.BALL_RADIUS, Ball.INITIAL_SPEED);
		}
		else if(ball.getX() >= game.getDisplay().getCanvas().getWidth()) {
			yourPoints++;
			ball.reset(game.getDisplay().getCanvas().getWidth() / 2 - Ball.BALL_RADIUS, game.getDisplay().getCanvas().getHeight() / 2 - Ball.BALL_RADIUS, Ball.INITIAL_SPEED);
		}
	}
	
}
