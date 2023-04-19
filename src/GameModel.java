/// GameModel
/// By: Andre Arcaina, Nicholas Hioe, Sean Kwee
/// ICS 4U1
/// Version 1.0
/// 2022-01-27

import java.util.ArrayList;

/**
 * This class represents the data of the game.
 * Contains subclasses of characters, projectiles, and terrains with methods.
 */
public class GameModel{
	///properties 
	
	// Objects relevant for gameplay.
	// CHARACTER OBJECT
	
	/**
	* This class represents the character object and stores set values and values to change.
	* Contains methods for the character object to move, spawn, die, much more.
	*/
	public class Character1{
		///properties
		int intID = 0; //Default Value -- logic reasons, ID number of player
		double dblX = 0; //Default Value -- Char pos x
		double dblY = 0; //Default Value -- Char pos y
		int intHP; // Below are gameplay realted values
		int intAttack;
		double dblSpeedX = 0;
		double dblSpeedY = 0;
		int intSizeX = 20;
		int intSizeY = 20;
		int intSkillTime = 0; // Skill CD time
		int intCharType; // what the class is
		int intLives = 3;
		String strUser; // user name
		
		ArrayList<Projectile1> projectiles = new ArrayList<Projectile1>(); // internal projectile tracker
		boolean blnShooting = false; // is the user shooting
	
		///methods
		
		/**
		 * The spawn method determines where the player spawns and is dependent on the Player ID, which is given to them once they join. 
		 */
		public void spawn(){ // spawning players -- ID is based on entry to game, so spawning is also based on that
			if(intID == 5){ 
				dblX = 10;
				dblY = 10;
			}else if(intID == 6){
				dblX = 620;
				dblY = 10;
			}else if(intID == 7){
				dblX = 10;
				dblY = 620;
			}else if(intID == 8){
				dblX = 620;
				dblY = 620;				
			}
			intHP = 100; // double check reseting hp vals.
		}
		/**
		 * The moveX method is the basic movement of the character object in the X axis.
		 * This method also limits character borders.
		 */
		public void moveX(){ // movement -- x
			this.dblX += dblSpeedX;
			if(dblX >= 640 || dblX <= 0){ // character borders
				dblX = dblX-dblSpeedX;
				dblX = dblX-dblSpeedX;
			}
		}
		/**
		 * The moveY method is the basic movement of the character object in the Y axis.
		 * This method also limits character borders.
		 */
		public void moveY(){ // movement -- y
			this.dblY += dblSpeedY;	
			if(dblY >= 640 || dblY <= 0){ // character borders
				dblY = dblY-dblSpeedY;
				dblY = dblY-dblSpeedY;
			}
		}
		
		/**
		 * This method calculates the speed at which the character moves upwards in the Y axis.
		 * Determined and modified by User Input.
		 */
		//speed calculations -- modifications based on gameController aka user input
		public void up(int dblSpeedYIn){ 
			dblSpeedY = -dblSpeedYIn;
		}
		
		/**
		 * This method calculates the speed at which the character moves downwards in the Y axis.
		 * Determined and modified by User Input.
		 */
		public void down(int dblSpeedYIn){
			dblSpeedY = dblSpeedYIn;
		}
		
		/**
		 * This method calculates the speed at which the character moves left in the X axis.
		 * Determined and modified by User Input.
		 */
		public void right(int dblSpeedXIn){
			dblSpeedX = dblSpeedXIn;
		}
		
		/**
		 * This method calculates the speed at which the character moves in the X axis.
		 * Determined and modified by User Input.
		 */
		public void left(int dblSpeedXIn){
			dblSpeedX = -dblSpeedXIn;
		}
		
