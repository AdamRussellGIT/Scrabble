//Team: Camel Bois
//Members: Adam Russell - 18328861
//         Karol Wojcik - 18322146
//         Carlo Motteran - 18717341

import java.util.ArrayList;

public class Scrabble
{
    public ArrayList<Word> findAllWords(int row, int col, char dir, String wrd, Board gameBoard, Board previousBoard){
        ArrayList<Word> ans = new ArrayList<Word>();        //this will be returned
        ans.add(new Word(row,col,dir,wrd));                 //the first word added to the answer is the user input

        /*the method does the same thing twice, once for words going across and once for words going down*/
        if(dir=='A' || dir=='a'){
            for(int i=0; i<wrd.length(); i++){              //for each letter in the word

                /*if the letter isn't on the first row, and there's a tile above it, or the word isn't on the bottom row, and there's a tile below it
                * AND the letter wasn't there in the previousBoard (i.e.: was just placed by the currentPlayer)
                * then it means that there's a word on that column (col+i) that the currentPlayer extended, so we should find it and add it to the ans ArrayList*/
                if(((row-1 >= 0 && gameBoard.board[row-1][col+i][0]!=null) || (row+1 <= 14 && gameBoard.board[row+1][col+i][0]!=null)) && previousBoard.board[row][col+i][0] == null) {
                    Word tmpWord = new Word(0,0,'d',"");    //this will hold the word
                    String tmpString = new String();                                            //this will hold the string value of the word while we add the letters one by one

                    int j = 1;
                    while (row-j>=0 && gameBoard.board[row - j][col + i][0] != null) {          //this goes up until it finds the start of the word that was extended
                        j++;
                    }

                    tmpWord.setStartColumn(col + i);
                    tmpWord.setStartRow(row - j+1);

                    while(row-j<= 14 && gameBoard.board[row-j+1][col+i][0]!=null){               //this adds the letters one by one to the tmpString while going through the word
                        tmpString=tmpString.concat(String.valueOf(gameBoard.board[row-j+1][col+i][0].getLetter()));
                        j--;
                    }
                    tmpWord.setWord(tmpString);
                    ans.add(tmpWord);
                }
            }
        }
        //in case the word inputted was going down instead of across the method operates in the same way, but swaps the operations it does on rows and columns
        else if(dir=='D' || dir=='d'){
            for(int i=0; i<wrd.length(); i++){

                /*if the letter isn't on the first column, and there's a tile left of it, or the word isn't on the bottom column, and there's a tile right of it
                 * AND the letter wasn't there in the previousBoard (i.e.: was just placed by the currentPlayer)
                 * then it means that there's a word on that row (row+i) that the currentPlayer extended, so we should find it and add it to the ans ArrayList*/
                if(((col-1 >= 0 && gameBoard.board[row+i][col-1][0]!=null) || (col+1 <= 14 && gameBoard.board[row+i][col+1][0]!=null))&&previousBoard.board[row+i][col][0]==null) {
                    Word tmpWord = new Word(0,0,'a',"");
                    String tmpString = new String();

                    int j = 1;
                    while (col-j >=0 && gameBoard.board[row+i][col-j][0] != null) {
                        j++;
                    }

                    tmpWord.setStartColumn(col-j+1);
                    tmpWord.setStartRow(row+i);
                    while(col-j+1<=14 && gameBoard.board[row+i][col-j+1][0]!=null || row >= 14){
                        tmpString=tmpString.concat(String.valueOf(gameBoard.board[row+i][col-j+1][0].getLetter()));
                        j--;
                    }
                    tmpWord.setWord(tmpString);
                    ans.add(tmpWord);
                }
            }
        }
        return ans;
    }


    //Exchange method that exchanges desired words from the players frame and passes the trun
    //to their opponent. The exchanged letters are thrown into the pool after a new set
    //of letters are selected in their stead (avoids return of the same letters)
    public boolean exchange(Pool gamePool, Player currentPlayer, String[] parsedInput)
    {
        ArrayList<Tile> tmpExchange = new ArrayList<>();

        for (int i = 1; i < parsedInput.length; i++)
        {
            //if they want to exchange a blank, allow them to do so by typing blank, then change it to internal representation of blank tile char
            if (parsedInput[i].equals("BLANK"))
            {
                parsedInput[i] = " ";
            }

            if (currentPlayer.frame.checkLettersFrame(parsedInput[i].charAt(0)))
            {
                //remove tile from players frame
                tmpExchange.add(currentPlayer.frame.removeLettersFrame(parsedInput[i].charAt(0)));
            }

            else
            {
                currentPlayer.frame.theFrameArray.addAll(tmpExchange);
                return false;
            }
        }

        currentPlayer.frame.refillFrame(gamePool);

        gamePool.workPool.addAll(tmpExchange);

        tmpExchange.clear();

        return true;
    }

