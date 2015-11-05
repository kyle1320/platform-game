package me.kyle1320.platformer;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

/**	A screen displayed when the player completes a level.
	@author Kyle Cutler
	@version 1/1/14
*/
public class WinScreen extends Displayable {
	// size
	// Static constants representing the size of elements on the screen.
	private static final int MARGIN = 50;

	private static final int TITLE_HEIGHT = 80;
	private static final int TITLE_PADDING = 50;

	private static final int INFO_HEIGHT = 40;
	private static final int INFO_PADDING = 10;
	private static final int INFO_LINES = 2;

	private static final int BUTTON_WIDTH = 320;
	private static final int BUTTON_HEIGHT = 160;
	private static final int BUTTON_PADDING = 8;

	// style
	// Static constants representing the style of the screen.
	private static final MenuStyle style = new MenuStyle(PlayerHUD.gameFont, Background.normal, GameSprite.liquidWater, GameSprite.liquidLava, GameSprite.grassCenter, Color.WHITE, Color.BLACK);

	private static final Color infoColor = Color.BLACK;

	private static final Font titleFont = style.font.deriveFont((float)TITLE_HEIGHT);
	private static final Font infoFont = style.font.deriveFont((float)INFO_HEIGHT);

	// information
	private GamePanel parent;
	private int points;
	private String levelName;

	private volatile int entryState;
	private int selected;

	// calculated sizes
	private int titleX, titleY, titleWidth, titleHeight;
	private int infoX, infoY, infoWidth, infoHeight;
	private int buttonsX, buttonsY, buttonWidth, buttonHeight;

	/**	Creates a new WinScreen with the given GamePanel as its parent.
		@param parent the GamePanel that holds this table
	*/
	public WinScreen(GamePanel parent) {
		this.parent = parent;

		this.points = 0;
		this.levelName = "No level";

		reset();
	}

	/**	Draws this screen on the given Graphics2D object, with the given screen width and height
		@param g The Graphics2D object on which to draw
		@param width The width to draw this screen
		@param height The height to draw this screen
	*/
	public void draw(Graphics2D g, int width, int height) {
		getSizes(width, height);

		style.background.draw(g, 0, 0, width, height);

		g.setColor(style.title);
		g.setFont(titleFont);
		TextUtils.drawCenteredString(g, "Level Complete!", titleX, titleY, titleWidth, titleHeight, false);

		g.setColor(infoColor);
		g.setFont(infoFont);
		TextUtils.drawCenteredString(g, levelName, infoX, infoY, infoWidth, INFO_HEIGHT, false);
		TextUtils.drawCenteredString(g, "Score: " + points, infoX, infoY+INFO_HEIGHT+INFO_PADDING, infoWidth, INFO_HEIGHT, false);

		for (int i=0; i < 3; i++) {
			g.setPaint(style.buttonOutline);
			g.fillRoundRect(buttonsX+(buttonWidth+BUTTON_PADDING)*i, buttonsY, buttonWidth, buttonHeight, BUTTON_PADDING*2, BUTTON_PADDING*2);

			if (selected == i)
				g.setPaint(style.buttonSelected);
			else
				g.setPaint(style.buttonUnselected);
			g.fillRoundRect(buttonsX+(buttonWidth+BUTTON_PADDING)*i+BUTTON_PADDING, buttonsY+BUTTON_PADDING, buttonWidth-BUTTON_PADDING*2, buttonHeight-BUTTON_PADDING*2, BUTTON_PADDING*2, BUTTON_PADDING*2);
		}

		g.setColor(style.text);
		g.setFont(style.font);
		TextUtils.drawCenteredString(g, entryState == 1 ? "Save Score" : 
										entryState == 0 ? "Saving..." : 
										entryState == -1 ? "Saved!" : 
										selected == 0 ? "Try Again" : "Save Failed!", buttonsX+BUTTON_PADDING, buttonsY+BUTTON_PADDING, buttonWidth-BUTTON_PADDING*2, buttonHeight-BUTTON_PADDING*2, true);
		TextUtils.drawCenteredString(g, "Retry Level", buttonsX+buttonWidth+BUTTON_PADDING*2, buttonsY+BUTTON_PADDING, buttonWidth-BUTTON_PADDING*2, buttonHeight-BUTTON_PADDING*2, true);
		TextUtils.drawCenteredString(g, "Main Menu", buttonsX+(buttonWidth+BUTTON_PADDING)*2 + BUTTON_PADDING, buttonsY+BUTTON_PADDING, buttonWidth-BUTTON_PADDING*2, buttonHeight-BUTTON_PADDING*2, true);
	}

