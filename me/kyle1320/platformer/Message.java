package me.kyle1320.platformer;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.TexturePaint;

/**	A message displayed to the user.
	@author Kyle Cutler
	@version 1/1/14
*/
public class Message implements Updatable {
	// size
	// Static constants representing the design of the mssage box.
	private static final int HEIGHT = 90;
	private static final int PADDING = 6;
	private static final int MARGIN = 10;

	// style
	private static final Color textColor = Color.BLACK;

	private static final Font textFont = PlayerHUD.gameFont.deriveFont(24.0F);

	private static final TexturePaint 	borderTexture = GameSprite.grassCenter.getTexture(),
										fillTexture = GameSprite.stoneCenter.getTexture();
	
	private String text;
	private long delay;

	/**	Creates a new empty message.
	*/
	public Message() {
		this.text = "";
		this.delay = -1;
	}

	/**	Updates this message so that it disappears after a while.
	*/
	public void update() {
		if (delay > 0) {
			delay -= UPDATE_DELAY;

			if (delay <= 0) {
				delay = 0;
				text = "";
			}
		}
	}

	/**	Draws this message on the given Graphics2D object, with the given screen width and height
		@param g The Graphics2D object on which to draw
		@param width The width to draw the display
		@param height The height to draw the display
	*/
	public void draw(Graphics2D g, int width, int height) {
		if (text.length() == 0)
			return;

		int miny = height-MARGIN-HEIGHT-PADDING*2;

		g.setPaint(borderTexture);
		g.fillRoundRect(MARGIN, miny, width-MARGIN*2, HEIGHT+PADDING*2, PADDING, PADDING);
		g.setPaint(fillTexture);
		g.fillRoundRect(MARGIN+PADDING, miny+PADDING, width-MARGIN*2-PADDING*2, HEIGHT, PADDING, PADDING);

		g.setColor(textColor);
		g.setFont(textFont);
		TextUtils.drawWrappedString(g, text, MARGIN+PADDING*2, miny+PADDING*2, width-MARGIN*2-PADDING*4, HEIGHT-PADDING*2);
	}

	/**	Returns the text of this message.
		@return The text of this message
	*/
	public String getText() {
		return text;
	}

	/**	Sets the text and disappear delay of this message.
		@param text The new text for this message
		@param delay The time, in milliseconds, before this message should disappear
	*/
	public void newMessage(String text, int delay) {
		this.text = text;
		this.delay = delay;
	}

	/**	Sets the text of this message.
		@param text The new text for this message
	*/
	public void setText(String text) {
		this.text = text;
	}

	/**	Sets the disappear delay of this message.
		@param delay The time, in milliseconds, before this message should disappear
	*/
	public void setDelay(int delay) {
		this.delay = delay;
	}
}