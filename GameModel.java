import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class GameModel{
	///properties 
	ArrayList<String> strConnectInfo = new ArrayList<String>();
	String[][] strMUInfo = new String[4][10]; // 10 is flex number we see later
	int[][] intMap = new int[360][360]; 

	///methods
	
	///constructor
	
	// Temporary hypothetical properties/methods of characters
	public class Character1{
		///properties
		int intID;
		int intX;
		int intY;
		int intHP;
		int intAttack;
		int intSpeedX = 0;
		int intSpeedY = 0;
		int intSizeX = 40;
		int intSizeY = 40;
		String strChar;
	
		///methods
		public void spawn(){
		}
		
		public void move(){
			this.intSizeX += intSpeedX;
			this.intSizeY += intSpeedY;
		}
		
		public void basic(){
		}
		
		public void skill(){
		}
		
		public void collision(){
		}
		
		///constructor
		
	// Temporary hypothethical properties of terrain objects
	public class Terrain1{
		/// properties
		int intX;
		int intY;
		int intHP;
		
		/// methods
		///constructor
	}
	
		// Temporary hypothethical properties of projectile objects
	public class Projectile1{
		/// properties
		int intX;
		int intY;
		int intMaxRangeX;
		int intMaxRangeY;
		int intSpeedX;
		int intSpeedY;
		int intSize;
		int intDamage;
		
		/// methods
		
		public void collision(){
			
		}
		
		///constructor
	}
	
	}
}
