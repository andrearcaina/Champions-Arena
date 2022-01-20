import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class GPanel extends JPanel implements ActionListener{
	///properties
	Timer timer = new Timer(1000/60, this);
	
	///methods
	public void actionPerformed(ActionEvent evt){
		
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0,0,1280,720);
	}
	
	///constructor
	public GPanel(){		
		super();
		this.setLayout(null);
		timer.start();
	}
	
		
}
