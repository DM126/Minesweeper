import java.util.Random;

public class Board 
{
	private int width;
	private int height;
	private int mines;
	private int flags;
	private Tile[][] tiles;
	private Random rand;
	
	public Board(int width, int height) 
	{
		this.width = width;
		this.height = height;
		rand = new Random();
		
		mines = 10;
		flags = mines;
		setBoard();
	}
	
	private void setBoard() 
	{
		tiles = new Tile[width][height];
		
		for (int y = 0; y < height; y++) 
		{
			for (int x = 0; x < width; x++) 
			{
				tiles[x][y] = new Tile();
			}
		}
		
		placeMines();
		searchForMines();
	}
	
	public Tile getTile(int x, int y) 
	{
		return tiles[x][y];
	}
	
	public void click(int x, int y) 
	{
		//TODO: Should left-clicking a flagged tile unflag it?
		if (!tiles[x][y].isFlagged()) 
		{
			tiles[x][y].uncover();
			
			if (!tiles[x][y].isMine())
			{
				uncoverRegion(x, y);
			}
		}
	}
	
	/**
	 * Flood fill algorithm to uncover regions of tiles.
	 * 
	 * @param x the x coordinate to begin uncovering from
	 * @param y the y coordinate to begin uncovering from
	 */
	public void uncoverRegion(int x, int y) 
	{
		if (tiles[x][y].isVisible())
		{
			return;
		}
		
		if (tiles[x][y].adjMines() == 0)
		{
			if (tiles[x][y+1].isFlagged())
			{
				
			}
		}
		
		
		
		if (tiles[x][y].adjMines() == 0) 
		{
			for (int y2 = y - 1; y2 <= y + 1; y2++) 
			{
				if (y2 >= 0 && y2 < height) 
				{
					for (int x2 = x - 1; x2 <= x + 1; x2++) 
					{
						if (x2 >= 0 && x2 < width) 
						{
							if (!tiles[x2][y2].isVisible() && !tiles[x][y].equals(tiles[x2][y2]) && !tiles[x2][y2].isFlagged()) 
							{
								tiles[x2][y2].uncover();
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Flags or unflags an uncovered tile.
	 * 
	 * @param x the x coordinate of the tile to flag
	 * @param y the y coordinate of the tile to flag
	 */
	public void flag(int x, int y) 
	{
		if (flags > 0 && !tiles[x][y].isVisible()) 
		{
			tiles[x][y].flagTile();
			if (tiles[x][y].isFlagged())
			{
				flags--;
			}
			else
			{
				flags++;
			}
		}
		
		//DEBUG: REMOVE
		System.out.println(flags);
	}
	
	/**
	 * Uncovers the board after hitting a mine.
	 */
	public void gameOver() 
	{
		for (int y = 0; y < height; y++) 
		{
			for (int x = 0; x < width; x++) 
			{
				if (tiles[x][y].isMine())
				{
					tiles[x][y].uncover();
				}
			}
		}
	}
	
	/**
	 * Resets the board and starts a new game.
	 */
	public void reset() 
	{
		mines = 10;
		flags = mines;
		setBoard();
	}
	
	/**
	 * Sets the mines around the board.
	 */
	private void placeMines() 
	{
		int x;
		int y;
		int i = 0;
		
		while (i < mines) 
		{
			x = rand.nextInt(width);
			y = rand.nextInt(height);
			
			if (!tiles[x][y].isMine()) 
			{
				tiles[x][y].setMine();
				i++;
			}
		}
	}
	
	/**
	 * Finds the number of mines adjacent to each tile on the board and sends that number to the tile object.
	 */
	private void searchForMines() 
	{	
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++) 
			{
				findAdjacentMines(x, y);
			}
		}
	}
	
	/**
	 * Finds the number of mines around a tile.
	 * 
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 */
	private void findAdjacentMines(int thisX, int thisY)
	{
		int adjMines = 0;
		
		for (int otherY = thisY - 1; otherY <= thisY + 1; otherY++) 
		{
			if (otherY >= 0 && otherY < height) 
			{
				for (int otherX = thisX - 1; otherX <= thisX + 1; otherX++) 
				{
					if (otherX >= 0 && otherX < width && 
						!tiles[thisX][thisY].equals(tiles[otherX][otherY]) && 
						tiles[otherX][otherY].isMine()) 
					{
						adjMines++;
					}
				}
			}
		}
		
		tiles[thisX][thisY].setAdjMines(adjMines);
	}
}
