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
		Tile tile = tiles[x][y];
		if (!tile.isFlagged() && !tile.isMine())
		{
			uncoverRegion(x, y);
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
		if (!inBounds(x, y)) {
			return;
		}
		
		Tile tile = tiles[x][y];
		if (tile.isVisible()) {
			return;
		}
		
		tile.uncover();
		if (tile.adjMines() == 0) {
			uncoverRegion(x+1, y);
			uncoverRegion(x-1, y);
			uncoverRegion(x, y-1);
			uncoverRegion(x, y+1);
			uncoverRegion(x+1, y+1);
			uncoverRegion(x-1, y+1);
			uncoverRegion(x+1, y-1);
			uncoverRegion(x-1, y-1);
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
		Tile tile = tiles[x][y];
		if (!tile.isVisible()) 
		{
			if (!tile.isFlagged()) 
			{
				if (flags > 0)
				{
					tile.flagTile();
					flags--;
				}
			} 
			else if (flags < mines)
			{
				tile.flagTile();
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
	
	/**
	 * @param x the column
	 * @param y the row
	 * @return true if x and y are within the board bounds
	 */
	public boolean inBounds(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}
