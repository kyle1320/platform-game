package me.kyle1320.platformer;

/**	A static class used for parsing data into a Tile.
	@author Kyle Cutler
	@version 1/4/13
*/
public final class TileParser {
	/**	<pre>Parses a tile from the given data relative to the given Tile.
Tile data is parsed as follows:
	0:	int or String 	If the data starts with the character '#', the entry is read as a pair of relative coordinates [x, y]. The material of the tile will be copied from the tile they point to.
	1:	int 			Relative x coordinate
	2:	int 			Relative y coordinate
	3: 	String 			Tile data, $ if same as tile material was copied from, or @ if the tile data already at the position should be copied</pre>
	
		@param rel The til to parse relative to
		@param data The String to parse
		@return The Tile parsed from the given data
	*/
	public static final Tile parse(Tile rel, String data) {
		TileData tileData = TileData.create(data);
		Material material;
		int dx, dy, nx, ny;
		String strData;
		Tile copy = rel;
		Tile replace;
		TileData newData;

		if (data.length() > 0 && data.charAt(0) == '#') {
			TileData dataCopy = TileData.create(tileData.getString(0, ""));
			copy = rel.getRelative(dataCopy.getInt(0, 0), dataCopy.getInt(1, 0));
			material = copy.getMaterial();
		} else {
			material = Material.values()[tileData.getInt(0, 0)];
		}

		dx = tileData.getInt(1, 0);
		dy = tileData.getInt(2, 0);
		nx = rel.getTileX() + dx;
		ny = rel.getTileY() + dy;

		replace = rel.getRelative(dx, dy);

		strData = tileData.getString(3, "");
		if (strData.equals("$"))
			newData = copy.getData();
		else if (strData.equals("@") && replace != null)
			newData = replace.getData();
		else
			newData = TileData.create(strData);

		Tile newTile = new Tile(material, rel.getLevel(), nx, ny);
		newTile.setData(newData);
		
		return newTile;
	}
}