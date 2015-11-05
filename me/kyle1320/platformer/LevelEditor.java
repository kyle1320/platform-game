package me.kyle1320.platformer;

import java.util.HashMap;
import java.util.Scanner;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.TexturePaint;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JOptionPane;

import java.io.File;
import java.io.PrintWriter;

public class LevelEditor extends Displayable {
	private static final int MARGIN = 50;

	private static final int TITLE_HEIGHT = 50;

	private static final int BUTTONS = 5;
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 50;
	private static final int PADDING = 10;

	private static final int LIST_WIDTH = 200;
	private static final int ENTRY_HEIGHT = 40;
	private static final int SCROLLBAR_WIDTH = 16;

	private static final int TILE_SIZE = 50;

	private static final int LEFT_KEY = KeyEvent.VK_A;
	private static final int RIGHT_KEY = KeyEvent.VK_D;
	private static final int UP_KEY = KeyEvent.VK_W;
	private static final int DOWN_KEY = KeyEvent.VK_S;

	private static final Background background = Background.castle;

	private static final Color titleColor = Color.WHITE;
	private static final Color buttonColor = Color.WHITE;
	private static final Color entryColor = Color.BLACK;

	private static final Font titleFont = PlayerHUD.gameFont.deriveFont((float)TITLE_HEIGHT);
	private static final Font buttonFont = PlayerHUD.gameFont.deriveFont((float)BUTTON_HEIGHT/2);
	private static final Font entryFont = PlayerHUD.gameFont.deriveFont((float)ENTRY_HEIGHT/2);

	private static final TexturePaint 	listTexture = GameSprite.grassCenter.getTexture(),
										buttonTexture = GameSprite.liquidWater.getTexture(),
										buttonSelectedTexture = GameSprite.liquidLava.getTexture(),
										scrollTexture = GameSprite.castleCenter.getTexture(),
										scrollBarTexture = GameSprite.liquidLava.getTexture();

	private GamePanel parent;

	private Background levelBackground;
	private HashMap<Coord, Tile> tiles;
	private HashMap<Coord, String> customData;
	private double levelScrollX, levelScrollY;
	private int selectedIndex;

	private int selectedX;
	private int selectedY;

	private int selectedButton;

	private int playerStartX, playerStartY;

	private Material[] materials;
	private double scroll;

	private int clickX, clickY;
	private boolean scrolling;
	private boolean deleting;

	private int buttonsX, buttonsY, buttonWidth, buttonHeight;
	private int levelX, levelY, levelWidth, levelHeight;
	private int listX, listY, listWidth, listHeight;
	private double scrollScale;

	private class Tile {
		Material material;
		int x, y;

		public Tile(Material material, int x, int y) {
			this.material = material;
			this.x = x;
			this.y = y;
		}
	}

	private class Coord {
		int x, y;

		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int hashCode() {
			return (x << 16) ^ y;
		}

		public boolean equals(Object other) {
	        Coord o = (Coord)other;
	        return x == o.x && y == o.y;
	    }
	}

	public LevelEditor(GamePanel parent) {
		this.parent = parent;

		levelBackground = Background.normal;
		tiles = new HashMap<Coord, Tile>();
		customData = new HashMap<Coord, String>();

		materials = Material.values();

		reset();
	}

	public void update() {
		int mx = Input.getMouseX();
		int my = Input.getMouseY();

		if (mx > levelX && mx < levelX + levelWidth &&
			my > levelY && my < levelY + levelHeight) {
			selectedX = (int)((double)(mx - levelX) / TILE_SIZE + levelScrollX);
			selectedY = (int)((double)(my - levelY) / TILE_SIZE + levelScrollY);
		} else if (	mx > buttonsX && mx < buttonsX + buttonWidth &&
					my > buttonsY && my < buttonsY + BUTTONS*(buttonHeight + PADDING)) {
			int relY = my - buttonsY;
			int overlapY = relY % (buttonHeight + PADDING);
			
			if (overlapY < buttonHeight)
				selectedButton = relY / (buttonHeight + PADDING);
			else
				selectedButton = -1;
		} else {
			selectedButton = -1;
		}

		if (Input.isKeyPressed(UP_KEY))
			levelScrollY -= UPDATE_DELAY/100.0;
		if (Input.isKeyPressed(DOWN_KEY))
			levelScrollY += UPDATE_DELAY/100.0;
		if (Input.isKeyPressed(LEFT_KEY))
			levelScrollX -= UPDATE_DELAY/100.0;
		if (Input.isKeyPressed(RIGHT_KEY))
			levelScrollX += UPDATE_DELAY/100.0;
	}

