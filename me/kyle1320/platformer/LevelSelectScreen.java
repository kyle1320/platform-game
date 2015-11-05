package me.kyle1320.platformer;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.TexturePaint;
import java.awt.BasicStroke;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import java.util.ArrayList;

/**	A table that shows the levels that can be played.
	@author Kyle Cutler
	@version 1/1/14
*/
public class LevelSelectScreen extends Displayable {
	// size
	// Static constants representing the size of elements on the screen.
	private static final int MARGIN = 50;
	private static final int TITLE_HEIGHT = 80;
	private static final int TITLE_PADDING = 40;
	private static final int ENTRY_HEIGHT = 40;
	private static final int SCROLLBAR_WIDTH = 30;

	// style
	// Static constants representing the style of the screen.
	private static final Background background = Background.normal;

	private static final Color titleColor = Color.WHITE;
	private static final Color titleOutlineColor = Color.BLACK;
	private static final Color tabColor = Color.WHITE;
	private static final Color entryColor = Color.BLACK;

	private static final Font titleFont = PlayerHUD.gameFont.deriveFont((float)TITLE_HEIGHT);
	private static final Font entryFont = PlayerHUD.gameFont.deriveFont((float)ENTRY_HEIGHT/2);
	private static final Font returnFont = PlayerHUD.gameFont.deriveFont((float)MARGIN/2);

	private static final TexturePaint 	titleTexture = GameSprite.grassMid.getTexture(),
										tableTexture = GameSprite.grassCenter.getTexture(),
										entryTexture = GameSprite.liquidWater.getTexture(),
										entrySelectedTexture = GameSprite.liquidLava.getTexture(),
										scrollTexture = GameSprite.castleCenter.getTexture(),
										scrollBarTexture = GameSprite.liquidLava.getTexture();

	// information
	private static final GameLevel[] levels = GameLevel.values();
	private double scroll;
	private int selected;

	private GamePanel parent;

	// for scrolling behavior
	private int clickX, clickY;
	private boolean scrolling;

	// calculated sizes
	private int tableX, tableY, tableWidth, tableHeight;
	private int scrollX, scrollY, scrollWidth, scrollHeight;
	private double scrollScale;

	/**	Creates a new LevelSelectScreen with the given GamePanel as its parent.
		@param parent the GamePanel that holds this table
	*/
	public LevelSelectScreen(GamePanel parent) {
		this.parent = parent;
		reset();
	}

	/**	Draws this table on the given Graphics2D object, with the given screen width and height
		@param g The Graphics2D object on which to draw
		@param width The width to draw this table
		@param height The height to draw this table
	*/
	public void draw(Graphics2D g, int width, int height) {
		getSizes(width, height);

		background.draw(g, 0, 0, width, height);

		g.setColor(titleColor);		// title
		g.setFont(titleFont);
		TextUtils.drawOutlinedString(g, "Select A Level", 0, MARGIN, width, TITLE_HEIGHT, TextUtils.ALIGN_CENTER, TextUtils.ALIGN_CENTER, titleOutlineColor, 1.0F, false);
		//TextUtils.drawCenteredString(g, "Select A Level", 0, MARGIN, width, TITLE_HEIGHT, false);

									// use separate graphics for clipping
		Graphics2D table = (Graphics2D)g.create(tableX, tableY, tableWidth, tableHeight);

		table.setPaint(tableTexture);	// table background
		table.fillRect(0, 0, tableWidth, tableHeight);

		// because of scrolling, we need to calculate where to start drawing the list
		int curry = (int)((-scroll%1)*ENTRY_HEIGHT);

		// determine the first item in the list to draw
		int firstIndex = (int)scroll;

		for (int i=firstIndex; i < levels.length; i++) {
			GameLevel level = levels[i];

			if (curry >= tableHeight)
				break;

			if (i == selected)
				table.setPaint(entrySelectedTexture);
			else
				table.setPaint(entryTexture);	// entry background
			table.fillRect(1, curry+1, tableWidth-2, ENTRY_HEIGHT-2);

			table.setColor(entryColor);		// entry text
			table.setFont(entryFont);
			TextUtils.drawCenteredString(table, level.toString(), 5, curry, tableWidth-10, ENTRY_HEIGHT, true);

			curry += ENTRY_HEIGHT;
		}

		g.setPaint(scrollTexture);				// scrollbar background
		g.fillRect(scrollX, scrollY, scrollWidth, tableHeight);

		g.setPaint(scrollBarTexture);			// scrollbar
		g.fillRoundRect(scrollX+1, (int)(scrollY+((scroll/levels.length)*scrollHeight)+1), scrollWidth - 2, (int)(scrollHeight*scrollScale)-1, scrollWidth-2, scrollWidth-2);
		
		g.setColor(Color.BLACK);
		g.setFont(returnFont);
		TextUtils.drawCenteredString(g, "Press ESC to return", 0, height-MARGIN, width, MARGIN, false);
	}

