package wseditor.gui;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

@SuppressWarnings("serial")
public class ConsoleTextPane extends JTextPane
{
	public ConsoleTextPane()
	{
		setEditable(false);
	}

	public void printLine(String line, Color fg, Color bg)
	{
		MutableAttributeSet a = new SimpleAttributeSet();
		StyleConstants.setForeground(a, fg);
		StyleConstants.setBackground(a, bg);
		StyledDocument doc = getStyledDocument();
		try
		{
			doc.insertString(doc.getLength(), line + "\n", a);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}
}
