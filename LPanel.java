import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class LPanel extends JPanel implements ActionListener{
	///properties
	
	Timer timer = new Timer(1000/60, this);
	
	Icon create = new ImageIcon("createLobby.png");
	Icon join = new ImageIcon("joinLobby.png");
	JButton createLobby = new JButton(create);
	JButton joinLobby = new JButton(join);
	JButton Return = new JButton("Main Menu");
	JTextField serverInfo = new JTextField("IP Information");
	JTextField enterIP = new JTextField("Enter IP Address");
	JTextField enterUsername = new JTextField("E.g: DIABLOGAMER1337");
	
	JLabel countdownLabel = new JLabel("Loading Lobby... 5");
	
	BufferedImage lobbyTitle;
	BufferedImage inputUser;
	BufferedImage hostGame;
	BufferedImage joinGame;
	BufferedImage championOne;
	BufferedImage championTwo;
	BufferedImage championThree;
	BufferedImage championFour;
	
	///methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == timer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		g.drawImage(lobbyTitle, 290, 10, null);
		g.drawImage(inputUser, 100, 120, null);
		g.drawImage(hostGame, 40, 295, null);
		g.drawImage(joinGame, 350, 295, null);
		
		// 150 x 200
		g.setColor(new Color(34, 72, 109));
		// g.fillRect(620, 280, 150, 200);
		g.drawImage(championOne, 650, 150, null);
		g.drawImage(championTwo, 850, 150, null);
		g.drawImage(championThree, 650, 400, null);
		g.drawImage(championFour, 850, 400, null);
	}
	///constructor
	public LPanel(){
		super();
		this.setLayout(null);

		try{ //Game Model maybe?
			//accesses ttf file, creates it into a ttf font with java swing, and derives the font size using float
			Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("font1.ttf")).deriveFont(30f);
			Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("font2.ttf")).deriveFont(20f);
			enterUsername.setFont(customFont1);
			serverInfo.setFont(customFont1);
			enterIP.setFont(customFont1);
			countdownLabel.setFont(customFont1);
			Return.setFont(customFont2);
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(FontFormatException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
		
		countdownLabel.setForeground(Color.WHITE);
		countdownLabel.setBounds(10, 600, 300, 50);
		countdownLabel.setVisible(false);
		this.add(countdownLabel);
		
		enterUsername.setBounds(120, 190, 380, 70);
		enterUsername.setBackground(new Color(153, 153, 153));
		enterUsername.setForeground(Color.WHITE);
		enterUsername.setHorizontalAlignment(SwingConstants.CENTER);
		createLobby.setBounds(15, 370, 280, 110);
		createLobby.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		joinLobby.setBounds(325, 370, 280, 110);
		joinLobby.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		serverInfo.setBounds(15, 500, 280, 70);
		enterIP.setBounds(325, 500, 280, 70);
		Return.setBounds(1050, 20, 180, 80);
		Return.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		
		serverInfo.setForeground(Color.WHITE);
		enterIP.setForeground(Color.WHITE);
		Return.setBackground(Color.BLACK);
		Return.setForeground(Color.WHITE);
		createLobby.setBackground(Color.BLACK);
		joinLobby.setBackground(Color.BLACK);
		serverInfo.setBackground(new Color(153, 153, 153));
		serverInfo.setHorizontalAlignment(SwingConstants.CENTER);
		enterIP.setBackground(new Color(153, 153, 153));
		enterIP.setHorizontalAlignment(SwingConstants.CENTER);
		serverInfo.setEditable(false);
		
		
		this.add(createLobby);
		this.add(joinLobby);
		this.add(serverInfo);
		this.add(enterIP);
		this.add(enterUsername);
		this.add(Return);
		
		timer.start();
		
		//buffered images
		try{
			lobbyTitle = ImageIO.read(LPanel.class.getClassLoader().getResourceAsStream("lobbyTitle.png"));
			inputUser = ImageIO.read(LPanel.class.getClassLoader().getResourceAsStream("userFile.PNG"));
			hostGame = ImageIO.read(LPanel.class.getClassLoader().getResourceAsStream("hostGame.png"));
			joinGame = ImageIO.read(LPanel.class.getClassLoader().getResourceAsStream("joinGame.PNG"));
			championOne = ImageIO.read(LPanel.class.getClassLoader().getResourceAsStream("c1.PNG"));
			championTwo = ImageIO.read(LPanel.class.getClassLoader().getResourceAsStream("c2.png"));
			championThree = ImageIO.read(LPanel.class.getClassLoader().getResourceAsStream("c3.png"));
			championFour = ImageIO.read(LPanel.class.getClassLoader().getResourceAsStream("c4.png"));
		}catch(IOException e){
			System.out.println(e.toString());
		}
		
	}
}
