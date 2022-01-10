import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class GameView{
	///properties
	JFrame frame = new JFrame("Champions Arena Test 1"); //one frame only
	
	//general panels: UX1 - UX3
	MPanel mainPanel = new MPanel(); //main panel
	///HPanel helpPanel = new HPanel(); //help panel
	///TPanel tutorialPanel = new TPanel(); //interactive tutorial
	
	//once you press play panels: UX4 - UX8
	///LPanel lobbyPanel = new LPanel(); //lobby creation/entering lobby + usernames
	///CPanel charPanel = new CPanel(); //character selection panel
	///GPanel gamePanel = new GPanel(); //actual gameplay panel
	
	//leaderboard/win/lost/end panel: UX9
	///EPanel endPanel = new EPanel(); //once game ends panel / leaderboards
	
	//cap limit panel: UX10
	///CPanel capPanel = new CPanel(); //for users trying to join even though there is a lobby with 4 people or a game already has started
	
	public GameView(){
		//frame
		frame.setPreferredSize(new Dimension(1280, 720));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setContentPane(mainPanel);
		frame.pack();
	} 
}
