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
	
	//Icon return = new ImageIcon("Return.png");
	JButton Return = new JButton("Return");
	JButton Tutorial = new JButton("Tutorial");
	
	BufferedImage HelpTitle;
	BufferedImage Logo;
	
	///methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == timer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 1280, 720);
		g.drawImage(HelpTitle, 20, -40, null);  
		//g.drawImage(Logo, (some number), (some number), null);
	}
	///constructor
	public HPanel(){		
		super();
		this.setLayout(null);
		
		Return.setBounds(1000, 10, 150, 80);
		Tutorial.setBounds(100, 10, 150, 80);
		
		this.add(Return);
		this.add(Tutorial);
		
		timer.start();
		
		//buffered images
		try{
			HelpTitle = ImageIO.read(new File("HelpTitle.png"));
			//Logo = ImageIO.read(new File("Logo.png"));
		}catch(IOException e){
			System.out.println("Unable to load image.");
		}
	}
}
