package wseditor.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class OutputPane extends JComponent
{
	private final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

	private JTabbedPane tab;
	private ConsoleTextPane output;
	private ConsoleTextPane errors;

	public OutputPane()
	{
		setLayout(new BorderLayout());

		output = new ConsoleTextPane();
		errors = new ConsoleTextPane();

		tab = new JTabbedPane();
		tab.setTabPlacement(JTabbedPane.BOTTOM);
		tab.addTab("Output", new JScrollPane(output));
		tab.addTab("Input", new JPanel());
		tab.addTab("Errors", new JScrollPane(errors));
		add(tab, BorderLayout.CENTER);
	}

	public void printOutput(String line)
	{
		synchronized (DATE_FORMAT)
		{
			final String s = "[" + DATE_FORMAT.format(new Date()) + "] " + line;
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					tab.setSelectedIndex(0);
					output.printLine(s, Color.BLACK, Color.WHITE);
				}
			});
		}
	}

	public void printError(String line)
	{
		synchronized (DATE_FORMAT)
		{
			final String s = "[" + DATE_FORMAT.format(new Date()) + "] " + line;
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					tab.setSelectedIndex(2);
					errors.printLine(s, Color.RED, Color.WHITE);
				}
			});
		}
	}
}
