import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class LPanel extends JPanel implements ActionListener{
	///properties
	SuperSocketMaster ssm;
	
	Timer timer = new Timer(1000/60, this);
	
	Icon create = new ImageIcon("createLobby.png");
	Icon join = new ImageIcon("joinLobby.png");
	JButton createLobby = new JButton(create);
	JButton joinLobby = new JButton(join);
	JButton Return = new JButton("Main Menu");
	JTextField serverInfo = new JTextField("IP Information");
	JTextField enterIP = new JTextField("Enter IP Address");
	JTextField enterUsername = new JTextField("E.g: DIABLOGAMER1337");
	
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
		}else if(evt.getSource() == createLobby){ //TEMPORARY, THIS IS NOT FIXED FOR THE FINAL ; this doesn't actually do anything for the game
			ssm = new SuperSocketMaster(6112, this);
			boolean blnConnect = ssm.connect();
			
			serverInfo.setText("IP: "+ssm.getMyAddress());
			
			
		}else if(evt.getSource() == joinLobby){
			ssm = new SuperSocketMaster(enterIP.getText(), 6112, this);
			boolean blnConnect = ssm.connect();
			String strConnect = "connect,"+enterUsername.getText();
			if(ssm != null){
				ssm.sendText(strConnect);
			}
			
			
		}else if(evt.getSource() == ssm){
			String strParts[] = ssm.readText().split(",");
			
			// Message type: connect
			if(strParts[0].equals("connect")){
				
				// textchat.append(strParts[1]+" has joined. \n");
			}
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		g.drawImage(lobbyTitle, 290, 10, null);
		g.drawImage(inputUser, 100, 120, null);
		g.drawImage(hostGame, 40, 265, null);
		g.drawImage(joinGame, 350, 265, null);
	}
	///constructor
	public LPanel(){
		super();
		this.setLayout(null);

		try{
			//accesses ttf file, creates it into a ttf font with java swing, and derives the font size using float
			Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("font1.ttf")).deriveFont(30f);
			Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("font2.ttf")).deriveFont(20f);
			enterUsername.setFont(customFont1);
			serverInfo.setFont(customFont1);
			enterIP.setFont(customFont1);
			joinLobby.setFont(customFont2);
			Return.setFont(customFont2);
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(FontFormatException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}

		enterUsername.setBounds(120, 190, 380, 70);
		enterUsername.setBackground(new Color(153, 153, 153));
		enterUsername.setForeground(Color.WHITE);
		enterUsername.setHorizontalAlignment(SwingConstants.CENTER);
		createLobby.setBounds(15, 315, 280, 110);
		joinLobby.setBounds(325, 315, 280, 110);
		serverInfo.setBounds(15, 440, 280, 70);
		enterIP.setBounds(325, 440, 280, 70);
		Return.setBounds(1050, 20, 180, 80);
		
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
		
		createLobby.addActionListener(this);
		
		this.add(createLobby);
		this.add(joinLobby);
		this.add(serverInfo);
		this.add(enterIP);
		this.add(enterUsername);
		this.add(Return);
		
		timer.start();
		
		//buffered images
		try{
			lobbyTitle = ImageIO.read(new File("lobbyTitle.png"));
			inputUser = ImageIO.read(new File("userFile.png"));
			hostGame = ImageIO.read(new File("hostGame.png"));
			joinGame = ImageIO.read(new File("joinGame.png"));
			//championOne = ImageIO.read(new File("c1.png"));
			//championTwo = ImageIO.read(new File("c2.png"));
			//championThree = ImageIO.read(new File("c3.png"));
			//championFour = ImageIO.read(new File("c4.png"));
		}catch(IOException e){
			System.out.println("Unable to load image.");
		}
		
	}
}
