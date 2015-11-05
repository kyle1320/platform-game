package me.kyle1320.platformer;

/**	Represents an object with a position and shape in space. Allows for collision resolution between shapes.
	@author Kyle Cutler
	@version 12/31/13
*/
public class Collidable {
	private Shape shape;
	private double x, y;

	/**	Constructs a new Collidable object with the given shape, x coordinate, and y coordinate.
		@param shape The shape of this object
		@param x The lowest x coordinate of this object
		@param y The lowest y coordinate of this object
	*/
	public Collidable(Shape shape, double x, double y) {
		this.shape = shape;
		this.x = x;
		this.y = y;
	}

	/**	Moves this object so that its shape lies within the given bounds.
		@param minx The minimum x coordinate of the rectangle to push this object within
		@param maxx The maximum x coordinate of the rectangle to push this object within
		@param miny The minimum y coordinate of the rectangle to push this object within
		@param maxy The maximum y coordinate of the rectangle to push this object within
		@return The CollisionAxis representing which sides of this object collided with the bounding rectangle
	*/
	public CollisionAxis pushInBounds(double minx, double miny, double maxx, double maxy) {
		double thisMinX = shape.getMinX();
		double thisMinY = shape.getMinY();
		double thisMaxX = shape.getMaxX();
		double thisMaxY = shape.getMaxY();

		CollisionAxis collision = new CollisionAxis();

		if (x + thisMinX < minx) {
			x = minx - thisMinX;
			collision.west = true;
		}
		if (x + thisMaxX > maxx) {
			x = maxx - thisMaxX;
			collision.east = true;
		}
		if (y + thisMinY < miny) {
			y = miny - thisMinY;
			collision.south = true;
		}
		if (y + thisMaxY > maxy) {
			y = maxy - thisMaxY;
			collision.north = true;
		}

		return collision;
	}

	/**	Returns the Chebyshev (as opposed to Euclidean) distance this Collidable is from another.
		@param other The other Collidable to calculate the distance from
		@return The distance from this Collidable to the other
	*/
	public double getDistanceFrom(Collidable other) {
		double thisMinX = getMinX();
		double thisMinY = getMinY();
		double thisMaxX = getMaxX();
		double thisMaxY = getMaxY();

		double otherMinX = other.getMinX();
		double otherMinY = other.getMinY();
		double otherMaxX = other.getMaxX();
		double otherMaxY = other.getMaxY();

		double dx, dy;

		if (otherMaxX < thisMinX)
			dx = thisMinX - otherMaxX;
		else if (otherMinX > thisMaxX)
			dx = otherMinX - thisMaxX;
		else
			dx = 0;

		if (otherMaxY < thisMinY)
			dy = thisMinY - otherMaxY;
		else if (otherMinY > thisMaxY)
			dy = otherMinY - thisMaxY;
		else
			dy = 0;

		return Math.max(Math.abs(dx), Math.abs(dy));
	}

	/**	Returns true if this collidable overlaps another or is within a certain margin of error.
		@param other The other collidable to test
		@return True if this collidable overlaps another
	*/
	public boolean overlaps(Collidable other) {
		return getDistanceFrom(other) <= 1e-10;
	}

	/**	Collides this Collidable with another. What this means is dependent on the Shape of the two objects.
		@param other The other Collidable to collide with
		@param mx The x movement of this Collidable as it collided with the other
		@param my The y movement of this Collidable as it collided with the other
		@return The CollisionAxis representing which sides of this object collided with the other object
	*/
	public Collision collideWith(Collidable other, double mx, double my) {
		Collision c = shape.collideWith(x, y, mx, my, other.shape, other.x, other.y);
		//move(c.x, c.y);

		return c;//.collision;
	}

	/**	Moves this object some distance in the x and y direction.
		@param mx The distance to move along the x axis
		@param my The distance to move along the y axis
	*/
	public void move(double mx, double my) {
		this.x += mx;
		this.y += my;
	}

	/**	Moves this object some distance in the x direction.
		@param mx The distance to move along the x axis
	*/
	public void moveX(double mx) {
		this.x += mx;
	}

	/**	Moves this object some distance in the y direction.
		@param my The distance to move along the y axis
	*/
	public void moveY(double my) {
		this.y += my;
	}

	/**	Sets the lower x coordinate of this object.
		@param x The new x coordinate
	*/
	public void setX(double x) {
		this.x = x;
	}

	/**	Sets the lower y coordinate of this object.
		@param y The new y coordinate
	*/
	public void setY(double y) {
		this.y = y;
	}

	/**	Sets the shape of this object.
		@param shape The new shape for this object.
	*/
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	/**	Returns the x coordinate of this object.
		@return The lower x coordinate of this object
	*/
	public double getX() {
		return x;
	}

	/**	Returns the y coordinate of this object.
		@return The lower y coordinate of this object
	*/
	public double getY() {
		return y;
	}

	/**	Returns the width of the shape of this object.
		@return The width of the shape of this object.
	*/
	public double getWidth() {
		return shape.getWidth();
	}

	/**	Returns the height of the shape of this object.
		@return The height of the shape of this object.
	*/
	public double getHeight() {
		return shape.getHeight();
	}

	/**	Returns the minimum x coordinate of the shape of this object.
		@return The minimum x coordinate of the shape of this object. Note this is not necessarily equal to getX() due to shape implementation.
	*/
	public double getMinX() {
		return x + shape.getMinX();
	}

	/**	Returns the minimum y coordinate of the shape of this object.
		@return The minimum y coordinate of the shape of this object. Note this is not necessarily equal to getY() due to shape implementation.
	*/
	public double getMinY() {
		return y + shape.getMinY();
	}

	/**	Returns the maximum x coordinate of the shape of this object.
		@return The maximum x coordinate of the shape of this object. Note this is not necessarily equal to getX()+getWidth() due to shape implementation.
	*/
	public double getMaxX() {
		return x + shape.getMaxX();
	}

	/**	Returns the maximum y coordinate of the shape of this object.
		@return The maximum y coordinate of the shape of this object. Note this is not necessarily equal to getY()+getHeight() due to shape implementation.
	*/
	public double getMaxY() {
		return y + shape.getMaxY();
	}

	/**	Returns the middle x coordinate of the shape of this object.
		@return The middle x coordinate of the shape of this object.
	*/
	public double getMidX() {
		return x + shape.getMidX();
	}

	/**	Returns the middle x coordinate of the shape of this object.
		@return The middle x coordinate of the shape of this object.
	*/
	public double getMidY() {
		return y + shape.getMidY();
	}
}