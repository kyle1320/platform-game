package me.kyle1320.platformer;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import java.io.File;
import javax.imageio.ImageIO;

/**	Used to draw background images behind the level.
	@author Kyle Cutler
	@version 12/31/13
*/
public enum Background {
	normal(GameSprite.background_normal),
	castle(GameSprite.background_castle);

	// the image to draw
	private BufferedImage image;

	/**	Creates a new Background with the given sprite.
		@param sprite The GameSprite from which to retrieve the image to draw
	*/
	private Background(GameSprite sprite) {
		this.image = sprite.getSprite().getImage();
	}

	/**	Draws the background image on the given Graphics2D with the given scrolling amounts, screen width, and screen height. The image is tiled until it covers the entire screen.
		@param g The Graphics2D object on which to draw
		@param scrollX The x coordinate the background is scrolled by
		@param scrollY The y coordinate the background is scrolled by
		@param width The screen width
		@param height The screen height
	*/
	public void draw(Graphics2D g, int scrollX, int scrollY, int width, int height) {
		if (image == null) {
			System.out.println("Null background image!");
			return;
		}

		int x, y = (-image.getHeight()+(scrollY % image.getHeight())) % image.getHeight();

		do {
			x = -scrollX % image.getWidth();

			do {
				g.drawImage(image, x, y, null);
				x += image.getWidth();
			} while (x < width);

			y += image.getHeight();
		} while (y < height);
	}
}