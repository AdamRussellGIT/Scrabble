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
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import javafx.scene.image.Image;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UI extends Application
{
    Stage curr_window;
    GridPane gridPane;
    GridPane framePane;
    Button my_button;

    TextField input;

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

    @Override
    public void start(Stage Scrabble) throws FileNotFoundException {
        curr_window = Scrabble;
        curr_window.setTitle("Scrabble");

        gridPane = new GridPane();
        gridPane.setHgap(7);
        gridPane.setVgap(7);

        HBox leftH = new HBox(8);
        HBox rightH = new HBox(8);

        VBox gameInfo = new VBox(380);

        VBox playerInfo = new VBox(8);

        Label currPlayer = new Label("Current Player: ");
        Label currScore = new Label("Current Score: ");

        playerInfo.getChildren().addAll(currPlayer,currScore);

        VBox frameBox = new VBox(8);

        framePane = new GridPane();
        gridPane.setHgap(7);
        gridPane.setVgap(7);

        VBox inputBox = new VBox(8);

        //setting up textfield
        input = new TextField();

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
                butt.setPrefSize(55, 55);
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

        //gathering input from user
        input.setOnAction(e -> {
            String receivedInput = input.getText().toUpperCase();
            String[] parsedInput = receivedInput.split(" ");

            //parsing input
            if (parsedInput[0].equals("EXCHANGE"))
            {
                //exchange method call etc
            }

            else if (parsedInput[0].equals("PASS"))
            {
                turn++;
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
        });
        rightH.getChildren().add(gameInfo);
        leftH.getChildren().addAll(gridPane,rightH);


        Scene scene = new Scene(leftH, 1920, 1080);
        curr_window.setMaximized(true);
        curr_window.setResizable(false);
        Scrabble.setScene(scene);
        Scrabble.show();
    }

    public void changeCurrentPlayer()
    {
        if (turn % 2 == 0) {
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }
    }
}