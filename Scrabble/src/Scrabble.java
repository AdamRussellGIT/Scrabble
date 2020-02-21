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

    int turn = 0;
    Player currentPlayer;
    String choice;

    public Scrabble(String name1, String name2)
    {
        gamePool = new Pool();
        gameBoard = new Board();
        player1 = new Player(name1, gamePool);
        player2 = new Player(name2, gamePool);
    }

    public void playGame()
    {
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
                UI.print("Enter 'QUIT', 'PASS', 'EXCHANGE', 'HELP', 'PLACEWORD', 'CHALLENGE'");
                choice = UI.getChoice();
            } while (!UI.correctChoice);

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
        UI.print("Enter the name for Player 1 : ");
        String name1 = UI.getInput();
        UI.print("Enter the name for Player 2 : ");
        String name2 = UI.getInput();
        UI.print("Let's play!");
        Scrabble scrabbleGame = new Scrabble(name1, name2);
        scrabbleGame.playGame();
    }
}
