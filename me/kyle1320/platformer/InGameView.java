package me.kyle1320.platformer;

import java.awt.Graphics2D;

import java.awt.event.KeyEvent;

/**	Draws a GameState.
	@author Kyle Cutler
	@version 1/1/14
*/
public class InGameView extends Displayable {
	static final int TILE_SIZE = 70;

	private GamePanel parent;
	private GameState game;
	private PlayerHUD hud;

	/**	Creates a new InGameView with the given GamePanel as its parent.
		@param parent the GamePanel that holds this view
	*/
	public InGameView(GamePanel parent) {
		this.parent = parent;
	}

	/**	Updates the game.
	*/
	public void update() {
		game.update();

		if (game.isFinished())	// If the player finished the level
			parent.winScreen();	// Show the win screen
	}

	/**	Doesn't do anything.
	*/
	public void reset() {
		
	}

	/**	Loads the given GameState to display.
		@param game The GameState to display
	*/
	public void loadGame(GameState game) {
		this.game = game;
		this.hud = new PlayerHUD(game.getPlayer());
	}

	/**	Returns True if a game has been loaded in this view.
		@return True if a game has been loaded in this view.
	*/
	public boolean hasGame() {
		return game != null;
	}

	/**	Draws this view on the given Graphics2D object, with the given screen width and height
		@param g The Graphics2D object on which to draw
		@param width The width to draw this view
		@param height The height to draw this view
	*/
	public void draw(Graphics2D g, int width, int height) {
		Level level = game.getLevel();
		Player p = game.getPlayer();
		Background background = level.getBackground();

		// convert pixel coordinate width and height to tile coordinates
		double realWidth = (double)width / TILE_SIZE;
		double realHeight = (double)height / TILE_SIZE;

		// synchronized with the player so that updates don't affect the player's locaion while we're drawing
		synchronized (p) {
			// center around the player, cut off at the left, right, and bottom of the level. The scroll y amount can go up further than the level.
			double scrollX = Math.max(0, Math.min(level.getWidth() - realWidth, p.getX() + 0.5 - realWidth/2));
			double scrollY = Math.max(0, p.getY() - realHeight/2);

			// draw the background
			background.draw(g, (int)(scrollX*TILE_SIZE), (int)(scrollY*TILE_SIZE), width, height);

			// the first tile we need to draw
			int firstx = (int)scrollX;
			int firsty = (int)scrollY;

			// the screen coordinates of the first tile we draw
			int startx = (int)(-(scrollX % 1)*TILE_SIZE);
			int starty = (int)((scrollY % 1)*TILE_SIZE - TILE_SIZE);

			// current x and y coordinates of the tile we're drawing
			int newx, newy = starty + height;

			// draw until we reach the edge of the screen
			for (int y=firsty; newy > -TILE_SIZE; y++, newy -= TILE_SIZE) {
				newx = startx;

				for (int x=firstx; newx < width; x++, newx += TILE_SIZE) {
					Tile draw = game.getLevel().getTileAt(x, y);

					// if there is a tile to draw (not off the edge of the level)
					if (draw != null) {
						Sprite sprite = draw.getSprite();

						sprite.draw(g, newx, newy, TILE_SIZE, TILE_SIZE);
					}
				}
			}

			// draw each entity, adjusting screen coordinates depending on the scroll amount
			for (Entity e : game.getEntities())
				e.draw(g, (e.getX()-scrollX)*TILE_SIZE, height-(e.getY()-scrollY)*TILE_SIZE, TILE_SIZE);

			// draw the player
			p.draw(g, (p.getX()-scrollX)*TILE_SIZE, height-(p.getY()-scrollY)*TILE_SIZE, TILE_SIZE);
			
			// draw the player HUD (heads up display)
			hud.draw(g, width, height);
		}
	}

	/**	Returns the game drawn by this view.
		@return The GameState drawn by this view
	*/
	public GameState getGame() {
		return game;
	}

	/**	Loads the currently loaded level again, resetting the game.
	*/
	public void reloadLevel() {
		loadGame(new GameState(game.getLevel().getGameLevel().getLevel()));
	}

	/**	Pressing the escape key shows the pause screen.
		@param e The key press event
	*/
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				parent.pauseMenu();
				break;
		}
	}
}