package wseditor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import wseditor.gui.MainPanel;

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

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				buildAndShow();
			}
		});
	}

	private static void buildAndShow()
	{
		final MainPanel panel = new MainPanel();

		JFrame f = new JFrame("wseditor " + Application.getVersion());
		f.addWindowListener(new WindowAdapter()
		{
			public void windowOpened(WindowEvent e)
			{
				panel.initializeSplitDiviers();
			}
		});

		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.add(panel);
		f.setSize(500, 400);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
