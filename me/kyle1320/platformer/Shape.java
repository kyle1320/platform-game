package me.kyle1320.platformer;

/**	Represnts an object that collide with another Shape.
	@author Kyle Cutler
	@version 1/1/14
*/
public interface Shape {
	/**	Collides this shape with another.
		@param x The lower x coordinate of this shape
		@param y The lower y coordinate of this shape
		@param mx The x movement of this shape
		@param my the y movement of this shape
		@param other The other shape being collided with
		@param sx The lower x coordinate of the other shape
		@param sy The lower y coordinate of the other shape
		@return The Collision representing which sides of this shape collided with the other shape and how much this shape was moved
	*/
	public Collision collideWith(double x, double y, double mx, double my, Shape other, double sx, double sy);

	/**	Returns the width of this shape.
		@return The width of this shape
	*/
	public double getWidth();

	/**	Returns the height of this shape.
		@return The height of this shape
	*/
	public double getHeight();

	/**	Returns the minimum x coordinate of this shape.
		@return The minimum x coordinate of this shape
	*/
	public double getMinX();

	/**	Returns the minimum y coordinate of this shape.
		@return The minimum y coordinate of this shape
	*/
	public double getMinY();

	/**	Returns the maximum x coordinate of this shape.
		@return The maximum x coordinate of this shape
	*/
	public double getMaxX();

	/**	Returns the maximum y coordinate of this shape.
		@return The maximum y coordinate of this shape
	*/
	public double getMaxY();

	/**	Returns the middle x coordinate of this shape.
		@return The middle x coordinate of this shape
	*/
	public double getMidX();

	/**	Returns the middle y coordinate of this shape.
		@return The middle y coordinate of this shape
	*/
	public double getMidY();
}