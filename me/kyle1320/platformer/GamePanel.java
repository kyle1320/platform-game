package me.kyle1320.platformer;

import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.*;

import java.awt.geom.AffineTransform;

/**	The JPanel that displays the game.
	@author Kyle Cutler
	@version 12/31/13
*/
public class GamePanel extends JPanel implements Updatable {
	// the screen being displayed
	private Displayable display;

	// szeparate threads for updates and screen redraws
	private UpdateThread update;
	private RepaintThread repaint;

	// save different screens as instance variables so they can be loaded at any time
	private InGameView game;
	private Menu mainMenu;
	private Menu pauseMenu;
	private WinScreen winScreen;
	private AboutScreen aboutScreen;
	private HighScoreTable scoreTable;
	private LevelEditor levelEditor;
	private LevelSelectScreen levelSelect;

	/**	Redraws the current view on this panel
		@param g the Graphics object on which to draw
	*/
	public void paintComponent(Graphics g) {
		//System.out.println("start " + System.currentTimeMillis());

		int width = (int)g.getClipBounds().getWidth();
		int height = (int)g.getClipBounds().getHeight();

		Graphics2D g2 = (Graphics2D)g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

		display.draw(g2, width, height);

		//System.out.println("stop " + System.currentTimeMillis());
	}

	/**	Creates a new GamePanel.
	*/
	public GamePanel() {
		game = new InGameView(this);
		mainMenu = new MainMenu(this);
		pauseMenu = new PauseMenu(this);
		winScreen = new WinScreen(this);
		aboutScreen = new AboutScreen(this);
		scoreTable = new HighScoreTable(this);
		levelEditor = new LevelEditor(this);
		levelSelect = new LevelSelectScreen(this);

		display = mainMenu;

		update = new UpdateThread(this);
		repaint = new RepaintThread(this);

		Input input = new Input();
		this.addKeyListener(input);
		this.addMouseListener(input);
		this.addMouseMotionListener(input);

		this.setPreferredSize(new Dimension(800, 600));
		this.setFocusable(true);
		addListeners();
	}

	/**	Stops the threads and exits the program.
	*/
	public void exit() {
		update.stop();
		repaint.stop();
		System.exit(0);
	}

	/**	If there is a game in progress, this method switches to the game view.
	*/
	public void returnToGame() {
		if (game.hasGame())
			setDisplay(game);
		else
			System.out.println("Cannot return to unloaded game!");
	}

	/**	Switches to the main menu.
	*/
	public void mainMenu() {
		setDisplay(mainMenu);
	}

	/**	Switches to the pause menu.
	*/
	public void pauseMenu() {
		setDisplay(pauseMenu);
	}

	/** Sets the display to the level complete screen
	*/
	public void winScreen() {
		if (game.hasGame())
			winScreen.setGame(game.getGame());
		setDisplay(winScreen);
	}

	/**	Displays the "About" message screen.
	*/
	public void aboutScreen() {
		setDisplay(aboutScreen);
	}

	/**	Switches to the high score table.
	*/
	public void highScores() {
		setDisplay(scoreTable);
	}

	/**	Switches to the level editor.
	*/
	public void levelEditor() {
		setDisplay(levelEditor);
	}

	/**	Switches to the level select screen.
	*/
	public void levelSelect() {
		setDisplay(levelSelect);
	}

	/**	Reloads the level currently being played.
	*/
	public void reloadLevel() {
		game.reloadLevel();
		setDisplay(game);
	}

	/**	Loads the given level and switches to the game view.
		@param level The new level to load
	*/
	public void goToLevel(Level level) {
		game.loadGame(new GameState(level));

		setDisplay(game);
	}

	/**	Starts the update and repaint threads.
	*/
	public void start() {
		update.start();
		repaint.start();
	}

	/**	Pauses the update and repaint threads.
	*/
	public void stop() {
		update.stop();
		repaint.start();
	}

	/**	Updates the view being displayed.
	*/
	public void update() {
		display.update();
	}

	/**	Sets the view to be displayed, and adds listeners for the new display
		@param display The display to switch to
	*/
	private void setDisplay(Displayable display) {
		removeListeners();
		display.reset();
		this.display = display;
		addListeners();
	}

	/**	removes the current display from this panel's listeners
	*/
	private void removeListeners() {
		this.removeKeyListener(display);
		this.removeMouseListener(display);
		this.removeMouseMotionListener(display);
		this.removeMouseWheelListener(display);
	}

	/**	annd the current display to this panel's listeners
	*/
	private void addListeners() {
		this.addKeyListener(display);
		this.addMouseListener(display);
		this.addMouseMotionListener(display);
		this.addMouseWheelListener(display);
	}
}