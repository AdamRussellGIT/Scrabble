//Team: Camel Bois
//Members: Adam Russell - 18328861
//		   Carlo Motteran - 18717341
//		   Karol Wojcik - 18322146

public class Board 
{
	Tile[][] board;
	
	public Board()
	{
		board = new Tile[15][15];
	}
	
	public Tile[][] placeWord()
	{
		return board;
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
		String gameBoard = "  ";
		
		for (int i = 1; i <= board.length; i++)
		{
			gameBoard += (i + "\t");
		}
		
		gameBoard += "\n";
		
		for (int j = 0; j < board.length; j++)
		{
			if (j < 9)
			{
				gameBoard += ((j + 1) + "  ");
			}
			
			else
			{
				gameBoard += ((j + 1) + " ");
			}
			
			for (int k = 0; k < board.length; k++)
			{
				if (board[j][k] == null)
				{
					gameBoard += ("_\t");
				}
				
				else
				{
					gameBoard += ((board[j][k].getLetter()) + "\t");
				}
			}
			
			gameBoard = gameBoard + "\n\n";
		}
		
		return gameBoard;
	}
	
	public static void main(String[] args)
	{
		Board theBoard = new Board();
		theBoard.board[3][4] = new Tile('S', 3);
		theBoard.board[3][5] = new Tile('E', 5);
		theBoard.board[3][6] = new Tile('X', 8);
		System.out.println(theBoard.toString());
	}
}
