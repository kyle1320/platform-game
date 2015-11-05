package me.kyle1320.platformer;

/**	An enumeration of "materials" that tiles can be made of. Each has a sprite and properties.
	@author Kyle Cutler
	@version 1/1/14
*/
public enum Material {
	air(GameSprite.air, new TileProperties(false, false, new AppearInteraction())),							// 0
	box(GameSprite.box, new TileProperties(new PushInteraction())),											// 1
	boxAlt(GameSprite.boxAlt, new TileProperties(new PushInteraction())),									// 2
	boxCoin(GameSprite.boxCoin, new TileProperties(new AppearInteraction(178, 0, 0, true, false))),			// 3
	boxCoinAlt(GameSprite.boxCoinAlt),																		// 4
	boxCoinAlt_disabled(GameSprite.boxCoinAlt_disabled),													// 5
	boxCoin_disabled(GameSprite.boxCoin_disabled),															// 6
	boxEmpty(GameSprite.boxEmpty),																			// 7
	boxExplosive(GameSprite.boxExplosive),																	// 8
	boxExplosiveAlt(GameSprite.boxExplosiveAlt),															// 9
	boxExplosive_disabled(GameSprite.boxExplosive_disabled),												// 10
	boxItem(GameSprite.boxItem),																			// 11
	boxItemAlt(GameSprite.boxItemAlt),																		// 12
	boxItemAlt_disabled(GameSprite.boxItemAlt_disabled),													// 13
	boxItem_disabled(GameSprite.boxItem_disabled),															// 14
	boxWarning(GameSprite.boxWarning),																		// 15
	brickWall(GameSprite.brickWall, new TileProperties(new AppearInteraction())),							// 16
	bridge(GameSprite.bridge),																				// 17
	bridgeLogs(GameSprite.bridgeLogs),																		// 18
	castle(GameSprite.castle),																				// 19
	castleCenter(GameSprite.castleCenter),																	// 20
	castleCenter_rounded(GameSprite.castleCenter_rounded),													// 21
	castleCliffLeft(GameSprite.castleCliffLeft),															// 22
	castleCliffLeftAlt(GameSprite.castleCliffLeftAlt),														// 23
	castleCliffRight(GameSprite.castleCliffRight),															// 24
	castleCliffRightAlt(GameSprite.castleCliffRightAlt),													// 25
	castleHalf(GameSprite.castleHalf),																		// 26
	castleHalfLeft(GameSprite.castleHalfLeft),																// 27
	castleHalfMid(GameSprite.castleHalfMid),																// 28
	castleHalfRight(GameSprite.castleHalfRight),															// 29
	castleHillLeft(GameSprite.castleHillLeft),																// 30
	castleHillLeft2(GameSprite.castleHillLeft2),															// 31
	castleHillRight(GameSprite.castleHillRight),															// 32
	castleHillRight2(GameSprite.castleHillRight2),															// 33
	castleLedgeLeft(GameSprite.castleLedgeLeft),															// 34
	castleLedgeRight(GameSprite.castleLedgeRight),															// 35
	castleLeft(GameSprite.castleLeft),																		// 36
	castleMid(GameSprite.castleMid),																		// 37
	castleRight(GameSprite.castleRight),																	// 38
	dirt(GameSprite.dirt),																					// 39
	dirtCenter(GameSprite.dirtCenter),																		// 40
	dirtCenter_rounded(GameSprite.dirtCenter_rounded),														// 41
	dirtCliffLeft(GameSprite.dirtCliffLeft),																// 42
	dirtCliffLeftAlt(GameSprite.dirtCliffLeftAlt),															// 43
	dirtCliffRight(GameSprite.dirtCliffRight),																// 44
	dirtCliffRightAlt(GameSprite.dirtCliffRightAlt),														// 45
	dirtHalf(GameSprite.dirtHalf),																			// 46
	dirtHalfLeft(GameSprite.dirtHalfLeft),																	// 47
	dirtHalfMid(GameSprite.dirtHalfMid),																	// 48
	dirtHalfRight(GameSprite.dirtHalfRight),																// 49
	dirtHillLeft(GameSprite.dirtHillLeft),																	// 50
	dirtHillLeft2(GameSprite.dirtHillLeft2),																// 51
	dirtHillRight(GameSprite.dirtHillRight),																// 52
	dirtHillRight2(GameSprite.dirtHillRight2),																// 53
	dirtLedgeLeft(GameSprite.dirtLedgeLeft),																// 54
	dirtLedgeRight(GameSprite.dirtLedgeRight),																// 55
	dirtLeft(GameSprite.dirtLeft),																			// 56
	dirtMid(GameSprite.dirtMid),																			// 57
	dirtRight(GameSprite.dirtRight),																		// 58
	door_closedMid(GameSprite.door_closedMid, new TileProperties(false)),									// 59
	door_closedTop(GameSprite.door_closedTop, new TileProperties(false)),									// 60
	door_openMid(GameSprite.door_openMid, new TileProperties(false, false, new TeleportInteraction())),		// 61
	door_openTop(GameSprite.door_openTop, new TileProperties(false)),										// 62
	fence(GameSprite.fence, new TileProperties(false)),														// 63
	fenceBroken(GameSprite.fenceBroken, new TileProperties(false)),											// 64
	grass(GameSprite.grass),																				// 65
	grassCenter(GameSprite.grassCenter),																	// 66
	grassCenter_rounded(GameSprite.grassCenter_rounded),													// 67
	grassCliffLeft(GameSprite.grassCliffLeft),																// 68
	grassCliffLeftAlt(GameSprite.grassCliffLeftAlt),														// 69
	grassCliffRight(GameSprite.grassCliffRight),															// 70
	grassCliffRightAlt(GameSprite.grassCliffRightAlt),														// 71
	grassHalf(GameSprite.grassHalf),																		// 72
	grassHalfLeft(GameSprite.grassHalfLeft),																// 73
	grassHalfMid(GameSprite.grassHalfMid),																	// 74
	grassHalfRight(GameSprite.grassHalfRight),																// 75
	grassHillLeft(GameSprite.grassHillLeft),																// 76
	grassHillLeft2(GameSprite.grassHillLeft2),																// 77
	grassHillRight(GameSprite.grassHillRight),																// 78
	grassHillRight2(GameSprite.grassHillRight2),															// 79
	grassLedgeLeft(GameSprite.grassLedgeLeft),																// 80
	grassLedgeRight(GameSprite.grassLedgeRight),															// 81
	grassLeft(GameSprite.grassLeft),																		// 82
	grassMid(GameSprite.grassMid),																			// 83
	grassRight(GameSprite.grassRight),																		// 84
	hill_large(GameSprite.hill_large),																		// 85
	hill_largeAlt(GameSprite.hill_largeAlt),																// 86
	hill_small(GameSprite.hill_small),																		// 87
	hill_smallAlt(GameSprite.hill_smallAlt),																// 88
	ladder_mid(GameSprite.ladder_mid, new TileProperties(false, true)),										// 89
	ladder_top(GameSprite.ladder_top, new TileProperties(false, true)),										// 90
	liquidLava(GameSprite.liquidLava),																		// 91
	liquidLavaTop(GameSprite.liquidLavaTop),																// 92
	liquidLavaTop_mid(GameSprite.liquidLavaTop),															// 93*
	liquidWater(GameSprite.liquidWater),																	// 94
	liquidWaterTop(GameSprite.liquidWaterTop),																// 95
	liquidWaterTop_mid(GameSprite.liquidWaterTop),															// 96*
	lock_blue(GameSprite.lock_blue, new TileProperties(new LockInteraction(KeyType.blue))), 				// 97
	lock_green(GameSprite.lock_green, new TileProperties(new LockInteraction(KeyType.green))),				// 98
	lock_red(GameSprite.lock_red, new TileProperties(new LockInteraction(KeyType.red))),					// 99
	lock_yellow(GameSprite.lock_yellow, new TileProperties(new LockInteraction(KeyType.yellow))),			// 100
	rockHillLeft(GameSprite.rockHillLeft),																	// 101
	rockHillRight(GameSprite.rockHillRight),																// 102
	ropeAttached(GameSprite.ropeAttached),																	// 103
	ropeHorizontal(GameSprite.ropeHorizontal),																// 104
	ropeVertical(GameSprite.ropeVertical),																	// 105
	sand(GameSprite.sand),																					// 106
	sandCenter(GameSprite.sandCenter),																		// 107
	sandCenter_rounded(GameSprite.sandCenter_rounded),														// 108
	sandCliffLeft(GameSprite.sandCliffLeft),																// 109
	sandCliffLeftAlt(GameSprite.sandCliffLeftAlt),															// 110
	sandCliffRight(GameSprite.sandCliffRight),																// 111
	sandCliffRightAlt(GameSprite.sandCliffRightAlt),														// 112
	sandHalf(GameSprite.sandHalf),																			// 113
	sandHalfLeft(GameSprite.sandHalfLeft),																	// 114
	sandHalfMid(GameSprite.sandHalfMid),																	// 115
	sandHalfRight(GameSprite.sandHalfRight),																// 116
	sandHillLeft(GameSprite.sandHillLeft),																	// 117
	sandHillLeft2(GameSprite.sandHillLeft2),																// 118
	sandHillRight(GameSprite.sandHillRight),																// 119
	sandHillRight2(GameSprite.sandHillRight2),																// 120
	sandLedgeLeft(GameSprite.sandLedgeLeft),																// 121
	sandLedgeRight(GameSprite.sandLedgeRight),																// 122
	sandLeft(GameSprite.sandLeft),																			// 123
	sandMid(GameSprite.sandMid),																			// 124
	sandRight(GameSprite.sandRight),																		// 125
	sign(GameSprite.sign, new TileProperties(false, false, new NotifyInteraction())),						// 126
	signExit(GameSprite.signExit, new TileProperties(false)),												// 127
	signLeft(GameSprite.signLeft, new TileProperties(false)),												// 128
	signRight(GameSprite.signRight, new TileProperties(false)),												// 129
	snow(GameSprite.snow),																					// 130
	snowCenter(GameSprite.snowCenter),																		// 131
	snowCenter_rounded(GameSprite.snowCenter_rounded),														// 132
	snowCliffLeft(GameSprite.snowCliffLeft),																// 133
	snowCliffLeftAlt(GameSprite.snowCliffLeftAlt),															// 134
	snowCliffRight(GameSprite.snowCliffRight),																// 135
	snowCliffRightAlt(GameSprite.snowCliffRightAlt),														// 136
	snowHalf(GameSprite.snowHalf),																			// 137
	snowHalfLeft(GameSprite.snowHalfLeft),																	// 138
	snowHalfMid(GameSprite.snowHalfMid),																	// 139
	snowHalfRight(GameSprite.snowHalfRight),																// 140
	snowHillLeft(GameSprite.snowHillLeft),																	// 141
	snowHillLeft2(GameSprite.snowHillLeft2),																// 142
	snowHillRight(GameSprite.snowHillRight),																// 143
	snowHillRight2(GameSprite.snowHillRight2),																// 144
	snowLedgeLeft(GameSprite.snowLedgeLeft),																// 145
	snowLedgeRight(GameSprite.snowLedgeRight),																// 146
	snowLeft(GameSprite.snowLeft),																			// 147
	snowMid(GameSprite.snowMid),																			// 148
	snowRight(GameSprite.snowRight),																		// 149
	stone(GameSprite.stone),																				// 150
	stoneCenter(GameSprite.stoneCenter),																	// 151
	stoneCenter_rounded(GameSprite.stoneCenter_rounded),													// 152
	stoneCliffLeft(GameSprite.stoneCliffLeft),																// 153
	stoneCliffLeftAlt(GameSprite.stoneCliffLeftAlt),														// 154
	stoneCliffRight(GameSprite.stoneCliffRight),															// 155
	stoneCliffRightAlt(GameSprite.stoneCliffRightAlt),														// 156
	stoneHalf(GameSprite.stoneHalf),																		// 157
	stoneHalfLeft(GameSprite.stoneHalfLeft),																// 158
	stoneHalfMid(GameSprite.stoneHalfMid),																	// 159
	stoneHalfRight(GameSprite.stoneHalfRight),																// 160
	stoneHillLeft2(GameSprite.stoneHillLeft2),																// 161
	stoneHillRight2(GameSprite.stoneHillRight2),															// 162
	stoneLedgeLeft(GameSprite.stoneLedgeLeft),																// 163
	stoneLedgeRight(GameSprite.stoneLedgeRight),															// 164
	stoneLeft(GameSprite.stoneLeft),																		// 165
	stoneMid(GameSprite.stoneMid),																			// 166
	stoneRight(GameSprite.stoneRight),																		// 167
	stoneWall(GameSprite.stoneWall),																		// 168
	torchLit(GameSprite.torchLit, new TileProperties(false)),												// 169
	torch(GameSprite.torch, new TileProperties(false, false, new AppearInteraction(169, 0, 0, true, true))),// 170
	window(GameSprite.window),																				// 171
	keyBlue(GameSprite.keyBlue, new TileProperties(false, false, new KeyInteraction(KeyType.blue))),		// 172
	keyGreen(GameSprite.keyGreen, new TileProperties(false, false, new KeyInteraction(KeyType.green))),		// 173
	keyRed(GameSprite.keyRed, new TileProperties(false, false, new KeyInteraction(KeyType.red))),			// 174
	keyYellow(GameSprite.keyYellow, new TileProperties(false, false, new KeyInteraction(KeyType.yellow))),	// 175
	coinBronze(GameSprite.coinBronze, new TileProperties(false, false, new CoinInteraction(1))),			// 176
	coinSilver(GameSprite.coinSilver, new TileProperties(false, false, new CoinInteraction(5))),			// 177
	coinGold(GameSprite.coinGold, new TileProperties(false, false, new CoinInteraction(25))),				// 178
	spikes(GameSprite.spikes, new TileProperties(false)),													// 179
	bush(GameSprite.bush, new TileProperties(false)),														// 180
	cactus(GameSprite.cactus, new TileProperties(false)),													// 181
	plant(GameSprite.plant, new TileProperties(false)),														// 182
	rock(GameSprite.rock, new TileProperties(false)),														// 183
	snowhill(GameSprite.snowhill, new TileProperties(false)),												// 184
	mushroomBrown(GameSprite.mushroomBrown, new TileProperties(false)),										// 185
	mushroomRed(GameSprite.mushroomRed, new TileProperties(false)),											// 186
	flagBlue(GameSprite.flagBlue, new TileProperties(false, false, new FinishInteraction())),				// 187
	buttonBlue_pressed(GameSprite.buttonBlue_pressed),																				// 188
	buttonBlue(GameSprite.buttonBlue, new TileProperties(true, false, new ButtonInteraction(Material.buttonBlue_pressed))),			// 189
	buttonGreen_pressed(GameSprite.buttonGreen_pressed),																			// 190
	buttonGreen(GameSprite.buttonGreen, new TileProperties(true, false, new ButtonInteraction(Material.buttonGreen_pressed))),		// 191
	buttonRed_pressed(GameSprite.buttonRed_pressed),																				// 192
	buttonRed(GameSprite.buttonRed, new TileProperties(true, false, new ButtonInteraction(Material.buttonRed_pressed))),			// 193
	buttonYellow_pressed(GameSprite.buttonYellow_pressed),																			// 194
	buttonYellow(GameSprite.buttonYellow, new TileProperties(true, false, new ButtonInteraction(Material.buttonYellow_pressed))),	// 195
	boxBlue(GameSprite.boxBlue),																			// 196
	boxGreen(GameSprite.boxGreen),																			// 197
	boxRed(GameSprite.boxRed),																				// 198
	boxYellow(GameSprite.boxYellow),																		// 199
	markerBlue(GameSprite.markerBlue),																		// 200
	markerGreen(GameSprite.markerGreen),																	// 201
	markerRed(GameSprite.markerRed),																		// 202
	markerYellow(GameSprite.markerYellow),																	// 203
	invisibleWall(GameSprite.air);																			// 204

