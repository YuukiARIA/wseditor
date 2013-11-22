package wseditor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import wseditor.gui.editor.WSEditor;

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

		final JTextArea codePane = new JTextArea();
		JScrollPane sp2 = new JScrollPane(codePane);

		/*
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
		*/

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new WSEditor(), sp2);
		split.setContinuousLayout(true);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(split, BorderLayout.CENTER);

		JFrame f = new JFrame("wseditor " + Application.getVersion());
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.add(panel);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
