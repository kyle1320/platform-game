package me.kyle1320.platformer;

/**	An enumeration of Shapes used in the game.
	@author Kyle Cutler
	@version 1/1/14
*/
public enum SpriteShape {
	fullSquare(new ShapeRectangle(1, 1, 0, 0, 1, 1)),
	centerQuarter(new ShapeRectangle(1, 1, 0.25, 0.25, 0.75, 0.75)),
	topHalf(new ShapeRectangle(1, 1, 0, 0.5, 1, 1)),
	bottomHalf(new ShapeRectangle(1, 1, 0, 0, 1, 0.5)),
	bottomThird(new ShapeRectangle(1, 1, 0, 0, 1, 1.0/3)),
	bottomQuarter(new ShapeRectangle(1, 1, 0, 0, 1, 0.25)),
	buttonShape(new ShapeRectangle(1, 1, 0, 0, 1, 0.2)),
	keyShape(new ShapeRectangle(1, 1, 0.125, 0.25, 0.875, 0.75)),
	torchShape(new ShapeRectangle(1, 1, 0.25, 0.1, 0.75, 0.9)),
	cloudShape(new ShapeRectangle(1.85, 1, 0, 0, 1, 1)),
	player1Stand(new ShapeRectangle(66.0/80, 92.0/80, 0, 0, 1, 1)),
	player1Move(new ShapeRectangle(66.0/80, 89.0/80, 0, 0, 1, 1)),
	player1Jump(new ShapeRectangle(66.0/80, 94.0/80, 0, 0, 1, 1)),
	player1Crouch(new ShapeRectangle(66.0/80, 68.0/80, 0, 0, 1, 1)),
	player1Climb(new ShapeRectangle(66.0/80, 89.0/80, 0, 0, 1, 1));

	private Shape shape;

	/**	Creates a new SpriteShape with the given shape.
		@param shape The Shape of this SpriteShape
	*/
	private SpriteShape(Shape shape) {
		this.shape = shape;
	}

	/**	Returns the shape of this SpriteShape.
		@return The shape of this SpriteShape
	*/
	public Shape getShape() {
		return shape;
	}
}