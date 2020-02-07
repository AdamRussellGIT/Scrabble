//Team: Camel Bois
//Members: Adam Russell - 18328861
//		   Carlo Motteran - 18717341
//		   Karol Wojcik - 18322146

public class Board 
{	
	Tile[][] board;
	private int row;
	private int column;
	private char direction;
	private String word;
	
	int[][] tripleWord = new int[][] {	{0,0},{0,7},{0,14},{7,0},{7,14},{14,0},{14,7},{14,14}};

	int[][] doubleWord = new int[][] {	{1,1},{1,13},{2,2},{2,12},{3,3},{3,11},
										{4,4},{4,10},{7,7},{10,4},{10,10},{11,3},
										{11,11},{12,2},{12,12},{13,1},{13,13}};

	int[][] tripleLetter = new int[][]{	{1,5},{1,9},{5,1},{5,5},{5,9},{5,13},
										{9,1},{9,5},{9,9},{9,13},{13,5},{13,9}};
		
	int[][] doubleLetter = new int[][]{	{0,3},{0,11},{2,6},{2,8},{3,0},{3,7},{3,14},{6,2},{6,6},
										{6,8},{6,12},{7,3},{7,11},{8,2},{8,6},{8,8},{8,12},{11,0},
										{11,7},{11,14},{12,6},{12,8},{14,3},{14,11}};

	private String test = "DummyThicc";
	
	public Board()
	{
		board = new Tile[15][15];
	}
	
	public void resetBoard()
	{
		for(int i = 0;i < board.length;i++)
		{
			for(int j = 0;j < board.length;j++)
			{
				board[i][j] = null;
			}
		}
	}
	
	//need params
	public void placeWord()
	{
		
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
		
		for (int i = 0; i < board.length; i++)
		{
			gameBoard += (i + "\t");
		}
		
		gameBoard += "\n";
		
		for (int j = 0; j < board.length; j++)
		{
			if (j < 10)
			{
				gameBoard += (j + "  ");
			}
			
			else
			{
				gameBoard += (j + " ");
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
