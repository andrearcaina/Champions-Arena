import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class CPanel extends JPanel implements ActionListener{
	///properties
	Timer timer = new Timer(1000/60, this);
	
	JLabel c1Name = new JLabel("Character 1");
	JLabel c2Name = new JLabel("Character 2");
	JLabel c3Name = new JLabel("Character 3");
	JLabel c4Name = new JLabel("Character 4"); 
	
	//ImageIcon c1Icon = new ImageIcon("c1.png");
	//ImageIcon c2Icon = new ImageIcon("c2.png");
	//ImageIcon c3Icon = new ImageIcon("c3.png");
	//ImageIcon c4Icon = new ImageIcon("c4.png");
	
	JButton c1Button = new JButton("Character");
	JButton c2Button = new JButton("Character");
	JButton c3Button = new JButton("Character");
	JButton c4Button = new JButton("Character");
	
	JButton readyUp = new JButton("Ready Up!");
	JButton startGame = new JButton("Start Game");
	
	JLabel serverIP = new JLabel(); //put server IP of server here
	JLabel waitHost = new JLabel("Waiting for host to start game..."); 
	
	JTextArea chatArea = new JTextArea();
	JTextField chatMessage = new JTextField("Message");
	
	JScrollPane chatPane = new JScrollPane(chatArea);
	
	///methods
	public void actionPerformed(ActionEvent evt){

	}
	
	public void paintComponent(Graphics g){
		g.setColor(new Color(102, 102, 102));
		g.fillRect(0, 0, 1280, 720); 
	}
	
	///constructor
	public CPanel(){
		//c1Name.setBounds(0, 0, 0, 0);
		//c2Name.setBounds(0, 0, 0, 0);
		//c3Name.setBounds(0, 0, 0, 0);
		//c4Name.setBounds(0, 0, 0, 0);
		//c1Button.setBounds(0, 0, 0, 0);
		//c2Button.setBounds(0, 0, 0, 0);
		//c3Button.setBounds(0, 0, 0, 0);
		//c4Button.setBounds(0, 0, 0, 0);
		//readyUp.setBounds(0, 0, 0, 0);
		//startGame.setBounds(0, 0, 0, 0);
		//serverIP.setBounds(0, 0, 0, 0);
		//waitHost.setBounds(0, 0, 0, 0);
		//chatMessage.setBounds(0, 0, 0, 0);
		//chatPane.setBounds(0, 0, 0, 0);
		
		//this.add(c1Name);
		//this.add(c2Name);
		//this.add(c3Name);
		//this.add(c4Name);
		//this.add(c1Button);
		//this.add(c2Button);
		//this.add(c3Button);
		//this.add(c4Button);
		//this.add(readyUp);
		//this.add(startGame);
		//this.add(serverIP);
		//this.add(waitHost);
		//this.add(chatMessage);
		//this.add(chatPane);
	}
	
}
