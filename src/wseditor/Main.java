package wseditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainView;
import javax.swing.text.Segment;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.Utilities;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

import wseditor.translator.Translator;

@SuppressWarnings("serial")
class WSEditorKit extends StyledEditorKit
{
	public Document createDefaultDocument()
	{
		return new DefaultStyledDocument();
	}

	public ViewFactory getViewFactory()
	{
		return new WSViewFactory();
	}
}

class WSViewFactory implements ViewFactory
{
	public View create(Element elem)
	{
		return new WSView(elem);
	}
}

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

@SuppressWarnings("serial")
class SyntaxHighlightEditor extends JTextPane
{
	public SyntaxHighlightEditor()
	{
		setEditorKit(new WSEditorKit());
	}

	public LineColumn getLineColumn()
	{
		int caretpos = getCaretPosition();
		Element root = getDocument().getDefaultRootElement();
		int index = root.getElementIndex(caretpos);
		int line = index + 1;
		int column = caretpos - root.getElement(index).getStartOffset() + 1;
		return new LineColumn(caretpos, line, column);
	}

	public boolean getScrollableTracksViewportWidth()
	{
		return getUI().getPreferredSize(this).width < getParent().getWidth();
	}
}

class LineColumn
{
	public final int offset;
	public final int line;
	public final int column;

	public LineColumn(int offset, int line, int column)
	{
		this.offset = offset;
		this.line = line;
		this.column = column;
	}
}

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
		}

		final SyntaxHighlightEditor editor = new SyntaxHighlightEditor();
		editor.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		JScrollPane sp = new JScrollPane(editor);
		sp.setPreferredSize(new Dimension(300, 200));

		final JTextArea codePane = new JTextArea();
		JScrollPane sp2 = new JScrollPane(codePane);

		editor.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_T, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "translate");
		editor.getActionMap().put("translate", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				Translator translator = new Translator(editor.getText());
				try
				{
					List<String> code = translator.translate();
					codePane.setText(code.toString().replace(",", "\n").replace("[", "").replace("]", ""));
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp, sp2);

		final JLabel bar = new JLabel();
		editor.addCaretListener(new CaretListener()
		{
			public void caretUpdate(CaretEvent e)
			{
				LineColumn lc = editor.getLineColumn();
				bar.setText(lc.line + " : " + lc.column);
			}
		});
		editor.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				LineColumn lc = editor.getLineColumn();
				bar.setText(lc.line + " : " + lc.column);
			}
		});

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(split, BorderLayout.CENTER);
		panel.add(bar, BorderLayout.SOUTH);

		JFrame f = new JFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.add(panel);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
