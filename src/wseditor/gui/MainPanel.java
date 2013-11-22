package wseditor.gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import wseditor.gui.editor.WSEditor;
import wseditor.translator.Translator;

@SuppressWarnings("serial")
public class MainPanel extends JPanel
{
	private WSEditor editor;
	private JTextArea codePane;

	public MainPanel()
	{
		setLayout(new BorderLayout());

		editor = new WSEditor();

		codePane = new JTextArea();
		JScrollPane sp2 = new JScrollPane(codePane);

		editor.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_T, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "translate");
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

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editor, sp2);
		split.setContinuousLayout(true);

		add(split, BorderLayout.CENTER);
	}
}
