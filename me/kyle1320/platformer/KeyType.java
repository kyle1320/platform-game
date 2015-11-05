package me.kyle1320.platformer;

/**	An enumeration of key "types", including their HUD icons.
	@author Kyle Cutler
	@version 1/1/14
*/
public enum KeyType {
	blue(GameSprite.hud_keyBlue, GameSprite.hud_keyBlue_disabled),
	green(GameSprite.hud_keyGreen, GameSprite.hud_keyGreen_disabled),
	red(GameSprite.hud_keyRed, GameSprite.hud_keyRed_disabled),
	yellow(GameSprite.hud_keyYellow, GameSprite.hud_keyYellow_disabled);

	private GameSprite key;
	private GameSprite keyDisabled;

	/**	Creates a new KeyType with the given icons.
		@param key The sprite to show in the HUD when the player has a key of this color
		@param keyDisabled The sprite to show in the HUD when the player does not have a key of this color
	*/
	private KeyType(GameSprite key, GameSprite keyDisabled) {
		this.key = key;
		this.keyDisabled = keyDisabled;
	}

	/**	Returns the sprite to show in the HUD when the player has a key of this color.
		@return The sprite to show in the HUD when the player has a key of this color
	*/
	public Sprite getKeySprite() {
		return key.getSprite();
	}

	/**	Returns the sprite to show in the HUD when the player does not have a key of this color.
		@return The sprite to show in the HUD when the player does not have a key of this color
	*/
	public Sprite getDisabledKeySprite() {
		return keyDisabled.getSprite();
	}
}