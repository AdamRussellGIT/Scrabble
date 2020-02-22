//Team: Camel Bois
//Members: Adam Russell - 18328861
//         Karol Wojcik - 18322146
//         Carlo Motteran -

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

        while (gamePool.poolEmpty() != true && (player1.frame.checkEmptyFrame() != true || player2.frame.checkEmptyFrame() != true))
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
                gameUI.print("Enter 'QUIT', 'PASS', 'EXCHANGE', 'HELP', 'PLACEWORD', 'CHALLENGE'");
                choice = gameUI.getChoice();
            } while (!gameUI.correctChoice);

            if (choice == "QUIT")
            {
                gamePool.workPool.clear();
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
