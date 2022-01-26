import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class EPanel extends JPanel implements ActionListener{
	///properties	
	Timer timer = new Timer(1000/60, this);
	
	JButton Return = new JButton("Main Menu");
	JLabel endTitle = new JLabel("Leaderboards");
	
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
			Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("font2.TTF")).deriveFont(35f);
			Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("font2.ttf")).deriveFont(20f);
			Font customFont3 = Font.createFont(Font.TRUETYPE_FONT, new File("font2.ttf")).deriveFont(60f);
			
			Return.setFont(customFont2);
			endTitle.setFont(customFont3);		
			
			c1 = ImageIO.read(new File("c1.png"));
			c2 = ImageIO.read(new File("c2.png"));
			c3 = ImageIO.read(new File("c3.png"));
			c4 = ImageIO.read(new File("c4.png"));
		
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(FontFormatException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
		
		endTitle.setBounds(50, 10, 300, 200);
		Return.setBounds(1000, 100, 150, 80);
		
		endTitle.setForeground(Color.WHITE);
		Return.setForeground(Color.WHITE);
		Return.setBackground(Color.GRAY);
		
		Return.setHorizontalAlignment(SwingConstants.CENTER);
		Return.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		
		this.add(endTitle);
		this.add(Return);
		
	}
}