    //Calculating score based on the combined value of letters placed to create
    //a valid word. Also taking into account the special board tiles which
    //augment the score in the player's favour if a letter is placed upon them
    public int calculateScore(ArrayList<Word> wordsArray, Player currentPlayer, Board gameBoard)
    {
        int score = 0;
        int wordMultiplier;
        int letterScore;
        int wordScore;

        for (int i = 0; i < wordsArray.size(); i++)
        {
            wordMultiplier = 1;
            wordScore = 0;

            if (wordsArray.get(i).getDirection() == 'D' || wordsArray.get(i).getDirection() == 'd')
            {
                for (int j = 0; j < wordsArray.get(i).getWord().length(); j++)
                {
                    letterScore = 0;
                    //add the letter value to their score
                    letterScore += gameBoard.board[wordsArray.get(i).getStartRow() + j][wordsArray.get(i).getStartColumn()][0].getValue();

                    if (gameBoard.board[wordsArray.get(i).getStartRow() + j][wordsArray.get(i).getStartColumn()][1] != null)
                    {
                        //tripleWord square
                        if (gameBoard.board[wordsArray.get(i).getStartRow() + j][wordsArray.get(i).getStartColumn()][1].getLetter() == '$')
                        {
                            //increase tripleword value
                            wordMultiplier = wordMultiplier * gameBoard.board[wordsArray.get(i).getStartRow() + j][wordsArray.get(i).getStartColumn()][1].getValue();
                        }

                        //doubleWord square
                        if (gameBoard.board[wordsArray.get(i).getStartRow() + j][wordsArray.get(i).getStartColumn()][1].getLetter() == '!')
                        {
                            //increase tripleword value
                            wordMultiplier = wordMultiplier * gameBoard.board[wordsArray.get(i).getStartRow() + j][wordsArray.get(i).getStartColumn()][1].getValue();
                        }

                        //tripleLetter square
                        if (gameBoard.board[wordsArray.get(i).getStartRow() + j][wordsArray.get(i).getStartColumn()][1].getLetter() == '@')
                        {
                            //add the letter value to their score
                            letterScore *= gameBoard.board[wordsArray.get(i).getStartRow() + j][wordsArray.get(i).getStartColumn()][1].getValue();
                        }

                        //doubleLetter square
                        if (gameBoard.board[wordsArray.get(i).getStartRow() + j][wordsArray.get(i).getStartColumn()][1].getLetter() == '#')
                        {
                            //add the letter value to their score
                            letterScore *= gameBoard.board[wordsArray.get(i).getStartRow() + j][wordsArray.get(i).getStartColumn()][1].getValue();
                        }
                    }

                    wordScore += letterScore;
                }

                wordScore *= wordMultiplier;
                score += wordScore;
            }

            else
            {
                for (int j = 0; j < wordsArray.get(i).getWord().length(); j++)
                {
                    letterScore = 0;

                    letterScore += gameBoard.board[wordsArray.get(i).getStartRow()][wordsArray.get(i).getStartColumn() + j][0].getValue();

                    if (gameBoard.board[wordsArray.get(i).getStartRow()][wordsArray.get(i).getStartColumn() + j][1] != null)
                    {
                        //tripleWord square
                        if (gameBoard.board[wordsArray.get(i).getStartRow()][wordsArray.get(i).getStartColumn() + j][1].getLetter() == '$')
                        {
                            //increase tripleword value
                            wordMultiplier = wordMultiplier * gameBoard.board[wordsArray.get(i).getStartRow()][wordsArray.get(i).getStartColumn() + j][1].getValue();
                        }

                        //doubleWord square
                        if (gameBoard.board[wordsArray.get(i).getStartRow()][wordsArray.get(i).getStartColumn() + j][1].getLetter() == '!')
                        {
                            //increase tripleword value
                            wordMultiplier = wordMultiplier * gameBoard.board[wordsArray.get(i).getStartRow()][wordsArray.get(i).getStartColumn() + j][1].getValue();
                        }

                        //tripleLetter square
                        if (gameBoard.board[wordsArray.get(i).getStartRow()][wordsArray.get(i).getStartColumn() + j][1].getLetter() == '@')
                        {
                            //add the letter value to their score
                            letterScore *= gameBoard.board[wordsArray.get(i).getStartRow()][wordsArray.get(i).getStartColumn() + j][1].getValue();
                        }

                        //doubleLetter square
                        if (gameBoard.board[wordsArray.get(i).getStartRow()][wordsArray.get(i).getStartColumn() + j][1].getLetter() == '#')
                        {
                            //add the letter value to their score
                            letterScore *= gameBoard.board[wordsArray.get(i).getStartRow()][wordsArray.get(i).getStartColumn() + j][1].getValue();
                        }
                    }

                    wordScore += letterScore;
                }

                wordScore *= wordMultiplier;
                score += wordScore;
            }
        }

        if (currentPlayer.frame.checkEmptyFrame())
        {
            score += 50;
        }

        currentPlayer.setScore(score);

        return score;
    }

    //Challenge which uses binary search to check if the word that was challenged by the player exists
    //The word is searched in the dictionary and if successful causes all the tiles ("letters") of that
    //invalid word to be returned to the player; leaving you to continue your turn.

    //However if challenge is unsuccessful the turn updates leaving you to forfeit your turn as a result.
    public boolean challenge(ArrayList<Word> foundWords, String[] dictionary)
    {
        for (int i = 0; i < foundWords.size(); i++) {       //for each word the last player got points for

            if (!binarySearch(foundWords.get(i).getWord(), dictionary, 0, dictionary.length - 1)) {    //if it isn't found in the dictionary the challenge was sucessfull
                return true;
            }
        }
        return false;  //only called if all the words were found
    }

    //iterative binarySearch
    //Searches iteratively through the provided dictionary array
    //containing all the valid words in our Scrabble version
    //returning true if the word passed in is valid and false if it's invalid.

    public static boolean binarySearch(String word, String[] dictionary, int begin, int last){
        while (begin <= last)
        {
            int mid = (begin + last)/2;

            if (dictionary[mid].compareTo(word) < 0)
            {
                begin = mid + 1;
            }

            else if (dictionary[mid].compareTo(word) > 0)
            {
                last = mid - 1;
            }

            else
            {
                return true;
            }
        }

        return false;
    }
}
