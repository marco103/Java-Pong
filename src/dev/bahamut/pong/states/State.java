package dev.bahamut.pong.states;

import java.awt.Graphics;

public abstract class State {

	public abstract void render(Graphics g);
	
	public abstract void tick();
	
}
