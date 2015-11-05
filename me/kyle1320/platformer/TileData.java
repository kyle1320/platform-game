package me.kyle1320.platformer;

import java.util.Scanner;
import java.util.ArrayList;

/**	Extra data stored with a tile. This is used in interactions.
	@author Kyle Cutler
	@version 1/1/14
*/
public class TileData {
	private String[] data;

	/**	Creates TileData from the given list of Strings.
		@param data A varargs list of Strings that make up this TileData
	*/
	public TileData(String... data) {
		this.data = data;
	}

	/**	Reads TileData from a single line in the given Scanner.
		@param in The Scanner to read the data from
		@return TileData created from the next line in the Scanner
	*/
	public static TileData read(Scanner in) {
		String line = in.nextLine().trim();
		return new TileData(split(line));
	}

	/** Formats the given string and returns TileData created from it.
		@param data The data to format
		@return TileData created from the given data
	*/
	public static TileData create(String data) {
		return new TileData(split(data));
	}

	/**	<pre>Takes a String and formats it via the following rules:
	1. data is split by semicolons, 
	2. anything surrounded by square brackets is not split</pre>
		@param data The data to format
		@return A String array of the formatted data
	*/
	private static String[] split(String data) {
        if (data == null || data.length() == 0)
            return new String[0];
        
        ArrayList<String> split = new ArrayList<String>();
        int last = 0;

        for (int i=0; i < data.length(); i++) {
            char c = data.charAt(i);
            if (c == '[') {
                int length = bracketRun(data.substring(i));

                if (length < 0)
                    continue;
                else {
                    split.add(data.substring(i+1, i+length));
                    i += length+1;
                    last = i+1;
                }
            }else if (c == ';') {
                split.add(data.substring(last, i));
                last = i+1;
            }
        }

        if (last < data.length())
            split.add(data.substring(last));

        return split.toArray(new String[0]);
    }

	/**	Determines the length of a pair of brackets.
		@param data The String to find brackets in and return their length
		@return The length of the first pait of square brackets found, or -1 if they do not match.
	*/
	private static int bracketRun(String data) {
		int count = 0;

		for (int i=0; i < data.length(); i++) {
			char c = data.charAt(i);

			if (c == '[')
				count++;
			else if (c == ']')
				count--;

			if (count == 0)
				return i;
		}

		return -1;
	}

	/**	Returns the number of entries in this data.
		@return The number of entries in this data
	*/
	public int getSize() {
		return data.length;
	}

	/**	Returns the String found at the given index.
		@param index The data index
		@return The String at that index, or an empty String if one is not found.
	*/
	public String getString(int index) {
		if (index >= 0 && index < data.length)
			return data[index];
		return "";
	}

	/**	Returns the boolean found at the given index.
		@param index The data index
		@return The boolean at that index, or false if one is not found.
	*/
	public boolean getBoolean(int index) {
		if (index >= 0 && index < data.length)
			return Boolean.valueOf(data[index]);
		return false;
	}

	/**	Returns the int found at the given index.
		@param index The data index
		@return The int at that index, or -1 if one is not found.
	*/
	public int getInt(int index) {
		if (index >= 0 && index < data.length)
			try {
				return Integer.parseInt(data[index]);
			} catch (NumberFormatException e) {}
		return -1;
	}

	/**	Returns the double found at the given index.
		@param index The data index
		@return The double at that index, or -1.0 if one is not found.
	*/
	public double getDouble(int index) {
		if (index >= 0 && index < data.length)
			try {
				return Double.parseDouble(data[index]);
			} catch (NumberFormatException e) {}
		return -1.0;
	}

	/**	Returns the String found at the given index.
		@param index The data index
		@param def The default value to return if the index is out of bounds or a String is not found
		@return The String at that index, the default value if one is not found.
	*/
	public String getString(int index, String def) {
		if (index >= 0 && index < data.length)
			if (!data[index].equals(""))
				return data[index];
		return def;
	}

	/**	Returns the boolean found at the given index.
		@param index The data index
		@param def The default value to return if the index is out of bounds or a boolean is not found
		@return The boolean at that index, the default value if one is not found.
	*/
	public boolean getBoolean(int index, boolean def) {
		if (index >= 0 && index < data.length)
			if (!data[index].equals(""))
				return Boolean.valueOf(data[index]);
		return def;
	}

	/**	Returns the int found at the given index.
		@param index The data index
		@param def The default value to return if the index is out of bounds or a int is not found
		@return The int at that index, the default value if one is not found.
	*/
	public int getInt(int index, int def) {
		if (index >= 0 && index < data.length)
			if (!data[index].equals(""))
				try {
					return Integer.parseInt(data[index]);
				} catch (NumberFormatException e) {}
		return def;
	}

	/**	Returns the double found at the given index.
		@param index The data index
		@param def The default value to return if the index is out of bounds or a double is not found
		@return The double at that index, the default value if one is not found.
	*/
	public double getDouble(int index, double def) {
		if (index >= 0 && index < data.length)
			if (!data[index].equals(""))
				try {
					return Double.parseDouble(data[index]);
				} catch (NumberFormatException e) {}
		return def;
	}
}