import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class GPanel extends JPanel implements ActionListener{
	///properties
	Timer timer = new Timer(1000/60, this);
	
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == timer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 1280, 720);		
	}
	
	public GPanel(){		
		super();
		this.setLayout(null);
		
		timer.start();
		
		//buffered images
	}
		
}
