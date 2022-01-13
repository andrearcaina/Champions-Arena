import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class GameView implements ActionListener, KeyListener{
	///properties
	JFrame frame = new JFrame("Champions Arena Test 1"); //one frame only
	Timer timer = new Timer(1000/60, this);

	//general panels: UX1 - UX3
	MPanel mainPanel = new MPanel(); //main panel
	HPanel helpPanel = new HPanel(); //help panel
	///TPanel tutorialPanel = new TPanel(); //interactive tutorial
	
	//once you press play panels: UX4 - UX8
	LPanel lobbyPanel = new LPanel(); //lobby creation/entering lobby + usernames
	CPanel charPanel = new CPanel(); //character selection panel
	GPanel gamePanel = new GPanel(); //actual gameplay panel
	GameController model = new GameController();
	GameModel.Character1 c1 = new GameModel().new Character1(1, 200, 200, 100, 1, 0, 0, "g"); //accessing the Character object in the GameModel class

	
	//leaderboard/win/lost/end panel: UX9
	///EPanel endPanel = new EPanel(); //once game ends panel / leaderboards
	
	//cap limit panel: UX10
	///CPanel capPanel = new CPanel(); //for users trying to join even though there is a lobby with 4 people or a game already has started
	
	//method for ActionListener
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
		}else if(evt.getSource() == lobbyPanel.createLobby){ //if this happens, who will be able to see the server's IP information? 
			frame.setContentPane(charPanel);
			frame.pack();
		}else if(evt.getSource() == lobbyPanel.joinLobby){
			frame.setContentPane(charPanel);
			frame.pack();
		}else if(evt.getSource() == helpPanel.Tutorial){
			frame.addKeyListener(this);
			frame.requestFocus();
			timer.start();
			frame.setContentPane(gamePanel);
			frame.pack();
		}
		if(evt.getSource() == timer){
			c1.moveX();
			c1.moveY();
			gamePanel.intX = c1.intX;
			gamePanel.intY = c1.intY;
			gamePanel.intSizeX = c1.intSizeX;
			gamePanel.intSizeY = c1.intSizeY;
		}
	}
	
	public void keyReleased(KeyEvent evt){
		if(evt.getKeyChar() == 'w'){
			c1.intSpeedY = model.up(0); 
			System.out.println("W");
		}else if(evt.getKeyChar() == 'a'){
			c1.intSpeedX = model.right(0); 
			System.out.println("A");
		}else if(evt.getKeyChar() == 's'){
			c1.intSpeedY = model.down(0);
			System.out.println("S");
		}else if(evt.getKeyChar() == 'd'){
			c1.intSpeedX = model.left(0); 
			System.out.println("D");
		}
	} 
	public void keyPressed(KeyEvent evt){
		if(evt.getKeyChar() == 'w'){
			c1.intSpeedY = model.up(5); 
			System.out.println("W");
		}else if(evt.getKeyChar() == 'a'){
			c1.intSpeedX = model.right(5); 
			System.out.println("A");
		}else if(evt.getKeyChar() == 's'){
			c1.intSpeedY = model.down(5);
			System.out.println("S");
		}else if(evt.getKeyChar() == 'd'){
			c1.intSpeedX = model.left(5);
			System.out.println("D");
		}
	}
	public void keyTyped(KeyEvent evt){
	}
	
	public GameView(){
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
		
		//tutorial
		
	} 
}
