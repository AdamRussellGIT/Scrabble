//Team: Camel Bois

import java.util.Scanner;
public class boardTest
{
    public static void main(String[] args)
    {
        Board board = new Board();
        Pool pool = new Pool();
        Player p1 = new Player("Adam", pool);
        Player p2 = new Player("Carlo", pool);

        //placeWord TEST
        System.out.println("placeWord TEST");
        System.out.println(p1.toString());
        Scanner in = new Scanner(System.in);
        String word = in.nextLine();
        board.placeWord(7,7,'D', word, p1);
        p1.frame.refillFrame(pool);
        System.out.println(board.toString());
        System.out.println(p1.toString());
        word = in.nextLine();
        board.placeWord(7, 8, 'A', word, p1);
        p1.frame.refillFrame(pool);
        System.out.println(board.toString());

        //resetBoard TEST
        System.out.println("resetBoard TEST");

        try
        {
            board.placeWord(0,0,' ',"", null);
            board.placeWord(0,0,' ',"", null);
            board.placeWord(0,0,' ',"", null);
            board.placeWord(0,0,' ',"", null);

            board.resetBoard();
        }
        catch(RuntimeException r)
        {
            throw new RuntimeException("ERROR inside resetBoard");
        }

        //wordPlacementCheck TEST
        System.out.println("wordPlacementCheck TEST");

        try
        {
            //if first word whether it's in the centre of the board
            board.placeWord(0,0,' ',"", null);
            //Check if the tile is out of bounds
            board.placeWord(0,0,' ',"", null);
            //Check if there is an existing tile already in the position
            board.placeWord(0,0,' ',"", null);
            //if not first word if it's connected to another word on the board
            board.placeWord(0,0,' ',"", null);
            //if the player has the necessary letters
            board.placeWord(0,0,' ',"", null);
            //if the word conflicts with existing letters
            board.placeWord(0,0,' ',"", null);
            //if placement uses at least one letter from rack
            board.placeWord(0,0,' ',"", null);
        }
        catch(RuntimeException r)
        {
            throw new RuntimeException("ERROR inside wordPlacementCheck");
        }

        //Board class TEST
        System.out.println("Board class TEST");

        try
        {

        }
        catch(RuntimeException r)
        {
            throw new RuntimeException("ERROR inside Board class");
        }

        //toString TEST
        System.out.println("toString TEST");

        try
        {

        }
        catch(RuntimeException r)
        {
            throw new RuntimeException("ERROR inside toString");
        }



    }

}
