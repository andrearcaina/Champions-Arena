import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class GameModel{
	///properties 
	ArrayList<String> projectiles = new ArrayList<String>();
	String[][] strMUInfo = new String[4][10]; // 10 is flex number we see later
	int[][] intMap = new int[360][360]; 

	///methods
	
	///constructor
	
	// Temporary hypothetical properties/methods of characters
	public class Character1{
		///properties
		int intID = 0;
		int intX = 0; //temp
		int intY = 0; //temp
		int intHP;
		int intAttack;
		int intSpeedX = 0;
		int intSpeedY = 0;
		int intSizeX = 20;
		int intSizeY = 20;
		int intSkillTime = 0;
		int intCharType;
		int intLives = 3;
		
		ArrayList<Projectile1> projectiles = new ArrayList<Projectile1>();
		boolean blnShooting = false;
	
		///methods
		public void spawn(){
			if(intID == 5){ // spawning players
				intX = 10;
				intY = 10;
			}else if(intID == 6){
				intX = 620;
				intY = 10;
			}else if(intID == 7){
				intX = 10;
				intY = 620;
			}else if(intID == 8){
				intX = 620;
				intY = 620;				
			}
			intHP = 100;
		}
		
		public void moveX(){
			this.intX += intSpeedX;
			if(intX >= 640 || intX <= 0){ // character borders
				intX = intX-intSpeedX;
				intX = intX-intSpeedX;
			}
		}
		
		public void moveY(){
			this.intY += intSpeedY;	
			if(intY >= 640 || intY <= 0){
				intY = intY-intSpeedY;
				intY = intY-intSpeedY;
			}
		}
		
		//speed calculations
		public void up(int intSpeedYIn){
			intSpeedY = -intSpeedYIn;
		}
		public void down(int intSpeedYIn){
			intSpeedY = intSpeedYIn;
		}
		public void right(int intSpeedXIn){
			intSpeedX = intSpeedXIn;
		}
		public void left(int intSpeedXIn){
			intSpeedX = -intSpeedXIn;
		}
		
		public void shoot(int intIDn, int intSpeedXn, int intSpeedYn, int intSize, int intXfrom, int intYfrom){
			projectiles.add(new Projectile1(intIDn, intXfrom, intYfrom, 200, 200, intSpeedXn, intSpeedYn, intSize, intAttack, intXfrom, intYfrom));
		}
		
		public boolean skill(int intIDn, int intCharTypeIn, int intXn, int intYn){
			if(intSkillTime > 99){
				if(intCharTypeIn == 1){
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 10, 10, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -10, -10, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 10, -10, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -10, 10, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 12, 12, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -12, -12, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 12, -12, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -12, 12, 10, 20, intXn, intYn));		
				}else if(intCharTypeIn == 2){
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 10, 0, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -10, 0, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 0, -10, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 0, 10, 10, 20, intXn, intYn));		
				}else if(intCharTypeIn == 3){
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, 8, 8, 20, 30, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, -8, -8, 20, 30, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, 8, -8, 20, 30, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, -8, 8, 20, 30, intXn, intYn));					
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, 15, 15, 5, 10, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, -15, -15, 5, 10, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, 15, -15, 5, 10, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, -15, 15, 5, 10, intXn, intYn));		
				}else if(intCharTypeIn == 4){
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 10, 0, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 12, 0, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 15, 0, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -10, 0, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -12, 0, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -15, 0, 10, 20, intXn, intYn));		
				}
				if(intID == intIDn){
					intSkillTime = 0;
				}
				return true;
			}
			return false;
		}
		
		public void collision(int intIDn, int intDamagen){
			if (intIDn == 100){ //default terrain
				intX = intX-intSpeedX;
				intY = intY-intSpeedY;
			}
			if (intIDn == 50){ // damaging terrain
				intHP--;
			}
			if (intIDn == 63){ // Dummy
				intX = intX-intSpeedX;
				intY = intY-intSpeedY;
			}
			if (intIDn != intID){
				intHP = intHP - intDamagen;
			}
		}

		public void update(){ // projectile range
			for(int intCount = projectiles.size()-1; intCount > -1; intCount--){
				projectiles.get(intCount).move();
				if (projectiles.get(intCount).intShotX + projectiles.get(intCount).intMaxRangeX < projectiles.get(intCount).intX || projectiles.get(intCount).intShotX - projectiles.get(intCount).intMaxRangeX > projectiles.get(intCount).intX){
					projectiles.remove((intCount));
				}else if (projectiles.get(intCount).intShotY + projectiles.get(intCount).intMaxRangeY < projectiles.get(intCount).intY || projectiles.get(intCount).intShotY - projectiles.get(intCount).intMaxRangeY > projectiles.get(intCount).intY){
					projectiles.remove((intCount));
				}
			}
			intSkillTime++;
			if(intSkillTime > 100){
				intSkillTime = 100;
			}
		}
		
		public boolean deathCheck(){
			if(intHP < 1){
				intLives--;
				intX = 10000;
				intY = 10000;
				intHP = 100;
				return true;
			}
			return false;
		}
		
		public boolean outCheck(){
			if(intLives < 1){
				return true;
			}
			return false;
		}
		
		///constructor
		public Character1(int intID, int intX, int intY, int intHP, int intAttack, int intSpeedX, int intSpeedY, int intCharType){
			this.intID = intID;
			this.intX = intX;
			this.intY = intY;
			this.intHP = intHP;
			this.intAttack = intAttack;
			this.intSpeedX = intSpeedX;
			this.intSpeedY = intSpeedY;
			this.intCharType = intCharType;
		}
	}	
	// Temporary hypothethical properties of terrain objects
	class Terrain1{
		/// properties
		int intX;
		int intY;
		int intSizeX;
		int intSizeY;
		int intID;
		//int intHP;
		
		/// methods
		
		///constructor
		public Terrain1(int intX, int intY, int intSizeX, int intSizeY, int intID){
			this.intX = intX;
			this.intY = intY;
			this.intSizeX = intSizeX;
			this.intSizeY = intSizeY;
			this.intID = intID;
		}
	}
	
		// Temporary hypothethical properties of projectile objects
	class Projectile1{
		/// properties
		int intID;
		int intX;
		int intY;
		int intMaxRangeX;
		int intMaxRangeY;
		int intSpeedX;
		int intSpeedY;
		int intSize;
		int intDamage;
		int intShotX;
		int intShotY;
		
		/// methods
		
		public void collision(int intIDn){
			
		}
		public void move(){
			intX += intSpeedX;
			intY += intSpeedY;
			//System.out.println(1);
		}
		
		///constructor
		public Projectile1(int intID, int intX, int intY, int intMaxRangeX, int intMaxRangeY, int intSpeedX, int intSpeedY, int intSize, int intDamage, int intShotX, int intShotY){
			this.intID = intID;
			this.intX = intX;
			this.intY = intY;
			this.intMaxRangeX = intMaxRangeX;
			this.intMaxRangeY = intMaxRangeY;
			this.intSpeedX = intSpeedX;
			this.intSpeedY = intSpeedY;
			this.intSize = intSize;
			this.intDamage = intDamage;
			this.intShotX = intShotX;
			this.intShotY = intShotY;
		
		}
	}
	
}

