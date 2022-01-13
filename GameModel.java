import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class GameModel{
	///properties 
	ArrayList<String> strConnectInfo = new ArrayList<String>();
	String[][] strMUInfo = new String[4][10]; // 10 is flex number we see later
	int[][] intMap = new int[700][700];
	
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
		int intSpeedX;
		int intSpeedY;
	
		///methods
		public void spawn(){
		}
		
		public void move(){
		}
		
		public void basic(){
		}
		
		public void skill(){
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
		///constructor
	}
	
	}
}
