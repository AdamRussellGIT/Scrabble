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
import java.util.Scanner;

public class UI extends Application
{
    Stage curr_window;
    Button my_button;

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
        //Images for background
//        Image board = new Image(new FileInputStream("C:\\Users\\karol\\IdeaProjects\\Camel-bois\\Scrabble\\res\\scrabbleBoard.png"));
//        Image frame = new Image(new FileInputStream("C:\\Users\\karol\\IdeaProjects\\Camel-bois\\Scrabble\\res\\scrabbleFrame.png"));
//
//        ImageView imageView = new ImageView(board);
//        ImageView imageView2 = new ImageView(frame);
//
//
//        imageView.setY(50);
//        imageView.setX(20);
//
//        imageView2.setY(520);
//        imageView2.setX(25);
//
//        imageView.setFitHeight(455);
//        imageView.setFitWidth(500);
//
//        imageView2.setFitHeight(760);
//        imageView2.setFitWidth(200);
//
//        imageView.setPreserveRatio(true);
//        imageView2.setPreserveRatio(true);
//
//        Group root = new Group(imageView,imageView2);

        GridPane gridPane = new GridPane();

        curr_window = Scrabble;
        curr_window.setTitle("Scrabble");

        gridPane.setHgap(7);
        gridPane.setVgap(7);

        //Centre tile image set-up
        ImageView imgV;
        Image starImg = new Image(new FileInputStream("C:\\Users\\karol\\IdeaProjects\\Camel-bois\\Scrabble\\res\\star.jpg"));
        imgV = new ImageView(starImg);

        for (int i = 0; i < 15; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                Button butt = new Button("(" + i + "," + j + ")");
                butt.setPrefSize(55,55);
                butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                butt.setStyle("-fx-background-color:#c8c2a8");
                gridPane.add(butt, j, i, 1, 1);

                //triple word score
                if((i == 0 && j==0)||(i == 0 && j==7)||(i == 0 && j==14)||(i == 7 && j==0)||(i == 7 && j==14)||(i == 14 && j==0)||(i == 14 && j==7)||(i == 14 && j==14))
                {
                    butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                    butt.setStyle("-fx-background-color:#f35f49");
                }
                //double word score
                if((i == 1 && j== 1)||(i == 2 && j== 2)||(i == 3 && j== 3)||(i == 4 && j== 4)||(i == 10 && j== 4)||(i == 11 && j== 3)||(i == 12 && j== 2)||(i == 13 && j== 1)||(i == 1 && j== 13)||(i == 2 && j== 12)||(i == 3 && j== 11)||(i == 4 && j== 10)||(i == 13 && j== 13)||(i == 12 && j== 12)||(i == 11 && j== 11)||(i == 10 && j== 10))
                {
                    butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                    butt.setStyle("-fx-background-color:#f6b9ab");
                }
                //triple letter score
                if((i == 1 && j== 5)||(i == 1 && j== 9)||(i == 5 && j== 1)||(i == 5 && j== 5)||(i == 5 && j== 9)||(i == 5 && j== 13)||(i == 9 && j== 1)||(i == 9 && j== 5)||(i == 9 && j== 9)||(i == 9 && j== 13)||(i == 13 && j== 5)||(i == 13 && j== 9))
                {
                    butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                    butt.setStyle("-fx-background-color:#3d9eb2");
                }
                //double letter score
                if((i == 0 && j== 3)||(i == 0 && j== 11)||(i == 2 && j== 6)||(i == 2 && j== 8)||(i == 3 && j== 7)||(i == 3 && j== 0)||(i == 3 && j== 14)||(i == 6 && j== 2)||(i == 6 && j== 6)||(i == 6 && j== 8)||(i == 6 && j== 12)||(i == 7 && j== 3)||(i == 7 && j== 11)||(i == 8 && j== 2)||(i == 8 && j== 6)||(i == 8 && j== 8)||(i == 8 && j== 12)||(i == 11 && j== 0)||(i == 11 && j== 7)||(i == 11 && j== 14)||(i == 12 && j== 6)||(i == 12 && j== 8)||(i == 14 && j== 3)||(i == 14 && j== 11))
                {
                    butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                    butt.setStyle("-fx-background-color:#b9d3d0");
                }
                //centre tile
                if((i == 7 && j== 7) )
                {
                    butt.setText("");
                    butt.setStyle("-fx-border-color: #fdf4ff; -fx-border-width: 2px");
                    butt.setStyle("-fx-background-color:#f6b9ab");

                    butt.setGraphic(new ImageView(starImg));
                }

                //example placement
                if((i == 0 && j == 0))
                {
                    butt.setText("A");
                    butt.setStyle("-fx-font-weight: bold");
                }
            }
        }

        Scene scene = new Scene(gridPane, 1230, 800);

        Scrabble.setScene(scene);
        Scrabble.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}