		/**
		 * This method shoots a projectile by creating a new projectile in the projectile tracker array, and is based on Player ID, Projectile Speed in X and Y components, and the maximum range of the projectile.  
		 */
		public void shoot(int intIDn, double dblSpeedXn, double dblSpeedYn, int intSize, double dblXFrom, double dblYFrom){ // projectile adding function, can be from local user can be from other users.
			projectiles.add(new Projectile1(intIDn, dblXFrom, dblYFrom, 200, 200, dblSpeedXn, dblSpeedYn, intSize, intAttack, dblXFrom, dblYFrom));
			//Creates new projectile in projectile tracker array, based on input values representing ID (who's bullet it is), speed in x and y, the general size, and where the projectile was shot from (to calculate for the maximum ranges)
		}
		
		/**
		 * Dependent on the Character Type, this method will use that certain Character's skill.
		 * This method will only return true if the Skill Bar is full.
		 * Otherwise, it will return false.
		 */
		public boolean skill(int intIDn, int intCharTypeIn, double dblSpeedY, double dblYn){ // skills
			if(intSkillTime > 99){ // if the skill bar is full, time based charge, will unleash skill upon command.
				//Projectiles use the same logic as the shoot above, using the projectile class's constructor.
				if(intCharTypeIn == 1){ //Character one's skill
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, 10, 10, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, -10, -10, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, 10, -10, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, -10, 10, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, 12, 12, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, -12, -12, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, 12, -12, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, -12, 12, 10, 20, dblSpeedY, dblYn));		
				}else if(intCharTypeIn == 2){ //Character two's skill
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, 10, 0, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, -10, 0, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, 0, -10, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, 0, 10, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, 10, 10, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, -10, -10, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, 10, -10, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, -10, 10, 10, 20, dblSpeedY, dblYn));		
				}else if(intCharTypeIn == 3){ // Character three's skill
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 400, 400, 8, 8, 20, 30, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 400, 400, -8, -8, 20, 30, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 400, 400, 8, -8, 20, 30, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 400, 400, -8, 8, 20, 30, dblSpeedY, dblYn));					
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 400, 400, 15, 15, 5, 10, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 400, 400, -15, -15, 5, 10, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 400, 400, 15, -15, 5, 10, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 400, 400, -15, 15, 5, 10, dblSpeedY, dblYn));		
				}else if(intCharTypeIn == 4){ // Character four's skill
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, 10, 0, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, 12, 0, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, 15, 0, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, -10, 0, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, -12, 0, 10, 20, dblSpeedY, dblYn));
					projectiles.add(new Projectile1(intIDn, dblSpeedY, dblYn, 500, 500, -15, 0, 10, 20, dblSpeedY, dblYn));		
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
		
		/**
		 * This method detects collision for the character object.
		 * Each collision has Hidden IDs, and if a certain terrain object has a certain ID, the character will either not be able to walk into the terrain or they will take damage from it (I.e water).
		 */
		public void collision(int intIDn, int intDamagen){ // collision detection for player.
			if (intIDn == 100){ // collides with default terrain
				dblX = dblX-dblSpeedX; // no move
				dblY = dblY-dblSpeedY;
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

		/**
		 * This method updates the projectile range, skill cooldowns, and runs with the timer.
		 */
		public void update(){ // projectile range and projectile updating. Also skill CD updates. Runs iwth gamecontroller timer.
			for(int intCount = projectiles.size()-1; intCount > -1; intCount--){
				projectiles.get(intCount).move();
				//below 
				if (projectiles.get(intCount).dblShotX + projectiles.get(intCount).dblMaxRangeX < projectiles.get(intCount).dblX || projectiles.get(intCount).dblShotX - projectiles.get(intCount).dblMaxRangeX > projectiles.get(intCount).dblX){
					projectiles.remove((intCount));
				}else if (projectiles.get(intCount).dblShotY + projectiles.get(intCount).dblMaxRangeY < projectiles.get(intCount).dblY || projectiles.get(intCount).dblShotY - projectiles.get(intCount).dblMaxRangeY > projectiles.get(intCount).dblY){
					projectiles.remove((intCount));
				}
			}
			intSkillTime++; //Runs on timer, every timer tick causes skill bar to be refreshed by val of 1.
			if(intSkillTime > 100){ // prevent skillbar from overflowing.
				intSkillTime = 100;
			}
		}
		
		/**
		 * This method checks if the player has died.
		 * The lives will slowly go done. 
		 */
		public boolean deathCheck(){ //checks if a player dies
			if(intHP < 1){ // if player has no more health, put them off the screen, reset their health, and reduce live.
				intLives--;
				dblX = 10000;
				dblY = 10000;
				intHP = 100;
				return true; // return that they did indeed die
			}
			return false; // return that they are alive and breathing(?)
		}
		
		/**
		 * This method checks if the player has less than one Life.
		 * If they have less than one life, they are "out" or eliminated from the game.
		 */
		public boolean outCheck(){ // checks if a player is "out"/eliminated
			if(intLives < 1){ //if they have no more lives they are out
				return true; // they are eliminated
			}
			return false; // they just died but still have lives so they are not eliminated.
		}
		
		/**
		 * This is the constructor with parameters, respectively, of the player ID, the player's coordinates, the player's HP, the player's speed in X and Y, the character they selected, and their username.
		 */
		///constructor
		public Character1(int intID, double dblX, double dblY, int intHP, int intAttack, double dblSpeedX, double dblSpeedY, int intCharType, String strUser){
			this.intID = intID;
			this.dblX = dblX;
			this.dblY = dblY;
			this.intHP = intHP;
			this.intAttack = intAttack;
			this.dblSpeedX = dblSpeedX;
			this.dblSpeedY = dblSpeedY;
			this.intCharType = intCharType;
			this.strUser = strUser;
		}
	}	
	// TERRAIN OBJECT
	
	/**
	*Terrain object class.
	*/
	public class Terrain1{
		/// properties
		double dblX; // location
		double dblY;
		int intSizeX; // size
		int intSizeY;
		int intID; // type of terrain, uses same ID logic as characters
		
	
		
		/**
		*This is the constructor with parameters, respectively, of the terrain object location in the X and Y, the terrain object's Size in the X and Y, and the Terrain object's ID for collision detection.
		*/
		///constructor
		public Terrain1(double dblX, double dblY, int intSizeX, int intSizeY, int intID){
			this.dblX = dblX;
			this.dblY = dblY;
			this.intSizeX = intSizeX;
			this.intSizeY = intSizeY;
			this.intID = intID;
		}
	}
	
	/**
	*Projectile object class.
	*/
	// PROJECTILE OBJECT
	public class Projectile1{
		/// properties
		int intID; // who's projectile it is. Uses same Id logic as characters and terrain.
		double dblX; // location
		double dblY;
		double dblMaxRangeX; //maximum range
		double dblMaxRangeY;
		double dblSpeedX; // speed
		double dblSpeedY;
		int intSize; // general size (always square/circle)
		int intDamage; // how much damage each projectile does
		double dblShotX; // where it was originally shot (for max range calcs)
		double dblShotY;
		
		/// methods
		
		/**
		*Move's projectile location.
		*/
		public void move(){ // move projectile location
			dblX += dblSpeedX;
			dblY += dblSpeedY;
		}
		
		/**
		* This is the constructor with parameters, respectively, of the projectiles ID for collision detection, the location in the X and Y, the max range in the X and Y, the speed in the X and Y, the size of the projectile, the damage, and where the projectile was shot from.
		*/
		///constructor
		public Projectile1(int intID, double dblX, double dblY, double dblMaxRangeX, double dblMaxRangeY, double dblSpeedX, double dblSpeedY, int intSize, int intDamage, double dblShotX, double dblShotY){
			this.intID = intID;
			this.dblX = dblX;
			this.dblY = dblY;
			this.dblMaxRangeX = dblMaxRangeX;
			this.dblMaxRangeY = dblMaxRangeY;
			this.dblSpeedX = dblSpeedX;
			this.dblSpeedY = dblSpeedY;
			this.intSize = intSize;
			this.intDamage = intDamage;
			this.dblShotX = dblShotX;
			this.dblShotY = dblShotY;
		
		}
	}
	
}

