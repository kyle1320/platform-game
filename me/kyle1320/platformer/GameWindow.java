package me.kyle1320.platformer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.Image;

//import com.apple.eawt.Application;
//import com.apple.eawt.FullScreenUtilities;

/**	The JFrame that displays the game
	@author Kyle Cutler
	@version 12/31/13
*/
public class GameWindow extends JFrame {
	private GamePanel panel;

	/**	Creates a new GameWindow.
	*/
	public GameWindow() {
		super("Platform Game");
		panel = new GamePanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(panel);

		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Platform Game");

		Image icon = SpriteSheet.loadImage("/img/lock.png");
		setIconImage(icon);

		//Application currApp = Application.getApplication();
		//currApp.setDockIconImage(icon);
		//FullScreenUtilities.setWindowCanFullScreen(this, true);
	}

	/**	Displays this GameWindow.
	*/
	public void display() {
		pack();

		Point corner = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		corner.translate(-getWidth()/2, -getHeight()/2);
		setLocation(corner);

		panel.start();
		
		setVisible(true);
	}
}