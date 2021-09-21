package dev.bahamut.pong.states;

public class StateManager {
	
	private static State currentState;
	
	public static State getState() {
		return currentState;
	}
	
	public static void setState(State state) {
		currentState = state;
	}

}
