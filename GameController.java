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
	SuperSocketMaster ssm;
	boolean blnServer;
	int intPlayerCount = 1;
	int intPlayerTotal = 1;
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
	///EPanel endPanel = new EPanel(); //once game ends panel / leaderboards
	
	//cap limit panel: UX10
	///CPanel capPanel = new CPanel(); //for users trying to join even though there is a lobby with 4 people or a game already has started
	
	//Accessing the Character object in the GameModel class
	// tutorial character
	GameModel.Character1 c1 = new GameModel().new Character1(1, 200, 200, 100, 1, 0, 0, 1);
	// dummy model
	GameModel.Character1 cT = new GameModel().new Character1(63, 345, 0, 100, 1, 0, 0, 1); 
	ArrayList<GameModel.Terrain1> map = new ArrayList<GameModel.Terrain1>();
	ArrayList<GameModel.Character1> characters = new ArrayList<GameModel.Character1>();
	
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
		}else if(evt.getSource() == lobbyPanel.createLobby){ // CREATE LOBBY
			lobbyPanel.joinLobby.setEnabled(false);
			lobbyPanel.enterIP.setEnabled(false);
			lobbyPanel.enterUsername.setEnabled(false);
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
		}else if(evt.getSource() == lobbyPanel.joinLobby){ // JOINING LOBBY
			ssm = new SuperSocketMaster(lobbyPanel.enterIP.getText(), 6112, this);
			boolean blnConnect = ssm.connect();
			if(blnConnect == true){
				blnServer = false;
				frame.setContentPane(charPanel);
				frame.pack();
				charPanel.serverIP.setText("IP: "+lobbyPanel.enterIP.getText());
				charPanel.startGame.setVisible(false);
				String strConnect = "connect,"+lobbyPanel.enterUsername.getText();
				if(ssm != null){
					ssm.sendText(strConnect);
					//System.out.println(strConnect);
				}
			}else if(blnConnect == false){
				
			}
			
		}else if(evt.getSource() == helpPanel.Tutorial){
			frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("aimCursor.png").getImage(), new Point(0,0),"aim cursor"));
			frame.addKeyListener(this);
			frame.addMouseListener(this);
			frame.requestFocus();
			timer.start();
			intPlaying = 1;
			frame.setContentPane(tutorialPanel);
			frame.pack();
			tutorialPanel.projectiles = c1.projectiles;
			loadMap();
			frame.requestFocus();
		}else if(evt.getSource() == charPanel.c1Button){
			charPanel.intCharType = 1;
			c1.intCharType = 1;
		}else if(evt.getSource() == charPanel.c2Button){
			charPanel.intCharType = 2;
			c1.intCharType = 2;
		}else if(evt.getSource() == charPanel.c3Button){
			charPanel.intCharType = 3;
			c1.intCharType = 3;
		}else if(evt.getSource() == charPanel.c4Button){
			charPanel.intCharType = 4;
			c1.intCharType = 4;
		}else if(evt.getSource() == charPanel.readyUp){
			charPanel.readyUp.setEnabled(false);
			String strSelect = "select,"+c1.intID+","+c1.intCharType;
			ssm.sendText(strSelect);
			System.out.println(3);
			addChar(c1.intID, c1.intCharType);
		}else if(evt.getSource() == charPanel.startGame){ // START GAME
			System.out.println(intPlayerTotal);
			System.out.println(intPlayerCount);
			if(intPlayerCount == intPlayerTotal){
				System.out.println(2);
				frame.addKeyListener(this);
				frame.addMouseListener(this);
				frame.requestFocus();
				timer.start();
				intPlaying = 2;
				frame.setContentPane(gamePanel);
				frame.pack();
				gamePanel.projectiles = c1.projectiles;
				loadMap();
				ssm.sendText("starting,"+intPlayerTotal);
			}
		}else if(evt.getSource() == ssm){
			//String testssm = ssm.readText();
			//System.out.println(testssm);
			String strParts[] = ssm.readText().split(",");
			
			// Message type: connect
			if(strParts[0].equals("connect")){
				// textchat.append(strParts[1]+" has joined. \n");
				if(blnServer){
					intPlayerTotal++;
					ssm.sendText("IDAssign,"+(4+intPlayerTotal));
					System.out.println(c1.intID);
				}
			}
			// Message type: select
			if(strParts[0].equals("select")){
				if(blnServer){
					intPlayerCount++;
				}
				addChar(Integer.parseInt(strParts[1]), Integer.parseInt(strParts[2]));
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
					}
				}
			}
			// Message type: Shoot
			if(strParts[0].equals("shoot")){
				c1.shoot(Integer.parseInt(strParts[1]), Integer.parseInt(strParts[2]), Integer.parseInt(strParts[3]), Integer.parseInt(strParts[4]), Integer.parseInt(strParts[5]), Integer.parseInt(strParts[6]));
			}
			// Message type: remove proj
			if(strParts[0].equals("removeProj")){ 
				c1.projectiles.remove(Integer.parseInt(strParts[1]));
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
				loadMap();
			}
			// Messaage type: Skill
			if(strParts[0].equals("skill")){
				c1.skill(Integer.parseInt(strParts[2]), Integer.parseInt(strParts[1]),Integer.parseInt(strParts[3]), Integer.parseInt(strParts[4]));
			}
			
		}
		
		if(evt.getSource() == timer){
			if(intPlaying == 1){
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
				c1.moveX();
				c1.moveY();
				collision();
				gamePanel.intX = c1.intX;
				gamePanel.intY = c1.intY;
				gamePanel.intSizeX = c1.intSizeX;
				gamePanel.intSizeY = c1.intSizeY;
				gamePanel.intSkillTime = c1.intSkillTime;
				c1.update();
				gamePanel.projectiles = c1.projectiles;
				gamePanel.intHP = c1.intHP;
				gamePanel.map = map;
				ssm.sendText("data"+"," + c1.intID+","+c1.intX+"," + c1.intY+","+ c1.intHP);
				for(int intCount = characters.size()-1; intCount >= 0; intCount--){
					if(characters.get(intCount).intID == c1.intID){
						characters.get(intCount).intX = c1.intX;
						characters.get(intCount).intY = c1.intY;
						characters.get(intCount).intHP = c1.intHP;
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
			cT.shoot(99,0,4,10,cT.intX,cT.intY);
			cT.update();
			c1.projectiles.addAll(cT.projectiles);
			cT.projectiles.remove(0);
		}
		if(evt.getKeyChar() == 'r'){
			if(c1.skill(c1.intID, c1.intCharType, c1.intX, c1.intY)){
				if(intPlaying == 2){
					ssm.sendText("skill,"+c1.intCharType+","+c1.intID+","+c1.intX+","+c1.intY);
				}
			}
			c1.update();
	
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
			int intX = evt.getX();
			int intY = evt.getY();
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
			int intX = evt.getX();
			int intY = evt.getY();
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
	public void mousePressed(MouseEvent evt){
			
	}
	
	//read CSV
	public void loadMap(){ // temporary -- for tutorial
		try{
			//reading CSV file
			BufferedReader map1 = new BufferedReader(new FileReader("map1.csv"));
			int intCol;
			int intRow;
			String strRead;
			String strSplit[];
			String[][] mapData = new String[484][5];
			for(intRow = 0; intRow < 484; intRow++){
				strRead = map1.readLine();
				strSplit = strRead.split(",");
				for(intCol = 0; intCol < 5; intCol++){
					mapData[intRow][intCol] = strSplit[intCol];
					tutorialPanel.mapData[intRow][intCol] = mapData[intRow][intCol];
				}
				for(intCol = 0; intCol < 5; intCol++){
					mapData[intRow][intCol] = strSplit[intCol];
					gamePanel.mapData[intRow][intCol] = mapData[intRow][intCol];
				}
			}
			for(int intCount = 0; intCount < 484; intCount++){
				//map.add(new GameModel().new Terrain1(500, 300, 50, 50)); <-- Format to add new Terrain. Position, Size, ID number (type of terrain)
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
				}
			}
			
		}catch(IOException e){
			e.toString();
		}
	}
	
	public void collision(){//Collision detection
		for(int intCount = c1.projectiles.size() -1; intCount >= 0; intCount--){ // projectiles w/ player
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
		for(int intCount = map.size() -1; intCount >= 0; intCount--){ // terrain
			if(map.get(intCount).intX < c1.intX+c1.intSizeX && map.get(intCount).intY < c1.intY+c1.intSizeY && 
			(map.get(intCount).intSizeX+map.get(intCount).intX) > c1.intX && 
			(map.get(intCount).intSizeY+map.get(intCount).intY) > c1.intY){
				c1.collision(map.get(intCount).intID, 0);
				//System.out.println(1);
			}
			for(int intCount2 = c1.projectiles.size() -1; intCount2 >= 0; intCount2--){ // projectiles w/ terrain
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
			characters.add(new GameModel().new Character1(intID, 200, 200, 100, 2, 0, 0, 1));
		}else if(intCharType == 3){
			characters.add(new GameModel().new Character1(intID, 200, 200, 100, 2, 0, 0, 1));
		}else if(intCharType == 4){
			characters.add(new GameModel().new Character1(intID, 200, 200, 100, 2, 0, 0, 1));
		}
			
	}
	
	public void updateChars(){
		
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
				
		//tutorialPanel
		
		//cursor
		frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("customCursor.png").getImage(), new Point(0,0),"custom cursor"));
	} 
	
	///main method
	public static void main(String[] args){
		new GameController();
	} 
}
