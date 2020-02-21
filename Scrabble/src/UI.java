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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Scanner;

public class UI extends Application
{
        public String getInput()
        {
            Scanner in = new Scanner(System.in);

            String input = in.nextLine();
            return input.toUpperCase();
        }

        public Boolean checkChoice(String input)
        {
            input.toUpperCase();

            if(input == "QUIT" ||input == "PASS" || input == "CHALLENGE" ||input == "HELP" || input == "EXCHANGE" || input == "PLACEWORD")
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        Stage curr_window;


    @Override
    public void start(Stage Scrabble)
    {
        curr_window = Scrabble;
        curr_window.setTitle("Scrabble");
        StackPane the_layout = new StackPane();

        Scene scene = new Scene(the_layout, 500, 400);

        Scrabble.setScene(scene);
        Scrabble.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}