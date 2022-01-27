/// HPanel - Help Menu Panel
/// By: Andre Arcaina, Nicholas Hioe, Sean Kwee
/// ICS 4U1
/// Version 1.0
/// 2021-01-27

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class HPanel extends JPanel implements ActionListener{
	///properties
	Timer timer = new Timer(1000/60, this);
	
	JButton Return = new JButton("Main Menu");
	JButton Next = new JButton("Next");
	
	// Buffered Images
	BufferedImage HelpTitle;
	BufferedImage help1;
	BufferedImage help2;
	BufferedImage help3;
	int intPageCount;
	
	///methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == timer){
			this.repaint();
		}
	}
	
	// Detects what help page they are on, and draws that page (and/or changes the button text accordingly)
	public void paintComponent(Graphics g){
		if(intPageCount == 0){
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1280, 720);
			Next.setText("Next");
			g.drawImage(help1, 0, 0, null );
		}else if(intPageCount == 1){
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1280, 720);
			g.drawImage(help2, 0, 0, null );
		}else if(intPageCount == 2){
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1280, 720);
			g.drawImage(help3, 0, 0, null );
			Next.setText("Tutorial");
		}
	}
	///constructor
	public HPanel(){		
		super();
		this.setLayout(null);
		
		//buffered images and font
		try{
			
			Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, HPanel.class.getClassLoader().getResourceAsStream("font2.ttf")).deriveFont(20f);
			HelpTitle = ImageIO.read(HPanel.class.getClassLoader().getResourceAsStream("HelpTitle.png"));
			help1 = ImageIO.read(HPanel.class.getClassLoader().getResourceAsStream("help1.png"));
			help2 = ImageIO.read(HPanel.class.getClassLoader().getResourceAsStream("help2.png"));
			help3 = ImageIO.read(HPanel.class.getClassLoader().getResourceAsStream("help3.png"));
		
			Return.setFont(customFont2);
			Next.setFont(customFont2);
		
		}catch(IOException e){
			e.toString();
		}catch(FontFormatException e){
			e.toString();
		}
		
		Return.setBounds(1100, 10, 150, 40);
		Next.setBounds(900, 10, 150, 40);
			
		Return.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		Next.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		
		Return.setBackground(Color.BLACK);
		Return.setForeground(Color.WHITE);
		
		Next.setBackground(Color.BLACK);
		Next.setForeground(Color.WHITE);
		
		timer.start();
		
		this.add(Return);
		this.add(Next);
	}
}
