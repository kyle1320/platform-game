package me.kyle1320.platformer;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.TexturePaint;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import java.util.ArrayList;

/**	A table that shows the top scores as retrieved from ScoreDAO.
	@author Kyle Cutler
	@version 1/1/14
*/
public class HighScoreTable extends Displayable {
	// size
	private static final int MARGIN = 50;
	private static final int TITLE_HEIGHT = 80;
	private static final int TITLE_PADDING = 40;
	private static final int TAB_HEIGHT = 40;
	private static final int ENTRY_HEIGHT = 40;
	private static final int SCROLLBAR_WIDTH = 30;

	private static final double PLACE_WIDTH = 0.1;
	private static final double NAME_WIDTH = 0.3;
	private static final double SCORE_WIDTH = 0.3;
	private static final double LEVEL_WIDTH = 0.3;

	// max results to display
	private static final int RESULTS = 25;

	// style
	private static final Background background = Background.castle;

	private static final Color titleColor = Color.WHITE;
	private static final Color titleOutlineColor = Color.BLACK;
	private static final Color tabColor = Color.WHITE;
	private static final Color entryColor = Color.BLACK;

	private static final Font titleFont = PlayerHUD.gameFont.deriveFont((float)TITLE_HEIGHT);
	private static final Font tabFont = PlayerHUD.gameFont.deriveFont((float)TAB_HEIGHT/2);
	private static final Font entryFont = PlayerHUD.gameFont.deriveFont((float)ENTRY_HEIGHT/2);
	private static final Font returnFont = PlayerHUD.gameFont.deriveFont((float)MARGIN/2);

	private static final TexturePaint 	tableTexture = GameSprite.grassCenter.getTexture(),
										tabTexture = GameSprite.grassMid.getTexture(),
										entryTexture = GameSprite.liquidWater.getTexture(),
										scrollTexture = GameSprite.castleCenter.getTexture(),
										scrollBarTexture = GameSprite.liquidLava.getTexture();

	// information
	private ArrayList<Score> scores;
	private double scroll;

	private GamePanel parent;

	// for scrolling behavior
	private int clickX, clickY;
	private boolean scrolling;

	// calculated sizes
	private int placeTabX, nameTabX, scoreTabX, levelTabX;
	private int placeTabWidth, nameTabWidth, scoreTabWidth, levelTabWidth;
	private int tableX, tableY, tableWidth, tableHeight;
	private int scrollX, scrollY, scrollWidth, scrollHeight;
	private double scrollScale;

	/**	Creates a new HighScoreTable with the given GamePanel as its parent.
		@param parent the GamePanel that holds this table
	*/
	public HighScoreTable(GamePanel parent) {
		this.scores = new ArrayList<Score>();

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

		background.draw(g, 0, 0, width, height);	// background

		g.setColor(titleColor);		// title
		g.setFont(titleFont);
		TextUtils.drawOutlinedString(g, "High Scores", 0, MARGIN, width, TITLE_HEIGHT, TextUtils.ALIGN_CENTER, TextUtils.ALIGN_CENTER, titleOutlineColor, 1.0F, false);

									// use separate graphics for clipping
		Graphics2D tabs = (Graphics2D)g.create(tableX, tableY-TAB_HEIGHT, tableWidth, TAB_HEIGHT);
		Graphics2D table = (Graphics2D)g.create(tableX, tableY, tableWidth, tableHeight);

		tabs.setPaint(tabTexture);

		// draw the three tabs
		tabs.fillRoundRect(placeTabX+1, 0, placeTabWidth-2, TAB_HEIGHT*2, TAB_HEIGHT, TAB_HEIGHT);
		tabs.fillRoundRect(nameTabX+1, 0, nameTabWidth-2, TAB_HEIGHT*2, TAB_HEIGHT, TAB_HEIGHT);
		tabs.fillRoundRect(scoreTabX+1, 0, scoreTabWidth-2, TAB_HEIGHT*2, TAB_HEIGHT, TAB_HEIGHT);
		tabs.fillRoundRect(levelTabX+1, 0, levelTabWidth-2, TAB_HEIGHT*2, TAB_HEIGHT, TAB_HEIGHT);

		tabs.setColor(tabColor);
		tabs.setFont(tabFont);

		// label the tabs
		TextUtils.drawCenteredString(tabs, "Place", placeTabX, 0, placeTabWidth, TAB_HEIGHT, false);
		TextUtils.drawCenteredString(tabs, "Name", nameTabX, 0, nameTabWidth, TAB_HEIGHT, false);
		TextUtils.drawCenteredString(tabs, "Score", scoreTabX, 0, scoreTabWidth, TAB_HEIGHT, false);
		TextUtils.drawCenteredString(tabs, "Level", levelTabX, 0, levelTabWidth, TAB_HEIGHT, false);

		table.setPaint(tableTexture);	// table background
		table.fillRect(0, 0, tableWidth, tableHeight);

		// because of scrolling, we need to calculate where to start drawing the list
		int curry = (int)((-scroll%1)*ENTRY_HEIGHT);

		// determine the first item in the list to draw
		int firstIndex = (int)scroll;

		for (int i=firstIndex; i < scores.size(); i++) {
			Score score = scores.get(i);

			if (curry >= tableHeight)
				break;

			table.setPaint(entryTexture);	// entry background
			table.fillRect(1, curry+1, tableWidth-2, ENTRY_HEIGHT-2);

			table.setColor(entryColor);		// entry text
			table.setFont(entryFont);
			TextUtils.drawVerticalCenteredString(table, toPlace(i+1), placeTabX+5, curry, placeTabWidth-10, ENTRY_HEIGHT, true);
			TextUtils.drawVerticalCenteredString(table, score.getName(), nameTabX+5, curry, nameTabWidth-10, ENTRY_HEIGHT, true);
			TextUtils.drawVerticalCenteredString(table, "" + score.getScore(), scoreTabX+5, curry, scoreTabWidth-10, ENTRY_HEIGHT, true);
			TextUtils.drawVerticalCenteredString(table, score.getLevelName(), levelTabX+5, curry, levelTabWidth-10, ENTRY_HEIGHT, true);

			curry += ENTRY_HEIGHT;	// move down
		}

		// draw dividing lines
		table.setPaint(tableTexture);
		table.fillRect(nameTabX-1, 0, 2, tableHeight);
		table.fillRect(scoreTabX-1, 0, 2, tableHeight);
		table.fillRect(levelTabX-1, 0, 2, tableHeight);

		g.setPaint(scrollTexture);				// scrollbar background
		g.fillRect(scrollX, scrollY, scrollWidth, tableHeight);

		g.setPaint(scrollBarTexture);			// scrollbar
		g.fillRoundRect(scrollX+1, (int)(scrollY+((scroll/scores.size())*scrollHeight)+1), scrollWidth - 2, (int)(scrollHeight*scrollScale)-1, scrollWidth-2, scrollWidth-2);
		
		g.setColor(Color.WHITE);
		g.setFont(returnFont);
		TextUtils.drawCenteredString(g, "Press ESC to return", 0, height-MARGIN, width, MARGIN, false);
	}

