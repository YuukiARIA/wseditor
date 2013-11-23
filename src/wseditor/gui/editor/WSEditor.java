package wseditor.gui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

@SuppressWarnings("serial")
public class WSEditor extends JPanel
{
	private WSEditorPane editorPane;
	private JLabel status;

	public WSEditor()
	{
		setLayout(new BorderLayout());

		editorPane = new WSEditorPane();
		editorPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		JScrollPane scroll = new JScrollPane(editorPane);
		add(scroll, BorderLayout.CENTER);

		status = new JLabel("#");
		status.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(2, 2, 2, 2),
			BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(1, 1, 0, 0, Color.LIGHT_GRAY),
				BorderFactory.createMatteBorder(0, 0, 1, 1, Color.WHITE)
			)
		));
		add(status, BorderLayout.SOUTH);

		editorPane.addCaretListener(new CaretListener()
		{
			public void caretUpdate(CaretEvent e)
			{
				updateLineColumnStatus();
			}
		});
		editorPane.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				updateLineColumnStatus();
			}
		});
	}

	public void setText(String s)
	{
		editorPane.setText(s);
	}

	public String getText()
	{
		return editorPane.getText();
	}

	private void updateLineColumnStatus()
	{
		LineColumn lc = editorPane.getLineColumn();
		status.setText(lc.line + " : " + lc.column);
	}
}
