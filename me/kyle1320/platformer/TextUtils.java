package me.kyle1320.platformer;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.AlphaComposite;
import java.awt.Stroke;
import java.awt.BasicStroke;

import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;

import java.text.AttributedString;
import java.text.AttributedCharacterIterator;

/**	A static class for drawing text on a Graphics2D object.
	@author Kyle Cutler
	@version 1/1/14
*/
public final class TextUtils {
	// Public static constants used in drawString, drawOutlinedString, and drawMultilinedString
	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_CENTER = 1;
	public static final int ALIGN_RIGHT = 2;

	public static final int ALIGN_TOP = 3;
	public static final int ALIGN_BOTTOM = 4;

	/**	Returns the height, in pixels, that the given String would be if drawn on the given Graphics.
		@param g The Graphics2D on which the string is to be drawn
		@param s The String to be drawn
		@return The summed height of each line in the string, separated by newline (\n) characters.
	*/
	public static int getStringHeight(Graphics2D g, String s) {
		FontMetrics font = g.getFontMetrics();
		int lines = s.split("\n").length;
		return lines*font.getAscent() + font.getDescent();
	}

	/**	Used for cutting off text that is too wide.
		@param font The FontMetrice displaying the string
		@param s The String to be displayed
		@param width The cutoff width
		@return The string cut to fit into the width with "..." added to the end.
	*/
	private static String clip(FontMetrics font, String s, int width) {
		if (font.stringWidth(s) <= width)
			return s;
		else {
			String clip = s.substring(0, s.length()-1);

			while (font.stringWidth(clip + "...") > width && clip.length() > 0)
				clip = clip.substring(0, clip.length() - 1);

			return clip + "...";
		}
	}

	/**	Draws the given string on the given graphics with a given horizontal and vertical alignment.
		@param g The Graphics2D object on which to draw the string
		@param s The String to draw
		@param x The minimum x coordinate of the rectangle in which to draw the string
		@param y The minimum y coordinate of the rectangle in which to draw the string
		@param width The width of the rectangle in which to draw the string
		@param height The height the rectangle in which to draw the string
		@param alignHorizontal The horizontal alignment of the string. Should be ALIGN_LEFT, ALIGN_CENTER, or ALIGN_RIGHT
		@param alignVertical The vertical alignment of the string. Should be ALIGN_TOP, ALIGN_CENTER, or ALIGN_BOTTOM
		@param clip True if the string should not go past the rectangle if it is too wide
	*/
	public static void drawString(Graphics2D g, String s, int x, int y, int width, int height, int alignHorizontal, int alignVertical, boolean clip) {
		if (s.length() == 0)
			return;

		FontMetrics font = g.getFontMetrics();
		if (clip) s = clip(font, s, width);

		int alignx = x;
		int aligny = y;

		switch (alignHorizontal) {
			case ALIGN_LEFT:
			case ALIGN_CENTER:
			case ALIGN_RIGHT:
				alignx += alignHorizontal*(width-font.stringWidth(s))/2;
				break;
			default:
				throw new IllegalArgumentException("Invalid horizontal alignment!");
		}

		switch (alignVertical) {
			case ALIGN_TOP:
				aligny += font.getAscent();
				break;
			case ALIGN_CENTER:
				aligny += height/2+(font.getAscent()-font.getDescent())/2;
				break;
			case ALIGN_BOTTOM:
				aligny += height - font.getDescent();
				break;
			default:
				throw new IllegalArgumentException("Invalid vertical alignment!");
		}

		g.drawString(s, alignx, aligny);
	}

