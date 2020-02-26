//Team: Camel Bois
//Members: Adam Russell - 18328861
//		   Carlo Motteran - 18717341
//		   Karol Wojcik - 18322146



public class Player 
{
	private String name = null; //variable to hold name
	private int score = 0; // variable to hold score
	Frame frame; // variable to store the players frame
	
	public Player(String name, Pool p)
	{
		setName(name); // sets the name for the player
		setScore(0); // sets the intial score for the player
		setFrame(p); // assigns a frame to the player
	}
	
	//resets player varaibles
	public void resetPlayer(String name, Pool p)
	{
		setName(name);
		setScore(0);
		setFrame(p);
	}
	
	//assigns a new frame to the player
	private void setFrame(Pool p)
	{
		this.frame = new Frame(p);
	}
	
	
	//sets the name of the player
	private void setName(String name)
	{
			this.name = name;
	}
	
	//adds to the current players score
	public void setScore(int score)
	{
		this.score += score;
	}
	
	//returns the name of the current player
	public String getName()
	{
		return this.name;
	}
	
	//returns the scoe of the current player
	public int getScore()
	{
		return this.score;
	}
	
	//prints the players rack
	public String toString()
	{
		return this.frame.toString();
	}
}
