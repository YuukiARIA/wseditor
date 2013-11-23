package wseditor.gui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class OutputPane extends JComponent
{
	private JTabbedPane tab;

	public OutputPane()
	{
		setLayout(new BorderLayout());

		tab = new JTabbedPane();
		tab.setTabPlacement(JTabbedPane.BOTTOM);
		tab.addTab("Output", new JPanel());
		tab.addTab("Input", new JPanel());
		tab.addTab("Errors", new JPanel());
		add(tab, BorderLayout.CENTER);
	}
}
