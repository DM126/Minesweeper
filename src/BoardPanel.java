import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel 
{
	private Board board;
	private ImageIcon[][] images;
	private static final int IMAGE_SIZE = 32; //pixel size of a tile?
	
	//Tile images
	private ImageIcon[] numbers; //Shows number of mines in adjacent tiles (0 to 8)
	private ImageIcon covered; //Tile that hasn't been revealed
	private ImageIcon flag; //Flagged tile
	private ImageIcon mine; //Mine tile
	
	//TODO: add a timer, mines cleared, etc.
	
	public BoardPanel() 
	{
		board = new Board(8, 8); //eventually allow for custom boards and number of mines.
		images = new ImageIcon[board.getWidth()][board.getHeight()];
		
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
		for (int y = 0; y < board.getHeight(); y++) 
		{
			for (int x = 0; x < board.getWidth(); x++) 
			{
				images[x][y] = setImage(board.getTile(x, y));
			}
		}
		
		addMouseListener(new ClickListener());
		
		setPreferredSize(new Dimension(board.getWidth() * IMAGE_SIZE, board.getHeight() * IMAGE_SIZE));
		setFocusable(true);
	}
	
	public Board getBoard()
	{
		return board;
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
		
		for (int y = 0; y < board.getHeight(); y++) 
		{
			for (int x = 0; x < board.getWidth(); x++) 
			{
				images[x][y].paintIcon(this, page, IMAGE_SIZE * x, IMAGE_SIZE * y);
			}
		}
	}
	
	private class ClickListener implements MouseListener 
	{
		public void mouseClicked(MouseEvent event) 
		{
			if (!board.isGameOver()) 
			{
				//x and y coordinates within the board grid. (0,0) is the top left.
				int x = event.getX() / IMAGE_SIZE;
				int y = event.getY() / IMAGE_SIZE;
				if (!board.inBounds(x, y))
				{
					return;
				}
				
				if (SwingUtilities.isLeftMouseButton(event)) 
				{
					Tile tile = board.getTile(x, y);
					//double click number, do flood fill if all mines are flagged
					if (event.getClickCount() == 2 && tile.isVisible() && tile.adjMines() > 0 && tile.adjMines() == board.adjFlags(x, y)) 
					{
						System.out.println("double");
						board.floodFillAdjacentTiles(x, y);
					}
					
					board.click(x, y);
					if (tile.isMine() && !tile.isFlagged()) 
					{
						board.gameOver();
					}
					else if (board.checkWin()) 
					{
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
				board.newGame();
			}
			
			//TODO: INEFFICIENT!!! ONLY CHANGE TILES THAT WERE ALTERED!
			for (int col = 0; col < board.getWidth(); col++) 
			{
				for (int row = 0; row < board.getHeight(); row++) 
				{
					images[col][row] = setImage(board.getTile(col, row));
				}
			}
			
			repaint();
		}
		
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
		public void mousePressed(MouseEvent event) {}
		public void mouseReleased(MouseEvent event) {}
	}
}
