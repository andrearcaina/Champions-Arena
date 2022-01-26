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
	JFrame frame = new JFrame("Champions Arena Test 1"); //one frame only
	Timer timer = new Timer(1000/60, this);
	Timer countdownTimer = new Timer(1000, this);
	int intSecond = 5;
	int intRandom;
	SuperSocketMaster ssm;
	boolean blnServer;
	int intPlayerCount = 1;
	int intPlayerTotal = 1;
	int intStartCheck = 0;
	int intX = 0;
	int intY = 0;
	boolean blnIn = false;
	///GAME VIEW INCORPORATED: ANIMATED JPANELS
	//general panels: UX1 - UX3
	MPanel mainPanel = new MPanel(); //main panel
	HPanel helpPanel = new HPanel(); //help panel
	TPanel tutorialPanel = new TPanel(); //interactive tutorial
	
	//once you press play panels: UX4 - UX8
	LPanel lobbyPanel = new LPanel(); //lobby creation/entering lobby + usernames
	CPanel charPanel = new CPanel(); //character selection panel
	GPanel gamePanel = new GPanel(); //actual gameplay panel
	
	//leaderboard/win/lost/end panel: UX9
	EPanel endPanel = new EPanel(); //once game ends panel / leaderboards
	
	//sorry/cap limit panel: UX10
	SPanel capPanel = new SPanel(); //for users trying to join even though there is a lobby with 4 people or a game already has started
	
	//Accessing the Character object in the GameModel class
	// tutorial character
	GameModel.Character1 c1 = new GameModel().new Character1(1, 200, 200, 100, 1, 0, 0, 1);
	// dummy model
	GameModel.Character1 cT = new GameModel().new Character1(63, 345, 0, 100, 1, 0, 0, 1); 
	ArrayList<GameModel.Terrain1> map = new ArrayList<GameModel.Terrain1>();
	ArrayList<GameModel.Character1> characters = new ArrayList<GameModel.Character1>();
	boolean blnShoot = false;
	boolean blnSkill = false;
	int intPlaying = 0;
	
	///CONTROLLER METHODS BELOW: BUTTON INTERACTIONS, CHARACTER MOVEMENTS, PROJECTILES
	//methods for ActionListener (BUTTON INTERACTIONS)
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == mainPanel.Play){
			frame.setContentPane(lobbyPanel);
			frame.pack();
		}else if(evt.getSource() == mainPanel.Help){
			frame.setContentPane(helpPanel);
			frame.pack();			
		}else if(evt.getSource() == mainPanel.Quit){
			System.exit(0); //quits application 
		}else if(evt.getSource() == helpPanel.Return){ 
			frame.setContentPane(mainPanel);
			frame.pack();
		}else if(evt.getSource() == lobbyPanel.Return){
			frame.setContentPane(mainPanel);
			frame.pack();
		}else if(evt.getSource() == capPanel.Return){
			frame.setContentPane(mainPanel);
			frame.pack();
		}else if(evt.getSource() == endPanel.Return){
			frame.setContentPane(mainPanel);
			frame.pack();
		}else if(evt.getSource() == lobbyPanel.createLobby){ // CREATE LOBBY
			lobbyPanel.joinLobby.setEnabled(false);
			lobbyPanel.enterIP.setEnabled(false);
			lobbyPanel.enterUsername.setEnabled(false);
			lobbyPanel.Return.setEnabled(false);
			frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("timeCursor.png").getImage(), new Point(0,0),"time cursor"));
			lobbyPanel.countdownLabel.setVisible(true);
			charPanel.waitHost.setVisible(false);
			//^^^ Lobby buttons
			countdownTimer.start();
			ssm = new SuperSocketMaster(6112, this);
			boolean blnConnect = ssm.connect();
			lobbyPanel.serverInfo.setText("IP: "+ssm.getMyAddress());
			charPanel.serverIP.setText("IP: "+ssm.getMyAddress());
			blnServer = true;
			c1.intID = 5;
			charPanel.chatArea.append(lobbyPanel.enterUsername.getText()+": created the lobby.\n");
			charPanel.chatArea.append((4-intPlayerTotal)+" spot(s) remaining.\n");
		}else if(evt.getSource() == lobbyPanel.joinLobby){ // JOINING LOBBY
			ssm = new SuperSocketMaster(lobbyPanel.enterIP.getText(), 6112, this);
			boolean blnConnect = ssm.connect();
			if(blnConnect == true){
				blnServer = false;
				frame.setContentPane(charPanel);
				frame.pack();
				charPanel.serverIP.setText("IP: "+lobbyPanel.enterIP.getText());
				charPanel.startGame.setVisible(false);
				charPanel.lockIn.setVisible(false);
				String strConnect = "connect,"+lobbyPanel.enterUsername.getText();
				if(ssm != null){
					ssm.sendText(strConnect);
					//System.out.println(strConnect);
				}
				charPanel.chatArea.append(lobbyPanel.enterUsername.getText()+": joined the lobby.\n");
				ssm.sendText("chat,"+lobbyPanel.enterUsername.getText()+","+"joined the lobby.\n"); 
			}else if(blnConnect == false){
				
			}
			
		}else if(evt.getSource() == helpPanel.Tutorial){
			frame.addKeyListener(this);
			frame.addMouseListener(this);
			frame.requestFocus();
			timer.start();
			intPlaying = 1;
			frame.setContentPane(tutorialPanel);
			frame.pack();
			tutorialPanel.projectiles = c1.projectiles;
			loadMap("map1.csv");
			frame.requestFocus();
		}else if(evt.getSource() == charPanel.c1Button){
			charPanel.intCharType = 1;
			c1.intCharType = 1;		
			charPanel.readyUp.setEnabled(true);
		}else if(evt.getSource() == charPanel.c2Button){
			charPanel.intCharType = 2;
			c1.intCharType = 2;
			charPanel.readyUp.setEnabled(true);
		}else if(evt.getSource() == charPanel.c3Button){
			charPanel.intCharType = 3;
			c1.intCharType = 3;
			charPanel.readyUp.setEnabled(true);
		}else if(evt.getSource() == charPanel.c4Button){
			charPanel.intCharType = 4;
			c1.intCharType = 4;
			charPanel.readyUp.setEnabled(true);
		}else if(evt.getSource() == charPanel.readyUp){
			charPanel.readyUp.setEnabled(false);
			String strSelect = "select,"+c1.intID+","+c1.intCharType;
			ssm.sendText(strSelect);
			// After lockIn checks selected character and disables selection of any characters
			// Fades out all other character images that were not selected
			if(c1.intCharType == 1){
				charPanel.chatArea.append(lobbyPanel.enterUsername.getText()+" picked Flamel. \n"); 
				String strPicked = "picked Flamel.";
				ssm.sendText("chat,"+lobbyPanel.enterUsername.getText()+","+strPicked);
				charPanel.c1Button.removeActionListener(this);
				charPanel.c2Button.setEnabled(false);
				charPanel.c3Button.setEnabled(false);
				charPanel.c4Button.setEnabled(false);
			}else if(c1.intCharType == 2){
				charPanel.chatArea.append(lobbyPanel.enterUsername.getText()+" picked Bishop. \n"); 
				String strPicked = "picked Bishop.";
				ssm.sendText("chat,"+lobbyPanel.enterUsername.getText()+","+strPicked);
				charPanel.c2Button.removeActionListener(this);
				charPanel.c1Button.setEnabled(false);
				charPanel.c3Button.setEnabled(false);
				charPanel.c4Button.setEnabled(false);
			}else if(c1.intCharType == 3){
				charPanel.chatArea.append(lobbyPanel.enterUsername.getText()+" picked Magnus. \n"); 
				String strPicked = "picked Magnus.";
				ssm.sendText("chat,"+lobbyPanel.enterUsername.getText()+","+strPicked);
				charPanel.c3Button.removeActionListener(this);
				charPanel.c2Button.setEnabled(false);
				charPanel.c1Button.setEnabled(false);
				charPanel.c4Button.setEnabled(false);
			}else if(c1.intCharType == 4){
				charPanel.chatArea.append(lobbyPanel.enterUsername.getText()+" picked Shadow. \n"); 
				String strPicked = "picked Shadow.";
				ssm.sendText("chat,"+lobbyPanel.enterUsername.getText()+","+strPicked);
				charPanel.c4Button.removeActionListener(this);
				charPanel.c2Button.setEnabled(false);
				charPanel.c3Button.setEnabled(false);
				charPanel.c1Button.setEnabled(false);
			}
			charPanel.startGame.setEnabled(true);
			addChar(c1.intID, c1.intCharType);
		}else if(evt.getSource() == charPanel.startGame){ // START GAME
			System.out.println(intPlayerTotal);
			System.out.println(intPlayerCount);
			if(intPlayerCount == intStartCheck){
				System.out.println(2);
				frame.addKeyListener(this);
				frame.addMouseListener(this);
				frame.requestFocus();
				timer.start();
				intPlaying = 2;
				frame.setContentPane(gamePanel);
				frame.pack();
				gamePanel.projectiles = c1.projectiles;
				intRandom = (int)(Math.random()*3)+1;
				System.out.println("random numb: "+intRandom);
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
				}
				c1.spawn();
				intPlayerTotal = 4;
				blnIn = true;
			}
			c1.intLives = 3;
		}else if(evt.getSource() == charPanel.lockIn){
			charPanel.lockIn.setEnabled(false);
			intStartCheck = intPlayerTotal;
			intPlayerTotal = 4;
			charPanel.c1Button.setEnabled(true);
			charPanel.c2Button.setEnabled(true);
			charPanel.c3Button.setEnabled(true);
			charPanel.c4Button.setEnabled(true);
			ssm.sendText("lock,1");
		}else if(evt.getSource() == charPanel.chatMessage){
			charPanel.chatArea.append(lobbyPanel.enterUsername.getText()+": "+charPanel.chatMessage.getText()+"\n");
			ssm.sendText("chat,"+lobbyPanel.enterUsername.getText()+","+charPanel.chatMessage.getText());
		}else if(evt.getSource() == ssm){
			//String testssm = ssm.readText();
			//System.out.println(testssm);
			String strParts[] = ssm.readText().split(",");
			
			// Message type: connect
			if(strParts[0].equals("connect")){
				// textchat.append(strParts[1]+" has joined. \n");
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
				addChar(Integer.parseInt(strParts[1]), Integer.parseInt(strParts[2]));
			}
			// Message type: lobby chat
			if(strParts[0].equals("chat")){
				charPanel.chatArea.append(strParts[1]+": "+strParts[2]+"\n");
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
							System.out.println(c1.intID);
							resetVals();
							ssm.disconnect();
						}
					}
				}
			}
			
			// Message type; game done
			if(strParts[0].equals("gameDone")){
				if(blnIn){
					ssm.sendText("winner,"+c1.intID);
					frame.setContentPane(endPanel);
					frame.pack();
					System.out.println(c1.intID);
					resetVals();
					ssm.disconnect();
				}
			}
			
			// Message type; winner!
			if(strParts[0].equals("winner")){
				frame.setContentPane(endPanel);
				frame.pack();
				System.out.println(strParts[1]);
				resetVals();
				ssm.disconnect();
			}
			
		}
		
		if(evt.getSource() == timer){
			if(intPlaying == 1){
				shoot();
				blnSkill = false;
				c1.moveX();
				c1.moveY();
				collision();
				tutorialPanel.intX = c1.intX;
				tutorialPanel.intY = c1.intY;
				tutorialPanel.intSizeX = c1.intSizeX;
				tutorialPanel.intSizeY = c1.intSizeY;
				tutorialPanel.intSkillTime = c1.intSkillTime;
				c1.update();
				tutorialPanel.projectiles = c1.projectiles;
				tutorialPanel.intHP = c1.intHP;
				tutorialPanel.map = map;	
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
		if(evt.getKeyChar() == 'l'){
			if(intPlaying == 1){
				cT.shoot(63,0,4,10,cT.intX,cT.intY);
				cT.update();
				c1.projectiles.addAll(cT.projectiles);
				cT.projectiles.remove(0);
			}
		}
		if(evt.getKeyChar() == 'r'){
			blnSkill = true;
			//c1.update();
	
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
		if(intPlaying == 2){ // network
			intX = evt.getX();
			intY = evt.getY();
			blnShoot = true;
		}
	}
	public void mousePressed(MouseEvent evt){
			
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
	
	public void addChar(int intID, int intCharType){ // will modify later.
		if(intCharType == 1){
			characters.add(new GameModel().new Character1(intID, 200, 200, 100, 2, 0, 0, 1));
		}else if(intCharType == 2){
			characters.add(new GameModel().new Character1(intID, 200, 200, 100, 2, 0, 0, 2));
		}else if(intCharType == 3){
			characters.add(new GameModel().new Character1(intID, 200, 200, 100, 2, 0, 0, 3));
		}else if(intCharType == 4){
			characters.add(new GameModel().new Character1(intID, 200, 200, 100, 2, 0, 0, 4));
		}
			
	}
	
	public void updateChars(){
		
	}
	
	public void resetVals(){
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
		c1 = new GameModel().new Character1(1, 200, 200, 100, 1, 0, 0, 1);
		map.clear();
		characters.clear();
		c1.projectiles.clear();
		blnShoot = false;
		blnSkill = false;
		lobbyPanel.joinLobby.setEnabled(true);
		lobbyPanel.enterIP.setEnabled(true);
		lobbyPanel.enterUsername.setEnabled(true);
		lobbyPanel.Return.setEnabled(true);
		charPanel.readyUp.setEnabled(true);
		charPanel.c1Button.setEnabled(false);
		charPanel.c2Button.setEnabled(false);
		charPanel.c3Button.setEnabled(false);
		charPanel.c4Button.setEnabled(false);
		charPanel.lockIn.setEnabled(true);
		charPanel.waitHost.setVisible(true);
		charPanel.startGame.setVisible(true);
		charPanel.lockIn.setVisible(true);
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
				
		//tutorialPanel
		
		
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
