package me.kyle1320.platformer;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.KeyEvent;

/**	A screen that shows program credits.
	@author Kyle Cutler
	@version 1/8/14
*/
public class AboutScreen extends Displayable {
	// size
	// Static constants representing the design of the screen.
	private static final int MARGIN = 50;
	private static final int TITLE_HEIGHT = 80;

	// style
	private static final Background background = Background.normal;

	private static final Color titleColor = Color.WHITE;
	private static final Color titleOutlineColor = Color.BLACK;
	private static final Color textColor = Color.BLACK;

	private static final Font titleFont = PlayerHUD.gameFont.deriveFont((float)TITLE_HEIGHT);
	private static final Font textFont = PlayerHUD.gameFont;
	private static final Font returnFont = PlayerHUD.gameFont.deriveFont((float)MARGIN/2);

	private GamePanel parent;

	private int textY, textHeight;

	/**	Creates a new AboutScreen with the given GamePanel as its parent.
		@param parent the GamePanel that holds this table
	*/
	public AboutScreen(GamePanel parent) {
		this.parent = parent;
	}

	/**	Draws this screen on the given Graphics2D object, with the given screen width and height
		@param g The Graphics2D object on which to draw
		@param width The width to draw this table
		@param height The height to draw this table
	*/
	public void draw(Graphics2D g, int width, int height) {
		getSizes(width, height);

		background.draw(g, 0, 0, width, height);

		g.setColor(titleColor);
		g.setFont(titleFont);
		TextUtils.drawOutlinedString(g, "About", 0, MARGIN, width, TITLE_HEIGHT, TextUtils.ALIGN_CENTER, TextUtils.ALIGN_CENTER, titleOutlineColor, 1.0F, false);
		
		g.setColor(textColor);
		g.setFont(textFont);
		TextUtils.drawMultilineString(g, "Created by Kyle Cutler\n\nCredits to:\nKenNL (Kenney.nl) for the graphics", 0, textY, width, textHeight, TextUtils.ALIGN_CENTER, TextUtils.ALIGN_CENTER, false);
		
		g.setColor(Color.BLACK);
		g.setFont(returnFont);
		TextUtils.drawHorizontalCenteredString(g, "Press ESC to return", 0, height-MARGIN, width, MARGIN, true);
	}

	/**	Doesn't do anything.
	*/
	public void update() {}

	/**	Doesn't do anything.
	*/
	public void reset() {}

	/**	Calculates the placement and sizes for the elements displayed in this screen given a width and height
		@param width The screen width
		@param height The screen height
	*/
	private void getSizes(int width, int height) {
		textY = MARGIN+TITLE_HEIGHT;
		textHeight = height - MARGIN - textY;
	}

	/**	This table can be exited by pressing the escape key.
		@param e The key event
	*/
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				parent.mainMenu();
				break;
		}
	}
}