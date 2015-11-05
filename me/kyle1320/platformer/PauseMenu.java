package me.kyle1320.platformer;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**	A Menu that contains: 
		a button to return to the active game, 
		and a button to return to the Main Menu.
	@author Kyle Cutler
	@version 1/1/14
*/
public class PauseMenu extends Menu {
	// Static constant representing the style of this type of Menu.
	private static final MenuStyle scheme = new MenuStyle(PlayerHUD.gameFont, Background.normal, GameSprite.liquidWater, GameSprite.liquidLava, GameSprite.grassCenter, Color.WHITE, Color.BLACK);

	/**	Creates a new PauseMenu with the given GamePanel as its parent.
		@param parent The GamePanel that holds this menu
	*/
	public PauseMenu(GamePanel parent) {
		super(parent, "Paused", 3, 1, 640, 160, scheme);

		addOption(new MenuOption("Return", new MenuAction() {
												public void performAction(GamePanel parent) {
													parent.returnToGame();
												}
											}));
		addOption(new MenuOption("Restart Level", new MenuAction() {
												public void performAction(GamePanel parent) {
													parent.reloadLevel();
												}
											}));
		addOption(new MenuOption("Main Menu", new MenuAction() {
												public void performAction(GamePanel parent) {
													parent.mainMenu();
												}
											}));
	}

	/**	This menu can be exited by pressing the escape key.
		@param e The key event
	*/
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				getParent().pauseMenu();
				break;
		}
	}
}