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
	
	JLabel charTitle = new JLabel("Choose Your Champion");
	
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
	
	//ImageIcon ready = new JButton("readyUp.png");
	JButton readyUp = new JButton("Ready Up!");
	JButton startGame = new JButton("START GAME");
	
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
		super();
		this.setLayout(null);

		try{
			//accesses ttf file, creates it into a ttf font with java swing, and derives the font size using float
			Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("font1.ttf")).deriveFont(30f);
			Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("font2.ttf")).deriveFont(20f);
			Font customFont3 = Font.createFont(Font.TRUETYPE_FONT, new File("font3.TTF")).deriveFont(55f);
			
			chatArea.setFont(customFont1);
			chatMessage.setFont(customFont1);
			
			c1Name.setFont(customFont2);
			c2Name.setFont(customFont2);
			c3Name.setFont(customFont2);
			c4Name.setFont(customFont2);
			
			charTitle.setFont(customFont3);
			
			
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(FontFormatException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
		
		charTitle.setBounds(30, 20, 800, 100);
		charTitle.setForeground(Color.WHITE);
		
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
		
		this.add(charTitle);
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