	public void reset() {
		levelBackground = Background.normal;
		tiles.clear();
		customData.clear();

		levelScrollX = 0;
		levelScrollY = 0;
		selectedIndex = -1;
		selectedX = 0;
		selectedY = 0;
		selectedButton = -1;
		scroll = 0;

		scrolling = false;
		deleting = false;
	}

	public void draw(Graphics2D g, int width, int height) {
		getSizes(width, height);

		background.draw(g, 0, 0, width, height);

		g.setColor(titleColor);
		g.setFont(titleFont);
		TextUtils.drawCenteredString(g, "Level Editor", 0, MARGIN, width, TITLE_HEIGHT, false);

		Graphics2D level = (Graphics2D)g.create(levelX, levelY, levelWidth, levelHeight);
		Graphics2D list = (Graphics2D)g.create(listX, listY, listWidth, listHeight);

		for (int i=0; i < BUTTONS; i++) {
			if (i == selectedButton)
				g.setPaint(buttonSelectedTexture);
			else
				g.setPaint(buttonTexture);
			g.fillRoundRect(buttonsX, buttonsY + i*(buttonHeight + PADDING), buttonWidth, buttonHeight, PADDING*2, PADDING*2);
		}

		g.setColor(buttonColor);
		g.setFont(buttonFont);
		TextUtils.drawCenteredString(g, "Load", buttonsX, buttonsY, buttonWidth, buttonHeight, false);
		TextUtils.drawCenteredString(g, "Save", buttonsX, buttonsY + (buttonHeight + PADDING), buttonWidth, buttonHeight, false);
		TextUtils.drawCenteredString(g, "Clear", buttonsX, buttonsY + 2*(buttonHeight + PADDING), buttonWidth, buttonHeight, false);
		TextUtils.drawCenteredString(g, "Help", buttonsX, buttonsY + 3*(buttonHeight + PADDING), buttonWidth, buttonHeight, false);
		TextUtils.drawCenteredString(g, "Exit", buttonsX, buttonsY + 4*(buttonHeight + PADDING), buttonWidth, buttonHeight, false);

		int minx = (int)levelScrollX;
		int miny = (int)levelScrollY;

		int tilesx = (int)Math.ceil((double)levelWidth / TILE_SIZE) + 1;
		int tilesy = (int)Math.ceil((double)levelHeight / TILE_SIZE) + 1;

		int startx = (int)((-levelScrollX%1)*TILE_SIZE);
		int starty = (int)((-levelScrollY%1)*TILE_SIZE);

		levelBackground.draw(level, 0, 0, levelWidth, levelHeight);

		for (int y=0; y < tilesy; y++) {
			for (int x=0; x < tilesx; x++) {
				Tile t = tiles.get(new Coord(x+minx, y+miny));
				if (t != null)
					t.material.getSprite().draw(level, startx+x*TILE_SIZE, starty+y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
			}
		}

		level.setColor(Color.GREEN);
		level.drawRect((int)((playerStartX-levelScrollX)*TILE_SIZE), (int)((playerStartY-levelScrollY)*TILE_SIZE), TILE_SIZE, TILE_SIZE);

		if (deleting)
			level.setColor(Color.RED);
		else
			level.setColor(Color.BLACK);
		level.drawRect((int)((selectedX-levelScrollX)*TILE_SIZE), (int)((selectedY-levelScrollY)*TILE_SIZE), TILE_SIZE, TILE_SIZE);

		int curry = (int)((-scroll%1)*ENTRY_HEIGHT);

		int firstIndex = (int)scroll;

		list.setPaint(listTexture);
		list.fillRect(0, 0, listWidth-SCROLLBAR_WIDTH, listHeight);

		for (int i=firstIndex; i < materials.length; i++) {
			Material material = materials[i];

			if (curry >= listHeight)
				break;

			if (i == selectedIndex)
				list.setColor(Color.RED);
			else
				list.setColor(Color.WHITE);
			list.fillRect(1, curry+1, LIST_WIDTH-2, ENTRY_HEIGHT-2);

			list.setColor(entryColor);		// entry text
			list.setFont(entryFont);
			material.getSprite().draw(list, 2, curry+2, ENTRY_HEIGHT-4, ENTRY_HEIGHT-4);
			TextUtils.drawVerticalCenteredString(list, material.toString(), ENTRY_HEIGHT, curry, LIST_WIDTH - ENTRY_HEIGHT, ENTRY_HEIGHT, true);

			curry += ENTRY_HEIGHT;
		}

		g.setPaint(scrollTexture);				// scrollbar background
		g.fillRect(listX+LIST_WIDTH, listY, SCROLLBAR_WIDTH, listHeight);

		g.setPaint(scrollBarTexture);			// scrollbar
		g.fillRoundRect(listX+LIST_WIDTH+1, (int)(listY+((scroll/materials.length)*listHeight)+1), SCROLLBAR_WIDTH - 2, (int)(listHeight*scrollScale)-2, SCROLLBAR_WIDTH-2, SCROLLBAR_WIDTH-2);
	}

	private void getSizes(int width, int height) {
		levelHeight = Math.max(0, height - TITLE_HEIGHT - PADDING - MARGIN*2);

		buttonsX = MARGIN;
		buttonsY = MARGIN + TITLE_HEIGHT + PADDING;
		buttonWidth = BUTTON_WIDTH;
		buttonHeight = Math.max(0, (levelHeight - PADDING*(BUTTONS-1))/BUTTONS);

		levelX = buttonsX + BUTTON_WIDTH + PADDING;
		levelY = buttonsY;
		levelWidth = Math.max(0, width - LIST_WIDTH - SCROLLBAR_WIDTH - buttonWidth - PADDING*2 - MARGIN*2);

		listX = levelX + levelWidth + PADDING;
		listY = levelY;
		listWidth = LIST_WIDTH + SCROLLBAR_WIDTH;
		listHeight = levelHeight;

		double displayTiles = (double)listHeight / ENTRY_HEIGHT;
		scrollScale = Math.min(1, displayTiles/materials.length);

		scroll = Math.max(0, Math.min(materials.length - displayTiles, scroll));
	}

	private void clickButton() {
		if (selectedButton < 0 || selectedButton >= BUTTONS)
			return;

		switch (selectedButton) {
			case 0:
				String fname = JOptionPane.showInputDialog(null, "Enter a file name");
				if (fname != null)
					openLevel(fname);
				break;
			case 1:
				fname = JOptionPane.showInputDialog(null, "Enter a file name");
				if (fname != null)
					saveToFile(fname);
				break;
			case 2:
				reset();
				break;
			case 3:
				JOptionPane.showMessageDialog(null, "WASD to pan\nDouble click to set player spawn\nRight click to enter custom tile data\nPress delete or backspace to toggle eraser");
				break;
			case 4:
				parent.mainMenu();
				break;
		}
	}

	private void removeTile(Coord key) {
		tiles.remove(key);
		customData.remove(key);
	}

	public void saveToFile(String fname) {
		int maxx = Integer.MIN_VALUE, minx = Integer.MAX_VALUE, maxy = maxx, miny = minx;

		for (Tile t : tiles.values()) {
			if (t.x < minx)
				minx = t.x;
			if (t.x > maxx)
				maxx = t.x;
			if (t.y < miny)
				miny = t.y;
			if (t.y > maxy)
				maxy = t.y;
		}

		if (playerStartX > maxx)
			maxx = playerStartX;
		if (playerStartX < minx)
			minx = playerStartX;
		if (playerStartY > maxy)
			maxy = playerStartY;
		if (playerStartY < miny)
			miny = playerStartY;

		PrintWriter out = null;

		try {
			out = new PrintWriter(new File(fname));

			out.println((maxx - minx + 1) + " " + (maxy - miny + 1));

			for (int y=miny; y <= maxy; y++) {
				for (int x=minx; x <= maxx; x++) {
					Tile t = tiles.get(new Coord(x, y));

					if (t == null)
						out.print("0\t");
					else
						out.print(t.material.ordinal() + "\t");
				}
				out.println();
			}

			out.println(0);
			out.println(playerStartX-minx + " " + (maxy-playerStartY));

			for (Coord c : customData.keySet()) {
				out.println(c.x-minx + " " + (maxy-c.y) + " " + customData.get(c));
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to write to file!");
		} finally {
			if (out != null)
				out.close();
		}
	}

	public void openLevel(String fname) {
		try {
			Scanner in = new Scanner(new File(fname));

			tiles.clear();
			customData.clear();

			int width = in.nextInt();
			int height = in.nextInt();

			int x, y;

			for (y=0; y < height; y++) {
				for (x=0; x < width; x++) {
					int id = in.nextInt();
					//System.out.println(x + ", " + y + " : " + id);
					if (id != 0)
						tiles.put(new Coord(x, y), new Tile(Material.values()[id], x, y));
				}
			}

			in.nextInt();

			playerStartX = (int)in.nextDouble();
			playerStartY = height-(int)in.nextDouble()-1;

			while (in.hasNext()) {
				x = in.nextInt();
				y = height-in.nextInt()-1;

				customData.put(new Coord(x, y), in.nextLine().substring(1));
			}
		} catch (Exception e) {
			reset();
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to read level!");
			return;
		}

		levelScrollX = 0;
		levelScrollY = 0;
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				parent.mainMenu();
				break;
			case KeyEvent.VK_E:
				Coord c = new Coord(selectedX, selectedY);
				Tile t = tiles.get(c);

				if (t != null)
					selectedIndex = t.material.ordinal();
				break;
			case KeyEvent.VK_DELETE:
			case KeyEvent.VK_BACK_SPACE:
				deleting = !deleting;
				break;
		}
	}

	public void mousePressed(MouseEvent e) {
		clickX = e.getX();
		clickY = e.getY();

		if (clickX > listX + LIST_WIDTH && clickX < listX + LIST_WIDTH + SCROLLBAR_WIDTH &&
			clickY > listY && clickY < listY + listHeight)
			scrolling = true;
		else if (	clickX > listX && clickX < listX + LIST_WIDTH &&
					clickY > listY && clickY < listY + listHeight) {
			selectedIndex = (int)((double)(clickY - listY) / ENTRY_HEIGHT + scroll);
			if (selectedIndex < 0 || selectedIndex > materials.length)
				selectedIndex = -1;
		} else if (	clickX > levelX && clickX < levelX + levelWidth &&
					clickY > levelY && clickY < levelY + levelHeight) {
			selectedX = (int)((double)(clickX - levelX) / TILE_SIZE + levelScrollX);
			selectedY = (int)((double)(clickY - levelY) / TILE_SIZE + levelScrollY);

			if (e.getButton() == MouseEvent.BUTTON1) {
				Coord key = new Coord(selectedX, selectedY);
				if (e.getClickCount() == 2) {
					playerStartX = selectedX;
					playerStartY = selectedY;
				} else if (selectedIndex <= 0 || deleting)
						removeTile(key);
				else
					tiles.put(key, new Tile(materials[selectedIndex], selectedX, selectedY));
			} else {
				Coord key = new Coord(selectedX, selectedY);
				String data = JOptionPane.showInputDialog(null, "Enter custom data for this tile", customData.get(key));

				if (data != null)
					customData.put(key, data);
			}	
		}
	}

	public void mouseReleased(MouseEvent e) {
		scrolling = false;

		if (selectedButton >= 0) {
			clickButton();
		}
	}

	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (mx > levelX && mx < levelX + levelWidth &&
			my > levelY && my < levelY + levelHeight) {
			selectedX = (int)((double)(mx - levelX) / TILE_SIZE + levelScrollX);
			selectedY = (int)((double)(my - levelY) / TILE_SIZE + levelScrollY);
			selectedButton = -1;
		} else if (	mx > buttonsX && mx < buttonsX + buttonWidth &&
					my > buttonsY && my < buttonsY + BUTTONS*(buttonHeight + PADDING)) {
			int relY = my - buttonsY;
			int overlapY = relY % (buttonHeight + PADDING);

			if (overlapY < buttonHeight)
				selectedButton = relY / (buttonHeight + PADDING);
			else
				selectedButton = -1;
		} else {
			selectedButton = -1;
		}
	}

