//Team: Camel Bois
//Members: Adam Russell - 18328861
//		   Carlo Motteran - 18717341
//		   Karol Wojcik - 18322146

import java.util.ArrayList;

public class Board
{	
	Tile[][][] board;
	
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
	
	public void placeWord(int row, int column, char direction, String word, Player p) {
		String upperWord = word.toUpperCase();

		if (direction == 'A' || direction == 'a') {
			int j = row;
			int counter = 0;

			for (int k = column; k < (upperWord.length() + column); k++) {
				if (board[j][k][0] == null)
				{
					if (p.frame.checkLettersFrame(upperWord.charAt(counter)))
					{
						board[j][k][0] = p.frame.removeLettersFrame(upperWord.charAt(counter++));
					}

					else {
						Tile alter = p.frame.removeLettersFrame(' ');
						alter.setLetter(upperWord.charAt(counter));
						alter.setValue(0);
						board[j][k][0] = alter;
						counter++;
					}
				}

				else {
					counter++;
				}
			}
		}

		else {
			int k = column;
			int counter = 0;

			for (int j = row; j < (upperWord.length() + row); j++) {
				if (board[j][k][0] == null)
				{
					if (p.frame.checkLettersFrame(upperWord.charAt(counter)))
					{
						board[j][k][0] = p.frame.removeLettersFrame(upperWord.charAt(counter++));
					}

					else {
						Tile alter = p.frame.removeLettersFrame(' ');
						alter.setLetter(upperWord.charAt(counter));
						alter.setValue(0);
						board[j][k][0] = alter;
						counter++;
					}
				}

				else {
					counter++;
				}
			}
		}
    }


	public boolean wordPlacementCheck(int row, int col, char dir, String word, Player p){
		word = word.toUpperCase();
		//checks for a valid directional input
		if(dir!='A' && dir!='a' && dir!='D' && dir!='d') {
			return false;
		}

		//Checks if the tile is out of bounds
		if(row>14||row<0||col<0||col>14||((dir=='a'||dir=='A') && col + word.length() > 14) || ((dir=='d' || dir=='D') && row+word.length()>14)){
			return false;
		}

		//Checks that there is no tile before start of word
		if (dir == 'A' || dir == 'a')
		{
			if (board[row][col-1][0] != null)
			{
				return false;
			}
		}

		else
		{
			if (board[row-1][col][0] != null)
			{
				return false;
			}
		}

		//if necessary tiles are either in frame or on board
		ArrayList<Tile> temp = new ArrayList<Tile>();
		for (int i = 0; i < word.length(); i++)
        {
            if (dir == 'A' || dir == 'a')
            {
                if (board[row][col+i][0] != null)
                {
                    if (board[row][col+i][0].getLetter() != word.charAt(i)) {
                        for (int j = 0; j < temp.size(); j++) {
							p.frame.theFrameArray.add(temp.get(j));
						}
                        temp.clear();
                        return false;
                    }
                }

                else
                {
                    if (p.frame.checkLettersFrame(word.charAt(i)))
                    {
                        temp.add(p.frame.removeLettersFrame(word.charAt(i)));
                    }

                    else
                    {
                        if (p.frame.checkLettersFrame(' '))
                        {
                            temp.add(p.frame.removeLettersFrame(' '));
                        }

                        else
                        {
                            for (int j = 0; j < temp.size(); j++)
                            {
                                p.frame.theFrameArray.add(temp.get(j));
                            }
                            temp.clear();
                            return false;
                        }
                    }
                }
            }

            else
			{
				if (board[row+i][col][0] != null)
				{
					if (board[row+i][col][0].getLetter() != word.charAt(i)) {
						for (int j = 0; j < temp.size(); j++)
						{
							p.frame.theFrameArray.add(temp.get(j));
						}
						temp.clear();
						return false;
					}
				}

				else
				{
					if (p.frame.checkLettersFrame(word.charAt(i)))
					{
						temp.add(p.frame.removeLettersFrame(word.charAt(i)));
					}

					else
					{
						if (p.frame.checkLettersFrame(' '))
						{
							temp.add(p.frame.removeLettersFrame(' '));
						}

						else
						{
							for (int j = 0; j < temp.size(); j++)
							{
								p.frame.theFrameArray.add(temp.get(j));
							}
							temp.clear();
							return false;
						}
					}
				}
			}
        }

		for (int a = 0; a < temp.size(); a++)
        {
            p.frame.theFrameArray.add(temp.get(a));
        }
		temp.clear();
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
						if (board[row + i][col - 1][0] != null || board[row + i][col + 1][0] != null || board[row+i][col][0] != null) {
							connected = true;
						}
					}
				}
			}
			else if(dir=='A' || dir=='a'){
				if(board[row][col-1][0]!=null || board[row][col+word.length()+1][0]!=null){
					connected=true;
				}
				else{
					for(int i=0; i<word.length(); i++){
						if(board[row-1][col+i][0]!=null || board[row+1][col+i][0]!=null || board[row][col+i][0] != null){
							connected = true;
						}
					}
				}
			}
			if(!connected) {
				return connected;
			}
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
}
