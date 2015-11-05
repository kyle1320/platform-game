package me.kyle1320.platformer;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**	A Sprite that contains several images played consecutively in order to form an animation
	@author Kyle Cutler
	@version 12/31/2013
*/
public class AnimatedSprite implements Sprite {
	private Sprite[] images;
	private int currImage;

	// used for timing; the number of updates required between updates.
	private int updateSteps;
	private int currStep;
	
	private boolean running;

	/**	Creates a new AnimatedSprite with the given fps and image set.
		@param fps The number of image updates that should occur each second (approximate, actual fps is limited by update frequency)
		@param images The array (varargs list) of images this animation consists of
	*/
	public AnimatedSprite(int fps, Sprite... images) {
		this.images = images;
		this.currImage = 0;

		this.updateSteps = 1000 / (fps * UPDATE_DELAY);
		this.currStep = 0;

		this.running = false;
	}

	/**	Private constructor used for copying AnimatedSprites.
		@param currImage The current image index
		@param updateSteps The number of steps between updates
		@param currStep The number of steps since the last update
		@param images The array of Sprites this animation consists of
	*/
	private AnimatedSprite(int currImage, int updateSteps, int currStep, Sprite[] images) {
		this.images = images;
		this.currImage = currImage;

		this.updateSteps = updateSteps;
		this.currStep = currStep;

		this.running = true;
	}

	/**	Updates the number of steps and advances the animation if necessary.
	*/
	public void update() {
		if (running) {
			currStep++;

			if (currStep >= updateSteps) {
				currStep = 0;
				currImage++;

				if (currImage >= images.length)
					currImage = 0;
			}
		}
	}

	/**	Pauses the animation.
	*/
	public void start() {
		running = true;
	}

	/**	Plays the animation
	*/
	public void stop() {
		running = false;
	}

	/**	Resets the animation to its default state, that is the first image in the animation with zero time since the last update.
	*/
	public void reset() {
		currStep = 0;
		currImage = 0;
		running = true;
	}

	/**	Draws the current image on the given Graphics2D object at the given coordinates and with the given width and height.
		@param g The Graphics2D object to draw on
		@param x The x coordinate of the left corner of the rectangle to draw on
		@param y The y coordinate of the upper corner of the rectangle to draw on
		@param width The width to draw the image
		@param height The height to draw the image
	*/
	public void draw(Graphics2D g, int x, int y, int width, int height) {
		draw(g, x, y, width, height, false);
	}

	/**	Draws the current image on the given Graphics2D object at the given coordinates and with the given width and height.
		@param g The Graphics2D object to draw on
		@param x The x coordinate of the left corner of the rectangle to draw on
		@param y The y coordinate of the upper corner of the rectangle to draw on
		@param width The width to draw the image
		@param height The height to draw the image
		@param reversed True if the image should be drawn with its horizontal axis flipped
	*/
	public void draw(Graphics2D g, int x, int y, int width, int height, boolean reversed) {
		images[currImage].draw(g, x, y, width, height, reversed);
	}

	/**	Returns the width of the current image
		@return The width of the image currently displayed by this animation
	*/
	public int getWidth() {
		return images[currImage].getWidth();
	}

	/**	Returns the height of the current image
		@return The height of the image currently displayed by this animation
	*/
	public int getHeight() {
		return images[currImage].getHeight();
	}

	/**	Returns the current image
		@return The image currently displayed by this animation
	*/
	public BufferedImage getImage() {
		return images[currImage].getImage();
	}

	/**	Returns the shape of the current image
		@return The shape of the image currently displayed by this animation
	*/
	public Shape getShape() {
		return images[currImage].getShape();
	}

	/**	Returns a copy of this animation
		@return A copy of this animation with all values the same.
	*/
	public Sprite copy() {
		return new AnimatedSprite(currImage, updateSteps, currStep, images);
	}
}