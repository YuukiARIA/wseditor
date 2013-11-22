package wseditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import wseditor.gui.editor.LineColumn;
import wseditor.gui.editor.WSEditorPane;
import wseditor.translator.Translator;

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

		final WSEditorPane editor = new WSEditorPane();
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

		JFrame f = new JFrame("wseditor " + Application.getVersion());
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.add(panel);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
