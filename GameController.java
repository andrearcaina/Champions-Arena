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
	
	///GAME VIEW INCORPORATED: ANIMATED JPANELS
	//general panels: UX1 - UX3
	MPanel mainPanel = new MPanel(); //main panel
	HPanel helpPanel = new HPanel(); //help panel
	TPanel tutorialPanel = new TPanel(); //interactive tutorial
	
	//once you press play panels: UX4 - UX8
	LPanel lobbyPanel = new LPanel(); //lobby creation/entering lobby + usernames
	CPanel charPanel = new CPanel(); //character selection panel
	///GPanel gamePanel = new GPanel(); //actual gameplay panel
	
	//leaderboard/win/lost/end panel: UX9
	///EPanel endPanel = new EPanel(); //once game ends panel / leaderboards
	
	//cap limit panel: UX10
	///CPanel capPanel = new CPanel(); //for users trying to join even though there is a lobby with 4 people or a game already has started
	
	//Accessing the Character object in the GameModel class
	GameModel.Character1 c1 = new GameModel().new Character1(1, 200, 200, 100, 1, 0, 0, "g");
	GameModel.Character1 cT = new GameModel().new Character1(100, 400, 0, 100, 1, 0, 0, "g"); 
	ArrayList<GameModel.Terrain1> map = new ArrayList<GameModel.Terrain1>();
	
	boolean blnPlaying = false;
	
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
		}else if(evt.getSource() == lobbyPanel.createLobby){
			frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("timeCursor.png").getImage(), new Point(0,0),"time cursor"));
			lobbyPanel.countdownLabel.setVisible(true);
			countdownTimer.start();
		}else if(evt.getSource() == lobbyPanel.joinLobby){
			//figure this out later
			frame.setContentPane(charPanel);
			frame.pack();
		}else if(evt.getSource() == helpPanel.Tutorial){
			frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("aimCursor.png").getImage(), new Point(0,0),"aim cursor"));
			frame.addKeyListener(this);
			frame.addMouseListener(this);
			frame.requestFocus();
			timer.start();
			blnPlaying = true;
			frame.setContentPane(tutorialPanel);
			frame.pack();
			tutorialPanel.projectiles = c1.projectiles;
			loadMap();
			frame.requestFocus();
		}		
		if(evt.getSource() == timer){
			c1.moveX();
			c1.moveY();
			collision();
			tutorialPanel.intX = c1.intX;
			tutorialPanel.intY = c1.intY;
			tutorialPanel.intSizeX = c1.intSizeX;
			tutorialPanel.intSizeY = c1.intSizeY;
			c1.update();
			tutorialPanel.projectiles = c1.projectiles;
			tutorialPanel.intHP = c1.intHP;
			tutorialPanel.map = map;
		}
		
		else if(evt.getSource() == countdownTimer){  
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
	public void keyPressed(KeyEvent evt){
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
	
	//methods for KeyListener (PROJECTILES)
	public void keyTyped(KeyEvent evt){
		if(evt.getKeyChar() == 'l'){
			cT.shoot(0,4,10);
			cT.update();
			c1.projectiles.addAll(cT.projectiles);
			cT.projectiles.remove(0);
		}
	}
	
	public void mouseExited(MouseEvent evt){
		
	}
	public void mouseReleased(MouseEvent evt){
		
	}
	public void mouseEntered(MouseEvent evt){
		
	}
	
	public void mouseClicked(MouseEvent evt){
		if(blnPlaying){
			int intX = evt.getX();
			int intY = evt.getY();
			if(intX <= c1.intX && intY <= c1.intY){ // based on mouse location, will shoot in that location.
				c1.shoot(-4,-4,10);
				tutorialPanel.projectiles = c1.projectiles;
			}else if(intX <= c1.intX && intY >= c1.intY){
				c1.shoot(-4,4,10);
				tutorialPanel.projectiles = c1.projectiles;
			}else if(intX >= c1.intX && intY <= c1.intY){
				c1.shoot(4,-4,10);
				tutorialPanel.projectiles = c1.projectiles;
			}else if(intX >= c1.intX && intY >= c1.intY){
				c1.shoot(4,4,10);
				tutorialPanel.projectiles = c1.projectiles;
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
			String[][] mapData = new String[286][5];
			for(intRow = 0; intRow < 286; intRow++){
				strRead = map1.readLine();
				strSplit = strRead.split(",");
				for(intCol = 0; intCol < 5; intCol++){
					mapData[intRow][intCol] = strSplit[intCol];
					tutorialPanel.mapData[intRow][intCol] = mapData[intRow][intCol];
				}
			}
			//map.add(new GameModel().new Terrain1(500, 300, 50, 50)); // temp
		}catch(IOException e){
			e.toString();
		}
	}
	
	public void collision(){//Collision detection
		for(int intCount = c1.projectiles.size() -1; intCount >= 0; intCount--){ // projectiles
			if(c1.projectiles.get(intCount).intX < c1.intX+c1.intSizeX && c1.projectiles.get(intCount).intY < c1.intY+c1.intSizeY && 
			(c1.projectiles.get(intCount).intSize+c1.projectiles.get(intCount).intX) > c1.intX && 
			(c1.projectiles.get(intCount).intSize+c1.projectiles.get(intCount).intY) > c1.intY){
				if(c1.projectiles.get(intCount).intID != c1.intID){
					c1.collision(c1.projectiles.get(intCount).intID, c1.projectiles.get(intCount).intDamage);
					c1.projectiles.remove(intCount);
				}
			}
		}
		for(int intCount = map.size() -1; intCount >= 0; intCount--){ // terrain
			if(map.get(intCount).intX < c1.intX+c1.intSizeX && map.get(intCount).intY < c1.intY+c1.intSizeY && 
			(map.get(intCount).intSizeX+map.get(intCount).intX) > c1.intX && 
			(map.get(intCount).intSizeY+map.get(intCount).intY) > c1.intY){
				c1.collision(map.get(intCount).intID, 0);
			}
			for(int intCount2 = c1.projectiles.size() -1; intCount2 >= 0; intCount2--){ // projectiles w/ terrain
				if(c1.projectiles.get(intCount2).intX < map.get(intCount).intX+map.get(intCount).intSizeX && c1.projectiles.get(intCount2).intY < map.get(intCount).intSizeY+map.get(intCount).intY && 
				(c1.projectiles.get(intCount2).intSize+c1.projectiles.get(intCount2).intX) > map.get(intCount).intX && 
				(c1.projectiles.get(intCount2).intSize+c1.projectiles.get(intCount2).intY) > map.get(intCount).intY){
					c1.projectiles.remove(intCount2);
				}
			}
		}		
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
		
		//tutorialPanel
		
		//cursor
		frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("customCursor.png").getImage(), new Point(0,0),"custom cursor"));
	} 
	
	///main method
	public static void main(String[] args){
		new GameController();
	} 
}
