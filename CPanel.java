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
	JLabel c4Name = new JLabel("Shadow"); 
	
	ImageIcon c1Icon = new ImageIcon("c1but.png");
	ImageIcon c2Icon = new ImageIcon("c2but.png");
	ImageIcon c3Icon = new ImageIcon("c3but.png");
	ImageIcon c4Icon = new ImageIcon("c4but.png");
	
	JButton c1Button = new JButton(c1Icon);
	JButton c2Button = new JButton(c2Icon);
	JButton c3Button = new JButton(c3Icon);
	JButton c4Button = new JButton(c4Icon);
	
	JButton readyUp = new JButton("Ready Up!");
	JButton startGame = new JButton("START GAME");
	JButton lockIn = new JButton("Lock Lobby");
	
	JLabel serverIP = new JLabel("number"); //put server IP of server here
	JLabel waitHost = new JLabel("Waiting for host to start game..."); 
	
	JTextArea chatArea = new JTextArea();
	JTextField chatMessage = new JTextField("Message");
	
	JScrollPane chatPane = new JScrollPane(chatArea);
	
	BufferedImage c1but;
	BufferedImage c2but;
	BufferedImage c3but;
	BufferedImage c4but;
	
	int intCharType;
	
	///methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == timer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(new Color(102, 102, 102));
		g.fillRect(0, 0, 1280, 720); 
		
		g.setColor(Color.BLACK);
		g.drawRect(585, 360, 200, 70);
		g.setColor(Color.YELLOW);
		if(intCharType == 1){
			g.drawRect(73, 158, 143, 143);
		}else if(intCharType == 2){
			g.drawRect(243, 158, 143, 143);
		}else if(intCharType == 3){
			g.drawRect(408, 158, 143, 143);
		}else if(intCharType == 4){
			g.drawRect(578, 158, 143, 143);
		}else if(intCharType == 0){
			g.drawRect(0, 0, 0, 0); //for reset
		}
			
	}
	
	///constructor
	public CPanel(){
		super();
		this.setLayout(null);

		try{
			//accesses ttf file, creates it into a ttf font with java swing, and derives the font size using float
			Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, CPanel.class.getClassLoader().getResourceAsStream("font1.ttf")).deriveFont(30f);
			Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, CPanel.class.getClassLoader().getResourceAsStream("font2.ttf")).deriveFont(40f);
			Font customFont3 = Font.createFont(Font.TRUETYPE_FONT, CPanel.class.getClassLoader().getResourceAsStream("font3.TTF")).deriveFont(55f);
			Font customFont4 = Font.createFont(Font.TRUETYPE_FONT, CPanel.class.getClassLoader().getResourceAsStream("font4.TTF")).deriveFont(60f);
			Font customFont5 = Font.createFont(Font.TRUETYPE_FONT, CPanel.class.getClassLoader().getResourceAsStream("font1.ttf")).deriveFont(20f);
			Font customFont6 = Font.createFont(Font.TRUETYPE_FONT, CPanel.class.getClassLoader().getResourceAsStream("font2.ttf")).deriveFont(20f);

			
			chatArea.setFont(customFont6);
			chatMessage.setFont(customFont6);
			
			c1Name.setFont(customFont1);
			c2Name.setFont(customFont1);
			c3Name.setFont(customFont1);
			c4Name.setFont(customFont1);
			serverIP.setFont(customFont5);
			lockIn.setFont(customFont5);
			waitHost.setFont(customFont1);
			
			startGame.setFont(customFont2);
			
			charTitle.setFont(customFont3);
			
			readyUp.setFont(customFont4);
			
		}catch(FileNotFoundException e){
			System.out.println(e.toString());
		}catch(FontFormatException e){
			System.out.println(e.toString());
		}catch(IOException e){
			System.out.println(e.toString());
		}
		timer.start();
		
		startGame.setEnabled(false);
		readyUp.setEnabled(false);
		c1Button.setEnabled(false);
		c2Button.setEnabled(false);
		c3Button.setEnabled(false);
		c4Button.setEnabled(false);
		
		charTitle.setBounds(30, 20, 800, 100);
		charTitle.setForeground(Color.WHITE);
		
		c1Name.setBounds(95, 120, 140, 30);
		c1Name.setForeground(Color.WHITE);
		c2Name.setBounds(260, 120, 140, 30);
		c2Name.setForeground(Color.WHITE);
		c3Name.setBounds(420, 120, 140, 30);
		c3Name.setForeground(Color.WHITE);
		c4Name.setBounds(590, 120, 140, 30);
		c4Name.setForeground(Color.WHITE);
		c1Button.setBounds(75, 160, 140, 140);
		c2Button.setBounds(245, 160, 140, 140);
		c3Button.setBounds(410, 160, 140, 140);
		c4Button.setBounds(580, 160, 140, 140);
		readyUp.setBounds(235, 340, 320, 120);
		serverIP.setBounds(585, 360, 200, 70);
		startGame.setBounds(265, 480, 250, 80);
		lockIn.setBounds(20, 360, 200, 70);
		waitHost.setBounds(123, 580, 660, 70);
		chatPane.setBounds(860, 20, 380, 550);
		chatMessage.setBounds(860, 580, 380, 80);
		
		c1Button.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		c2Button.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		c3Button.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		c4Button.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		
		readyUp.setForeground(Color.WHITE);
		readyUp.setBackground(new Color(102, 102, 102));
		readyUp.setHorizontalAlignment(SwingConstants.CENTER);
		readyUp.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		
		startGame.setForeground(Color.WHITE);
		startGame.setBackground(new Color(102, 102, 102));
		startGame.setHorizontalAlignment(SwingConstants.CENTER);
		startGame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		
		lockIn.setForeground(Color.WHITE);
		lockIn.setBackground(new Color(102, 102, 102));
		lockIn.setHorizontalAlignment(SwingConstants.CENTER);
		lockIn.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		
		serverIP.setForeground(Color.BLACK);
		serverIP.setHorizontalAlignment(SwingConstants.CENTER);
		waitHost.setForeground(Color.WHITE);
		
		chatMessage.setForeground(Color.WHITE);
		chatMessage.setBackground(new Color(102, 102, 102));
		chatArea.setEditable(false);
		chatArea.setForeground(Color.WHITE);
		chatArea.setBackground(new Color(102, 102, 102));
		
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
		this.add(startGame);
		this.add(lockIn);
		this.add(serverIP);
		this.add(waitHost);
		this.add(chatMessage);
		this.add(chatPane);
	}
	
}
