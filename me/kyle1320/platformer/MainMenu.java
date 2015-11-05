package me.kyle1320.platformer;

import java.awt.Color;
import javax.swing.JOptionPane;

/**	A Menu that contains: 
		a button to show the Level Select screen, 
		a button to show the High Score table, 
		a button to open the About Screen, 
		and a button to exit the game.
	@author Kyle Cutler
	@version 1/1/14
*/
public class MainMenu extends Menu {
	private static final MenuStyle scheme = new MenuStyle(PlayerHUD.gameFont, Background.normal, GameSprite.liquidWater, GameSprite.liquidLava, GameSprite.grassCenter, Color.WHITE, Color.BLACK);

	/**	Creates a new MainMenu with the given GamePanel as its parent.
		@param parent The GamePanel that holds this menu
	*/
	public MainMenu(GamePanel parent) {
		super(parent, "Platform Game", 2, 2, 320, 160, scheme);

		addOption(new MenuOption("Level Select", new MenuAction() {
												public void performAction(GamePanel parent) {
													parent.levelSelect();
												}
											}));
		addOption(new MenuOption("High Scores", new MenuAction() {
												public void performAction(GamePanel parent) {
													parent.highScores();
												}
											}));
		/*addOption(new MenuOption("Level Editor", new MenuAction() {
												public void performAction(GamePanel parent) {
													parent.levelEditor();
												}
											}));*/
		addOption(new MenuOption("About", new MenuAction() {
												public void performAction(GamePanel parent) {
													parent.aboutScreen();
												}
											}));
		addOption(new MenuOption("Exit", new MenuAction() {
												public void performAction(GamePanel parent) {
													parent.exit();
												}
											}));
	}
}