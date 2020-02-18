//Team: Camel Bois
//Members: Adam Russell - 18328861
//		   Carlo Motteran - 18717341
//		   Karol Wojcik - 18322146
public class Tile 
{
	int value;
	char letter;
	
	public Tile(char letter, int value)
	{
		this.letter = letter;
		this.value = value;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public char getLetter()
	{
		return this.letter;
	}

	public void setValue(int val)
	{
		this.value = val;
	}

	public void setLetter(char let)
	{
		this.letter = let;
	}
}