	/**	Draws the given string on the given graphics with a given horizontal and vertical alignment and with an outline of a given width and color.
		@param g The Graphics2D object on which to draw the string
		@param s The String to draw
		@param x The minimum x coordinate of the rectangle in which to draw the string
		@param y The minimum y coordinate of the rectangle in which to draw the string
		@param width The width of the rectangle in which to draw the string
		@param height The height the rectangle in which to draw the string
		@param alignHorizontal The horizontal alignment of the string. Should be ALIGN_LEFT, ALIGN_CENTER, or ALIGN_RIGHT
		@param alignVertical The vertical alignment of the string. Should be ALIGN_TOP, ALIGN_CENTER, or ALIGN_BOTTOM
		@param outlineColor The color of the outline
		@param outline The width of the outline
		@param clip True if the string should not go past the rectangle if it is too wide
	*/
	public static void drawOutlinedString(Graphics2D g, String s, int x, int y, int width, int height, int alignHorizontal, int alignVertical, Color outlineColor, float outline, boolean clip) {
		FontMetrics font = g.getFontMetrics();
		if (clip) s = clip(font, s, width);

		int alignx = x;
		int aligny = y;

		switch (alignHorizontal) {
			case ALIGN_LEFT:
			case ALIGN_CENTER:
			case ALIGN_RIGHT:
				alignx += alignHorizontal*(width-font.stringWidth(s))/2;
				break;
			default:
				throw new IllegalArgumentException("Invalid horizontal alignment!");
		}

		switch (alignVertical) {
			case ALIGN_TOP:
				aligny += font.getAscent();
				break;
			case ALIGN_CENTER:
				aligny += height/2+(font.getAscent()-font.getDescent())/2;
				break;
			case ALIGN_BOTTOM:
				aligny += height - font.getDescent();
				break;
			default:
				throw new IllegalArgumentException("Invalid vertical alignment!");
		}

		java.awt.Shape stringOutline = font.getFont().createGlyphVector(g.getFontRenderContext(), s).getOutline(alignx, aligny);
		Stroke stroke = g.getStroke();
		Color color = g.getColor();

		g.setStroke(new BasicStroke(outline*2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.setColor(outlineColor);

		g.draw(stringOutline);
		g.setStroke(stroke);
		g.setColor(color);

		g.drawString(s, alignx, aligny);
		
	}

	/**	Draws the given multi-lined string on the given graphics with a given horizontal and vertical alignment.
		@param g The Graphics2D object on which to draw the string
		@param s The String to draw
		@param x The minimum x coordinate of the rectangle in which to draw the string
		@param y The minimum y coordinate of the rectangle in which to draw the string
		@param width The width of the rectangle in which to draw the string
		@param height The height the rectangle in which to draw the string
		@param alignHorizontal The horizontal alignment of the string. Should be ALIGN_LEFT, ALIGN_CENTER, or ALIGN_RIGHT
		@param alignVertical The vertical alignment of the string. Should be ALIGN_TOP, ALIGN_CENTER, or ALIGN_BOTTOM
		@param clip True if the string should not go past the rectangle if it is too wide
	*/
	public static void drawMultilineString(Graphics2D g, String s, int x, int y, int width, int height, int alignHorizontal, int alignVertical, boolean clip) {
		if (s.length() == 0)
			return;

		FontMetrics font = g.getFontMetrics();

		String[] split = s.split("\n");
		int lines = split.length;
		int alignx = x;
		int aligny = y;

		switch (alignVertical) {
			case ALIGN_TOP:
				aligny += font.getAscent();
				break;
			case ALIGN_CENTER:
				aligny += height/2-(font.getAscent()*lines+font.getDescent())/2 + font.getAscent();
				break;
			case ALIGN_BOTTOM:
				aligny += height - font.getDescent() - font.getAscent()*lines;
				break;
			default:
				throw new IllegalArgumentException("Invalid vertical alignment!");
		}

		for (String s2 : split) {
			if (clip) s2 = clip(font, s2, width);

			switch (alignHorizontal) {
				case ALIGN_LEFT:
				case ALIGN_CENTER:
				case ALIGN_RIGHT:
					alignx = x + alignHorizontal*(width-font.stringWidth(s2))/2;
					break;
				default:
					throw new IllegalArgumentException("Invalid horizontal alignment!");
			}

			g.drawString(s2, alignx, aligny);
			aligny += font.getAscent();
		}
	}

	/**	Draws the given String on the given Graphics2D aligned to the very center of the bounding rectangle.
		@param g The Graphics2D object on which to draw the string
		@param s The String to draw
		@param x The minimum x coordinate of the rectangle in which to draw the string
		@param y The minimum y coordinate of the rectangle in which to draw the string
		@param width The width of the rectangle in which to draw the string
		@param height The height the rectangle in which to draw the string
		@param clip True if the string should not go past the rectangle if it is too wide
	*/
	public static void drawCenteredString(Graphics2D g, String s, int x, int y, int width, int height, boolean clip) {
		FontMetrics font = g.getFontMetrics();
		if (clip) s = clip(font, s, width);

		g.drawString(s, x+width/2-font.stringWidth(s)/2, y+height/2+(font.getAscent()-font.getDescent())/2);
	}

	/**	Draws the given String on the given Graphics2D aligned to the top center of the bounding rectangle.
		@param g The Graphics2D object on which to draw the string
		@param s The String to draw
		@param x The minimum x coordinate of the rectangle in which to draw the string
		@param y The minimum y coordinate of the rectangle in which to draw the string
		@param width The width of the rectangle in which to draw the string
		@param height The height the rectangle in which to draw the string
		@param clip True if the string should not go past the rectangle if it is too wide
	*/
	public static void drawHorizontalCenteredString(Graphics2D g, String s, int x, int y, int width, int height, boolean clip) {
		FontMetrics font = g.getFontMetrics();
		if (clip) s = clip(font, s, width);

		g.drawString(s, x+width/2-font.stringWidth(s)/2, y+font.getAscent());
	}

	/**	Draws the given String on the given Graphics2D aligned to the left center of the bounding rectangle.
		@param g The Graphics2D object on which to draw the string
		@param s The String to draw
		@param x The minimum x coordinate of the rectangle in which to draw the string
		@param y The minimum y coordinate of the rectangle in which to draw the string
		@param width The width of the rectangle in which to draw the string
		@param height The height the rectangle in which to draw the string
		@param clip True if the string should not go past the rectangle if it is too wide
	*/
	public static void drawVerticalCenteredString(Graphics2D g, String s, int x, int y, int width, int height, boolean clip) {
		FontMetrics font = g.getFontMetrics();
		if (clip) s = clip(font, s, width);

		g.drawString(s, x, y+height/2+(font.getAscent()-font.getDescent())/2);
	}

	/**	Draws the given String on the given Graphics2D aligned to the top left of the bounding rectangle.
		@param g The Graphics2D object on which to draw the string
		@param s The String to draw
		@param x The minimum x coordinate of the rectangle in which to draw the string
		@param y The minimum y coordinate of the rectangle in which to draw the string
		@param width The width of the rectangle in which to draw the string
		@param height The height the rectangle in which to draw the string
		@param clip True if the string should not go past the rectangle if it is too wide
	*/
	public static void drawLeftAlignedString(Graphics2D g, String s, int x, int y, int width, int height, boolean clip) {
		FontMetrics font = g.getFontMetrics();
		if (clip) s = clip(font, s, width);

		g.drawString(s, x, y+font.getAscent());
	}

	/**	Draws the given String on the given Graphics2D aligned to the top right of the bounding rectangle.
		@param g The Graphics2D object on which to draw the string
		@param s The String to draw
		@param x The minimum x coordinate of the rectangle in which to draw the string
		@param y The minimum y coordinate of the rectangle in which to draw the string
		@param width The width of the rectangle in which to draw the string
		@param height The height the rectangle in which to draw the string
		@param clip True if the string should not go past the rectangle if it is too wide
	*/
	public static void drawRightAlignedString(Graphics2D g, String s, int x, int y, int width, int height, boolean clip) {
		FontMetrics font = g.getFontMetrics();
		if (clip) s = clip(font, s, width);

		g.drawString(s, x+width-font.stringWidth(s), y+font.getAscent());
	}

	/**	Draws the given String on the given Graphics2D aligned to the top left and line wrapped inside of the the bounding rectangle.
		@param g The Graphics2D object on which to draw the string
		@param s The String to draw
		@param x The minimum x coordinate of the rectangle in which to draw the string
		@param y The minimum y coordinate of the rectangle in which to draw the string
		@param width The width of the rectangle in which to draw the string
		@param height The height the rectangle in which to draw the string
	*/
	// straight off the internet
	public static void drawWrappedString(Graphics2D g, String s, int x, int y, int width, int height) {
		if (s.length() == 0)
			return;
		
		AttributedString as = new AttributedString(s);
		as.addAttribute(TextAttribute.FOREGROUND, g.getPaint());
		as.addAttribute(TextAttribute.FONT, g.getFont());
		AttributedCharacterIterator aci = as.getIterator();
		FontRenderContext frc = new FontRenderContext(null, true, false);
		LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc);
		int maxy = y + height;

		while (lbm.getPosition() < s.length()) {
			TextLayout tl = lbm.nextLayout(width);
			y += tl.getAscent();
			if (y > maxy)
				break;

			tl.draw(g, x, y);
			y += tl.getDescent() + tl.getLeading();
		}
	}
}