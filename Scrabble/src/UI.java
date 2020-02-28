//Team: Camel Bois

//    A class called UI that:
//    Gathers together all console display and input methods
//    Prompts the player whose move it is
//    Accepts keyboard input from the console
//    Parses the input to detect the following user commands: “QUIT”(quit game);
//    “PASS”(passes current move);“HELP”(displays help);<grid ref> <across/down> <word> (where <grid ref> is the position for the first letter,
//    <across/down> is the direction of word placement and <word> is the word to be placed),
//    e.g. “A1 A HELLO”; “EXCHANGE<letters>”(exchanges these letters from the frame with random letters from the pool).
//    Displays a graphical representation of the board

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UI extends Application
{
    Stage curr_window;
    Button my_button;

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

    public void print(String s)
    {
        System.out.println(s);
    }

    public String getInput()
    {
        Scanner in = new Scanner(System.in);

        String input = in.nextLine();
        return input.toUpperCase();
    }

    public Boolean checkChoice(String input)
    {
        input = input.toUpperCase();

        if (input.equals("QUIT") || input.equals("PASS") || input.equals("CHALLENGE") || input.equals("HELP") || input.equals("EXCHANGE") || input.equals("PLACEWORD")) {
            return true;
        } else {
            return false;
        }
    }






    public void displayBoard()
    {


    }


    @Override
    public void start(Stage Scrabble) throws FileNotFoundException {
        GridPane gridPane = new GridPane();

        curr_window = Scrabble;
        curr_window.setTitle("Scrabble");

        gridPane.setHgap(7);
        gridPane.setVgap(7);

        //Centre tile image set-up
        ImageView imgV;
        Image starImg = new Image(new FileInputStream("Scrabble\\res\\star.jpg"));
        imgV = new ImageView(starImg);

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Button butt = new Button("(" + i + "," + j + ")");
                butt.setPrefSize(55, 55);
                butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                butt.setStyle("-fx-background-color:#c8c2a8");
                gridPane.add(butt, j, i, 1, 1);

                //triple word score
                if ((i == 0 && j == 0) || (i == 0 && j == 7) || (i == 0 && j == 14) || (i == 7 && j == 0) || (i == 7 && j == 14) || (i == 14 && j == 0) || (i == 14 && j == 7) || (i == 14 && j == 14)) {
                    butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                    butt.setStyle("-fx-background-color:#f35f49");
                }
                //double word score
                if ((i == 1 && j == 1) || (i == 2 && j == 2) || (i == 3 && j == 3) || (i == 4 && j == 4) || (i == 10 && j == 4) || (i == 11 && j == 3) || (i == 12 && j == 2) || (i == 13 && j == 1) || (i == 1 && j == 13) || (i == 2 && j == 12) || (i == 3 && j == 11) || (i == 4 && j == 10) || (i == 13 && j == 13) || (i == 12 && j == 12) || (i == 11 && j == 11) || (i == 10 && j == 10)) {
                    butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                    butt.setStyle("-fx-background-color:#f6b9ab");
                }
                //triple letter score
                if ((i == 1 && j == 5) || (i == 1 && j == 9) || (i == 5 && j == 1) || (i == 5 && j == 5) || (i == 5 && j == 9) || (i == 5 && j == 13) || (i == 9 && j == 1) || (i == 9 && j == 5) || (i == 9 && j == 9) || (i == 9 && j == 13) || (i == 13 && j == 5) || (i == 13 && j == 9)) {
                    butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                    butt.setStyle("-fx-background-color:#3d9eb2");
                }
                //double letter score
                if ((i == 0 && j == 3) || (i == 0 && j == 11) || (i == 2 && j == 6) || (i == 2 && j == 8) || (i == 3 && j == 7) || (i == 3 && j == 0) || (i == 3 && j == 14) || (i == 6 && j == 2) || (i == 6 && j == 6) || (i == 6 && j == 8) || (i == 6 && j == 12) || (i == 7 && j == 3) || (i == 7 && j == 11) || (i == 8 && j == 2) || (i == 8 && j == 6) || (i == 8 && j == 8) || (i == 8 && j == 12) || (i == 11 && j == 0) || (i == 11 && j == 7) || (i == 11 && j == 14) || (i == 12 && j == 6) || (i == 12 && j == 8) || (i == 14 && j == 3) || (i == 14 && j == 11)) {
                    butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                    butt.setStyle("-fx-background-color:#b9d3d0");
                }
                //centre tile
                if ((i == 7 && j == 7)) {
                    butt.setText("");
                    butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                    butt.setStyle("-fx-background-color:#f6b9ab");

                    butt.setGraphic(new ImageView(starImg));
                }

                //example placement
                if ((i == 0 && j == 0)) {
                    butt.setText("A");
                    butt.setStyle("-fx-font-weight: bold");
                }
            }
        }

        Scene scene = new Scene(gridPane, 1230, 800);

        Scrabble.setScene(scene);
        Scrabble.show();

        Scanner in = new Scanner(System.in);

        System.out.println("Enter the name for Player 1 : ");
        String name1 = in.nextLine();
        System.out.println("Enter the name for Player 2 : ");
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
                else {
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
        gameUI.print("QUIT: Quits the game and exits.");
        gameUI.print("PASS: Passes the current players turn, effectively doing nothing.");
        gameUI.print("EXCHANGE: Allows you to exchange some or all of your tiles.");
        gameUI.print("PLACEWORD: Enter the row your word starts with/nEnter the column where your word starts.");
        gameUI.print("/tEnter the direction your word goes (A, a, D, d, are accepted), press enter");
        gameUI.print("/tEnter the word you want placed");
        gameUI.print("NOTE: If there is a 'G' on the board, and you wish to place the word GET, and you only have E and T in your frame,");
        gameUI.print("you must specify that the word starts in the row and column where G is, and say that the word you want to place is GET");
    }
}