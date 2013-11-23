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
import wseditor.translator.exception.TranslateException;

@SuppressWarnings("serial")
public class MainPanel extends JPanel
{
	private WSEditor editor;
	private JTextArea codePane;
	private OutputPane outputPane;

	private JSplitPane splitH;
	private JSplitPane splitV;

	public MainPanel()
	{
		setLayout(new BorderLayout());

		editor = new WSEditor();

		codePane = new JTextArea();

		editor.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_T, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "translate");
		editor.getActionMap().put("translate", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				Translator translator = new Translator(editor.getText());
				try
				{
					List<String> code = translator.translate();
					codePane.setText(code.toString().replaceAll(",\\s*", "\n").replace("[", "").replace("]", ""));
				}
				catch (TranslateException ex)
				{
					outputPane.printError("Error: " + ex.getMessage());
				}
			}
		});

		splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editor, new JScrollPane(codePane));
		splitH.setResizeWeight(0.8);
		splitH.setContinuousLayout(true);

		outputPane = new OutputPane();
		//output.setEditable(false);
		splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitH, outputPane);
		splitV.setResizeWeight(1.0);

		add(splitV, BorderLayout.CENTER);
	}

	public void initializeSplitDiviers()
	{
		splitV.setDividerLocation(0.8);
		splitH.setDividerLocation(0.8);
	}
}
