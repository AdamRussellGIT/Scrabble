//Karol Wojcik 18322146
//Adam Russell 18328861
//Carlo Motteran 18717341

//Team: Camel Bois
//Assignment 1

import java.util.ArrayList;

public class Frame 
{
	ArrayList<Tile> theFrameArray;
	
	Pool p;
	
	//Frame Constructor
	public Frame()
	{
		theFrameArray = new ArrayList<Tile>();
		p = new Pool();
		
		refillFrame(p);
	}
	
	//Remove letters method
	public Tile removeLettersFrame(char removeLetter)
	{
		char ch = Character.toUpperCase(removeLetter);
		Tile r = new Tile(' ', 0);
		boolean found = false;
		//Loops through the Array
		for(int i =0; i<theFrameArray.size();i++)
		{
			//Checks if the chosen letter to remove is equal to
			//any letter present within the Frame
			if(theFrameArray.get(i).letter == ch)
			{
				r = theFrameArray.get(i);
				theFrameArray.remove(i);
				found = true;
				break;
			}
		}

		if (!found)
		{
			throw new IllegalArgumentException("Tile not in  Pool!");
		}
		
		return r;
	}
	
	//Checking if the letter is contained within the Frame
	public Boolean checkLettersFrame(char letter)
	{
		char ch = Character.toUpperCase(letter);
		for(int i =0; i<theFrameArray.size();i++)
		{
			if((theFrameArray.get(i).letter) == (ch))
			{
				return true;
			}
		}
		return false;	
	}
	
	//Accesses the Frame
	public ArrayList<Tile> accessLettersFrame(ArrayList<Tile> theFrameArray)
	{
		return theFrameArray;
	}
	
	//Collaboration with Carlo's Pool class
	public void refillFrame(Pool p)
	{	
		//Runs while the Frame has less than 7 Tiles
		while(theFrameArray.size() != 7)
		{
			//Calling draw class from the Pool class in order to draw a new Tile into the Frame from the Pool
			theFrameArray.add(p.draw());	
		}
	}
	
	//Checks if the Frame is Empty
	public Boolean checkEmptyFrame()
	{
		return theFrameArray.isEmpty();
	}
	
	//To String that prints out the Frame in an appropriate format
	public String toString()
	{
		//return "[" + theFrameArray.get(0).letter + " " + theFrameArray.get(1).letter + "" + " " + theFrameArray.get(2).letter + " " + theFrameArray.get(3).letter + "" + " " + theFrameArray.get(4).letter + " " + theFrameArray.get(5).letter + "" + " " + theFrameArray.get(6).letter + "]" + "\n" + "|" + theFrameArray.get(0).value + " " + theFrameArray.get(1).value + "" + " " + theFrameArray.get(2).value + " " + theFrameArray.get(3).value + "" + " " + theFrameArray.get(4).value + " " + theFrameArray.get(5).value + " " + theFrameArray.get(6).value + "|";
		String f = "";
		f += "[ ";
		for(int i = 0; i < theFrameArray.size(); i++)
		{
			f += (theFrameArray.get(i).letter + " ");
		}
		f += "]\n[ ";
		
		for (int j = 0; j < theFrameArray.size(); j++)
		{
			f += (theFrameArray.get(j).value + " ");
		}
		f += "]";
		
		return f;
	}
	
	
}



