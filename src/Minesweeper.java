import javax.swing.*;

public class Minesweeper 
{
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("Minesweeper");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(new MinesweeperPanel());
		
		frame.pack();
		frame.setVisible(true);
	}
}
