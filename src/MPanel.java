/// MPanel - Main Menu Panel
/// By: Andre Arcaina, Nicholas Hioe, Sean Kwee
/// ICS 4U1
/// Version 1.0
/// 2022-01-27

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class MPanel extends JPanel implements ActionListener{
	///properties
	Timer timer = new Timer(1000/60, this);
	
	// JComponents
	Icon playIcon = new ImageIcon("play.png");
	Icon helpIcon = new ImageIcon("help.png");
	Icon quitIcon = new ImageIcon("quit.png");
	JButton Play = new JButton(new ImageIcon(this.getClass().getResource("play.png")));
	JButton Help = new JButton(new ImageIcon(this.getClass().getResource("help.png")));
	JButton Quit = new JButton(new ImageIcon(this.getClass().getResource("quit.png")));
	
	// BufferedImage
	BufferedImage Title;
	
	///methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == timer){
			this.repaint();
		}
	}
	
	// Draws title screen 
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		g.drawImage(Title, 240, 20, null);
	}
	///constructor
	public MPanel(){
		super();
		this.setLayout(null);
		
		Play.setBounds(225, 410, 220, 130);
		Help.setBounds(475, 410, 220, 130);
		Quit.setBounds(725, 410, 220, 130);
		
		Play.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(this.getClass().getResource("handCursor.png")).getImage(), new Point(0,0),"hand cursor"));
		Help.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(this.getClass().getResource("handCursor.png")).getImage(), new Point(0,0),"hand cursor"));
		Quit.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(this.getClass().getResource("handCursor.png")).getImage(), new Point(0,0),"hand cursor"));
		
		this.add(Play);
		this.add(Help);
		this.add(Quit);
		
		timer.start();
		
		//buffered images
		try{
			Title = ImageIO.read(MPanel.class.getClassLoader().getResourceAsStream("Title.png"));
		}catch(IOException e){
			System.out.println(e.toString());
		}
		
	}
}
