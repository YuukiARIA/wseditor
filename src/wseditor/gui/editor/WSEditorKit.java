package wseditor.gui.editor;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

@SuppressWarnings("serial")
public class WSEditorKit extends StyledEditorKit
{
	public Document createDefaultDocument()
	{
		return new DefaultStyledDocument();
	}

	public ViewFactory getViewFactory()
	{
		return new ViewFactory()
		{
			public View create(Element elem)
			{
				return new WSView(elem);
			}
		};
	}
}
