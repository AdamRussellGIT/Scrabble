//Team: Camel-Bois
//Members: Adam Russell - 18328861
//         Carlo Motteran - 18717341
//         Karol Wojcik - 18322146

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Bot0 implements BotAPI {

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the game objects
    // It may only inspect the state of the board and the player objects

    private PlayerAPI me;
    private OpponentAPI opponent;
    private BoardAPI board;
    private UserInterfaceAPI info;
    private DictionaryAPI dictionary;
    private int turnCount;
    private String word;
    private String possiblePlacement;
    private ArrayList<String> possibleWords;
    private String command;
    private boolean firstTurn = true;

    Bot0(PlayerAPI me, OpponentAPI opponent, BoardAPI board, UserInterfaceAPI ui, DictionaryAPI dictionary) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.info = ui;
        this.dictionary = dictionary;
        turnCount = 0;
    }

    public String getCommand() {

        if (firstTurn)
        {
            Node root = new Node();
            try
            {
                File f = new File("csw.txt");
                Scanner in = new Scanner(f);

                while (in.hasNextLine()) {
                    String word = in.nextLine();
                    Node currentNode = root;
                    for (int i=0; i<word.length(); i++) {
                        char currentLetter = word.charAt(i);
                        if (currentNode.isChild(currentLetter)) {
                            currentNode = currentNode.getChild(currentLetter);
                        }
                        else {
                            currentNode = currentNode.addChild(currentLetter);
                        }
                    }
                    currentNode.setEndOfWord();
                }
                in.close();
            }
            catch(FileNotFoundException f)
            {
                System.out.println("Error!");
            }
        }

        //find all possible word combinations that could be placed on this turn
        possibleWords = new ArrayList<>();
        possibleWords = findPossibleWords();

        //find best possible play from possiblePlays and return it
        command = bestPossiblePlay(possibleWords);

        return command;
    }

    private String bestPossiblePlay(ArrayList<String> possibleWords)
    {
        String bestPlay = "";

        return bestPlay;
    }

    private ArrayList<String> findPossibleWords()
    {
//        word = "";
//        possiblePlacement = "";
//
//        //go through the board
//        for (int i = 0; i < 15; i++)
//        {
//            for (int j = 0; j < 15; j++)
//            {
//                //if we find an occupied square
//                if (board.getSquareCopy(i, j).isOccupied())
//                {
//                    //if square to left has tile, already added, break
//                    if ((board.getSquareCopy(i-1, j).isOccupied()) && (board.getSquareCopy(i, j-1).isOccupied()))
//                    {
//                        break;
//                    }
//
//                    //only need to check down
//                    else if (board.getSquareCopy(i-1, j).isOccupied())
//                    {
//                        /*
//                        FIND THE PREFIX/SUFFIX OF THE CURRENT LETTER TO MAKE A PROPER WORD (IF THERE IS ONE)
//                            SO WE CAN ADD IT TO THE END OF EACH WORD?
//                        */
//                        int findPreSuf = i;
//                        String preSuf = "";
//
//                        //find all letters currently on the board
//                        while (board.getSquareCopy(findPreSuf, j).isOccupied())
//                        {
//                            preSuf = preSuf.concat(String.valueOf(board.getSquareCopy(findPreSuf, j).getTile().getLetter()));
//
//                            findPreSuf++;
//                        }
//
//                        //need to make sure we find all possible words on either side of starting letter
//                        //e.g. *******help and help**** and everything in between
//                        for (int countUp = 0; countUp <= 7; countUp++)
//                        {
//                            /*
//                            MAKE THIS i-1 TO NOT RE ADD LETTER
//                             */
//                            int findTop = i - 1;
//
//                            //make word start as the prefix/sufix, and add either a * or a letter to the beginning each time as we go up
//                            word = preSuf;
//
//                            //find top most index
//                            //either be top of the board, or countUp = 7, which will help us move down the board finding all possible word placements
//                            while (findTop >= 0 || word.length() <= 7)
//                            {
//                                //if empty, could be any letter
//                                if (!board.getSquareCopy(findTop, j).isOccupied())
//                                {
//                                    word = "*".concat(word);
//                                }
//
//                                //if not empty, add letter found to word
//                                else
//                                {
//                                    //TODO
//                                    /*
//                                    ADD AS LOWER CASE FOR FUTURE PERMUTING?
//                                     */
//                                    word = String.valueOf(board.getSquareCopy(findTop, j).getTile().getLetter()).concat(word);
//                                }
//
//                                //create possible placement String and add to possible Words that could be created
//                                possiblePlacement += (String.valueOf(findTop + (int) 'A') + String.valueOf(j) + " D " + word);
//                                possibleWords.add(possiblePlacement);
//                                possiblePlacement = "";
//
//                                //checks to find the end
//                                countUp++;
//                                findTop--;
//                            }
//
//                            //findBottom of how far we can go
//                            int findBottom = i + preSuf.length();
//                            //reset word to be just the prefix/suffix
//                            word = preSuf;
//
//                            //find either end of board or first blank square
//                            while (findBottom < 15 || word.length() <= 7)
//                            {
//                                //if empty, could be any letter
//                                if (!board.getSquareCopy(findBottom, j).isOccupied())
//                                {
//                                    word = word.concat("*");
//                                }
//
//                                //if not empty, add letter found to word
//                                else
//                                {
//                                    //TODO
//                                    /*
//                                    ADD AS LOWER CASE FOR FUTURE PERMUTING?
//                                     */
//                                    word = word.concat(String.valueOf(board.getSquareCopy(findTop, j).getTile().getLetter()));
//                                }
//                                findBottom++;
//                            }
//
//                            //create possible placement String and add to possible Words that could be created
//                            possiblePlacement += (String.valueOf(findBottom + (int) 'A') + String.valueOf(j) + " A " + word);
//                            possibleWords.add(possiblePlacement);
//                            possiblePlacement = "";
//                        }
//                    }
//
//                    //only need to check across
//                    else if (board.getSquareCopy(i, j-1).isOccupied())
//                    {
//                        //counter to make sure we go all the way across
//                        //we need to find all possible values both on the left and right of our starting letter
//                        for (int countUp = 0; countUp <= 7; countUp++)
//                        {
//                            int findLeft = j;
//
//                            //find left most index
//                            while (findLeft >= 0 || countUp <= 7)
//                            {
//                                //if empty, could be any letter
//                                if (!board.getSquareCopy(i, findLeft).isOccupied())
//                                {
//                                    word = "*".concat(word);
//                                }
//
//                                //if not empty, add letter found to word
//                                else
//                                {
//                                    word = String.valueOf(board.getSquareCopy(i, findLeft).getTile().getLetter()).concat(word);
//                                }
//
//                                //checks to find the end
//                                countUp++;
//                                findLeft--;
//                            }
//
//                            //findRight of how far we can go
//                            int findRight = j;
//
//                            //find either end of board or first blank square
//                            while (board.getSquareCopy(i, findRight).isOccupied() || findRight < 15)
//                            {
//                                //add on letter
//                                word = word.concat(String.valueOf(board.getSquareCopy(i, findRight).getTile().getLetter()));
//
//                                findRight++;
//                            }
//
//                            //create possible placement String and add to possible Words that could be created
//                            possiblePlacement += (String.valueOf(i + (int) 'A') + String.valueOf(findLeft) + " A " + word);
//                            possibleWords.add(possiblePlacement);
//                            possiblePlacement = "";
//                        }
//                    }
//
//                    else
//                    {
//                        //need to make sure we find all possible words on either side of starting letter
//                        //e.g. *******help and help**** and everything in between
//                        for (int countUp = 0; countUp <= 7; countUp++)
//                        {
//                            int findTop = i;
//
//                            //find top most index
//                            //either be top of the board, or countUp = 7, which will help us move down the board finding all possible word placements
//                            while (findTop >= 0 || countUp <= 7)
//                            {
//                                //if empty, could be any letter
//                                if (!board.getSquareCopy(findTop, j).isOccupied())
//                                {
//                                    word = "*".concat(word);
//                                }
//
//                                //if not empty, add letter found to word
//                                else
//                                {
//                                    word = String.valueOf(board.getSquareCopy(findTop, j).getTile().getLetter()).concat(word);
//                                }
//
//                                //checks to find the end
//                                countUp++;
//                                findTop--;
//                            }
//
//                            //findBottom of how far we can go
//                            int findBottom = i;
//
//                            //find either end of board or first blank square
//                            while (board.getSquareCopy(findBottom, j).isOccupied() || findBottom < 15)
//                            {
//                                //add on letter
//                                word = word.concat(String.valueOf(board.getSquareCopy(findBottom, j).getTile().getLetter()));
//
//                                findBottom++;
//                            }
//
//                            //create possible placement String and add to possible Words that could be created
//                            possiblePlacement += (String.valueOf(findTop + (int) 'A') + String.valueOf(j) + " A " + word);
//                            possibleWords.add(possiblePlacement);
//                            possiblePlacement = "";
//                        }
//
//                        //counter to make sure we go all the way across
//                        //we need to find all possible values both on the left and right of our starting letter
//                        for (int countUp = 0; countUp <= 7; countUp++)
//                        {
//                            int findLeft = j;
//
//                            //find left most index
//                            while (findLeft >= 0 || countUp <= 7)
//                            {
//                                //if empty, could be any letter
//                                if (!board.getSquareCopy(i, findLeft).isOccupied())
//                                {
//                                    word = "*".concat(word);
//                                }
//
//                                //if not empty, add letter found to word
//                                else
//                                {
//                                    word = String.valueOf(board.getSquareCopy(i, findLeft).getTile().getLetter()).concat(word);
//                                }
//
//                                //checks to find the end
//                                countUp++;
//                                findLeft--;
//                            }
//
//                            //findRight of how far we can go
//                            int findRight = j;
//
//                            //find either end of board or first blank square
//                            while (board.getSquareCopy(i, findRight).isOccupied() || findRight < 15)
//                            {
//                                //add on letter
//                                word = word.concat(String.valueOf(board.getSquareCopy(i, findRight).getTile().getLetter()));
//
//                                findRight++;
//                            }
//
//                            //create possible placement String and add to possible Words that could be created
//                            possiblePlacement += (String.valueOf(i + (int) 'A') + String.valueOf(findLeft) + " A " + word);
//                            possibleWords.add(possiblePlacement);
//                            possiblePlacement = "";
//                        }
//                    }
//                }
//            }
//        }
//
//        possibleWords.add("Hello");
//        for (String words: possibleWords)
//        {
//            System.out.println(words);
//        }
//
//        return possibleWords;

        return null;
    }
}
