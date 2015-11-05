package me.kyle1320.platformer;

/**	A Shape that is a rectangle.
	@author Kyle Cutler
	@version 1/1/14
*/
public class ShapeRectangle implements Shape {
	private double width, height;
	private double minx, miny, maxx, maxy;

	/**	Creates a new ShapeRectangle with the given boundaries. Note that boundaries are relative to width and height, and should therefore be between 0 and 1 inclusive.
		@param width The width of this rectangle
		@param height The height of this rectangle
		@param minx The minimum x coordinate of this rectangle
		@param miny The minimum y coordinate of this rectangle
		@param maxx The maximum x coordinate of this rectangle
		@param maxy The maximum y coordinate of this rectangle
	*/
	public ShapeRectangle(double width, double height, double minx, double miny, double maxx, double maxy) {
		this.width = width;
		this.height = height;

		this.minx = minx;
		this.miny = miny;
		this.maxx = maxx;
		this.maxy = maxy;
	}

	/**	Collides this rectangle with another shape.
		@param x The lower x coordinate of this rectangle
		@param y The lower y coordinate of this rectangle
		@param mx The x movement of this rectangle
		@param my the y movement of this rectangle
		@param other The other shape being collided with
		@param sx The lower x coordinate of the other shape
		@param sy The lower y coordinate of the other shape
		@return The Collision representing which sides of this rectangle collided with the other shape and how much this rectangle was moved
	*/
	public Collision collideWith(double x, double y, double mx, double my, Shape other, double sx, double sy) {
		if (other instanceof ShapeRectangle) {
			double error = 1.0e-12;

			double thisMinX = x + getMinX() - error;
			double thisMinY = y + getMinY() - error;
			double thisMaxX = x + getMaxX() + error;
			double thisMaxY = y + getMaxY() + error;

			double otherMinX = sx + other.getMinX();
			double otherMinY = sy + other.getMinY();
			double otherMaxX = sx + other.getMaxX();
			double otherMaxY = sy + other.getMaxY();

			double distX = x + getMidX() - sx - other.getMidX();
			double distY = y + getMidY() - sy - other.getMidY();

			double overlapX = distX < 0 ? otherMinX - thisMaxX : otherMaxX - thisMinX;
			double overlapY = distY < 0 ? otherMinY - thisMaxY : otherMaxY - thisMinY;

			double correctx = 0.0;
			double correcty = 0.0;

			CollisionAxis collision = new CollisionAxis();

			if ((thisMinX <= otherMinX && thisMaxX <= otherMinX) || (thisMinY <= otherMinY && thisMaxY <= otherMinY) ||
				(thisMaxX >= otherMaxX && thisMinX >= otherMaxX) || (thisMaxY >= otherMaxY && thisMinY >= otherMaxY))
				return new Collision();

			if (Math.abs(overlapX) < Math.abs(overlapY)) {
				if (Math.signum(mx) != Math.signum(overlapX)) {
					if (overlapX > 0)
						collision.west = true;
					else if (overlapX < 0)
						collision.east = true;

					correctx = overlapX;
				}
			} else {
				if (Math.signum(my) != Math.signum(overlapY)) {
					if (overlapY > 0)
						collision.south = true;
					else if (overlapY < 0)
						collision.north = true;

					correcty = overlapY;
				}
			}

			/*if (Math.abs(overlapY) <= 0.15 && my == 0 && correcty == 0) {
				correcty = overlapY;
			}*/

			return new Collision(correctx, correcty, collision);
		}/*else if (other instanceof ShapeSlope) {
			ShapeSlope otherSlope = (ShapeSlope)other;
			boolean slopex = otherSlope.getSlopeX();
			boolean slopey = otherSlope.getSlopeY();

			double error = 1.0e-10;

			double thisMinX = x + getMinX() - error;
			double thisMinY = y + getMinY() - error;
			double thisMaxX = x + getMaxX() + error;
			double thisMaxY = y + getMaxY() + error;

			double otherMinX = sx + other.getMinX();
			double otherMinY = sy + other.getMinY();
			double otherMaxX = sx + other.getMaxX();
			double otherMaxY = sy + other.getMaxY();

			double distX = x + getMidX() - sx - other.getMidX();
			double distY = y + getMidY() - sy - other.getMidY();

			double overlapX = distX < 0 ? otherMinX - thisMaxX : otherMaxX - thisMinX;
			double overlapY = distY < 0 ? otherMinY - thisMaxY : otherMaxY - thisMinY;

			double correctx = 0.0;
			double correcty = 0.0;

			CollisionAxis collision = new CollisionAxis();

			if ((thisMinX <= otherMinX && thisMaxX <= otherMinX) || (thisMinY <= otherMinY && thisMaxY <= otherMinY) ||
				(thisMaxX >= otherMaxX && thisMinX >= otherMaxX) || (thisMaxY >= otherMaxY && thisMinY >= otherMaxY))
				return new Collision();

			double testX, testY;
			double offCenterX, offCenterY;
			int dirX = slopex ? 1 : -1;
			int dirY = slopey ? 1 : -1;

			testX = (thisMinX + thisMaxX)/2.0;

			testY = thisMinY;

			offCenterX = testX - (otherMinX + otherMaxX)/2;// - other.getMidX();
			offCenterY = testY - (otherMinY + otherMaxY)/2;// - other.getMidY();

			if (testX > otherMinX && testX < otherMaxX) {
				correcty = Math.abs(Math.abs(offCenterX) - Math.abs(offCenterY)) * Math.signum(dirY);
				collision.south = true;
			}else {
				if (Math.abs(overlapX) < Math.abs(overlapY)) {
					if (Math.signum(mx) != Math.signum(overlapX)) {
						if (overlapX > 0)
							collision.west = true;
						else if (overlapX < 0)
							collision.east = true;

						correctx = overlapX;
					}
				} else {
					if (Math.signum(my) != Math.signum(overlapY)) {
						if (overlapY > 0)
							collision.south = true;
						else if (overlapY < 0)
							collision.north = true;

						correcty = overlapY;
					}
				}
			}

			return new Collision(correctx, correcty, collision);
		}*/

		return new Collision();
	}

	/**	Returns the width of this rectangle.
		@return The width of this rectangle
	*/
	public double getWidth() {
		return width;
	}

	/**	Returns the height of this rectangle.
		@return The height of this rectangle
	*/
	public double getHeight() {
		return height;
	}

	/**	Returns the minimum x coordinate of this rectangle.
		@return The minimum x coordinate of this rectangle
	*/
	public double getMinX() {
		return minx * width;
	}

	/**	Returns the minimum y coordinate of this rectangle.
		@return The minimum y coordinate of this rectangle
	*/
	public double getMinY() {
		return miny * height;
	}

	/**	Returns the maximum x coordinate of this rectangle.
		@return The maximum x coordinate of this rectangle
	*/
	public double getMaxX() {
		return maxx * width;
	}

	/**	Returns the maximum y coordinate of this rectangle.
		@return The maximum y coordinate of this rectangle
	*/
	public double getMaxY() {
		return maxy * height;
	}

	/**	Returns the middle x coordinate of this rectangle.
		@return The middle x coordinate of this rectangle
	*/
	public double getMidX() {
		return (getMinX() + getMaxX())/2.0;
	}

	/**	Returns the middle y coordinate of this rectangle.
		@return The middle y coordinate of this rectangle
	*/
	public double getMidY() {
		return (getMinY() + getMaxY())/2.0;
	}
}