package me.kyle1320.platformer;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**	Represents an image displayed on the screen.
	@author Kyle Cutler
	@version 1/1/14
*/
public interface Sprite extends Updatable {
	/**	Draws the sprite on the given Graphics2D object at the given coordinates and with the given width and height.
		@param g The Graphics2D object to draw on
		@param x The x coordinate of the left corner of the rectangle to draw on
		@param y The y coordinate of the upper corner of the rectangle to draw on
		@param width The width to draw the sprite
		@param height The height to draw the sprite
	*/
	public void draw(Graphics2D g, int x, int y, int width, int height);

	/**	Draws the sprite on the given Graphics2D object at the given coordinates and with the given width and height.
		@param g The Graphics2D object to draw on
		@param x The x coordinate of the left corner of the rectangle to draw on
		@param y The y coordinate of the upper corner of the rectangle to draw on
		@param width The width to draw the sprite
		@param height The height to draw the sprite
		@param reversed True if the sprite should be drawn with its horizontal axis flipped
	*/
	public void draw(Graphics2D g, int x, int y, int width, int height, boolean reversed);

	/**	Returns the width of the sprite.
		@return The width of the sprite
	*/
	public int getWidth();

	/**	Returns the height of the sprite.
		@return The height of the sprite
	*/
	public int getHeight();

	/**	Returns the sprite image.
		@return The sprite image
	*/
	public BufferedImage getImage();

	/**	Returns the shape of the sprite.
		@return The shape of the sprite
	*/
	public Shape getShape();

	/**	Returns a copy of the sprite.
		@return A copy of the sprite
	*/
	public Sprite copy();

	/**	Resets the sprite to its default state.
	*/
	public void reset();

}