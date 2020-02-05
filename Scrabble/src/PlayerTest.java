//Team: Camel Bois
//Members: Adam Russell - 18328861
//		   Carlo Motteran - 18717341
//		   Karol Wojick - 18322146

import java.util.Scanner;
public class PlayerTest 
{
	public static void main(String[] args)
	{
		Pool p = new Pool();
		
		System.out.println("POOL TESTS\n");
		try 
		{
			System.out.println("Size of current pool: " + p.poolSize());
			Tile t = p.draw();
			Tile t2 = p.draw();
			Tile t3 = p.draw();
			
			System.out.println("Drew 3 tiles, pool size now " + p.poolSize() + ",\nand the three tiles were " + t.getLetter() + "," + t2.getLetter() + "," + t3.getLetter());
			
			p.reset();
			System.out.println("Pool was rest, size now " + p.poolSize());
		} catch(RuntimeException r)
		{
			throw new RuntimeException("Error in pool tests");
		}
		
		System.out.println("PLAYER TESTS\n");
		try 
		{
			Player po = new Player("Carlo", p);
			System.out.println("Expected:\nCarlo\nhis score being 0,\nand his frame.");
			System.out.println("\nGot:\n" + po.getName() + "\n" + po.getScore() + "\n" + po.toString());
			
			Player po1 = new Player("Adam", p);
			System.out.println("Expected:\nAdam\nhis score being 0,\nand his frame.");
			System.out.println("\nGot:\n" + po1.getName() + "\n" + po1.getScore() + "\n" + po1.toString());
			System.out.println("Does the frame contain G?: " + po1.frame.checkLettersFrame('G'));
			p.reset();
		} catch(RuntimeException r2)
		{
			throw new RuntimeException("Error in player class");
		}
		
		System.out.println("FRAME TESTS\n");
		
		try (Scanner in = new Scanner(System.in))
		{
			Player play = new Player("Karol", p);
			System.out.println("\nGot:\n" + play.getName() + "\n" + play.getScore() + "\n" + play.toString());
			System.out.println("Pick a letter you want to remove: ");
			char letter = in.nextLine().charAt(0);
			Tile test = play.frame.removeLettersFrame(letter);
			System.out.println("Removed: " + test.getLetter() + " " + test.getValue());
			play.frame.refillFrame(p);
			System.out.println("Pool Size reduced by 1: " + p.poolSize());
			
			System.out.println("\nNew frame: \n" + play.toString() + "\n");
			
			System.out.println("Reset the pool.\nPool size was " + p.poolSize());
			p.reset();
			System.out.println("Pool Size now " + p.poolSize() + "\n");
			
			System.out.println("Current Player looks like this: ");
			System.out.println("Name: " + play.getName() + "\nScore: " + play.getScore() + "\nFrame: \n" + play.toString());
			System.out.println("Reset the player value, enter the new name for the player: ");
			String newName = in.next();
			play.resetPlayer(newName, p);
			System.out.println("Name: " + play.getName() + "\nScore: " + play.getScore() + "\nFrame: \n" + play.toString());
		} catch (RuntimeException r3)
		{
			throw new RuntimeException("Error in frame class");
		}
		
		

		}
}
