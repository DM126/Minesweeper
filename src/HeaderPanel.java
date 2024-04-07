import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class HeaderPanel extends JPanel
{
	private static final int MAX_SECONDS = 999; //max seconds to show on the timer
	private JLabel flagCountLabel;
	private JLabel timerLabel;
	private Timer timer;
	private int timeElapsed;
	private static final int ONE_SECOND = 1000; //update timer every second
	
	//TODO add smiley
	
	/**
	 * Creates a header panel with an unflagged mine count and timer
	 * 
	 * @param width the width of the JPanel
	 * @param startingFlagCount the number of flags (mines) on the board
	 */
	public HeaderPanel(int width, int startingFlagCount)
	{
		setLayout(new BorderLayout());
		
		flagCountLabel = new JLabel();
		timerLabel = new JLabel();
		timer = new Timer(ONE_SECOND, event -> incrementTimer());
		
		add(flagCountLabel, BorderLayout.WEST);
		add(timerLabel, BorderLayout.EAST);
		
		setPreferredSize(new Dimension(width, 25));
		setFocusable(true);
		
		reset(startingFlagCount);
	}
	
	/**
	 * Sets the unflagged mine count label.
	 * 
	 * @param mineCount the number of unflagged mines.
	 */
	public void setFlagCount(int mineCount)
	{
		setLabel(flagCountLabel, mineCount);
	}
	
	/**
	 * Sets the timer label.
	 * 
	 * @param seconds the number of seconds elapsed
	 */
	public void setTimer(int seconds)
	{
		if (seconds >= MAX_SECONDS)
		{
			seconds = MAX_SECONDS;
		}
		
		//TODO don't need to set if above 999 more than once
		setLabel(timerLabel, seconds);
	}
	
	/**
	 * Sets a label with a value, prepending with 0s so there is always 3 digits
	 * 
	 * @param label the label to set
	 * @param value the value to display on the label
	 */
	private void setLabel(JLabel label, int value)
	{
		StringBuilder valueString = new StringBuilder(Integer.toString(value));
		//prepend with 0s so 3 digits are always shown
		for (int i = valueString.length(); i < 3; i++)
		{
			valueString.insert(0, "0");
		}
		
		label.setText(valueString.toString());
	}
	
	/**
	 * Adds one second to the timer
	 */
	public void incrementTimer()
	{
		timeElapsed++;
		setTimer(timeElapsed);
	}
	
	/**
	 * Stops the timer.
	 */
	public void stopTimer()
	{
		timer.stop();
	}
	
	/**
	 * resets the counters
	 * 
	 * @param mines the number of mines on the board
	 */
	public void reset(int mines)
	{
		setFlagCount(mines);
		timeElapsed = 0;
		setTimer(timeElapsed);
		timer.restart();
	}
}
