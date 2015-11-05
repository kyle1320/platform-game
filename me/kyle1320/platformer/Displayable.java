package me.kyle1320.platformer;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import java.awt.Graphics2D;

/**	Represents an object that can be drawn on the screen and interacted with.
	@author Kyle Cutler
	@version 10/31/13
*/
public abstract class Displayable implements Updatable, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	/**	Should reset the object to its original state
	*/
	public abstract void reset();

	// empty implementations so that not all are required when this class is extended.

	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}

	public void mouseWheelMoved(MouseWheelEvent e) {}

	/**	Draws the object on the given Graphics2D object, with the given screen width and height
		@param g The Graphics2D object on which to draw
		@param width The width to draw the display
		@param height The height to draw the display
	*/
	public abstract void draw(Graphics2D g, int width, int height);
}