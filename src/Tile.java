
public class Tile 
{
	private TileType tileType;
	private int adjMines; //Number of mines in adjacent tiles.
	private boolean isMine;
	
	public Tile() 
	{
		adjMines = 0;
		tileType = TileType.COVERED;
		isMine = false;
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
		tileType = TileType.UNCOVERED;
	}
	
	/**
	 * Will add a flag to the tile if it is unflagged, otherwise will remove the flag.
	 */
	public void flagTile() 
	{
		if (!isVisible())
		{
			switch (tileType)
			{
				case COVERED:
				{
					tileType = TileType.FLAG;
					break;
				}
				case FLAG:
				{
					tileType = TileType.QUESTION_MARK;
					break;
				}
				case QUESTION_MARK:
				{
					tileType = TileType.COVERED;
					break;
				}
				default: break;
			}
		}
	}
	
	/**
	 * @return true if the tile contains a mine.
	 */
	public boolean isMine()
	{
		return isMine;
	}
	
	/**
	 * @return true if the tile is uncovered.
	 */
	public boolean isVisible()
	{
		return tileType == TileType.UNCOVERED;
	}
	
	/**
	 * @return true if the tile is flagged.
	 */
	public boolean isFlagged()
	{
		return tileType == TileType.FLAG;
	}
	
	/**
	 * @return the number of mines in adjacent tiles.
	 */
	public int adjMines()
	{
		return adjMines;
	}
	
	/**
	 * Sets the number of adjacent mines to the parameter.
	 * @param adj the number of mines adjacent to this tile
	 */
	public void setAdjMines(int adj) 
	{
		adjMines = adj;
	}
}
