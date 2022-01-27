import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class EPanel extends JPanel implements ActionListener{
	///properties	
	Timer timer = new Timer(1000/60, this);
	
	JButton Return = new JButton("Main Menu");
	JLabel endTitle = new JLabel("THE WINNER IS:");
	
	JLabel winner = new JLabel();
	JLabel funmessage = new JLabel();
	
	BufferedImage c1;
	BufferedImage c2;
	BufferedImage c3;
	BufferedImage c4;
	
	String strDraw;	
	///methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == timer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		
		if(strDraw.equals("Flamel")){
			g.drawImage(c1, 1000, 200, null);
		
		}else if(strDraw.equals("Bishop")){
			g.drawImage(c2, 1000, 200, null);
		
		}else if(strDraw.equals("Magnus")){
			g.drawImage(c3, 1000, 200, null);
		
		}else if(strDraw.equals("Shadow")){
			g.drawImage(c4, 1000, 200, null);
		
		}
	}
	///constructor
	public EPanel(){
		super();
		this.setLayout(null);
		
		try{ 
			//accesses ttf file, creates it into a ttf font with java swing, and derives the font size using float
			Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, EPanel.class.getClassLoader().getResourceAsStream("font2.ttf")).deriveFont(100f);
			Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, EPanel.class.getClassLoader().getResourceAsStream("font2.ttf")).deriveFont(20f);
			Font customFont3 = Font.createFont(Font.TRUETYPE_FONT, EPanel.class.getClassLoader().getResourceAsStream("font2.ttf")).deriveFont(150f);
			
			
			winner.setFont(customFont1);
			funmessage.setFont(customFont2);
			Return.setFont(customFont2);
			endTitle.setFont(customFont3);		
			
			c1 = ImageIO.read(EPanel.class.getClassLoader().getResourceAsStream("c1.PNG"));
			c2 = ImageIO.read(EPanel.class.getClassLoader().getResourceAsStream("c2.png"));
			c3 = ImageIO.read(EPanel.class.getClassLoader().getResourceAsStream("c3.png"));
			c4 = ImageIO.read(EPanel.class.getClassLoader().getResourceAsStream("c4.png"));
		
		}catch(FileNotFoundException e){
			System.out.println(e.toString());
		}catch(FontFormatException e){
			System.out.println(e.toString());
		}catch(IOException e){
			System.out.println(e.toString());
		}
		
		winner.setBounds(50, 220, 1000, 150);	
		funmessage.setBounds(50, 500, 1000, 150);
		
		endTitle.setBounds(50, 10, 1000, 200);
		Return.setBounds(1000, 100, 150, 80);
		
		winner.setForeground(Color.WHITE);
		funmessage.setForeground(Color.WHITE);
		
		endTitle.setForeground(Color.WHITE);
		Return.setForeground(Color.WHITE);
		Return.setBackground(Color.GRAY);
		
		Return.setHorizontalAlignment(SwingConstants.CENTER);
		Return.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		
		this.add(winner);
		this.add(funmessage);
		this.add(endTitle);
		this.add(Return);
		
	}
}
