import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class GameController implements ActionListener, KeyListener, MouseListener{
	///properties	
	JFrame frame = new JFrame("Champion's Arena: Fight for Glory"); //one frame only
	Timer timer = new Timer(1000/60, this); // Gameplay timer.
	Timer countdownTimer = new Timer(1000, this); // Countdown timer
	int intSecond = 5;
	int intRandom; // Maps
	SuperSocketMaster ssm; // networks
	boolean blnServer; // is user the server or not
	int intPlayerCount = 1; // server only: players PLAYING the game
	int intPlayerTotal = 1; // server only: players IN the game LOBBY. Is then  used for some player cap commands and server lockout commands.
	int intStartCheck = 0; //Duplicate var of intPlayerTotal. Amount  of players in the lobby, thus amount of plaers needed to start the game.
	int intX = 0; // player x location
	int intY = 0; // player y location.
	boolean blnIn = false; // is the player elimed or not
	boolean blnShootPrompt = true; //tutorial prompt
	boolean blnSkillPrompt = true; //tutorial prompt
	boolean blnDonePrompt = true; //tutorial prompt
	int intDummyCounter; // tutorial dummy shooting timer
	
	///GAME VIEW INCORPORATED: ANIMATED JPANELS
	//general panels: UX1 - UX3
	MPanel mainPanel = new MPanel(); //main panel
	HPanel helpPanel = new HPanel(); //help panel
	TPanel tutorialPanel = new TPanel(); //interactive tutorial
	
	//once you press play panels: UX4 - UX8
	LPanel lobbyPanel = new LPanel(); //lobby creation/entering lobby + usernames
	CPanel charPanel = new CPanel(); //character selection panel
	GPanel gamePanel = new GPanel(); //actual gameplay panel
	
	//win/lost/end panel: UX9
	EPanel endPanel = new EPanel(); //once game ends panel
	
	//sorry/cap limit panel: UX10
	SPanel capPanel = new SPanel(); //for users trying to join even though there is a lobby with 4 people or a game already has started
	
	///Accessing the Character object in the GameModel class
	GameModel.Character1 c1 = new GameModel().new Character1(1, 200, 200, 100, 1, 0, 0, 3, ""); // Local character
	GameModel.Character1 cT = new GameModel().new Character1(63, 330, 0, 100, 1, 0, 0, 1, ""); // Dummy character
	ArrayList<GameModel.Terrain1> map = new ArrayList<GameModel.Terrain1>(); // map is an arraylist of terrain objects.
	ArrayList<GameModel.Character1> characters = new ArrayList<GameModel.Character1>(); // all characters in the game's values
	boolean blnShoot = false; // is shooting
	boolean blnSkill = false; // is attempting skil
	int intPlaying = 0; // what type of game are they playing (not playing = 0, is playing single = 1, is playing multi = 2)
	
	///CONTROLLER METHODS BELOW: BUTTON INTERACTIONS, CHARACTER MOVEMENTS, PROJECTILES
	//methods for ActionListener (BUTTON INTERACTIONS + SSM)
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == mainPanel.Play){ // play button
			frame.setContentPane(lobbyPanel); // bring to lobby setting
			frame.pack();
		}else if(evt.getSource() == mainPanel.Help){ // Help menu button
			frame.setContentPane(helpPanel); // bring to help screen
			frame.pack();			
		}else if(evt.getSource() == mainPanel.Quit){ // exit game button
			System.exit(0); //quits application 	
		}
		//returns to main menu screen
		else if(evt.getSource() == helpPanel.Return || evt.getSource() == lobbyPanel.Return || evt.getSource() == capPanel.Return || evt.getSource() == endPanel.Return){ 
			frame.setContentPane(mainPanel); // bring to main menu
			frame.pack();
		}else if(evt.getSource() == tutorialPanel.Return){
			mainPanel.Help.doClick();
			map.clear();
		}else if(evt.getSource() == lobbyPanel.createLobby){ // CREATE LOBBY
			//setting GUI elements for LobbyPanel + frame cursor 
			frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("timeCursor.png").getImage(), new Point(0,0),"time cursor"));
			lobbyPanel.joinLobby.setEnabled(false);
			lobbyPanel.enterIP.setEnabled(false);
			lobbyPanel.enterUsername.setEnabled(false);
			lobbyPanel.Return.setEnabled(false);
			lobbyPanel.countdownLabel.setVisible(true);
			charPanel.waitHost.setVisible(false);
			countdownTimer.start(); //countdown lobby creation
			ssm = new SuperSocketMaster(6112, this); // set up network info
			boolean blnConnect = ssm.connect(); // connect
			lobbyPanel.serverInfo.setText("IP: "+ssm.getMyAddress()); // visuals
			charPanel.serverIP.setText("IP: "+ssm.getMyAddress());
			blnServer = true; // is server
			c1.intID = 5; // default ID for server is 5 -- always first to join lobby.
			charPanel.chatArea.append(lobbyPanel.enterUsername.getText()+": created the lobby.\n"); // chat messages related to lobby creation.
			charPanel.chatArea.append((4-intPlayerTotal)+" spot(s) remaining.\n");
		}else if(evt.getSource() == lobbyPanel.joinLobby){ // JOINING LOBBY
			ssm = new SuperSocketMaster(lobbyPanel.enterIP.getText(), 6112, this); // connect to lobby
			boolean blnConnect = ssm.connect();
			if(blnConnect == true){ // if successful
				blnServer = false; // not server
				frame.setContentPane(charPanel); // to charPanel
				frame.pack();
				charPanel.serverIP.setText("IP: "+lobbyPanel.enterIP.getText()); 
				charPanel.startGame.setVisible(false); // lobby visual formatting, removing pointless buttons
				charPanel.lockIn.setVisible(false);
				String strConnect = "connect,"+lobbyPanel.enterUsername.getText(); // set network message notifying you conneted successfully.
				if(ssm != null){ // if ssm is not null send network message
					ssm.sendText(strConnect);
					//System.out.println(strConnect);
				}
				charPanel.chatArea.append(lobbyPanel.enterUsername.getText()+": joined the lobby.\n"); // chat stuff
				ssm.sendText("chat,"+lobbyPanel.enterUsername.getText()+","+"joined the lobby.\n"); 
			}else if(blnConnect == false){ // do noting if not successfl
				System.out.println("UNSUCCESSFUL CONNECTION."); 
			}
		}else if(evt.getSource() == helpPanel.Tutorial){ // tutorial button
			//resetVals();
			blnShootPrompt = true;
			blnSkillPrompt = true; 
			blnDonePrompt = true;
			frame.removeKeyListener(this);
			frame.removeMouseListener(this);
			frame.addKeyListener(this); // add actionlisteners
			frame.addMouseListener(this);
			frame.requestFocus();
			timer.start(); // game timer start
			intPlaying = 1; // single player
			frame.setContentPane(tutorialPanel); // tutorial game visuals panel
			frame.pack();
			tutorialPanel.projectiles = c1.projectiles; // data setting
			//resetting values of character
			c1.intX = 200;
			c1.intY = 200;
			c1.intHP = 100;
			c1.intAttack = 1;
			c1.intLives = 3;
			tutorialPanel.intCharType = 3;
			cT.intLives = 1;
			cT.intHP = 100;
			cT.intX = 330;
			cT.intY = 0;
			c1.projectiles.clear();
			loadMap("map1.csv"); // map info loading
			tutorialPanel.promptUser.setText("Press W, A, S, D to move.");
			frame.requestFocus();
		}else if(evt.getSource() == tutorialPanel.changeChamp){
			frame.requestFocus();
			c1.intCharType = tutorialPanel.intCharType + 1;
			c1.intX = 200;
			c1.intY = 200;
			c1.intLives = 3;
			c1.intHP = 100;
			//cycles champions
			if(c1.intCharType > 4){
				c1.intCharType = 1;
			} 
		}else if(evt.getSource() == charPanel.c1Button){ // character 1 select button
			charPanel.intCharType = 1; // setting values
			c1.intCharType = 1;		
			charPanel.readyUp.setEnabled(true); // able to ready up now
		}else if(evt.getSource() == charPanel.c2Button){ // character 2 select button
			charPanel.intCharType = 2; // setting vals
			c1.intCharType = 2;
			charPanel.readyUp.setEnabled(true); // able to ready up now
		}else if(evt.getSource() == charPanel.c3Button){ // character 3 select button
			charPanel.intCharType = 3; // setting vals
			c1.intCharType = 3;
			charPanel.readyUp.setEnabled(true); // able to ready up now
		}else if(evt.getSource() == charPanel.c4Button){ // character 4 select button
			charPanel.intCharType = 4; // setting vals
			c1.intCharType = 4;
			charPanel.readyUp.setEnabled(true); // able to ready up now
		}else if(evt.getSource() == charPanel.readyUp){ // ready up button
			charPanel.readyUp.setEnabled(false); 
			
			c1.strUser = lobbyPanel.enterUsername.getText(); // setting your username

			String strSelect = "select,"+c1.intID+","+c1.intCharType+","+c1.strUser; // setting network message up
			
			ssm.sendText(strSelect); // send network message
			// After lockIn checks selected character and disables selection of any other characters locally
			// Fades out all other character images that were not selected
			if(c1.intCharType == 1){
				charPanel.chatArea.append(lobbyPanel.enterUsername.getText()+" picked Flamel. \n"); 
				String strPicked = "picked Flamel.";
				ssm.sendText("chat,"+lobbyPanel.enterUsername.getText()+","+strPicked);
				charPanel.c1Button.removeActionListener(this);
				charPanel.c2Button.setEnabled(false);
				charPanel.c3Button.setEnabled(false);
				charPanel.c4Button.setEnabled(false);
				endPanel.strDraw = "Flamel";
			}else if(c1.intCharType == 2){
				charPanel.chatArea.append(lobbyPanel.enterUsername.getText()+" picked Bishop. \n"); 
				String strPicked = "picked Bishop.";
				ssm.sendText("chat,"+lobbyPanel.enterUsername.getText()+","+strPicked);
				charPanel.c2Button.removeActionListener(this);
				charPanel.c1Button.setEnabled(false);
				charPanel.c3Button.setEnabled(false);
				charPanel.c4Button.setEnabled(false);
				endPanel.strDraw = "Bishop";
			}else if(c1.intCharType == 3){
				charPanel.chatArea.append(lobbyPanel.enterUsername.getText()+" picked Magnus. \n"); 
				String strPicked = "picked Magnus.";
				ssm.sendText("chat,"+lobbyPanel.enterUsername.getText()+","+strPicked);
				charPanel.c3Button.removeActionListener(this);
				charPanel.c2Button.setEnabled(false);
				charPanel.c1Button.setEnabled(false);
				charPanel.c4Button.setEnabled(false);
				endPanel.strDraw = "Magnus";
			}else if(c1.intCharType == 4){
				charPanel.chatArea.append(lobbyPanel.enterUsername.getText()+" picked Shadow. \n"); 
				String strPicked = "picked Shadow.";
				ssm.sendText("chat,"+lobbyPanel.enterUsername.getText()+","+strPicked);
				charPanel.c4Button.removeActionListener(this);
				charPanel.c2Button.setEnabled(false);
				charPanel.c3Button.setEnabled(false);
				charPanel.c1Button.setEnabled(false);
				endPanel.strDraw = "Shadow";
			}
			charPanel.startGame.setEnabled(true); // only server sees
			addChar(c1.intID, c1.intCharType, c1.strUser); // adds character to char tracking array
		}else if(evt.getSource() == charPanel.startGame){ // START GAME func
			if(intPlayerCount == intStartCheck){ // if players locked in and playing the game is equal to toal players in the lobby
				//System.out.println(2);
				frame.removeKeyListener(this); // game formatting stuff
				frame.removeMouseListener(this);
				frame.addKeyListener(this); 
				frame.addMouseListener(this);
				frame.requestFocus();
				timer.start(); // start gameplay timer
				intPlaying = 2; // multi player game type
				frame.setContentPane(gamePanel);
				frame.pack();
				gamePanel.projectiles = c1.projectiles; // data setting
				intRandom = (int)(Math.random()*4)+1; // RANDOM MAP
				System.out.println("random numb: "+intRandom); // system check
				//based on the number generated, will load corresponding map in the map arraylisst
				if(intRandom == 1){
					System.out.println("Loading game map 1");
					String strMap = "gamemap1.csv";
					loadMap(strMap);
					ssm.sendText("starting,"+intPlayerTotal+","+strMap);
				}else if(intRandom == 2){
					System.out.println("Loading game map 2");
					String strMap = "gamemap2.csv";
					loadMap(strMap);
					ssm.sendText("starting,"+intPlayerTotal+","+strMap);
				}else if(intRandom == 3){
					System.out.println("Loading game map 3");
					String strMap = "gamemap3.csv";
					loadMap(strMap);
					ssm.sendText("starting,"+intPlayerTotal+","+strMap);
				}else if(intRandom == 4){
					System.out.println("Loading game map 4");
					String strMap = "gamemap4.csv";
					loadMap(strMap);
					ssm.sendText("starting,"+intPlayerTotal+","+strMap);
				}
				c1.spawn(); //spawns own player into map
				intPlayerTotal = 4; // locks the lobby
				blnIn = true; // local player is indeed not eliminated
			}
			c1.intLives = 3; // local player has 3 lives.
		}else if(evt.getSource() == charPanel.lockIn){ // lock in button
			if(intPlayerTotal == 1){
				charPanel.chatArea.append("You cannot play by yourself! \n");
			}else if(intPlayerTotal >= 2 && intPlayerTotal < 5){ 
				charPanel.lockIn.setEnabled(false);
				intStartCheck = intPlayerTotal;
				intPlayerTotal = 4;
				charPanel.c1Button.setEnabled(true);
				charPanel.c2Button.setEnabled(true);
				charPanel.c3Button.setEnabled(true);
				charPanel.c4Button.setEnabled(true);
				ssm.sendText("lock,1");
			}
		}else if(evt.getSource() == charPanel.chatMessage){
			charPanel.chatArea.append(lobbyPanel.enterUsername.getText()+": "+charPanel.chatMessage.getText()+"\n");
			ssm.sendText("chat,"+lobbyPanel.enterUsername.getText()+","+charPanel.chatMessage.getText());
		}else if(evt.getSource() == gamePanel.enterMessage){
			gamePanel.gameChat.append(lobbyPanel.enterUsername.getText()+": "+gamePanel.enterMessage.getText()+"\n");
			ssm.sendText("ingame_chat,"+lobbyPanel.enterUsername.getText()+","+gamePanel.enterMessage.getText());
			gamePanel.enterMessage.setEnabled(false);
			gamePanel.enterMessage.setEditable(false);
			frame.requestFocus();
		}
		
		//SSM STATEMENTS
		else if(evt.getSource() == ssm){
			String strParts[] = ssm.readText().split(",");
			
			// Message type: connect
			if(strParts[0].equals("connect")){
				if(blnServer){
					intPlayerTotal++;
					if(intPlayerTotal > 4){
						intPlayerTotal = 4;
						ssm.sendText("maxCap,"+1);
						System.out.println("max cap");
					}else{
						ssm.sendText("IDAssign,"+(4+intPlayerTotal));
						System.out.println(c1.intID);
						charPanel.chatArea.append((4-intPlayerTotal)+" spot(s) remaining.\n");
					}
				}
			}
			// Message type: max cap (doesnt work)
			if(strParts[0].equals("maxCap")){
				if(c1.intID < 2){
					ssm.disconnect();
					System.out.println("ME GONE");
					capPanel.labelTwo.setText("four players in a lobby. Better luck next time "+lobbyPanel.enterUsername.getText()+".");
					lobbyPanel.enterUsername.setText("E.g: DIABLOGAMER1337");
					lobbyPanel.enterIP.setText("Enter IP Address");
					frame.setContentPane(capPanel);
					frame.pack();			
				}
				System.out.println("Max Cap");
			}
			// Message type: lock
			if(strParts[0].equals("lock")){
				charPanel.c1Button.setEnabled(true);
				charPanel.c2Button.setEnabled(true);
				charPanel.c3Button.setEnabled(true);
				charPanel.c4Button.setEnabled(true);
			}
			// Message type: select
			if(strParts[0].equals("select")){
				if(blnServer){
					intPlayerCount++;
				}
				addChar(Integer.parseInt(strParts[1]), Integer.parseInt(strParts[2]), strParts[3]);
			}
			// Message type: lobby chat
			if(strParts[0].equals("chat")){
				charPanel.chatArea.append(strParts[1]+": "+strParts[2]+"\n");
			}
			//Mesage type: game chat
			if(strParts[0].equals("ingame_chat")){
				gamePanel.gameChat.append(strParts[1]+": "+strParts[2]+"\n");
			}
			// Message type: ID assignment
			if(strParts[0].equals("IDAssign")){
				System.out.println(Integer.parseInt(strParts[1]));
				if(c1.intID > 1){
					
				}else{
					c1.intID = Integer.parseInt(strParts[1]);
					System.out.println(c1.intID);
				}
			}
			// Message type: New Data
			if(strParts[0].equals("data")){ // update info
				for(int intCount = characters.size()-1; intCount >= 0; intCount--){
					if(characters.get(intCount).intID == Integer.parseInt(strParts[1])){
						characters.get(intCount).intX = Integer.parseInt(strParts[2]);
						characters.get(intCount).intY = Integer.parseInt(strParts[3]);
						characters.get(intCount).intHP = Integer.parseInt(strParts[4]);
						characters.get(intCount).intSkillTime = Integer.parseInt(strParts[5]);
					}
				}
			}
			// Message type: Shoot
			if(strParts[0].equals("shoot")){
				c1.shoot(Integer.parseInt(strParts[1]), Integer.parseInt(strParts[2]), Integer.parseInt(strParts[3]), Integer.parseInt(strParts[4]), Integer.parseInt(strParts[5]), Integer.parseInt(strParts[6]));
			}
			// Message type: remove proj
			if(strParts[0].equals("removeProj")){ 
				try{
					c1.projectiles.remove(Integer.parseInt(strParts[1]));
					System.out.println("Removed"+Integer.parseInt(strParts[1]));
				}catch(IndexOutOfBoundsException e){
					
				}
				
			}
			// Messaage type: Starto
			if(strParts[0].equals("starting")){
				frame.removeKeyListener(this); // game formatting stuff
				frame.removeMouseListener(this);
				frame.addKeyListener(this);
				frame.addMouseListener(this);
				frame.requestFocus();
				timer.start();
				intPlaying = 2;
				frame.setContentPane(gamePanel);
				frame.pack();
				gamePanel.projectiles = c1.projectiles;
				loadMap(strParts[2]);
				c1.intLives = 3;
				c1.spawn();
				blnIn = true;
				
			}
			// Messaage type: Skill
			if(strParts[0].equals("skill")){
				c1.skill(Integer.parseInt(strParts[2]), Integer.parseInt(strParts[1]),Integer.parseInt(strParts[3]), Integer.parseInt(strParts[4]));
			}
			// Message type: die
			if(strParts[0].equals("die")){
				for(int intCount = characters.size()-1; intCount >= 0; intCount--){
					if(characters.get(intCount).intID == Integer.parseInt(strParts[1])){
						characters.get(intCount).intLives = Integer.parseInt(strParts[2]);
					}
				}
			}
			// Message type: out
			if(strParts[0].equals("out")){
				if(blnServer){
					intPlayerCount--;
					if(intPlayerCount > 1){
						
					}else{
						ssm.sendText("gameDone,1");
						if(blnIn){
							ssm.sendText("winner,"+c1.intID);
							frame.setContentPane(endPanel);
							frame.pack();
							System.out.println("SOMETHING: "+c1.intID);
							resetVals();
							ssm.disconnect();
						}
					}
				}
			}
			
			// Message type; game done
			if(strParts[0].equals("gameDone")){
				if(blnIn){
					frame.setContentPane(endPanel);
					frame.pack();
					System.out.println("ACTUAL WINNER: "+c1.intID);
					endPanel.winner.setText(c1.strUser+"!");
					ssm.sendText("winner,"+c1.intID+","+c1.strUser);
					resetVals();
					ssm.disconnect();
				}
			}
			
			// Message type; winner!
			if(strParts[0].equals("winner")){
				frame.setContentPane(endPanel);
				frame.pack();
				System.out.println("YO, YOU LOST BUT THE WINNER IS: "+strParts[1]);
				endPanel.winner.setText(strParts[2]+"!");
				resetVals();
				ssm.disconnect();
			}
			
		}
		
		if(evt.getSource() == timer){
			if(intPlaying == 1){ //singleplayer
				shoot();
				blnSkill = false;
				c1.moveX();
				c1.moveY();
				collision();
				//character
				tutorialPanel.intX = c1.intX;
				tutorialPanel.intY = c1.intY;
				tutorialPanel.intSizeX = c1.intSizeX;
				tutorialPanel.intSizeY = c1.intSizeY;
				tutorialPanel.intSkillTime = c1.intSkillTime;
				tutorialPanel.intLives = c1.intLives;
				tutorialPanel.intCharType = c1.intCharType;
				//dummy
				tutorialPanel.intDummyHP = cT.intHP;
				tutorialPanel.intDummyX = cT.intX;
				tutorialPanel.intDummyY = cT.intY;
				
				c1.update();
				tutorialPanel.projectiles = c1.projectiles;
				tutorialPanel.intHP = c1.intHP;
				tutorialPanel.map = map;
				
				for(int intCount = c1.projectiles.size() -1; intCount > -1; intCount--){ // projectiles w/ dummy
					if(c1.projectiles.get(intCount).intX < cT.intX+cT.intSizeX && c1.projectiles.get(intCount).intY < cT.intY+cT.intSizeY && 
						(c1.projectiles.get(intCount).intSize+c1.projectiles.get(intCount).intX) > cT.intX && 
						(c1.projectiles.get(intCount).intSize+c1.projectiles.get(intCount).intY) > cT.intY){
						if(c1.projectiles.get(intCount).intID != cT.intID){
							cT.collision(c1.projectiles.get(intCount).intID, c1.projectiles.get(intCount).intDamage);
							System.out.println("count: "+c1.projectiles.get(intCount).intDamage);
							c1.projectiles.remove(intCount);
							System.out.println(intCount);
						}
					}
				}
				
				
				//detects if character is nearby to prompt dummy shooting
				if(tutorialPanel.intX > 150 && tutorialPanel.intX < 450 && tutorialPanel.intY < 200){
					if(intDummyCounter % 7 == 0){
						cT.shoot(63,0,4,10,cT.intX+10,cT.intY+20);
						cT.update();
						c1.projectiles.addAll(cT.projectiles);
						cT.projectiles.remove(0);
					}
					intDummyCounter++;	
				}
				
				
				if(blnShootPrompt){
					if(tutorialPanel.intX != 200 || tutorialPanel.intY != 200){ 
						tutorialPanel.promptUser.setText("Press left click to shoot.");
						blnShootPrompt = false;
					}
				}
				//checking death dummy
				if(cT.deathCheck()){
					if(cT.outCheck()){
						tutorialPanel.promptUser.setText("You are ready for the arena!");
					}
				}
				//tutorial spawn + checking death
				if(c1.deathCheck()){
					c1.intX = 200;
					c1.intY = 200;
					if(c1.outCheck()){
						c1.intLives = 3;
					}
				}
			}
			if(intPlaying == 2){
				if(!blnIn){
					c1.intX = -1000;
					c1.intY = -1000;
				}
				shoot(); // check if shoot command is issued.
				blnSkill = false;
				blnShoot = false;
				c1.moveX();
				c1.moveY();
				c1.update();
				collision();
				gamePanel.intX = c1.intX;
				gamePanel.intY = c1.intY;
				gamePanel.intSizeX = c1.intSizeX;
				gamePanel.intSizeY = c1.intSizeY;
				gamePanel.intSkillTime = c1.intSkillTime;
				gamePanel.projectiles = c1.projectiles;
				gamePanel.intHP = c1.intHP;
				gamePanel.map = map;
				if(c1.deathCheck()){//=true
					ssm.sendText("die,"+c1.intID+","+c1.intLives);
					if(c1.outCheck()){
						ssm.sendText("data"+"," + c1.intID+","+c1.intX+"," + c1.intY+","+ c1.intHP+","+c1.intSkillTime);
						ssm.sendText("out,"+c1.intID);
						blnIn = false;
						if(blnServer){
							intPlayerCount--;
							if(intPlayerCount > 1){
								
							}else{
								ssm.sendText("gameDone,1");
							}
						}
					}
					c1.spawn();
				}
				ssm.sendText("data"+"," + c1.intID+","+c1.intX+"," + c1.intY+","+ c1.intHP+","+c1.intSkillTime);
				for(int intCount = characters.size()-1; intCount >= 0; intCount--){
					if(characters.get(intCount).intID == c1.intID){
						characters.get(intCount).intX = c1.intX;
						characters.get(intCount).intY = c1.intY;
						characters.get(intCount).intHP = c1.intHP;
						characters.get(intCount).intSkillTime = c1.intSkillTime;
						characters.get(intCount).intLives = c1.intLives;
					}
				}
				gamePanel.characters = characters;
			}	
		}else if(evt.getSource() == countdownTimer){  
			intSecond--;
			lobbyPanel.countdownLabel.setText("Loading Lobby... "+ intSecond);
			if(intSecond == 0){
				countdownTimer.stop();
				frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("customCursor.png").getImage(), new Point(0,0),"custom cursor"));
				frame.setContentPane(charPanel);
				frame.pack();
			}
		}
	}
	
	//methods for KeyListener (CHARACTER MOVEMENT COMMANDS)
	public void keyReleased(KeyEvent evt){
		if(intPlaying == 1){	
			if(evt.getKeyChar() == 'w'){
				c1.up(0); 
			}else if(evt.getKeyChar() == 'a'){
				c1.left(0); 
			}else if(evt.getKeyChar() == 's'){
				c1.down(0);
			}else if(evt.getKeyChar() == 'd'){
				c1.right(0); 
			}
		}
		if(intPlaying == 2){	
			if(evt.getKeyChar() == 'w'){
				c1.up(0); 
			}else if(evt.getKeyChar() == 'a'){
				c1.left(0); 
			}else if(evt.getKeyChar() == 's'){
				c1.down(0);
			}else if(evt.getKeyChar() == 'd'){
				c1.right(0); 
			}
		}
	} 
	public void keyPressed(KeyEvent evt){
		if(intPlaying == 1){	
			if(evt.getKeyChar() == 'w'){
				c1.up(5); 
			}else if(evt.getKeyChar() == 'a'){
				c1.left(5); 
			}else if(evt.getKeyChar() == 's'){
				c1.down(5);
			}else if(evt.getKeyChar() == 'd'){
				c1.right(5);
			}
		}
		if(intPlaying == 2){	
			if(evt.getKeyChar() == 'w'){
				c1.up(5); 
			}else if(evt.getKeyChar() == 'a'){
				c1.left(5); 
			}else if(evt.getKeyChar() == 's'){
				c1.down(5);
			}else if(evt.getKeyChar() == 'd'){
				c1.right(5);
			}
		}
	}
	
	//methods for KeyListener (PROJECTILES)
	public void keyTyped(KeyEvent evt){
		if(evt.getKeyChar() == 'r'){
			blnSkill = true;
			if(blnDonePrompt == true && blnSkillPrompt != true && c1.intSkillTime == 100){
				tutorialPanel.promptUser.setText("Try killing the dummy!");
				blnDonePrompt = false;
			}
		}else if(evt.getKeyChar() == KeyEvent.VK_ENTER){
			gamePanel.enterMessage.setText("Press enter to type");
			gamePanel.enterMessage.setEnabled(true);
			gamePanel.enterMessage.setEditable(true);
		}
	}
	
	public void mouseExited(MouseEvent evt){
		
	}
	public void mouseReleased(MouseEvent evt){
		
	}
	public void mouseEntered(MouseEvent evt){
		
	}
	
	public void mouseClicked(MouseEvent evt){ // shooting
		if(intPlaying == 1){
			intX = evt.getX();
			intY = evt.getY();
			if(SwingUtilities.isLeftMouseButton(evt)){
				if(intX <= c1.intX && intY <= c1.intY){ // based on mouse location, will shoot in that location.
					c1.shoot(c1.intID,-4,-4,5, c1.intX, c1.intY);
					tutorialPanel.projectiles = c1.projectiles;
				}else if(intX <= c1.intX && intY >= c1.intY){
					c1.shoot(c1.intID,-4,4,5, c1.intX, c1.intY);
					tutorialPanel.projectiles = c1.projectiles;
				}else if(intX >= c1.intX && intY <= c1.intY){
					c1.shoot(c1.intID,4,-4,5, c1.intX, c1.intY);
					tutorialPanel.projectiles = c1.projectiles;
				}else if(intX >= c1.intX && intY >= c1.intY){
					c1.shoot(c1.intID,4,4,5, c1.intX, c1.intY);
					tutorialPanel.projectiles = c1.projectiles;
				}
			}
		}
		if(intPlaying == 2){ // network
			if(SwingUtilities.isLeftMouseButton(evt)){
				intX = evt.getX();
				intY = evt.getY();
				blnShoot = true;
			}
		}
	}
	public void mousePressed(MouseEvent evt){
		if(SwingUtilities.isLeftMouseButton(evt)){
			if(blnSkillPrompt == true && blnShootPrompt != true){
				tutorialPanel.promptUser.setText("Press R to use skill.");
				blnSkillPrompt = false;
			}
		}
	}
	
	//shoot check
	public void shoot(){
		if(blnSkill){
			if(c1.skill(c1.intID, c1.intCharType, c1.intX, c1.intY)){
				if(intPlaying == 2){
					ssm.sendText("skill,"+c1.intCharType+","+c1.intID+","+c1.intX+","+c1.intY);
				}
			}
		}
		if(intPlaying == 2){
			if(blnShoot){
				if(intX <= c1.intX && intY <= c1.intY){ // based on mouse location, will shoot in that location.
					c1.shoot(c1.intID,-4,-4,5, c1.intX, c1.intY);
					ssm.sendText("shoot,"+c1.intID+","+-4+","+-4+","+5+","+c1.intX+","+c1.intY);
					gamePanel.projectiles = c1.projectiles;
				}else if(intX <= c1.intX && intY >= c1.intY){
					c1.shoot(c1.intID,-4,4,5, c1.intX, c1.intY);
					ssm.sendText("shoot,"+c1.intID+","+-4+","+4+","+5+","+c1.intX+","+c1.intY);
					gamePanel.projectiles = c1.projectiles;
				}else if(intX >= c1.intX && intY <= c1.intY){
					c1.shoot(c1.intID,4,-4,5, c1.intX, c1.intY);
					ssm.sendText("shoot,"+c1.intID+","+4+","+-4+","+5+","+c1.intX+","+c1.intY);
					gamePanel.projectiles = c1.projectiles;
				}else if(intX >= c1.intX && intY >= c1.intY){
					c1.shoot(c1.intID,4,4,5, c1.intX, c1.intY);
					ssm.sendText("shoot,"+c1.intID+","+4+","+4+","+5+","+c1.intX+","+c1.intY);
					gamePanel.projectiles = c1.projectiles;
				}
			}
		}
	}
	
	//read CSV
	public void loadMap(String strCSV){ // temporary -- for tutorial
		frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("aimCursor.png").getImage(), new Point(0,0),"aim cursor")); //custom aim cursor
		try{
			//reading CSV file
			BufferedReader map1 = new BufferedReader(new FileReader(strCSV));
			
			int intCol;
			int intRow;
			String strRead;
			String strSplit[];
			String[][] mapData = new String[484][5];
			for(intRow = 0; intRow < 484; intRow++){ // reading the map and loading it in
				strRead = map1.readLine();
				strSplit = strRead.split(",");
				for(intCol = 0; intCol < 5; intCol++){
					mapData[intRow][intCol] = strSplit[intCol];
					tutorialPanel.mapData[intRow][intCol] = mapData[intRow][intCol]; //loading tutorial map
				}
				for(intCol = 0; intCol < 5; intCol++){
					mapData[intRow][intCol] = strSplit[intCol];
					gamePanel.mapData[intRow][intCol] = mapData[intRow][intCol]; //loading game map (randomized)
				}
			}
			for(int intCount = 0; intCount < 484; intCount++){ // detecting terrain
				if(mapData[intCount][2].equals("water")){	
					map.add(new GameModel().new Terrain1(Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), 50));
				}else if(mapData[intCount][2].equals("dummy")){
					map.add(new GameModel().new Terrain1(Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), 63)); // dummy
				}else if(mapData[intCount][2].equals("tree")){
					map.add(new GameModel().new Terrain1(Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), 100));
				}else if(mapData[intCount][2].equals("statue")){
					map.add(new GameModel().new Terrain1(Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), 100));
				}else if(mapData[intCount][2].equals("building")){
					map.add(new GameModel().new Terrain1(Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), 100));
				}else if(mapData[intCount][2].equals("grass")){
					
				}else if(mapData[intCount][2].equals("path")){
					
				}else if(mapData[intCount][2].equals("bridge")){
					
				}else if(mapData[intCount][2].equals("statuex")){
					map.add(new GameModel().new Terrain1(Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), 100));
				}else if(mapData[intCount][2].equals("bones")){
					map.add(new GameModel().new Terrain1(Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), 100));
				}else if(mapData[intCount][2].equals("lava")){
					map.add(new GameModel().new Terrain1(Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), 50));
				}else if(mapData[intCount][2].equals("rock")){
					map.add(new GameModel().new Terrain1(Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), 100));
				}else if(mapData[intCount][2].equals("volcano")){
					map.add(new GameModel().new Terrain1(Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), 100));
				}else if(mapData[intCount][2].equals("volcanot")){
					map.add(new GameModel().new Terrain1(Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), 100));
				}else if(mapData[intCount][2].equals("grasspath")){
				
				}else if(mapData[intCount][2].equals("torch")){
					map.add(new GameModel().new Terrain1(Integer.parseInt(mapData[intCount][0]), Integer.parseInt(mapData[intCount][1]), Integer.parseInt(mapData[intCount][3]), Integer.parseInt(mapData[intCount][4]), 100));
				}
			}
			
		}catch(IOException e){
			e.toString();
		}
	}
	
	public void collision(){//Collision detection
		for(int intCount = c1.projectiles.size() -1; intCount > -1; intCount--){ // projectiles w/ player
			if(c1.projectiles.get(intCount).intX < c1.intX+c1.intSizeX && c1.projectiles.get(intCount).intY < c1.intY+c1.intSizeY && 
			(c1.projectiles.get(intCount).intSize+c1.projectiles.get(intCount).intX) > c1.intX && 
			(c1.projectiles.get(intCount).intSize+c1.projectiles.get(intCount).intY) > c1.intY){
				if(c1.projectiles.get(intCount).intID != c1.intID){
					c1.collision(c1.projectiles.get(intCount).intID, c1.projectiles.get(intCount).intDamage);
					c1.projectiles.remove(intCount);
					if(intPlaying == 2){
						ssm.sendText("removeProj,"+intCount);		
					}
				}
			}
		}
		for(int intCount = map.size() -1; intCount > -1; intCount--){ // terrain
			if(map.get(intCount).intX < c1.intX+c1.intSizeX && map.get(intCount).intY < c1.intY+c1.intSizeY && 
			(map.get(intCount).intSizeX+map.get(intCount).intX) > c1.intX && 
			(map.get(intCount).intSizeY+map.get(intCount).intY) > c1.intY){
				c1.collision(map.get(intCount).intID, 0);
				//System.out.println(1);
			}
			for(int intCount2 = c1.projectiles.size() -1; intCount2 > -1; intCount2--){ // projectiles w/ terrain
				if(c1.projectiles.get(intCount2).intX < map.get(intCount).intX+map.get(intCount).intSizeX && c1.projectiles.get(intCount2).intY < map.get(intCount).intSizeY+map.get(intCount).intY && 
				(c1.projectiles.get(intCount2).intSize+c1.projectiles.get(intCount2).intX) > map.get(intCount).intX && 
				(c1.projectiles.get(intCount2).intSize+c1.projectiles.get(intCount2).intY) > map.get(intCount).intY){
					if(map.get(intCount).intID == 100){
						c1.projectiles.remove(intCount2);
					}else if(map.get(intCount).intID != 50 && map.get(intCount).intID != c1.projectiles.get(intCount2).intID){
						c1.projectiles.remove(intCount2);
					}
				}
			}
		}
	}
	
	public void addChar(int intID, int intCharType, String strUser){ // will modify later.
		if(intCharType == 1){
			characters.add(new GameModel().new Character1(intID, 200, 200, 100, 2, 0, 0, 1, strUser));
		}else if(intCharType == 2){
			characters.add(new GameModel().new Character1(intID, 200, 200, 100, 2, 0, 0, 2, strUser));
		}else if(intCharType == 3){
			characters.add(new GameModel().new Character1(intID, 200, 200, 100, 2, 0, 0, 3, strUser));
		}else if(intCharType == 4){
			characters.add(new GameModel().new Character1(intID, 200, 200, 100, 2, 0, 0, 4, strUser));
		}
			
	}
	
	public void updateChars(){
		
	}
	
	//resetting all values that were changed upon interacting/playing once everybody disconnects in end panel screen
	public void resetVals(){
		frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("customCursor.png").getImage(), new Point(0,0),"custom cursor"));
		intPlaying = 0;
		intSecond = 5;
		intRandom = 1;
		blnServer = false;
		intPlayerCount = 1;
		intPlayerTotal = 1;
		intStartCheck = 0;
		intX = 0;
		intY = 0;
		blnIn = false;
		c1 = new GameModel().new Character1(1, 200, 200, 100, 1, 0, 0, 1, "");
		map.clear();
		characters.clear();
		c1.projectiles.clear();
		blnShoot = false;
		blnSkill = false;
		
		lobbyPanel.countdownLabel.setText("Loading Lobby... 5");
		lobbyPanel.enterUsername.setText("E.g: DIABLOGAMER1337");
		lobbyPanel.enterIP.setText("Enter IP Address");
		lobbyPanel.joinLobby.setEnabled(true);
		lobbyPanel.enterIP.setEnabled(true);
		lobbyPanel.enterUsername.setEnabled(true);
		lobbyPanel.Return.setEnabled(true);
		lobbyPanel.countdownLabel.setVisible(false);
		
		charPanel.chatArea.setText("");
		charPanel.chatMessage.setText("Message");
		charPanel.intCharType = 0; //reset yellow champion "hover"
		charPanel.readyUp.setEnabled(false);
		charPanel.c1Button.setEnabled(false);
		charPanel.c2Button.setEnabled(false);
		charPanel.c3Button.setEnabled(false);
		charPanel.c4Button.setEnabled(false);
		charPanel.lockIn.setEnabled(true);
		charPanel.waitHost.setVisible(true);
		charPanel.startGame.setVisible(true);
		charPanel.lockIn.setVisible(true);
		charPanel.c1Button.addActionListener(this);
		charPanel.c2Button.addActionListener(this);
		charPanel.c3Button.addActionListener(this);
		charPanel.c4Button.addActionListener(this);
		timer.stop();
		
	}
	
	///constructor
	public GameController(){
		//frame
		frame.setPreferredSize(new Dimension(1280, 720));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		//MainPanel
		mainPanel.Play.addActionListener(this);
		mainPanel.Help.addActionListener(this);
		mainPanel.Quit.addActionListener(this);
		frame.setContentPane(mainPanel);
		frame.pack();
		
		//HelpPanel
		helpPanel.Return.addActionListener(this);
		helpPanel.Tutorial.addActionListener(this);
	
		//tutorialPanel
		tutorialPanel.changeChamp.addActionListener(this);
		tutorialPanel.Return.addActionListener(this);
		
		//LobbyPanel
		lobbyPanel.Return.addActionListener(this);
		lobbyPanel.joinLobby.addActionListener(this);
		lobbyPanel.createLobby.addActionListener(this);
		
		//charPanel
		charPanel.startGame.addActionListener(this);
		charPanel.readyUp.addActionListener(this);
		charPanel.c1Button.addActionListener(this);
		charPanel.c2Button.addActionListener(this);
		charPanel.c3Button.addActionListener(this);
		charPanel.c4Button.addActionListener(this);
		charPanel.chatMessage.addActionListener(this);
		charPanel.lockIn.addActionListener(this);
			
		//gamePanel
		gamePanel.enterMessage.addActionListener(this); 
		
		//capPanel
		capPanel.Return.addActionListener(this);
		
		//endPanel
		endPanel.Return.addActionListener(this);
		
		//cursor
		frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("customCursor.png").getImage(), new Point(0,0),"custom cursor"));
	} 
	
	///main method
	public static void main(String[] args){
		new GameController();
	} 
}