	/**	Calculates the placement and sizes for the elements displayed in this screen given a width and height
		@param width The screen width
		@param height The screen height
	*/
	private void getSizes(int width, int height) {
		titleX = MARGIN;
		titleY = MARGIN;
		titleWidth = width - MARGIN*2;
		titleHeight = TITLE_HEIGHT;

		infoX = MARGIN;
		infoY = titleY + titleHeight + TITLE_PADDING + INFO_PADDING;
		infoWidth = titleWidth;
		infoHeight = (INFO_HEIGHT+INFO_PADDING)*INFO_LINES;

		int miny = infoY + infoHeight;
		int spacey = height - MARGIN - miny;
		int spacex = width - MARGIN*2;

		buttonWidth = Math.max(0, Math.min(BUTTON_WIDTH, (spacex-BUTTON_PADDING*2)/3));
		buttonHeight = Math.max(0, Math.min(BUTTON_HEIGHT, spacey));

		buttonsX = (width-buttonWidth*3-BUTTON_PADDING*2)/2;
		buttonsY = miny + (spacey - buttonHeight)/2;
	}

	/**	Selects a button in this screen given a mouse x and y coordinate.
		@param x The x coordinate of the cursor
		@param y The y coordinate of the cursor
	*/
	private void selectButton(int x, int y) {
		if (x > buttonsX && x < buttonsX + buttonWidth*3 + BUTTON_PADDING*2 &&
			y > buttonsY && y < buttonsY + buttonHeight) {
			int relX = x - buttonsX;
			int overlapX = relX % (buttonWidth + BUTTON_PADDING);

			if (overlapX < buttonWidth)
				selected = relX / (buttonWidth + BUTTON_PADDING);
			else
				selected = -1;

			if (selected < 0 || selected > 2 || (selected == 0 && (entryState == 0 || entryState == -1)))
				selected = -1;
		} else {
			selected = -1;
		}
	}

	private void doOperation() {
		if (selected < 0)
			return;

		switch (selected) {
			case 0:
				new SaveThread().start();
				selected = -1;
				break;
			case 1:
				parent.reloadLevel();
				break;
			case 2:
				parent.mainMenu();
				break;
		}
	}

	public void update() {

	}

	/**	Resets this screen to its default state.
	*/
	public void reset() {
		entryState = 1;
		selected = -1;
	}

	
	public void setGame(GameState game) {
		this.points = game.getPlayer().getPoints();
		this.levelName = game.getLevel().getName();
	}

	/**	Handles mouse press events for button selection and input.
		@param e The mouse event
	*/
	public void mousePressed(MouseEvent e) {
		
	}

	/**	Handles mouse release events for button selection and input.
		@param e The mouse event
	*/
	public void mouseReleased(MouseEvent e) {
		if (selected >= 0)
			doOperation();
	}

	/**	Handles mouse movement events for button selection and input.
		@param e The mouse event
	*/
	public void mouseMoved(MouseEvent e) {
		selectButton(e.getX(), e.getY());

		selected = selected;
	}

	/**	Handles mouse drag events for button selection and input.
		@param e The mouse event
	*/
	public void mouseDragged(MouseEvent e) {
		selectButton(e.getX(), e.getY());

		selected = selected;
	}

	private class SaveThread extends Thread {
		public void run() {
			entryState = 0;

			String name = JOptionPane.showInputDialog("Enter your name");

			if (name == null) {
				entryState = 1;
				return;
			}

			try {
				ScoreDAO.addScore(new Score(name, points, levelName));
				entryState = -1;
			} catch (Exception e) {
				e.printStackTrace();
				entryState = -2;
			}
		}
	}
}