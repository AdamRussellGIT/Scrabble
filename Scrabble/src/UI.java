//Team: Camel Bois
//Members: Adam Russell - 18328851
//         Karol Wojcik - 18322146
//         Carlo Motteran - 18717341

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
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.stage.Stage;


import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class UI extends Application
{
    Stage curr_window;
    GridPane gridPane;
    GridPane framePane;
    GridPane rightPane;

    TextField input;
    Label currPlayer;
    Label currScore;
    Label turnText;
    Label helpHint;

    Button frameBoxButton;

    Label colourKey;
    Label doubleLetterScore;
    Label tripleLetterScore;
    Label doubleWordScore;
    Label tripleWordScore;

    Button doubleLetterScoreButton;
    Button tripleLetterScoreButton;
    Button doubleWordScoreButton;
    Button tripleWordScoreButton;

    Player playerOne;
    Player playerTwo;
    Scrabble gameLogic;
    Pool gamePool;
    Board gameBoard;

    int turn = -1;
    Player currentPlayer;
    String choice;

    String[] dictionary;

    int endcounter = 0;

    int previousScore = 0;
    Board previousBoard = new Board();
    ArrayList<Word> foundWords;

    public UI() throws FileNotFoundException {
    }

    private void removeSpecialSquares(Board gameBoard)
    {
        for (int i = 0; i < 15; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                if (gameBoard.board[i][j][0] != null)
                {
                    gameBoard.board[i][j][1] = null;
                }
            }
        }
    }

    public void updateBoard(Board gameBoard)
    {
        for (int i = 0; i < 15; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                Button butt = new Button("(" + i + "," + j + ")");

                if (gameBoard.board[i][j][0] != null)
                {
                    butt.setPrefSize(70, 70);
                    butt.setText(String.valueOf(gameBoard.board[i][j][0].getLetter()));
                    butt.setStyle("-fx-font-weight: bold");
                    gridPane.add(butt, j, i, 1, 1);
                }

                //we will need this else statement if we want to return the board to what it looked like during the previous turn, in case of a successful challenge
                else {
                    butt.setPrefSize(70, 70);
                    butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                    butt.setStyle("-fx-background-color:#c8c2a8");
                    gridPane.add(butt, j, i, 1, 1);

                    //triple word score
                    if ((i == 0 && j == 0) || (i == 0 && j == 7) || (i == 0 && j == 14) || (i == 7 && j == 0) || (i == 7 && j == 14)
                            || (i == 14 && j == 0) || (i == 14 && j == 7) || (i == 14 && j == 14)) {
                        butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                        butt.setStyle("-fx-background-color:#f35f49");
                    }
                    //double word score
                    if ((i == 1 && j == 1) || (i == 2 && j == 2) || (i == 3 && j == 3) || (i == 4 && j == 4) || (i == 10 && j == 4) || (i == 11 && j == 3) ||
                            (i == 12 && j == 2) || (i == 13 && j == 1) || (i == 1 && j == 13) || (i == 2 && j == 12) || (i == 3 && j == 11) || (i == 4 && j == 10) ||
                            (i == 13 && j == 13) || (i == 12 && j == 12) || (i == 11 && j == 11) || (i == 10 && j == 10)) {
                        butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                        butt.setStyle("-fx-background-color:#f6b9ab");
                    }
                    //triple letter score
                    if ((i == 1 && j == 5) || (i == 1 && j == 9) || (i == 5 && j == 1) || (i == 5 && j == 5) || (i == 5 && j == 9) || (i == 5 && j == 13) ||
                            (i == 9 && j == 1) || (i == 9 && j == 5) || (i == 9 && j == 9) || (i == 9 && j == 13) || (i == 13 && j == 5) || (i == 13 && j == 9)) {
                        butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                        butt.setStyle("-fx-background-color:#3d9eb2");
                    }
                    //double letter score
                    if ((i == 0 && j == 3) || (i == 0 && j == 11) || (i == 2 && j == 6) || (i == 2 && j == 8) || (i == 3 && j == 7) || (i == 3 && j == 0) ||
                            (i == 3 && j == 14) || (i == 6 && j == 2) || (i == 6 && j == 6) || (i == 6 && j == 8) || (i == 6 && j == 12) || (i == 7 && j == 3) ||
                            (i == 7 && j == 11) || (i == 8 && j == 2) || (i == 8 && j == 6) || (i == 8 && j == 8) || (i == 8 && j == 12) || (i == 11 && j == 0) ||
                            (i == 11 && j == 7) || (i == 11 && j == 14) || (i == 12 && j == 6) || (i == 12 && j == 8) || (i == 14 && j == 3) || (i == 14 && j == 11)) {
                        butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                        butt.setStyle("-fx-background-color:#b9d3d0");
                    }
                    //centre tile
                    if ((i == 7 && j == 7)) {
                        butt.setText("");
                        butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                        butt.setStyle("-fx-background-color:#706a6c");
                    }
                }
            }
        }
    }

    public void updateFrame(Frame f)
    {
        for (int i = 0; i < f.theFrameArray.size(); i++)
        {
            Button frameButt = new Button(String.valueOf(f.theFrameArray.get(i).getLetter()));
            frameButt.setPrefSize(92, 92f);
            frameButt.setPrefHeight(130);

            frameButt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
            frameButt.setStyle("-fx-background-color:#c8c2a8");

            framePane.add(frameButt, i, 0);
        }


        //checks if player has less than 7 tiles
        //if so, replace with pseudo blank (invisible) tiles
        //same colour as background
        for (int j = f.theFrameArray.size(); j < 7; j++)
        {
            Button emptyButt = new Button("");
            emptyButt.setPrefSize(92, 92f);
            emptyButt.setPrefHeight(130);

            emptyButt.setStyle("-fx-border-color: #f4f4f4; -fx-border-width: 2px");
            emptyButt.setStyle("-fx-background-color:#f4f4f4");

            framePane.add(emptyButt, j, 0);
        }
    }

    @Override
    public void start(Stage Scrabble) throws FileNotFoundException
    {
        Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
        gameLogic = new Scrabble();
        gameBoard = new Board();
        gamePool = new Pool();
        curr_window = Scrabble;
        curr_window.setTitle("Scrabble");

        gridPane = new GridPane();
        gridPane.setHgap(7);
        gridPane.setVgap(7);

        //We divide the scene is divided into two HBoxes
        //The left HBox holds the Scrabble Board
        //The right HBox holds the gameInfo

        HBox leftH = new HBox(8);
        HBox rightH = new HBox(8);
        rightPane = new GridPane();
        rightPane.setVgap(screenRes.getHeight()/4);

        VBox gameInfo = new VBox(15);

        VBox playerInfo = new VBox(2);

        currPlayer = new Label("Current Player: ");
        currPlayer.setFont(new Font(30));
        currPlayer.setStyle("-fx-font-weight: bold");


        currScore = new Label("Current Score: ");
        currScore.setFont(new Font(30));
        currScore.setStyle("-fx-font-weight: bold");

        turnText = new Label("Turn: ");
        turnText.setFont(new Font(30));
        turnText.setStyle("-fx-font-weight: bold");


        //setting up key
        colourKey = new Label("Board Colour Key: ");
        colourKey.setFont(new Font(18));
        colourKey.setStyle("-fx-font-weight: bold");

        doubleLetterScoreButton = new Button(" ");
        tripleLetterScoreButton = new Button(" ");
        doubleWordScoreButton = new Button(" ");
        tripleWordScoreButton = new Button(" ");

        doubleLetterScore = new Label("Double Letter Score" );
        doubleLetterScore.setGraphic(doubleLetterScoreButton);
        doubleLetterScoreButton.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
        doubleLetterScoreButton.setStyle("-fx-background-color:#b9d3d0");
        doubleLetterScore.setFont(new Font(15));

        tripleLetterScore = new Label("Triple Letter Score");
        tripleLetterScore.setGraphic(tripleLetterScoreButton);
        tripleLetterScore.setFont(new Font(15));
        tripleLetterScoreButton.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
        tripleLetterScoreButton.setStyle("-fx-background-color:#3d9eb2");

        doubleWordScore = new Label("Double Word Score");
        doubleWordScore.setGraphic(doubleWordScoreButton);
        doubleWordScore.setFont(new Font(15));
        doubleWordScoreButton.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
        doubleWordScoreButton.setStyle("-fx-background-color:#f6b9ab");

        tripleWordScore = new Label("Triple Word Score");
        tripleWordScore.setGraphic(tripleWordScoreButton);
        tripleWordScore.setFont(new Font(15));
        tripleWordScoreButton.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
        tripleWordScoreButton.setStyle("-fx-background-color:#f35f49");

        playerInfo.getChildren().addAll(currPlayer,currScore,turnText,colourKey,doubleLetterScore,tripleLetterScore,doubleWordScore,tripleWordScore);
        VBox frameBox = new VBox(2);

        frameBoxButton = new Button("");
        frameBoxButton.setTranslateX(20);
        frameBoxButton.setStyle("-fx-background-color: #666666; -fx-border-width: 2px");
        frameBoxButton.setMaxHeight(70);
        frameBoxButton.setMaxWidth(710);

        framePane = new GridPane();
        framePane.setHgap(11);
        framePane.setPadding(new Insets(0, 0, 0, 20));
        frameBox.getChildren().addAll(framePane,frameBoxButton);

        VBox inputBox = new VBox(2);

        helpHint = new Label("Type \"HELP\" for help");
        helpHint.setFont(new Font(15));

        helpHint.setTextFill(Color.BLACK);

        //setting up text field
        input = new TextField();
        input.setPrefHeight(50);
        input.setPrefWidth(400);
        input.setFont(new Font(30));

        inputBox.getChildren().addAll(helpHint,input);

        gameInfo.getChildren().addAll(playerInfo,frameBox,inputBox);

        //setting up initial scrabble board
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Button butt = new Button("(" + i + "," + j + ")");
                butt.setPrefSize(70, 70);
                butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                butt.setStyle("-fx-background-color:#c8c2a8");
                gridPane.add(butt, j, i, 1, 1);

                //triple word score
                if ((i == 0 && j == 0) || (i == 0 && j == 7) || (i == 0 && j == 14) || (i == 7 && j == 0) || (i == 7 && j == 14)
                        || (i == 14 && j == 0) || (i == 14 && j == 7) || (i == 14 && j == 14)) {
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
                    butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                    butt.setStyle("-fx-background-color:#706a6c");
                }
            }
        }

        getNames();

        setUpDictionary();

        changeCurrentPlayer();

        //gathering input from user
        input.setOnAction(e -> {
            String receivedInput = input.getText().toUpperCase();
            String[] parsedInput = receivedInput.split(" ");

            //parsing input
            if(parsedInput[0].equals("CHALLENGE")) {

                if(turn == 0)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error!");
                    alert.setContentText("Can't Challenge on the first turn!");
                    alert.initOwner(curr_window);

                    alert.showAndWait();
                }
                else
                {
                    if(!gameLogic.challenge(foundWords,dictionary))
                    {
                        removeSpecialSquares(gameBoard);
                        //Pop up unsucessful challenge
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Unsuccesful Challenge");
                        alert.setContentText("The challenge was unsuccessful, you will now miss your turn");
                        alert.initOwner(curr_window);

                        alert.showAndWait();

                    }
                    else
                    {
                        for(int i=0; i<15; i++){
                            for(int j=0; j<15; j++){
                                if(gameBoard.board[i][j][0]!=previousBoard.board[i][j][0]){

                                    previousPlayer.frame.add(gameBoard.board[i][j][0]);
                                    gameBoard.board[i][j][0] = previousBoard.board[i][j][0];
                                }
                            }
                        }
                        previousPlayer.setScore(-previousScore);

                        //Reset to previous score

                        //Pop up successful challenge
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Succesful Challenge");
                        alert.setContentText("The challenge was successful!");
                        alert.initOwner(curr_window);

                        alert.showAndWait();
                        //update turn
                        endcounter++;

                    }

                }
            }
            else{
                removeSpecialSquares(gameBoard);
                if (parsedInput[0].equals("EXCHANGE"))
                {
                    endcounter++;
                    if (parsedInput.length > 8 || parsedInput.length < 2)
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Error!");
                        alert.setContentText("Too many, or too little arguments!");

                        alert.showAndWait();
                    }

                    else
                    {
                        if (!gameLogic.exchange(gamePool, currentPlayer, parsedInput))
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText("Error!");
                            alert.setContentText("You don't have one of the tiles!");

                            alert.showAndWait();
                        }

                        else
                        {
                            changeCurrentPlayer();
                        }
                    }
                }

                else if (parsedInput[0].equals("PASS"))
                {
                    changeCurrentPlayer();
                    endcounter++;
                }

                else if (parsedInput[0].equals("HELP"))
                {
                    Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);

                    helpAlert.setContentText(" INSTRUCTIONS: \n  How to PLAY: \n *Both lower and UPPERCASE input is acceptable " +
                            "\n *Blank tile in the frame constitutes a blank tile \n  that can be substituted for any letter in a word you wish to place\n\n " +
                            "TO PLACE A WORD: \n Input Format: (coordinates, direction, word) \n Coordinates: \n Coordinates range from 0 - 14 and should have " +
                            "have a space between them i.e. (0 0). \n If you are starting the game you MUST place your first word so that one of the letters is placed on (7 7) " +
                            "*the key centre tile \n\n Direction: \n The word can either go down or across on the board and is denoted \n as either A - ACROSS " +
                            "and D - DOWN, after having chosen the coordinates placing a word would \n look as follows (7 7 A). *Note the space between coordinates & direction: e.g. (77a) or (7 7a) are INVALID!" +
                            "\n\n Word: \n The word must use available tiles from your frame and connect them appropriately  to another word on the Scrabble board." +
                            "\n If the first letter of your word already resides on the Scrabble board, simply enter the coordinates of that letter and type \n in the " +
                            "word fully. \n The format is as follows: (7 7 A dog) *notice the space between the direction and the word for valid input. \n " +
                            "\n\n TO PASS YOUR TURN: \n In the event that you wish to forfeit your turn if you can't think of a suitable word, type in \"PASS\" into " +
                            "the text-field. \n In the event the game consists of 6 PASS/or Non-Scoring turns the game terminates. \n\n\n TO EXCHANGE A WORD \n " +
                            "If you wish to exchange certain letters in your frame from the pool you must enter the desired letters in the textfield in the " +
                            "\n following format: (Exchange a b c). \n *Note the " +
                            "letters must be in your frame and must have a space between them. If you have duplicate letters the same procedure \n  follows e.g. (a a). " +
                            "Once you have exchanged your letters they will appear in your frame and the turn will be tunred over to your \n  opponent. \n *In the event " +
                            "the game consists of 6 EXCHANGE/or Non-Scoring turns the game terminates." +
                            "\n\n TO QUIT THE GAME: \n If you wish to quit the game type in \"QUIT\" \n\n Game End Conditions: \n 1.When QUIT is typed in \n 2.PASS/EXCHANGE exceeds 6 continuous occurances \n 3.No more letters" +
                            "are left in either the Pool/Frame \n 4.No more words can be placed in the board.");
                    helpAlert.initOwner(curr_window);
                    helpAlert.setTitle("Help");
                    helpAlert.setHeaderText("Help Information");
                    helpAlert.getDialogPane().setPrefSize(720,900);
                    helpAlert.showAndWait();

                    gridPane.requestFocus();

                }

                else if (parsedInput[0].equals("QUIT"))
                {
                    Alert quit = new Alert(Alert.AlertType.CONFIRMATION, "Do you really wish to quit? you'll lose the game", ButtonType.YES, ButtonType.NO);
                    quit.initOwner(curr_window);
                    quit.showAndWait();
                    if(quit.getResult()==ButtonType.YES){
                        changeCurrentPlayer();
                        endGame(currentPlayer);
                    }
                }

                //if none of the above, assume player is trying to placeword
                else
                {
                    //check if there are 4 things in the array (row, col, direction, word)
                    if (parsedInput.length != 4)
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initOwner(curr_window);
                        alert.setHeaderText("Error!");
                        alert.setContentText("Invalid parameters to place a word!");

                        alert.showAndWait();
                    }

                    //parse input to row column etc
                    else
                    {
                        int row = Integer.parseInt(parsedInput[0]);
                        int column = Integer.parseInt(parsedInput[1]);
                        char direction = parsedInput[2].charAt(0);
                        String word = parsedInput[3];

                        //run checks and placeword etc otherwise throw error yada yada ;)
                        if (gameBoard.wordPlacementCheck(row, column, direction, word, currentPlayer))
                        {
                            //update previous board
                            for (int i = 0; i < 15; i++)
                            {
                                for (int j = 0; j < 15; j++)
                                {
                                    previousBoard.board[i][j][0] = gameBoard.board[i][j][0];
                                    previousBoard.board[i][j][1] = gameBoard.board[i][j][1];
                                }
                            }

                            gameBoard.placeWord(row, column, direction, word, currentPlayer);

                            //check for bonus 50 points
                            if (currentPlayer.frame.checkEmptyFrame())
                            {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.initOwner(curr_window);
                                alert.setHeaderText("Congratulations!");
                                alert.setContentText("You used all 7 of the tiles in your frame, and got a 50 point bonus!");

                                alert.showAndWait();
                            }

                            //finding all words created by the word placement
                            foundWords = gameLogic.findAllWords(row, column, direction, word, gameBoard, previousBoard);

                            gameLogic.calculateScore(foundWords, currentPlayer, gameBoard, previousScore);

                            endcounter=0;

                            changeCurrentPlayer();
                        }

                        else
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.initOwner(curr_window);
                            alert.setHeaderText("Error!");
                            alert.setContentText("Failed Word Placement Check!");

                            alert.showAndWait();
                        }
                    }
                }
            }

            input.clear();
            gridPane.requestFocus();
        });

        rightPane.getChildren().addAll(playerInfo, frameBox, inputBox);
        rightPane.setRowIndex(playerInfo, 0);
        rightPane.setColumnIndex(playerInfo, 0);
        rightPane.setRowIndex(frameBox, 1);
        rightPane.setColumnIndex(frameBox, 0);
        rightPane.setRowIndex(inputBox, 2);
        rightPane.setColumnIndex(inputBox, 0);
        rightH.getChildren().add(rightPane);
        leftH.getChildren().addAll(gridPane,rightH);


        Scene scene = new Scene(leftH, 1920, 1080);
        curr_window.setFullScreen(true);
        curr_window.setResizable(false);
        Scrabble.setScene(scene);
        Scrabble.show();
    }

    void hasGameEnded(){
        if(gamePool.poolSize()==0 || endcounter>=6){
            if(playerOne.frame.checkEmptyFrame() || playerTwo.frame.checkEmptyFrame() || endcounter>=6){
                if(playerOne.frame.checkEmptyFrame()){
                    int tmp=0;
                    for(int i=0; i<playerTwo.frame.theFrameArray.size(); i++){
                        tmp+=playerTwo.frame.theFrameArray.get(i).getValue();
                    }
                    playerOne.setScore(2*tmp);
                }
                else if(playerTwo.frame.checkEmptyFrame()){
                    int tmp=0;
                    for(int i=0; i<playerOne.frame.theFrameArray.size(); i++){
                        tmp+=playerOne.frame.theFrameArray.get(i).getValue();
                    }
                    playerTwo.setScore(2*tmp);
                }
                else if(!playerTwo.frame.checkEmptyFrame() && !playerOne.frame.checkEmptyFrame()){
                    int tmp=0;
                    for(int i=0; i<playerOne.frame.theFrameArray.size(); i++){
                        tmp-=playerOne.frame.theFrameArray.get(i).getValue();
                    }
                    playerOne.setScore(tmp);
                    tmp=0;
                    for(int i=0; i<playerTwo.frame.theFrameArray.size(); i++){
                        tmp-=playerTwo.frame.theFrameArray.get(i).getValue();
                    }
                    playerTwo.setScore(tmp);
                }
                if(playerOne.getScore()>playerTwo.getScore()){
                    endGame(playerOne);
                }
                else if(playerTwo.getScore()>playerOne.getScore()){
                    endGame(playerTwo);
                }
                else {
                    endGame(null);
                }
            }
        }
    }
    


    public void changeCurrentPlayer()
    {
        turn++;

        if (turn % 2 == 0) {
            currentPlayer = playerOne;
        } else {
            currentPlayer = playerTwo;
        }

        currPlayer.setText("Current Player: " + currentPlayer.getName());
        currScore.setText("Current Score: " + currentPlayer.getScore());
        turnText.setText("Turn: " + (turn + 1));
        currentPlayer.frame.refillFrame(gamePool);
        updateFrame(currentPlayer.frame);
        updateBoard(gameBoard);
        hasGameEnded();
    }

    public void setUpDictionary() throws FileNotFoundException
    {
        InputStream txt = this.getClass().getResourceAsStream("sowpods.txt");
        Scanner scan = new Scanner(txt);

        ArrayList<String> data = new ArrayList<String>();

        while (scan.hasNextLine())
        {
            data.add(scan.nextLine());
        }

        dictionary = data.toArray(new String[]{});
    }

    void endGame(Player winner){

        if(winner!=null) {
            Alert gameOver = new Alert(Alert.AlertType.INFORMATION, winner.getName() + " has won, congratulations " + winner.getName() + "!", ButtonType.OK);
            gameOver.setTitle("Winner Winner Chicken Dinner!");
            gameOver.setHeaderText(null);
            gameOver.initOwner(curr_window);
            gameOver.showAndWait();

            if(gameOver.getResult()==ButtonType.OK){
                System.exit(0);
            }
        }
        if(winner==null){
            Alert gameOver= new Alert(Alert.AlertType.INFORMATION,"Both of you have a score of " + playerOne.getScore() + " so it's a draw!",ButtonType.OK);
            gameOver.setTitle("What are the chances?");
            gameOver.setHeaderText(null);
            gameOver.initOwner(curr_window);
            gameOver.showAndWait();

            if(gameOver.getResult()==ButtonType.OK){
                Platform.exit();
                System.exit(0);
            }
        }
    }

    private void getNames()
    {
        //Creating a dialog box
        //To enter both Player One's and Two's names
        //Displays before the game starts and the board is shown
        TextInputDialog nameInputBox1 = new TextInputDialog("");

        nameInputBox1.setHeaderText("Enter Player One's Name: ");
        nameInputBox1.setContentText("Name");
        nameInputBox1.setTitle("Player One Name");

        Optional<String> result1 = nameInputBox1.showAndWait();

        //the following either creates a player with a specified (or default name)
        //otherwise the program exits if cancel is typed
        if(result1.isPresent())
        {
            if(!(result1.get().equals("")))
            {
                result1.ifPresent(name ->
                {
                    playerOne = new Player(result1.get(),gamePool);
                });
            }
            else
            {
                if(result1.isPresent())
                {
                    result1.ifPresent(name ->
                            playerOne = new Player("Player One",gamePool));
                }
            }
        }
        else
        {
            Platform.exit();
            System.exit(0);
        }

        TextInputDialog nameInputBox2 = new TextInputDialog("");

        nameInputBox2.setHeaderText("Enter Player Two's Name: ");
        nameInputBox2.setContentText("Name");
        nameInputBox2.setTitle("Player Two Name");

        Optional<String> result2 = nameInputBox2.showAndWait();

        if(result2.isPresent())
        {
            if(!(result2.get().equals("")))
            {
                result2.ifPresent(name ->
                {
                    playerTwo = new Player(result2.get(),gamePool);
                });
            }
            else
            {
                if(result2.isPresent())
                {
                    result2.ifPresent(name ->
                    {
                        playerTwo = new Player("Player Two",gamePool);
                    });
                }
            }
        }
        else
        {
            Platform.exit();
            System.exit(0);
        }
    }
}