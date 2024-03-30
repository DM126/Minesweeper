
public class Tile 
{
	private int adjMines; //Number of mines in adjacent tiles.
	private boolean isMine; //true if this tile contains a mine
	private boolean isVisible; //true if this tile is uncovered
	private boolean isFlagged; //true if this tile has a flag on it
	
	public Tile() 
	{
		adjMines = 0;
		isMine = false;
		isVisible = false;
		isFlagged = false;
	}
	
	/**
	 * Plants a mine on this tile.
	 * Used during initialization of the game.
	 */
	public void setMine() 
	{
		isMine = true;
	}
	
	/**
	 * Uncovers the tile.
	 */
	public void uncover() 
	{
		isVisible = true;
	}
	
	/**
	 * Will add a flag to the tile if it is unflagged, otherwise will remove the flag.
	 */
	public void flagTile() 
	{
		if (!isFlagged && !isVisible)
		{
			isFlagged = true;
		}
		else
		{
			isFlagged = false;
		}
	}
	
	/**
	 * @return true if the tile contains a mine.
	 */
	public boolean isMine() { return isMine; }
	
	/**
	 * @return true if the tile is uncovered.
	 */
	public boolean isVisible() { return isVisible; }
	
	/**
	 * @return true if the tile is flagged.
	 */
	public boolean isFlagged() { return isFlagged; }
	
	/**
	 * @return the number of mines in adjacent tiles.
	 */
	public int adjMines() { return adjMines; }
	
	/**
	 * Sets the number of adjacent mines to the parameter.
	 * @param adj the number of mines adjacent to this tile
	 * TODO: DOES THIS BREAK ENCAPSULATION?
	 */
	public void setAdjMines(int adj) 
	{
		adjMines = adj;
	}
}
