package wseditor;

import javax.swing.JFrame;
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

		JFrame f = new JFrame("wseditor " + Application.getVersion());
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.add(new MainPanel());
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
