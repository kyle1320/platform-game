package me.kyle1320.platformer;

import java.awt.image.BufferedImage;
import java.awt.*;

import java.io.File;
import javax.imageio.ImageIO;

/**	An enumeration of images from which sprites are to be cut.
	@author Kyle Cutler
	@version 1/1/14
*/
public enum SpriteSheet {
	tiles("/img/tiles.png"),
	lava("/img/lava_ani.png"),
	water("/img/water_ani.png"),
	items("/img/items.png"),
	hud("/img/hud.png"),
	extras("/img/extras.png"),
	player1("/img/player1.png"),
	bg_normal("/img/bg.png"),
	bg_castle("/img/bg_castle.png");

	private BufferedImage image;

	/**	Creates a new SpriteSheet from the given file name.
		@param imageName The path to the image of this SpriteSheet
	*/
	private SpriteSheet(String imageName) {
		image = loadImage(imageName);
	}

	public static final BufferedImage loadImage(String name) {
		try {
			BufferedImage read = ImageIO.read(SpriteSheet.class.getResourceAsStream(name));

			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice device = env.getDefaultScreenDevice();
			GraphicsConfiguration config = device.getDefaultConfiguration();
			BufferedImage image = config.createCompatibleImage(read.getWidth(), read.getHeight(), Transparency.TRANSLUCENT);

			// gets an image type that is easily written to the screen by the OS.

			image.createGraphics().drawImage(read, 0, 0, null);
			return image;
		} catch (Exception e) {
			System.out.println("Could not load sprite sheet image from " + name);
		}

		return null;
	}

	/**	Returns this SpriteSheet's full image.
		@return This SpriteSheet's full image
	*/
	public BufferedImage getImage() {
		return image;
	}

	/**	Cuts an image from this spritesheet and returns it.
		@param startx The lower x coordinate of the image to cut
		@param starty The lower y coordinate of the image to cut
		@param width The width of the image to cut
		@param height The height of the image to cut
		@return The cut BufferedImage.
	*/
	public BufferedImage getSubImage(int startx, int starty, int width, int height) {
		if (image == null)
			return null;

		return image.getSubimage(startx, starty, width, height);
	}
}