	public void mouseDragged(MouseEvent e) {
		int minScroll = listY + (int)(scroll*listHeight/materials.length) + 1;
		int maxScroll = minScroll + (int)(scrollScale*listHeight);

		if (scrolling && clickY > minScroll && clickY < maxScroll) {
			int rel = e.getY() - clickY;

			double scrollamt = (double)rel*materials.length / listHeight;

			scroll += scrollamt;
		} else if (clickX > levelX && clickX < levelX + levelWidth &&
			clickY > levelY && clickY < levelY + levelHeight) {
			selectedX = (int)((double)(clickX - levelX) / TILE_SIZE + levelScrollX);
			selectedY = (int)((double)(clickY - levelY) / TILE_SIZE + levelScrollY);

			Coord key = new Coord(selectedX, selectedY);
			
			if (tiles.containsKey(key)) {
				if (selectedIndex <= 0 || deleting)
					removeTile(key);
			}
			else if (selectedIndex > 0 && !deleting)
				tiles.put(key, new Tile(materials[selectedIndex], selectedX, selectedY));
		} else if (	clickX > buttonsX && clickX < buttonsX + buttonWidth &&
					clickY > buttonsY && clickY < buttonsY + BUTTONS*(buttonHeight + PADDING)) {
			int relY = clickY - buttonsY;
			int overlapY = relY % (buttonHeight + PADDING);

			if (overlapY < buttonHeight)
				selectedButton = relY / (buttonHeight + PADDING);
			else
				selectedButton = -1;
		} else {
			selectedButton = -1;
		}

		clickX = e.getX();
		clickY = e.getY();
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		int mx = Input.getMouseX();
		int my = Input.getMouseY();

		if (mx >= listX && mx < listX + listWidth &&
			my >= listY && my < listY + listHeight)
			scroll += e.getWheelRotation();
	}
}