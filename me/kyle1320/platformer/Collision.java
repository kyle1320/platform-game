package me.kyle1320.platformer;

/**	Represents a collision between two shapes.
	@author Kyle Cutler
	@version 12/31/13
*/
public final class Collision {
	public double x;
	public double y;
	public CollisionAxis collision;

	/**	Creates a new Collision with default zero values.
	*/
	public Collision() {
		this.x = 0.0;
		this.y = 0.0;
		this.collision = new CollisionAxis();
	}

	/**	Creates a new Collision with the given values.
		@param x The x value of the movement of the colliding shape
		@param y The y value of the movement of the colliding shape
		@param axis The CollisionAxis representing which sides of the colliding shape collided with the other shape.
	*/
	public Collision(double x, double y, CollisionAxis axis) {
		this.x = x;
		this.y = y;
		this.collision = axis;
	}
}