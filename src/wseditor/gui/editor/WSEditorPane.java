package wseditor.gui.editor;

import javax.swing.JTextPane;
import javax.swing.text.Element;

@SuppressWarnings("serial")
public class WSEditorPane extends JTextPane
{
	public WSEditorPane()
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
