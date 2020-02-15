//Team: Camel Bois
//Members: Adam Russell - 18328861
//		   Carlo Motteran - 18717341
//		   Karol Wojcik - 18322146

import java.util.ArrayList;

public class Board
{	
	Tile[][][] board;
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
	
	public Board()
	{
		board = new Tile[15][15][2];

		this.setUpBoard();
	}

	public void setUpBoard()
    {
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
                //create tripleWord Square
                if (((i % 7 == 0) && (j % 7 == 0)) && ((i != 7) || (j != 7)))
                {
                    this.board[i][j][1] = new Tile('$', 3);
                }

                //creat doubleWord Square
                else if (((i==1)&&(j==1)) || ((i==1)&&(j==13)) || ((i==2)&&(j==2)) || ((i==2)&&(j==12)) || ((i==3)&&(j==3)) || ((i==3)&&(j==11)) || ((i==4)&&(j==4)) || ((i==4)&&(j==10)) ||
                        ((i==7)&&(j==7)) || ((i==10)&&(j==4)) || ((i==10)&&(j==10)) || ((i==11)&&(j==3)) || ((i==11)&&(j==11)) || ((i==12)&&(j==2)) || ((i==12)&&(j==12)) || ((i==13)&&(j==1)) ||
                        ((i==13)&&(j==13)))
                {
                    this.board[i][j][1] = new Tile('!', 2);
                }

                //create tripleLetter Square
                else if (((i==1)&&(j==5)) || ((i==1)&&(j==9)) || ((i==5)&&(j==1)) || ((i==5)&&(j==5)) || ((i==5)&&(j==9)) || ((i==5)&&(j==13)) || ((i==9)&&(j==1)) || ((i==9)&&(j==5)) ||
                        ((i==9)&&(j==9)) || ((i==9)&&(j==13)) || ((i==13)&&(j==5)) || ((i==13)&&(j==9)))
                {
                    this.board[i][j][1] = new Tile('@', 3);
                }

                //create doubleLetter Square
                else if (((i==0)&&(j==3)) || ((i==0)&&(j==11)) || ((i==2)&&(j==6)) || ((i==2)&&(j==8)) || ((i==3)&&(j==0)) || ((i==3)&&(j==7)) || ((i==3)&&(j==14)) || ((i==6)&&(j==2)) ||
                        ((i==6)&&(j==6)) || ((i==6)&&(j==8)) || ((i==6)&&(j==12)) || ((i==7)&&(j==3)) || ((i==7)&&(j==11)) || ((i==8)&&(j==2)) || ((i==8)&&(j==6)) || ((i==8)&&(j==8)) ||
                        ((i==8)&&(j==12)) || ((i==11)&&(j==0)) || ((i==11)&&(j==7)) || ((i==11)&&(j==14)) || ((i==12)&&(j==6)) || ((i==12)&&(j==8)) || ((i==14)&&(j==3)) || ((i==14)&&(j==11)))
                {
                    this.board[i][j][1] = new Tile('#', 2);
                }

                else
                {
                    this.board[i][j][1] = null;
                }
            }
        }
    }
	
	public void resetBoard()
	{
		for(int i = 0;i < board.length;i++)
		{
			for(int j = 0;j < board.length;j++)
			{
				board[i][j][0] = null;
			}
		}
	}
	
	public void placeWord(int row, int column, char direction, String word, Player p)
	{
		String upperWord = word.toUpperCase();
		char[] wordToChar = new char[7];
			
		for (int i = 0; i < word.length(); i++)
		{
			wordToChar[i] = upperWord.charAt(i);
		}
			
		if (direction == 'A' || direction == 'a')
		{
			int j = row;
			int counter = 0;
				
			for (int k = column; k < (word.length() + column); k++)
			{
				board[j][k][0] = p.frame.removeLettersFrame(wordToChar[counter++]);
			}
		}
			
		else
		{
			int k = column;
			int counter = 0;
			
			for (int j = row; j < (word.length() + row); j++)
			{
				board[j][k][0] = p.frame.removeLettersFrame(wordToChar[counter++]);
			}
		}
	}


	public boolean wordPlacementCheck(int row, int col, char dir, String word, Player p){

		//checks for a valid directional input
		if(dir!='A' && dir!='a' && dir!='D' && dir!='d') {
			return false;
		}

		//Checks if the tile is out of bounds
		if(row>14||row<0||col<0||col>14){
			return false;
		}

		//Check if there is an existing conflicting tile already in the position
		for(int i=0;i<word.length();i++) {
			if(dir=='a'||dir=='A') {
				if(board[row+i][col][0]!=null && board[row+i][col][0].getLetter()!=word.charAt(i)) {
					return false;
				}
			}
			if(board[row][col+i][0]!=null && board[row][col+i][0].getLetter()!=word.charAt(i)) {
				return false;
			}
		}

		//finds out if the word is the first word on the board
		boolean first = true;
		for(int i =0; i<15; i++){
			for (int j=0; j<15; j++){
				if(board[i][j][0]!=null){
					first=false;
				}
			}
		}

		//if first word whether it's in the centre of the board
		if(first){
			if((dir=='A' || dir=='a') && (row!=7 || col+word.length()<7 || col>7)){
				return false;
			}
			if((dir=='D' || dir=='d') && (col!=7 || row+word.length()<7 || row>7)){
				return false;
			}
		}

		//if not first word if it's connected to another word on the board
		boolean connected = false;
		if(!first){
			if(dir=='D' || dir=='d'){
				if(board[row-1][col][0]!=null || board[row+word.length()+1][col][0]!=null){
					connected=true;
				}
				else {
					for (int i = 0; i < word.length(); i++) {
						if (board[row + i][col - 1][0] != null || board[row + i][col + 1][0] != null) {
							connected = true;
						}
					}
				}
			}
			else if(dir=='D' || dir=='d'){
				if(board[row][col-1][0]!=null || board[row][col+word.length()+1][0]!=null){
					connected=true;
				}
				else{
					for(int i=0; i<word.length(); i++){
						if(board[row-1][col+i][0]!=null || board[row+1][col+i][0]!=null){
							connected = true;
						}
					}
				}
			}
			return connected;
		}

		//if the player has the necessary letters
		ArrayList<Tile> tmp = null;
		for(int i=0; i<word.length();i++) {
			if (dir == 'A' || dir == 'a') {
				if (word.charAt(i) != board[row][col + i][0].getLetter()) {
					if (!p.frame.checkLettersFrame(word.charAt(i))) {
						return false;
					}
					tmp.add(p.frame.removeLettersFrame(word.charAt(i)));
				}
			}
			else if(dir == 'D' || dir=='d'){
				if (word.charAt(i) != board[row + i][col][0].getLetter()) {
					if (!p.frame.checkLettersFrame(word.charAt(i))) {
						return false;
					}
					tmp.add(p.frame.removeLettersFrame(word.charAt(i)));
				}
			}
		}
		for(int j=0; j<word.length();j++){
			p.frame.theFrameArray.add(tmp.remove(j));
		}

		//if placement uses at least one letter from rack
		boolean usesRack = false;
		for(int i=0; i<word.length(); i++){
			if(dir=='A' || dir=='a'){
				if(board[row][col+i][0]==null){
					usesRack = true;
				}
			}
			else if(dir=='D' || dir=='d'){
				if(board[row+i][col][0]==null){
					usesRack = true;
				}
			}
		}
		return usesRack;
	}
	public String toString()
	{
		String gameBoard = "\t";
		
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
				if (board[j][k][0] == null)
				{
					gameBoard += ("_\t");
				}
				
				else
				{
					gameBoard += ((board[j][k][0].getLetter()) + "\t");
				}
			}
			
			gameBoard = gameBoard + "\n\n";
		}
		
		return gameBoard;
	}
	
	public static void main(String[] args)
	{
		Pool pool = new Pool();
		Player p1 = new Player("Adam", pool);
		System.out.println(p1.toString());
		Board theBoard = new Board();
		System.out.println(theBoard.board[0][0][1].getValue());
        System.out.println(theBoard.board[2][6][1].getLetter());
	}
}
