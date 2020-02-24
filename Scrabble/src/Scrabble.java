//Team: Camel Bois
//Members: Adam Russell - 18328861
//         Karol Wojcik - 18322146
//         Carlo Motteran -

import java.util.ArrayList;

public class Scrabble
{
    Player player1;
    Player player2;
    Pool gamePool;
    Board gameBoard;
    UI gameUI;

    int turn = 0;
    Player currentPlayer;
    String choice;

    public Scrabble()
    {
        gamePool = new Pool();
        gameBoard = new Board();
        gameUI = new UI();
    }

    public void playGame()
    {
        gameUI.print("Enter the name for Player 1 : ");
        String name1 = gameUI.getInput();
        gameUI.print("Enter the name for Player 2 : ");
        String name2 = gameUI.getInput();
        gameUI.print("Let's play!");

        player1 = new Player(name1, gamePool);
        player2 = new Player(name2, gamePool);

        while (!gamePool.poolEmpty() && (!player1.frame.checkEmptyFrame() || !player2.frame.checkEmptyFrame()))
        {
            if (turn%2 == 0)
            {
                currentPlayer = player1;
            }

            else
            {
                currentPlayer = player2;
            }

            do {
                gameUI.print("Enter 'QUIT', 'PASS', 'EXCHANGE', 'HELP', 'PLACEWORD'");
                choice = gameUI.getInput();
            } while (!gameUI.checkChoice(choice));

            if (choice.equals("QUIT"))
            {
                gameUI.print("User chose to quit the game!");
                gamePool.workPool.clear();
            }

            if (choice.equals("PASS"))
            {
                gameUI.print("User chose to pass!");
            }

            if (choice.equals("EXCHANGE"))
            {
                ArrayList<Tile> tmpExchange = new ArrayList<>();
                String keepExchanging = "y";
                String exchangeInput;
                char charExchange;

                while (tmpExchange.size() <= gamePool.poolSize() && !keepExchanging.equals("y") && !currentPlayer.frame.checkEmptyFrame())
                {
                    gameUI.print("Enter the character you want to exchange : ");
                    exchangeInput = gameUI.getInput();
                    exchangeInput = exchangeInput.toUpperCase();
                    charExchange = exchangeInput.charAt(0);

                    if (currentPlayer.frame.checkLettersFrame(charExchange))
                    {
                        //remove tile from players frame
                        tmpExchange.add(currentPlayer.frame.removeLettersFrame(charExchange));
                    }

                    else
                    {
                        gameUI.print("You do not have that tile!");
                    }

                    if (!currentPlayer.frame.checkEmptyFrame()) {
                        do {
                            gameUI.print("Press y to exchange another letter, press n to finish exchanging : ");
                            keepExchanging = gameUI.getInput();
                        } while (!keepExchanging.equals("y") && !keepExchanging.equals("n"));
                    }
                }

                currentPlayer.frame.refillFrame(gamePool);

                gamePool.workPool.addAll(tmpExchange);

                tmpExchange.clear();
            }

            if (choice.equals("PLACEWORD"))
            {
                int row;
                int column;
                char direction;
                String word;
            }

            if (choice.equals("HELP"))
            {

            }

            turn++;
        }
    }

    public void calculateScore(Player player)
    {

    }



    public static void main(String[] args)
    {
        Scrabble scrabbleGame = new Scrabble();
        scrabbleGame.playGame();
    }
}
