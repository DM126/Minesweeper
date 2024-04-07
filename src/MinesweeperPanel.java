import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
		
		boardPanel.addPropertyChangeListener(new ChangeListener());
	}

	private class ChangeListener implements PropertyChangeListener
	{
		@Override
		public void propertyChange(PropertyChangeEvent event)
		{
			System.out.println(event);
			switch (event.getPropertyName())
			{
				case "flags": 
				{
					headerPanel.setFlagCount((Integer)event.getNewValue());
					break;
				}
				case "gameOver":
				{
					if (Boolean.TRUE.equals(event.getNewValue())) //true, game ended
					{
						headerPanel.stopTimer();
					}
					else //false, new game started
					{
						headerPanel.reset(boardPanel.getBoard().getMines());
					}
					break;
				}
				default: break;
			}
		}
	}
}