	/**	Calculates the placement and sizes for the elements displayed in this table given a width and height
		@param width The screen width
		@param height The screen height
	*/
	private void getSizes(int width, int height) {
		tableX = MARGIN;
		tableY = MARGIN + TITLE_HEIGHT + TITLE_PADDING;

		tableWidth = width - MARGIN*2 - SCROLLBAR_WIDTH;
		tableHeight = height - MARGIN - tableY;

		scrollX = width - MARGIN - SCROLLBAR_WIDTH;
		scrollY = tableY;

		scrollWidth = SCROLLBAR_WIDTH;
		scrollHeight = tableHeight;

		double displayTiles = (double)scrollHeight / ENTRY_HEIGHT;
		scrollScale = Math.min(1, displayTiles/levels.length);

		scroll = Math.max(0, Math.min(levels.length-displayTiles, scroll));
	}

	/**	Loads the selected level if there is one.
	*/
	private void enterLevel() {
		if (selected < 0)
			return;

		parent.goToLevel(levels[selected].getLevel());
	}

	/**	Doesn't do anything.
	*/
	public void update() {

	}

	/**	Resets this table to its default state.
	*/
	public void reset() {
		scrolling = false;
		scroll = 0.0;
		selected = -1;
	}

	/**	Handles mouse press events for scrolling and level selection.
		@param e The mouse event
	*/
	public void mousePressed(MouseEvent e) {
		clickX = e.getX();
		clickY = e.getY();

		if (clickX > scrollX && clickX < scrollX + scrollWidth)
			scrolling = true;
		else if (	clickX > tableX && clickX < tableX + tableWidth &&
					clickY > tableY && clickY < tableY + tableHeight) {
			selected = (int)((double)(clickY - tableY) / ENTRY_HEIGHT + scroll);
			if (selected < 0 || selected > levels.length)
				selected = -1;

			if (e.getClickCount() == 2)
				enterLevel();
		} else {
			selected = -1;
		}
	}

	/**	Handles mouse drag events for scrolling.
		@param e The mouse event
	*/
	public void mouseDragged(MouseEvent e) {
		int minScroll = scrollY + (int)(scroll*scrollHeight/levels.length) + 1;
		int maxScroll = minScroll + (int)(scrollScale*scrollHeight);

		if (scrolling && clickY > minScroll && clickY < maxScroll) {

			int rel = e.getY() - clickY;

			double scrollamt = (double)rel*levels.length / scrollHeight;

			scroll += scrollamt;
		}

		clickX = e.getX();
		clickY = e.getY();
	}

	/**	Handles mouse release events for scrolling.
		@param e The mouse event
	*/
	public void mouseReleased(MouseEvent e) {
		scrolling = false;
	}

	/**	Handles mouse wheel events for scrolling.
		@param e The mouse event
	*/
	public void mouseWheelMoved(MouseWheelEvent e) {
		int mx = Input.getMouseX();
		int my = Input.getMouseY();

		if (mx >= tableX && mx < tableX + tableWidth &&
			my >= tableY && my < tableY + tableHeight)
			scroll += e.getWheelRotation();
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