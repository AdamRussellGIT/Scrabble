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
    private String command;
    private boolean firstTurn = true;
    private Node root;

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
            root = createTrie();
        }

        //find anchor squares
        for (int i = 0; i < 15; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                if (isAnchorSquare(i, j))
                {
                    ArrayList<String> prefixList = new ArrayList<>();
                    findPrefix(i, j, root, prefixList);
                }
            }
        }

        return command;
    }

    private void findPrefix(int row, int column, Node root, ArrayList<String prefixList>)
    {
        char currLetter = frame.romve(0);

        if (corssCheck(row, column, root, currLetter) && root.isChild(currLetter))
        {
            prefixList.add(currLetter);

            root = root.getChild(currLetter);
            ArrayList<Tile> tmp = new ArrayList<>();
            for (int j = 0; j < frame.size()-1; j++)
            {
                tmp.add(me.frame.get(j));
            }
            //worry about column-1
            findPrefix(row, column-1, root);

            for (String prefix : prefixList)
            {
                prefix = currLetter.concat(prefix);
            }
        }
    }

    private void findSuffix()
    {

    }

    private boolean isAnchorSquare(int row, int column)
    {
        //is current square occupied
        if (board.getSquareCopy(row, column).isOccupied())
        {
            return true;
        }

        //is square above occupied
        else if ((row >= 1) && (board.getSquareCopy(row-1, column).isOccupied()))
        {
            return true;
        }

        //is square below occupied
        else if ((row <= 14) && (board.getSquareCopy(row+1, column).isOccupied()))
        {
            return true;
        }

        //is square to left occupied
        else if ((column >= 1) && (board.getSquareCopy(row, column-1).isOccupied()))
        {
            return true;
        }

        //is square to right occupied
        else if ((column <= 14) && (board.getSquareCopy(row, column+1).isOccupied()))
        {
            return true;
        }

        //square is not an anchor sqaure
        else
        {
            return false;
        }
    }

    private boolean crossCheck(int row, int column, char letter, Node root)
    {
        //if there is no letter above a given board position AND there is no letter beneath a given board position, cross check is true
        //  because there are no new words being created vertically at this position
        if ((row >= 1) && (!board.getSquareCopy(row-1, column).isOccupied()) && (row <= 14) && (!board.getSquareCopy(row+1, column).isOccupied()))
        {
            return true;
        }

        else
        {
            //word we will need to check our lexicon for
            String wordCheck = String.valueOf(letter);

            //used to create word
            //set to row-1 to prevent duplicating letter on current row already added to wordCheck
            int findRow = row-1;

            //add all letters already on board before starting position to start of word
            while ((findRow >= 0) && (board.getSquareCopy(findRow, column).isOccupied()))
            {
                wordCheck = String.valueOf(board.getSquareCopy(findRow, column).getTile().getLetter()).concat(wordCheck);
                findRow--;
            }

            //set to row+1 to prevent duplicating letter on current row already added to wordCheck
            findRow = row+1;

            //add all letters already on board after starting position to end of word
            while ((findRow <= 14) && (board.getSquareCopy(findRow, column).isOccupied()))
            {
                wordCheck = wordCheck.concat(String.valueOf(board.getSquareCopy(findRow, column).getTile().getLetter()));
                findRow++;
            }

            //check trie to see if word is there with given letter
            Node currentNode = root;
            for (int i=0; i < wordCheck.length(); i++)
            {
                char currentLetter = wordCheck.charAt(i);

                if (currentNode.isChild(currentLetter))
                {
                    currentNode = currentNode.getChild(currentLetter);
                }

                else
                {
                    return false;
                }
            }

            if (!currentNode.isEndOfWord())
            {
                return false;
            }

            return true;
        }
    }

    private Node createTrie()
    {
        try
        {
            Node root = new Node();
            File inputFile = new File("csw.txt");
            Scanner in = new Scanner(inputFile);
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

            return root;
        }

        catch(Exception e)
        {
            System.out.println("Error creating trie in bot class!");

            return null;
        }
    }

