//Team: Camel Bois

import java.util.Scanner;
public class BoardTest
{
    //Basic Simulation of the Board
    //in which different combinations and actions
    //that can be taken in the game are tested
    //in order to showcase their full and
    //correct functionality
    public static void main(String[] args)
    {
        Board board = new Board();
        Pool pool = new Pool();
        Player p1 = new Player("Adam", pool);
        Player p2 = new Player("Carlo", pool);
        int row = 0;
        int col = 0;
        String sdir = "";
        char dir = ' ';
        String word = "";

        //placeWord TEST
        System.out.println("placeWord TEST\n\n");
        Scanner in = new Scanner(System.in);
        int x = 0;
        System.out.println("In the following, you can keep playing and placing words, or quit the test.");

        do {
            do {
                System.out.println("Press 1 to keep playing the game, press 2 to quit the test : ");
                x = in.nextInt();
            } while (x != 1 && x != 2);

            if (x == 1) {
                System.out.println("This is player 1's current frame of tiles : ");
                System.out.println(p1.toString());
                System.out.println("Enter row for first word, and after pressing return, enter the column : ");
                row = in.nextInt();
                col = in.nextInt();
                System.out.println("Enter A (or a) or D (or d) to indicate which direction you want your word to go : ");
                sdir = in.next();
                dir = sdir.charAt(0);
                System.out.println("Enter the word you want to place : ");
                word = in.next();
                board.placeWord(row, col, dir, word, p1);
                p1.frame.refillFrame(pool);
                System.out.println(board.toString());

                System.out.println("This is player 2's current frame of tiles : ");
                System.out.println(p2.toString());
                System.out.println("Enter row for first word, and after pressing return, enter the column : ");
                row = in.nextInt();
                col = in.nextInt();
                System.out.println("Enter A (or a) or D (or d) to indicate which direction you want your word to go : ");
                sdir = in.next();
                dir = sdir.charAt(0);
                System.out.println("Enter the word you want to place : ");
                word = in.next();
                board.placeWord(row, col, dir, word, p2);
                p2.frame.refillFrame(pool);
                System.out.println(board.toString());
            }
        } while (x == 1 && (pool.poolEmpty() == false));
    }

}
