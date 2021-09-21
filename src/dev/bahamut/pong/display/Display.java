package dev.bahamut.pong.display;

import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JFrame;

public class Display {

	JFrame frame;
	Canvas canvas;
	
	public Display() {
		frame = new JFrame();
		frame.setLocationRelativeTo(null);
		frame.setSize(1600, 900);
		frame.setTitle("Pong");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas = new Canvas();
		canvas.setSize(frame.getSize());
		canvas.setMinimumSize(frame.getSize());
		canvas.setMaximumSize(frame.getSize());
		canvas.setPreferredSize(frame.getSize());
		canvas.setBackground(Color.black);
		
		frame.add(canvas);
		frame.setVisible(true);
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
