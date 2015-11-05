package me.kyle1320.platformer;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class StaticSprite implements Sprite {
	private BufferedImage image;
	private Shape shape;

	/**	Creates a StaticSprite with an empty image, a shape of SpriteShape.fullSquare, and a width and height of 1.
	*/
	public StaticSprite() {
		this.image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		this.shape = SpriteShape.fullSquare.getShape();
	}

	/**	Creates a StaticSprite from the given sprite sheet. The full sheet is used, and the shape is SpriteShape.fullSquare.
		@param sheet The SpriteSheet to use.
	*/
	public StaticSprite(SpriteSheet sheet) {
		this.image = sheet.getImage();
		this.shape = SpriteShape.fullSquare.getShape();
	}

	/**	Creates a StaticSprite from the given sheet and coordinates to cut an image from. The shape is SpriteShape.fullSquare.
		@param sheet The sheet to cut the image from
		@param x The lower x coordinate of the image to cut
		@param y The lower y coordinate of the image to cut
		@param width The width of the image to cut
		@param height The height of the image to cut
	*/
	public StaticSprite(SpriteSheet sheet, int x, int y, int width, int height) {
		this.image = sheet.getSubImage(x, y, width, height);
		this.shape = SpriteShape.fullSquare.getShape();
	}

	/**	Creates a StaticSprite from the given shape, sheet, and coordinates to cut an image from.
		@param sheet The sheet to cut the image from
		@param x The lower x coordinate of the image to cut
		@param y The lower y coordinate of the image to cut
		@param width The width of the image to cut
		@param height The height of the image to cut
		@param shape The shape to give this StaticSprite
	*/
	public StaticSprite(SpriteSheet sheet, int x, int y, int width, int height, SpriteShape shape) {
		this.image = sheet.getSubImage(x, y, width, height);
		this.shape = shape.getShape();
	}

	/*public StaticSprite(SpriteSheet sheet, int x, int y, int width, int height, int padding_top, int padding_bottom, int padding_left, int padding_right) {
		BufferedImage sub = sheet.getSubImage(x, y, width, height);

		this.image = new BufferedImage(sub.getWidth() + padding_left + padding_right, sub.getHeight() + padding_top + padding_bottom);
		this.image.createGraphics().drawImage(sub, padding_left, padding_top);
	}*/

	public StaticSprite(BufferedImage image, Shape shape) {
		this.image = image;
		this.shape = shape;
	}

	/**	Doesn't do anything.
	*/
	public void update() {
	}

	/**	Draws this sprite on the given Graphics2D object at the given coordinates and with the given width and height.
		@param g The Graphics2D object to draw on
		@param x The x coordinate of the left corner of the rectangle to draw on
		@param y The y coordinate of the upper corner of the rectangle to draw on
		@param width The width to draw this sprite
		@param height The height to draw this sprite
	*/
	public void draw(Graphics2D g, int x, int y, int width, int height) {
		draw(g, x, y, width, height, false);
	}

	/**	Draws this sprite on the given Graphics2D object at the given coordinates and with the given width and height.
		@param g The Graphics2D object to draw on
		@param x The x coordinate of the left corner of the rectangle to draw on
		@param y The y coordinate of the upper corner of the rectangle to draw on
		@param width The width to draw this sprite
		@param height The height to draw this sprite
		@param reversed True if this sprite should be drawn with its horizontal axis flipped
	*/
	public void draw(Graphics2D g, int x, int y, int width, int height, boolean reversed) {
		if (image != null) {
			if (reversed)
				g.drawImage(image, x+width, y, -width, height, null);
			else
				g.drawImage(image, x, y, width, height, null);
		} else {
			System.out.println("Null sprite image!");
		}
	}

	/**	Returns the width of this sprite.
		@return The width of this sprite
	*/
	public int getWidth() {
		return image.getWidth();
	}

	/**	Returns the height of this sprite.
		@return The height of this sprite
	*/
	public int getHeight() {
		return image.getHeight();
	}

	/**	Returns this sprite's image.
		@return This sprite's image
	*/
	public BufferedImage getImage() {
		return image;
	}

	/**	Returns the shape of this sprite.
		@return The shape of this sprite
	*/
	public Shape getShape() {
		return shape;
	}

	/**	Returns a copy of this sprite.
		@return A copy of this sprite
	*/
	public Sprite copy() {
		return new StaticSprite(image, shape);
	}

	/**	Resets this sprite to its default state.
	*/
	public void reset() {

	}
}