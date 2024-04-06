import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MinesweeperPanel extends JPanel
{
	private HeaderPanel headerPanel;
	private BoardPanel boardPanel;
	
	public MinesweeperPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		int mines = 10;
		boardPanel = new BoardPanel(8, 8, mines);
		headerPanel = new HeaderPanel(boardPanel.getWidth(), mines);
		
		add(headerPanel);
		add(boardPanel);
	}
	
	public void reset()
	{
		System.out.println("reset");
	}
}
