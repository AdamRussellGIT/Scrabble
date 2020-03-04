//Team: Camel Bois
//Members: Adam Russell - 18328861
//         Karol Wojcik - 18322146
//         Carlo Motteran -

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Scrabble
{
    Player player1;
    Player player2;
    Pool gamePool;
    Board gameBoard;
    UI gameUI;

    int turn = 0;
    Player currentPlayer;
    String choice;

    int previousScore = 0;
    Board previousBoard = new Board();

    /*public Scrabble()
    {
        gamePool = new Pool();
        gameBoard = new Board();
        gameUI = new UI();
    }*/

    /*public void playGame()
    {
        gameUI.print("Enter the name for Player 1 : ");
        String name1 = gameUI.getInput();
        gameUI.print("Enter the name for Player 2 : ");
        String name2 = gameUI.getInput();
        gameUI.print("Let's play!");

        player1 = new Player(name1, gamePool);
        player2 = new Player(name2, gamePool);

        while (!gamePool.poolEmpty() && (!player1.frame.checkEmptyFrame() || !player2.frame.checkEmptyFrame()))
        {
            if (turn % 2 == 0) {
                currentPlayer = player1;
            } else {
                currentPlayer = player2;
            }

            //set a previous board incase we need to remove letters after a players turn
            for (int i = 0; i < 15; i++)
            {
                for (int j = 0; j < 15; j++)
                {
                    for (int k = 0; k < 2; k++)
                    {
                        previousBoard.board[i][j][k] = gameBoard.board[i][j][k];
                    }
                }
            }

            System.out.println("Current player: " + currentPlayer.getName() + " and their score : " + currentPlayer.getScore());
            System.out.println(currentPlayer.toString());

            do {
                gameUI.print("Enter 'QUIT', 'PASS', 'EXCHANGE', 'HELP', 'PLACEWORD'");
                choice = gameUI.getInput();
            } while (!gameUI.checkChoice(choice));

            //don't increment turn if the player wants help
            if (choice.equals("HELP")) {
                help();
            }

            else if (choice.equals("PLACEWORD")) {
                //if they placed a valid word, move to the next turn
                if (placeWordBoard(currentPlayer))
                {
                    turn++;
                }

                //if they did not pass the wordPlacementCheck, stay on the current players turn and tell them
                else
                {
                    gameUI.print("That was an invalid word placement!");
                }
            }

            else {
                if (choice.equals("QUIT")) {
                    gameUI.print("User chose to quit the game!");
                    gamePool.workPool.clear();
                }

                if (choice.equals("PASS")) {
                    gameUI.print("User chose to pass!");
                }

                if (choice.equals("EXCHANGE")) {
                    exchange(gamePool, currentPlayer);
                }

                turn++;
            }
            currentPlayer.frame.refillFrame(gamePool);
            System.out.println(gameBoard.toString());
        }
    }

    public ArrayList<Word> findAllWords(int row, int col, char dir, String wrd){
        ArrayList<Word> ans = new ArrayList<Word>();
        ans.add(new Word(row,col,dir,wrd));
        if(dir=='A' || dir=='a'){
            for(int i=0; i<wrd.length(); i++){
                String a = new String();
                if((gameBoard.board[row-1][col+i][0]!=null || gameBoard.board[row+1][col+i][0]!=null) && previousBoard.board[row][col+i][0] == null) {
                    Word tmp = new Word(0,0,'d',"");
                    int j = 1;
                    while (gameBoard.board[row - j][col + i][0] != null) {
                        j++;
                    }
                    if(gameBoard.board[row-1][col+i][0]!=null)
                        j--;
                    tmp.setStartColumn(col + i);
                    tmp.setStartRow(row - j);
                    while(gameBoard.board[row-j][col+i][0]!=null || col >= 14){
                        a=a.concat(String.valueOf(gameBoard.board[row-j][col+i][0].getLetter()));
                        j--;
                    }
                    tmp.setWord(a);
                    ans.add(tmp);
                }
            }
        }
        else if(dir=='D' || dir=='d'){
            for(int i=0; i<wrd.length(); i++){
                String a = new String();
                if((gameBoard.board[row+i][col-1][0]!=null || gameBoard.board[row+i][col+1][0]!=null)&&previousBoard.board[row+i][col][0]==null) {
                    System.out.println("Yoink");
                    Word tmp = new Word(0,0,'a',"");
                    int j = 1;
                    while (gameBoard.board[row+i][col-j][0] != null) {
                        j++;
                    }
                    if(gameBoard.board[row+i][col-1][0]!=null)
                        j--;
                    tmp.setStartColumn(col-j);
                    tmp.setStartRow(row+i);
                    while(gameBoard.board[row+i][col-j][0]!=null || row >= 14){
                        a=a.concat(String.valueOf(gameBoard.board[row+i][col-j][0].getLetter()));
                        j--;
                    }
                    System.out.println("a is : " + a);
                    tmp.setWord(a);
                    System.out.println("What is a using get : " + tmp.getWord());
                    ans.add(tmp);
                    for (int z = 0; z < ans.size(); z++)
                    {
                        System.out.println("Whats in ans : " + ans.get(z).getWord());
                    }
                }
            }

        }
        return ans;
    }


    public void exchange(Pool gamePool, Player currentPlayer)
    {
        ArrayList<Tile> tmpExchange = new ArrayList<>();
        String keepExchanging = "Y";
        String exchangeInput;
        char charExchange;
        Scanner in = new Scanner(System.in);

        while (tmpExchange.size() <= gamePool.poolSize() && keepExchanging.equals("Y") && !currentPlayer.frame.checkEmptyFrame()) {
            System.out.println("Enter the character you want to exchange : ");
            exchangeInput = in.nextLine();
            exchangeInput = exchangeInput.toUpperCase();
            charExchange = exchangeInput.charAt(0);

            if (currentPlayer.frame.checkLettersFrame(charExchange)) {
                //remove tile from players frame
                tmpExchange.add(currentPlayer.frame.removeLettersFrame(charExchange));
            } else {
                System.out.println("You do not have that tile!");
            }

            if (!currentPlayer.frame.checkEmptyFrame()) {
                do {
                    System.out.println("Press y to exchange another letter, press n to finish exchanging : ");
                    keepExchanging = in.nextLine();
                } while (!keepExchanging.equals("Y") && !keepExchanging.equals("N"));
            }
        }

        currentPlayer.frame.refillFrame(gamePool);

        gamePool.workPool.addAll(tmpExchange);

        tmpExchange.clear();
    }

    public boolean placeWordBoard(Player currentPlayer)
    {
        int row;
        int column;
        char direction;
        String word;

        gameUI.print("Enter row that your word will start on : ");
        row = Integer.parseInt(gameUI.getInput());

        gameUI.print("Enter column that your word will start on : ");
        column = Integer.parseInt(gameUI.getInput());

        gameUI.print("Enter direction your word will go : ");
        direction = gameUI.getInput().charAt(0);

        gameUI.print("Enter the word you want to place");
        word = gameUI.getInput();

        if(gameBoard.wordPlacementCheck(row, column, direction, word, currentPlayer))
        {
            gameBoard.placeWord(row, column, direction, word, currentPlayer);
            calculateScore(findAllWords(row, column, direction, word), currentPlayer);
            return true;
        }

        else
        {
            return false;
        }
    }

    public void calculateScore(ArrayList<Word> wordsArray, Player currentPlayer)
    {
        System.out.println("Hello there");
        for (int i = 0; i < wordsArray.size(); i++)
        {
            System.out.println(wordsArray.get(i).getWord());
        }
        int score = 0;
        int wordMultiplier;
        int letterScore;
        int wordScore;
        previousScore = 0;

        for (int i = 0; i < wordsArray.size(); i++)
        {
            wordMultiplier = 1;
            wordScore = 0;

            System.out.println(wordsArray.get(i).getWord() + " row = " + wordsArray.get(i).getStartRow() + " column = " + wordsArray.get(i).getStartColumn());

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

        previousScore = score;
        currentPlayer.setScore(score);
    }

    public void help()
    {
        System.out.println("QUIT: Quits the game and exits.");
        System.out.println("PASS: Passes the current players turn, effectively doing nothing.");
        System.out.println("EXCHANGE: Allows you to exchange some or all of your tiles.");
        System.out.println("PLACEWORD: Enter the row your word starts with/nEnter the column where your word starts.");
        System.out.println("/tEnter the direction your word goes (A, a, D, d, are accepted), press enter");
        System.out.println("/tEnter the word you want placed");
        System.out.println("NOTE: If there is a 'G' on the board, and you wish to place the word GET, and you only have E and T in your frame,");
        System.out.println("you must specify that the word starts in the row and column where G is, and say that the word you want to place is GET");
    }*/
}
