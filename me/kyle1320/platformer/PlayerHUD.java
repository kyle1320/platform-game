package me.kyle1320.platformer;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.AlphaComposite;
import java.awt.*;
import java.awt.font.*;
import java.text.*;

/**	The overlay to an InGameView that shows information about the player.
	@author Kyle Cutler
	@version 1/1/14
*/
public class PlayerHUD {
	// size
	// Static constants representing the design of the HUD.
	private static final int MARGIN = 10;
	private static final int PADDING = 6;
	private static final int ELEMENT_SIZE = 32;

	// style
	// Public static constant font to be used by every class when text is drawn.
	public static final Font gameFont = new Font("Calibri", Font.BOLD, 32);

	private Player player;

	/**	Creates a new PlayerHUD with the given player
		@param player The player to display information about
	*/
	public PlayerHUD(Player player) {
		this.player = player;
	}

	/**	Draws the player HUD on the given Graphics2D object, with the given screen width and height
		@param g The Graphics2D object on which to draw
		@param width The width to draw the display
		@param height The height to draw the display
	*/
	public void draw(Graphics2D g, int width, int height) {
		player.getMessage().draw(g, width, height);	// draw the message being displayed to the player, if any
		
		for (KeyType t : KeyType.values()) {		// draw each key type
			if (player.hasKey(t))					// if the player has the key, draw the proper sprite
				t.getKeySprite().draw(g, MARGIN + t.ordinal()*(ELEMENT_SIZE+PADDING), MARGIN/* + ELEMENT_SIZE + PADDING*/, ELEMENT_SIZE, ELEMENT_SIZE);
			else									// otherwise draw the empty key sprite
				t.getDisabledKeySprite().draw(g, MARGIN + t.ordinal()*(ELEMENT_SIZE+PADDING), MARGIN/* + ELEMENT_SIZE + PADDING*/, ELEMENT_SIZE, ELEMENT_SIZE);
		}

		double alignx = width-MARGIN;				// align the score to the right side
		Sprite spr = GameSprite.hud_coins.getSprite();
		double sprWidth = (double)spr.getWidth() * ELEMENT_SIZE / spr.getHeight();
		alignx -= sprWidth;
													// draw the coin sprite
		spr.draw(g, (int)alignx, MARGIN, (int)sprWidth, ELEMENT_SIZE);

		alignx -= PADDING;

		int score = player.getPoints();

		do {										// for each digit in the player score
			spr = GameSprite.values()[GameSprite.hud_0.ordinal() + score % 10].getSprite();
			sprWidth = (double)spr.getWidth() * ELEMENT_SIZE / spr.getHeight();
			alignx -= sprWidth;
													// draw it and move back
			spr.draw(g, (int)alignx, MARGIN, (int)sprWidth, ELEMENT_SIZE);

			score /= 10;
		} while (score != 0);

		/*String timeStr = String.format("Time: %d", player.getGame().getTime()/1000);
		g.setColor(Color.WHITE);
		g.setFont(gameFont);
		TextUtils.drawHorizontalCenteredString(g, timeStr, 0, MARGIN, width, ELEMENT_SIZE, false);*/
	}
}