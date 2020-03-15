//Team: Camel Bois
//Members: Adam Russell - 18328861
//         Karol Wojcik - 18322146
//         Carlo Motteran -

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Scrabble
{
    public ArrayList<Word> findAllWords(int row, int col, char dir, String wrd, Board gameBoard, Board previousBoard){
        ArrayList<Word> ans = new ArrayList<Word>();    //this will be returned
        ans.add(new Word(row,col,dir,wrd));             // the first word added to the ArrayList is the word inputted by the user

        /*the program is divided in two halves that do the same process in the same way, but swap the rows and columns
          accordingly with the orientation of the inputted word*/

        if(dir=='A' || dir=='a'){    //going across
            for(int i=0; i<wrd.length(); i++){  //for every square of the board on which the word has a letter

                /*if the word isn't placed in row 0, and there's a tile above it, or the word isn't on row 14 and there's a tile below it
                * AND the tile that we're checking was not there in the previousBoard (i.e.: was just placed to form the word)
                * then it means that there is another word that was formed by the player, going down, in that column(col+i)
                * */
                if(((row-1 >= 0 && gameBoard.board[row-1][col+i][0]!=null) || (row+1 <= 14 && gameBoard.board[row+1][col+i][0]!=null)) && previousBoard.board[row][col+i][0] == null) {
                    Word tmpWord = new Word(0,0,'d',"");    //then a new word is made
                    String tmpString = new String();                                            // and a new String is made to store that word temporarily

                    int j = 1;
                    while (row-j>=0 && gameBoard.board[row - j][col + i][0] != null) {          //while we haven't reached the top row, and there's a tile above this we keep looking for the start of the new word
                        j++;
                    }
                    if(gameBoard.board[row-1][col+i][0]!=null)                                  //if the
                        j--;
                    tmpWord.setStartColumn(col + i);
                    tmpWord.setStartRow(row - j);
                    while(row-j <= 14 && gameBoard.board[row-j][col+i][0]!=null){
                        tmpString=tmpString.concat(String.valueOf(gameBoard.board[row-j][col+i][0].getLetter()));
                        j--;
                    }
                    tmpWord.setWord(tmpString);
                    ans.add(tmpWord);
                }
            }
        }
        else if(dir=='D' || dir=='d'){
            for(int i=0; i<wrd.length(); i++){
                String a = new String();
                if(((col-1 >= 0 && gameBoard.board[row+i][col-1][0]!=null) || (col+1 <= 14 && gameBoard.board[row+i][col+1][0]!=null))&&previousBoard.board[row+i][col][0]==null) {
                    Word tmp = new Word(0,0,'a',"");
                    int j = 1;
                    while (col-j >=0 && gameBoard.board[row+i][col-j][0] != null) {
                        j++;
                    }
                    if(gameBoard.board[row+i][col-1][0]!=null)
                        j--;
                    tmp.setStartColumn(col-j);
                    tmp.setStartRow(row+i);
                    while(col-j<=14 && gameBoard.board[row+i][col-j][0]!=null || row >= 14){
                        a=a.concat(String.valueOf(gameBoard.board[row+i][col-j][0].getLetter()));
                        j--;
                    }
                    tmp.setWord(a);
                    ans.add(tmp);
                   }
            }

        }
        return ans;
    }


    public boolean exchange(Pool gamePool, Player currentPlayer, String[] parsedInput)
    {
        boolean goodInput = true;
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

    public void calculateScore(ArrayList<Word> wordsArray, Player currentPlayer, Board gameBoard, int previousScore)
    {
        int score = 0;
        int wordMultiplier;
        int letterScore;
        int wordScore;
        previousScore = 0;

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

        previousScore = score;
        currentPlayer.setScore(score);
    }
}
