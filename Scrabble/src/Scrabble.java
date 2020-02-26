//Team: Camel Bois
//Members: Adam Russell - 18328861
//         Karol Wojcik - 18322146
//         Carlo Motteran -

import java.util.ArrayList;

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
    Board previousBoard;

    public Scrabble()
    {
        gamePool = new Pool();
        gameBoard = new Board();
        gameUI = new UI();
    }

    public void playGame()
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
            previousBoard.board = gameBoard.board;
            System.out.println("Current player: " + currentPlayer.getName());

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
        }
    }

    public ArrayList<Word> findAllWords(int row, int col, char dir, String wrd){

        ArrayList<Word> ans = new ArrayList<Word>();
        if(dir=='A' || dir=='a'){

            Word tmp = new Word(0,0,'d',"");
            for(int i=0; i<wrd.length(); i++){
                if(gameBoard.board[row-1][col+i][0]!=null || gameBoard.board[row+1][col+i][0]!=null) {
                    int j = 0;
                    while (gameBoard.board[row - j][col + i][0] != null) {
                        j++;
                    }
                    if(gameBoard.board[row-1][col+i][0]!=null)
                        j--;
                    tmp.setStartColumn(col + i);
                    tmp.setStartRow(row - j);
                    String a = new String();
                    while(gameBoard.board[row-j][col+i][0]!=null){
                        a=a.concat(String.valueOf(gameBoard.board[row-j][col+i][0].getLetter()));
                        j--;
                    }
                    tmp.setWord(a);
                }
                ans.add(tmp);
                ans.clear();
                tmp.clear();
            }
        }
        else if(dir=='D' || dir=='d'){

            Word tmp = new Word(0,0,'a',"");
            for(int i=0; i<wrd.length(); i++){
                if(gameBoard.board[row+i][col-1][0]!=null || gameBoard.board[row+i][col+1][0]!=null) {
                    int j = 0;
                    while (gameBoard.board[row+i][col-j][0] != null) {
                        j++;
                    }
                    if(gameBoard.board[row+i][col-1][0]!=null)
                        j--;
                    tmp.setStartColumn(col-j);
                    tmp.setStartRow(row+i);
                    String a = new String();
                    while(gameBoard.board[row+i][col-j][0]!=null){
                        a=a.concat(String.valueOf(gameBoard.board[row+i][col-j][0].getLetter()));
                        j--;
                    }
                    tmp.setWord(a);
                }
                ans.add(tmp);
                ans.clear();
                tmp.clear();
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

        while (tmpExchange.size() <= gamePool.poolSize() && keepExchanging.equals("Y") && !currentPlayer.frame.checkEmptyFrame()) {
            gameUI.print("Enter the character you want to exchange : ");
            exchangeInput = gameUI.getInput();
            exchangeInput = exchangeInput.toUpperCase();
            charExchange = exchangeInput.charAt(0);

            if (currentPlayer.frame.checkLettersFrame(charExchange)) {
                //remove tile from players frame
                tmpExchange.add(currentPlayer.frame.removeLettersFrame(charExchange));
            } else {
                gameUI.print("You do not have that tile!");
            }

            if (!currentPlayer.frame.checkEmptyFrame()) {
                do {
                    gameUI.print("Press y to exchange another letter, press n to finish exchanging : ");
                    keepExchanging = gameUI.getInput();
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
            return true;
        }

        else
        {
            return false;
        }
    }

    public void calculateScore(ArrayList<Word> wordsArray, Player currentPlayer)
    {
        int score = 0;
        int wordMultiplier = 1;
        int letterScore = 0;
        previousScore = 0;

        for (int i = 0; i < wordsArray.size(); i++)
        {
            letterScore = 0;
            wordMultiplier = 1;

            if (wordsArray.get(i).getDirection() == 'D' || wordsArray.get(i).getDirection() == 'd')
            {
                for (int j = 0; j < wordsArray.get(i).getWord().length(); j++)
                {
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

                    score += letterScore;
                }

                score *= wordMultiplier;
            }

            else
            {
                for (int j = 0; j < wordsArray.get(i).getWord().length(); j++)
                {
                    letterScore = gameBoard.board[wordsArray.get(i).getStartRow()][wordsArray.get(i).getStartColumn() + j][0].getValue();

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

                    score += letterScore;
                }

                score *= wordMultiplier;
            }
        }

        previousScore = score;
        currentPlayer.setScore(score);
    }

    public void help()
    {
        gameUI.print("QUIT: Quits the game and exits.");
        gameUI.print("PASS: Passes the current players turn, effectively doing nothing.");
        gameUI.print("EXCHANGE: Allows you to exchange some or all of your tiles.");
        gameUI.print("PLACEWORD: Enter the row your word starts with/nEnter the column where your word starts.");
        gameUI.print("/tEnter the direction your word goes (A, a, D, d, are accepted), press enter");
        gameUI.print("/tEnter the word you want placed");
        gameUI.print("NOTE: If there is a 'G' on the board, and you wish to place the word GET, and you only have E and T in your frame,");
        gameUI.print("you must specify that the word starts in the row and column where G is, and say that the word you want to place is GET");
    }

    public static void main(String[] args)
    {
        Scrabble scrabbleGame = new Scrabble();
        scrabbleGame.playGame();
    }
}
