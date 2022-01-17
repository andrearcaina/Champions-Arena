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
	
	JLabel c1Name = new JLabel("Flamel");
	JLabel c2Name = new JLabel("Bishop");
	JLabel c3Name = new JLabel("Magnus");
	JLabel c4Name = new JLabel("Diablo"); 
	
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
			Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("font2.ttf")).deriveFont(40f);
			Font customFont3 = Font.createFont(Font.TRUETYPE_FONT, new File("font3.TTF")).deriveFont(55f);
			Font customFont4 = Font.createFont(Font.TRUETYPE_FONT, new File("font4.TTF")).deriveFont(60f);
			
			chatArea.setFont(customFont1);
			chatMessage.setFont(customFont1);
			
			c1Name.setFont(customFont1);
			c2Name.setFont(customFont1);
			c3Name.setFont(customFont1);
			c4Name.setFont(customFont1);
			serverIP.setFont(customFont1);
			waitHost.setFont(customFont1);
			
			startGame.setFont(customFont2);
			
			charTitle.setFont(customFont3);
			
			readyUp.setFont(customFont4);
			
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(FontFormatException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
		
		charTitle.setBounds(30, 20, 800, 100);
		charTitle.setForeground(Color.WHITE);
		
		c1Name.setBounds(95, 120, 140, 30);
		c1Name.setForeground(Color.WHITE);
		c2Name.setBounds(260, 120, 140, 30);
		c2Name.setForeground(Color.WHITE);
		c3Name.setBounds(420, 120, 140, 30);
		c3Name.setForeground(Color.WHITE);
		c4Name.setBounds(600, 120, 140, 30);
		c4Name.setForeground(Color.WHITE);
		c1Button.setBounds(75, 160, 140, 140);
		c2Button.setBounds(245, 160, 140, 140);
		c3Button.setBounds(410, 160, 140, 140);
		c4Button.setBounds(580, 160, 140, 140);
		readyUp.setBounds(235, 340, 320, 120);
		
		c1Button.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		c2Button.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		c3Button.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		c4Button.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		
		readyUp.setForeground(Color.WHITE);
		readyUp.setBackground(new Color(102, 102, 102));
		readyUp.setHorizontalAlignment(SwingConstants.CENTER);
		readyUp.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		//startGame.setBounds(0, 0, 0, 0);
		//serverIP.setBounds(0, 0, 0, 0);
		//waitHost.setBounds(0, 0, 0, 0);
		//chatMessage.setBounds(0, 0, 0, 0);
		//chatPane.setBounds(0, 0, 0, 0);
		
		this.add(charTitle);
		this.add(c1Name);
		this.add(c2Name);
		this.add(c3Name);
		this.add(c4Name);
		this.add(c1Button);
		this.add(c2Button);
		this.add(c3Button);
		this.add(c4Button);
		this.add(readyUp);
		//this.add(startGame);
		//this.add(serverIP);
		//this.add(waitHost);
		//this.add(chatMessage);
		//this.add(chatPane);
	}
	
}
