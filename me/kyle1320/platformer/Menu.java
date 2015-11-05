package me.kyle1320.platformer;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

/**	A Menu that allows the user to select an option that performs an action.
	@author Kyle Cutler
	@version 1/1/14
*/
public abstract class Menu extends Displayable {
	private final int PADDING_X = 8;
	private final int PADDING_Y = 8;
	private final int TITLE_HEIGHT = 80;
	private final int TITLE_MARGIN = 30;

	private Font titleFont;

	private ArrayList<MenuOption> options;
	private int selected;
	private String title;
	private MenuStyle style;

	private GamePanel parent;

	private int rows, columns;
	private int btnWidth, btnHeight;

	private int realBtnWidth;
	private int realBtnHeight;

	private int menuWidth;
	private int menuHeight;

	private int minx;
	private int miny;

	/**	Constructs a new Menu with the given properties.
		@param parent the GamePanel that holds this menu
		@param title The tile of this menu
		@param rows The number of rows of buttons in this menu
		@param columns The number of columns of buttons in this menu
		@param maxWidth The maximum width of buttons in this menu
		@param maxHeight The maximum height of butons in this menu
		@param style the style of this menu
	*/
	public Menu(GamePanel parent, String title, int rows, int columns, int maxWidth, int maxHeight, MenuStyle style) {
		this.options = new ArrayList<MenuOption>();
		this.selected = -1;
		this.title = title;
		this.style = style;

		this.titleFont = style.font.deriveFont((float)TITLE_HEIGHT);

		this.parent = parent;

		this.rows = rows;
		this.columns = columns;
		this.btnWidth = maxWidth;
		this.btnHeight = maxHeight;
	}

	/**	Adds an option to this menu
		@param option The option to add
	*/
	public void addOption(MenuOption option) {
		options.add(option);
	}

	/**	Doesn't do anything
	*/
	public void update() {
		
	}

	/**	Resets this menu to its default state
	*/
	public void reset() {
		selected = -1;
	}

	/**	Draws this menu on the given Graphics2D object, with the given screen width and height
		@param g The Graphics2D object on which to draw
		@param width The width to draw this menu
		@param height The height to draw this menu
	*/
	public void draw(Graphics2D g, int width, int height) {
		style.background.draw(g, 0, 0, width, height);

		getSizes(width, height);

		g.setColor(style.title);
		g.setFont(titleFont);
		TextUtils.drawCenteredString(g, title, 0, 0, width, miny, false);

		for (int i=0; i < options.size() && i < rows * columns; i++) {
			int indexx = i % columns;
			int indexy = i / columns;

			int realx = minx + indexx*(realBtnWidth + PADDING_X);
			int realy = miny + indexy*(realBtnHeight + PADDING_Y);

			g.setPaint(style.buttonOutline);
			g.fillRoundRect(realx, realy, realBtnWidth, realBtnHeight, PADDING_X*2, PADDING_Y*2);

			if (selected == i)
				g.setPaint(style.buttonSelected);
			else
				g.setPaint(style.buttonUnselected);
			g.fillRoundRect(realx+PADDING_X, realy+PADDING_Y, realBtnWidth-PADDING_X*2, realBtnHeight-PADDING_Y*2, PADDING_X*2, PADDING_Y*2);

			g.setColor(style.text);
			g.setFont(style.font);
			TextUtils.drawCenteredString(g, options.get(i).getText(), realx+PADDING_X, realy+PADDING_Y, realBtnWidth-PADDING_X*2, realBtnHeight-PADDING_Y*2, true);
		}
	}

	/**	Calculates the placement and sizes for the elements displayed in this menu given a width and height
		@param width The screen width
		@param height The screen height
	*/
	private void getSizes(int width, int height) {
		realBtnWidth = Math.min((width - PADDING_X*(columns+1))/columns, btnWidth);
		realBtnHeight = Math.min((height - PADDING_Y*(rows+1) - TITLE_HEIGHT - TITLE_MARGIN*2)/rows, btnHeight);

		menuWidth = realBtnWidth*columns + PADDING_X*(columns+1);
		menuHeight = realBtnHeight*rows + PADDING_Y*(rows+1);

		minx = Math.max(PADDING_X, (width - menuWidth)/2);
		miny = TITLE_MARGIN*2 + TITLE_HEIGHT + PADDING_Y + Math.max(0, (height - TITLE_MARGIN*2 - TITLE_HEIGHT - menuHeight)/2);
	}

	/**	Calculated the selected button given a cursor x and y coordinate.
		@param x The cursor x coordinate
		@param y The cursor y coordinate
		@return The selected button index
	*/
	private int getButton(int x, int y) {
		int relx = x - minx;
		int rely = y - miny;

		if (relx < 0 || relx % (realBtnWidth + PADDING_X) > realBtnWidth ||
			rely < 0 || rely % (realBtnHeight + PADDING_Y) > realBtnHeight)
			return -1;

		int col = relx / (realBtnWidth + PADDING_X);
		int row = rely / (realBtnHeight + PADDING_Y);

		if (row < 0 || row >= rows || col < 0 || col >= columns)
			return -1;

		int index = row * columns + col;

		if (index < 0 || index >= options.size())
			return -1;

		return index;
	}

	/**	Returns the parent GamePanel.
		@return The parent GamePanel
	*/
	public GamePanel getParent() {
		return parent;
	}

	/**	Handles mouse release events for button presses.
		@param e The mouse event
	*/
	public void mouseReleased(MouseEvent e) {
		if (selected >= 0)
			options.get(selected).performAction(parent);
	}

	/**	Handles mouse release events for button selection.
		@param e The mouse event
	*/
	public void mouseMoved(MouseEvent e) {
		int over = getButton(e.getX(), e.getY());

		selected = over;
	}

	/**	Handles mouse release events for button selection.
		@param e The mouse event
	*/
	public void mouseDragged(MouseEvent e) {
		int over = getButton(e.getX(), e.getY());

		selected = over;
	}
}