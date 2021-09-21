package dev.bahamut.pong.main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.bahamut.pong.display.Display;
import dev.bahamut.pong.input.KeyManager;
import dev.bahamut.pong.states.GameState;
import dev.bahamut.pong.states.StateManager;

public class Game implements Runnable{
	
	Display display;
	
	Thread gameThread;
	boolean running;
	
	BufferStrategy bs;
	Graphics g;
	
	KeyManager keyManager;
	
	GameState gameState;
	
	public Game() {
		display = new Display();
		keyManager = new KeyManager();
		display.getFrame().addKeyListener(keyManager);;
		start();
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public Display getDisplay() {
		return display;
	}
	
	public synchronized void start() {
		if(running)
			return;
		
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public synchronized void stop() {
		if(!running)
			return;
		
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		gameState = new GameState(this);
		
		StateManager.setState(gameState);
	}

	public void tick() {
		keyManager.tick();
		
		if(StateManager.getState() != null)
			StateManager.getState().tick();
	}
	
	public void render() {
		
		bs = display.getCanvas().getBufferStrategy();
		
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		g.setFont(new Font("Helvetica", Font.PLAIN, 50));
		g.clearRect(0, 0, display.getCanvas().getWidth(), display.getCanvas().getHeight());
		
		//RENDER
		if (StateManager.getState() != null) {
			StateManager.getState().render(g);
		}
		//RENDER END
		
		bs.show();
		g.dispose();
	
	}
	
	@Override
	public void run() {
		
		int fps = 60;
		double timePerTick = 1000000000/fps;
		
		long now;
		long lastTime;
		double delta = 0;
		
		
		init();
		
		while(running) {
			
			lastTime = System.nanoTime();
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			
			if(delta >= 1) {
				tick();
				render();
				delta--;
			}
		}
		
	}

}
