//Team: Camel Bois
//Members: Adam Russell - 18328861
//		   Carlo Motteran - 18717341
//		   Karol Wojcik - 18322146

public class Board 
{
	Tile board[][];
	
	public Board()
	{
		
	}
	
	public Tile[][] resetBoard()
	{
		for(int i = 0;i < board.length;i++)
		{
			for(int j = 0;j < board.length;j++)
			{
				board[i][j] = null;
			}
		}
		return board;
	}
	
	
	public void wordPlacementCheck()
	{
		
		
		//Check if the tile is out of bounds
		//Check if there is an existing tile already in the position
		//if first word whether it's in the centre of the board
		//if not first word if it's connected to another word on the board
		//if the player has the necessary letters
		//if the word conflicts with existing letters
		//if placement uses at least one letter from rack
	}
	public String toString()
	{
		String gameBoard = "";
		
		return gameBoard;
	}
}