	/**	Converts a given number to its language equivalent: 1 becomes 1st, 2 becomes 2nd, etc.
		@param place The number to convert
		@return The place String
	*/
	private String toPlace(int place) {
		String as = "" + place;
		if (as.length() > 1 && as.charAt(as.length()-2) == '1')
			return as + "th";
		else
			switch (place%10) {
				case 1:
					return as + "st";
				case 2:
					return as + "nd";
				case 3:
					return as + "rd";
				default:
					return as + "th";
			}
	}

	/**	Calculates the placement and sizes for the elements displayed in this table given a width and height
		@param width The screen width
		@param height The screen height
	*/
	private void getSizes(int width, int height) {
		tableX = MARGIN;
		tableY = MARGIN + TITLE_HEIGHT + TITLE_PADDING + TAB_HEIGHT;

		tableWidth = width - MARGIN*2 - SCROLLBAR_WIDTH;
		tableHeight = height - MARGIN - tableY;

		placeTabX = 0;
		nameTabX = (int)(PLACE_WIDTH*tableWidth);
		scoreTabX = (int)((PLACE_WIDTH+NAME_WIDTH)*tableWidth);
		levelTabX = (int)((PLACE_WIDTH+NAME_WIDTH+SCORE_WIDTH)*tableWidth);

		placeTabWidth = nameTabX-placeTabX;
		nameTabWidth = scoreTabX-nameTabX;
		scoreTabWidth = levelTabX-scoreTabX;
		levelTabWidth = tableWidth-levelTabX;

		scrollX = width - MARGIN - SCROLLBAR_WIDTH;
		scrollY = tableY;

		scrollWidth = SCROLLBAR_WIDTH;
		scrollHeight = tableHeight;

		double displayTiles = (double)scrollHeight / ENTRY_HEIGHT;
		scrollScale = Math.min(1, displayTiles/scores.size());

		scroll = Math.max(0, Math.min(scores.size()-displayTiles, scroll));
	}

	/**	Doesn't do anything.
	*/
	public void update() {

	}

	/**	Resets this table to its default state, including refreshing the scores.
	*/
	public void reset() {
		scrolling = false;
		scroll = 0.0;
		refreshScores();
	}

	/**	Retrieves the list of scores from the database so they can be displayed.
	*/
	public void refreshScores() {
		// fetch the scores in a separate thread, so we don't lag the game
		new Thread("Score Fetcher") {
			public void run() {
				try {
					scores = ScoreDAO.getAllScores(RESULTS);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Unable to fetch scores from database");
				}
			}
		}.start();
	}

	/**	Handles mouse press events for scrolling.
		@param e The mouse event
	*/
	public void mousePressed(MouseEvent e) {
		clickX = e.getX();
		clickY = e.getY();

		if (clickX > scrollX && clickX < scrollX + scrollWidth)
			scrolling = true;
	}

	/**	Handles mouse drag events for scrolling.
		@param e The mouse event
	*/
	public void mouseDragged(MouseEvent e) {
		int minScroll = scrollY + (int)(scroll*scrollHeight/scores.size()) + 1;
		int maxScroll = minScroll + (int)(scrollScale*scrollHeight);

		if (scrolling && clickY > minScroll && clickY < maxScroll) {

			int rel = e.getY() - clickY;

			double scrollamt = (double)rel*scores.size() / scrollHeight;

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
		@param e The mouse wheel event
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