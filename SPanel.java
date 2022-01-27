import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class SPanel extends JPanel implements ActionListener{
	///properties	
	Timer timer = new Timer(1000/60, this);
	
	JLabel sorryTitle = new JLabel("Sorry...");
	JLabel labelOne = new JLabel("But it looks like you were too late to join a game or there is already");
	JLabel labelTwo = new JLabel(); 
	JButton Return = new JButton("Main Menu");
	
	BufferedImage sad;
	
	
	///methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == timer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		g.drawImage(sad, 500, 400, null);
	}
	///constructor
	public SPanel(){
		super();
		this.setLayout(null);
		
		try{ 
			//accesses ttf file, creates it into a ttf font with java swing, and derives the font size using float
			Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, SPanel.class.getClassLoader().getResourceAsStream("font2.ttf")).deriveFont(35f);
			Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, SPanel.class.getClassLoader().getResourceAsStream("font2.ttf")).deriveFont(20f);
			Font customFont3 = Font.createFont(Font.TRUETYPE_FONT, SPanel.class.getClassLoader().getResourceAsStream("font2.ttf")).deriveFont(60f);
			
			labelOne.setFont(customFont1);
			labelTwo.setFont(customFont1);
			
			Return.setFont(customFont2);
			sorryTitle.setFont(customFont3);		
			sad = ImageIO.read(new File("bigSad.png"));
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(FontFormatException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
		
		sorryTitle.setBounds(50, 10, 300, 200);
		labelOne.setBounds(40, 210, 1000, 50);
		labelTwo.setBounds(40, 260, 1000, 50);
		Return.setBounds(40, 400, 200, 100);
		
		sorryTitle.setForeground(Color.WHITE);
		labelOne.setForeground(Color.WHITE);
		labelTwo.setForeground(Color.WHITE);
		Return.setForeground(Color.WHITE);
		Return.setBackground(Color.BLACK);
		
		Return.setHorizontalAlignment(SwingConstants.CENTER);
		Return.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		
		this.add(sorryTitle);
		this.add(labelOne);
		this.add(labelTwo);
		this.add(Return);
		
	}
}
