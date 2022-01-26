import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class GameModel{
	///properties 
	
	// Objects relevant for gameplay.
	// CHARACTER OBJECT
	public class Character1{
		///properties
		int intID = 0; //Default Value -- logic reasons, ID number of player
		int intX = 0; //Default Value -- Char pos x
		int intY = 0; //Default Value -- Char pos y
		int intHP; // Below are gameplay realted values
		int intAttack;
		int intSpeedX = 0;
		int intSpeedY = 0;
		int intSizeX = 20;
		int intSizeY = 20;
		int intSkillTime = 0; // Skill CD time
		int intCharType; // what the class is
		int intLives = 3;
		String strUser; // user name
		
		ArrayList<Projectile1> projectiles = new ArrayList<Projectile1>(); // internal projectile tracker
		boolean blnShooting = false; // is the user shooting
	
		///methods
		public void spawn(){ // spawning players -- ID is based on entry to game, so spawning is also based on that
			if(intID == 5){ 
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
			intHP = 100; // double check reseting hp vals.
		}
		
		public void moveX(){ // movement -- x
			this.intX += intSpeedX;
			if(intX >= 640 || intX <= 0){ // character borders
				intX = intX-intSpeedX;
				intX = intX-intSpeedX;
			}
		}
		
		public void moveY(){ // movement -- y
			this.intY += intSpeedY;	
			if(intY >= 640 || intY <= 0){ // character borders
				intY = intY-intSpeedY;
				intY = intY-intSpeedY;
			}
		}
		
		//speed calculations -- modifications based on gameController aka user input
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
		
		public void shoot(int intIDn, int intSpeedXn, int intSpeedYn, int intSize, int intXfrom, int intYfrom){ // projectile adding function, can be from local user can be from other users.
			projectiles.add(new Projectile1(intIDn, intXfrom, intYfrom, 200, 200, intSpeedXn, intSpeedYn, intSize, intAttack, intXfrom, intYfrom));
			//Creates new projectile in projectile tracker array, based on input values representing ID (who's bullet it is), speed in x and y, the general size, and where the projectile was shot from (to calculate for the maximum ranges)
		}
		
		public boolean skill(int intIDn, int intCharTypeIn, int intXn, int intYn){ // skills
			if(intSkillTime > 99){ // if the skill bar is full, time based charge, will unleash skill upon command.
				//Projectiles use the same logic as the shoot above, using the projectile class's constructor.
				if(intCharTypeIn == 1){ //Character one's skill
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 10, 10, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -10, -10, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 10, -10, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -10, 10, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 12, 12, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -12, -12, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 12, -12, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -12, 12, 10, 20, intXn, intYn));		
				}else if(intCharTypeIn == 2){ //Character two's skill
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 10, 0, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -10, 0, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 0, -10, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 0, 10, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 10, 10, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -10, -10, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 10, -10, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -10, 10, 10, 20, intXn, intYn));		
				}else if(intCharTypeIn == 3){ // Character three's skill
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, 8, 8, 20, 30, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, -8, -8, 20, 30, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, 8, -8, 20, 30, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, -8, 8, 20, 30, intXn, intYn));					
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, 15, 15, 5, 10, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, -15, -15, 5, 10, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, 15, -15, 5, 10, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 400, 400, -15, 15, 5, 10, intXn, intYn));		
				}else if(intCharTypeIn == 4){ // Character four's skill
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 10, 0, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 12, 0, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, 15, 0, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -10, 0, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -12, 0, 10, 20, intXn, intYn));
					projectiles.add(new Projectile1(intIDn, intXn, intYn, 500, 500, -15, 0, 10, 20, intXn, intYn));		
				}
				// all shoot rounds of special bullets.
				if(intID == intIDn){ // if it is the local user who shot the bullet , reset skill bar. (IDn -- inputted Id = local ID).
					intSkillTime = 0;
				}
				return true; // returns skill shot is true as skillbar was full.
			}
			return false; // returns skill shot is false as skillbar was not full.
			//t/f used for ssm data.
		}
		
		public void collision(int intIDn, int intDamagen){ // collision detection for player.
			if (intIDn == 100){ // collides with default terrain
				intX = intX-intSpeedX; // no move
				intY = intY-intSpeedY;
			}
			if (intIDn == 50){ // collides with damaging terrain
				intHP--; // take damage
			}
			if (intIDn == 63){ // collides with Dummy (tutorial)
				
			}
			if (intIDn != intID){ // collides with something else 
				intHP = intHP - intDamagen; // take damage based on damagen field 
			}
		}

		public void update(){ // projectile range and projectiele updating. Also skill CD updates. Runs iwth gamecontroller timer.
			for(int intCount = projectiles.size()-1; intCount > -1; intCount--){
				projectiles.get(intCount).move();
				//below 
				if (projectiles.get(intCount).intShotX + projectiles.get(intCount).intMaxRangeX < projectiles.get(intCount).intX || projectiles.get(intCount).intShotX - projectiles.get(intCount).intMaxRangeX > projectiles.get(intCount).intX){
					projectiles.remove((intCount));
				}else if (projectiles.get(intCount).intShotY + projectiles.get(intCount).intMaxRangeY < projectiles.get(intCount).intY || projectiles.get(intCount).intShotY - projectiles.get(intCount).intMaxRangeY > projectiles.get(intCount).intY){
					projectiles.remove((intCount));
				}
			}
			intSkillTime++; //Runs on timer, every timer tick causes skill bar to be refreshed by val of 1.
			if(intSkillTime > 100){ // prevent skillbar from overflowing.
				intSkillTime = 100;
			}
		}
		
		public boolean deathCheck(){ //checks if a player dies
			if(intHP < 1){ // if player has no more health, put them off the screen, reset their health, and reduce live.
				intLives--;
				intX = 10000;
				intY = 10000;
				intHP = 100;
				return true; // return that they did indeed die
			}
			return false; // return that they are alive and breathing(?)
		}
		
		public boolean outCheck(){ // checks if a player is "out"/eliminated
			if(intLives < 1){ //if they have no more lives they are out
				return true; // they are eliminated
			}
			return false; // they just died but still have lives so they are not eliminated.
		}
		
		///constructor
		public Character1(int intID, int intX, int intY, int intHP, int intAttack, int intSpeedX, int intSpeedY, int intCharType, String strUser){
			this.intID = intID;
			this.intX = intX;
			this.intY = intY;
			this.intHP = intHP;
			this.intAttack = intAttack;
			this.intSpeedX = intSpeedX;
			this.intSpeedY = intSpeedY;
			this.intCharType = intCharType;
			this.strUser = strUser;
		}
	}	
	// TERRAIN OBJECT
	class Terrain1{
		/// properties
		int intX; // location
		int intY;
		int intSizeX; // size
		int intSizeY;
		int intID; // type of terrain, uses same ID logic as characters
		
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
	
	// PROJECTILE OBJECT
	class Projectile1{
		/// properties
		int intID; // who's projectile it is. Uses same Id logic as characters and terrain.
		int intX; // location
		int intY;
		int intMaxRangeX; //maximum range
		int intMaxRangeY;
		int intSpeedX; // speed
		int intSpeedY;
		int intSize; // general size (always square/circle)
		int intDamage; // how much damage each projectile does
		int intShotX; // where it was originally shot (for max range calcs)
		int intShotY;
		
		/// methods
		
		public void move(){ // move projectile location
			intX += intSpeedX;
			intY += intSpeedY;
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

