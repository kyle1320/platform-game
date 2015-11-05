package me.kyle1320.platformer;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import java.util.HashSet;
import java.util.*;

/**	A static class that holds information about mouse position, mouse state, and keyboard state.
	@author Kyle Cutler
	@version 1/1/14
*/
public final class Input implements KeyListener, MouseListener, MouseMotionListener {
	// These don't need to be kept track of more than once
	private static HashSet<Integer> pressedKeys = new HashSet<Integer>();
	private static int mouseX = 0, mouseY = 0;
	private static boolean mousePressed = false;
	private static boolean mouseOnScreen = false;

	/**	Updates information on pressed keys based on the event.
		@param e The key event
	*/
	public void keyPressed(KeyEvent e) {
		pressedKeys.add(e.getKeyCode());
	}

	/**	Updates information on pressed keys based on the event.
		@param e The key event
	*/
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
	}

	/**	Updates information on mouse state based on the event.
		@param e The mouse event
	*/
	public void mousePressed(MouseEvent e) {
		mousePressed = true;
	}

	/**	Updates information on mouse state based on the event.
		@param e The mouse event
	*/
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
	}

	/**	Updates information on mouse position based on the event.
		@param e The mouse event
	*/
	public void mouseMoved(MouseEvent e) {
		mouseOnScreen = true;
		mouseX = e.getX();
		mouseY = e.getY();
	}

	/**	Updates information on mouse position based on the event.
		@param e The mouse event
	*/
	public void mouseDragged(MouseEvent e) {
		mouseOnScreen = true;
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void keyTyped(KeyEvent e) {}
	public void mouseClicked(MouseEvent e) {}

	/**	Updates information on mouse state based on the event.
		@param e The mouse event
	*/
	public void mouseEntered(MouseEvent e) {
		mouseOnScreen = true;
	}

	/**	Updates information on mouse state based on the event.
		@param e The mouse event
	*/
	public void mouseExited(MouseEvent e) {
		mouseOnScreen = false;
	}

	/**	Returns true if the given key is pressed.
		@param keyCode The key code of the test key as defined in KeyEvent
		@return True if the given key is pressed
	*/
	public static final boolean isKeyPressed(int keyCode) {
		return pressedKeys.contains(keyCode);
	}

	/**	Returns the current mouse x coordinate.
		@return The current mouse x coordinate
	*/
	public static final int getMouseX() {
		return mouseX;
	}

	/**	Returns the current mouse y coordinate.
		@return The current mouse y coordinate
	*/
	public static final int getMouseY() {
		return mouseY;
	}

	/**	Returns true if a mouse button is pressed
		@return True if a mouse button is pressed
	*/
	public static final boolean isMousePressed() {
		return mousePressed;
	}

	/**	Returns true if the mouse is over a window this listener is added to.
		@return True if the mouse is over a window this listener is added to
	*/
	public static final boolean isMouseOnScreen() {
		return mouseOnScreen;
	}
}