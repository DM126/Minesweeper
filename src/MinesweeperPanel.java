import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MinesweeperPanel extends JPanel 
{
	private int width;
	private int height;
	private Board board;
	private ImageIcon[][] images;
	private static final int IMAGE_SIZE = 32; //pixel size of a tile?
	private boolean gameOver;
	
	//Tile images
	private ImageIcon[] numbers; //Shows number of mines in adjacent tiles (0 to 8)
	private ImageIcon covered; //Tile that hasn't been revealed
	private ImageIcon flag; //Flagged tile
	private ImageIcon mine; //Mine tile
	
	//TODO: add a timer, mines cleared, etc.
	
	public MinesweeperPanel() 
	{
		gameOver = false;
		width = 8;
		height = 8;
		board = new Board(width, height); //eventually allow for custom boards and number of mines.
		images = new ImageIcon[width][height];
		
		String imageFolder = "resources/";
		numbers = new ImageIcon[] { new ImageIcon(imageFolder + "0.png"),
									new ImageIcon(imageFolder + "1.png"),
									new ImageIcon(imageFolder + "2.png"),
									new ImageIcon(imageFolder + "3.png"),
									new ImageIcon(imageFolder + "4.png"),
									new ImageIcon(imageFolder + "5.png"),
									new ImageIcon(imageFolder + "6.png"),
									new ImageIcon(imageFolder + "7.png"),
									new ImageIcon(imageFolder + "8.png")};
		
		covered = new ImageIcon(imageFolder + "covered.png");
		flag = new ImageIcon(imageFolder + "flag.png");
		mine = new ImageIcon(imageFolder + "mine.png");
		
		//initialize all images to covered at first.
		for (int y = 0; y < height; y++) 
		{
			for (int x = 0; x < width; x++) 
			{
				images[x][y] = setImage(board.getTile(x, y));
			}
		}
		
		addMouseListener(new ClickListener());
		
		setPreferredSize(new Dimension(width * IMAGE_SIZE, height * IMAGE_SIZE));
		setFocusable(true);
	}
	
	/**
	 * Finds the image that represents a tile depending on its state.
	 * 
	 * @param t the tile to determine the image of
	 * @return the image to represent the tile
	 */
	public ImageIcon setImage(Tile t) 
	{
		ImageIcon image = covered;
		
		if (t.isVisible()) 
		{
			if (t.isMine()) 
			{
				image = mine;
			}
			else 
			{
				image = numbers[t.adjMines()];
			}
		}
		else if (t.isFlagged())
		{
			image = flag;
		}
		
		return image;
	}
	
	@Override
	public void paintComponent(Graphics page) 
	{
		super.paintComponent(page);
		
		for (int y = 0; y < height; y++) 
		{
			for (int x = 0; x < width; x++) 
			{
				images[x][y].paintIcon(this, page, 32 * x, 32 * y);
			}
		}
	}
	
	/**
	 * Checks if the win condition is satisfied.
	 * 
	 * @return true if the player has won the game
	 */
	private boolean checkWin() 
	{
		for (int y = 0; y < height; y++) 
		{
			for (int x = 0; x < width; x++) 
			{
				if (!board.getTile(x, y).isVisible() && !board.getTile(x, y).isFlagged())
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	private class ClickListener implements MouseListener 
	{
		public void mousePressed(MouseEvent event) 
		{
			if (!gameOver) 
			{
				int x = event.getX() / IMAGE_SIZE;
				int y = event.getY() / IMAGE_SIZE;
				
				//TODO: Refactor into leftMouseClick(x, y) and rightMouseClick(x, y)???
				if (SwingUtilities.isLeftMouseButton(event)) 
				{
					board.click(x, y);
					if (board.getTile(x, y).isMine() && !board.getTile(x, y).isFlagged()) 
					{
						gameOver = true;
						board.gameOver();
					}
					else if (checkWin()) 
					{
						gameOver = true;
						System.out.println("Congrats"); //TODO: Display a dialog box
					}
				}
				else if (SwingUtilities.isRightMouseButton(event))
				{
					board.flag(x, y);
				}
			}
			else //click the board to reset after a game over
			{
				gameOver = false;
				board.reset();
			}
			
			//TODO: INEFFICIENT!!! ONLY CHANGE TILES THAT WERE ALTERED!
			for (int col = 0; col < width; col++) 
			{
				for (int row = 0; row < height; row++) 
				{
					images[col][row] = setImage(board.getTile(col, row));
				}
			}
			//images[x][y] = setImage(board.getTile(x, y)); 	//what is this?
			
			repaint();
		}
		
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
		public void mouseClicked(MouseEvent event) {}
		public void mouseReleased(MouseEvent event) {}
	}
}
