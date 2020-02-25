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
    public void start(Stage Scrabble)
    {
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


        for (int i = 0; i < 15; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                Button butt = new Button("(" + i + "," + j + ")");
                butt.setPrefSize(55,55);
                gridPane.add(butt, j, i, 1, 1);
            }
        }

        Scene scene = new Scene(gridPane, 900, 800);

        Scrabble.setScene(scene);
        Scrabble.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}