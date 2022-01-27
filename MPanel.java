import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class MPanel extends JPanel implements ActionListener{
	///properties
	Timer timer = new Timer(1000/60, this);
	
	Icon playIcon = new ImageIcon("play.png");
	Icon helpIcon = new ImageIcon("help.png");
	Icon quitIcon = new ImageIcon("quit.png");
	JButton Play = new JButton(playIcon);
	JButton Help = new JButton(helpIcon);
	JButton Quit = new JButton(quitIcon);
	
	BufferedImage Title;
	BufferedImage Logo;
	
	///methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == timer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		g.drawImage(Title, 208, 20, null);
		//g.drawImage(Logo, (some number), (some number), null);
	}
	///constructor
	public MPanel(){
		super();
		this.setLayout(null);
		
		Play.setBounds(150, 150, 220, 130);
		Help.setBounds(150, 300, 220, 130);
		Quit.setBounds(150, 450, 220, 130);
		
		Play.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		Help.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		Quit.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("handCursor.png").getImage(), new Point(0,0),"hand cursor"));
		
		this.add(Play);
		this.add(Help);
		this.add(Quit);
		
		timer.start();
		
		//buffered images
		try{
			Title = ImageIO.read(MPanel.class.getClassLoader().getResourceAsStream("Title.png"));
			//Logo = ImageIO.read(MPanel.class.getClassLoader().getResourceAsStream("Logo.png"));
		}catch(IOException e){
			System.out.println("Unable to load image.");
		}
		
	}
}
