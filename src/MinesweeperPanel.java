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
		
		boardPanel = new BoardPanel();
		headerPanel = new HeaderPanel(boardPanel.getWidth(), 10);
		
		add(headerPanel);
		add(boardPanel);
	}
	
	public void reset()
	{
		System.out.println("reset");
	}
}
