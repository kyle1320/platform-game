package me.kyle1320.platformer;

import java.awt.Color;
import java.awt.TexturePaint;
import java.awt.Font;

/**	Represents the style of a Menu.
	@author Kyle Cutler
	@version 1/1/14
*/
public final class MenuStyle {
	public final Font font;
	public final Background background;
	public final TexturePaint 	buttonUnselected,
								buttonSelected,
								buttonOutline;
	public final Color text, title;

	/**	Creates a new MenuStyle with the given values.
		@param font The Font to be displayed in the Menu
		@param bg The Background to be drawn behind the Menu
		@param btn The Texture a button is to be given when it is not selected
		@param btns The Texture a button is to be given when it is selected
		@param out The Texture to outline a button with
		@param text The Color that button text is to be
		@param title The Color the menu title is to be
	*/
	public MenuStyle(Font font, Background bg, GameSprite btn, GameSprite btns, GameSprite out, Color text, Color title) {
		this.font = font;
		//this.title = title.getSprite();
		this.background = bg;
		this.buttonUnselected = btn.getTexture();
		this.buttonSelected = btns.getTexture();
		this.buttonOutline = out.getTexture();
		this.text = text;
		this.title = title;
	}
}