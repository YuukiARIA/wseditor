package wseditor.gui.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

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

		setPreferredSize(new Dimension(400, 300));
	}

	private void updateLineColumnStatus()
	{
		LineColumn lc = editorPane.getLineColumn();
		status.setText(lc.line + " : " + lc.column);
	}
}
