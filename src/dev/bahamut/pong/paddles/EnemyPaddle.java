package dev.bahamut.pong.paddles;

import dev.bahamut.pong.main.Game;

public class EnemyPaddle extends Paddle {
	
	public static float DEF_ENEMY_SPEED = 15.0f;
	
	public EnemyPaddle(float yStart, float yEnd, Game game) {
		super(yStart, yEnd, game);
		x = game.getDisplay().getCanvas().getWidth() - PADDLE_WIDTH;
	}
	
	@Override
	public void tick() {
		
		if(game.getGameState().getBall().getY() < (yStart + yEnd)/2 - 10 && !isCollidingWallsUp()) {
			yStart -=  DEF_ENEMY_SPEED;
			yEnd -= DEF_ENEMY_SPEED;
		}
		else if (game.getGameState().getBall().getY() > (yStart + yEnd)/2 + 10 && !isCollidingWallsDown()) {
			yStart +=  DEF_ENEMY_SPEED;
			yEnd += DEF_ENEMY_SPEED;
		}
	}
}
