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
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import javafx.scene.image.Image;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class UI extends Application
{
    Stage curr_window;
    GridPane gridPane;
    GridPane framePane;
    GridPane rightPane;
    Button my_button;

    TextField input;
    Label currPlayer;
    Label currScore;

    Player playerOne;
    Player playerTwo;
    Pool gamePool;
    Board gameBoard;
    UI gameUI;

    int turn = -1;
    Player currentPlayer;
    String choice;

    int previousScore = 0;
    Board previousBoard = new Board();

    ImageView imgV;
    Image starImg = new Image(new FileInputStream("Scrabble\\res\\star.jpg"));

    public UI() throws FileNotFoundException {
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

                else {
                    imgV = new ImageView(starImg);
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
                        butt.setText("");
                        butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                        butt.setStyle("-fx-background-color:#f6b9ab");

                        butt.setGraphic(new ImageView(starImg));
                    }
                }
            }
        }
    }

    public void updateFrame(Frame f)
    {
        for (int i = 0; i < 7; i++)
        {
            if (f.theFrameArray.get(i) != null)
            {
                Button frameButt = new Button(String.valueOf(f.theFrameArray.get(i).getLetter()));
                frameButt.setPrefSize(92, 92f);
                frameButt.setPrefHeight(130);

                frameButt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                frameButt.setStyle("-fx-background-color:#c8c2a8");

                framePane.add(frameButt, i, 0);
            }
            else
            {
                Button emptyButt = new Button("");
                emptyButt.setVisible(false);
            }

        }
    }

    @Override
    public void start(Stage Scrabble) throws FileNotFoundException
    {
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
        rightPane.setVgap(250);
        rightPane.setHgap(100);


        VBox gameInfo = new VBox(415);

        VBox playerInfo = new VBox(8);

        currPlayer = new Label("Current Player: ");
        currPlayer.setFont(new Font(30));

        currScore = new Label("Current Score: ");
        currScore.setFont(new Font(30));



        playerInfo.getChildren().addAll(currPlayer,currScore);

        VBox frameBox = new VBox(8);

        framePane = new GridPane();
        framePane.setHgap(11);
        framePane.setPadding(new Insets(0, 0, 0, 15));
        frameBox.getChildren().add(framePane);


        VBox inputBox = new VBox(8);

        //setting up textfield
        input = new TextField();
        input.setPrefHeight(50);
        input.setPrefWidth(400);
        input.setFont(new Font(30));

        inputBox.getChildren().add(input);


        gameInfo.getChildren().addAll(playerInfo,frameBox,inputBox);



        //Centre tile image set-up
        ImageView imgV;
        Image starImg = new Image(new FileInputStream("Scrabble\\res\\star.jpg"));
        imgV = new ImageView(starImg);

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
                    butt.setText("");
                    butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                    butt.setStyle("-fx-background-color:#f6b9ab");

                    butt.setGraphic(new ImageView(starImg));
                }
            }
        }

        //TODO Check for cancel and null
        //Creating a dialog box
        //To enter both Player One's and Two's names
        //Displays before the game starts and the board is shown
        TextInputDialog nameInputBox1 = new TextInputDialog("");

        nameInputBox1.setHeaderText("Enter Player One's Name: ");
        nameInputBox1.setContentText("Name");

        Optional<String> result1 = nameInputBox1.showAndWait();

//
//        result1.ifPresent(name ->
//        {
//            playerOne = new Player(result1.get(),gamePool);
//        });
//
//        if(result1.isPresent())
//        {
//            ;
//        }
//        else if(result1 == null || result1.isEmpty())
//        {
//
//        }
//        else
//        {
//            Scrabble.setOnCloseRequest(e ->
//            {
//                Platform.exit();
//                System.exit(0);
//            });
//        }

        TextInputDialog nameInputBox2 = new TextInputDialog("");

        nameInputBox2.setHeaderText("Enter Player Two's Name: ");
        nameInputBox2.setContentText("Name");

        Optional<String> result2 = nameInputBox2.showAndWait();

        result2.ifPresent(name ->
        {
            playerTwo = new Player(result2.get(),gamePool);
        });

        if(result2.isPresent())
        {
            ;
        }
        else if(result2 == null || result1.isEmpty())
        {

        }
        else
        {
            Scrabble.setOnCloseRequest(e ->
            {
                Platform.exit();
                System.exit(0);
            });
        }

        changeCurrentPlayer();

        //gathering input from user
        input.setOnAction(e -> {
            System.out.println(currentPlayer.getName());
            String receivedInput = input.getText().toUpperCase();
            String[] parsedInput = receivedInput.split(" ");

            //parsing input
            if (parsedInput[0].equals("EXCHANGE"))
            {
                //exchange method call etc
            }

            else if (parsedInput[0].equals("PASS"))
            {
                changeCurrentPlayer();
            }

            else if (parsedInput.equals("HELP"))
            {
                //show help dialog box?
            }

            else if (parsedInput[0].equals("QUIT"))
            {
                //show dialog box asking if sure and if sure then close etc?
            }

            //if none of the above, assume player is trying to placeword
            else
            {
                //check if there are 4 things in the array (row, col, direction, word)
                if (parsedInput.length != 4)
                {
                    //tell the user that its invalid through text above textfield maybe?
                }

                //parse input to row column etc
                else
                {
                    int row = Integer.parseInt(parsedInput[0]);
                    int column = Integer.parseInt(parsedInput[1]);
                    char direction = parsedInput[2].charAt(0);
                    String word = parsedInput[3];

                    //run checks and placeword etc otherwise throw error yada yada ;)
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
        updateFrame(currentPlayer.frame);
    }
}