//    private ArrayList<String> findPossibleWords()
//    {
//        word = "";
//        possiblePlacement = "";
//        int countUp;
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
//                    if ((board.getSquareCopy(i - 1, j).isOccupied() || i-1 < 0) && (board.getSquareCopy(i, j - 1).isOccupied() || j-1 < 0))
//                    {
//                        break;
//                    }
//
//                    //only need to check down
//                    else if ((i-1 >= 0) && board.getSquareCopy(i-1, j).isOccupied())
//                    {
//                        /*
//                        MAKE THIS i-1 TO NOT RE ADD LETTER
//                        */
//                        int findTop = i - 1;
//
//                        countUp = 0;
//
//                        //find top most index
//                        //either be top of the board, or countUp = 7, which will help us move down the board finding all possible word placements
//                        while (findTop >= 0 && countUp < 7)
//                        {
//                            //if empty, could be any letter
//                            if (!board.getSquareCopy(findTop, j).isOccupied()) {
//                                word = "*".concat(word);
//                            }
//
//                            //if not empty, add letter found to word
//                            else {
//                                //TODO
//                                /*
//                                   ADD AS LOWER CASE FOR FUTURE PERMUTING?
//                                */
//                                word = String.valueOf(board.getSquareCopy(findTop, j).getTile().getLetter()).concat(word);
//                            }
//
//                            //checks to find the top/start
//                            countUp++;
//                            findTop--;
//                        }
//
//                        //find bottom of word
//                        countUp = 0;
//                        int findBottom = i;
//
//                        while (findBottom <= 14 && countUp < 7)
//                        {
//                            //if empty, could be any letter
//                            if (!board.getSquareCopy(findBottom, j).isOccupied()) {
//                                word = word.concat("*");
//                            }
//
//                            //if not empty, add letter found to word
//                            else {
//                                //TODO
//                                /*
//                                   ADD AS LOWER CASE FOR FUTURE PERMUTING?
//                                */
//                                word = word.concat(String.valueOf(board.getSquareCopy(findTop, j).getTile().getLetter()));
//                            }
//
//                            //checks to find the top/start
//                            countUp++;
//                            findBottom++;
//                        }
//
//                        //prevent findTop from being -1 when checking near top
//                        int colin = findTop + 66;
//                        char colch = (char) colin;
//                        possiblePlacement += (String.valueOf(colch) + String.valueOf(j) + " D " + word);
//                        possibleWords.add(possiblePlacement);
//                        possiblePlacement = "";
//                        word = "";
//                    }
//
//                    //only need to check across
//                    else if ((j-1 >= 0) && board.getSquareCopy(i, j-1).isOccupied())
//                    {
//                        /*
//                        MAKE THIS i-1 TO NOT RE ADD LETTER
//                        */
//                        int findLeft = j - 1;
//
//                        countUp = 0;
//
//                        //find top most index
//                        //either be top of the board, or countUp = 7, which will help us move down the board finding all possible word placements
//                        while (findLeft >= 0 && countUp < 7)
//                        {
//                            //if empty, could be any letter
//                            if (!board.getSquareCopy(i, findLeft).isOccupied()) {
//                                word = "*".concat(word);
//                            }
//
//                            //if not empty, add letter found to word
//                            else {
//                                //TODO
//                                /*
//                                   ADD AS LOWER CASE FOR FUTURE PERMUTING?
//                                */
//                                word = String.valueOf(board.getSquareCopy(i, findLeft).getTile().getLetter()).concat(word);
//                            }
//
//                            //checks to find the top/start
//                            countUp++;
//                            findLeft--;
//                        }
//
//                        //find bottom of word
//                        countUp = 0;
//                        int findRight = j;
//
//                        while (findRight <= 14 && countUp < 7)
//                        {
//                            //if empty, could be any letter
//                            if (!board.getSquareCopy(i, findRight).isOccupied()) {
//                                word = word.concat("*");
//                            }
//
//                            //if not empty, add letter found to word
//                            else {
//                                //TODO
//                                /*
//                                   ADD AS LOWER CASE FOR FUTURE PERMUTING?
//                                */
//                                word = word.concat(String.valueOf(board.getSquareCopy(i, findRight).getTile().getLetter()));
//                            }
//
//                            //checks to find the top/start
//                            countUp++;
//                            findRight++;
//                        }
//
//                        //prevent findTop from being -1 when checking near top
//                        int colin = findLeft + 66;
//                        char colch = (char) colin;
//                        possiblePlacement += (String.valueOf(colch) + String.valueOf(j) + " A " + word);
//                        possibleWords.add(possiblePlacement);
//                        possiblePlacement = "";
//                        word = "";
//                    }
//
//                    //need to do both down AND across
//                    else
//                    {
//                        /*
//                        MAKE THIS i-1 TO NOT RE ADD LETTER
//                        */
//                        int findTop = i - 1;
//
//                        countUp = 0;
//
//                        //find top most index
//                        //either be top of the board, or countUp = 7, which will help us move down the board finding all possible word placements
//                        while (findTop >= 0 && countUp < 7)
//                        {
//                            //if empty, could be any letter
//                            if (!board.getSquareCopy(findTop, j).isOccupied()) {
//                                word = "*".concat(word);
//                            }
//
//                            //if not empty, add letter found to word
//                            else {
//                                //TODO
//                                /*
//                                   ADD AS LOWER CASE FOR FUTURE PERMUTING?
//                                */
//                                word = String.valueOf(board.getSquareCopy(findTop, j).getTile().getLetter()).concat(word);
//                            }
//
//                            //checks to find the top/start
//                            countUp++;
//                            findTop--;
//                        }
//
//                        //find bottom of word
//                        countUp = 0;
//                        int findBottom = i;
//
//                        while (findBottom <= 14 && countUp < 7)
//                        {
//                            //if empty, could be any letter
//                            if (!board.getSquareCopy(findBottom, j).isOccupied()) {
//                                word = word.concat("*");
//                            }
//
//                            //if not empty, add letter found to word
//                            else {
//                                //TODO
//                                /*
//                                   ADD AS LOWER CASE FOR FUTURE PERMUTING?
//                                */
//                                word = word.concat(String.valueOf(board.getSquareCopy(findTop, j).getTile().getLetter()));
//                            }
//
//                            //checks to find the top/start
//                            countUp++;
//                            findBottom++;
//                        }
//
//                        //prevent findTop from being -1 when checking near top
//                        int colin = findTop + 66;
//                        char colch = (char) colin;
//                        possiblePlacement += (String.valueOf(colch) + String.valueOf(j) + " D " + word);
//                        possibleWords.add(possiblePlacement);
//                        possiblePlacement = "";
//                        word = "";
//
//                        //going across
//                        /*
//                        MAKE THIS i-1 TO NOT RE ADD LETTER
//                        */
//                        int findLeft = j - 1;
//
//                        countUp = 0;
//
//                        //find top most index
//                        //either be top of the board, or countUp = 7, which will help us move down the board finding all possible word placements
//                        while (findLeft >= 0 && countUp < 7)
//                        {
//                            //if empty, could be any letter
//                            if (!board.getSquareCopy(i, findLeft).isOccupied()) {
//                                word = "*".concat(word);
//                            }
//
//                            //if not empty, add letter found to word
//                            else {
//                                //TODO
//                                /*
//                                   ADD AS LOWER CASE FOR FUTURE PERMUTING?
//                                */
//                                word = String.valueOf(board.getSquareCopy(i, findLeft).getTile().getLetter()).concat(word);
//                            }
//
//                            //checks to find the top/start
//                            countUp++;
//                            findLeft--;
//                        }
//
//                        //find bottom of word
//                        countUp = 0;
//                        int findRight = j;
//
//                        while (findRight <= 14 && countUp < 7)
//                        {
//                            //if empty, could be any letter
//                            if (!board.getSquareCopy(i, findRight).isOccupied()) {
//                                word = word.concat("*");
//                            }
//
//                            //if not empty, add letter found to word
//                            else {
//                                //TODO
//                                /*
//                                   ADD AS LOWER CASE FOR FUTURE PERMUTING?
//                                */
//                                word = word.concat(String.valueOf(board.getSquareCopy(i, findRight).getTile().getLetter()));
//                            }
//
//                            //checks to find the top/start
//                            countUp++;
//                            findRight++;
//                        }
//
//                        //prevent findTop from being -1 when checking near top
//                        colin = findLeft + 66;
//                        colch = (char) colin;
//                        possiblePlacement += (String.valueOf(colch) + String.valueOf(j) + " A " + word);
//                        possibleWords.add(possiblePlacement);
//                        possiblePlacement = "";
//                        word = "";
//                    }
//                }
//            }
//        }
//
//        return possibleWords;
//    }
//


    //LEGACY CODE, MAY NEED PARTS OF IT IN FUTURE PERHAPS

//    private String bestPossiblePlay(ArrayList<String> possibleWords)
//    {
//        String bestPlay = "";
//
//        return bestPlay;
//    }
//
//    private ArrayList<String> findPossibleWords()
//    {
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
//    }
}
