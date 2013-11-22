package wseditor.gui.editor;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainView;
import javax.swing.text.Segment;
import javax.swing.text.Utilities;

class WSView extends PlainView
{
	//private Color color = new Color((int)(255 * Math.random()), (int)(255 * Math.random()), (int)(255 * Math.random()));

	public WSView(Element e)
	{
		super(e);
	}

	public float nextTabStop(float x, int tabOffset)
	{
		int left = getContainer().getInsets().left;
		Graphics g = getGraphics();
		FontMetrics fm = g.getFontMetrics();
		int w = 4 * fm.charWidth(' ');
		return left + (int)Math.ceil(x / w) * w;
	}

	public void paint(Graphics g, Shape alloc)
	{
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		super.paint(g, alloc);
	}

	protected int drawUnselectedText(Graphics g, int x, int y, int p0, int p1) throws BadLocationException
	{
		Segment line = getLineBuffer();
		getDocument().getText(p0, p1 - p0, line);
		g.setColor(Color.BLACK);

		FontMetrics fm = g.getFontMetrics();
		Document doc = getDocument();
		for (int i = p0; i < p1; i++)
		{
			doc.getText(i, 1, line);
			char c = line.charAt(0);
			if (c == ' ' || c == '\t' || c == '\n')
			{
				int w = Utilities.getTabbedTextWidth(line, fm, x, this, 0);
				Color backColor, boxColor;
				if (c == ' ')
				{
					backColor = new Color(255, 200, 200);
					boxColor = new Color(255, 180, 180);
				}
				else if (c == '\t')
				{
					backColor = new Color(200, 200, 255);
					boxColor = new Color(180, 180, 255);
				}
				else
				{
					backColor = new Color(200, 255, 200);
					boxColor = new Color(180, 255, 180);
					w = fm.charWidth(' ') / 2;
				}
				g.setColor(backColor);
				g.fillRect(x, y - fm.getAscent(), w, fm.getHeight());
				g.setColor(boxColor);
				g.drawRect(x, y - fm.getAscent(), w - 1, fm.getHeight() - 1);
				//g.drawRect(x + 1, y - fm.getAscent() + 1, w - 3, fm.getHeight() - 3);
			}
			else
			{
				g.setColor(Color.GRAY);
			}
			x = Utilities.drawTabbedText(line, x, y, g, this, 0);
		}

		return x;
	}

	protected int drawSelectedText(Graphics g, int x, int y, int p0, int p1) throws BadLocationException
	{
		return super.drawSelectedText(g, x, y, p0, p1);
	}
}