	// so we don't have a forward declaration in the values
	static {
		buttonBlue_pressed.properties = new TileProperties(true, false, new ButtonPressedInteraction(buttonBlue));
		buttonGreen_pressed.properties = new TileProperties(true, false, new ButtonPressedInteraction(buttonGreen));
		buttonRed_pressed.properties = new TileProperties(true, false, new ButtonPressedInteraction(buttonRed));
		buttonYellow_pressed.properties = new TileProperties(true, false, new ButtonPressedInteraction(buttonYellow));
	}

	private GameSprite sprite;
	private TileProperties properties;

	/**	Creates a new Material with the given sprite and default properties
		@param sprite The GameSprite that represents this Material
	*/
	private Material(GameSprite sprite) {
		this.sprite = sprite;
		this.properties = new TileProperties();
	}

	/**	Creates a new Material with the given sprite and properties
		@param sprite The GameSprite that represents this Material
		@param properties The TileProperties that describe tiles of this material
	*/
	private Material(GameSprite sprite, TileProperties properties) {
		this.sprite = sprite;
		this.properties = properties;
	}

	/**	Returns the shape of this material's sprite
		@return The shape of this material's sprite
	*/
	public Shape getSpriteShape() {
		return sprite.getSprite().getShape();
	}

	/**	Returns a copy of this Material's sprite
		@return A copy of this Material's sprite
	*/
	public Sprite getSprite() {
		return sprite.getSprite();
	}

	/**	Returns a copy of this Material's properties
		@return A copy of this Material's properties
	*/
	public TileProperties getProperties() {
		return properties;
	}
}